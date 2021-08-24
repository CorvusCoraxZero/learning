package com.zero.dag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DagNode {
    private String nodeName;

    private boolean isReturnable;       // 会返回数值吗？
    private boolean isIfNode;           // 是if分支节点吗？

    private String condition;           // 如果是if节点的判断条件
    private String function = "speak";            // 要执行的方法
    private int parallelism;
    private Map<String,String> properties;    // 属性参数

    private List<DagNode> nextNode;     // 下一个节点，或者 condition判断为 真 的下一个节点
    private List<DagNode> failedNode;   // condition 判断为false的下一个节点。

    public DagNode(String nodeName) {
        this.nodeName = nodeName;
        this.nextNode = new ArrayList<>();
        this.function = getNodeName() + ".speak();";
    }

    public void addNextNode(DagNode dagNode) {
        if (this.getNextNode() == null) {
            this.nextNode = new ArrayList<DagNode>();
        }
        this.nextNode.add(dagNode);
    }

    public void addFailNode(DagNode dagNode) {
        if (this.getFailedNode() == null) {
            this.failedNode = new ArrayList<DagNode>();
        }
        this.failedNode.add(dagNode);
    }

    public String speak() {
        System.out.println(nodeName + " 被执行了");
        return "给你传参的是 " + this.getNodeName();
    }

    public String speak(String data) {
        System.out.println(nodeName + " 获取了data " + data);
        return "给你传参的是 " + this.getNodeName();
    }
}
