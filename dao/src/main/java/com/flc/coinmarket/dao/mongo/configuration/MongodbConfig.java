package com.flc.coinmarket.dao.mongo.configuration;

import com.bugull.mongo.BuguConnection;
import com.bugull.mongo.BuguDao;
import com.bugull.mongo.BuguFramework;
import com.bugull.mongo.annotations.Entity;
import com.flc.coinmarket.dao.mongo.dao.ConsumerTranceDetailDAO;
import com.flc.coinmarket.dao.mongo.model.ConsumerTranceDetail;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.annotation.PostConstruct;

@Configuration
public class MongodbConfig {
    @Autowired
    MongodbProperties mongodbProperties;

    private BuguConnection connection;
    @Bean
    public ConsumerTranceDetailDAO getBuguConnection(){
        BuguConnection connection = BuguFramework.getInstance().createConnection("db");
        connection.connect(mongodbProperties.getHost(), mongodbProperties.getPort(), mongodbProperties.getDatabase(), mongodbProperties.getUsername(), mongodbProperties.getPassword());
        this.connection = connection;
        return new ConsumerTranceDetailDAO();
    }

}