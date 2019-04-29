package com.example.pokedexfragment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        var intent = intent
        var instance = Fragmento2.newIntance(intent.getStringExtra("Url"), intent.getStringExtra("Name"))
        supportFragmentManager.beginTransaction().replace(R.id.fragmento_2, instance).commit()
    }
}
