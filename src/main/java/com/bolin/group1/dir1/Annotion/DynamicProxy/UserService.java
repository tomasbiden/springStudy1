package com.bolin.group1.dir1.Annotion.DynamicProxy;

interface UserService {
    void createUser(String username);
    void deleteUser(int userId);
    default String getUserRole(int userId) {
        return "default_role";
    }
}

/**
 * 接口2：日志服务（包含多个方法）
 */
interface LogService {
    void logInfo(String message);
    void logError(String error);
}