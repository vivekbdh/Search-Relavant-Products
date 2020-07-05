package com.klevu.model;

import org.bson.types.ObjectId;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import java.util.Set;

/**
 * Created by vivek on 3/7/20.
 */
@EntityScan
public class Order {
    private ObjectId _id;
    private String customerIp;
    private Set<Product> productPurchased;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public Set<Product> getProductPurchased() {
        return productPurchased;
    }

    public void setProductPurchased(Set<Product> productPurchased) {
        this.productPurchased = productPurchased;
    }

    public String getCustomerIp() {
        return customerIp;
    }

    public void setCustomerIp(String customerIp) {
        this.customerIp = customerIp;
    }
}
