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

            db.ilce_metin_ekle("Avanos","Avanos, Nev??ehir ??line ba??l?? bir il??edir. Nev??ehir'in 18 km kuzeyinde olan yerle??iminin, Antik Devirdeki ad?? Venessa, Zuwinasa ya da Ouenasad??r. ??ok say??da ??anak ????mlek at??lyesi bulunan il??ede seramik yap??m gelene??i Hititlerden beri s??regelmektedir.Merkez buca????na ba??l?? 3, ??zkonak buca????na ba??l?? 10 ve Topakl?? buca????na ba??l?? 5 k??y?? vard??r.","38.71815380243403","34.84956566212165");
            db.ilce_metin_ekle("G??reme"," Nev??ehir ili Merkez il??esine ba??l?? bir beldedir. Ge??im kayna???? turizmdir.\n" + "\n" + "G??reme kasabas??n??n eski isimleri 'Matiana, Korama, Maccan ve Avc??lar'd??r. G??reme ile ilgili 6. y??zy??la ait bir belgede ilk olarak 'Korama' ad??na rastlan??ld??????ndan dolay?? en eski ad??n??n bu oldu??u d??????n??lmektedir.","38.64455709445237","34.83042669918836");
            db.ilce_metin_ekle("??rg??p"," Nev??ehir ??linin 20 km do??usunda olan il??esidir ve Kapadokya b??lgesinin ??nemli merkezlerinden birisidir. G??reme'de oldu??u gibi tarihsel s??re?? i??erisinde ??ok say??da isme sahip olmu??tur." +
                    " Bizans d??neminde Osiana (Assiana), Hagios Prokopios (Prokopi); Sel??uklular d??neminde Ba??hisar; Osmanl??lar zaman??nda Burgut kalesi; Cumhuriyetin ilk y??llar??ndan itibaren de ??rg??p ad??yla an??lm????t??r.","38.637255831360456","34.91237089574108");
            db.ilce_metin_ekle("K??z Kumu","Orhaniye???nin en pop??leri K??zkumu Plaj?? olup, Marmaris geziniz s??ras??nda muhakkak ki gitmenizi tavsiye ederim. Marmaris???e 30 km uzakl??kta konumlanan plaj, ad??n?? Bybassos Kral?????n??n k??z?? i??in anlat??lan hikayeden al??yor.","36.76120153345865","28.129752444887288");
            db.ilce_metin_ekle("Turgut","Turgut K??y?????n??n i??inde d??rt metrelik y??ksekten akan Turgut ??elalesi, yaz??n dahi serin olan b??lgede yaln??zca bir tane de??il pek ??ok farkl?? ??elale bulunuyor. " +
                    "Buz gibi suyu ve yemye??il do??as?? ile ziyaret??ilerin u??rak yeri haline gelen ??elale her mevsim ziyaret etmeniz i??in uygun.","36.731157862253994","28.15565884035792");
            db.ilce_metin_ekle("??cmeler","Merkeze yak??nl?????? sebebiyle ??ok fazla tercih edilen ????meler Plaj?? Marmaris merkeze sadece 8 km uzakl??kta. Denizin berrakl?????? ve dinginli??iyle birlikte g??zel bir deniz keyfi yapabilirsiniz. " +
                    "Plaj??n i??indeki tesislerde yeme-i??me durumunuz oldu??unda ??ezlong ve ??emsiyelere para ??demiyorsunuz. Tabi dilerseniz sadece havlunuzu omzunuza at??p da gelebilirsiniz.","36.803743","28.233852");
            db.ilce_metin_ekle("Side","Antalya???n??n en ??nemli antik kentlerinden bir di??eri ise Side Antik Kenti. Antik d??nemde Pamfilya???n??n liman kentlerinden olan Side???nin ge??mi??i" +
                    " M.??. 8. y??zy??la, Hititlere kadar uzan??yor. 12. y??zy??ldan itibaren terk edilmi?? kentten g??n??m??ze pek ??ok tarihi kal??nt?? ula??m????.","36.7680866084601","31.390278684539602");
            db.ilce_metin_ekle("Konyaalt??","Antalya???n??n en g??zel plajlar?? aras??nda bulunan Konyaalt?? Plaj??, 1.5 km uzunlu??unda sahil ??eridine sahip. Mavi Bayrakl?? denizi ile Konyaalt?????nda keyifle g??ne??lenebilir, su sporlar?? aktiviteleri yapabilirsiniz.","36.86719168926575","30.64410056823963");
            db.ilce_metin_ekle("Patara Antik Kenti","Antalya???da g??rmeniz gereken yerler aras??nda Patara Antik Kenti???ni de eklemelisiniz. Likya medeniyetine ba??kentlik yapm???? bir sahil kasabas?? olan Patara, arkeolojik " +
                    "ve tarihi de??erlerinin yan?? s??ra Caretta Caretta???lar??n milyonlarca y??ld??r yumurtlamak i??in u??rad??klar?? ender sahiller aras??nda bulunuyor.","36.26158259884113","29.315892393794797");
            db.ilce_metin_ekle("teleferik","T??rkiye???nin ilk Teleferi??i olan Bursa Teleferik; a????ld?????? g??nden (29 Ekim 1963) 2012 y??l??na kadar ayn?? teknoloji ile hizmet vermi??tir. 7 Haziran 2014 tarihinde teknolojisi, altyap??s?? ve istasyonlar?? tamamen yenilenerek " +
                    "bamba??ka bir yolculuk sunmak ??zere yola koyulmu??tur.","40.171057338702305","29.083315947848323");
            db.ilce_metin_ekle("Ulu Cami","Bursa Ulu Cami, Orhan Gazi Park??'n??n bulundu??u geni?? bir alana, Y??ld??r??m Bayezid zaman??nda, 1396-1400 y??llar?? aras??nda yap??lm????t??r. " +
                    "Osmanl?? camileri aras??nda ??ok kubbeli an??tsal yap??lar??n ilkidir. Ulu Cami???nin on iki b??y??k d??rt k????eli paye ??zerine oturan 20 kubbesi bulunmaktad??r. ","40.18416327652187","29.062323736210377");
            db.ilce_metin_ekle("Milli Park","Milli Park???a ad??n?? veren Uluda???????n mitolojideki ad?? OLYMPOSMYS??OS??? tur. Uluda??, Osmanl?? ??mparatorlu??u zaman??nda Ke??i?? Da???? olarak an??lm???? ve 1925 y??l??nda ??imdiki ad?? olan ULUDA?? ad??n?? alm????t??r.","40.112384721214426","29.0740268134511");
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
                //textView.setText("Aleyk??mselam");
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