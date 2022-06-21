package com.example.traveller;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.SupportMapFragment;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class MenuActivity extends AppCompatActivity {
    final String[]arrayMenu={
            "Çıkış",
            "Listem",
            "Yerler"


    };
    CircleMenu circleMenu;
    Button button;
    Button button2;
    Button button3;
    TextView welcome;
    String mail;
    public int indexitem=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SqliteHelper db=new SqliteHelper(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        Intent mailintent=getIntent();
        mail=mailintent.getStringExtra("mail");
        welcome=findViewById(R.id.textView);

        welcome.setText("Gezentiye Hoş Geldin \n"+db.getUsername(mail));

        circleMenu=findViewById(R.id.circlemenu);
        circleMenu.openMenu();
        circleMenu.setMainMenu(Color.parseColor("#cf4252"), R.drawable.ic_baseline_menu_24, R.drawable.ic_clear);

        circleMenu.addSubMenu(Color.parseColor("#00CD00"), R.drawable.mylist);
        circleMenu.addSubMenu(Color.parseColor("#f5c242"), R.drawable.gezen2);
        circleMenu.addSubMenu(Color.parseColor("#f5c1e3"), R.drawable.gezen);
        circleMenu.addSubMenu(Color.parseColor("#258CFF"), R.drawable.myexit);
        circleMenu.addSubMenu(Color.parseColor("#8760db"), R.drawable.close);
        circleMenu.setOnMenuSelectedListener(new OnMenuSelectedListener() {
            @Override
            public void onMenuSelected(int index) {
                indexitem=index;


            }

        }).setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {
            @Override
            public void onMenuOpened() {

            }

            @Override
            public void onMenuClosed() {
                switch (indexitem) {
                    case 3:
                        indexitem=-1;
                        onBackPressed();
                        break;
                    case 0:
                        indexitem=-1;
                        Intent ıntent2 = new Intent(MenuActivity.this, VisitPlace.class);
                        ıntent2.putExtra("mail", mail);
                        startActivity(ıntent2);
                        finish();
                        break;
                    case 1:
                        indexitem=-1;
                        Intent ıntent3 = new Intent(MenuActivity.this, MainActivity.class);
                        ıntent3.putExtra("mail", mail);
                        startActivity(ıntent3);
                        finish();
                        break;
                    case 2:
                        indexitem=-1;
                        Intent ıntent4 = new Intent(MenuActivity.this, placeUser.class);
                        ıntent4.putExtra("mail", mail);
                        startActivity(ıntent4);
                        finish();
                        break;
                    case 4:
                        indexitem=-1;
                        db.updateLst();
                        Intent ıntent5 = new Intent(MenuActivity.this, LoginActivity.class);
                        startActivity(ıntent5);
                        finish();
                        break;
                }

            }
        });

    }
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Uygulamadan çıkış yapmak istiyor musunuz?");
        builder.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                circleMenu.openMenu();
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}





