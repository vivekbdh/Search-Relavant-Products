package com.klevu.service;

import com.klevu.model.Order;
import com.klevu.model.Product;
import com.klevu.repository.OrderRepository;
import com.klevu.utils.CommonUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by vivek on 4/7/20.
 */
@Service
public class OrderDataService {

    @Autowired
    private OrderRepository orderRepository;

    public void storeData(List<String> orderDataList){
        long startTime = System.currentTimeMillis();
        for(String dataStr : orderDataList){
            try {
                String productId = dataStr.substring(0, dataStr.indexOf("\t")).trim();
                String customerIp = dataStr.substring(dataStr.indexOf("\t")+1, dataStr.indexOf("\t", dataStr.indexOf("\t")+1)).trim();
                String productName = dataStr.substring(dataStr.indexOf("\t", dataStr.indexOf("\t")+2)).trim();

                Product product = new Product(productId, productName);
                Order order = getOrderByData(customerIp, product);
                if(null != order)
                    orderRepository.save(order);
            }catch (Exception e){
                System.out.println("Found error in data : "+dataStr);
                e.printStackTrace();
            }finally {
                System.gc();
            }
        }
        System.out.println("Data successfully uploaded");
        System.out.println("Total time to process the data : "+((System.currentTimeMillis()-startTime))+" ms");
    }

    public Order getOrderByData(String customerIp, Product product){
        try {
            Order order = orderRepository.findByCustomerIp(customerIp);
            if(null == order){
                Set<Product> productPurchased = new HashSet<>();
                productPurchased.add(product);

                order = new Order();
                order.setCustomerIp(customerIp);
                order.setProductPurchased(productPurchased);
            }else{
                Set<Product> productPurchased = order.getProductPurchased();
                productPurchased.add(product);
                order.setProductPurchased(productPurchased);
            }
            return order;
        }catch (Exception e){
            System.out.println("Something wrong in the data: "+customerIp);
            e.printStackTrace();
            return null;
        }
    }

    public Map<Product, Integer> findRecommendedProducts(String productId){
        Map<Product, Integer> resultMap = new HashMap<>();
        try{
            List<Order> orderList = orderRepository.findByProductPurchasedProductIdContains(productId);
            for(Order order:orderList){
                for(Product product : order.getProductPurchased()){
                    if(!product.getProductId().equals(productId))
                        resultMap.put(product, resultMap.getOrDefault(product, 0)+1);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CommonUtility.sortByValue(resultMap);
    }
}
