package com.bolin.group1.dir1.Annotion.DynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxy1 {
    public static void main(String[] args) {
        // 创建 InvocationHandler，处理多接口多方法逻辑
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 根据接口类型和方法名分发逻辑
                Class<?> declaringInterface = method.getDeclaringClass();

                if (declaringInterface == UserService.class) {
                    switch (method.getName()) {
                        case "createUser":
                            System.out.println("[用户服务] 创建用户: " + args[0]);
                            return null;
                        case "deleteUser":
                            System.out.println("[用户服务] 删除用户ID: " + args[0]);
                            return null;
                        case "getUserRole":
                            return "admin"; // 覆盖默认实现
                        default:
                            throw new UnsupportedOperationException("未实现的方法: " + method);
                    }
                } else if (declaringInterface == LogService.class) {
                    switch (method.getName()) {
                        case "logInfo":
                            System.out.println("[日志] INFO: " + args[0]);
                            return null;
                        case "logError":
                            System.out.println("[日志] ERROR: " + args[0]);
                            return null;
                        default:
                            throw new UnsupportedOperationException("未实现的方法: " + method);
                    }
                }

                throw new UnsupportedOperationException("未知接口: " + declaringInterface);
            }
        };

        // 创建代理对象，同时实现 UserService 和 LogService
        Object proxy = Proxy.newProxyInstance(
                UserService.class.getClassLoader(),
                new Class[]{UserService.class, LogService.class}, // 关键：传入多个接口
                handler
        );

        // 转换为不同接口调用方法
        UserService userService = (UserService) proxy;
        LogService logService = (LogService) proxy;

        userService.createUser("Alice");    // 输出: [用户服务] 创建用户: Alice
        userService.deleteUser(1001);       // 输出: [用户服务] 删除用户ID: 1001
        System.out.println("用户角色: " + userService.getUserRole(1001)); // 输出: 用户角色: admin

        logService.logInfo("系统启动");      // 输出: [日志] INFO: 系统启动
        logService.logError("数据库连接失败");// 输出: [日志] ERROR: 数据库连接失败
    }
}
