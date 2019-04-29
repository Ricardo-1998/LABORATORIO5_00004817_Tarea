package com.example.pokedexfragment

import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.pokedexfragment.Network.NetworkUtilities
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.fragmento_detalles.view.*
import java.io.IOException


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Fragmento2 : Fragment(){

    var url = ""
    var name = ""
    lateinit var vista : View


    companion object {
        fun newIntance(url: String, name: String) : Fragmento2{
            var instance = Fragmento2()
            instance.url = url
            instance.name = name
            return instance
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragmento_detalles, container, false)
        vista.namePoke.text = "Name: ${name}"

        if (url != "") FetchPokemonTask().execute()

        return vista
    }

    inner class FetchPokemonTask : AsyncTask<Void, Void, String>(){
        var pesoPokemon = 0.0
        var altoPokemon = 0; var tipo = ""; var image = ""
        override fun doInBackground(vararg params: Void?): String {
            var url = NetworkUtilities().getUrl(url)
            var pokeArray: JsonArray
            var pokeParse = JsonParser()
            var pokeOb: JsonObject
            try {
                var result = NetworkUtilities().getResponseFromHttpUrl(url)
                result = "[$result]"
                pokeArray = pokeParse.parse(result).asJsonArray
                var pokeElement = pokeArray.get(0)
                pokeOb = pokeElement.asJsonObject
                pesoPokemon = (pokeOb.get("weight").asDouble/10)
                altoPokemon = (pokeOb.get("height").asInt*10)

                var type = pokeOb.get("types").asJsonArray
                for (i in 0 .. (type.size()-1)){
                    var typeOb : JsonObject; var typeObjI : JsonObject
                    var typeIS : String; var pokePa = JsonParser();

                    typeIS = type.get(i).toString()
                    typeOb = pokePa.parse(typeIS).asJsonObject
                    typeObjI = typeOb.get("type").asJsonObject

                    if (pokeOb.get("sprites").asJsonObject.get("front_default").asString != null){
                        image = pokeOb.get("sprites").asJsonObject.get("front_default").asString
                    } else{
                        image = pokeOb.get("sprites").asJsonObject.get("front_female").asString
                    }

                    if (type.size() == i + 1) {
                        tipo += typeObjI.get("name").asString
                    } else {
                        tipo += typeObjI.get("name").asString + "/"
                    }
                }

            } catch (e : IOException){
                e.printStackTrace()
            }
            return ""
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            vista.pokeType.text = "Type: $tipo"
            vista.alturaPoke.text = "Height: $altoPokemon cm"
            vista.pesoPoke.text = "Weight: $pesoPokemon Kg"
            Glide.with(vista.context).load(image).into(vista.pokeimage)
        }
    }
}