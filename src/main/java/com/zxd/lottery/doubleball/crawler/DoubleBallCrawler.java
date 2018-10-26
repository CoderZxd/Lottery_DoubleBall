package com.zxd.lottery.doubleball.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.sort;

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

    //TODO:修改多线程
    public static void main(String[] args) throws Exception{
        Document doc = Jsoup.connect(url).get();
        Elements aElements =doc.select(".iSelectList > a");
        if(null != aElements){
            System.out.println("一共期数为:"+aElements.size());
            List<ResultDto> resultDtoList = new ArrayList<ResultDto>(2500);
            for(Element a:aElements){
                try {
                    ResultDto resultDto = new ResultDto();
                    resultDto.setNumber(a.text());
                    resultDto.setHref(a.attr("href"));
                    doc = Jsoup.connect(a.attr("href")).get();
                    Elements balls =doc.select("div.ball_box01 li");
                    for(int i=0;i<balls.size();i++){
                        Element ball = balls.get(i);
                        switch (i){
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
                    System.out.println(resultDto.toString());
                    resultDtoList.add(resultDto);
                }catch (Exception e){
                    System.err.println("爬取双色球第"+a.text()+"期开奖结果异常(开奖结果详情页:"+a.attr("href")
                            + ").异常信息:"+e.getMessage());
                }
            }
            System.out.println(resultDtoList);
            Collections.sort(resultDtoList);
            System.out.println(resultDtoList);
        }
    }
}
