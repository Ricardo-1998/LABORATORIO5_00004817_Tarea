package com.example.pokedexfragment.Network

import android.net.Uri
import android.util.Log
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*

class NetworkUtilities {
    val POKEMON_API_BASE_URL = "https://pokeapi.co/api/v2/pokemon/?offset=0&limit=964"

    private val TAG = NetworkUtilities::class.java.simpleName

    fun buildUrl(): URL {
        val builtUri = Uri.parse(POKEMON_API_BASE_URL)
            .buildUpon()
            .build()

        val url = try {
            URL(builtUri.toString())
        } catch (e: MalformedURLException) {
            URL("")
        }

        Log.d(TAG, "Built URI $url")

        return url
    }

    fun getUrl(url : String): URL {
        val builtUri = Uri.parse(url)
            .buildUpon()
            .build()

        val url = try {
            URL(builtUri.toString())
        } catch (e: MalformedURLException) {
            URL("")
        }

        Log.d(TAG, "Built URI $url")

        return url
    }

    @Throws(IOException::class)
    fun getResponseFromHttpUrl(url: URL): String {
        val urlConnection = url.openConnection() as HttpURLConnection
        try {
            val `in` = urlConnection.inputStream

            val scanner = Scanner(`in`)
            scanner.useDelimiter("\\A")

            val hasInput = scanner.hasNext()
            return if (hasInput) {
                scanner.next()
            } else {
                ""
            }
        } finally {
            urlConnection.disconnect()
        }
    }
}