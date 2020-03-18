package com.rara.moviecatalog;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rara.moviecatalog.model.Movie;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView tvJudul, tvSinopsis;
        ImageView ivPoster;

        tvJudul = findViewById(R.id.tvJudulFilm);
        ivPoster = findViewById(R.id.ivPoster);
        tvSinopsis = findViewById(R.id.tvSinopsis);


        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        if (movie !=null){
            setTitle(movie.getJudul());

            tvJudul.setText(movie.getJudul());
            tvSinopsis.setText(movie.getSinopsis());
            ivPoster.setImageResource(movie.getPoster());
        }

    }
}
