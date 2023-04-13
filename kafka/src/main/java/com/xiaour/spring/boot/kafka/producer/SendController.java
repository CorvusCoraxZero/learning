package com.xiaour.spring.boot.kafka.producer;

import com.sun.xml.internal.ws.client.sei.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Xiaour
 * @Description:
 * @Date: 2018/5/22 15:13
 */
@RestController
@RequestMapping("/kafka")
public class SendController {

    @Autowired
    private Producer producer;

    @PostMapping("/send")
    public String send(@RequestBody String message) {
        producer.send(message);
        return "发送成功";
    }

    @PostMapping("/etl/send/{topic}")
    public String sendEtl(@RequestBody String message, @PathVariable String topic) {
        if (topic == null || topic.isEmpty()) {
            producer.sendOriginal(message);
        } else {
            producer.sendOriginal(topic, message);
        }
        return "发送成功";
    }

    @GetMapping("/testCookie")
    public String testCookie(HttpServletRequest request, HttpServletResponse response) {
        if (request.getCookies() != null && request.getCookies().length >= 1){
            for (Cookie cookie : request.getCookies()) {
                System.out.println(cookie.getName());
            }
        }

        ResponseCookie build = ResponseCookie.from("TestNew","123456").secure(true).httpOnly(true).sameSite("Lax").build();
        ResponseCookie.from("TestNew","123456");

        if (request.getCookies() != null && request.getCookies().length >= 1){
            for (Cookie cookie : request.getCookies()) {
                System.out.println(cookie.getName());
            }
        }

        response.addCookie(new Cookie("newCookie","new-new"));
        response.addCookie(new Cookie("newCookie2","new-new"));

        // 为Cookie添加安全属性
        if (response.containsHeader(HttpHeaders.SET_COOKIE)){
            List<String> cookies = response.getHeaders(HttpHeaders.SET_COOKIE).stream().collect(Collectors.toList());
            // 清空数据
            response.setHeader(HttpHeaders.SET_COOKIE,"");
            System.out.println("尝试从响应头获取");
            for (int i = 0; i < cookies.size(); i++) {
                String cookieStr = cookies.get(i);
                cookieStr += "; Secure; HttpOnly; SameSite=Lax";
                if (i == 0){
                    // 清空原有的cookie
                    response.setHeader(HttpHeaders.SET_COOKIE,cookieStr);
                }else {
                    response.addHeader(HttpHeaders.SET_COOKIE,cookieStr);
                }
            }
        }

        return "发送成功";
    }
}
