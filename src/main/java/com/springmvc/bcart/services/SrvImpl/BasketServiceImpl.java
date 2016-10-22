package com.springmvc.bcart.services.SrvImpl;

import com.springmvc.bcart.model.Basket;
import com.springmvc.bcart.services.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketServiceImpl implements BasketService {

    @Autowired
    MongoTemplate mongoTemplate;

    public static String BASKET_COLLECTION_NAME = "Basket";

    @Override
    public List<Basket> getBaskets() {
        return mongoTemplate.findAll(Basket.class, BASKET_COLLECTION_NAME);
    }

    @Override
    public Basket save(String productId, Double count) {
        Basket basket = new Basket(productId, count);
        mongoTemplate.save(basket, BASKET_COLLECTION_NAME);
        return basket;
    }

    @Override
    public void deleteBasket(String productId) {
        mongoTemplate.remove(findBasketQuery(productId), BASKET_COLLECTION_NAME);
    }

    private Query findBasketQuery(String productId) {
        return new Query(Criteria.where("productId").is(productId));
    }

}
