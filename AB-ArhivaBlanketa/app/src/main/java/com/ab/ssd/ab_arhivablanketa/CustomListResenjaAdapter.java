package com.ab.ssd.ab_arhivablanketa;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.ssd.ab_arhivablanketa.Klase.Komentar;
import com.ab.ssd.ab_arhivablanketa.Klase.Resenje;

import java.util.List;

public class CustomListResenjaAdapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<Resenje> resenjeItems;

    public CustomListResenjaAdapter(Activity activity, List<Resenje> resenjeItems) {
        this.activity = activity;
        this.resenjeItems = resenjeItems;
    }

    @Override
    public int getCount() {
        return resenjeItems.size();
    }

    @Override
    public Object getItem(int location) {
        return resenjeItems.get(location);
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
            convertView = inflater.inflate(R.layout.list_resenja_row, null);

        TextView dodat= (TextView) convertView.findViewById(R.id.releaseYear);
        ImageView slika=(ImageView) convertView.findViewById(R.id.thumbnail);


        Resenje m = resenjeItems.get(position);

        slika.setImageBitmap(m.thumbnail);

        String dat=m.datum.substring(0,10);
        String vre=m.datum.substring(11);
        //datum.setText(m.datum.toString());
        dodat.setText(dat+" "+vre);



        return convertView;
    }
}
