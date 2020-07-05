package com.klevu.repository;

import com.klevu.model.Order;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by vivek on 4/7/20.
 */
public interface OrderRepository extends MongoRepository<Order, ObjectId>{
    Order findByCustomerIp(String customerIp);
    List<Order> findByProductPurchasedProductIdContains(String customerIp);
}
