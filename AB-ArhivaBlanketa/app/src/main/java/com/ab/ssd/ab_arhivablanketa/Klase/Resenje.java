package com.ab.ssd.ab_arhivablanketa.Klase;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Base64;

import java.io.Serializable;
import java.sql.Timestamp;

public class Resenje implements Serializable {
    public int ID;
    public Blanket blanket;
    public String datum;
    public Korisnik dodao;
    //public byte[] ImageInBytes { get; set; }
    public Bitmap slika;
    public Bitmap thumbnail;

    public Resenje(){}
    public Resenje(int i, Blanket b, String d, Korisnik k, Bitmap s, Bitmap t)
    {
        this.ID=i;
        this.blanket=b;
        this.datum=d;
        this.dodao=k;
        this.slika=s;
        this.thumbnail=t;
    }

    public Resenje(Resenje r)
    {
        this.ID=r.ID;
        this.blanket=r.blanket;
        this.datum=r.datum;
        this.dodao=r.dodao;
        this.slika=r.slika;
        this.thumbnail=r.thumbnail;
    }

    public Bitmap getBitmapFromString(String jsonString) {
        /*
        * This Function converts the String back to Bitmap
        * */
        byte[] decodedString = Base64.decode(jsonString, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
}
