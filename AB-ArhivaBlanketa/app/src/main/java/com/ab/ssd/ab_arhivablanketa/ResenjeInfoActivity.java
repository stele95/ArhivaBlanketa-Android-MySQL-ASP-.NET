package com.ab.ssd.ab_arhivablanketa;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.ssd.ab_arhivablanketa.Klase.Blanket;
import com.ab.ssd.ab_arhivablanketa.Klase.Komentar;
import com.ab.ssd.ab_arhivablanketa.Klase.Korisnik;
import com.ab.ssd.ab_arhivablanketa.Klase.Resenje;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class ResenjeInfoActivity extends AppCompatActivity {

    Resenje resenje;

    TextView naziv;
    TextView datum;
    ImageView slika;
    View mProgressView;
    View mainView;
    Bitmap bitmap;


    private ListView listView;
    private CustomListKomAdapter adapter;
    ArrayList<Komentar> listKomentar=new ArrayList<>();

    ListView komentarisi;
    private CustomListKomentarisiAdapter komentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resenje_info);

        mainView=findViewById(R.id.scroll_info);
        mProgressView=findViewById(R.id.info_progress);

        resenje=(Resenje) getIntent().getSerializableExtra("ResenjeSlanje");

        naziv=(TextView) findViewById(R.id.txtNaziv);
        datum=(TextView) findViewById(R.id.txtDatum);
        slika=(ImageView) findViewById(R.id.slikaBlanket);

        if(resenje!=null)
        {
            showProgress(true);
            naziv.setText("Resenje za blanket: "+resenje.blanket.toString());


            String dat=resenje.datum.substring(0,10);
            String vre=resenje.datum.substring(11);
            //datum.setText(m.datum.toString());
            datum.setText("Dodat: "+dat+" "+vre);

            String url="http://160.99.38.140:2666/api/resenje/"+resenje.ID;
            JsonObjectRequest blanketReq = new JsonObjectRequest(Request.Method.GET,url,null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject obj) {
                            showProgress(false);
                            try {
                                bitmap=resenje.getBitmapFromString(obj.getString("ImageInBytes"));
                                slika.setImageBitmap(bitmap);
                            }
                            catch (Exception e)
                            {
                                Toast.makeText(ResenjeInfoActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showProgress(false);
                    Toast.makeText(ResenjeInfoActivity.this,error.toString(),Toast.LENGTH_SHORT).show();

                }
            });

            final RequestQueue queue = Volley.newRequestQueue(ResenjeInfoActivity.this);
            queue.add(blanketReq);

            slika.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ResenjeInfoActivity.this, ZoomInZoomOut.class);
                    intent.putExtra("Blanket",0);
                    intent.putExtra("BlanketIDImage",resenje.ID);
                    startActivity(intent);
                }
            });

            String urlkom="http://160.99.38.140:2666/api/komentardto/"+resenje.ID+"?blanket=false";
            JsonArrayRequest request = new JsonArrayRequest(urlkom,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            //listKomentar=new ArrayList<>();
                            // Parsing json
                            for (int i = 0; i < response.length(); i++) {
                                try {

                                    JSONObject obj = response.getJSONObject(i);
                                    Komentar kom=new Komentar(obj.getInt("IdKomentar"),null,null,obj.getString("Datum"),
                                            new Korisnik(obj.getJSONObject("Dodao").getInt("IdKorisnik"),obj.getJSONObject("Dodao").getString("Ime"),
                                                    obj.getJSONObject("Dodao").getString("Prezime"),obj.getJSONObject("Dodao").getInt("BrIndeksa"),null,null,null,null,false)
                                            ,true,obj.getString("KomentarData"));

                                    listKomentar.add(kom);

                                } catch (Exception e) {
                                    Toast.makeText(ResenjeInfoActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                                }
                            }

                            listView = (ListView) findViewById(R.id.listKoment);
                            adapter = new CustomListKomAdapter(ResenjeInfoActivity.this, listKomentar);


                            listView.setAdapter(adapter);

                            adapter.notifyDataSetChanged();
                            setListViewHeightBasedOnChildren(listView);


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //VolleyLog.d("Error: " + error.getMessage());
                    Toast.makeText(ResenjeInfoActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                }
            });

            queue.add(request);



            komentarisi=(ListView) findViewById(R.id.listKomentarisi);
            if(KorisnikInstance.getInstance()!=null)
            {
                komentAdapter =new CustomListKomentarisiAdapter(ResenjeInfoActivity.this,resenje.ID,false);
                komentarisi.setAdapter(komentAdapter);
                komentAdapter.notifyDataSetChanged();
            }
            else
                komentarisi.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.resenje_info_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_save:
                saveToInternalStorage(bitmap);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveToInternalStorage(Bitmap bitmapImage){

        try {
            Toast.makeText(ResenjeInfoActivity.this,"Pokusavam da sacuvam...",Toast.LENGTH_SHORT).show();
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator+"ArhivaBlanketa";
            //Toast.makeText(BlanketInfoActivity.this, path.toString(), Toast.LENGTH_SHORT).show();
            File outputDir = new File(path);


            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
            File newFile = new File(path + File.separator +"Resenje za blanket "+resenje.blanket.toString()+ ".png");
            FileOutputStream out = new FileOutputStream(newFile);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, out);
            Toast.makeText(ResenjeInfoActivity.this,"Uspesno sacuvano!",Toast.LENGTH_SHORT).show();
        }

        catch (Exception e)
        {
            Toast.makeText(ResenjeInfoActivity.this,e.toString(),Toast.LENGTH_LONG).show();
        }

    }


    public void reloadKomentar()
    {
        final ArrayList list=new ArrayList();
        showProgress(true);
        String urlkom="http://160.99.38.140:2666/api/komentardto/"+resenje.ID+"?blanket=false";
        JsonArrayRequest request = new JsonArrayRequest(urlkom,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Komentar kom=new Komentar(obj.getInt("IdKomentar"),null,null,obj.getString("Datum"),
                                        new Korisnik(obj.getJSONObject("Dodao").getInt("IdKorisnik"),obj.getJSONObject("Dodao").getString("Ime"),
                                                obj.getJSONObject("Dodao").getString("Prezime"),obj.getJSONObject("Dodao").getInt("BrIndeksa"),null,null,null,null,false)
                                        ,true,obj.getString("KomentarData"));

                                list.add(kom);



                            } catch (Exception e) {
                                Toast.makeText(ResenjeInfoActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }

                        listView = (ListView) findViewById(R.id.listKoment);

                        listKomentar=list;
                        adapter = new CustomListKomAdapter(ResenjeInfoActivity.this, listKomentar);


                        listView.setAdapter(adapter);

                        adapter.notifyDataSetChanged();
                        setListViewHeightBasedOnChildren(listView);

                        showProgress(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d("Error: " + error.getMessage());
                Toast.makeText(ResenjeInfoActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                showProgress(false);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(ResenjeInfoActivity.this);
        queue.add(request);

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, AppBarLayout.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
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

    public void stampaj(String s)
    {
        Toast.makeText(ResenjeInfoActivity.this,s,Toast.LENGTH_SHORT).show();
    }
}
