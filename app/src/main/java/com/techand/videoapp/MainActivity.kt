package com.techand.videoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.techand.videoapp.playlist.PlayListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .add(R.id.container, PlayListFragment.newInstance())
                .commit()
        }
    }
}