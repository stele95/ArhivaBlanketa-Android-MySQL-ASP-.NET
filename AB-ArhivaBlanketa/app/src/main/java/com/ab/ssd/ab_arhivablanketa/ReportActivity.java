package com.ab.ssd.ab_arhivablanketa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.ssd.ab_arhivablanketa.Klase.Blanket;
import com.ab.ssd.ab_arhivablanketa.Klase.Fakultet;
import com.ab.ssd.ab_arhivablanketa.Klase.Korisnik;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class ReportActivity extends AppCompatActivity {

    TextView blanketText;
    EditText unos;
    Button prijavi;
    Blanket blanket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);


        blanket=(Blanket) getIntent().getSerializableExtra("BlanketReportSlanje");

        blanketText=(TextView)findViewById(R.id.txtBlanketReport);
        unos=(EditText)findViewById(R.id.txtKomentarReport);
        prijavi=(Button) findViewById(R.id.btnReport);

        blanketText.setText(blanket.toString());

        prijavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prijavi.setEnabled(false);
                unos.setEnabled(false);
                JSONObject prijava=new JSONObject();
                try {
                    prijava.put("Dodao", KorisnikInstance.getInstance().ID);
                    prijava.put("Blanket", blanket.ID);
                    prijava.put("KomentarData",unos.getText());
                    String urllog = "http://160.99.38.140:2666/api/prijava";

                    JsonObjectRequest request1 = new JsonObjectRequest
                            (Request.Method.POST, urllog, prijava, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    Toast.makeText(ReportActivity.this, "Uspesno ste prijavili blanket!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(ReportActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                    prijavi.setEnabled(true);
                                    unos.setEnabled(true);
                                }
                            });

                    RequestQueue queue1 = Volley.newRequestQueue(ReportActivity.this);
                    queue1.add(request1);
                }
                catch (Exception e)
                {
                    Toast.makeText(ReportActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                    prijavi.setEnabled(true);
                    unos.setEnabled(true);
                }
            }
        });


    }
}
