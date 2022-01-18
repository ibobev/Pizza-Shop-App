package com.f91268.pizzashop.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.f91268.pizzashop.CartActivity;
import com.f91268.pizzashop.R;
import com.f91268.pizzashop.data.DBHandler;
import com.f91268.pizzashop.models.CartItem;

import java.util.ArrayList;


public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.CartViewHolder>{

    private ArrayList<CartItem> cartItemArrayList;
    private LayoutInflater layoutInflater;

    public CartRecyclerViewAdapter(Context context, ArrayList<CartItem> cartItemArrayList) {
        this.cartItemArrayList = cartItemArrayList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.cart_item_row_holder, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItemArrayList.get(position);

        int id = cartItem.getId();
        String idString = Integer.toString(id);

        //double totalCost = cartItem.getItemPrice() * cartItem.getItemQuantity();
        String quantity = Integer.toString(cartItem.getItemQuantity());
        String totalCostString = Double.toString(cartItem.getItemPrice());

        System.out.println(cartItem.getItemExtraToppings());

        holder.itemNameView.setText(cartItem.getItemName());
        holder.itemIngredientsView.setText(cartItem.getItemDescription());
        holder.itemToppingsView.setText(cartItem.getItemExtraToppings());
        holder.itemQuantityView.setText(quantity);
        holder.itemPriceView.setText(totalCostString);

        holder.btnRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler dbHandler = new DBHandler(v.getContext());
                dbHandler.deleteCartItem(idString);
                cartItemArrayList.remove(cartItem);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartItemArrayList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{
        TextView itemNameView, itemIngredientsView, itemToppingsView, itemPriceView, itemQuantityView;
        ImageButton btnRemoveItem;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameView = itemView.findViewById(R.id.item_name);
            itemIngredientsView = itemView.findViewById(R.id.item_ingredients);
            itemToppingsView = itemView.findViewById(R.id.item_toppings);
            itemPriceView = itemView.findViewById(R.id.item_total_cost);
            itemQuantityView = itemView.findViewById(R.id.item_quantity);
            btnRemoveItem = itemView.findViewById(R.id.btn_remove_from_cart);
        }
    }
}
