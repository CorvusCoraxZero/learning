package com.example.testevent;

import com.google.common.io.Files;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// @SpringBootTestz
class TestCharSink {

    @Test
    void contextLoads() throws Exception{
        ArrayList<String> list = new ArrayList<>();
        list.add("IPV6PREFIX=9999");
        Files.asCharSink(new File("/Users/guojiaxin/Desktop/aa"), Charset.defaultCharset()).writeLines(list);
    }

}
