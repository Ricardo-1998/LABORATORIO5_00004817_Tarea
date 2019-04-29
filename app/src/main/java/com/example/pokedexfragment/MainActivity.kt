package com.example.pokedexfragment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.main_principal, Fragmento()).commit()
        if(fragmento_2 != null){
            supportFragmentManager.beginTransaction().replace(R.id.fragmento_2, Fragmento2()).commit()
        }
    }
}
