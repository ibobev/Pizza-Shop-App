package com.f91268.pizzashop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.f91268.pizzashop.R;
import com.f91268.pizzashop.models.MenuItem;

import java.util.ArrayList;

public class PizzaMenuAdapter extends ArrayAdapter<MenuItem> {


    public PizzaMenuAdapter(@NonNull Context context, @NonNull ArrayList<MenuItem> menuItems) {
        super(context, 0, menuItems);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Nullable
    @Override
    public MenuItem getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listMenuItemsView = convertView;
        if(listMenuItemsView == null){
            listMenuItemsView = LayoutInflater.from(getContext()).inflate(R.layout.pizza_menu_fragment,parent,false);
        }

        MenuItem current = getItem(position);

        TextView nameTextView = listMenuItemsView.findViewById(R.id.item_name_view);
        nameTextView.setText(current.getName());

        TextView ingredientsTextView = listMenuItemsView.findViewById(R.id.item_ingredients_view);
        ingredientsTextView.setText(current.getIngredients());

        TextView priceTextView = listMenuItemsView.findViewById(R.id.item_price_view);
        //String price = String.valueOf(current.getPrice());
        //priceTextView.setText(price);
        priceTextView.setText(current.getPrice());

        return listMenuItemsView;
    }
}
