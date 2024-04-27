package com.example.githubuser.data.Retrofit;

import com.example.githubuser.data.response.ItemsItem;
import com.example.githubuser.data.response.ResponseDetailUser;
import com.example.githubuser.data.response.ResponseGithubUser;

import retrofit2.Call;
import retrofit2.http.*;
public interface ApiService {

    @Headers({"Authorization: token "})
    @GET("search/users")
    Call<ResponseGithubUser> getUsers(@Query("q") String query);

    @Headers({"Authorization: token"})
    @GET("users/{username}")
    Call<ResponseDetailUser> getUsersDetail(@Path("username")String username);
}

