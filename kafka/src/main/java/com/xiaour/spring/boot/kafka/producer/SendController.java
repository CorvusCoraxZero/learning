package com.xiaour.spring.boot.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Xiaour
 * @Description:
 * @Date: 2018/5/22 15:13
 */
@RestController
@RequestMapping("/kafka")
public class SendController {

    @Autowired
    private Producer producer;

    @PostMapping("/send")
    public String send(@RequestBody String message) {
        producer.send(message);
        return "发送成功";
    }

    @PostMapping("/etl/send/{topic}")
    public String sendEtl(@RequestBody String message, @PathVariable String topic) {
        if (topic == null || topic.isEmpty()) {
            producer.sendOriginal(message);
        } else {
            producer.sendOriginal(topic, message);
        }
        return "发送成功";
    }
}
