package com.nsv.categorytestnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.nsv.categorytestnews.databinding.ActivityMainBinding
import com.nsv.categorytestnews.databinding.ActivityRatingBinding

class RatingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRatingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRatingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        val ratingBar : RatingBar = binding.ratingBar
        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            Toast.makeText(this, "Thank you for rating us: $rating stars", Toast.LENGTH_SHORT).show()
        }



        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }
}