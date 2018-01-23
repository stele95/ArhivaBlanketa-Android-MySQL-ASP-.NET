package com.ab.ssd.ab_arhivablanketa;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.ssd.ab_arhivablanketa.Klase.Blanket;
import com.ab.ssd.ab_arhivablanketa.Klase.Fakultet;
import com.ab.ssd.ab_arhivablanketa.Klase.GodinaStudija;
import com.ab.ssd.ab_arhivablanketa.Klase.Komentar;
import com.ab.ssd.ab_arhivablanketa.Klase.Korisnik;
import com.ab.ssd.ab_arhivablanketa.Klase.Predmet;
import com.ab.ssd.ab_arhivablanketa.Klase.Rok;
import com.ab.ssd.ab_arhivablanketa.Klase.Smer;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BlanketInfoActivity extends AppCompatActivity {

    public Blanket blanket;
    TextView naziv;
    TextView rok;
    TextView pismeni;
    TextView usmeni;
    TextView datum;
    ImageView slika;
    View mProgressView;
    View mainView;
    Bitmap bitmap;
    //Button prijava;
    //Button cuvaj;

    private ListView listView;
    private CustomListKomAdapter adapter;
    ArrayList<Komentar> listKomentar=new ArrayList<>();

    ListView komentarisi;
    private CustomListKomentarisiAdapter komentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blanket_info);

        naziv=(TextView) findViewById(R.id.txtNaziv);
        rok=(TextView) findViewById(R.id.txtRok);
        pismeni=(TextView) findViewById(R.id.txtPismeni);
        usmeni=(TextView) findViewById(R.id.txtUsmeni);
        datum=(TextView) findViewById(R.id.txtDatum);
        slika=(ImageView) findViewById(R.id.slikaBlanket);
        mainView=findViewById(R.id.scroll_info);
        mProgressView=findViewById(R.id.info_progress);

        blanket=(Blanket) getIntent().getSerializableExtra("BlanketSlanje");

        if(blanket!=null)
        {
            showProgress(true);
            naziv.setText("Predmet: "+blanket.predmet.naziv);
            rok.setText("Rok: "+blanket.rok.naziv+" "+blanket.godina);
            if(blanket.pismeni==true)
            {
                pismeni.setText("Pismeni: DA");
            }
            else if(blanket.pismeni==false)
            {
                pismeni.setText("Pismeni: NE");
            }

            if(blanket.usmeni==true)
            {
                usmeni.setText("Usmeni: DA");
            }
            else if(blanket.usmeni==false)
            {
                usmeni.setText("Usmeni: NE");
            }


            // datum kada je dodato
            // String.valueOf(m.getYear())
            String dat=blanket.datum.substring(0,10);
            String vre=blanket.datum.substring(11);
            //datum.setText(m.datum.toString());
            datum.setText("Dodat: "+dat+" "+vre);

            String url="http://160.99.38.140:2666/api/blanket/"+blanket.ID;
            JsonObjectRequest blanketReq = new JsonObjectRequest(Request.Method.GET,url,null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject obj) {
                            showProgress(false);
                            //prijava.setVisibility(View.VISIBLE);
                            //cuvaj.setVisibility(View.VISIBLE);
                            try {
                                bitmap=blanket.getBitmapFromString(obj.getString("ImageInBytes"));
                                slika.setImageBitmap(bitmap);
                            }
                            catch (Exception e)
                            {
                                Toast.makeText(BlanketInfoActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showProgress(false);
                    Toast.makeText(BlanketInfoActivity.this,error.toString(),Toast.LENGTH_SHORT).show();

                }
            });

            final RequestQueue queue = Volley.newRequestQueue(BlanketInfoActivity.this);
            queue.add(blanketReq);

            slika.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(BlanketInfoActivity.this, ZoomInZoomOut.class);
                    intent.putExtra("Blanket",1);
                    intent.putExtra("BlanketIDImage",blanket.ID);
                    startActivityForResult(intent,133);
                }
            });

            String urlkom="http://160.99.38.140:2666/api/komentardto/"+blanket.ID+"?blanket=true";
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
                                    Toast.makeText(BlanketInfoActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                                }
                            }

                            listView = (ListView) findViewById(R.id.listKoment);
                            adapter = new CustomListKomAdapter(BlanketInfoActivity.this, listKomentar);


                            listView.setAdapter(adapter);

                            adapter.notifyDataSetChanged();
                            setListViewHeightBasedOnChildren(listView);


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //VolleyLog.d("Error: " + error.getMessage());
                    Toast.makeText(BlanketInfoActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                }
            });

            queue.add(request);



            komentarisi=(ListView) findViewById(R.id.listKomentarisi);
            if(KorisnikInstance.getInstance()!=null)
            {
                komentAdapter =new CustomListKomentarisiAdapter(BlanketInfoActivity.this,blanket.ID,true);
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
        inflater.inflate(R.menu.blanket_info_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_save:
                saveToInternalStorage(bitmap);
                return true;
            case R.id.action_report:
                if(KorisnikInstance.getInstance()==null)
                {
                    Toast.makeText(BlanketInfoActivity.this,"Prijavite se!",Toast.LENGTH_SHORT).show();
                    return true;
                }
                Intent intent = new Intent(BlanketInfoActivity.this, ReportActivity.class);
                Blanket bl=new Blanket(blanket);
                bl.thumbnail=null;
                bl.slika=null;
                intent.putExtra("BlanketReportSlanje",bl);
                startActivity(intent);
                return true;
            case R.id.action_resenja:
                //Toast.makeText(BlanketInfoActivity.this,"Not implemented yet!",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(BlanketInfoActivity.this, ResenjaActivity.class);
                Blanket bl1=new Blanket(blanket);
                bl1.thumbnail=null;
                bl1.slika=null;
                intent1.putExtra("BlanketResenjaSlanje",bl1);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


    private void saveToInternalStorage(Bitmap bitmapImage){

       try {
           Toast.makeText(BlanketInfoActivity.this,"Pokusavam da sacuvam...",Toast.LENGTH_SHORT).show();
           String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator+"ArhivaBlanketa";
           //Toast.makeText(BlanketInfoActivity.this, path.toString(), Toast.LENGTH_SHORT).show();
           File outputDir = new File(path);


           if (!outputDir.exists()) {
               outputDir.mkdirs();
           }
           File newFile = new File(path + File.separator +blanket.toString()+ ".png");
           FileOutputStream out = new FileOutputStream(newFile);
           bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, out);
           Toast.makeText(BlanketInfoActivity.this,"Uspesno sacuvano!",Toast.LENGTH_SHORT).show();
       }

       catch (Exception e)
       {
           Toast.makeText(BlanketInfoActivity.this,e.toString(),Toast.LENGTH_LONG).show();
       }

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

    public void reloadKomentar()
    {
        //listKomentar=new ArrayList<>();
        final ArrayList list=new ArrayList();
        showProgress(true);

        String urlkom="http://160.99.38.140:2666/api/komentardto/"+blanket.ID+"?blanket=true";
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

                                list.add(kom);



                            } catch (Exception e) {
                                Toast.makeText(BlanketInfoActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }

                        listView = (ListView) findViewById(R.id.listKoment);

                        listKomentar=list;
                        adapter = new CustomListKomAdapter(BlanketInfoActivity.this, listKomentar);


                        listView.setAdapter(adapter);

                        adapter.notifyDataSetChanged();
                        setListViewHeightBasedOnChildren(listView);

                        showProgress(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d("Error: " + error.getMessage());
                Toast.makeText(BlanketInfoActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                showProgress(false);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(BlanketInfoActivity.this);
        queue.add(request);





        //Toast.makeText(BlanketInfoActivity.this,"Gotovo ucitavanje",Toast.LENGTH_SHORT).show();
    }

    public void stampaj(String s)
    {
        Toast.makeText(BlanketInfoActivity.this,s,Toast.LENGTH_SHORT).show();
    }



}
