package com.example.edu.myimageviewprovider_1113;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.provider.MediaStore;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int LOAD_IMAGE =101;//이미지
    int IMAGE_CAPTURE = 102;//카메라
    Button fromGalleryButton, imageCaptureButton;//이미지버튼, 카메라버튼
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fromGalleryButton = (Button)findViewById(R.id.fromGalleryButton);
        fromGalleryButton.setOnClickListener(this);
        imageCaptureButton = (Button)findViewById(R.id.imageCaptureButton);
        imageCaptureButton.setOnClickListener(this);
        imageView = (ImageView)findViewById(R.id.imagesViewFromGallery);
    }
//
    @Override
    public void onClick(View v) {
//        암묵적intent에는 Action, data, filter가 필요
        Intent intent = new Intent();//intend

        switch (v.getId()){
            case R.id.fromGalleryButton:
                intent.setAction(Intent.ACTION_GET_CONTENT);//action-> setAction(intent, ACTION_GET_CONTENT)
                intent.setType("image/*");//data-->setType("")
                startActivityForResult(intent,LOAD_IMAGE);//filter->startActivityForResult(intent,LOAT_IMAGE)
                break;

            case R.id.imageCaptureButton:
                if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, IMAGE_CAPTURE);
                }
                break;
        }


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == LOAD_IMAGE && resultCode == RESULT_OK ) {
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


        if(requestCode == IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(bitmap);
        }
    }
}
