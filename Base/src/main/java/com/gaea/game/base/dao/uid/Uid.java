/**
 * Copyright Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 * <p>
 * 2017年2月27日
 */
package com.gaea.game.base.dao.uid;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 *
 * @author Alan
 *
 * @since 1.0
 *
 */
@Document
public class Uid {
    @Id
    private UidTypeEnum name;

    private Long lastId;

    public Uid(UidTypeEnum name, Long lastId) {
        super();
        this.name = name;
        this.lastId = lastId;
    }

    public UidTypeEnum getName() {
        return name;
    }

    public void setName(UidTypeEnum name) {
        this.name = name;
    }

    public Long getLastId() {
        return lastId;
    }

    public void setLastId(Long lastId) {
        this.lastId = lastId;
    }

}
