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
public class DagText {
    private String nodeName;
    private ArrayList<String> nextNode;
}
