package com.bolin.redis.utils;

import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.redisson.api.RList;
import org.redisson.api.RMap;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.*;
import com.bolin.redis.utils.Detail;


@Component
public class SessionIpManager {

    @Autowired
    private RedissonClient redissonClient;

    private static final String ACCOUNT_TO_ID_LIST = "AccountToIdList";  // Redis 中存储的 List 键

    private static final  String ID_TO_DETAIL_MAP="IdToDetailMap";



    // 添加 userAccount -> sessionId 的映射（sessionId 存储为 List）
    public void addSessionIdToAccount(String userAccount, String sessionId) {
        // 获取 Redis 中的 RList
        RList<String> sessionList = redissonClient.getList(ACCOUNT_TO_ID_LIST + ":" + userAccount);

        // 如果 sessionId 不在列表中，则添加
        if (!sessionList.contains(sessionId)) {
            sessionList.add(sessionId);
        }
    }

    // 删除 userAccount 下指定的 sessionId
    public void removeSessionIdFromAccount(String userAccount, String sessionId) {
        // 获取 Redis 中的 RList
        RList<String> sessionList = redissonClient.getList(ACCOUNT_TO_ID_LIST + ":" + userAccount);

        // 如果 sessionId 存在，删除它
        sessionList.remove(sessionId);
    }

    // 获取 userAccount 对应的所有 sessionId 列表
    public List<String> getSessionIdsByAccount(String userAccount) {
        // 获取 Redis 中的 RList
        RList<String> sessionList = redissonClient.getList(ACCOUNT_TO_ID_LIST + ":" + userAccount);

        // 返回所有 sessionId
        return sessionList.readAll();
    }

    // 更新 userAccount 对应的 sessionId（替换旧 sessionId 为新 sessionId）
    public void updateSessionId(String userAccount, String oldSessionId, String newSessionId) {
        // 获取 Redis 中的 RList
        RList<String> sessionList = redissonClient.getList(ACCOUNT_TO_ID_LIST + ":" + userAccount);

        // 删除旧的 sessionId
        sessionList.remove(oldSessionId);

        // 添加新的 sessionId
        sessionList.add(newSessionId);
    }

    // 删除 userAccount 下的所有 sessionId 映射
    public void removeAllSessionIdsByAccount(String userAccount) {
        // 获取 Redis 中的 RList
        RList<String> sessionList = redissonClient.getList(ACCOUNT_TO_ID_LIST + ":" + userAccount);

        // 清空所有 sessionId
        sessionList.clear();
    }

    // 获取所有 userAccount 对应的 sessionId 列表（返回所有用户的 sessionId）
    public List<String> getAllSessionIds() {
        // 获取 Redis 中的所有 sessionId 映射
        // 这个方法是通过遍历所有的 userAccount 来获取 sessionId 的
        // 如果需要通过 account 获取 sessionId，则可以实现一个遍历所有 account 的方法
        List<String> allSessionIds = new ArrayList<>();
        for (String account : redissonClient.getKeys().getKeysByPattern(ACCOUNT_TO_ID_LIST + ":*")) {
            RList<String> sessionList = redissonClient.getList(account);
            allSessionIds.addAll(sessionList);
        }
        return allSessionIds;
    }
    // 检查给定的 userAccount 和 sessionId 是否存在映射关系
    public boolean isSessionIdExists(String userAccount, String sessionId) {
        // 获取 Redis 中的 RList
        RList<String> sessionList = redissonClient.getList(ACCOUNT_TO_ID_LIST + ":" + userAccount);

        // 返回 sessionId 是否存在于列表中
        return sessionList.contains(sessionId);
    }

    // 添加或更新 ID -> Detail 映射
    public void addOrUpdateDetail(String id, Detail detail) {
        // 获取 Redis 中的 RMap
        RMap<String, Detail> idToDetailMap = redissonClient.getMap(ID_TO_DETAIL_MAP);

        // 使用 put 方法添加或更新映射
        idToDetailMap.put(id, detail);
    }

    // 删除 ID -> Detail 映射
    public void removeDetail(String id) {
        // 获取 Redis 中的 RMap
        RMap<String, Detail> idToDetailMap = redissonClient.getMap(ID_TO_DETAIL_MAP);

        // 删除指定的 ID 映射
        idToDetailMap.remove(id);
    }

    // 获取 ID 对应的 Detail
    public Detail getDetailById(String id) {
        // 获取 Redis 中的 RMap
        RMap<String, Detail> idToDetailMap = redissonClient.getMap(ID_TO_DETAIL_MAP);

        // 返回指定 ID 对应的 Detail
        return idToDetailMap.get(id);
    }

    // 更新 ID 对应的 Detail 中某个字段
    public void updateDetailField(String id, String field, Object value) {
        // 获取 Redis 中的 RMap
        RMap<String, Detail> idToDetailMap = redissonClient.getMap(ID_TO_DETAIL_MAP);

        // 获取指定 ID 对应的 Detail
        Detail detail = idToDetailMap.get(id);

        if (detail != null) {
            // 根据字段名更新值
            switch (field) {
                case "userName":
                    detail.setUserName((String) value);
                    break;
                case "count":
                    detail.setCount((Long) value);
                    break;
                case "createDate":
                    detail.setCreateDate((Long) value);
                    break;
                case "expireDate":
                    detail.setExpireDate((Long) value);
                    break;
                case "lastAccessDate":
                    detail.setLastAccessDate((Long) value);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field name");
            }

            // 更新 Redis 中的 Detail
            idToDetailMap.put(id, detail);
        }
    }

    // 获取所有 ID -> Detail 映射的所有 IDs
    public Set<String> getAllIds() {
        // 获取 Redis 中的 RMap
        RMap<String, Detail> idToDetailMap = redissonClient.getMap(ID_TO_DETAIL_MAP);

        // 返回所有的 ID
        return idToDetailMap.keySet();
    }

    // 获取所有 Detail 对象
    public Collection<Detail> getAllDetails() {
        // 获取 Redis 中的 RMap
        RMap<String, Detail> idToDetailMap = redissonClient.getMap(ID_TO_DETAIL_MAP);

        // 返回所有的 Detail 对象
        return idToDetailMap.values();
    }
}

