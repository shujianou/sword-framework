package com.sword.framework.plugins.mq;

/**
 * MQ消息处理器
 *
 * @author shujian.ou
 * @since 2023-01-08 22:21
 */
public interface IMsgHandler {

    /**
     * 所属标签
     */
    String getTag();

    /**
     * 处理消息
     *
     * @param msg
     */
    void handle(String msg);
}
