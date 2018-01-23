package com.ab.ssd.ab_arhivablanketa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.ssd.ab_arhivablanketa.Klase.Fakultet;
import com.ab.ssd.ab_arhivablanketa.Klase.Korisnik;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class UserEditActivity extends AppCompatActivity {

    private EditText mIme;
    private EditText mPrezime;
    private EditText mBrIndexa;
    private EditText mUsername;
    private EditText mEmail;

    Button save;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        mIme= (EditText)findViewById(R.id.txtimeedit);
        mPrezime= (EditText)findViewById(R.id.txtprezimeedit);
        mBrIndexa= (EditText)findViewById(R.id.txtbrindexaedit);
        mUsername= (EditText)findViewById(R.id.txtusernameedit);
        mEmail= (EditText)findViewById(R.id.txtemailedit);
        save=(Button) findViewById(R.id.btnSaveEdit);
        cancel=(Button)findViewById(R.id.btnCancelEdit);

        mIme.setText(KorisnikInstance.getInstance().ime, TextView.BufferType.EDITABLE);
        mPrezime.setText(KorisnikInstance.getInstance().prezime, TextView.BufferType.EDITABLE);
        mBrIndexa.setText(KorisnikInstance.getInstance().brIndexa+"", TextView.BufferType.EDITABLE);
        mUsername.setText(KorisnikInstance.getInstance().username, TextView.BufferType.EDITABLE);
        mEmail.setText(KorisnikInstance.getInstance().email, TextView.BufferType.EDITABLE);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                KorisnikInstance k=new KorisnikInstance(KorisnikInstance.getInstance().ID,mIme.getText().toString(),
                        mPrezime.getText().toString(),Integer.parseInt(mBrIndexa.getText().toString()),mUsername.getText().toString(),
                        mEmail.getText().toString(), KorisnikInstance.getInstance().pass, KorisnikInstance.getInstance().fakultet,false);

                String urllog = "http://160.99.38.140:2666/api/korisnik/"+KorisnikInstance.getInstance().ID;

                JsonObjectRequest request1 = new JsonObjectRequest
                        (Request.Method.PUT, urllog, KorisnikInstance.getInstance().korisnikJSON, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(UserEditActivity.this, "Uspesno ste promenili podatke!", Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(UserEditActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                            }
                        });

                RequestQueue queue1 = Volley.newRequestQueue(UserEditActivity.this);
                queue1.add(request1);
            }
        });
    }
}
