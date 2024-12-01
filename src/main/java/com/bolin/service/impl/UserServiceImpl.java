package com.bolin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bolin.pojo.User;
import com.bolin.service.UserService;
import com.bolin.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-11-30 15:32:00
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




