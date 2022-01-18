package com.f91268.pizzashop.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.f91268.pizzashop.R;
import com.f91268.pizzashop.adapters.FavoriteRecyclerViewAdapter;
import com.f91268.pizzashop.data.DBHandler;
import com.f91268.pizzashop.models.FavoritePizza;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    RecyclerView favoriteRecyclerView;
    ArrayList<FavoritePizza> favoritePizzaArrayList;
    FavoriteRecyclerViewAdapter favoriteRecyclerViewAdapter;
    DBHandler dbHandler;

    public FavoriteFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_favorite,container,false);

        favoriteRecyclerView = rootView.findViewById(R.id.rv_favorite_container);
        favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(
                rootView.getContext()
        ));

        dbHandler = new DBHandler(getContext());
        favoritePizzaArrayList = dbHandler.readFavorite();

        favoriteRecyclerViewAdapter = new FavoriteRecyclerViewAdapter(favoritePizzaArrayList, getContext());
        favoriteRecyclerView.setAdapter(favoriteRecyclerViewAdapter);

        return rootView;
    }
}
