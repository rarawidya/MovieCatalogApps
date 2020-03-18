package com.rara.moviecatalog.fragment;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rara.moviecatalog.R;
import com.rara.moviecatalog.adapter.MovieAdapter;
import com.rara.moviecatalog.model.Movie;

import java.util.ArrayList;


public class MovieFragment extends Fragment {
    private String[] dataJudul, dataSinopsis;
    private TypedArray dataPoster;
    private MovieAdapter movieAdapter;
    private RecyclerView rvMovie;
    private ArrayList<Movie> listMovie = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        movieAdapter = new MovieAdapter(getContext());
        rvMovie = view.findViewById(R.id.rvMovie);
        rvMovie.addItemDecoration(new DividerItemDecoration(rvMovie.getContext(), DividerItemDecoration.VERTICAL));
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovie.setAdapter(movieAdapter);

        addItem();

        return view;
    }

    private void addItem() {
        dataJudul = getResources().getStringArray(R.array.data_judul_movie);
        dataSinopsis = getResources().getStringArray(R.array.data_sinopsis_movie);
        dataPoster = getResources().obtainTypedArray(R.array.data_poster_movie);
        listMovie = new ArrayList<>();

        for (int i = 0; i < dataJudul.length; i++) {
            Movie movie = new Movie();
            movie.setJudul(dataJudul[i]);
            movie.setPoster(dataPoster.getResourceId(i, -1));
            movie.setSinopsis(dataSinopsis[i]);
            listMovie.add(movie);
        }
        movieAdapter.setListMovie(listMovie);
    }


}
