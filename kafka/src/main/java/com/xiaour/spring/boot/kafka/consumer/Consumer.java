package com.xiaour.spring.boot.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Author: Xiaour
 * @Description:
 * @Date: 2018/5/22 15:03
 */
@Component
public class Consumer {

    @KafkaListener(topics = {"taskResultProcessTime"})
    public void listen(ConsumerRecord<?, ?> record){
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            // System.out.println("---->"+record);
            System.out.println("任务执行时间区间消息");
            System.out.println("---->"+message);

        }

    }


    @KafkaListener(topics = {"transportMessageForServiceWebScanTaskResult"})
    public void listen2(ConsumerRecord<?, ?> record){

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());

        if (kafkaMessage.isPresent()) {

            Object message = kafkaMessage.get();
            System.out.println("已接收到消息: "+record);
            System.out.println("---->"+message);

        }

    }

    @KafkaListener(topics = {"taskRequestMsg"})
    public void listen3(ConsumerRecord<?, ?> record){
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            System.out.println("收到任务处理消息: "+record);
            System.out.println("---->"+message);

        }
    }
}
