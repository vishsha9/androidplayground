package com.vishalshah.playground.models;

/**
 * Created by vishalshah on 07/07/16.
 */
public class ProductModelRXRetrofit {
    private String name;
    private double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductModelRXRetrofit{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
