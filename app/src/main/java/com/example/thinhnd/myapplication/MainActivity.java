package com.example.thinhnd.myapplication;

import android.content.Context;

import android.net.ConnectivityManager;

import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import retrofit2.Call;
import retrofit2.Callback;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    ToggleButton toggleBtn;
    boolean enable;
    Presenter mPresenter;
    Network mCurrentWiFiNetwork;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toggleBtn= (ToggleButton) findViewById(R.id.testTg);
        toggleBtn.setChecked(false);
        enable=false;
        final Handler handler=new Handler();

        toggleBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(enable==false)
                {
                    enable=true;
                    Log.e("THINH", "3g: On" );
                    mPresenter=new ImpPresenter(new View() {
                        @Override
                        public void dataTest(ArrayList<user> listUser) {
                            Toast.makeText(MainActivity.this,listUser.size()+"",Toast.LENGTH_LONG).show();
                        }
                    });
                    Runnable runnable=new Runnable() {
                        @Override
                        public void run() {
                            mPresenter.getDataWithRetrofit();
                        }
                    };
                    if(enable==true)
                    {
                        handler.postDelayed(runnable,5000);
                    }

                   // getDataWithRetrofit();
                    runnable.run();


                }else
                {
                    enable=false;
                    Toast.makeText(MainActivity.this,"Stoped!",Toast.LENGTH_LONG).show();
                    Log.e("THINH", "3g: Off" );
                }

            }
        });
    }

    public void getDataWithRetrofit()
    {
         final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IUser service = retrofit.create(IUser.class);
        Call<ArrayList<user>> call = service.listRepos();
       call.enqueue(new Callback<ArrayList<user>>() {
           @Override
           public void onResponse(Call<ArrayList<user>> call, Response<ArrayList<user>> response) {
               ArrayList<user> listUser=new ArrayList<user>();
               listUser.addAll(response.body());
               Log.e("THINH", "onResponse: " + listUser.size());
               if(!listUser.isEmpty())
               {
                   Toast.makeText(MainActivity.this,listUser.get(0).getTitle(),Toast.LENGTH_LONG).show();
               }
               else
               {
                   Toast.makeText(MainActivity.this,"ko co",Toast.LENGTH_LONG).show();
               }
           }

           @Override
           public void onFailure(Call<ArrayList<user>> call, Throwable t) {
                t.printStackTrace();
           }
       });
    }



}
