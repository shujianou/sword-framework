package com.sword.framework.plugins.mq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by shujian.ou 2022-12-31 10:50
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MqMsg implements Serializable {

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

}
