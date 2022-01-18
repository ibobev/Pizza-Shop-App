package com.f91268.pizzashop.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.f91268.pizzashop.AccountDetailsActivity;
import com.f91268.pizzashop.R;
import com.f91268.pizzashop.adapters.DetailsRecyclerViewAdapter;
import com.f91268.pizzashop.data.DBHandler;
import com.f91268.pizzashop.models.Account;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CustomerDetailsFragment extends Fragment {

    RecyclerView detailRecyclerView;
    FloatingActionButton btnAddDetails;
    DBHandler dbHandler;
    ArrayList<Account> accountArrayList;
    DetailsRecyclerViewAdapter detailsRecyclerViewAdapter;

    public CustomerDetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.customer_details_fragment,container,false);

        detailRecyclerView = rootView.findViewById(R.id.rv_details_container);
        detailRecyclerView.setLayoutManager(new LinearLayoutManager(
                rootView.getContext()
        ));

        btnAddDetails = rootView.findViewById(R.id.btn_add_details);
        btnAddDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AccountDetailsActivity.class);
                startActivity(intent);
            }
        });

        dbHandler = new DBHandler(getContext());

        accountArrayList = dbHandler.readAccountDetails();

        detailsRecyclerViewAdapter = new DetailsRecyclerViewAdapter(accountArrayList, getContext());
        detailRecyclerView.setAdapter(detailsRecyclerViewAdapter);

        return rootView;
    }

}
