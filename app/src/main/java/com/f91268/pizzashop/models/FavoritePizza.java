package com.f91268.pizzashop.models;

public class FavoritePizza {

    private int id;
    private String pizzaName;
    private String ingredients;

    public FavoritePizza() {
    }

    public FavoritePizza(int id, String pizzaName, String ingredients) {
        this.id = id;
        this.pizzaName = pizzaName;
        this.ingredients = ingredients;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPizzaName() {
        return pizzaName;
    }

    public void setPizzaName(String pizzaName) {
        this.pizzaName = pizzaName;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "FavoritePizza{" +
                "id=" + id +
                ", pizzaName='" + pizzaName + '\'' +
                ", ingredients='" + ingredients + '\'' +
                '}';
    }
}
