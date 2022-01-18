package com.f91268.pizzashop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.f91268.pizzashop.data.DBHandler;


public class DisplayItemActivity extends AppCompatActivity implements View.OnClickListener {

    CheckBox checkPepperoni, checkBacon, checkMozzarella, checkMushrooms, checkOnions;
    TextView quantityTextView, nameTextView, ingredientsTextView, priceTextView;
    int quantity = 1;
    double extraPepperoni = 1.2;
    double extraBacon = 0.9;
    double extraMozzarella = 0.8;
    double extraMushrooms = 0.7;
    double extraOnions = 0.6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_item);

        nameTextView = findViewById(R.id.selected_item_name);
        ingredientsTextView = findViewById(R.id.selected_item_ingredients);
        priceTextView = findViewById(R.id.selected_item_price);

        Intent intent = getIntent();

        if (intent.getExtras() != null) {
            String name = intent.getStringExtra("item_name");
            String ingredients = intent.getStringExtra("item_ingredients");
            String price = intent.getStringExtra("item_price");

            nameTextView.setText(name);
            ingredientsTextView.setText(ingredients);
            priceTextView.setText(price);
        }

        // Quantity View
        quantityTextView = findViewById(R.id.display_quantity);

        // Buttons
        Button btnIncrement = findViewById(R.id.btn_increment);
        Button btnDecrement = findViewById(R.id.btn_decrement);
        ImageButton btnAddToFavorites = findViewById(R.id.btn_add_to_favorites);
        ImageButton btnAddToCart = findViewById(R.id.btn_add_to_cart);

        btnIncrement.setOnClickListener(this);
        btnDecrement.setOnClickListener(this);
        btnAddToFavorites.setOnClickListener(this);
        btnAddToCart.setOnClickListener(this);

        // Checkbox

        checkPepperoni = findViewById(R.id.check_additional_pepperoni);
        checkBacon = findViewById(R.id.check_additional_bacon);
        checkMozzarella = findViewById(R.id.check_additional_mozzarella);
        checkMushrooms = findViewById(R.id.check_additional_mushrooms);
        checkOnions = findViewById(R.id.check_additional_onions);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("quantity", quantityTextView.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        quantityTextView.setText(savedInstanceState.getString("quantity"));
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(DisplayItemActivity.this, "Press the back arrow to go back", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_increment:
                increment(v);
                break;
            case R.id.btn_decrement:
                decrement(v);
                break;
            case R.id.btn_add_to_favorites:
                addToFavorites(v);
                break;
            case R.id.btn_add_to_cart:
                addToCart(v);
                break;

        }
    }

    private void addToCart(View v) {

        boolean hasExtraPepperoni = checkPepperoni.isChecked();
        boolean hasExtraBacon = checkBacon.isChecked();
        boolean hasExtraMozzarella = checkMozzarella.isChecked();
        boolean hasExtraMushrooms = checkMushrooms.isChecked();
        boolean hasExtraOnions = checkOnions.isChecked();

        double pizzaPrice = Double.parseDouble(priceTextView.getText().toString());

        double currentCost = calculateAdditionalCost(hasExtraPepperoni,hasExtraBacon,hasExtraMozzarella,hasExtraMushrooms,hasExtraOnions);
        String extraToppings = extraToppingsString(hasExtraPepperoni,hasExtraBacon,hasExtraMozzarella,hasExtraMushrooms,hasExtraOnions);


        double totalCost = (currentCost + pizzaPrice)*quantity;

        DBHandler dbHandler = new DBHandler(DisplayItemActivity.this);
        dbHandler.addItemToCart(nameTextView.getText().toString(),ingredientsTextView.getText().toString(),
                extraToppings, totalCost, quantity);

    }

    private double calculateAdditionalCost(
            boolean hasExtraPepperoni,
            boolean hasExtraBacon,
            boolean hasExtraMozzarella,
            boolean hasExtraMushrooms,
            boolean hasExtraOnions) {

        double additionalCost = 0;

        if(hasExtraPepperoni){
            additionalCost += extraPepperoni;
        }
        if(hasExtraBacon){
            additionalCost += extraBacon;
        }
        if(hasExtraMozzarella){
            additionalCost += extraMozzarella;
        }
        if(hasExtraMushrooms){
            additionalCost += extraMushrooms;
        }
        if(hasExtraOnions){
            additionalCost += extraOnions;
        }

        return additionalCost;

    }

    private String extraToppingsString(
            boolean hasExtraPepperoni,
            boolean hasExtraBacon,
            boolean hasExtraMozzarella,
            boolean hasExtraMushrooms,
            boolean hasExtraOnions){

        String toppings = "";

        if(hasExtraPepperoni){
            toppings += "Extra Pepperoni;";
        }
        if(hasExtraBacon){
            toppings += "Extra Bacon;";
        }
        if(hasExtraMozzarella){
            toppings += "Extra Mozzarella;";
        }
        if(hasExtraMushrooms){
            toppings += "Extra Mushrooms;";
        }
        if(hasExtraOnions){
            toppings += "Extra Onions;";
        }

        return toppings;

    }

    private void addToFavorites(View view) {
        DBHandler dbHandler = new DBHandler(DisplayItemActivity.this);
        dbHandler.addPizzaToFavorites(nameTextView.getText().toString(),ingredientsTextView.getText().toString());
    }

    private void increment(View view) {
        if(quantity < 5){
            quantity += 1;
            displayQuantity(quantity);
        }else {
            Toast.makeText(DisplayItemActivity.this, "Quantity limit reached!", Toast.LENGTH_SHORT).show();
        }
    }

    private void decrement(View view) {
        if (quantity != 1) {
            quantity -= 1;
            displayQuantity(quantity);
        } else {
            Toast.makeText(this, "Quantity cannot be less than 1!", Toast.LENGTH_LONG).show();
        }
    }

    private void displayQuantity(int num) {
        String stringNum = String.valueOf(num);
        quantityTextView.setText(stringNum);
    }


}