package com.zero.dag;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * 尝试使用Dag图生成QL表达式
 */
public class TestDag {
    public static void main(String[] args) throws Exception {
        List<DagInfo> texts = Translation.jsonToDagInfoList("[\n" +
                "        {\n" +
                "            \"nextNode\": [\n" +
                "                \"avitorMap\"\n" +
                "            ],\n" +
                "            \"nodeName\": \"kafkaSource\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"nextNode\": [\n" +
                "                \"schemaMap\",\n" +
                "                \"conditionMap\"\n" +
                "            ],\n" +
                "            \"nodeName\": \"avitorMap\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"successNode\": [\n" +
                "                \"KafkaSink\"\n" +
                "            ],\n" +
                "            \"failedNode\": [\n" +
                "                \"clickhouseSink\"\n" +
                "            ],\n" +
                "            \"nodeName\": \"conditionMap\"\n" +
                "        },\n" +
                "         {\n" +
                "            \"nextNode\": [\n" +
                "                \"KafkaSink\",\n" +
                "                \"clickhouseSink\"\n" +
                "            ],\n" +
                "            \"nodeName\": \"schemaMap\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"nodeName\": \"KafkaSink\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"nodeName\": \"clickhouseSink\"\n" +
                "        }\n" +
                "    ]");

        List<NodeInfo> nodeInfos = Translation.jsonToNodeInfoList("[\n" +
                "         {\n" +
                "            \"function\": \"ConditionFunction\",\n" +
                "            \"nodeName\": \"conditionMap\",\n" +
                "            \"parallelism\": \"5\",\n" +
                "            \"properties\": {\n" +
                "                \"rule\": \"content.CurrentTemperature<10.1\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"function\": \"StringKafkaSource\",\n" +
                "            \"nodeName\": \"kafkaSource\",\n" +
                "            \"parallelism\": \"5\",\n" +
                "            \"properties\": {\n" +
                "                \"group.id\": \"1405050244499869697\",\n" +
                "                \"topic\": \"transportMessageForDeviceLifeCycle,transportMessageForService,transportMessageForEvent,transportMessageForProperty\",\n" +
                "                \"bootstrap.servers\": \"10.20.28.231:9092,10.20.28.232:9092,10.20.28.233:9092\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"function\": \"AviatorProcessFunction\",\n" +
                "            \"nodeName\": \"avitorMap\",\n" +
                "            \"parallelism\": \"5\"" +
                "        },\n" +
                "        {\n" +
                "            \"function\": \"StringKafkaSink\",\n" +
                "            \"nodeName\": \"KafkaSink\",\n" +
                "            \"parallelism\": \"5\",\n" +
                "            \"properties\": {\n" +
                "                \"topic\": \"1405001041595420673mytopic\",\n" +
                "                \"bootstrap.servers\": \"10.20.28.231:9092,10.20.28.232:9092,10.20.28.233:9092\",\n" +
                "                \"sinkType\": \"kafka\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"function\": \"SchemaProcessFunction\",\n" +
                "            \"nodeName\": \"schemaMap\",\n" +
                "            \"parallelism\": \"5\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"function\": \"ClickHouseSink\",\n" +
                "            \"nodeName\": \"clickhouseSink\",\n" +
                "            \"parallelism\": \"5\",\n" +
                "            \"properties\": {\n" +
                "                \"password\": \"123456\",\n" +
                "                \"minIdle\": 10,\n" +
                "                \"initialSize\": 10,\n" +
                "                \"jdbcUrl\": \"jdbc:clickhouse://10.20.28.231:8123/test\",\n" +
                "                \"driverName\": \"ru.yandex.clickhouse.ClickHouseDriver\",\n" +
                "                \"sinkType\": \"clickhouse\",\n" +
                "                \"tableName\": \"rule_1405050244499869697\",\n" +
                "                \"username\": \"default\",\n" +
                "                \"maxActive\": 100\n" +
                "            }\n" +
                "        }\n" +
                "    ]");

        DagNode source = Translation.dagInfoToDagNode(texts);
        Translation.addNodeInfoToDagNode(nodeInfos,source);
        generateRunner(source);
    }

    /**
     * 为测试生成DAG图
     * @return
     */
    public static DagNode prepare(){
        DagNode source = new DagNode("kafkaSource");
        DagNode schemaMap = new DagNode("schemaMap");
        DagNode avitorMap = new DagNode("avitorMap");
        DagNode kafkaSink = new DagNode("kafkaSink");
        DagNode clickhouseSink = new DagNode("clickhouseSink");
        source.addNextNode(schemaMap);
        schemaMap.addNextNode(avitorMap);
        avitorMap.addNextNode(kafkaSink);
        avitorMap.addNextNode(clickhouseSink);
        return source;
    }

    /**
     * 生成 Runner
     * @return
     */
    public static ExpressRunner generateRunner(DagNode source) throws Exception {
        // QLExpress
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String,Object> context = new DefaultContext<>();
        String exp = Translation.dagNodeToQl(source, context);
        System.out.println(exp);

        runner.addFunctionOfClassMethod("StringKafkaSource",TestDag.class.getName(),"StringKafkaSource",new String[]{"String","String","String"},null);
        runner.addFunctionOfClassMethod("AviatorProcessFunction",TestDag.class.getName(),"AviatorProcessFunction",new String[]{},null);
        runner.addFunctionOfClassMethod("SchemaProcessFunction",TestDag.class.getName(),"SchemaProcessFunction",new String[]{},null);
        runner.addFunctionOfClassMethod("StringKafkaSink",TestDag.class.getName(),"StringKafkaSink",new String[]{"String","String","String","String"},null);
        runner.addFunctionOfClassMethod("ClickHouseSink",TestDag.class.getName(),"ClickHouseSink",new String[]{"String","String","String","String","String","String","String","String","String"},null);
        runner.addFunctionOfClassMethod("ConditionFunction",TestDag.class.getName(),"ConditionFunction",new String[]{"String"},null);

        // 执行QLExpress
        Object executeResult = runner.execute(exp.toString(), context, null, true, false);

        return runner;
    }

    public String StringKafkaSource(String groupId,String topic,String servers){
        System.out.println("StringKafkaSource++ groupID:" + groupId + " topic:" + topic + " servers:" + servers );
        return "StringKafkaSource";
    }

    public boolean ConditionFunction(String rule){
        System.out.println("ConditionFunction++ rule:" + rule);
        return false;
    }

    public String AviatorProcessFunction(){
        System.out.println("AviatorProcessFunction++");
        return "AviatorProcessFunction";
    }

    public String SchemaProcessFunction(){
        System.out.println("SchemaProcessFunction++");
        return "SchemaProcessFunction";
    }

    public String StringKafkaSink(String topic,String servers,String sinkType,String data){
        System.out.println("StringKafkaSink++ topic:" + topic + " servers:" + servers + " sinkType:" + sinkType + "data:" + data);
        return "StringKafkaSink";
    }

    public String ClickHouseSink(String password, String minIdle,String initialSize,String jdbcUrl,String driverName,String tableName,String username,String maxActive,String data){
        System.out.println("ClickHouseSink++ password:" + " data:" + data + "太多啦不想写啦！！！");
        return "ClickHouseSink";
    }

    /**
     * 根据DagNode生成Json
     */
    private static String createJson() throws JsonProcessingException {
        ArrayList<String> next = new ArrayList<>();
        next.add("1");
        next.add("2");

        DagInfo dagInfo = new DagInfo();
        dagInfo.setNodeName("0");
        dagInfo.setNextNode(next);

        DagInfo dagInfo1 = new DagInfo();
        dagInfo1.setNodeName("1");
        dagInfo1.setNextNode(next);

        DagInfo dagInfo2 = new DagInfo();
        dagInfo2.setNodeName("2");
        dagInfo2.setNextNode(next);


        ObjectMapper objectMapper = new ObjectMapper();

        ArrayList<DagInfo> list = new ArrayList<>();
        list.add(dagInfo);
        list.add(dagInfo1);
        list.add(dagInfo2);

        for (DagInfo text : list) {
            System.out.println(text);
        }

        String s = objectMapper.writeValueAsString(list);
        System.out.println(s);
        return s;
    }
}
