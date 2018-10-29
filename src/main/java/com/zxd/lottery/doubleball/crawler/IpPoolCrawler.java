package com.zxd.lottery.doubleball.crawler;

import org.jsoup.Jsoup;

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
            Jsoup.connect("https://www.baidu.com/").timeout(5000).proxy(ip, port).get();
            return true;
        } catch (Exception e) {
            System.err.println("代理IP不可用。异常信息为:"+e.getMessage());
            return false;
        }
    }
    
    public static void main(String[] args){
        if(checkProxy("183.32.89.96",6666)){
            System.out.println("代理IP可用!");
        }else{
            System.out.println("代理IP不可用!");
        }
    }
}
