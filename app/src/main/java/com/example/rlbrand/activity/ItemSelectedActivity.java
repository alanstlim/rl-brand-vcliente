package com.example.rlbrand.activity;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.rlbrand.helper.Permission;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rlbrand.R;

import java.io.ByteArrayOutputStream;

public class ItemSelectedActivity extends AppCompatActivity {

    String itemCategory, itemSubcategory, itemUrlImage, itemPrice, itemSize;
    ImageView imageItem, imageShare;
    TextView textItemCategory, textItemSubCategory, textPrice, textSize;
    LinearLayout linearInfos;
    Uri imageUri;
    boolean tryPermission;
    Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_selected);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageItem           = findViewById(R.id.imageItem);
        imageShare          = findViewById(R.id.imageShare);
        textItemCategory    = findViewById(R.id.textItemCategory);
        textItemSubCategory = findViewById(R.id.textItemSubCategory);
        textPrice           = findViewById(R.id.textPrice);
        textSize            = findViewById(R.id.textSize);
        linearInfos         = findViewById(R.id.linearInfos);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            itemCategory = extras.getString("category");
            itemSubcategory = extras.getString("subCategory");
            itemUrlImage = extras.getString("urlImage");

            setTitle(itemCategory + " " + itemSubcategory);
            textItemCategory.setText(itemCategory);
            textItemSubCategory.setText(itemSubcategory);
            Uri itemSelected = Uri.parse (itemUrlImage);
            Glide.with(getApplicationContext()).load(itemSelected).into(imageItem);
            imageUri = Uri.parse (itemUrlImage);
        }

        imageItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ViewGroup.LayoutParams params = linearInfos.getLayoutParams();
                int finalHeight;
                int initialHeight = linearInfos.getHeight();


                if (initialHeight == 30){
                    finalHeight = 550;

                } else {
                    finalHeight = 30;
                }

                ValueAnimator valueAnimator = ValueAnimator.ofInt(initialHeight, finalHeight);
                valueAnimator.setDuration(700);

                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        params.height = (Integer) animation.getAnimatedValue();
                        linearInfos.requestLayout();
                    }
                });
                valueAnimator.start();
            }
        });

        imageShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Permission.validatePermission(MainActivity.permissions, activity, 1);

                if (tryPermission = true) {
                    shareImage();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int resultPermission : grantResults) {
            if (resultPermission == PackageManager.PERMISSION_DENIED) {
                alertValidatePermission();
               tryPermission = false;
            } else {
                tryPermission = true;
            }
        }
    }

    private void alertValidatePermission () {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para compartilhar é necessário aceitar as permissões");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void shareImage () {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/jpeg");
        intent.setType("text/plain");

        String text = itemCategory + " " + itemSubcategory + " da RL Brand";
        intent.putExtra(Intent.EXTRA_TEXT, text);

        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageItem.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, bytes);

        String path = MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,itemCategory + "_" + itemSubcategory, null);
        Uri uri = Uri.parse(path);

        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Compartilhar " + itemCategory + " " + itemSubcategory));

    }

}