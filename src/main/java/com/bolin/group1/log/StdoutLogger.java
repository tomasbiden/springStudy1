package com.bolin.group1.log;

public class StdoutLogger extends com.p6spy.engine.spy.appender.StdoutLogger {

    @Override
    public void logText(String text) {
        System.out.printf("96spy接入");
        System.err.println(text+"ceshiya");
    }
}