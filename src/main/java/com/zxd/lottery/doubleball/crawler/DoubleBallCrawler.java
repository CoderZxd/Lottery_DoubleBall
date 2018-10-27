package com.zxd.lottery.doubleball.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.currentThread;

/**
 * @author CoderZZ
 * @Title: ${FILE_NAME}
 * @Project: DoubleBall
 * @Package com.zxd.lottery.doubleball.crawler
 * @description: 双色球(6+1):red:1-33,blue:1-16
 * @Version 1.0
 * @create 2018-10-26 0:35
 **/
public class DoubleBallCrawler {

    private static String url_prefix = "http://kaijiang.500.com/shtml/ssq/";

    private static String url_suffix = ".shtml";

    //03年第1期开奖页面
    private static String url = "http://kaijiang.500.com/shtml/ssq/03001.shtml";

    //多线程爬取
    private static int threadNum = 10;

    private static CountDownLatch countDownLatch = new CountDownLatch(threadNum);

    //线程安全list,TODO:可以改为普通list
    private static List<ResultDto> resultDtoList = new Vector<ResultDto>(2500);

    //抓取成功期数开奖信息写入该文件
    private static String fileName = "lottery.txt";

    //抓取失败的期数写入该文件
    private static String failureNumFileName = "failure.txt";

    private static List<String> failureNums = new Vector<String>(10);

    public static void main(String[] args) throws Exception{
        //批量抓取所有
//        crawlerForBatch();
//
//        //抓取单期开奖结果
//        ResultDto resultDto = crawlerForOnce("03001");

        //将批量信息写入本地
//        writeLotteryInfoToFile(fileName,resultDtoList);
    }

    /**
     * @FileName DoubleBallCrawler.java
     * @ClassName DoubleBallCrawler
     * @MethodName crawlerForBatch
     * @Desc 批量多线程抓取信息
     * @author zouxiaodong
     * @date 2018/10/27 15:39
     * @Params []
     * @return void
     */
    public static void crawlerForBatch(){
        try {
            Document doc = Jsoup.connect(url).get();
            Elements aElements =doc.select(".iSelectList > a");
            if(null != aElements){
                System.out.println("一共期数为:"+aElements.size());
                for(Element aEle:aElements){
                    ResultDto resultDto = new ResultDto();
                    resultDto.setNumber(aEle.text());
                    resultDto.setHref(aEle.attr("href"));
                    resultDtoList.add(resultDto);
                }
                System.out.println(resultDtoList);
                ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
                for(int i=0;i<threadNum;i++){
                    final int localI = i;
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            for(int j=localI;j<resultDtoList.size();j=j+threadNum) {
                                ResultDto resultDto = resultDtoList.get(j);
                                try {
                                    Document detailDoc = Jsoup.connect(resultDto.getHref()).get();
                                    getLotteryInfo(detailDoc,resultDto);
                                } catch (IOException e) {
                                    //记录爬取失败的开奖期号
                                    failureNums.add(resultDto.getNumber());
                                    System.err.println("爬取双色球第"+resultDto.getNumber()+"期开奖结果异常(开奖结果详情页:"+resultDto.getHref()+ ").异常信息:"+e.getMessage());
                                }
                            }
                            System.out.println(currentThread().getName()+"抓取结束!");
                            countDownLatch.countDown();
                        }
                    });
                }
                countDownLatch.await();
                System.out.println("所有线程抓取结束,程序继续执行.........");
                //如果failureNums不为空，说明有爬取失败,将失败的期号写入本地文件
                if(!failureNums.isEmpty()){
                    System.out.println("存在爬取失败的期号,写入本地文件"+failureNumFileName+"中......");
                    Collections.sort(failureNums);
                    writeFailureNumToFile(failureNums);
                }
                System.out.println(resultDtoList);
                Collections.sort(resultDtoList);
                System.out.println(resultDtoList);
                executorService.shutdown();
            }
        }catch (Exception e){
            System.err.println("爬取"+url+ "异常.异常信息:"+e.getMessage());
        }
    }

    /**
     * @FileName DoubleBallCrawler.java
     * @ClassName DoubleBallCrawler
     * @MethodName crawlerForOnce
     * @Desc 抓取某一期信息
     * @author zouxiaodong
     * @date 2018/10/27 15:26
     * @Params [num 期号]
     * @return void
     */
    public static ResultDto crawlerForOnce(String num){
        String url = url_prefix + num + url_suffix;
        ResultDto resultDto = new ResultDto();
        try {
            Document detailDoc = Jsoup.connect(url).get();
            getLotteryInfo(detailDoc,resultDto);
        } catch (IOException e) {
            System.err.println("爬取双色球第"+resultDto.getNumber()+"期开奖结果异常(开奖结果详情页:"+resultDto.getHref()+ ").异常信息:"+e.getMessage());
        }
        return resultDto;
    }

    /**
     * @FileName DoubleBallCrawler.java
     * @ClassName DoubleBallCrawler
     * @MethodName getLotteryInfo
     * @Desc doc转dto
     * @author zouxiaodong
     * @date 2018/10/27 15:34
     * @Params [detailDoc, resultDto]
     * @return void
     */
    private static void getLotteryInfo(Document detailDoc,ResultDto resultDto){
        Elements balls = detailDoc.select("div.ball_box01 li");
        for(int k=0;k<balls.size();k++){
            Element ball = balls.get(k);
            switch (k){
                case 0:
                    resultDto.setRed1(ball.text());
                    break;
                case 1:
                    resultDto.setRed2(ball.text());
                    break;
                case 2:
                    resultDto.setRed3(ball.text());
                    break;
                case 3:
                    resultDto.setRed4(ball.text());
                    break;
                case 4:
                    resultDto.setRed5(ball.text());
                    break;
                case 5:
                    resultDto.setRed6(ball.text());
                    break;
                case 6:
                    resultDto.setBlue(ball.text());
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * @FileName DoubleBallCrawler.java
     * @ClassName DoubleBallCrawler
     * @MethodName writeLotteryInfoToFile
     * @Desc 将开奖信息写入本地文件
     * @author zouxiaodong
     * @date 2018/10/27 15:54
     * @Params [fileName, resultDtoList]
     * @return void
     */
    private static void writeLotteryInfoToFile(String fileName,List<ResultDto> resultDtoList){
        try {
            FileOutputStream fos = new FileOutputStream(new File(fileName),true);
            for(ResultDto resultDto:resultDtoList){
                fos.write(resultDto.toString().concat("\n").getBytes("UTF-8"));
            }
            fos.close();
        } catch (Exception e) {
            System.err.println("开奖信息写入本地文件异常.异常信息为:"+e.getMessage());
        }
    }

    /**
     * @FileName DoubleBallCrawler.java
     * @ClassName DoubleBallCrawler
     * @MethodName writeFailureNumToFile
     * @Desc 将失败的期数写入本地文件
     * @author zouxiaodong
     * @date 2018/10/27 17:31
     * @Params [numbers]
     * @return void
     */
    private static void writeFailureNumToFile(List<String> numbers){
        try {
            FileOutputStream fos = new FileOutputStream(new File(failureNumFileName),true);
            for(String num:numbers){
                fos.write(num.concat("\n").getBytes("UTF-8"));
            }
            fos.close();
        } catch (Exception e) {
            System.err.println("异常期号写入本地文件异常.异常信息为:"+e.getMessage());
        }
    }
}
