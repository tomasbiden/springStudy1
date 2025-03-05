package com.bolin.group1.dir2.cata1.folder.javaBasic.Exception.grouop1.dir1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FiveLayerExceptionDemo {

    // 自定义异常类（层级1到5）
    static class Layer1Exception extends RuntimeException {
        public Layer1Exception(String message, Throwable cause) {
            super(message, cause);
        }
    }

    static class Layer2Exception extends RuntimeException {
        public Layer2Exception(String message, Throwable cause) {
            super(message, cause);
        }
    }

    static class Layer3Exception extends RuntimeException {
        public Layer3Exception(String message, Throwable cause) {
            super(message, cause);
        }
    }

    static class Layer4Exception extends RuntimeException {
        public Layer4Exception(String message, Throwable cause) {
            super(message, cause);
        }
    }

    static class Layer5Exception extends RuntimeException {
        public Layer5Exception(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static void main(String[] args) {
        try {
            methodA();
        } catch (Layer1Exception e) {
            e.printStackTrace(); // 打印五层嵌套的异常链
        }
    }

    // Layer 1
    static void methodA() {
        try {
            methodB();
        } catch (Layer2Exception e) {
            throw new Layer1Exception("Layer1: 调用B失败", e);
        }
    }

    // Layer 2
    static void methodB() {
        try {
            methodC();
        } catch (Layer3Exception e) {
            throw new Layer2Exception("Layer2: 调用C失败", e);
        }
    }

    // Layer 3
    static void methodC() {
        try {
            methodD();
        } catch (Layer4Exception e) {
            throw new Layer3Exception("Layer3: 调用D失败", e);
        }
    }

    // Layer 4
    static void methodD() {
        try {
            methodE();
        } catch (Layer5Exception e) {
            throw new Layer4Exception("Layer4: 调用E失败", e);
        }
    }

    // Layer 5 (最底层)
    static void methodE() {
        try {
            new FileInputStream("invalid.txt");
        } catch (FileNotFoundException e) {
            throw new Layer5Exception("Layer5: 文件未找到", e);
        }
    }
}