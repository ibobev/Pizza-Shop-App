package com.f91268.pizzashop;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.f91268.pizzashop.adapters.PizzaMenuAdapter;
import com.f91268.pizzashop.adapters.ScreenSlideAdapter;
import com.f91268.pizzashop.models.MenuItem;
import com.f91268.pizzashop.utils.HttpHandler;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<MenuItem> arrayListMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayListMenu = new ArrayList<>();

        ViewPager2 viewPager2 = findViewById(R.id.pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        ScreenSlideAdapter adapter = new ScreenSlideAdapter(this);
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            if (position == 0) {
                tab.setText("Menu");
            } else if (position == 1) {
                tab.setText("My Details");
            } else if(position == 2){
                tab.setText("Favorites");
            }
        }).attach();

        if (!isConnected(MainActivity.this)) {
            showDialogNoConnection();
        } else {
            new RequestPizzaListJSON().execute();
        }

    }

    @Override
    public void onBackPressed() {
        finishAndRemoveTask();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                Intent intent = new Intent(this, CartActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    private boolean isConnected(MainActivity mainActivity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected())) {
            return true;
        }
        return false;
    }

    private void showDialogNoConnection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("No internet connection! Please connect to a network!")
                .setTitle("Connection failed")
                .setCancelable(false)
                .setPositiveButton("Refresh", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        recreate();
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAndRemoveTask();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private class RequestPizzaListJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            HttpHandler httpHandler = new HttpHandler();
            String resource = "https://my-json-server.typicode.com/xdrqa/mock-json/db";
            String result = httpHandler.makeServiceCall(resource);

            try {
                JSONObject object = new JSONObject(result);
                JSONArray menuList = object.getJSONArray("menu");

                for (int i = 0; i < menuList.length(); i++) {
                    JSONObject pizzaObject = menuList.getJSONObject(i);

                    int id = pizzaObject.getInt("id");
                    String name = pizzaObject.getString("name");
                    String ingredients = pizzaObject.getString("ingredients");
                    //double price = pizzaObject.getDouble("price");
                    String price = pizzaObject.getString("price");

                    MenuItem menuModel = new MenuItem();
                    menuModel.setId(id);
                    menuModel.setName(name);
                    menuModel.setIngredients(ingredients);
                    menuModel.setPrice(price);
                    arrayListMenu.add(menuModel);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            PizzaMenuAdapter adapter = new PizzaMenuAdapter(MainActivity.this, arrayListMenu);

            final ListView pizzaMenuList = findViewById(R.id.pizza_list);
            pizzaMenuList.setAdapter(adapter);

            pizzaMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, DisplayItemActivity.class);
                    intent.putExtra("item_name", arrayListMenu.get(position).getName());
                    intent.putExtra("item_ingredients", arrayListMenu.get(position).getIngredients());
                    intent.putExtra("item_price", arrayListMenu.get(position).getPrice());
                    startActivity(intent);
                }
            });

        }
    }


}