package com.xtxk.cn.third.service.impl.hk;


import cn.hutool.core.util.ArrayUtil;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.xtxk.cn.third.dto.hk.EventMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.List;

/***
 * @description TODO
 * @author liulei
 * @date 2023-09-04 10:13
 */
@Service
public class MessageManager  {

    protected Disruptor<EventMessage> disruptor;

    private MessageProducer producer;

    @Autowired
    private ConsumerMessageImpl consumerMessage;


    @PostConstruct
    private void init(){
        // 实例化，MessageHandler- 为线程名
        disruptor = new Disruptor<>(new MessageFactory(), 1024 * 256,
                new CustomizableThreadFactory("MessageHandler-"), ProducerType.SINGLE, new BlockingWaitStrategy());

        // 设置事件业务处理器---消费者
        disruptor.handleEventsWith(consumerMessage);
        // 启动
        disruptor.start();
        // 实例化生产者
        producer = new MessageProducer(disruptor.getRingBuffer());
    }

    /**
     * 发布事件
     *
     * @param value
     * @return
     */
    public void publish(EventMessage value) {
        producer.publish(value);
    }

    /***
     * 批量发生消息
     * @param list
     */
    public boolean batchPublish(List<EventMessage> list) {
        if (ArrayUtil.isEmpty(list)) {
            return false;
        }
        for (EventMessage message : list) {
            producer.publish(message);
        }
        return true;
    }

    public long getCursor() {
        return disruptor.getCursor();
    }


}
