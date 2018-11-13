package com.example.edu.myimageviewprovider_1113;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int LOAD_IMAGE =101;
    Button fromGalleryButton;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fromGalleryButton = (Button)findViewById(R.id.fromGalleryButton);
        fromGalleryButton.setOnClickListener(this);
        imageView = (ImageView)findViewById(R.id.imagesViewFromGallery);
    }
//
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,LOAD_IMAGE);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(data !=null){
            Uri selectedImage = data.getData();
            InputStream inputStream = null;
            try {
                inputStream = this.getContentResolver().openInputStream(selectedImage);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
    }
}
