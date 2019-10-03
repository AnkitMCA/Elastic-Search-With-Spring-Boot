package com.spring.elasticsearchpractice.model;

import org.springframework.stereotype.Component;

/**
 * The type User.
 */
@Component
public class User {

    private String id;
    private String name;
    private String email;
    private String mobile;
    private String others;

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     * @return the id
     */
    public User setId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     * @return the name
     */
    public User setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     * @return the email
     */
    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Gets mobile.
     *
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Sets mobile.
     *
     * @param mobile the mobile
     * @return the mobile
     */
    public User setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    /**
     * Gets others.
     *
     * @return the others
     */
    public String getOthers() {
        return others;
    }

    /**
     * Sets others.
     *
     * @param others the others
     * @return the others
     */
    public User setOthers(String others) {
        this.others = others;
        return this;
    }
}
