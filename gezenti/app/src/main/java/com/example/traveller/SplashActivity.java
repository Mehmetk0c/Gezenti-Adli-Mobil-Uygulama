package com.example.traveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        LottieAnimationView animView = findViewById(R.id.lottieAnimation);
        animView.setAnimation("splash.json");
        animView.playAnimation();
        SqliteHelper db=new SqliteHelper(getApplicationContext());
        Thread timerThread = new Thread(){
            public void run(){
                try{

                    sleep(4550);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    if(db.getLast2()==null || db.getLast2()==""){
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);

                    }
                    else {
                        Intent intent = new Intent(SplashActivity.this,MenuActivity.class);
                        intent.putExtra("mail",db.getLast());
                        startActivity(intent);
                    }
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}