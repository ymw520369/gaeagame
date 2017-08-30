/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package com.gaea.game.logic.sample;

import com.dyuproject.protostuff.Tag;

import java.io.Serializable;

/**
 * 样本类
 * 
 * Created on 2017/3/14.
 * 
 * @author Alan
 * @since 1.0
 */
public abstract class Sample implements Cloneable , Serializable{

    /**
     * 样本id
     */
    @Tag(1)
    public int sid;

    /**
     * 样本名称
     */
    @Tag(2)
    public String name;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 从配置文件读取属性
     * @param attributeName 属性名称
     * @param attributeValue 属性值
     * @return 返回是否成功
     */
    public boolean setAttribute(String attributeName, String attributeValue) {
        if ("sid".equals(attributeName)) {
            sid = Integer.parseInt(attributeValue);
            return true;
        } else if ("name".equals(attributeName)) {
            name = attributeValue;
            return true;
        }
        return false;
    }

    @Override
    public Sample clone() {
        try {
            return (Sample) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(
                    getClass().getName() + " clone, sid=" + sid, e);
        }
    }
}
