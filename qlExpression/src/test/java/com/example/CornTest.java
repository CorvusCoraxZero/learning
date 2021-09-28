package com.example;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class CornTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue( true );
    }

    private static String getTimeJson() {
        return "{\n" +
                "\t\t\t\\\"triggerMode\\\":\\\"cycleTime\\\",\n" +
                "\t\t\t\\\"interval\\\":\\\"5\\\",\n" +
                "\t\t\t\\\"cycle\\\":\\\"hour\\\",\n" +
                "\t\t\t\\\"startTime\\\":1629734400000,\n" +
                "\t\t\t\\\"endTime\\\":1629907200000,\n" +
                "\t\t\t\\\"listenType\\\":\\\"timer\\\",\n" +
                "\t\t\t\\\"apiDomain\\\":\n" +
                "\t\t\t\\\"api.link.aliyun.com\\\"\n" +
                "\t\t\t}";
    }
}