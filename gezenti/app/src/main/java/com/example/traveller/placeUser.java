package com.example.traveller;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class placeUser extends AppCompatActivity {
    TextView textlength;
    Button button;
    EditText place, info;
    FusedLocationProviderClient FusedLocationClient;
    String mail, place1, info1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userplace);
        button = findViewById(R.id.localbtn);
        place = findViewById(R.id.placetxt);
        info = findViewById(R.id.locationtxt);
        place1 = place.getText().toString();
        info1 = info.getText().toString();
        Intent intent = getIntent();
        textlength = findViewById(R.id.textView2);
        mail = intent.getStringExtra("mail");

        FusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                place1 = place.getText().toString();
                info1 = info.getText().toString();
                if ((place1.equals("") || info1.equals("") || info1.length() <= 50)) {
                    Toast.makeText(placeUser.this, "Lütfen Her Yeri Doldurun!", Toast.LENGTH_SHORT).show();
                } else {
                    if (ActivityCompat.checkSelfPermission(placeUser.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        getLocation();


                    } else {
                        ActivityCompat.requestPermissions(placeUser.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    }

                }

            }


        });
        info.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = info.getText().toString();
                int length = text.length();
                textlength.setText(length + "");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getLocation() {
        SqliteHelper db = new SqliteHelper(getApplicationContext());
        String[] list3 = new String[2];
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        FusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {

            @Override
            public void onComplete(@NonNull Task<Location> task) {
                double lat, longg;

                Location local = task.getResult();
                if (local != null) {
                    Geocoder geo = new Geocoder(placeUser.this, Locale.getDefault());
                    try {
                        List<Address> list = geo.getFromLocation(local.getLatitude(), local.getLongitude(), 1);
                        lat = list.get(0).getLatitude();
                        longg = list.get(0).getLongitude();
                        list3[0] = Double.toString(lat);
                        list3[1] = Double.toString(longg);
                        if (db.getCount(mail) <= 4) {
                            int residual = db.getCount(mail);
                            AlertDialog.Builder builder = new AlertDialog.Builder(placeUser.this);
                            builder.setCancelable(false);
                            builder.setMessage("En Fazla 5 Konum Eklenebilir " + residual + " Konum Eklediniz Eklemek İster Misiniz?");
                            builder.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db.insertUserLocal(mail, place1, info1, list3[0], list3[1], residual);
                                    Toast.makeText(placeUser.this, "Konumunuz Başarıyla Eklendi.", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(placeUser.this, "İncelemelerin Ardından Uygulamaya Eklenecektir.", Toast.LENGTH_SHORT).show();
                                    place.setText("");
                                    info.setText("");
                                }
                            });
                            builder.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        } else
                            Toast.makeText(placeUser.this, "5 Ten Fazla Konum Eklenemez!", Toast.LENGTH_SHORT).show();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }
    public void onBackPressed() {
        Intent get=getIntent();
        Intent intent2 = new Intent(this, MenuActivity.class);
        intent2.putExtra("mail",get.getStringExtra("mail"));
        startActivity(intent2);
        finish();
    }
}
