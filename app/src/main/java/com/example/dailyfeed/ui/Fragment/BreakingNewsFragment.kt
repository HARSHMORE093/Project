package com.example.dailyfeed.ui.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyfeed.R
import com.example.dailyfeed.adapters.NewsAdapter
import com.example.dailyfeed.databinding.FragmentBreakingNewsBinding
import com.example.dailyfeed.ui.NewsActivity
import com.example.dailyfeed.ui.NewsViewModel
import com.example.dailyfeed.utils.Resource
import com.example.dailyfeed.utils.constants.Companion.Query_Size

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    private var _binding: FragmentBreakingNewsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        setUpRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle=Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }
        viewModel.BreakingNews.observe(viewLifecycleOwner, Observer { res ->
            when (res) {
                is Resource.Sucess -> {
                    hidebar()
                    res.data?.let { NewsRes ->
                        newsAdapter.differ.submitList(NewsRes.articles.toList())
                        val totalPage=NewsRes.totalResults/ Query_Size+2
                        isLastPage=viewModel.breakingNewsPage==totalPage
                        if(isLastPage){
                            binding.rvBreakingNews.setPadding(0,0,0,0)
                        }
                    }
                }
                is Resource.Error -> {
                    hidebar()
                    res.message?.let { Mess ->
                        Log.e("Harsh", "An Error is Occur:$Mess")
                    }
                }
                is Resource.Loading->{
                    showbar()
                }
            }

        })
    }

    private fun hidebar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
        isLoad=false
    }

    private fun showbar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
        isLoad=true
    }
    //pagination

    var isLoad=false
    var isLastPage=false
    var isScrolling=false
    val ScrollListener =object :RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling=true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager=recyclerView.layoutManager as LinearLayoutManager
            val FirstItem= layoutManager.findFirstVisibleItemPosition()
            val VisibleItem=layoutManager.childCount
            val Total=layoutManager.itemCount

            val isNotLoadingAndNotLastPage=!isLoad && !isLastPage
            val isAtLast=FirstItem+VisibleItem>=Total
            val isNotAtFirst=FirstItem>=0
            val isTotalMoreVisisble=Total>=Query_Size
            val shouldPaginate=isNotLoadingAndNotLastPage && isAtLast && isNotAtFirst && isTotalMoreVisisble && isScrolling
            if(shouldPaginate){
                viewModel.getBreakingNews("in")
            }
        }
    }

    private fun setUpRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@BreakingNewsFragment.ScrollListener)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}