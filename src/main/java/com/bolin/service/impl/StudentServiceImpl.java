package com.bolin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bolin.demos.pojo.Student;
import com.bolin.service.StudentService;
import com.bolin.mapper.StudentMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【student(学生表)】的数据库操作Service实现
* @createDate 2024-11-30 15:45:38
*/
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
    implements StudentService{

}




