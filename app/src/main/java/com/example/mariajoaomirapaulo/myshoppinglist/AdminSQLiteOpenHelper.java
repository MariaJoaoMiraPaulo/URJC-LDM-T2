package com.example.mariajoaomirapaulo.myshoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * The SQLite Open Helper class
 */
public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    // Database Name
    private static final String DATABASE_NAME = "myShoppingList";

    /**
     * The database constructor.
     *
     * @param context the context
     */
    public AdminSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /**
     * Creates the needed tables for the database.
     * The current shopping list products ( table products ).
     * The past shopping lists (table history), with a string called products with all the products comma separated.
     *
     * @param database the database
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE products(id integer primary key autoincrement, name text, photo BLOB)");
        database.execSQL("CREATE TABLE history(id integer primary key autoincrement, products text, date text)");
    }

    /**
     * On database upgrade.
     *
     * @param db         the database
     * @param oldVersion old version
     * @param newVersion new version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Add product to the products table.
     *
     * @param product the product
     * @return success or failure
     */
    public boolean addProduct(ProductItem product) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", product.getName());

        // All the created products don't have a photo when they are created, so a default photo is displayed.
        contentValues.put("photo", product.getPhoto());

        long result = database.insert("products", null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks if there is a product on the list with the same name.
     *
     * @param productName the product name
     * @return if there is or not
     */
    public boolean productWithSameName(String productName) {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM products WHERE name = '" + productName + "'";
        Cursor product = database.rawQuery(query, null);

        // If there is no product with the same name
        if (product.getCount() == 0) {
            return false;
        }

        return true;
    }

    /**
     * Inserts a new shopping list to the history table, with the products
     * comma separated and the date when the shopping list was concluded
     *
     * @param products the products comma separated
     * @return success or failure
     */
    public boolean addList(String products) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();


        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("products", products);
        contentValues.put("date", dateFormat.format(date));


        long result = database.insert("history", null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Gets a past shopping list by date.
     *
     * @param date the date
     * @return the past shopping list
     */
    public Cursor getListByDate(String date) {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM history WHERE date = '" + date + "'";
        Cursor list = database.rawQuery(query, null);

        return list;
    }

    /**
     * Gets all the existent products in the products table (current shopping list).
     *
     * @return the existent products
     */
    public Cursor getAllProducts() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM products";
        Cursor products = database.rawQuery(query, null);
        return products;
    }

    /**
     * Gets all the past shopping lists.
     *
     * @return the past shopping lists
     */
    public Cursor getAllLists() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM history";
        Cursor lists = database.rawQuery(query, null);
        return lists;
    }

    /**
     * Gets a product Id by its name.
     *
     * @param productName the product name
     * @return the product Id
     */
    public Cursor getProductId(String productName) {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT id FROM products WHERE name = '" + productName + "'";
        Cursor product = database.rawQuery(query, null);

        return product;
    }

    /**
     * Updates the product name.
     *
     * @param productName    the current product name
     * @param id             the product Id
     * @param newProductName the new product name
     */
    public void updateProductName(String productName, int id, String newProductName) {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "UPDATE products SET name = '" + newProductName + "' WHERE id = '"
                + id + "' AND name = '" + productName + "'";
        database.execSQL(query);

    }

    /**
     * Deletes a product.
     *
     * @param productName the product name
     * @param id          the product id
     */
    public void deleteProduct(String productName, int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "DELETE FROM products WHERE id = '"
                + id + "' AND name = '" + productName + "'";
        database.execSQL(query);

    }

    /**
     * Creates a new products table to have a new shopping list.
     */
    public void createCurrentListTable() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("CREATE TABLE products(id integer primary key autoincrement, name text, photo text)");
    }

    /**
     * Drops the current shoopping list ( it was concluded )
     */
    public void dropCurrentListTable() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DROP TABLE IF EXISTS 'products'");
        createCurrentListTable();
    }

}
