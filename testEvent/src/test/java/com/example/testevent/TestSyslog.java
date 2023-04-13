package com.example.testevent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import net.bytebuddy.implementation.bytecode.assign.TypeCasting;
import org.graylog2.syslog4j.Syslog;
import org.graylog2.syslog4j.SyslogConfigIF;
import org.graylog2.syslog4j.SyslogConstants;
import org.graylog2.syslog4j.SyslogIF;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestSyslog {

    private static SyslogIF syslog;
    private static ThreadPoolExecutor snedSyslogThreadPool = new ThreadPoolExecutor(1, 8, 10, TimeUnit.SECONDS,
            new ArrayBlockingQueue(100), (new ThreadFactoryBuilder()).setNameFormat("Thread-pool-sendSyslog").build(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) throws InterruptedException {
        String message = "Test Syslog";
        TestSyslog test = new TestSyslog();
        syslog = Syslog.getInstance(SyslogConstants.UDP);
        test.configSyslogSender("192.168.29.2", 514);
        for (int i = 0; i < 10; i++) {
            snedSyslogThreadPool.execute(test.doSendSyslog(message + i));
            if (i == 4) {
                Thread.sleep(2000);
                test.configSyslogSender("192.168.29.149", 514);
            }
        }
    }

    private Runnable doSendSyslog(String message) {
        return () -> {
            try {
                syslog.log(0, URLDecoder.decode(message, "utf-8"));
                syslog.log(0, message + "----");
            } catch (Exception e) {
                System.out.println("发送失败");
            }
        };
    }

    public static void configSyslogSender(String host, Integer port) {
        SyslogConfigIF config = syslog.getConfig();
        Syslog.shutdown();
        config.setHost(host);
        config.setPort(port);
        syslog = Syslog.createInstance(SyslogConstants.UDP, config);
        /*if (host.equals("192.168.29.149")){
            syslog = null;
        }*/

    }

    @Test
    void testComparing() {
        ArrayList<Integer> list = new ArrayList();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            list.add(random.nextInt(100));
        }
        list.stream().sorted(Comparator.comparing(a -> a, (a, b) -> b - a)).forEach(System.out::println);
    }
    @Test

    void testToMap() {
        ArrayList<Integer> list = new ArrayList();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            list.add(random.nextInt(100));
        }
        list.stream().sorted(Comparator.comparing(a -> a, (a, b) -> b - a)).collect(Collectors.toMap(a -> a,a -> a*2,(oldVal,newVal) -> newVal));
        for (Iterator<Integer> it = list.stream().iterator(); it.hasNext(); ) {
            Integer integer = it.next();
        }
        // Stream<String> stream = Files.lines();

    }


}