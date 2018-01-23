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

import java.util.List;

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Blanket> blanketItems;
    //ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<Blanket> blanketItems) {
        this.activity = activity;
        this.blanketItems = blanketItems;
    }

    @Override
    public int getCount() {
        return blanketItems.size();
    }

    @Override
    public Object getItem(int location) {
        return blanketItems.get(location);
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
            convertView = inflater.inflate(R.layout.list_row, null);

        ImageView slika= (ImageView) convertView.findViewById(R.id.thumbnail);
        TextView predmet = (TextView) convertView.findViewById(R.id.predmet);
        TextView rok = (TextView) convertView.findViewById(R.id.rok);
        TextView pismeni = (TextView) convertView.findViewById(R.id.pismeni);
        TextView usmeni = (TextView) convertView.findViewById(R.id.usmeni);
        TextView datum = (TextView) convertView.findViewById(R.id.releaseYear);

        Blanket m = blanketItems.get(position);

        // thumbnail image
        //thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
        slika.setImageBitmap(m.thumbnail);

        // predmet
        predmet.setText(m.predmet.naziv);

        // rok
        rok.setText(m.rok.naziv+" "+m.godina);


        if(m.pismeni==true)
        {
            pismeni.setText("Pismeni: DA");
        }
        else if(m.pismeni==false)
        {
            pismeni.setText("Pismeni: NE");
        }

        if(m.usmeni==true)
        {
            usmeni.setText("Usmeni: DA");
        }
        else if(m.usmeni==false)
        {
            usmeni.setText("Usmeni: NE");
        }


        // datum kada je dodato
        // String.valueOf(m.getYear())
        String dat=m.datum.substring(0,10);
        String vre=m.datum.substring(11);
        //datum.setText(m.datum.toString());
        datum.setText(dat+" "+vre);

        return convertView;
    }

}
