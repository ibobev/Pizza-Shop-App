package com.f91268.pizzashop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.f91268.pizzashop.R;
import com.f91268.pizzashop.models.FavoritePizza;

import java.util.ArrayList;

public class FavoriteRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteRecyclerViewAdapter.FavoriteViewHolder>{

    private final ArrayList<FavoritePizza> favoritePizzaArrayList;
    private Context context;

    public FavoriteRecyclerViewAdapter(ArrayList<FavoritePizza> favoritePizzas, Context context) {
        this.favoritePizzaArrayList = favoritePizzas;
        this.context = context;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_pizza_row_holder,parent,false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        FavoritePizza favoritePizza = favoritePizzaArrayList.get(position);

        // get id

        holder.getPizzaNameView().setText(favoritePizza.getPizzaName());
        holder.getIngredientsView().setText(favoritePizza.getIngredients());

        // get row layout
        // onclick -> delete item from favorites

    }

    @Override
    public int getItemCount() {
        return favoritePizzaArrayList.size();
    }


    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView pizzaNameView, ingredientsView;
        //LinearLayout rowPizzaLayout;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            pizzaNameView = itemView.findViewById(R.id.pizza_name);
            ingredientsView = itemView.findViewById(R.id.pizza_ingredients);
            //rowPizzaLayout = itemView.findViewById(R.id.row_pizza_linear_layout);
        }

        public TextView getPizzaNameView() {
            return pizzaNameView;
        }

        public TextView getIngredientsView() {
            return ingredientsView;
        }

       /* public LinearLayout getRowPizzaLayout() {
            return rowPizzaLayout;
        }*/
    }
}
