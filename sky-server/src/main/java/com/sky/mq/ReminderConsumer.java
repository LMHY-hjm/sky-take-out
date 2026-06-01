package com.sky.mq;

import com.sky.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
@RocketMQMessageListener(
        topic = "reminder_topic",
        consumerGroup = "reminder_consumer_group"
)
public class ReminderConsumer implements RocketMQListener<String> {

    @Resource
    private WebSocketServer webSocketServer;

    @Override
    public void onMessage(String message) {
        log.info("MQ 收到催单通知：{}", message);
        webSocketServer.sendToAllClient(message);
        log.info("催单已推送给商家");
    }
}