package com.ab.ssd.ab_arhivablanketa;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.ssd.ab_arhivablanketa.Klase.Blanket;
import com.ab.ssd.ab_arhivablanketa.Klase.Komentar;

import java.util.List;

public class CustomListKomAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Komentar> komentarItems;
    //ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListKomAdapter(Activity activity, List<Komentar> komentarItems) {
        this.activity = activity;
        this.komentarItems = komentarItems;
    }

    @Override
    public int getCount() {
        return komentarItems.size();
    }

    @Override
    public Object getItem(int location) {
        return komentarItems.get(location);
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
            convertView = inflater.inflate(R.layout.list_koment_row, null);

        TextView ime = (TextView) convertView.findViewById(R.id.txtKomIme);
        TextView komentar = (TextView) convertView.findViewById(R.id.txtKomKom);
        TextView dodat= (TextView) convertView.findViewById(R.id.txtKomDodat);


        Komentar m = komentarItems.get(position);

        ime.setText(m.dodao.ime+" "+m.dodao.prezime);
        komentar.setText(m.komentarData);

        String dat=m.datum.substring(0,10);
        String vre=m.datum.substring(11);
        //datum.setText(m.datum.toString());
        dodat.setText(dat+" "+vre);

        return convertView;
    }

}
