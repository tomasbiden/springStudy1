package com.bolin.group1.dir1.Annotion.DynamicProxy.group1.dir1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class DynamicProxy1 {

    // 接口1：用户服务
    interface UserService {
        void createUser(String username);
        void deleteUser(int userId);
        default String getUserRole(int userId) {
            return "default_role";
        }
    }

    // 接口2：日志服务
    interface LogService {
        void logInfo(String message);
        void logError(String error);
    }

    // 真实实现类：用户服务
    static class UserServiceImpl implements UserService {
        @Override
        public void createUser(String username) {
            System.out.println("[真实用户服务] 创建用户: " + username);
        }

        @Override
        public void deleteUser(int userId) {
            System.out.println("[真实用户服务] 删除用户ID: " + userId);
        }

        // 覆盖默认方法
        @Override
        public String getUserRole(int userId) {
            return "user_role_from_impl";
        }
    }

    // 真实实现类：日志服务
    static class LogServiceImpl implements LogService {
        @Override
        public void logInfo(String message) {
            System.out.println("[真实日志服务] INFO: " + message);
        }

        @Override
        public void logError(String error) {
            System.out.println("[真实日志服务] ERROR: " + error);
        }
    }

    public static void main(String[] args) {
        // 创建真实目标对象
        UserService realUserService = new UserServiceImpl();
        LogService realLogService = new LogServiceImpl();

        // 将接口与真实对象映射
        Map<Class<?>, Object> targetMap = new HashMap<>();
        targetMap.put(UserService.class, realUserService);
        targetMap.put(LogService.class, realLogService);

        // 创建 InvocationHandler，处理多接口多方法逻辑
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 1. 根据接口类型获取真实目标对象
                Class<?> declaringInterface = method.getDeclaringClass();
                Object target = targetMap.get(declaringInterface);

                if (target == null) {
                    throw new UnsupportedOperationException("未找到目标对象: " + declaringInterface);
                }

                // 2. 前置增强：打印日志
                System.out.println("[代理逻辑] 调用方法: " + method.getName());

                // 3. 调用真实对象的方法
                Object result = method.invoke(target, args);

                // 4. 后置增强：处理返回值（示例）
                if (method.getName().equals("getUserRole")) {
                    return "admin_proxy"; // 覆盖真实对象的返回值
                }

                return result;
            }
        };

        // 创建代理对象，同时实现 UserService 和 LogService
        Object proxy = Proxy.newProxyInstance(
                UserService.class.getClassLoader(),
                new Class[]{UserService.class, LogService.class},
                handler
        );

        // 转换为不同接口调用方法
        UserService userService = (UserService) proxy;
        LogService logService = (LogService) proxy;

        userService.createUser("Alice");
        // 输出:
        // [代理逻辑] 调用方法: createUser
        // [真实用户服务] 创建用户: Alice

        userService.deleteUser(1001);
        // 输出:
        // [代理逻辑] 调用方法: deleteUser
        // [真实用户服务] 删除用户ID: 1001

        System.out.println("用户角色: " + userService.getUserRole(1001));
        // 输出:
        // [代理逻辑] 调用方法: getUserRole
        // 用户角色: admin_proxy （覆盖了真实对象的返回值）

        logService.logInfo("系统启动");
        // 输出:
        // [代理逻辑] 调用方法: logInfo
        // [真实日志服务] INFO: 系统启动

        logService.logError("数据库连接失败");
        // 输出:
        // [代理逻辑] 调用方法: logError
        // [真实日志服务] ERROR: 数据库连接失败
    }
}