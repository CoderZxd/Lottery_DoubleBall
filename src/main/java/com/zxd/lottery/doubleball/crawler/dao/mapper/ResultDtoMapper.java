package com.zxd.lottery.doubleball.crawler.dao.mapper;

import com.zxd.lottery.doubleball.crawler.dao.model.ResultDto;

public interface ResultDtoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table result
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String number);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table result
     *
     * @mbggenerated
     */
    int insert(ResultDto record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table result
     *
     * @mbggenerated
     */
    int insertSelective(ResultDto record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table result
     *
     * @mbggenerated
     */
    ResultDto selectByPrimaryKey(String number);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table result
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ResultDto record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table result
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ResultDto record);
}