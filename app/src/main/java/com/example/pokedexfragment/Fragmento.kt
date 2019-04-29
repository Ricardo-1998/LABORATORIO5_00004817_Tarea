package com.example.pokedexfragment

import android.content.Intent
import android.content.res.Configuration
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pokedexfragment.Models.Pokemon
import com.example.pokedexfragment.Models.Pokemones
import com.example.pokedexfragment.Network.NetworkUtilities
import com.example.pokedexfragment.adaptador.Adapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragmentolistado.view.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Fragmento : Fragment() {

    var listaAll : ArrayList<Pokemon> = ArrayList()
    lateinit var vista : View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragmentolistado, container, false)
        FetchPokemonTask().execute()
        return vista
    }

    private fun portClickListener(item : Pokemon){
        var intent = Intent(vista.context, Main2Activity::class.java)
        intent.putExtra("Name", item.name)
        intent.putExtra("Url", item.url)
        startActivity(intent)
    }

    private fun landClickListener(item : Pokemon){
        var instance = Fragmento2.newIntance(item.url, item.name)
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmento_2, instance)?.commit()
    }

    private fun initRecycler(){
        var adapter : Adapter

        if (vista.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            adapter = Adapter(
                listaAll,
                { pokemon: Pokemon -> portClickListener(pokemon) })
        } else{
            adapter = Adapter(
                listaAll,
                { pokemon: Pokemon -> landClickListener(pokemon) })
        }
        vista.rv_pokemonlist.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(vista.context)
            this.adapter = adapter
        }
    }

    private inner class FetchPokemonTask : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg query: String): String {

            var url = NetworkUtilities().buildUrl()
            var result = NetworkUtilities().getResponseFromHttpUrl(url)
            var gson = Gson()
            var lista = gson.fromJson(result, Pokemones::class.java)
            listaAll = lista.results
            return ""
        }

        override fun onPostExecute(pokemonInfo: String) {
            initRecycler()
        }
    }

}