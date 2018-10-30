package com.zxd.lottery.doubleball.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
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
 * @description: 抓取代理IP(http://www.xicidaili.com/nn/)，验证代理IP是否可用
 * @Version 1.0
 * @create 2018-10-30 1:07
 **/
public class IpPoolCrawler {

    //url前缀
    private static String ipProxyUrl_prefix = "http://www.xicidaili.com/nn/";

    //默认爬取起始页
    private static int startPage = 20;

    //第一页地址
    private static String ipProxyUrl = "http://www.xicidaili.com/nn/1";

    //通过使用代理ip访问该url校验代理是否可用
    private static String proxyCheckUrl = "https://www.baidu.com";

    //爬取的所有代理ip
    private static List<IpInfo> ipInfoList = new ArrayList<IpInfo>(10);

    //检查可以用的代理ip,线程安全
    private static List<IpInfo> enableIpProxyList = new Vector<IpInfo>(10);

    //多线程抓取
    private static int threadNum = 2;

    //多线程check
    private static int checkThreadNum = 20;


    private static CountDownLatch countDownLatch = new CountDownLatch(threadNum);



    /**
     * class_name: checkProxy
     * param: [ip, port]
     * describe: 验证代理是否可用
     * creat_user: CoderZZ
     * creat_date: 2018-10-30
     * creat_time: 1:09
     **/
    private static boolean checkProxy(String ip, Integer port) {
        try {
            Jsoup.connect(proxyCheckUrl).timeout(5000).proxy(ip, port).get();
            System.out.println("代理IP("+ip+":"+port+")可用。");
            return true;
        } catch (Exception e) {
//            System.err.println("线程"+Thread.currentThread().getName()+"校验代理IP("+ip+":"+port+")不可用。异常信息为:"+e.getMessage());
            return false;
        }
    }


    /**
     * @FileName IpPoolCrawler.java
     * @ClassName IpPoolCrawler
     * @MethodName MultiThreadToCheckProxy
     * @Desc 多线程校验代理ip是否可用
     * @author zouxiaodong
     * @date 2018/10/30 17:27
     * @Params []
     * @return void
     */
    private static void MultiThreadToCheckProxy(){
        try {
            countDownLatch = new CountDownLatch(checkThreadNum);
            ExecutorService executorService = Executors.newFixedThreadPool(checkThreadNum);
            for(int i=0;i<checkThreadNum;i++){
                final int localI = i;
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        for(int j=localI;j<ipInfoList.size();j=j+checkThreadNum) {
                            IpInfo ipInfo = ipInfoList.get(j);
                            boolean b = checkProxy(ipInfo.getIp(),ipInfo.getPort());
                            if(b){
                                enableIpProxyList.add(ipInfo);
                            }
                        }
                        countDownLatch.countDown();
                        System.out.println(currentThread().getName()+"校验结束!");
                    }
                });
            }
            countDownLatch.await();
            executorService.shutdown();
            System.out.println("enableIpProxyList:"+enableIpProxyList);
        } catch (InterruptedException e) {
            System.out.println("多线程校验代理异常.异常信息为:"+e.getMessage());
        }
    }

    /**
     * @FileName IpPoolCrawler.java
     * @ClassName IpPoolCrawler
     * @MethodName crawlerIps
     * @Desc 爬取ip代理网站的ip地址，并校验是否可用
     * @author zouxiaodong
     * @date 2018/10/30 16:24
     * @Params [ipProxyUrl ]
     * @return void
     */
    private static void crawlerIps(String ipProxyUrl){
        try{
            Document doc = Jsoup.connect(ipProxyUrl).get();
            if(null != doc){
                Element table = doc.getElementById("ip_list");
                Elements trs = table.getElementsByTag("tr");
                for(Element tr:trs){
                    Elements tds = tr.getElementsByTag("td");
                    if(null != tds && !tds.isEmpty()){
                        Elements countryImgs = tds.first().getElementsByTag("img");
                        IpInfo ipInfo = new IpInfo();
                        if(countryImgs.first() != null){
                            ipInfo.setCountry(countryImgs.first().attr("alt"));
                        }
                        ipInfo.setIp(tds.get(1).text());
                        ipInfo.setPort(Integer.parseInt(tds.get(2).text()));
                        ipInfoList.add(ipInfo);
                    }
                }
            }
        }catch (Exception e){
            System.out.println("爬取代理IP异常.异常信息为:"+e.getMessage());
        }
    }


    /**
     * @FileName IpPoolCrawler.java
     * @ClassName IpPoolCrawler
     * @MethodName multiThreadCrawlerIps
     * @Desc 多线程抓取代理ip
     * @author zouxiaodong
     * @date 2018/10/30 17:44
     * @Params []
     * @return void
     */
    private static void multiThreadCrawlerIps(){
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
            for(int i=0;i<threadNum;i++){
                final int localI = i;
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        crawlerIps(ipProxyUrl_prefix+(startPage+localI));
                        countDownLatch.countDown();
                        System.out.println(currentThread().getName()+"抓取结束!");
                    }
                });
            }
            countDownLatch.await();
            executorService.shutdown();
            System.out.println("===所有线程爬取结束!===");
            System.out.println(ipInfoList);
            MultiThreadToCheckProxy();
        } catch (InterruptedException e) {
            System.out.println("多线程爬取IP代理异常.异常信息为:"+e.getMessage());
        }
    }

    /**
     * @FileName IpPoolCrawler.java
     * @ClassName IpPoolCrawler
     * @MethodName main
     * @Desc 测试代码
     * @author zouxiaodong
     * @date 2018/10/30 17:40
     * @Params [args]
     * @return void
     */
    public static void main(String[] args){
//        crawlerIps(ipProxyUrl);
        multiThreadCrawlerIps();
    }
}
