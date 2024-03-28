package com.xtxk.cn.third.service.impl.hk;

import com.lmax.disruptor.EventFactory;
import com.xtxk.cn.third.dto.hk.EventMessage;
import org.springframework.stereotype.Service;

/***
 * @description TODO
 * @author liulei
 * @date 2023-09-04 10:07
 */
@Service
public class MessageFactory implements EventFactory<EventMessage> {

    @Override
    public EventMessage newInstance() {
        return new EventMessage();
    }
}
