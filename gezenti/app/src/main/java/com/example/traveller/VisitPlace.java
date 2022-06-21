package com.example.traveller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class VisitPlace extends AppCompatActivity {
    Button button;
    Button button2;
    ListView listView;
    TextView title;
    String delete;
    String mail;
    ArrayList<String> list = new ArrayList<String>();
    ArrayList<Place> localList2 = new ArrayList<Place>();
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitplace);
        button = findViewById(R.id.deletplace);
        button2 = findViewById(R.id.button5);
        listView = findViewById(R.id.listplace);
        title = findViewById(R.id.textView4);
        SqliteHelper db = new SqliteHelper(getApplicationContext());
        Intent intent = getIntent();
        title.setText(db.getUsername(intent.getStringExtra("mail")) + " 'ın Gezi Listesi");
        mail = intent.getStringExtra("mail");
        list = db.getPlace(mail);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                delete = parent.getItemAtPosition(position).toString();
                Toast.makeText(VisitPlace.this, delete + " Seçildi", Toast.LENGTH_SHORT).show();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (delete != null) {
                    SqliteHelper db = new SqliteHelper(getApplicationContext());
                    localList2 = db.location();
                    Intent intent = new Intent(VisitPlace.this, MapsActivity.class);
                    for (Place place : localList2) {
                        if (delete.equals(place.name)) {
                            intent.putExtra("lat", place.lat);
                            intent.putExtra("longtt", place.longg);
                            startActivity(intent);
                        }
                    }
                    delete=null;
                } else
                    Toast.makeText(VisitPlace.this, "Lütfen Konum Seçin Yada Ekleyin!", Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (delete != null) {
                    db.deleteplace(delete, mail);
                    adapter.clear();
                    adapter.addAll(db.getPlace(mail));
                    adapter.notifyDataSetChanged();
                    delete=null;

                } else
                    Toast.makeText(VisitPlace.this, "Lütfen Silinecek Yeri Seçin", Toast.LENGTH_SHORT).show();


            }
        });


    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, MenuActivity.class);
        Intent get = getIntent();
        intent.putExtra("mail", get.getStringExtra("mail"));
        startActivity(intent);
        finish();
    }

}
