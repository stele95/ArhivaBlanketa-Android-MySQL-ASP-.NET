package com.ab.ssd.ab_arhivablanketa;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.ssd.ab_arhivablanketa.Klase.Blanket;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class AddResenjeActivity extends AppCompatActivity {

    int RESULT_LOAD_IMAGE=333;
    int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE=444;

    Bitmap fullsize=null;
    Bitmap thumbnail=null;

    Blanket blanket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resenje);

        blanket=(Blanket) getIntent().getSerializableExtra("BlanketResenje");

        TextView podaci=(TextView) findViewById(R.id.txtBlanket);

        podaci.setText("Dodajete resenje za blanket: "+blanket.toString());

        Button dugmeSlikaj=(Button) findViewById(R.id.btnAddSlikaj);

        dugmeSlikaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);
            }
        });

        Button dugmeIzaberi=(Button)findViewById(R.id.btnAddIzaberi);

        dugmeIzaberi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,RESULT_LOAD_IMAGE);
            }
        });

        Button btnPostavi=(Button)findViewById(R.id.btnPostavi);

        btnPostavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
               postaviResenje();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK)
        {
            ImageView slika=(ImageView) findViewById(R.id.imgAdd);

            Cursor cursor = AddResenjeActivity.this.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{
                            MediaStore.Images.Media.DATA,
                            MediaStore.Images.Media.DATE_ADDED,
                            MediaStore.Images.ImageColumns.ORIENTATION
                    },
                    MediaStore.Images.Media.DATE_ADDED,
                    null,
                    "date_added DESC");


            if (cursor != null && cursor.moveToFirst()) {
                Uri uri = Uri.parse(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                String photoPath = uri.toString();
                cursor.close();
                if (photoPath != null) {
                    // Get the dimensions of the View
                    int targetW = slika.getWidth();
                    int targetH = slika.getHeight();

                    // Get the dimensions of the bitmap
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    bmOptions.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(photoPath, bmOptions);
                    int photoW = bmOptions.outWidth;
                    int photoH = bmOptions.outHeight;

                    // Determine how much to scale down the image
                    int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

                    // Decode the image file into a Bitmap sized to fill the View
                    bmOptions.inJustDecodeBounds = false;
                    bmOptions.inSampleSize = scaleFactor;
                    //bmOptions.inPurgeable = true;
                    fullsize = BitmapFactory.decodeFile(photoPath, bmOptions);
                }
            }
            if(fullsize==null) {
                Toast.makeText(AddResenjeActivity.this,"Slika nije pronadjena!",Toast.LENGTH_LONG).show();
                return;
            }
            //thumbnail
            thumbnail= (Bitmap) data.getExtras().get("data");
            slika.setImageBitmap(thumbnail);

        }

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            if (picturePath != null) {

                ImageView imageView = (ImageView) findViewById(R.id.imgAdd);

                // Get the dimensions of the View
                int targetW = imageView.getWidth();
                int targetH = imageView.getHeight();

                // Get the dimensions of the bitmap
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(picturePath, bmOptions);
                int photoW = bmOptions.outWidth;
                int photoH = bmOptions.outHeight;

                // Determine how much to scale down the image
                int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

                // Decode the image file into a Bitmap sized to fill the View
                bmOptions.inJustDecodeBounds = false;
                bmOptions.inSampleSize = scaleFactor;
                fullsize = BitmapFactory.decodeFile(picturePath, bmOptions);

                thumbnail = ThumbnailUtils.extractThumbnail(fullsize, 200, 200);


                imageView.setImageBitmap(thumbnail);

            }
        }
    }

    public String getStringFromBitmap(Bitmap bitmapPicture) {
         /*
         * This functions converts Bitmap picture to a string which can be
         * JSONified.
         * */
        final int COMPRESSION_QUALITY = 80;
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.JPEG, COMPRESSION_QUALITY,
                byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
        //return b;
    }


    void postaviResenje()
    {

        if(fullsize==null || thumbnail==null) {
            Toast.makeText(AddResenjeActivity.this,"Niste slikali/izabrali resenje!",Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject resenjeZaPost=new JSONObject();

        try{

            resenjeZaPost.put("Dodao",KorisnikInstance.getInstance().ID+"");
            resenjeZaPost.put("Blanket",blanket.ID+"");
            resenjeZaPost.put("ImageInBytes",getStringFromBitmap(fullsize));
            resenjeZaPost.put("ThumbnailInBytes",getStringFromBitmap(thumbnail));
        }
        catch (Exception e)
        {
            Toast.makeText(AddResenjeActivity.this,e.toString(),Toast.LENGTH_LONG).show();
        }

        String url = "http://160.99.38.140:2666/api/resenje";
        JsonObjectRequest putRequest = new JsonObjectRequest
                (Request.Method.POST, url, resenjeZaPost, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(AddResenjeActivity.this,"Uspesno ste postavili resenje!",Toast.LENGTH_LONG).show();
                        finish();

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddResenjeActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
                );

        RequestQueue queue = Volley.newRequestQueue(AddResenjeActivity.this);
        queue.add(putRequest);

    }
}
