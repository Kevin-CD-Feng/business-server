package com.xtxk.cn.third.service.impl.hk;

import cn.hutool.json.JSONUtil;
import com.lmax.disruptor.RingBuffer;
import com.xtxk.cn.third.dto.hk.EventMessage;
import lombok.extern.slf4j.Slf4j;

/***
 * @description TODO
 * @author liulei
 * @date 2023-09-04 10:35
 */
@Slf4j
public class MessageProducer {

    private final RingBuffer<EventMessage> ringBuffer;

    public MessageProducer(RingBuffer<EventMessage> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void publish(EventMessage message) {
        // ringBuffer是个队列，其next方法返回的是下最后一条记录之后的位置，这是个可用位置
        long next = ringBuffer.next();
        try {
            EventMessage event = ringBuffer.get(next);
            event.setTime(message.getTime());
            event.setResourceId(message.getResourceId());
            event.setImgUrl(message.getImgUrl());
        } catch (Exception e) {
            log.error("向RingBuffer队列存入数据[{}]出现异常=>{}", JSONUtil.toJsonStr(message), e.getStackTrace());
        } finally {
            ringBuffer.publish(next);
        }
    }
}
