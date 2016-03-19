package com.deu.talha.deuyemek.ogeb;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by talha on 3.3.2015.
 */
public class DeuFeedBack {

    private static final String POST_URL = "http://www.deu.edu.tr/DEUWeb/Icerik/gonder_ogeb.php";
    ///////////////////Form Elements/////////////////////////////////////////////////////////////
    private final String KATEGORI = "kategori";
    private final String GRUP = "grup";
    private final String BIRIMLER = "birimler";
    private final String AD = "ad";
    private final String SOYAD = "soyad";
    private final String MAIL = "mail";
    private final String KONU = "konu";
    private final String MESAJ = "mesaj";
    ////////////////////////////////////////////////////////////////////////////////////////////
    //######Enums######

    public enum durumlar{
        Akademisyen,IdariPersonel,
        Ogrenci,Diger
    }

    public enum kategoriler{
        Memnuniyet,Elestiri,Oneri
    }
    //#################

    public void sendPost(String ad,String soyad,
                         String mail,String konu,
                         String mesaj,kategoriler kategoriEnum,
                         durumlar durumEnum) throws Exception {
        URL webUrl = new URL(POST_URL);
        HttpURLConnection conn = (HttpURLConnection) webUrl.openConnection();
        String kategori = "";
        if (kategoriEnum == kategoriler.Elestiri) {
            kategori = "Elestiri";
        } else if(kategoriler.Memnuniyet == kategoriEnum) {
            kategori = "Memnuniyet";
        } else if (kategoriEnum == kategoriler.Oneri) {
            kategori = "Öneri";
        }
        String grup = "";
        if (durumlar.Akademisyen == durumEnum) {
            grup = "Akademisyen";
        } else if (durumlar.IdariPersonel == durumEnum) {
            grup = "İdari personel";
        } else if (durumlar.Ogrenci == durumEnum) {
            grup = "Öğrenci";
        } else if (durumlar.Diger == durumEnum) {
            grup = "Diğer";
        }
        String postParams = KATEGORI + "=" + URLEncoder.encode(kategori, "UTF-8") + "&" +
                GRUP + "=" + URLEncoder.encode(grup, "UTF-8") + "&" + BIRIMLER +"=0&"+
                AD +"=" + URLEncoder.encode(ad, "UTF-8")+ "&" + SOYAD +"="+URLEncoder.encode(soyad, "UTF-8")
                + "&" + MAIL +"="+URLEncoder.encode(mail, "UTF-8") + "&" + KONU +"=" +
                URLEncoder.encode(konu, "UTF-8") +"&" + MESAJ +"=" + URLEncoder.encode(mesaj, "UTF-8");


        conn.setUseCaches(false);
        conn.setRequestMethod("POST");

        conn.setRequestProperty("Host", "www.deu.edu.tr");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        conn.setRequestProperty("Accept-Language", "tr-TR,tr;q=0.8,en-US;q=0.6,en;q=0.4");


        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Referer", "http://www.deu.edu.tr/DEUWeb/Icerik/Icerik.php?KOD=14757");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", Integer.toString(postParams.length()));
        conn.setDoOutput(true);
        conn.setDoInput(true);

        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(postParams);
        wr.flush();
        wr.close();

        int responseCode = conn.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + POST_URL);
        System.out.println("Post parameters : " + postParams);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in =
                new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
    }


}
