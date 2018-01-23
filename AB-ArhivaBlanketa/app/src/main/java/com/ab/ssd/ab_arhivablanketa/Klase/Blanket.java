package com.ab.ssd.ab_arhivablanketa.Klase;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;

public class Blanket implements Serializable{
    public int ID ;
    public Rok rok;
    public int godina ;
    public Predmet predmet ;
    public String datum ;
    public Korisnik dodao;
    public Korisnik odobrio;
    public boolean odobren;
    public boolean pismeni ;
    public boolean usmeni ;
    //public  byte[] ImageInBytes ;
    public Bitmap slika;
    public Bitmap thumbnail;



    public Blanket(){}
    public Blanket(int i, Rok r, int g, Predmet p, String d, Korisnik dod, Korisnik od, boolean odobren, boolean pi, boolean us, Bitmap s, Bitmap t)
    {
        this.ID=i;
        this.rok=r;
        this.godina=g;
        this.predmet=p;
        this.datum=d;
        this.dodao=dod;
        this.odobrio=od;
        this.odobren=odobren;
        this.pismeni=pi;
        this.usmeni=us;
        this.slika=s;
        this.thumbnail=t;
    }

    public Blanket(Blanket b)
    {
        this.ID=b.ID;
        this.rok=b.rok;
        this.godina=b.godina;
        this.predmet=b.predmet;
        this.datum=b.datum;
        this.dodao=b.dodao;
        this.odobrio=b.odobrio;
        this.odobren=b.odobren;
        this.pismeni=b.pismeni;
        this.usmeni=b.usmeni;
        this.slika=b.slika;
        this.thumbnail=b.thumbnail;
    }

    public Bitmap getBitmapFromString(String jsonString) {
        /*
        * This Function converts the String back to Bitmap
        * */
        byte[] decodedString = Base64.decode(jsonString, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public String getStringFromBitmap(Bitmap bitmapPicture) {
         /*
         * This functions converts Bitmap picture to a string which can be
         * JSONified.
         * */
        final int COMPRESSION_QUALITY = 100;
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.JPEG, COMPRESSION_QUALITY,
                byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }

    public String toString()
    {
        return predmet.naziv+", "+rok.naziv+" "+godina;
    }

}
