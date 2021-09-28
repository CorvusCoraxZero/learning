package com.zero.corn;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CornUtil {
    private static final String CYCLETIME =  "cycleTime";
    private static final String FIXEDTIME =  "fixedTime";
    private static final String MINUTE =  "min";
    private static final String HOUR =  "hour";
    private static final String DAY =  "hour";
    private static final String WEEK =  "week";
    private static final String MONTH =  "month";


    public static void main(String[] args) throws Exception {
        System.out.println(jsonParseToCorn(getTimeJson()));
    }

    private static String getTimeJson() {
        return "{\"triggerMode\":\"cycleTime\",\"listenType\":\"timer\",\"apiDomain\":\"api.link.aliyun.com\",\"cycle\":\"month\",\"dayOfMonth\":26,\"effectiveTime\":\"02:02:02\"}";
    }

    public static String jsonParseToCorn(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        CornInfo cornInfo = mapper.readValue(json, CornInfo.class);

        String seconds = "0 ";
        String minutes = "0 ";
        String hours = "0 ";
        String dayofMonth = "? ";
        String month = "* ";
        String dayofWeek = "? ";
        String year = "* ";

        if (CYCLETIME.equals(cornInfo.getTriggerMode())) {
            if (cornInfo.getEffectiveTime() != null) { // 设置激活时间
                String[] split = cornInfo.getEffectiveTime().split(":");
                hours = split[0] + " ";
                minutes = split[1] + " ";
                seconds = split[2] + " ";
            }

            if (MINUTE.equals(cornInfo.getCycle())) { // min,hour,day,week,month
                seconds = "0/" + cornInfo.getInterval() + " ";
                hours = "* ";
                dayofMonth = "* ";
            } else if (HOUR.equals(cornInfo.getCycle())) {
                hours = "0/" + cornInfo.getInterval() + " ";
                dayofMonth = "* ";
            } else if (DAY.equals(cornInfo.getCycle())) {
                dayofMonth = "* ";
            } else if (WEEK.equals(cornInfo.getCycle())) {
                dayofWeek = cornInfo.getDayOfWeek() + " ";
            } else if (MONTH.equals(cornInfo.getCycle())) {
                dayofMonth = cornInfo.getDayOfMonth() + " ";
            } else {
                throw new Exception("不存在的循环触发方式");
            }
        } else if (FIXEDTIME.equals(cornInfo.getTriggerMode())){
            String[] split = cornInfo.getEffectiveTime().split(":|\\s|/");
            year = split[0] + " ";
            month = split[1] + " ";
            dayofMonth = split[2] + " ";
            hours = split[3] + " ";
            minutes = split[4] + " ";
            seconds = split[5] + " ";
        }
        return seconds + minutes + hours + dayofMonth + month + dayofWeek + year;
    }
}
