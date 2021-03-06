package com.daoming.mq;

import com.daoming.utils.LogUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = RabbitConfig.QUEUE_NAME)
public class HelloReceiver {
    @RabbitHandler
    public void process(String hello) {
        LogUtils.d("Receiver1  : " + hello);
    }
}
