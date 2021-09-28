package com.zero.corn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CornInfo {
    private String triggerMode; // 触发方式 fixedTime,cycleTime
    private int interval;   // 循环周期
    private String cycle; // min,hour,day,week,month
    private String effectiveTime;  // 触发时间
    private String dayOfWeek;
    private String dayOfMonth;
    private String listenType;
    private String apiDomain;
}
