package com.rara.moviecatalog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rara.moviecatalog.R;
import com.rara.moviecatalog.model.Movie;

import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Movie>movies;

    public MovieAdapter (Context context){
        this.context = context;
        movies = new ArrayList<>();
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int i) {
        return movies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
        }
        ViewHolder viewHolder = new ViewHolder(view);
        Movie movie = (Movie) getItem(i);
        viewHolder.bind(movie);
        return view;
    }

    private class ViewHolder {
        private TextView tvJudul;
        private ImageView ivGambar;

        ViewHolder(View view) {
            tvJudul = view.findViewById(R.id.tvJudul);
            ivGambar = view.findViewById(R.id.ivGambar);
        }

        public void bind(Movie movie) {
            tvJudul.setText(movie.getJudul());
            ivGambar.setImageResource(movie.getPoster());
        }
    }
}
