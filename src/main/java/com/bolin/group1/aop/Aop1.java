package com.bolin.group1.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class Aop1 {

    @Pointcut("@annotation(com.bolin.group1.aop.annotion1)")
//    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    private  void pointCut(){}


    @Before("pointCut()")
    public  void before(JoinPoint joinPoint) throws NoSuchMethodException {
//        只能用于cglib代理
        /*
        // 获取目标方法
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        // 获取目标方法上的 @annotion1 注解
        Method method = methodSignature.getMethod();
        annotion1 annotation = method.getAnnotation(annotion1.class);

        // 获取注解的 value 属性值
        String value = annotation.value();

        // 打印注解的 value 属性值
        System.out.println("注解的 value 属性值为: " + value);

        // 打印方法参数（可选）
        Object[] args = joinPoint.getArgs();
        System.out.println("方法参数: " + Arrays.toString(args));

         */
//      可以用于jdk代理
        Method method=((MethodSignature)joinPoint.getSignature()).getMethod();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        Method realMethod = joinPoint.getTarget().getClass().getDeclaredMethod(methodSignature.getName(),
                method.getParameterTypes());
//此处realMethod是目标对象（原始的）的方法
        annotion1 annotation  = realMethod.getAnnotation(annotion1.class);
//此处 an 不为null


        // 获取注解的 value 属性值
        String value = annotation.value();

        // 打印注解的 value 属性值
        System.out.println("注解的 value 属性值为: " + value);

        // 打印方法参数（可选）
        Object[] args = joinPoint.getArgs();
        System.out.println("方法参数: " + Arrays.toString(args));
    }
}
