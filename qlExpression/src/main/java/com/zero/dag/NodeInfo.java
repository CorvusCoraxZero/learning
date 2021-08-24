package com.zero.dag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeInfo {
    private String nodeName;
    private String function;
    private int parallelism;
    private Map<String,String> properties;
//    private Map<String,String> ruleRegex;
}
