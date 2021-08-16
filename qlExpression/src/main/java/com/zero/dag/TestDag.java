package com.zero.dag;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * 尝试使用Dag图生成QL表达式
 */
public class TestDag {
    public static void main(String[] args) throws Exception {

        List<DagText> texts = createDagTextList("[\n" +
                "        {\n" +
                "            \"nextNode\": [\n" +
                "                \"avitorMap\"\n" +
                "            ],\n" +
                "            \"nodeName\": \"kafkaSource\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"nextNode\": [\n" +
                "                \"schemaMap\"\n" +
                "            ],\n" +
                "            \"nodeName\": \"avitorMap\"\n" +
                "        },\n" +
                "        {\n" +
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
        for (DagText text : texts) {
            System.out.println(text);
        }

//        DagNode source = prepare();
        DagNode source = createDag(texts);
        generateRunner(source);

//        createJson();
    }

    /**
     * 为测试生成DAG图
     * @return
     */
    public static DagNode prepare(){
        DagNode source = new DagNode("kafkaSource", new ArrayList<DagNode>());
        DagNode schemaMap = new DagNode("schemaMap", new ArrayList<DagNode>());
        DagNode avitorMap = new DagNode("avitorMap", new ArrayList<DagNode>());
        DagNode kafkaSink = new DagNode("kafkaSink", null);
        DagNode clickhouseSink = new DagNode("clickhouseSink", null);
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
        StringBuilder exp = new StringBuilder();

        generateExpress(source, context, exp);
        System.out.println(exp);

//        context.put("kafkaSource",source);
//        exp.append("kafkaSource.speak(); {kafkaSource.speak(); kafkaSource.speak();}");

        // 执行QLExpress
        Object executeResult = runner.execute(exp.toString(), context, null, true, false);

        return runner;
    }

    /**
     * 生成 QLExpress
     * @return
     */
    public static void generateExpress(DagNode dagNode, DefaultContext<String,Object> context,StringBuilder exp) throws Exception {
        if (dagNode == null) return;
        context.put(dagNode.getNodeName(),dagNode);
        exp.append(dagNode.getNodeName() + ".speak();"); // 节点要执行的方法
        if (dagNode.getNextNode() != null){
            exp.append(" {");
            for (DagNode nextNode : dagNode.getNextNode()) {
                generateExpress(nextNode,context,exp);
                exp.append(" ");
            }
            exp.append(" }");
        }else return;
    }

    /**
     *  解析json生成Dag图
     */
    public static List<DagText> createDagTextList(String jsonStr) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<DagText> dagNodes = objectMapper.readValue(jsonStr, new TypeReference<List<DagText>>() {});
        return dagNodes;
    }

    public static DagNode createDag(List<DagText> list){
        HashMap<String, DagNode> map = new HashMap<>();
        // 创建所有的DagNode 但是不指定nextNode
        for (DagText dagText : list) {
            map.put(dagText.getNodeName(),new DagNode(dagText.getNodeName()));
        }
        // 给所有的DagNode 指定nextNode
        for (DagText dagText : list) {
            DagNode node = map.get(dagText.getNodeName());
            if (dagText.getNextNode() != null){
                for (String name : dagText.getNextNode()) {
                    node.getNextNode().add(map.get(name));
                }
            }
        }
        // 返回sourceNode
        return map.get(list.get(0).getNodeName());
    }

    /**
     * 根据DagNode生成Json
     */
    private static String createJson() throws JsonProcessingException {
        ArrayList<String> next = new ArrayList<>();
        next.add("1");
        next.add("2");

        DagText dagText = new DagText();
        dagText.setNodeName("0");
        dagText.setNextNode(next);

        DagText dagText1 = new DagText();
        dagText1.setNodeName("1");
        dagText1.setNextNode(next);

        DagText dagText2 = new DagText();
        dagText2.setNodeName("2");
        dagText2.setNextNode(next);


        ObjectMapper objectMapper = new ObjectMapper();

        ArrayList<DagText> list = new ArrayList<>();
        list.add(dagText);
        list.add(dagText1);
        list.add(dagText2);

        for (DagText text : list) {
            System.out.println(text);
        }

        String s = objectMapper.writeValueAsString(list);
        System.out.println(s);
        return s;
    }
}
