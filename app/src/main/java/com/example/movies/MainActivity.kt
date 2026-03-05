package com.example.movies

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val adapter = MovieAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvMovies)

        val isLandscape = resources.configuration.orientation ==
                Configuration.ORIENTATION_LANDSCAPE

        recyclerView.layoutManager = if (isLandscape) {
            GridLayoutManager(this, 2)
        } else {
            LinearLayoutManager(this)
        }

        recyclerView.adapter = adapter

        loadMovies()
    }

    private fun loadMovies() {
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    ApiClient.api.getNowPlaying()
                }
                adapter.updateMovies(response.results)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}