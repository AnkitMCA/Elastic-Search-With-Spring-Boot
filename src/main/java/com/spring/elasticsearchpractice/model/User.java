package com.spring.elasticsearchpractice.model;

import org.springframework.stereotype.Component;

@Component
public class User {

    private String id;
    private String name;
    private String email;
    private String mobile;
    private String others;

    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public User setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getOthers() {
        return others;
    }

    public User setOthers(String others) {
        this.others = others;
        return this;
    }
}
