package com.example.pokebook

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.pokebook.data.model.RecentSearches
import com.example.pokebook.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SearchAdapter
//    private val viewModel: PokemonViewModel by viewModels()
    private val viewModel: PokemonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        // 초기 화면에서 autoTransition을 막기위해서
        binding.motionLayout.jumpToState(R.id.start)
        searchPokemon()
        settingRecyclerView()

        viewModel.pokemon.observe(this) {
            binding.pokemon = it
        }

        viewModel.addSearch.observe(this) { searchList ->
            val sortedSearchList = searchList.sortedByDescending { it.time }
            adapter.setSearchList(sortedSearchList)
        }

        viewModel.deleteSearch.observe(this) { searchList ->
            adapter.setSearchList(searchList)
            Log.e("deleteSearch:","${searchList.size}")
        }

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        hideKeyboard()
        return super.dispatchTouchEvent(ev)
    }

    private fun searchPokemon() {
        binding.searchEditText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                controlMotionLayoutState()
                val search = binding.searchEditText.text.toString()
                viewModel.searchPokemon(search)
                viewModel.addSearch(search)
                hideKeyboard()
                binding.searchEditText.text.clear()
                binding.searchEditText.clearFocus()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun hideKeyboard() {
        val ims = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        ims.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    private fun controlMotionLayoutState() {
        if (binding.motionLayout.currentState == R.id.start) {
            binding.motionLayout.transitionToState(R.id.end)
        } else {
            binding.motionLayout.transitionToState(R.id.start)
        }

    }

    private fun settingRecyclerView() {
        adapter = SearchAdapter()
        adapter.setOnItemClick(object : SearchAdapter.OnItemClickListener {
            override fun onItemClick(recentSearches: RecentSearches) {
                controlMotionLayoutState()
                binding.searchEditText.setText(recentSearches.search)
                viewModel.searchPokemon(recentSearches.search)
                viewModel.addSearch(recentSearches.search)
                Log.e("onItemClick:","${recentSearches.time}")
            }

            override fun onDeleteClick(recentSearches: RecentSearches, position: Int) {
                viewModel.deleteSearch(recentSearches)
            }
        })

        binding.searchRecyclerView.adapter = adapter
        binding.searchRecyclerView.itemAnimator = null
    }
}