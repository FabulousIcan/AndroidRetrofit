package com.example.android.javadevelopersinlagos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.pagination_list);
        ///OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder
//                .client(
//                        httpClient.build()
//                )
                .build();

        GitHubClient client = retrofit.create(GitHubClient.class);


        Call<List<GitHubRepo>> call = client.reposForUser("fs-opensource");

        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
                List<GitHubRepo> repos = response.body();

                listview.setAdapter(new GitHubRepoAdapter(MainActivity.this, repos));



            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

//        @Override
//        public boolean onCreateOptionsMenu(Menu menu){
//            getMenuInflater().inflate(R.menu.main, menu);
//            return true;
//        }
//
//        @Override
//        public boolean onOptionsItemSelected (MenuItem item){
//            int menuItemThatWasSelected = item.getItemId();
//            if (menuItemThatWasSelected == R.id.action_search) {
//                Context context = MainActivity.this;
//                String message = "Search Clicked";
//                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
//            }
//            return super.onOptionsItemSelected(item);
//        }
    }
}

