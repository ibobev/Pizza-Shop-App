package com.f91268.pizzashop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.f91268.pizzashop.adapters.CartRecyclerViewAdapter;
import com.f91268.pizzashop.data.DBHandler;
import com.f91268.pizzashop.models.Account;
import com.f91268.pizzashop.models.CartItem;

import java.util.ArrayList;
import java.util.Calendar;

public class CartActivity extends AppCompatActivity {

    CartRecyclerViewAdapter adapter;
    RecyclerView cartRecyclerView;
    ArrayList<CartItem> cartItems;
    DBHandler dbHandler;

    ImageButton btnOrder, btnDatePicker, btnTimePicker;
    TextView totalCostView, dateView, timeView;
    DatePickerDialog datePicker;
    TimePickerDialog timePicker;
    ArrayList<Account> accountDetails;
    double total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        dbHandler = new DBHandler(this);
        cartItems = dbHandler.readCart();
        accountDetails = dbHandler.readAccountDetails();

        cartRecyclerView = findViewById(R.id.rv_cart_container);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CartRecyclerViewAdapter(this, cartItems);
        cartRecyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(cartRecyclerView.getContext(),
                new LinearLayoutManager(this).getOrientation());
        cartRecyclerView.addItemDecoration(dividerItemDecoration);

        totalCostView = findViewById(R.id.total_cost_calc);
        total = dbHandler.calculateTotalCost();

        btnOrder = findViewById(R.id.btn_checkout);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dbHandler.isCartEmpty()) {
                    return;
                }
                if (dateView.getText().toString().isEmpty()) {
                    Toast.makeText(CartActivity.this, "Date is required!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (timeView.getText().toString().isEmpty()) {
                    Toast.makeText(CartActivity.this, "Time is required!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (dbHandler.isAccountPresent()) {
                    //System.out.println(cartItems);
                    total = dbHandler.calculateTotalCost();
                    showDialogOnCheckout();
                }
            }
        });

        dateView = findViewById(R.id.date_view);
        btnDatePicker = findViewById(R.id.btn_date);
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePicker = new DatePickerDialog(CartActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateView.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datePicker.show();
            }
        });

        timeView = findViewById(R.id.time_view);
        btnTimePicker = findViewById(R.id.btn_time);
        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                timePicker = new TimePickerDialog(CartActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        timeView.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, true);
                timePicker.show();
            }
        });


        if (total != 0) {
            String totalString = Double.toString(total);
            totalCostView.setText(totalString);
        } else {
            totalCostView.setText(R.string.empty_cart);
            btnOrder.setVisibility(View.GONE);
            btnDatePicker.setVisibility(View.GONE);
            btnTimePicker.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("time", timeView.getText().toString());
        outState.putString("date", dateView.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        timeView.setText(savedInstanceState.getString("time"));
        dateView.setText(savedInstanceState.getString("date"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Press the home icon to go back", Toast.LENGTH_SHORT).show();
    }

    private void showDialogOnCheckout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
        builder.setMessage("Are you ready with your order?")
                .setTitle("Checkout")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String orderDetails = prepareDetails();
                        System.out.println(orderDetails);

                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse("mailto:"));
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"bobev.ivan99@gmail.com"});
                        intent.putExtra(Intent.EXTRA_SUBJECT, "New order");
                        intent.putExtra(Intent.EXTRA_TEXT, orderDetails);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }

                        dbHandler.deleteAllItems();
                        dateView.setText("");
                        timeView.setText("");
                        recreate();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private String prepareDetails() {
        String message = "";

        for (CartItem cartItem : cartItems) {
            message += "Pizza : " + cartItem.getItemName() +
                    "\nExtra Toppings : " + cartItem.getItemExtraToppings() +
                    "\nQuantity : " + cartItem.getItemQuantity() +
                    "\nCurrent Pizza Total Cost : " + cartItem.getItemPrice() +
                    "\n------------------------------------\n";
        }

        for (Account account : accountDetails) {
            message += "Account : " + account.getFirstName() + " " + account.getLastName() +
                    "\nCity : " + account.getCity() +
                    "\nAddress : " + account.getAddress() +
                    "\n------------------------------------\n";
        }

        //String total = totalCostView.getText().toString();
        String totalString = Double.toString(total);
        String date = dateView.getText().toString();
        String time = timeView.getText().toString();

        message += "DATE : " + date + " ,TIME : " + time + "\nTOTAL : " + totalString;
        return message;
    }


}