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


public class TVShowFragment extends Fragment {
    private String[] dataJudul, dataSinopsis;
    private TypedArray dataPoster;
    private MovieAdapter tvAdapter;
    private RecyclerView rvTvshow;
    private ArrayList<Movie> listMovie = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tvshow, container, false);

        tvAdapter = new MovieAdapter(getContext());
        rvTvshow = view.findViewById(R.id.rvTvshow);
        rvTvshow.addItemDecoration(new DividerItemDecoration(rvTvshow.getContext(), DividerItemDecoration.VERTICAL));
        rvTvshow.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTvshow.setAdapter(tvAdapter);

        addItem();
        return view;

    }

    private void addItem() {
        dataJudul = getResources().getStringArray(R.array.data_judul_tv);
        dataSinopsis = getResources().getStringArray(R.array.data_sinopsis_tv);
        dataPoster = getResources().obtainTypedArray(R.array.data_poster_tv);
        listMovie = new ArrayList<>();

        for (int i = 0; i < dataJudul.length; i++) {
            Movie movie = new Movie();
            movie.setJudul(dataJudul[i]);
            movie.setSinopsis(dataSinopsis[i]);
            movie.setPoster(dataPoster.getResourceId(i, -1));
            listMovie.add(movie);

        }
        tvAdapter.setListMovie(listMovie);
    }
}
