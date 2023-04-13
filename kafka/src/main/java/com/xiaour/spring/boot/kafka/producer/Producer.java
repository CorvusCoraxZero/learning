package com.xiaour.spring.boot.kafka.producer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Xiaour
 * @Description:
 * @Date: 2018/5/22 15:07
 */
@Component
public class Producer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    private static Gson gson = new GsonBuilder().create();

    //发送消息方法
    public void send() {
        Message message = new Message();
        message.setId("KFK_"+System.currentTimeMillis());
        message.setMsg(UUID.randomUUID().toString());
        message.setSendTime(new Date());
        kafkaTemplate.send("test", gson.toJson(message));
    }

    public void send(String msgContent) {
        Message message = new Message();
        message.setId("KFK_"+System.currentTimeMillis());
        message.setMsg(msgContent);
        message.setSendTime(new Date());
        kafkaTemplate.send("test", gson.toJson(message));
    }

    public void sendOriginal(String msgContent) {
        kafkaTemplate.send("test", msgContent);
    }

    public void sendOriginal(String topic,String msgContent) {
        kafkaTemplate.send(topic, msgContent);
    }

    public static void main(String[] args) {
        // Pattern pattern = Pattern.compile("(?<bracket1>[',\"]?)\\$\\{(?<name>.*?)\\}(?<bracket2>[',\"]?)");
        // Matcher matcher = pattern.matcher("INSERT INTO \"tb_asset_port\"(\"id\",\"classify\",\"ipaddr\",\"identifier\",\"name\",\"asset_type\",\"risk_level\",\"active\",\"internet_exposed\",\"internet_protocol\",\"location\",\"source\",\"important\",\"state\",\"create_time\",\"update_time\",\"lock_fields\",\"port\",\"service\",\"protocol\",\"application\",\"version\",\"service_type\") VALUES(${id},${classify},'${ipaddr}','${identifier}','${name}',${assetType},${riskLevel},${active},${internetExposed},'${internetProtocol}','${location}','${source}',${important},${state},'${createTime}','${updateTime}','${lockFields}','${port}','${service}','${protocol}','${application}','${version}','${serviceType}');");
        // while (matcher.find()){
        //     if (matcher.group("bracket1") != null && !matcher.group("bracket1").equals("")){
        //         System.out.println(matcher.group("name"));
        //     }else {
        //         System.out.println("null");
        //     }
        // }
        System.out.println("UPDATE \"tb_asset_port\" SET \"classify\" = 0,\"ipaddr\" = '10.50.6.115',\"identifier\" = null,\"name\" = null,\"asset_type\" = null,\"risk_level\" = null,\"active\" = null,\"internet_exposed\" = null,\"internet_protocol\" = null,\"location\" = null,\"source\" = null,\"important\" = 1,\"state\" = null,\"update_time\" = '2023-01-06T14:00:41.606Z',\"lock_fields\" = null,\"port\" = 443,\"service\" = '',\"protocol\" = 'tcp',\"application\" = '',\"version\" = '',\"service_type\" = '' WHERE \"id\" = 1611324887345070080;");
    }

}
