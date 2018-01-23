package com.ab.ssd.ab_arhivablanketa;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class UserDataFragment extends Fragment {

    public static UserDataFragment newInstance() {
        UserDataFragment fragment = new UserDataFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_data, container, false);
    }*/

    private TextView mIme;
    private TextView mPrezime;
    private TextView mBrIndexa;
    private TextView mUsername;
    private TextView mEmail;
    private TextView mFaks;
    Button edit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_user_data, container, false);

        mIme= view.findViewById(R.id.txtime);
        mPrezime= view.findViewById(R.id.txtprezime);
        mBrIndexa= view.findViewById(R.id.txtbrindexa);
        mUsername= view.findViewById(R.id.txtusername);
        mEmail= view.findViewById(R.id.txtemail);
        mFaks= view.findViewById(R.id.txtfaks);
        edit=view.findViewById(R.id.btnEditUser);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDataFragment.this.getContext(), UserEditActivity.class);
                startActivity(intent);
            }
        });

        if(KorisnikInstance.getInstance()!=null) {
            mIme.setText("Ime: " + KorisnikInstance.getInstance().ime);
            mPrezime.setText("Prezime: " + KorisnikInstance.getInstance().prezime);
            mBrIndexa.setText("Broj indeksa: " + KorisnikInstance.getInstance().brIndexa);
            mUsername.setText("Username: " + KorisnikInstance.getInstance().username);
            mEmail.setText("Email: " + KorisnikInstance.getInstance().email);
            mFaks.setText("Fakultet: " + KorisnikInstance.getInstance().fakultet.toString());

        }

        return view;
    }
}
