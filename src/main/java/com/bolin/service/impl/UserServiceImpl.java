package com.bolin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bolin.common.BaseResponse;
import com.bolin.common.ErrorCode;
import com.bolin.common.ResultUtils;
import com.bolin.exception.BusinessException;
import com.bolin.model.dto.user.UserQueryRequest;
import com.bolin.model.param.LoginParam;
import com.bolin.model.pojo.User;
import com.bolin.model.vo.LoginUserVO;
import com.bolin.model.vo.UserVO;
import com.bolin.redis.utils.Detail;
import com.bolin.redis.utils.SessionIpManager;
import com.bolin.service.UserService;
import com.bolin.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.bolin.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author Administrator
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-12-29 16:39:41
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Autowired
    RedissonClient redissonClient;
    public static final String SESSION_HASH_NAME = "UserIdSessionIdMap";

    @Autowired
    SessionIpManager sessionIpManager;
    @Override
    public void test1() {
        // 获取 Redis 中的 HashMap (RMap)
        RMap<String, String> map = redissonClient.getMap("userSessions2");



        // 插入数据到 Redis HashMap
        map.put("userA", "Session data for userA");
        map.put("userB", "Session data for userB");

        // 获取一个 RList 对象 (Redis List)
        RList<Object> testList = redissonClient.getList("testList");
        testList.add("testList");


        // 打印 HashMap 数据
        System.out.println("userA Session: " + map.get("userA"));
        System.out.println("userB Session: " + map.get("userB"));

        // 打印 RList 中的数据
        System.out.println("RList Contents: ");
        for (Object item : testList) {
            System.out.println(item);
        }

        // 关闭 RedissonClient 连接
//        redissonClient.shutdown();



    }

    @Override
    public String UserAuthCheck(HttpServletRequest httpServletRequest, LoginParam loginParam) {
        return null;
    }
    /**
     * 盐值，混淆密码
     */
    public static final String SALT = "bolin";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        synchronized (userAccount.intern()) {
            // 账户不能重复
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userAccount", userAccount);
            long count = this.baseMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            // 2. 加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            // 3. 插入数据
            User user = new User();
            user.setUserAccount(userAccount);
            user.setUserPassword(encryptPassword);
            boolean saveResult = this.save(user);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            return user.getId();
        }
    }

    @Override
    public BaseResponse<LoginUserVO > userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号错误");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = this.baseMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
//            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }
        // 3. 记录用户的登录态
        String requestedSessionId = request.getRequestedSessionId();
        if(requestedSessionId==null||sessionIpManager.getDetailById(requestedSessionId)==null){

            sessionIpManager.addSessionIdToAccount(userAccount,request.getSession().getId());
            Detail detail = new Detail();
            detail.setUserName(user.getUserName());
            detail.setUserAccount(user.getUserAccount());
            detail.setCount(1L);
            detail.setCreateDate(System.currentTimeMillis());
            detail.setExpireDate(System.currentTimeMillis()+300000l);
            detail.setLastAccessDate(System.currentTimeMillis());
            sessionIpManager.addOrUpdateDetail(request.getSession().getId(),detail);
        }else {

            Detail detailById = sessionIpManager.getDetailById(requestedSessionId);
            detailById.setCount(detailById.getCount()+1);
            detailById.setExpireDate(System.currentTimeMillis()+300000l);
            detailById.setLastAccessDate(System.currentTimeMillis());
            sessionIpManager.addOrUpdateDetail(requestedSessionId,detailById);
        }

        if(sessionIpManager.getAllSessionIds().size()<=1){

        }else {
            for (String sessionId : sessionIpManager.getAllSessionIds()) {
                if(sessionId.equals(requestedSessionId)) {
                    continue;
                }else{
//                    提示已经登陆者来禁止当前的登陆
                    Detail detailById = sessionIpManager.getDetailById(requestedSessionId);
                    detailById.setNeedCheck(true);
                    sessionIpManager.addOrUpdateDetail(sessionId,detailById);
                }
            }
//            提示登陆者来禁止别人的已经登陆
            return  ResultUtils.success(this.getLoginUserVO(user),100);
        }


        return ResultUtils.success(this.getLoginUserVO(user));
    }

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    @Override
    public User getLoginUser(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        long userId = currentUser.getId();
        currentUser = this.getById(userId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    /**
     * 获取当前登录用户（允许未登录）
     *
     * @param request
     * @return
     */
    @Override
    public User getLoginUserPermitNull(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            return null;
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        long userId = currentUser.getId();
        return this.getById(userId);
    }

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    @Override
    public boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return isAdmin(user);
    }

    @Override
    public boolean isAdmin(User user) {
        return false;
    }


    /**
     * 用户注销
     *
     * @param request
     */
    @Override
    public boolean userLogout(HttpServletRequest request) {
        if (request.getSession().getAttribute(USER_LOGIN_STATE) == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "未登录");
        }
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }

    @Override
    public LoginUserVO getLoginUserVO(User user) {
        if (user == null) {
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        return loginUserVO;
    }

    @Override
    public UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public List<UserVO> getUserVO(List<User> userList) {
        if (CollUtil.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream().map(this::getUserVO).collect(Collectors.toList());
    }

    @Override
    public QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        /*-
        Long id = userQueryRequest.getId();
        String unionId = userQueryRequest.getUnionId();
        String mpOpenId = userQueryRequest.getMpOpenId();
        String userName = userQueryRequest.getUserName();
        String userProfile = userQueryRequest.getUserProfile();
        String userRole = userQueryRequest.getUserRole();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq(StringUtils.isNotBlank(unionId), "unionId", unionId);
        queryWrapper.eq(StringUtils.isNotBlank(mpOpenId), "mpOpenId", mpOpenId);
        queryWrapper.eq(StringUtils.isNotBlank(userRole), "userRole", userRole);
        queryWrapper.like(StringUtils.isNotBlank(userProfile), "userProfile", userProfile);
        queryWrapper.like(StringUtils.isNotBlank(userName), "userName", userName);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;

         */
        return null;
    }

    // 将 userId 和 sessionId 存入 Redis 的 Hash 类型
    public void addUserSession(String userId, String sessionId) {
        // 获取 Redis 中的 Hash 对象
        RMap<String, String> sessionMap = redissonClient.getMap(SESSION_HASH_NAME);
        // 添加键值对
        sessionMap.put(userId, sessionId);
    }

    // 根据 userId 获取 sessionId
    public String getSessionId(String userId) {
        RMap<String, String> sessionMap = redissonClient.getMap(SESSION_HASH_NAME);
        return sessionMap.get(userId);
    }

    // 删除指定 userId 的会话
    public void removeUserSession(String userId) {
        RMap<String, String> sessionMap = redissonClient.getMap(SESSION_HASH_NAME);
        sessionMap.remove(userId);
    }

    @Override
    public String getCount(String requestedSessionId) {
        Detail detailById = sessionIpManager.getDetailById(requestedSessionId);

        if(detailById==null||System.currentTimeMillis()>detailById.getExpireDate()){
            return  new String("请重新登陆");
        }else {


            Long count = detailById.getCount();
            detailById.setCount(count+1);
            sessionIpManager.addOrUpdateDetail(requestedSessionId,detailById);
            if( detailById.getNeedCheck()!=null&&detailById.getNeedCheck()){

                detailById.setNeedCheck(false);
                sessionIpManager.addOrUpdateDetail(requestedSessionId,detailById);
                return  new String("他人正在登陆");
            }

            return new String("Welcome"+detailById.getUserName()+count);
        }




    }

    @Override
    public void redisRemove(String requestedSessionId) {
        if(sessionIpManager.getAllSessionIds().size()<=1){
            return;
        }else {
            for (String sessionId : sessionIpManager.getAllSessionIds()) {
                if(sessionId.equals(requestedSessionId)) {
                    continue;
                }else{
                    Detail detailById = sessionIpManager.getDetailById(requestedSessionId);
                    sessionIpManager.removeSessionIdFromAccount(detailById.getUserAccount(),sessionId);
                    sessionIpManager.removeDetail(sessionId);
                }
            }
        }
    }
}




