package com.example.mybatisplustest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryOption {
    private Integer num;
    private String value;

    private Map params;

    public QueryOption(Integer num, String value) {
        this.num = num;
        this.value = value;
    }
}
