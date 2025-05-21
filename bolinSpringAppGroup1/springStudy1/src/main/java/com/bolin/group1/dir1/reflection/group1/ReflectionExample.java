package com.bolin.group1.dir1.reflection.group1;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;

class Person {
    private String name;
    private int age;

    public Person() {
        // 默认构造方法
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private void sayHello() {
        System.out.println("Hello, my name is " + name + " and I am " + age + " years old.");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

public class ReflectionExample {
    public static void main(String[] args) throws Exception {
        // 通过反射获取 Person 类的 Class 对象
        Class<?> personClass = Class.forName("Person");

        // 获取并调用构造方法
        Constructor<?> constructor = personClass.getConstructor(String.class, int.class);
        Object personObj = constructor.newInstance("John", 25);

        // 获取并修改 private 字段
        Field nameField = personClass.getDeclaredField("name");
        nameField.setAccessible(true);  // 访问 private 字段
        System.out.println("Before Reflection: Name = " + nameField.get(personObj));
        
        // 修改字段值
        nameField.set(personObj, "Alice");
        System.out.println("After Reflection: Name = " + nameField.get(personObj));

        // 调用私有方法
        Method sayHelloMethod = personClass.getDeclaredMethod("sayHello");
        sayHelloMethod.setAccessible(true);  // 访问私有方法
        sayHelloMethod.invoke(personObj);   // 调用方法

        // 调用公共方法
        Method setAgeMethod = personClass.getMethod("setAge", int.class);
        setAgeMethod.invoke(personObj, 30);
        
        // 调用修改后的对象
        sayHelloMethod.invoke(personObj);   // 调用方法
    }
}
