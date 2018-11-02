package com.zxd.lottery.doubleball.crawler;

/**
 * @author CoderZZ
 * @Title: ${FILE_NAME}
 * @Project: DoubleBall
 * @Package com.zxd.lottery.doubleball.crawler
 * @description: 双色球球的类型
 * @Version 1.0
 * @create 2018-11-02 23:40
 **/
public enum BallType {

    RED1("red1"),RED2("red2"),RED3("red3"),RED4("red4"),RED5("red5"),RED6("red6"),BLUE("blue");

    private String name;

    BallType(String name) {
        this.name = name;
    }
}
