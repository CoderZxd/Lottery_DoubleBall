package com.zxd.lottery.doubleball.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Collections;
import java.util.Vector;
import java.util.concurrent.*;

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

    //03年第1期开奖页面
    private static String url = "http://kaijiang.500.com/shtml/ssq/03001.shtml";

    //多线程爬取
    private static int threadNum = 10;

    private static CountDownLatch countDownLatch = new CountDownLatch(threadNum);

    //线程安全list,TODO:可以改为普通list
    private static Vector<ResultDto> resultDtoList = new Vector<ResultDto>(2500);

    public static void main(String[] args) throws Exception{
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
                                    Elements balls = detailDoc.select("div.ball_box01 li");
                                    for(int k=0;k<balls.size();k++){
                                        Element ball = balls.get(k);
                                        switch (k){
                                            case 0:
                                                resultDto.setRed1(ball.text());
                                            case 1:
                                                resultDto.setRed2(ball.text());
                                            case 2:
                                                resultDto.setRed3(ball.text());
                                            case 3:
                                                resultDto.setRed4(ball.text());
                                            case 4:
                                                resultDto.setRed5(ball.text());
                                            case 5:
                                                resultDto.setRed6(ball.text());
                                            case 6:
                                                resultDto.setBlue(ball.text());
                                        }
                                    }
                                } catch (IOException e) {
                                    System.err.println("爬取双色球第"+resultDto.getNumber()+"期开奖结果异常(开奖结果详情页:"+resultDto.getHref()+ ").异常信息:"+e.getMessage());
                                }
                            }
                            System.out.println(Thread.currentThread().getName()+"抓取结束!");
                            countDownLatch.countDown();
                        }
                    });
                }
                countDownLatch.await();
                System.out.println("所有线程抓取结束,程序继续执行.........");
                System.out.println(resultDtoList);
                Collections.sort(resultDtoList);
                System.out.println(resultDtoList);
                executorService.shutdown();
            }
        }catch (Exception e){
            System.err.println("爬取"+url+ "异常.异常信息:"+e.getMessage());
        }
    }
}
