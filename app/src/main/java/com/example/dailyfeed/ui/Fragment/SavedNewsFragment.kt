package com.example.dailyfeed.ui.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyfeed.R
import com.example.dailyfeed.adapters.NewsAdapter
import com.example.dailyfeed.databinding.FragmentSavedNewsBinding
import com.example.dailyfeed.databinding.FragmentSearchNewsBinding
import com.example.dailyfeed.ui.NewsActivity
import com.example.dailyfeed.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter:NewsAdapter
    private var _binding: FragmentSavedNewsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedNewsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as NewsActivity).viewModel
        setUpRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle=Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_articleFragment,
                bundle
            )
        }
        //swipe to delete Article in SavedNewsFragment
        val item=object :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val posi=viewHolder.adapterPosition
                val article=newsAdapter.differ.currentList[posi]
                viewModel.deleteArticle(article)
                Snackbar.make(view,"Succesfully deleted article",Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){viewModel.addArticle(article)}
                    show()
                }
            }
        }
        ItemTouchHelper(item).apply {
            attachToRecyclerView(binding.rvSavedNews)
        }
        //
        //save all article in recycler view mai
        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer {AllArti->
            newsAdapter.differ.submitList(AllArti)
        })
    }

    private fun setUpRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}