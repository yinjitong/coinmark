package com.flc.coinmarket.dao.mysql.model.consumer;

import java.io.Serializable;

public class ConsumerWithBLOBs extends Consumer implements Serializable {
    private String fullPath;

    private String pathDirection;

    private static final long serialVersionUID = 1L;

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getPathDirection() {
        return pathDirection;
    }

    public void setPathDirection(String pathDirection) {
        this.pathDirection = pathDirection;
    }
}