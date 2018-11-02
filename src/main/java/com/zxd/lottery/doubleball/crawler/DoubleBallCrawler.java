package com.zxd.lottery.doubleball.crawler;

import com.zxd.lottery.doubleball.crawler.dao.mapper.ResultDao;
import com.zxd.lottery.doubleball.crawler.dao.model.ResultDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
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
@Service
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

    @Autowired
    private static ResultDao resultDao;

    public static void main(String[] args) throws Exception{
        //批量抓取所有
//        crawlerForBatch();

//        //抓取单期开奖结果
//        ResultDto resultDto = crawlerForOnce("18127");
//        resultDtoList.add(resultDto);
//        writeLotteryInfoToFile(fileName,resultDtoList);

        //将批量信息写入本地
//        writeLotteryInfoToFile(fileName,resultDtoList);


        //以下逻辑为抓取失败的开奖结果
//        String[] failureArrays = new String[]{"09092","09097","09102","09107","09108","09112","09114","09117","09118","09121","09122","09124","09127","09128","09131","09132","09134","09137","09138","09141","09142","09144","09147","09148","09149","09151","09152","09154","10003","10004","10005","10007","10008","10010","10013","10014","10015","10017","10018","10020","10023","10024","10025","10027","10028","10030","10033","10034","10035","10037","10038","10040","10043","10044","10045","10047","10048","10050","10053","10054","10055","10057","10058","10060","10063","10064","10065","10067","10068","10070","10073","10074","10075","10077","10078","10080","10083","10084","10085","10087","10088","10090","10093","10094","10095","10097","10098","10100","10103","10104","10105","10107","10108","10109","10110","10113","10114","10115","10117","10118","10119","10120","10123","10124","10125","10126","10127","10128","10129","10130","10132","10133","10134","10135","10136","10137","10138","10139","10140","10141","10142","10143","10144","10145","10146","10147","10148","10149","10150","10151","10152","10153","11001","11002","11003","11004","11005","11006","11007","11008","11009","11010","11011","11012","11013","11014","11015","11016","11017","11018","11019","11020","11021","11022","11023","11024","11025","11026","11027","11028","11029","11030","11031","11032","11033","11034","11035","11036","11037","11038","11039","11040","11041","11042","11043","11044","11045","11046","11047","11048","11049","11050","11051","11052","11053","11054","11055","11056","11057","11058","11059","11060","11061","11062","11063","11064","11065","11066","11067","11068","11069","11070","11071","11072","11073","11074","11075","11076","11077","11078","11079","11080","11081","11082","11083","11084","11085","11086","11087","11088","11089","11090","11091","11092","11093","11094","11095","11096","11097","11098","11099","11100","11101","11102","11103","11104","11105","11106","11107","11108","11109","11110","11111","11112","11113","11114","11115","11116","11117","11118","11119","11120","11121","11122","11123","11124","11125","11126","11127","11128","11129","11130","11131","11132","11133","11134","11135","11136","11137","11138","11139","11140","11141","11142","11143","11144","11145","11146","11147","11148","11149","11150","11151","11152","11153","12001","12002","12003","12004","12005","12007","12008","12009","12010","12011","12012","12013","12014","12015","12017","12018","12019","12020","12021","12022","12023","12024","12025","12028","12033","12034","12035","12038","12043","12044","12045","12048","12053","12054","12055","12058","12063","12064","12065","12068","12073","12074","12075","12078","12083","12084","12085","12088","12093","12094","12095","12098","12103","12104","12105","12113","12114","12115","12123","12124","12133","12134","12143","12144","12153","12154","13009","13010","13019","13020","13029","13030","13040","13050"};
//        List<String> numbersList = new ArrayList<String>(failureArrays.length);
//        numbersList = Arrays.asList(failureArrays);
//        crawlerBarchForFailure(numbersList);
//        writeLotteryInfoToFile(fileName,resultDtoList);


        //将之前抓取的结果从文件中存入db
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("crawler-application.xml");
        ResultDao resultDao = applicationContext.getBean(ResultDao.class);
//        List<ResultDto> resultDtoList = getResultDtoFromFile();
//        for(ResultDto resultDto:resultDtoList){
//            resultDao.insert(resultDto);
//        }

        //抓取单次开奖记录插入数据库
//        ResultDto resultDto = crawlerForOnce("18128");
//        resultDao.insert(resultDto);

//        List<ResultDto> list = resultDao.getAll();
    }

    public static void calculateNumTimes(List<ResultDto> resultDtos){
        Map<String,Map<String,Integer>> result = new HashMap<String,Map<String,Integer>>(16);
    }

    /**
     * class_name: getResultDtoFromFile
     * param: []
     * describe: 将lottery.txt转换为对象存入数据库中
     * creat_user: CoderZZ
     * creat_date: 2018-11-01
     * creat_time: 0:07
     **/
    private static List<ResultDto> getResultDtoFromFile(){
        List<ResultDto> resultDtoList = new ArrayList<ResultDto>(2500);
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
            String line;
            while ((line = br.readLine())!= null){
                ResultDto temp = new ResultDto();
                String[] infoArray = line.split(",");
                String numberInfo = infoArray[0];
                String[] numberArray = numberInfo.split("\\s");
                temp.setNumber(numberArray[1].substring(1,6));
                String href = infoArray[1];
                temp.setHref(href.substring(5));
                String num = infoArray[2];
                String[] numArray = num.split("\\|");
                temp.setBlue(numArray[1].trim());
                String red = numArray[0].trim();
                String[] redArray = red.split("\\s");
                temp.setRed1(redArray[0].substring(5));
                temp.setRed2(redArray[1]);
                temp.setRed3(redArray[2]);
                temp.setRed4(redArray[3]);
                temp.setRed5(redArray[4]);
                temp.setRed6(redArray[5]);
                resultDtoList.add(temp);
            }
        }catch (Exception e){
            System.out.println("读取文件异常.异常信息为:"+e.getMessage());
        }
        return resultDtoList;
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
                                    Document detailDoc = null;
                                    //TODO:暂时不使用代理
                                    detailDoc = Jsoup.connect(resultDto.getHref()).proxy(null).get();
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
                    writeFailureNumToFile(failureNumFileName,failureNums);
                }
                Collections.sort(resultDtoList);
                System.out.println("抓取开奖结果为："+resultDtoList);
                executorService.shutdown();
            }
        }catch (Exception e){
            System.err.println("爬取"+url+ "异常.异常信息:"+e.getMessage());
        }
    }

    /**
     * class_name: crawlerBarchForFailure
     * param: [numbersList]
     * describe: 批量抓取失败过的期数开奖信息
     * creat_user: CoderZZ
     * creat_date: 2018-10-28
     * creat_time: 0:35
     **/
    public static void crawlerBarchForFailure(List<String> numbersList){
        try {
            String[] proxyArray = new String[]{"118.190.95.35:9001","140.224.108.227:53281","61.135.217.7:80","115.46.66.25:8123","113.77.87.113:8118","222.182.56.234:8118","222.76.74.214:808","113.16.160.101:8118","113.102.80.39:8118","101.132.98.70:808"};
            final List<Map<String,String>> proxyList = new ArrayList<Map<String,String>>(10);
            for(String proxy:proxyArray){
                String[] ipAndPort = proxy.split(":");
                Map<String,String> temp = new HashMap<String,String>(16);
                temp.put("ip",ipAndPort[0]);
                temp.put("port",ipAndPort[1]);
                proxyList.add(temp);
            }
            if(null != numbersList && !numbersList.isEmpty()){
                for(String num:numbersList){
                    ResultDto resultDto = new ResultDto();
                    resultDto.setNumber(num);
                    String href = url_prefix + num + url_suffix;
                    resultDto.setHref(href);
                    resultDtoList.add(resultDto);
                }
                System.out.println("重新抓取失败列表:"+resultDtoList);
                ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
                for(int i=0;i<threadNum;i++){
                    final int localI = i;
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            for(int j=localI;j<resultDtoList.size();j=j+threadNum) {
                                ResultDto resultDto = resultDtoList.get(j);
                                String ip = proxyList.get(localI).get("ip");
                                String port = proxyList.get(localI).get("port");
                                System.out.println("proxy@@@@@@@@@@@@@"+ip+":"+port);
                                try {
                                    Document detailDoc = Jsoup.connect(resultDto.getHref()).timeout(5000).proxy(ip,Integer.parseInt(port)).get();
//                                    Document detailDoc = Jsoup.connect(resultDto.getHref()).timeout(5000).proxy(null).get();
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
                    writeFailureNumToFile(failureNumFileName,failureNums);
                }
                Collections.sort(resultDtoList);
                executorService.shutdown();
            }
        }catch (Exception e){
            System.err.println("爬取"+url+ "异常.异常信息:"+e.getMessage());
        }
    }

    /**
     * class_name: checkProxy
     * param: [ip, port]
     * describe: 检查代理是否可用
     * creat_user: CoderZZ
     * creat_date: 2018-10-30
     * creat_time: 0:50
     **/
    private static boolean checkProxy(String ip, Integer port) {
        try {
            Jsoup.connect("https://www.baidu.com/").timeout(2000).proxy(ip, port).get();
            return true;
        } catch (Exception e) {
            return false;
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
        resultDto.setNumber(num);
        resultDto.setHref(url);
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
                //将爬取失败的开奖信息过滤掉
                if(resultDto.getRed1() != null && !"".equals(resultDto.getRed1())){
                    fos.write(resultDto.toString().concat("\n").getBytes("UTF-8"));
                }
            }
            fos.write("===============================================分割线===============================================".concat("\n").getBytes("UTF-8"));
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
     * @Params [fileName,numbers]
     * @return void
     */
    private static void writeFailureNumToFile(String fileName,List<String> numbers){
        try {
            FileOutputStream fos = new FileOutputStream(new File(fileName),true);
            for(String num:numbers){
                fos.write(num.concat("\n").getBytes("UTF-8"));
            }
            fos.write("========分割线========".concat("\n").getBytes("UTF-8"));
            fos.close();
        } catch (Exception e) {
            System.err.println("异常期号写入本地文件异常.异常信息为:"+e.getMessage());
        }
    }
}
