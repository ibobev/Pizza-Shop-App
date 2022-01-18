package com.f91268.pizzashop.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.f91268.pizzashop.models.Account;
import com.f91268.pizzashop.models.CartItem;
import com.f91268.pizzashop.models.FavoritePizza;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PizzaShop.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_ACCOUNT = "account";
    private static final String TABLE_FAVORITES = "favorites";
    private static final String TABLE_ITEMS = "items";

    private static final String KEY_ID = "_id";

    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_CITY = "city";
    private static final String COLUMN_ADDRESS = "address";

    private static final String COLUMN_PIZZA_NAME = "pizza_name";
    private static final String COLUMN_INGREDIENTS = "ingredients";

    private static final String COLUMN_EXTRA_TOPPINGS = "extra_toppings";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_QUANTITY = "quantity";

    private Context context;

    private static final String CREATE_TABLE_ACCOUNT =
            "CREATE TABLE " + TABLE_ACCOUNT +
                    " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_FIRST_NAME + " TEXT, " +
                    COLUMN_LAST_NAME + " TEXT, " +
                    COLUMN_CITY + " TEXT, " +
                    COLUMN_ADDRESS + " TEXT);";

    private static final String CREATE_TABLE_FAVORITES =
            "CREATE TABLE " + TABLE_FAVORITES +
                    " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PIZZA_NAME + " TEXT, " +
                    COLUMN_INGREDIENTS + " TEXT);";

    private static final String CREATE_TABLE_ITEMS =
            "CREATE TABLE " + TABLE_ITEMS +
                    " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PIZZA_NAME + " TEXT, " +
                    COLUMN_INGREDIENTS + " TEXT, " +
                    COLUMN_EXTRA_TOPPINGS + " TEXT, " +
                    COLUMN_PRICE + " REAL, " +
                    COLUMN_QUANTITY + " INTEGER);";


    public DBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ACCOUNT);
        db.execSQL(CREATE_TABLE_FAVORITES);
        db.execSQL(CREATE_TABLE_ITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }

    public void addItemToCart(String name, String ingredients, String toppings, double price, int quantity){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PIZZA_NAME, name);
        contentValues.put(COLUMN_INGREDIENTS, ingredients);
        contentValues.put(COLUMN_EXTRA_TOPPINGS, toppings);
        contentValues.put(COLUMN_PRICE, price);
        contentValues.put(COLUMN_QUANTITY, quantity);

        long result = db.insert(TABLE_ITEMS, null, contentValues);

        if (result == -1) {
            Toast.makeText(context, "Adding item to cart failed!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Pizza added to cart!", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<CartItem> readCart() {
        String query = "SELECT * FROM " + TABLE_ITEMS;
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<CartItem> cartItems = new ArrayList<>();

        if(db != null) {
            Cursor cursor = db.rawQuery(query,null);

            if(cursor.moveToFirst()) {
                do{
                    cartItems.add(new CartItem(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getDouble(4),
                            cursor.getInt(5)
                    ));
                }while(cursor.moveToNext());
            }
            cursor.close();
        }

        return cartItems;
    }

    public void deleteCartItem(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_ITEMS, "_id=?", new String[]{id});

        if(result == -1){
            Toast.makeText(context, "Failed to remove item!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Item removed successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    public void deleteAllItems() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ITEMS);
    }

    public boolean isCartEmpty(){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT count(*) FROM " + TABLE_ITEMS;
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);

        if(count == 0) {
            Toast.makeText(context, "No items in cart!", Toast.LENGTH_SHORT).show();
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    @SuppressLint("Range")
    public double calculateTotalCost() {
        double total = 0;
        String query = "SELECT (SUM(price)) AS total FROM " + TABLE_ITEMS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()) {
             total = cursor.getDouble(cursor.getColumnIndex("total"));
        }

        cursor.close();

        return total;
    }

    public void addPizzaToFavorites(String pizzaName, String ingredients) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_FAVORITES + " WHERE pizza_name = " + "\"" + pizzaName + "\"";
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.getCount() > 0) {
            Toast.makeText(context, "Selected pizza already exists in favorites!", Toast.LENGTH_SHORT).show();
        }else {

            ContentValues contentValues = new ContentValues();

            contentValues.put(COLUMN_PIZZA_NAME, pizzaName);
            contentValues.put(COLUMN_INGREDIENTS, ingredients);

            long result = db.insert(TABLE_FAVORITES, null, contentValues);

            if (result == -1) {
                Toast.makeText(context, "Add to favorites operation failed!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Pizza added to favorites!", Toast.LENGTH_SHORT).show();
            }
        }
        cursor.close();

    }

    public ArrayList<FavoritePizza> readFavorite() {

        String query = "SELECT * FROM " + TABLE_FAVORITES;
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<FavoritePizza> favoritePizzas = new ArrayList<>();

        if(db != null) {
            Cursor cursor = db.rawQuery(query,null);

            if(cursor.moveToFirst()) {
                do{
                    favoritePizzas.add(new FavoritePizza(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2)
                    ));
                }while(cursor.moveToNext());
            }
            cursor.close();
        }

        return favoritePizzas;
    }

    public void addAccountDetails(String firstName, String lastName, String city, String address) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT count(*) FROM " + TABLE_ACCOUNT;
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);

        if(count >= 1) {
            Toast.makeText(context, "An account already exists!", Toast.LENGTH_SHORT).show();
        }else {
            ContentValues contentValues = new ContentValues();

            contentValues.put(COLUMN_FIRST_NAME, firstName);
            contentValues.put(COLUMN_LAST_NAME, lastName);
            contentValues.put(COLUMN_CITY, city);
            contentValues.put(COLUMN_ADDRESS, address);

            long result = db.insert(TABLE_ACCOUNT, null, contentValues);

            if(result == -1){
                Toast.makeText(context, "Account creation failed!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Account details added successfully!", Toast.LENGTH_LONG).show();
            }
        }

        cursor.close();

    }

    public boolean isAccountPresent(){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT count(*) FROM " + TABLE_ACCOUNT;
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);

        if(count == 0) {
            Toast.makeText(context, "Please create an account!", Toast.LENGTH_SHORT).show();
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public void updateAccountDetails(String accountId, String firstName, String lastName, String city, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FIRST_NAME, firstName);
        contentValues.put(COLUMN_LAST_NAME, lastName);
        contentValues.put(COLUMN_CITY, city);
        contentValues.put(COLUMN_ADDRESS, address);

        long result = db.update(TABLE_ACCOUNT, contentValues, "_id=?", new String[] {accountId});

        if(result == -1){
            Toast.makeText(context,"Update failed!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context,"Account details have been updated!", Toast.LENGTH_SHORT).show();
        }

    }

    public void deleteAccount(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_ACCOUNT, "_id=?", new String[]{id});

        if(result == -1){
            Toast.makeText(context, "Failed to delete account!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Account deleted successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    public ArrayList<Account> readAccountDetails() {
        String query = "SELECT * FROM " + TABLE_ACCOUNT;
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Account> accounts = new ArrayList<>();

        if(db != null) {
           Cursor cursor = db.rawQuery(query,null);

           if(cursor.moveToFirst()) {
               do{
                   accounts.add(new Account(
                           cursor.getInt(0),
                           cursor.getString(1),
                           cursor.getString(2),
                           cursor.getString(3),
                           cursor.getString(4)
                           ));
               }while(cursor.moveToNext());
           }
           cursor.close();
        }

        return accounts;
    }

}
