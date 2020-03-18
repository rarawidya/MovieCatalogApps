package com.rara.moviecatalog.movie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rara.moviecatalog.R
import com.rara.moviecatalog.api.ApiRepository
import com.rara.moviecatalog.model.Movie
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item.view.*

class MovieAdapter(
    private val context: Context,
    private val listMovie: MutableList<Movie>,
    private val onClickListener: (Movie) -> Unit

) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    fun setData(movie: List<Movie>) {
        listMovie.clear()
        listMovie.addAll(movie)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
    )

    override fun getItemCount(): Int = listMovie.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, listMovie[position], onClickListener)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(context: Context, movie: Movie, onClickListener: (Movie) -> Unit) {
            itemView.tvJudul.text = movie.title
            Glide.with(context).load(ApiRepository.BASE_IMAGE + movie.poster_path)
                .into(itemView.ivGambar)
            containerView.setOnClickListener { onClickListener(movie) }
        }

    }

}