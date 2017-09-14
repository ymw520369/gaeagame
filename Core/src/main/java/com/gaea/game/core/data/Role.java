package com.gaea.game.core.data;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * author Alan
 * eamil mingweiyang@foxmail.com
 * date 2017/8/16
 */
@Document
public class Role {
    @Indexed(unique = true)
    public String userId;
    @Id
    public long roleUid;
    public String name;
    public long money;
}
