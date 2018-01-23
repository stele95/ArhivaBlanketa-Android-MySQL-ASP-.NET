package com.ab.ssd.ab_arhivablanketa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ab.ssd.ab_arhivablanketa.Klase.Blanket;
import com.ab.ssd.ab_arhivablanketa.Klase.*;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;



public class HomeFragment extends Fragment {

    //private ProgressBar pDialog;
    private ListView listView;
    private CustomListAdapter adapter;
    //private List<Blanket> blanketList=new ArrayList<Blanket>();

    BottomNavActivity activity;

    public static final int BLANKET_INFO_REQ=1;


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

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home, container, false);

        activity=(BottomNavActivity) getActivity();
        listView = view.findViewById(R.id.list);
        if(activity.search==true)
        {
            adapter = new CustomListAdapter(HomeFragment.this.getActivity(), activity.blanketSearch);
            activity.search=false;
        }
        else
        {
            adapter = new CustomListAdapter(HomeFragment.this.getActivity(), activity.blanketList);
            activity.search=false;
        }
        //adapter = new CustomListAdapter(HomeFragment.this.getActivity(), activity.blanketList);
        listView.setAdapter(adapter);
        listView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapter,View v, int position, long l) {
            Object item = adapter.getItemAtPosition(position);
            Blanket b = null;
            try {
                b = (Blanket) item;
            } catch (Exception e) {
                Toast.makeText(HomeFragment.this.getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
            }

            activity.startBlanketInfo(b,true);
        }
        });




        adapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(listView);



        return view;
    }


}
