package com.example.liteup;

public class Food {
    private int id;
    private  String name;
    private String price;
    private byte[] image;

    public Food(int id, String name, String price, byte[] image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
