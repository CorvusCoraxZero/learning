package com.zero;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.zero.tings.AirConditioner;
import com.zero.tings.Thermometer;
import lombok.Data;

/**
 * Hello world!
 *
 */
@Data
public class App 
{
    public static void main( String[] args ) throws Exception {
        System.out.println( "----- testScene1 ------" );
        testScene1();
        System.out.println( "----- testScene2 ------" );
        testScene2();
        System.out.println( "----- testScene3 ------" );
        testScene3();
    }

    /**
     * 两个房间的温度都大于30打开空调
     */
    public static void testScene1() throws Exception {
        // 创建测试环境
        Thermometer t1 = new Thermometer(32);
        Thermometer t2 = new Thermometer(35);
        AirConditioner airConditioner = new AirConditioner(false,25,4);

        // QLExpress
        ExpressRunner runner = new ExpressRunner();

        DefaultContext<String,Object> context = new DefaultContext<>();
        context.put("t1",t1);
        context.put("t2",t2);
        context.put("airConditioner",airConditioner);

        String exp  = "if t1.getTemperature() > 30 and t2.getTemperature() > 30 then airConditioner.setStatus(true) else airConditioner.setStatus(false)";

        Object executeResult = runner.execute(exp, context, null, true, false);
    }

    /**
     *  两个房间的温度都大于30打开空调 如果第一个房间温度小于30 更改空调风力为2档
     * @throws Exception
     */
    public static void testScene2() throws Exception {
        // 创建测试环境
        Thermometer t1 = new Thermometer(20);
        Thermometer t2 = new Thermometer(35);
        AirConditioner airConditioner = new AirConditioner(false,25,4);

        // QLExpress
        ExpressRunner runner = new ExpressRunner();

        DefaultContext<String,Object> context = new DefaultContext<>();
        context.put("t1",t1);
        context.put("t2",t2);
        context.put("airConditioner",airConditioner);

        String exp  = "if t1.getTemperature() > 30 then { if t2.getTemperature() > 30 then airConditioner.setStatus(true) else airConditioner.setStatus(false) } else airConditioner.setWindLevel(2)";

        Object executeResult = runner.execute(exp, context, null, true, false);
    }


    /**
     *  两个房间的温度都大于30打开空调 如果第一个房间温度小于30 获取第二个房间的温度 如果同样小于30 更改空调风力为2档 否则更新空调预期温度为23
     * @throws Exception
     */
    public static void testScene3() throws Exception {
        // 创建测试环境
        Thermometer t1 = new Thermometer(20);
        Thermometer t2 = new Thermometer(35);
        AirConditioner airConditioner = new AirConditioner(false,25,4);

        // QLExpress
        ExpressRunner runner = new ExpressRunner();

        DefaultContext<String,Object> context = new DefaultContext<>();
        context.put("t1",t1);
        context.put("t2",t2);
        context.put("airConditioner",airConditioner);

        String exp  = "if t1.getTemperature(deviceId,identifier) > 30 then { if t2.getTemperature() > 30 then airConditioner.setStatus(true) else airConditioner.setStatus(false) } else { if t2.getTemperature() < 30 then airConditioner.setWindLevel(2) else airConditioner.setExpectation(23) }";

        Object executeResult = runner.execute(exp, context, null, true, false);
    }
}
