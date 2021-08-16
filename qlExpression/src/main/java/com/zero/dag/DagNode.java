package com.zero.dag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DagNode {
    private String nodeName;
    private List<DagNode> nextNode;

    public void addNextNode(DagNode dagNode){
        this.nextNode.add(dagNode);
    }

    public DagNode(String nodeName) {
        this.nodeName = nodeName;
        this.nextNode = new ArrayList<>();
    }

    public void speak(){
        System.out.println(nodeName + " 被执行了");
    }
}
