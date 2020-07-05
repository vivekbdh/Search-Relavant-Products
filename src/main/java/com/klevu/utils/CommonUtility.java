package com.klevu.utils;

import com.klevu.model.Product;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import java.util.*;

/**
 * Created by vivek on 3/7/20.
 */
public class CommonUtility {
    public static final String getErrorResponse(String controller, String method, HttpStatus httpStatus){
        return  "{" +
                "    \"timestamp\": \""+new Date()+"\"," +
                "    \"status\": "+httpStatus.value()+"," +
                "    \"message\": \""+httpStatus.name()+"\"," +
                "    \"path\": \"/"+controller+"/"+method+"\"" +
                "}";
    }

    public static final String getSuccessResponse(String message, HttpStatus httpStatus){
        return  "{" +
                "    \"status\": "+httpStatus.value()+"," +
                "    \"message\": \""+message+"\"" +
                "}";
    }

    public static Map<Product, Integer> sortByValue(final Map<Product, Integer> mapObj) {
        Map<Product, Integer> resultMap = new LinkedHashMap<>();
        mapObj.entrySet().stream()
                .sorted(Map.Entry.<Product, Integer>comparingByValue().reversed())
                .forEachOrdered(x -> resultMap.put(x.getKey(), x.getValue()));
        return resultMap;
    }

    public static JSONObject getJsonResponseObject(Product product) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ProductId", product.getProductId());
        jsonObject.put("ProductName", product.getProductName());
        return jsonObject;
    }
}
