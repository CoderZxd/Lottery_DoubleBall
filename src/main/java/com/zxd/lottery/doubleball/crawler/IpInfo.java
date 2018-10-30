package com.zxd.lottery.doubleball.crawler;

/**
 * @Project Double-Ball
 * @Package com.zxd.lottery.doubleball.crawler
 * @Author：zouxiaodong
 * @Description:
 * @Date:Created in 17:03 2018/10/30.
 */
public class IpInfo {

    //国家
    private String country;

    //IP地址
    private String ip;

    //端口
    private int port;

    //服务器地址
    private String address;

    //是否高匿
    private boolean isAnonymous;

    //类型
    private String type;

    //速度
    private String speed;

    //连接时间
    private String connectTime;

    //存活时间
    private String survivalTime;

    //验证时间
    private String validateTime;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getConnectTime() {
        return connectTime;
    }

    public void setConnectTime(String connectTime) {
        this.connectTime = connectTime;
    }

    public String getSurvivalTime() {
        return survivalTime;
    }

    public void setSurvivalTime(String survivalTime) {
        this.survivalTime = survivalTime;
    }

    public String getValidateTime() {
        return validateTime;
    }

    public void setValidateTime(String validateTime) {
        this.validateTime = validateTime;
    }

    @Override
    public String toString(){
        return "{"+(this.country==null?"None":this.country)+","+this.ip+":"+this.port+"}";
    }
}
