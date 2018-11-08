package com.zxd.lottery.doubleball.crawler.dao.model;

import java.util.Date;

/**
 * @author CoderZZ
 * @Title: ${FILE_NAME}
 * @Project: DoubleBall
 * @Package com.zxd.lottery.doubleball.crawler
 * @description: TODO:一句话描述信息
 * @Version 1.0
 * @create 2018-10-26 1:04
 **/
public class ResultDto implements Comparable<ResultDto>{

    //期数
    private String number;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    //开奖结果html页面地址url
    private String href;
    private String red1;
    private String red2;
    private String red3;
    private String red4;
    private String red5;
    private String red6;
    private String blue;

    //开奖日期
    private Date opendate;

    //兑奖截止日期
    private Date deadlinedate;

    //预留扩展字段
    private String extend;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRed1() {
        return red1;
    }

    public void setRed1(String red1) {
        this.red1 = red1;
    }

    public String getRed2() {
        return red2;
    }

    public void setRed2(String red2) {
        this.red2 = red2;
    }

    public String getRed3() {
        return red3;
    }

    public void setRed3(String red3) {
        this.red3 = red3;
    }

    public String getRed4() {
        return red4;
    }

    public void setRed4(String red4) {
        this.red4 = red4;
    }

    public String getRed5() {
        return red5;
    }

    public void setRed5(String red5) {
        this.red5 = red5;
    }

    public String getRed6() {
        return red6;
    }

    public void setRed6(String red6) {
        this.red6 = red6;
    }

    public String getBlue() {
        return blue;
    }

    public void setBlue(String blue) {
        this.blue = blue;
    }

    public Date getOpendate() {
        return opendate;
    }

    public void setOpendate(Date opendate) {
        this.opendate = opendate;
    }

    public Date getDeadlinedate() {
        return deadlinedate;
    }

    public void setDeadlinedate(Date deadlinedate) {
        this.deadlinedate = deadlinedate;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    @Override
    public String toString(){
        return "双色球 第"+this.getNumber()+"期,详情地址:"+this.getHref()+",开奖号码:"+this.getRed1()+" "+this.getRed2()+
                " "+this.getRed3()+" "+this.getRed4()+" "+this.getRed5()+" "+this.getRed6()+" | "+this.getBlue();
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p>
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     * <p>
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     * <p>
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     * <p>
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     * <p>
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(ResultDto o) {
        return o.getNumber().compareTo(this.getNumber());
    }

    @Override
    public int hashCode(){
        return Integer.parseInt(this.getNumber());
    }

    @Override
    public boolean equals(Object resultDto){
        return this.getNumber().equals(((ResultDto)resultDto).getNumber());
    }
}
