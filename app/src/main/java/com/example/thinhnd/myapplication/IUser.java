package com.example.thinhnd.myapplication;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by thinh.nd on 10/5/2017.
 */

public interface IUser {
    @GET("posts")
    Call<ArrayList<user>> listRepos();
}
