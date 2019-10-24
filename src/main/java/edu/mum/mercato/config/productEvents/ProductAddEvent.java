package edu.mum.mercato.config.productEvents;


public class ProductAddEvent {

    private String message;

    public ProductAddEvent(String name){
        this.message = name;
    }

    public String getContent() {
        return message;
    }
}
