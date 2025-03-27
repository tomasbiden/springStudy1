package com.bolin.group1.dir2.cata1.folder.javaBasic.group1.dir1.cata1;

import com.bolin.group1.dir1.elasticsearch.dir1.Product;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CommonTips {


//    防止空异常,Optional 对象不可能为null，他的value可能为null， 里面.isPresent类似于ifElse作用
    public  void nullExceptionPrevention(){
        List<String> stringList = null; // 可能返回null

// 传统方式（可能抛出NullPointerException）
        if(stringList != null && !stringList.isEmpty()) {
            String firstElement = stringList.get(0);
            System.out.println(firstElement.toUpperCase());
        }
        Product product = new Product();
// 使用Optional的优雅方式
        Optional.ofNullable(stringList)
                .filter(list -> !list.isEmpty()) // 过滤空列表
                .map(list -> list.get(0))       // 获取第一个元素
                .map(String::toUpperCase)       // 转换为大写
                .ifPresent(product::setName); // 如果存在则打印

        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("sku11","可乐",new BigDecimal("3")));
        products.add(new Product("sku12","雪碧",new BigDecimal("3.5")));
//        其实没有必要变成Optional，因为有key的话一定有value
        Map<String, List<Product>> collect = products.stream().collect(Collectors.groupingBy(Product::getSku));
//        products.stream().collect(Collectors.groupingBy(Product::getSku,Product::getPrice,Collectors.reducing((left,right)->left)));
//        Optional是在map映射的时候引入的啊
        Map<String, Optional<BigDecimal>> collect1 = products.stream().collect(Collectors.groupingBy(Product::getSku, Collectors.mapping(Product::getPrice, Collectors.reducing((left, right) -> left))));
        Product needSetProdcutVO = new Product();
        needSetProdcutVO.setSku("sku11");
//        这里的Option.empty是为了配合后面的ifPresent
        collect1.getOrDefault(needSetProdcutVO.getSku(),Optional.empty()).ifPresent(needSetProdcutVO::setPrice);




    }

}
