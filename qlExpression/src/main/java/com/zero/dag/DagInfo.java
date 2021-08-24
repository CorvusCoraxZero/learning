package com.zero.dag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DagInfo {
    private String nodeName;
    private ArrayList<String> nextNode;
    private ArrayList<String> successNode;
    private ArrayList<String> failedNode;

    public DagInfo(String nodeName, ArrayList<String> nextNode) {
        this.nodeName = nodeName;
        this.nextNode = nextNode;
    }

    public DagInfo(String nodeName, ArrayList<String> successNode, ArrayList<String> failedNode) {
        this.nodeName = nodeName;
        this.successNode = successNode;
        this.failedNode = failedNode;
    }

}
