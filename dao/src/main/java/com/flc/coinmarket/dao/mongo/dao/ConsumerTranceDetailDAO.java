package com.flc.coinmarket.dao.mongo.dao;

import com.bugull.mongo.BuguDao;
import com.flc.coinmarket.dao.mongo.model.ConsumerTranceDetail;

public class ConsumerTranceDetailDAO extends BuguDao<ConsumerTranceDetail> {
    public ConsumerTranceDetailDAO(){
        super(ConsumerTranceDetail.class);
    }
}

