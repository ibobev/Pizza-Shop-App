package com.f91268.pizzashop.models;

public class CartItem {
    private int id;
    private String itemName;
    private String itemDescription;
    private String itemExtraToppings;
    private double itemPrice;
    private int itemQuantity;

    public CartItem() {

    }

    public CartItem(int id, String itemName, String itemDescription, String itemExtraToppings, double itemPrice, int itemQuantity) {
        this.id = id;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemExtraToppings = itemExtraToppings;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemExtraToppings() {
        return itemExtraToppings;
    }

    public void setItemExtraToppings(String itemExtraToppings) {
        this.itemExtraToppings = itemExtraToppings;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", itemExtraToppings='" + itemExtraToppings + '\'' +
                ", itemPrice=" + itemPrice +
                ", itemQuantity=" + itemQuantity +
                '}';
    }
}
