package com.makinul.bs23104.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.makinul.bs23104.R
import com.makinul.bs23104.data.Data
import com.makinul.bs23104.data.model.Product
import com.makinul.bs23104.databinding.FragmentHomeBinding
import com.makinul.bs23104.utils.AppConstants
import com.makinul.bs23104.utils.Extensions.gone
import com.makinul.bs23104.utils.Extensions.visible
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialPage = 0
        items.clear()
    }

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        prepareObserver()

        binding.swipeRefreshLayout.setOnRefreshListener {
            initialPage = 0
            items.clear()
            viewModel.fetchProducts(initialPage)
            binding.swipeRefreshLayout.isRefreshing = false
        }

        return binding.root
    }

    private var isLoading = false // Track loading state
    private var initialPage = 0

    private fun prepareObserver() {
        viewModel.products.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                when (it) {
                    is Data.Error -> {
                        isLoading = false
                        if (items.isEmpty()) {
                            binding.statusLay.root.visible()
                            binding.statusLay.statusMessage.text = it.message
                        } else {

                        }
                    }

                    Data.Loading -> {
                        isLoading = true
                        if (items.isEmpty()) {
                            binding.statusLay.root.visible()
                            binding.statusLay.progressBar.visible()
                        }
                    }

                    is Data.Success -> {
                        isLoading = false
                        binding.statusLay.root.gone()
                        val data = it.data
                        initialPage += 1
                        if (data.products.isNotEmpty()) {
                            if (items.isEmpty()) {
                                items.addAll(data.products)
                                items.add(Product(key = AppConstants.KEY_FOOTER))
                            } else {
                                items.removeAt(items.lastIndex)
                                items.addAll(data.products)
                                items.add(Product(key = AppConstants.KEY_FOOTER))
                            }
                        }
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (items.isEmpty()) {
            viewModel.fetchProducts(initialPage)
        }

        prepareView()
    }

    private lateinit var adapter: ProductAdapter
    private val items: ArrayList<Product> = arrayListOf()

    private fun prepareView() {
        adapter = ProductAdapter(
            items,
            object : ProductAdapter.OnClickListener {
                override fun clickOnItem(
                    position: Int,
                    item: Product
                ) {
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                }
            }
        )

        val linearlayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerView.apply {
            layoutManager = linearlayoutManager
            adapter = this@HomeFragment.adapter
        }

        // Set up the scroll listener for "load more"
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) { // Check for scrolling down
                    val visibleItemCount = linearlayoutManager.childCount
                    val totalItemCount = linearlayoutManager.itemCount
                    val firstVisibleItemPosition =
                        linearlayoutManager.findFirstVisibleItemPosition()

                    if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount && totalItemCount > 0) {
                        // Trigger load more
                        viewModel.fetchProducts(initialPage)
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}