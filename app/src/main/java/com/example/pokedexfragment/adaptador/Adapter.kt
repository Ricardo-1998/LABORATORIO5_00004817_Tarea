package com.example.pokedexfragment.adaptador

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pokedexfragment.Models.Pokemon
import com.example.pokedexfragment.R
import kotlinx.android.synthetic.main.pokemon.view.*

class Adapter (val items: List<Pokemon>, val clickListener: (Pokemon) -> Unit) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Adapter.ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.pokemon, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) = holder.bind(items[pos], clickListener)

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(item: Pokemon, clickListener: (Pokemon) -> Unit) = with(itemView) {

            nombre.text = item.name

            nombre.setOnClickListener { clickListener(item) }
        }
    }
}