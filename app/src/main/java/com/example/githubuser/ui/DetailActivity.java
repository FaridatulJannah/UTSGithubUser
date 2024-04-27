package com.example.githubuser.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.githubuser.R;
import com.example.githubuser.data.Retrofit.ApiConfig;
import com.example.githubuser.data.Retrofit.ApiService;
import com.example.githubuser.data.response.ItemsItem;
import com.example.githubuser.data.response.ResponseDetailUser;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        progressBar = findViewById(R.id.progressBar);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String username = extras.getString("username");
            ApiService apiService = ApiConfig.getApiService();
            Call<ResponseDetailUser> userCall = apiService.getUsersDetail(username);

            TextView Nama = findViewById(R.id.tvName);
            TextView Username = findViewById(R.id.tvUsername);
            TextView Bio = findViewById(R.id.tvBio);
            ImageView imageView = findViewById(R.id.ivAvatar);

            showLoading(true);
            userCall.enqueue(new Callback<ResponseDetailUser>() {
                @Override
                public void onResponse(Call<ResponseDetailUser> call, Response<ResponseDetailUser> response) {
                    if (response.isSuccessful()){
                        showLoading(false);
                        ResponseDetailUser user = response.body();
                        if (user != null){
                            String name = "Name : " + user.getName();
                            String usernames = " " + user.getLogin();
                            String bio = "Bio : " + user.getBio();
                            String gambar = user.getAvatarUrl();

                            Nama.setText(name);
                            Username.setText(usernames);
                            Bio.setText(bio);
                            Picasso.get().load(gambar).into(imageView);
                        }else {
                            Toast.makeText(DetailActivity.this, "Failed to get user data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseDetailUser> call, Throwable t) {
                    Toast.makeText(DetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void showLoading(Boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}