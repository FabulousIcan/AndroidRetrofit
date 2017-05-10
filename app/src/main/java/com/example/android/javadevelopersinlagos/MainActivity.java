package com.example.android.javadevelopersinlagos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
{
    private ListView listview;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.pagination_list);

        performNetworkCall();
    }

    private void performNetworkCall ()
    {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        GitHubClient client = retrofit.create(GitHubClient.class);

        Call<List<GitHubRepo>> call = client.reposForUser("fs-opensource");

        call.enqueue(new Callback<List<GitHubRepo>>()
        {
            @Override
            public void onResponse (Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response)
            {
                List<GitHubRepo> repos = response.body();

                GitHubRepoAdapter gitHubRepoAdapter = new GitHubRepoAdapter(MainActivity.this,
                                                                            repos);
                listview.setAdapter(gitHubRepoAdapter);
            }

            @Override
            public void onFailure (Call<List<GitHubRepo>> call, Throwable t)
            {
                Toast.makeText(MainActivity.this, "Sorry, an error occurred.", Toast.LENGTH_SHORT)
                     .show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        int selectedMenuItem = item.getItemId();
        if (selectedMenuItem == R.id.action_search)
        {
            Toast.makeText(MainActivity.this, "Search Clicked", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}

