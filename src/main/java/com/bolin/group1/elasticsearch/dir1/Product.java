package com.bolin.group1.elasticsearch.dir1;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class Product {
    String sku;
    String name;
    BigDecimal price;
}
