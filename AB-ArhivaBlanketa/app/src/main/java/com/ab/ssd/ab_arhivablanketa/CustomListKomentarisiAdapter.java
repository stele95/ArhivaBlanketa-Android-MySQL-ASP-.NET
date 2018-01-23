package com.ab.ssd.ab_arhivablanketa;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.ssd.ab_arhivablanketa.Klase.Blanket;
import com.ab.ssd.ab_arhivablanketa.Klase.Komentar;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

public class CustomListKomentarisiAdapter extends BaseAdapter {
    private Activity activity;
    private BlanketInfoActivity blanketInfo;
    private ResenjeInfoActivity resenjeInfo;
    private LayoutInflater inflater;
    int id;
    boolean pripada;
    //private List<Komentar> komentarItems;
    //ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListKomentarisiAdapter(Activity activity, int id, boolean pripada) {
        this.activity = activity;
        this.id=id;
        this.pripada=pripada;
        if(pripada)
        {
            this.blanketInfo=(BlanketInfoActivity) activity;
        }
        else
        {
            this.resenjeInfo=(ResenjeInfoActivity) activity;
        }
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int location) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_komentarisi, null);

        final TextView ime = (TextView) convertView.findViewById(R.id.txtKomentarisiIme);
        //final EditText komentar = convertView.findViewById(R.id.textKomentarisi);
        //final Button komentarisi=  convertView.findViewById(R.id.btnKomentarisi);

        ime.setText(KorisnikInstance.getInstance().ime+" "+KorisnikInstance.getInstance().prezime);

        Button btnKomentar=(Button) convertView.findViewById(R.id.btnKomentarisi);
        final EditText koment=(EditText) convertView.findViewById(R.id.textKomentarisi) ;
        btnKomentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!koment.getText().toString().isEmpty())
                {

                    try{
                        JSONObject komentar=new JSONObject();
                        komentar.put("ID",id);
                        komentar.put("Dodao",KorisnikInstance.getInstance().ID);
                        komentar.put("PripadaBlanketu",pripada);
                        komentar.put("KomentarData",koment.getText().toString());

                        String urlpost="http://160.99.38.140:2666/api/komentar";
                        JsonObjectRequest postreq = new JsonObjectRequest(Request.Method.POST,urlpost,komentar,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject obj) {
                                        if (pripada) {
                                            blanketInfo.stampaj("Uspesno komentarisano");
                                            blanketInfo.reloadKomentar();
                                        }
                                        else
                                        {
                                            resenjeInfo.stampaj("Uspesno komentarisano");
                                            resenjeInfo.reloadKomentar();
                                        }

                                        koment.setText("");

                                    }
                                }
                                , new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if(pripada)
                                    blanketInfo.stampaj(error.toString());
                                else
                                    resenjeInfo.stampaj(error.toString());

                            }
                        });

                        RequestQueue queue;
                        if(pripada)
                            queue = Volley.newRequestQueue(blanketInfo);
                        else
                            queue = Volley.newRequestQueue(resenjeInfo);
                        queue.add(postreq);
                    }
                    catch (Exception e)
                    {
                        if(pripada)
                            blanketInfo.stampaj(e.toString());
                        else
                            resenjeInfo.stampaj(e.toString());
                    }
                }
            }
        });

        return convertView;
    }

}
