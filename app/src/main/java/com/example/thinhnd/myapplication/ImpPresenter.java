package com.example.thinhnd.myapplication;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by thinh.nd on 10/9/2017.
 */

public class ImpPresenter implements Presenter {
    private View newView;

    public ImpPresenter(View newView) {
        this.newView = newView;
    }

    @Override
    public void getDataWithRetrofit() {
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
                  newView.dataTest(listUser);
                }

            }

            @Override
            public void onFailure(Call<ArrayList<user>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
