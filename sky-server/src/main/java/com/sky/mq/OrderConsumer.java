package com.sky.mq;

import apache.rocketmq.v2.Message;
import com.sky.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
@RocketMQMessageListener(
        topic = "order_topic",
        consumerGroup = "order_consumer_Group"
)
public class OrderConsumer implements RocketMQListener<String> {

    @Resource
    private WebSocketServer webSocketServer;

    @Override
    public void onMessage(String message) {
        try {
            log.info("MQ收到订单通知：{}",message);
            //真正推给商家
            webSocketServer.sendToAllClient(message);
            log.info("MQ 已通过 WebSocket 推送给商家");
        } catch (Exception e) {
            log.error("MQ消费失败", e);
        }
    }
}
