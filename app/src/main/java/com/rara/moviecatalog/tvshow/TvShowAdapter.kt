package com.rara.moviecatalog.tvshow

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rara.moviecatalog.R
import com.rara.moviecatalog.api.ApiRepository
import com.rara.moviecatalog.model.TvShow
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item.view.*

class TvShowAdapter(
    private val context: Context,
    private val listTvShow: MutableList<TvShow>,
    private val onClickListener: (TvShow) -> Unit

) : RecyclerView.Adapter<TvShowAdapter.ViewHolder>() {

    fun setData(tvShow: List<TvShow>) {
        listTvShow.clear()
        listTvShow.addAll(tvShow)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
    )

    override fun getItemCount(): Int = listTvShow.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, listTvShow[position], onClickListener)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(context: Context, tvShow: TvShow, onClickListener: (TvShow) -> Unit) {
            itemView.tvJudul.text = tvShow.name
            Glide.with(context).load(ApiRepository.BASE_IMAGE + tvShow.poster_path)
                .into(itemView.ivGambar)
            containerView.setOnClickListener { onClickListener(tvShow) }
        }

    }

}