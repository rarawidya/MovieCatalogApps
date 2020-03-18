package com.rara.moviecatalog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rara.moviecatalog.adapter.MovieAdapter;
import com.rara.moviecatalog.model.Movie;

import java.util.ArrayList;

import static android.os.Looper.prepare;

public class MainActivity extends AppCompatActivity {
    private String[] dataJudul;
    private String[] dataOverview;
    private TypedArray dataPoster;
    private MovieAdapter movieAdapter;
    private ListView lvMovie;
    private ArrayList<Movie>movies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieAdapter = new MovieAdapter(this);
        lvMovie = findViewById(R.id.lvMovie);

        lvMovie.setAdapter(movieAdapter);

        prepare();
        addItem();
        lvMovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(MainActivity.this, movies.get(i).getJudul(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_MOVIE, movies.get(i));
                startActivity(intent);
            }
        });

    }

    private void addItem() {
        movies = new ArrayList<>();
         for (int i=0; i < dataJudul.length; i++){
             Movie movie = new Movie();
             movie.setJudul(dataJudul[i]);
             movie.setDeskripsi(dataOverview[i]);
             movie.setPoster(dataPoster.getResourceId(i,-1));
             movies.add(movie);
         }
         movieAdapter.setMovies(movies);
    }
    private void prepare (){
        dataJudul = getResources().getStringArray(R.array.data_judul);
        dataOverview = getResources().getStringArray(R.array.data_overview);
        dataPoster = getResources().obtainTypedArray(R.array.data_poster);
    }
}
