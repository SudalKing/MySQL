package com.example.fastcampusmysql.util;

import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

public class PageHelper {
    public static String orderBy(Sort sort){
        if (sort.isEmpty()){
            return "id DESC";
        }

        List<Sort.Order> orders = sort.toList();
        var orderBys = orders
                .stream()
                .map(Sort.Order::getProperty)
                .toList();

        return String.join(", ", orderBys);
    }
}
