package com.example.demo.dao;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("keyCollection")
public class KeyEntity {
    private String id;
    private String use;
    private String key;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
