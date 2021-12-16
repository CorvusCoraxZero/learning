package com.zero.springsecurity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class SpringsecurityApplicationTests {

    @Test
    void contextLoads() {
        Integer a = 40;
        Integer b = 0;
        Integer c = 128;
        Integer d = 128;
        Integer f = 40;
        System.out.println(a + b);
        System.out.println(a == 40);
        System.out.println(a == f);
        System.out.println(c == 128);
        System.out.println(c == d);

    }

}
