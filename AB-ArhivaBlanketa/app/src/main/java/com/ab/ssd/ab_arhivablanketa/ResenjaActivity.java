package com.ab.ssd.ab_arhivablanketa;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ab.ssd.ab_arhivablanketa.Klase.Blanket;
import com.ab.ssd.ab_arhivablanketa.Klase.Fakultet;
import com.ab.ssd.ab_arhivablanketa.Klase.GodinaStudija;
import com.ab.ssd.ab_arhivablanketa.Klase.Korisnik;
import com.ab.ssd.ab_arhivablanketa.Klase.Predmet;
import com.ab.ssd.ab_arhivablanketa.Klase.Resenje;
import com.ab.ssd.ab_arhivablanketa.Klase.Rok;
import com.ab.ssd.ab_arhivablanketa.Klase.Smer;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResenjaActivity extends AppCompatActivity {

    Blanket blanket;
    ArrayList<Resenje> listResenja=new ArrayList<>();
    private ListView listView;
    private CustomListResenjaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resenja);


        blanket=(Blanket) getIntent().getSerializableExtra("BlanketResenjaSlanje");

        listView=(ListView) findViewById(R.id.listResenja);

        Button dodaj=(Button) findViewById(R.id.btnAddResenje);

        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(KorisnikInstance.getInstance()==null)
                {
                    Toast.makeText(ResenjaActivity.this,"Prijavite se!",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent=new Intent(ResenjaActivity.this,AddResenjeActivity.class);
                Blanket bl=new Blanket(blanket);
                bl.thumbnail=null;
                bl.slika=null;
                intent.putExtra("BlanketResenje",bl);
                startActivity(intent);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter,View v, int position, long l) {
                Object item = adapter.getItemAtPosition(position);
                Resenje b = null;
                try {
                    b = (Resenje) item;
                } catch (Exception e) {
                    Toast.makeText(ResenjaActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(ResenjaActivity.this, ResenjeInfoActivity.class);
                Resenje bl = new Resenje(b);
                bl.thumbnail = null;
                bl.slika = null;
                intent.putExtra("ResenjeSlanje", bl);
                startActivity(intent);
            }
        });

        String url="http://160.99.38.140:2666/api/ResenjeDTO/"+blanket.ID;
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Resenje resenje=new Resenje(obj.getInt("IdResenje"),blanket,obj.getString("Datum"),null,null,null);
                                resenje.thumbnail = resenje.getBitmapFromString(obj.getString("ThumbnailInBytes"));
                                listResenja.add(resenje);

                            } catch (Exception e) {
                                Toast.makeText(ResenjaActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        if(listResenja.size()==0) {
                            Toast.makeText(ResenjaActivity.this, "Nema resenja u bazi za zeljeni blanket!", Toast.LENGTH_LONG).show();
                            //finish();
                        }

                        listView = (ListView) findViewById(R.id.listResenja);
                        adapter = new CustomListResenjaAdapter(ResenjaActivity.this, listResenja);


                        listView.setAdapter(adapter);

                        adapter.notifyDataSetChanged();
                        setListViewHeightBasedOnChildren(listView);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d("Error: " + error.getMessage());
                Toast.makeText(ResenjaActivity.this,error.toString(),Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue queue = Volley.newRequestQueue(ResenjaActivity.this);
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
}
