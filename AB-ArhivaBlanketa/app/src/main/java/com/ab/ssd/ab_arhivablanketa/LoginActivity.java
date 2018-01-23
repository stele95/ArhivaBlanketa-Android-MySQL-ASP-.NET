package com.ab.ssd.ab_arhivablanketa;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {


    void registracija(View view)
    {
        //Toast.makeText(LoginActivity.this,"Not implemented yet!",Toast.LENGTH_SHORT).show();
        attemptRegister();
    }
    void skip(View view)
    {
        KorisnikInstance.setInstance(null);
        Toast.makeText(LoginActivity.this,"Neprijavljen korisnik!",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(LoginActivity.this, BottomNavActivity.class);
        startActivity(intent);
        finish();
    }

    private void attemptRegister() {
        if (mRegTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if(TextUtils.isEmpty(password))
        {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }


        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mRegTask = new UserRegisterTask(email, password);
            mRegTask.execute((Void) null);
        }
    }

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;
    private UserRegisterTask mRegTask=null;
    private PopulateLists mLists=null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.idEmail);


        mPasswordView = (EditText) findViewById(R.id.idPassword);


        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mLists=new PopulateLists();
        mLists.execute();
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if(TextUtils.isEmpty(password))
        {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }


        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        if(email==null)
            return false;
        else
        {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }

    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 1;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        //addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    /**private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }*/


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private Korisnik korisnik;


        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                String urllog="http://160.99.38.140:2666/api/login?email="+mEmail;
                JsonObjectRequest request = new JsonObjectRequest
                        (Request.Method.GET, urllog, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    korisnik = new Korisnik(response.getInt("IdKorisnik"),response.getString("Ime"),
                                    response.getString("Prezime"),response.getInt("BrIndeksa"),response.getString("Username"),
                                            response.getString("Email"),response.getString("Password"),
                                            new Fakultet(response.getJSONObject("Fakultet").getInt("IdFakultet"),
                                                    response.getJSONObject("Fakultet").getString("Naziv"),
                                                    response.getJSONObject("Fakultet").getString("Grad")),
                                            response.getBoolean("Admin"));

                                    if (mEmail.equals(korisnik.email)) {
                                        if (mPassword.equals(korisnik.pass)) {

                                            KorisnikInstance k=new KorisnikInstance(korisnik.ID,korisnik.ime,korisnik.prezime,korisnik.brIndexa,
                                                    korisnik.username,korisnik.email,korisnik.pass,korisnik.fakultet,korisnik.admin);
                                            Toast.makeText(LoginActivity.this, "Uspesno ste se prijavili", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(LoginActivity.this, BottomNavActivity.class);
                                            startActivity(intent);
                                            finish();

                                        } else {
                                            showProgress(false);
                                            mPasswordView.setError(getString(R.string.error_incorrect_password));
                                            mPasswordView.requestFocus();
                                        }
                                    } else {
                                        showProgress(false);
                                        mEmailView.setError(getString(R.string.error_invalid_email));
                                        mEmailView.requestFocus();
                                    }
                                    //Toast.makeText(LoginActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                                }
                                catch (Exception e)
                                {
                                    Toast.makeText(LoginActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                                    showProgress(false);
                                    //KorisnikInstance.setInstance(null);

                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(LoginActivity.this,"Ne postoji taj korisnik, registrujte se!",Toast.LENGTH_SHORT).show();
                                showProgress(false);
                            }
                        });

                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(request);
                return true;

            } catch (Exception e) {
                Toast.makeText(LoginActivity.this, "Greska: "+e.toString(), Toast.LENGTH_LONG).show();
                showProgress(false);
                return false;

            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            //showProgress(false);
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }


    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private Korisnik korisnik;


        UserRegisterTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {


                String urlnadji = "http://160.99.38.140:2666/api/login?email=" + mEmail;



                final JsonObjectRequest request = new JsonObjectRequest
                        (Request.Method.GET, urlnadji, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Toast.makeText(LoginActivity.this,"Korisnik vec postoji u bazi!",Toast.LENGTH_LONG).show();
                                    showProgress(false);
                                }
                                catch (Exception e) {
                                    Toast.makeText(LoginActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                                    showProgress(false);
                                }

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //Toast.makeText(LoginActivity.this,"Greska u registraciji!",Toast.LENGTH_SHORT).show();
                                try {
                                    String urllog = "http://160.99.38.140:2666/api/korisnik";
                                    JSONObject obj = new JSONObject();
                                    obj.put("Email", mEmail);
                                    obj.put("Password", mPassword);
                                    obj.put("Ime", "");
                                    obj.put("Prezime", "");
                                    obj.put("Username", "");
                                    obj.put("BrIndeksa", "");
                                    obj.put("Admin", false);
                                    JSONObject faks = new JSONObject();
                                    faks.put("IdFakultet", "1");
                                    faks.put("Naziv", "Elektronski fakultet");
                                    faks.put("Grad", "Nis");
                                    obj.put("Fakultet", faks);
                                    JsonObjectRequest request1 = new JsonObjectRequest
                                            (Request.Method.POST, urllog, obj, new Response.Listener<JSONObject>() {

                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    try {
                                                        korisnik = new Korisnik(response.getInt("IdKorisnik"), response.getString("Ime"),
                                                                response.getString("Prezime"), response.getInt("BrIndeksa"), response.getString("Username"),
                                                                response.getString("Email"), response.getString("Password"),
                                                                new Fakultet(response.getJSONObject("Fakultet").getInt("IdFakultet"),
                                                                        response.getJSONObject("Fakultet").getString("Naziv"),
                                                                        response.getJSONObject("Fakultet").getString("Grad")),
                                                                response.getBoolean("Admin"));

                                                        KorisnikInstance k = new KorisnikInstance(korisnik.ID, korisnik.ime, korisnik.prezime, korisnik.brIndexa,
                                                                korisnik.username, korisnik.email, korisnik.pass, korisnik.fakultet,korisnik.admin);
                                                        Toast.makeText(LoginActivity.this, "Uspesno ste se registrovali!", Toast.LENGTH_LONG).show();
                                                        Intent intent = new Intent(LoginActivity.this, BottomNavActivity.class);
                                                        startActivity(intent);
                                                        finish();

                                                    } catch (Exception e) {
                                                        Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                                        showProgress(false);
                                                    }
                                                }
                                            }, new Response.ErrorListener() {

                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(LoginActivity.this, "Greska u registraciji!", Toast.LENGTH_SHORT).show();
                                                    showProgress(false);
                                                }
                                            });

                                    RequestQueue queue1 = Volley.newRequestQueue(LoginActivity.this);
                                    queue1.add(request1);

                                } catch (Exception ex) {
                                    showProgress(false);
                                }
                            }
                        }
                        );

                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(request);
                return true;

            } catch (Exception e) {
                Toast.makeText(LoginActivity.this, "Greska: "+e.toString(), Toast.LENGTH_LONG).show();
                showProgress(false);
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mRegTask = null;
            //showProgress(false);
        }

        @Override
        protected void onCancelled() {
            mRegTask = null;
            showProgress(false);
        }
    }


    public class PopulateLists extends AsyncTask<Void, Void, Boolean> {

        PopulateLists() {

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.


            if (ListeInstance.getInstance().listSmer.isEmpty())
                if (!getSmer())
                    return false;
            if (ListeInstance.getInstance().listGodSt.isEmpty())
                if (!getGodSt())
                    return false;
            if (ListeInstance.getInstance().listPredmet.isEmpty())
                if (!getPred())
                    return false;
            if (ListeInstance.getInstance().listRok.isEmpty())
                if (!getRok())
                    return false;

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if(!success)
                Toast.makeText(LoginActivity.this,"Something went wrong.....",Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onCancelled() {

        }
    }

    boolean getRok()
    {
        try {
            String url = "http://160.99.38.140:2666/api/rok";
            JsonArrayRequest blanketReq = new JsonArrayRequest(url,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            // Parsing json
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    Rok rok = new Rok(obj.getInt("IdRok"),
                                            obj.getString("Naziv"));
                                    ListeInstance.getInstance().listRok.add(rok);

                                } catch (Exception e) {
                                    Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                }
            });

            // Adding request to request queue
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(blanketReq);
            return  true;
        }
        catch (Exception e)
        {
            Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            return false;
        }
    }

    boolean getGodSt()
    {
        try {
            String url = "http://160.99.38.140:2666/api/godinastudija";
            JsonArrayRequest blanketReq = new JsonArrayRequest(url,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            // Parsing json
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    GodinaStudija godst = new GodinaStudija(obj.getInt("IdGodina"),
                                            obj.getString("Godina"));
                                    ListeInstance.getInstance().listGodSt.add(godst);

                                } catch (Exception e) {
                                    Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                }
            });

            // Adding request to request queue
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(blanketReq);
            return  true;
        }
        catch (Exception e)
        {
            Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            return false;
        }
    }

    boolean getPred()
    {
        try {
            String url = "http://160.99.38.140:2666/api/predmet";
            JsonArrayRequest blanketReq = new JsonArrayRequest(url,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            // Parsing json
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    Predmet pred = new Predmet(obj.getInt("IdPredmet"),
                                            obj.getString("Naziv"),
                                            new Smer(obj.getJSONObject("Smer").getInt("IdSmer"),obj.getJSONObject("Smer").getString("Naziv"),
                                                    new Fakultet(obj.getJSONObject("Smer").getJSONObject("Fakultet").getInt("IdFakultet"),obj.getJSONObject("Smer").getJSONObject("Fakultet").getString("Naziv"),obj.getJSONObject("Smer").getJSONObject("Fakultet").getString("Grad"))),
                                            new GodinaStudija(obj.getJSONObject("Godina").getInt("IdGodina"),obj.getJSONObject("Godina").getString("Godina")));
                                    ListeInstance.getInstance().listPredmet.add(pred);

                                } catch (Exception e) {
                                    Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                }
            });

            // Adding request to request queue
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(blanketReq);
            return  true;
        }
        catch (Exception e)
        {
            Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            return false;
        }
    }

    boolean getSmer()
    {
        try {
            String url = "http://160.99.38.140:2666/api/smer";
            JsonArrayRequest blanketReq = new JsonArrayRequest(url,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            // Parsing json
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    Smer smer = new Smer(obj.getInt("IdSmer"),
                                            obj.getString("Naziv"),
                                            new Fakultet(obj.getJSONObject("Fakultet").getInt("IdFakultet"),
                                                    obj.getJSONObject("Fakultet").getString("Naziv"),
                                                    obj.getJSONObject("Fakultet").getString("Grad")));
                                    ListeInstance.getInstance().listSmer.add(smer);

                                } catch (Exception e) {
                                    Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                }
            });

            // Adding request to request queue
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(blanketReq);
            return  true;
        }
        catch (Exception e)
        {
            Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            return false;
        }
    }
}

