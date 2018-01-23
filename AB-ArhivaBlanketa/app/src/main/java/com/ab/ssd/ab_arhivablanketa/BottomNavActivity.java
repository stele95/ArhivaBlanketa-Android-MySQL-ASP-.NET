package com.ab.ssd.ab_arhivablanketa;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.ssd.ab_arhivablanketa.Klase.Blanket;
import com.ab.ssd.ab_arhivablanketa.Klase.Fakultet;
import com.ab.ssd.ab_arhivablanketa.Klase.GodinaStudija;
import com.ab.ssd.ab_arhivablanketa.Klase.Korisnik;
import com.ab.ssd.ab_arhivablanketa.Klase.Predmet;
import com.ab.ssd.ab_arhivablanketa.Klase.Rok;
import com.ab.ssd.ab_arhivablanketa.Klase.Smer;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BottomNavActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Button mDugme;
    private FrameLayout mFragmenti;
    private View mProgressView;
    private View mainView;
    public static final int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE = 1777;
    public static final int RESULT_LOAD_IMAGE = 1234;

    //public CustomListAdapter adapter;
    public List<Blanket> blanketList=new ArrayList<>();
    public List<Blanket> blanketSearch=new ArrayList<>();
    public boolean search;


    Bitmap fullsize=null;
    Bitmap thumbnail=null;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mFragmenti.setVisibility(View.VISIBLE);

                    mTextMessage.setText("Pocetna");
                    mDugme.setText("Trazi");
                    mDugme.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            changeToSearch();
                        }
                    });
                    mDugme.setVisibility(View.VISIBLE);
                    selectedFragment = HomeFragment.newInstance();

                    mDugme.setVisibility(View.VISIBLE);

                    break;
                case R.id.navigation_add:
                    mFragmenti.setVisibility(View.VISIBLE);
                    mTextMessage.setText("Dodaj");
                    mDugme.setText("Postavi");
                    mDugme.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            showProgress(true);
                            postaviBlanket();
                        }
                    });
                    mDugme.setVisibility(View.VISIBLE);
                    //mDugme.setVisibility(View.INVISIBLE);
                    if(KorisnikInstance.getInstance()==null)
                    {
                        mTextMessage.setText("Prijavite se!!");
                        //mDugme.setVisibility(View.VISIBLE);
                        mDugme.setText("Prijavi se");
                        mDugme.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                odjava();
                            }
                        });
                        mFragmenti.setVisibility(View.INVISIBLE);
                    }

                    selectedFragment = AddFragment.newInstance();;

                    break;
                case R.id.navigation_User:
                    mFragmenti.setVisibility(View.VISIBLE);
                    mDugme.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            odjava();
                        }
                    });
                    if(KorisnikInstance.getInstance()==null)
                    {
                        mTextMessage.setText("Prijavite se!!");
                        //mDugme.setVisibility(View.VISIBLE);
                        mDugme.setText("Prijavi se");
                        mFragmenti.setVisibility(View.INVISIBLE);

                    }
                    if(KorisnikInstance.getInstance()!=null) {
                        mTextMessage.setText("Podaci o korisniku");
                        mDugme.setText("Odjavi se");

                    }

                    selectedFragment = UserDataFragment.newInstance();

                    mDugme.setVisibility(View.VISIBLE);

                    break;

            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmenti, selectedFragment);
            transaction.commit();

            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);


        //adapter = new CustomListAdapter(BottomNavActivity.this, blanketList);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

         //Manually displaying the first fragment - one time only
         FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
         transaction.replace(R.id.fragmenti, HomeFragment.newInstance());
         transaction.commit();

        mFragmenti=(FrameLayout) findViewById(R.id.fragmenti);
        mFragmenti.setVisibility(View.VISIBLE);
        mainView=findViewById(R.id.maincontainer);
        mProgressView=findViewById(R.id.add_progress);
         //Used to select an item programmatically
         //bottomNavigationView.getMenu().getItem(2).setChecked(true);*/


        mDugme=(Button) findViewById(R.id.btnlogout);
        mDugme.setVisibility(View.VISIBLE);
        mDugme.setText("Trazi");
        mDugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                changeToSearch();
            }
        });


    }

    void logOut(View view)
    {
       odjava();
    }

    void odjava(){
        KorisnikInstance.setInstance(null);
        Intent intent=new Intent(BottomNavActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }


    public void startBlanketInfo(Blanket blanket, boolean put)
    {
        Intent intent = new Intent(BottomNavActivity.this, BlanketInfoActivity.class);
        //intent.putExtra("BlanketID",blanket.ID);
        if(put) {
            boolean nadjen=false;
            for (Blanket b:blanketList) {
                if(b.ID==blanket.ID)
                    nadjen=true;
            }
            if(!nadjen)
                blanketList.add(blanket);
            
        }
        Blanket bl=new Blanket(blanket);
        bl.thumbnail=null;
        bl.slika=null;
        intent.putExtra("BlanketSlanje",bl);
        //startActivityForResult(intent,HomeFragment.BLANKET_INFO_REQ);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Check that request code matches ours:
        if (requestCode == CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK)
        {
            ImageView slika=(ImageView) findViewById(R.id.imgAdd);

            Cursor cursor = BottomNavActivity.this.getContentResolver().query(
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
                Toast.makeText(BottomNavActivity.this,"Slika nije pronadjena!",Toast.LENGTH_LONG).show();
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



    void postaviBlanket()
    {
        Spinner spinpredmet=(Spinner)findViewById(R.id.spinnerPredmet);
        Spinner spinrok=(Spinner)findViewById(R.id.spinnerRok);
        Spinner spingodina=(Spinner)findViewById(R.id.spinnerGodina);
        CheckBox cbpismeni=(CheckBox)findViewById(R.id.cbPismeni);
        CheckBox cbusmeni=(CheckBox)findViewById(R.id.cbUsmeni);

        Predmet predmet=(Predmet) spinpredmet.getSelectedItem();
        Rok rok=(Rok) spinrok.getSelectedItem();
        String godina=(String) spingodina.getSelectedItem();


        if(predmet.ID==-1 || rok.ID==-1 || godina.equals("[Izaberite]"))
        {
            Toast.makeText(BottomNavActivity.this, "Niste uneli sve podatke!", Toast.LENGTH_SHORT).show();
            showProgress(false);
            return;
        }

        if(!cbpismeni.isChecked() && !cbusmeni.isChecked())
        {
            Toast.makeText(BottomNavActivity.this,"Morate izabrati da li je pismeni ili usmeni!",Toast.LENGTH_SHORT).show();
            showProgress(false);
            return;
        }

        if(fullsize==null) {
            Toast.makeText(BottomNavActivity.this,"Niste slikali/izabrali blanket!",Toast.LENGTH_SHORT).show();
            showProgress(false);
            return;
        }

        JSONObject blanketZaPost=new JSONObject();
        JSONObject rokJSON=new JSONObject();
        JSONObject predmetJSON=new JSONObject();
        JSONObject smerPredmeta=new JSONObject();
        JSONObject godStPredmeta=new JSONObject();
        JSONObject faksSmera=new JSONObject();
        JSONObject admin=new JSONObject();

        try{

            rokJSON.put("IdRok",rok.ID);
            rokJSON.put("Naziv",rok.naziv);

            faksSmera.put("IdFakultet",predmet.smer.fakultet.ID);
            faksSmera.put("Naziv",predmet.smer.fakultet.naziv);
            faksSmera.put("Grad",predmet.smer.fakultet.grad);

            smerPredmeta.put("IdSmer",predmet.smer.ID);
            smerPredmeta.put("Naziv", predmet.smer.naziv);
            smerPredmeta.put("Fakultet",faksSmera);

            godStPredmeta.put("IdGodina",predmet.godinaStudija.ID);
            godStPredmeta.put("Godina",predmet.godinaStudija.godina);


            predmetJSON.put("IdPredmet",predmet.ID);
            predmetJSON.put("Naziv",predmet.naziv);
            predmetJSON.put("Smer",smerPredmeta);
            predmetJSON.put("Godina",godStPredmeta);

            JSONObject faks=new JSONObject();
            faks.put("IdFakultet",1);
            faks.put("Naziv","Elektronski fakultet");
            faks.put("Grad","Nis");

            admin.put("IdKorisnik",1);
            admin.put("Ime","Stefan");
            admin.put("Prezime","Dimitrijevic");
            admin.put("BrIndeksa", 15071);
            admin.put("Username","stele95");
            admin.put("Password","pass1");
            admin.put("Email","stefan.stele@elfak.rs");
            admin.put("Fakultet", faks);
            admin.put("Admin",true);


            blanketZaPost.put("Dodao",KorisnikInstance.getInstance().korisnikJSON);
            blanketZaPost.put("Rok",rokJSON);
            blanketZaPost.put("Predmet",predmetJSON);
            blanketZaPost.put("Godina",Integer.parseInt(godina));
            blanketZaPost.put("Odobren",false);
            blanketZaPost.put("Odobrio",admin);
            blanketZaPost.put("Pismeni",cbpismeni.isChecked());
            blanketZaPost.put("Usmeni",cbusmeni.isChecked());
            blanketZaPost.put("ImageInBytes",getStringFromBitmap(fullsize));
            blanketZaPost.put("ThumbnailInBytes",getStringFromBitmap(thumbnail));
        }
        catch (Exception e)
        {
            Toast.makeText(BottomNavActivity.this,e.toString(),Toast.LENGTH_LONG).show();
        }



        String url = "http://160.99.38.140:2666/api/blanket";
        JsonObjectRequest putRequest = new JsonObjectRequest
                (Request.Method.POST, url, blanketZaPost, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(BottomNavActivity.this,"Uspesno ste postavili blanket!",Toast.LENGTH_LONG).show();
                        fullsize=null;
                        thumbnail=null;
                        BottomNavigationView bottomNavigationView;
                        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
                        bottomNavigationView.setSelectedItemId(R.id.navigation_add);
                        showProgress(false);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BottomNavActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                        showProgress(false);
                    }
                }
                );

        RequestQueue queue = Volley.newRequestQueue(BottomNavActivity.this);
        queue.add(putRequest);
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


    public Bitmap getBitmapFromString(String jsonString) {
        /*
        * This Function converts the String back to Bitmap
        * */
        byte[] decodedString = Base64.decode(jsonString, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mainView.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
            mainView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mainView.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
            mainView.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
        }
    }

    void changeToSearch()
    {
        mFragmenti.setVisibility(View.VISIBLE);
        mTextMessage.setText("Trazi");
        mDugme.setText("Pocetna");
        mDugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                changeToHome();
            }
        });


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmenti, SearchFragment.newInstance());
        transaction.commit();


    }

    void changeToHome()
    {
        mFragmenti.setVisibility(View.VISIBLE);
        mTextMessage.setText("Pocetna");
        mDugme.setText("Trazi");
        mDugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                changeToSearch();
            }
        });


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmenti, HomeFragment.newInstance());
        transaction.commit();
    }

    public void setSearch()
    {
        mFragmenti.setVisibility(View.VISIBLE);
        mTextMessage.setText("Rezultati pretrage");
        /**mDugme.setText("Trazi");
        mDugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                changeToSearch();
            }
        });*/

        search=true;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmenti, HomeFragment.newInstance());
        transaction.commit();
    }

    public void setButton(boolean b)
    {
        mDugme.setEnabled(b);
    }

    public void startSlikaj()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, BottomNavActivity.CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);
    }

    public void startIzaberi()
    {
        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }



}
