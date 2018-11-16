package com.example.mariajoaomirapaulo.myshoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Optional;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    // Database Name
    private static final String DATABASE_NAME = "myShoppingList";

    public AdminSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE products(id integer primary key autoincrement, name text, photo text)");
        database.execSQL("CREATE TABLE history(id integer primary key autoincrement, products text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addProduct(ProductItem product) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", product.getName());
        contentValues.put("photo", product.getPhoto());

        long result = database.insert("products", null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addList(String products){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("products", products);

        long result = database.insert("history", null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllProducts() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM products";
        Cursor data = database.rawQuery(query, null);
        return data;
    }

    public Cursor getAllLists(){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM history";
        Cursor data = database.rawQuery(query, null);
        return data;
    }

    public Cursor getProductId(String productName) {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT id FROM products WHERE name = '" + productName + "'";
        Cursor product = database.rawQuery(query, null);

        return product;
    }

    public Cursor getProductPhoto(String productName){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT photo FROM products WHERE name = '" + productName + "'";
        Cursor product = database.rawQuery(query, null);

        return product;
    }

    public void updateProductName(String productName, int id, String newProductName) {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "UPDATE products SET name = '" + newProductName + "' WHERE id = '"
                        + id + "' AND name = '" + productName + "'";
        database.execSQL(query);

    }

    public void updateProductPhoto(String productName, int id, String photoPath){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "UPDATE products SET photo = '" + photoPath + "' WHERE id = '"
                + id + "' AND name = '" + productName + "'";
        database.execSQL(query);
    }

    public void deleteProduct(String productName, int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "DELETE FROM products WHERE id = '"
                + id + "' AND name = '" + productName + "'";
        database.execSQL(query);

    }

    public void createCurrentListTable(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("CREATE TABLE products(id integer primary key autoincrement, name text, photo text)");
    }

    public void dropCurrentListTable(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DROP TABLE IF EXISTS 'products'");
        createCurrentListTable();
    }

}
