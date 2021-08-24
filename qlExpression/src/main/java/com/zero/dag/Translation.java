package com.zero.dag;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ql.util.express.DefaultContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Translation {

    /**
     * 根据DAG图 生成 QLExpress
     * @return
     */
    public static String dagNodeToQl(DagNode source, DefaultContext<String,Object> context) throws Exception {
        StringBuilder exp = new StringBuilder();
        dagNodeToQlRc(source,context,exp);
        return exp.toString();
    }

    /**
     * 根据DAG图 生成 QLExpress的递归函数
     * @return
     */
    private static void dagNodeToQlRc(DagNode dagNode, DefaultContext<String,Object> context, StringBuilder exp) throws Exception {
        if (dagNode == null) return;
        context.put(dagNode.getNodeName(),dagNode);
        exp.append(" \n{");
        if (dagNode.isReturnable()) {  // 如果该节点需要返回参数
            exp.append("String data = " );
        }
        exp.append(dagNode.getFunction()); // 要执行的方法
        if (dagNode.getNextNode() != null && dagNode.getNextNode().size() > 0 && !dagNode.isIfNode()){

            for (DagNode nextNode : dagNode.getNextNode()) {
                dagNodeToQlRc(nextNode,context,exp);
                exp.append(" ");
            }
        }else if ((dagNode.getNextNode() != null || dagNode.getFailedNode() != null) && dagNode.isIfNode()) {  // 判断是否是分支节点
            exp.append("\nif "+ dagNode.getCondition() +" then ");
            if (dagNode.getNextNode() != null && dagNode.getNextNode().size() > 0){
                for (DagNode nextNode : dagNode.getNextNode()) {
                    dagNodeToQlRc(nextNode,context,exp);
                    exp.append(" ");
                }
            }
            if (dagNode.getFailedNode() != null && dagNode.getFailedNode().size() > 0){
                exp.append("\n else ");
                for (DagNode nextNode : dagNode.getFailedNode()) {
                    dagNodeToQlRc(nextNode,context,exp);
                    exp.append(" ");
                }
            }
        }
        exp.append(" }");
    }
    /**
     *  解析json生成DagInfoList
     */
    public static List<DagInfo> jsonToDagInfoList(String jsonStr) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<DagInfo> dagInfos = objectMapper.readValue(jsonStr, new TypeReference<List<DagInfo>>() {});
        return dagInfos;
    }

    /**
     *  解析json生成DagInfoList
     */
    public static List<NodeInfo> jsonToNodeInfoList(String jsonStr) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<NodeInfo> nodeInfo = objectMapper.readValue(jsonStr, new TypeReference<List<NodeInfo>>() {});
        return nodeInfo;
    }

    /**
     * 通过dagInfo生成DagNode
     * @param list
     * @return
     */
    public static DagNode dagInfoToDagNode(List<DagInfo> list){
        HashMap<String, DagNode> map = new HashMap<>();
        // 创建所有的DagNode 但是不指定nextNode
        for (DagInfo dagInfo : list) {
            map.put(dagInfo.getNodeName(),new DagNode(dagInfo.getNodeName()));
        }
        // 给所有的DagNode 指定nextNode
        for (DagInfo dagInfo : list) {
            DagNode node = map.get(dagInfo.getNodeName());
            if (dagInfo.getNextNode() != null && dagInfo.getSuccessNode() == null && dagInfo.getFailedNode() == null){
                for (String name : dagInfo.getNextNode()) { //name 是节点的名字
                    node.addNextNode(map.get(name));
                }
            }
            if (dagInfo.getFailedNode() != null){
                node.setIfNode(true);  // 设置这是一个分支节点
                for (String name : dagInfo.getFailedNode()) {
                    node.addFailNode(map.get(name));
                }

            }
            if (dagInfo.getSuccessNode() != null){
                node.setIfNode(true);  // 设置这是一个分支节点
                for (String name : dagInfo.getSuccessNode()) {
                    node.getNextNode().add(map.get(name));
                }
            }
        }
        // 返回sourceNode
        return map.get(list.get(0).getNodeName());
    }

    /**
     * 递归实现给DagNode添加节点信息
     * @param list
     * @return
     */
    public static void addNodeInfoToDagNode(List<NodeInfo> list,DagNode source){
        Map<String, NodeInfo> map = list.stream().collect(Collectors.toMap(NodeInfo::getNodeName, nodeInfo -> nodeInfo));
        addNodeInfoToDagNodeRc(map, source);
        return;
    }

    private static void addNodeInfoToDagNodeRc(Map<String,NodeInfo> map, DagNode node){
        String name = node.getNodeName();
        NodeInfo nodeInfo = map.get(name);
        if (nodeInfo != null){
            node.setParallelism(nodeInfo.getParallelism());
            node.setProperties(nodeInfo.getProperties());
            setParam(name,node,nodeInfo);
        }
        if (node.getNextNode() != null || node.getNextNode().size() > 0){
            for (DagNode dagNode : node.getNextNode()) {
                addNodeInfoToDagNodeRc(map,dagNode);
            }
        }
        if (node.getFailedNode() != null && node.getFailedNode().size() > 0){
            for (DagNode dagNode : node.getNextNode()) {
                addNodeInfoToDagNodeRc(map,dagNode);
            }
        }
        return;
    }

    /**
     * 根据Nodename判断方法需要的参数 顺带完善node和方法有关的参数
     * @param name
     * @return
     */
    public static void setParam(String name, DagNode node, NodeInfo nodeInfo) {
        switch (name) {
            case "conditionMap":
                node.setIfNode(true);
                node.setCondition(nodeInfo.getFunction()+"(conditionMap.getProperties().get(\"rule\"))");
//                node.setCondition("false");
                return;
            case "kafkaSource":
                node.setReturnable(true);
                node.setFunction(nodeInfo.getFunction()+"(kafkaSource.getProperties().get(\"group.id\"),kafkaSource.getProperties().get(\"topic\"),kafkaSource.getProperties().get(\"bootstrap.servers\"));");
                return;
            case "KafkaSink":
                node.setFunction(nodeInfo.getFunction()+"(KafkaSink.getProperties().get(\"topic\"),KafkaSink.getProperties().get(\"bootstrap.servers\"),KafkaSink.getProperties().get(\"sinkType\"),data);");
                return;
            case "clickhouseSink":
                node.setFunction(nodeInfo.getFunction()+"(clickhouseSink.getProperties().get(\"password\"),clickhouseSink.getProperties().get(\"minIdle\"),clickhouseSink.getProperties().get(\"initialSize\"),clickhouseSink.getProperties().get(\"jdbcUrl\"),clickhouseSink.getProperties().get(\"driverName\"),clickhouseSink.getProperties().get(\"tableName\"),clickhouseSink.getProperties().get(\"username\"),clickhouseSink.getProperties().get(\"maxActive\"),data);");
                return;
            default:node.setFunction(nodeInfo.getFunction()+"();");return;
        }
    }
}
