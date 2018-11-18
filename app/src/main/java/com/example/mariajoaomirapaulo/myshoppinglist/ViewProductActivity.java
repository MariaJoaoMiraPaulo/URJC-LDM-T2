package com.example.mariajoaomirapaulo.myshoppinglist;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * View product activity class
 */
public class ViewProductActivity extends AppCompatActivity {

    private ImageButton deleteProduct, updateProduct, takePicture;
    private EditText newProductName;
    private ImageView productPicture;

    AdminSQLiteOpenHelper databaseHelper;

    private String productName;
    private int productId;

    SoundPool soundPool;
    int trashSound;

    static final int CAPTURE_IMAGE_REQUEST = 1;

    /**
     * Creates the listeners for deleting the product, editing and taking picture.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_product_layout);

        updateProduct = (ImageButton) findViewById(R.id.updateProduct);
        deleteProduct = (ImageButton) findViewById(R.id.deleteProduct);
        takePicture = (ImageButton) findViewById(R.id.takePicture);
        newProductName = (EditText) findViewById(R.id.newProductName);
        productPicture = (ImageView) findViewById(R.id.imageProduct);
        databaseHelper = new AdminSQLiteOpenHelper(this);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        trashSound = soundPool.load(this, R.raw.trash, 1);

        Intent receivedIntent = getIntent();

        productId = receivedIntent.getIntExtra("id", -1);
        productName = receivedIntent.getStringExtra("name");

        newProductName.setText(productName);


        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        loadImageFromStorage(directory.getAbsolutePath());


        updateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = newProductName.getText().toString();

                if (newName.equals("")) {
                    toastHelper("The new product name can't be empty!");
                } else {
                    databaseHelper.updateProductName(productName, productId, newName);
                    if (productPicture.getDrawable() != null) {
                        saveToInternalStorage(((BitmapDrawable) productPicture.getDrawable()).getBitmap());
                        toastHelper("Product name and photo updated!");
                    } else {
                        toastHelper("Product name updated!");
                    }
                }
            }
        });

        deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.deleteProduct(productName, productId);
                toastHelper("Product deleted!");
                soundPool.play(trashSound, 1, 3, 1, 0, 0);
                Intent shoppingListIntent = new Intent(ViewProductActivity.this, ShoppingListActivity.class);
                startActivity(shoppingListIntent);
            }
        });

        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                captureImage();
            }
        });
    }

    /**
     * On activity result after taking a picture sets the image in the image view.
     *
     * @param requestCode the request code received
     * @param resultCode  the result code received
     * @param data        the data received
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAPTURE_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            productPicture.setImageBitmap(imgBitmap);

        }
    }


    /**
     * Toast helper do show message.
     *
     * @param message the message
     */
    private void toastHelper(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Saves an image to the internal storage of the mobile phone.
     *
     * @param bitmapImage the image as bitmap
     * @return the path where the picture was saved
     */
    private String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File myPath = new File(directory, productName + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    /**
     * Loads the image from the internal storage.
     *
     * @param path the image path
     */
    private void loadImageFromStorage(String path) {
        try {
            File f = new File(path, productName + ".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            productPicture.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the camera to take a picture
     */
    public void captureImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAPTURE_IMAGE_REQUEST);
        }
    }

}
