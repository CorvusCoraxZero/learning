package com.zero.tings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 空调设备
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirConditioner extends Device{
    boolean status;  // 开关状态
    int expectation; //预期的温度
    int windLevel; //风力等级

    public void setStatus(boolean status) {
        this.status = status;
        if (this.status){
            System.out.println("空调开启了  " + "设定温度： " + expectation+ "摄氏度");
        }else {
            System.out.println("空调关闭了");
        }
    }

    public void setExpectation(int expectation) {
        this.expectation = expectation;
        System.out.println("更新设定温度为" + expectation + " 摄氏度");
    }

    public void setWindLevel(int windLevel) {
        this.windLevel = windLevel;
        System.out.println("更新风力等级为" + windLevel + " 档");
    }
}
