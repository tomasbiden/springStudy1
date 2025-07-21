package com.bolin.group2.dir1.cata1.abstractStudy.group1;

public class FileLogger extends AbstractLogger {
    // 成员变量
    protected String name;
    protected int level;

    public FileLogger(String name, int level) {
        super(name, level);
    }
    public void monitor(){
        
    }

    @Override
    public void writeToTarget(String message) {
        // 访问父类变量
        System.out.println("写入文件日志 level=" + level + ": " + message);
    }
    public static void main(String[] args){
//        super.log();
    }
}
