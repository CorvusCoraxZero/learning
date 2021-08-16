package com.zero.tings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 温度计
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Thermometer extends Device {
    int temperature;  // 温度
}
