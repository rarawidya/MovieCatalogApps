package com.rara.moviecatalog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.rara.moviecatalog.model.Movie;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvJudul, tvOverview;
        ImageView ivGambar;

        tvJudul = findViewById(R.id.tvJudulMovie);
        tvOverview = findViewById(R.id.tvOverview);
        ivGambar = findViewById(R.id.ivPosterMovie);

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        setTitle(movie.getJudul());

        tvJudul.setText(movie.getJudul());
        tvOverview.setText(movie.getDeskripsi());
        ivGambar.setImageResource(movie.getPoster());
    }
}
