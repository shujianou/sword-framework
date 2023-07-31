package com.sword.framework.plugins.mq;

import cn.hutool.core.util.IdUtil;
import lombok.Getter;

import java.io.Serializable;

/**
 * Created by shujian.ou 2022-12-31 10:50
 */
@Getter
public class MqMsgBuilder implements Serializable {

    /**
     * 消息ID,保证唯一,避免重复消费
     */
    private String msgId;

    /**
     * 消息标签
     */
    private String tag;

    /**
     * 交互数据
     */
    private String data;

    /**
     * 创建人ID
     */
    private Long creatorId;

    public MqMsgBuilder() {
        this.msgId = IdUtil.fastSimpleUUID();
    }

    public static MqMsgBuilder create() {
        return new MqMsgBuilder();
    }


    public MqMsgBuilder msgId(String msgId) {
        this.msgId = msgId;
        return this;
    }

    public MqMsgBuilder tag(String tag) {
        this.tag = tag;
        return this;
    }

    public MqMsgBuilder data(String data) {
        this.data = data;
        return this;
    }

    public MqMsgBuilder creatorId(Long creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    public MqMsg build() {
        return MqMsgBuilder.builder(this);
    }

    public static MqMsg builder(MqMsgBuilder builder) {
        return new MqMsg(builder.getMsgId(), builder.getTag(), builder.getData(), builder.getCreatorId());
    }
}
