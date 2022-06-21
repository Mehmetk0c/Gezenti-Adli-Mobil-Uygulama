package com.example.traveller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    public static ViewPager viewPager;
    public static int deger = 0;
    public static TextView textView;
    public static TextView textView1;
    public static String label;
    public static int label2;
    public static ImageAdapter adapter;
    public static ImageAdapter1 adapter1;
    public static ImageAdapter2 adapter2;
    public static ImageAdapter3 adapter3;
    Intent intent=getIntent();
    String x;
    Button button;
    Button button3;
    ArrayList<Place> localList2 = new ArrayList<Place>();

    //String isimler[]=new String[11];
    //String metinler[]=new String[11];
    List<String> isimler = new ArrayList<String>();
    List<String> metinler = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.spinner);
        viewPager = findViewById(R.id.viewPager);
        textView = findViewById(R.id.metin);
        textView1 = findViewById(R.id.editTextTextMultiLine);
        button = findViewById(R.id.button1);
        button3=findViewById(R.id.travellist);

        spinner.setOnItemSelectedListener(this);
        adapter = new ImageAdapter(this);
        adapter1 = new ImageAdapter1(this);
        adapter2 = new ImageAdapter2(this);
        adapter3 = new ImageAdapter3(this);
        viewPager.setAdapter(adapter);
        loadSpinnerData();
        loadTextData();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Toast.makeText(MainActivity.this, "ss"+position, Toast.LENGTH_SHORT).show();
                label2 = position;
                int deger1 = label2 + (deger * 3);
                //label2=position+(deger*3);
                textView.setText(isimler.get(deger1));
                textView1.setText(metinler.get(deger1));

            }

            @Override
            public void onPageSelected(int position) {
                //Toast.makeText(MainActivity.this, "ff"+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SqliteHelper db=new SqliteHelper(getApplicationContext());
                Intent intent=getIntent();
                x=intent.getStringExtra("mail");
                db.addplace(intent.getStringExtra("mail"),textView.getText().toString());
                Toast.makeText(MainActivity.this,"Gezi Listesine Eklendi", Toast.LENGTH_SHORT).show();



            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=textView.getText().toString();
                SqliteHelper db = new SqliteHelper(getApplicationContext());
                localList2=db.location();
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);

                for (Place place:localList2)
                {
                    if(text.equals(place.name))
                    {
                        intent.putExtra("lat",place.lat);
                        intent.putExtra("longtt",place.longg);
                        startActivity(intent);
                    }


                }
            }
        });
    }

    private void loadSpinnerData() {
        SqliteHelper db = new SqliteHelper(getApplicationContext());

        //   List<String> labels = new ArrayList<String>();
        // labels.add("Nevsehir");
        //labels.add("Marmaris");
        //labels.add("Antalya");
        //labels.add("Bursa");

        db.getAllLabels();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, db.getAllLabels());
        //Toast.makeText(this, ""+dataAdapter.getCount(), Toast.LENGTH_SHORT).show();
        if (dataAdapter.getCount() == 0) {
            db.insertLabel("Nevsehir");
            db.insertLabel("Marmaris");
            db.insertLabel("Antalya");
            db.insertLabel("Bursa");
            dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, db.getAllLabels());
        }
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


    }

    private void loadTextData() {
        SqliteHelper db=new SqliteHelper(getApplicationContext());
        Cursor res=db.ilce_metin_cek();
        if (res.getCount()==0){

            db.ilce_metin_ekle("Avanos","Avanos, Nevşehir İline bağlı bir ilçedir. Nevşehir'in 18 km kuzeyinde olan yerleşiminin, Antik Devirdeki adı Venessa, Zuwinasa ya da Ouenasadır. Çok sayıda çanak çömlek atölyesi bulunan ilçede seramik yapım geleneği Hititlerden beri süregelmektedir.Merkez bucağına bağlı 3, Özkonak bucağına bağlı 10 ve Topaklı bucağına bağlı 5 köyü vardır.","38.71815380243403","34.84956566212165");
            db.ilce_metin_ekle("Göreme"," Nevşehir ili Merkez ilçesine bağlı bir beldedir. Geçim kaynağı turizmdir.\n" + "\n" + "Göreme kasabasının eski isimleri 'Matiana, Korama, Maccan ve Avcılar'dır. Göreme ile ilgili 6. yüzyıla ait bir belgede ilk olarak 'Korama' adına rastlanıldığından dolayı en eski adının bu olduğu düşünülmektedir.","38.64455709445237","34.83042669918836");
            db.ilce_metin_ekle("Ürgüp"," Nevşehir İlinin 20 km doğusunda olan ilçesidir ve Kapadokya bölgesinin önemli merkezlerinden birisidir. Göreme'de olduğu gibi tarihsel süreç içerisinde çok sayıda isme sahip olmuştur." +
                    " Bizans döneminde Osiana (Assiana), Hagios Prokopios (Prokopi); Selçuklular döneminde Başhisar; Osmanlılar zamanında Burgut kalesi; Cumhuriyetin ilk yıllarından itibaren de Ürgüp adıyla anılmıştır.","38.637255831360456","34.91237089574108");
            db.ilce_metin_ekle("Kız Kumu","Orhaniye‘nin en popüleri Kızkumu Plajı olup, Marmaris geziniz sırasında muhakkak ki gitmenizi tavsiye ederim. Marmaris’e 30 km uzaklıkta konumlanan plaj, adını Bybassos Kralı’nın kızı için anlatılan hikayeden alıyor.","36.76120153345865","28.129752444887288");
            db.ilce_metin_ekle("Turgut","Turgut Köyü’nün içinde dört metrelik yüksekten akan Turgut Şelalesi, yazın dahi serin olan bölgede yalnızca bir tane değil pek çok farklı şelale bulunuyor. " +
                    "Buz gibi suyu ve yemyeşil doğası ile ziyaretçilerin uğrak yeri haline gelen şelale her mevsim ziyaret etmeniz için uygun.","36.731157862253994","28.15565884035792");
            db.ilce_metin_ekle("İcmeler","Merkeze yakınlığı sebebiyle çok fazla tercih edilen İçmeler Plajı Marmaris merkeze sadece 8 km uzaklıkta. Denizin berraklığı ve dinginliğiyle birlikte güzel bir deniz keyfi yapabilirsiniz. " +
                    "Plajın içindeki tesislerde yeme-içme durumunuz olduğunda şezlong ve şemsiyelere para ödemiyorsunuz. Tabi dilerseniz sadece havlunuzu omzunuza atıp da gelebilirsiniz.","36.803743","28.233852");
            db.ilce_metin_ekle("Side","Antalya’nın en önemli antik kentlerinden bir diğeri ise Side Antik Kenti. Antik dönemde Pamfilya’nın liman kentlerinden olan Side’nin geçmişi" +
                    " M.Ö. 8. yüzyıla, Hititlere kadar uzanıyor. 12. yüzyıldan itibaren terk edilmiş kentten günümüze pek çok tarihi kalıntı ulaşmış.","36.7680866084601","31.390278684539602");
            db.ilce_metin_ekle("Konyaaltı","Antalya’nın en güzel plajları arasında bulunan Konyaaltı Plajı, 1.5 km uzunluğunda sahil şeridine sahip. Mavi Bayraklı denizi ile Konyaaltı’nda keyifle güneşlenebilir, su sporları aktiviteleri yapabilirsiniz.","36.86719168926575","30.64410056823963");
            db.ilce_metin_ekle("Patara Antik Kenti","Antalya’da görmeniz gereken yerler arasında Patara Antik Kenti’ni de eklemelisiniz. Likya medeniyetine başkentlik yapmış bir sahil kasabası olan Patara, arkeolojik " +
                    "ve tarihi değerlerinin yanı sıra Caretta Caretta’ların milyonlarca yıldır yumurtlamak için uğradıkları ender sahiller arasında bulunuyor.","36.26158259884113","29.315892393794797");
            db.ilce_metin_ekle("teleferik","Türkiye’nin ilk Teleferiği olan Bursa Teleferik; açıldığı günden (29 Ekim 1963) 2012 yılına kadar aynı teknoloji ile hizmet vermiştir. 7 Haziran 2014 tarihinde teknolojisi, altyapısı ve istasyonları tamamen yenilenerek " +
                    "bambaşka bir yolculuk sunmak üzere yola koyulmuştur.","40.171057338702305","29.083315947848323");
            db.ilce_metin_ekle("Ulu Cami","Bursa Ulu Cami, Orhan Gazi Parkı'nın bulunduğu geniş bir alana, Yıldırım Bayezid zamanında, 1396-1400 yılları arasında yapılmıştır. " +
                    "Osmanlı camileri arasında çok kubbeli anıtsal yapıların ilkidir. Ulu Cami’nin on iki büyük dört köşeli paye üzerine oturan 20 kubbesi bulunmaktadır. ","40.18416327652187","29.062323736210377");
            db.ilce_metin_ekle("Milli Park","Milli Park’a adını veren Uludağ’ın mitolojideki adı OLYMPOSMYSİOS’ tur. Uludağ, Osmanlı İmparatorluğu zamanında Keşiş Dağı olarak anılmış ve 1925 yılında şimdiki adı olan ULUDAĞ adını almıştır.","40.112384721214426","29.0740268134511");
        }

        while(res.moveToNext()){

            isimler.add(res.getString(1));
            metinler.add(res.getString(2));



        }
    }
        @Override
        public void onItemSelected (AdapterView < ? > parent, View view,int position,
        long id){
            // On selecting a spinner item
            label = parent.getItemAtPosition(position).toString();

            if (label == parent.getItemAtPosition(0).toString()) {
                // textView.setText("merhaba");
                deger = 0;
                viewPager.setAdapter(adapter);

            } else if (label == parent.getItemAtPosition(1).toString()) {
                //textView.setText("Aleykümselam");
                deger = 1;
                viewPager.setAdapter(adapter2);
            } else if (label == parent.getItemAtPosition(2).toString()) {
                deger = 2;
                viewPager.setAdapter(adapter3);
            } else {
                deger = 3;
                viewPager.setAdapter(adapter1);
            }


        }

        @Override
        public void onNothingSelected (AdapterView < ? > arg0){
            // TODO Auto-generated method stub

        }
    public void onBackPressed() {
        Intent get=getIntent();
        Intent intent2 = new Intent(this, MenuActivity.class);
        intent2.putExtra("mail",get.getStringExtra("mail"));
        startActivity(intent2);
        finish();
    }


    }