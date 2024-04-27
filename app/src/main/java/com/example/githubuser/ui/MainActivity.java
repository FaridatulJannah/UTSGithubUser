package com.example.githubuser.ui;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.githubuser.R;
import com.example.githubuser.data.Retrofit.ApiConfig;
import com.example.githubuser.data.Retrofit.ApiService;
import com.example.githubuser.data.response.ItemsItem;
import com.example.githubuser.data.response.ResponseGithubUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycleview);

        ApiService apiService = ApiConfig.getApiService();
        Call<ResponseGithubUser> call = apiService.getUsers("farida");

        call.enqueue(new Callback<ResponseGithubUser>() {
            @Override
            public void onResponse(Call<ResponseGithubUser> call, Response<ResponseGithubUser> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ItemsItem> items = response.body().getItems();
                    adapter = new UserAdapter(items) ;
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                } else {
                    Toast.makeText(MainActivity.this, "Failed to get users", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseGithubUser> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}