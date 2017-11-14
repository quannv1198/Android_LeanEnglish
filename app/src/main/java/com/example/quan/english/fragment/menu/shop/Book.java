package com.example.quan.english.fragment.menu.shop;

public class Book {
    private String linkImage;
    private String link;
    private String name;
    private String price;

    public Book(String link, String name, String price,String linkImage) {
        this.link = link;
        this.name = name;
        this.price = price;
        this.linkImage = linkImage;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public String getPrice() {
        return price;
    }

    public String getLinkImage() {
        return linkImage;
    }
}
