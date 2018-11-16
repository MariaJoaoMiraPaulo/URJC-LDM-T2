package com.example.mariajoaomirapaulo.myshoppinglist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewProductActivity extends AppCompatActivity{

    private ImageButton deleteProduct, updateProduct, takePicture;
    private EditText newProductName;
    private ImageView productPicture;

    AdminSQLiteOpenHelper databaseHelper;

    private String productName;
    private String photoPath;
    private int productId;

    SoundPool soundPool;
    int trashSound;

    static final int CAPTURE_IMAGE_REQUEST = 1;

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
        trashSound = soundPool.load(this,R.raw.trash, 1);

        Intent receivedIntent = getIntent();

        productId = receivedIntent.getIntExtra("id", -1);
        productName = receivedIntent.getStringExtra("name");
        photoPath = receivedIntent.getStringExtra("photoPath");

        newProductName.setText(productName);

        if(!photoPath.equals("")){
            Bitmap imageBitmap = BitmapFactory.decodeFile(photoPath);
            productPicture.setImageBitmap(imageBitmap);
        }

        updateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = newProductName.getText().toString();

                if (newName.equals("")) {
                    toastHelper("The new product name can't be empty!");
                } else {
                    databaseHelper.updateProductName(productName, productId, newName);
                    toastHelper("Product name updated successfully!");
                }

                if(!photoPath.equals("")){
                    databaseHelper.updateProductPhoto(productName,productId,photoPath);
                    toastHelper("Product photo updated successfully!");
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
               captureImage();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAPTURE_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            productPicture.setImageBitmap(imageBitmap);
            try {
                createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        photoPath = image.getAbsolutePath();
    }


    public void captureImage(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAPTURE_IMAGE_REQUEST);
        }
    }


    private void toastHelper(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
