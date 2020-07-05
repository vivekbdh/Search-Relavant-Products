package com.klevu.controller;

import com.klevu.model.Product;
import com.klevu.service.OrderDataService;
import com.klevu.utils.CommonUtility;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.websocket.server.PathParam;
import java.util.Map;

/**
 * Created by vivek on 3/7/20.
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    OrderDataService orderDataService;

    @RequestMapping(value = "/getRelevant",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity uploadFile(@PathParam("productId") String productId){
        ResponseEntity responseEntity;
        try {
            System.out.println("Request to get recommendation for the productId:"+productId);
            Map<Product, Integer> relevantProducts = orderDataService.findRecommendedProducts(productId);

            JSONArray resultObjArray = new JSONArray();

            relevantProducts.entrySet().stream().limit(5).forEach(e ->
                    resultObjArray.put(CommonUtility.getJsonResponseObject(e.getKey()))
            );

            responseEntity = new ResponseEntity(resultObjArray.toString(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            responseEntity = new ResponseEntity(CommonUtility.getErrorResponse("file","uploadFile", HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}
