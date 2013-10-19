package com.mynameistodd.tappytap;

/**
 * Created by todd on 9/30/13.
 */
public class Subscription {
    private int id;
    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Subscription() {
    }

    public Subscription(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
