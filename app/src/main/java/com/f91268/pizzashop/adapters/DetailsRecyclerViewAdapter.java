package com.f91268.pizzashop.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.f91268.pizzashop.R;
import com.f91268.pizzashop.UpdateAccountActivity;
import com.f91268.pizzashop.models.Account;

import java.util.ArrayList;

public class DetailsRecyclerViewAdapter extends RecyclerView.Adapter<DetailsRecyclerViewAdapter.DetailsViewHolder> {


    private final ArrayList<Account> dataList;
    private Context context;

    public DetailsRecyclerViewAdapter(ArrayList<Account> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_details_row_holder,parent,false);
        return new DetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder holder, int position) {
        Account account = dataList.get(position);

        String names = account.getFirstName() + " " + account.getLastName();
        String accountId = String.valueOf(account.getId());

        holder.getAccountNamesView().setText(names);
        holder.getAccountCityView().setText(account.getCity());
        holder.getAccountAddressView().setText(account.getAddress());

        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateAccountActivity.class);
                intent.putExtra("account_id", accountId);
                intent.putExtra("account_first_name", account.getFirstName());
                intent.putExtra("account_last_name", account.getLastName());
                intent.putExtra("account_address", account.getAddress());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder {

        TextView accountNamesView, accountCityView, accountAddressView;
        LinearLayout rowLayout;

        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            accountNamesView = itemView.findViewById(R.id.account_names);
            accountCityView = itemView.findViewById(R.id.account_city);
            accountAddressView = itemView.findViewById(R.id.account_address);
            rowLayout = itemView.findViewById(R.id.row_linear_layout);
        }

        public TextView getAccountNamesView() {
            return accountNamesView;
        }

        public TextView getAccountCityView() {
            return accountCityView;
        }

        public TextView getAccountAddressView() {
            return accountAddressView;
        }
    }
}
