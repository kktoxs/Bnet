package com.example.bnet.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bnet.R
import com.example.bnet.databinding.ActivityMainBinding
import com.example.bnet.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var drugsAdapter: DrugListAdapter
    private lateinit var binding: ActivityMainBinding
    private var searchIsActive = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.drugsList.observe(this) {
            drugsAdapter.updateList(it)
            binding.noResultsTv.isVisible = drugsAdapter.currentList.isEmpty()
        }
        setupListAdapter()
        setupToolbar()
    }

    private fun setupListAdapter() {
        searchIsActive = false
        viewModel.getDrugs(0)
        drugsAdapter = DrugListAdapter(this) {
            viewModel.getDrugs(it)
        }
        setupAdapter()

    }

    private fun setupSearchAdapter(search: String) {
        searchIsActive = true
        viewModel.getDrugsBySearch(search, 0)
        drugsAdapter = DrugListAdapter(this) {
            viewModel.getDrugsBySearch(search, it)
        }
        setupAdapter()

    }

    private fun setupAdapter() {
        binding.drugsRv.layoutManager = GridLayoutManager(this, 2)
        binding.drugsRv.adapter = drugsAdapter

        drugsAdapter.onDrugClickListener = {
            viewModel.getDrugById(it.id)
            supportFragmentManager.beginTransaction().replace(R.id.container, DrugFragment())
                .addToBackStack("list").commit()
        }
    }

    private fun setupToolbar() {
        setupSearchButton()
        setupSearchInput()
        setupBackButton()
    }

    private fun setupSearchButton() {
        binding.searchButton.setOnClickListener {
            binding.titleTv.visibility = View.GONE
            binding.searchInput.visibility = View.VISIBLE
            binding.searchButton.isClickable = false
            binding.backButton.isClickable = true
            binding.searchInput.setQuery(null,false)
        }
    }

    private fun setupSearchInput() {
        binding.searchInput.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) {
                    drugsAdapter.submitList(null)
                    setupSearchAdapter(p0)
                }
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun setupBackButton() {
        binding.backButton.setOnClickListener {
            binding.titleTv.visibility = View.VISIBLE
            binding.searchInput.visibility = View.GONE
            binding.searchButton.isClickable = true
            binding.backButton.isClickable = false
            if(searchIsActive)
                setupListAdapter()
        }
    }

}