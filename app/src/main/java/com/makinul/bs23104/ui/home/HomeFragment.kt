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
import com.makinul.bs23104.data.ProductResponse
import com.makinul.bs23104.data.model.Product
import com.makinul.bs23104.databinding.FragmentHomeBinding
import com.makinul.bs23104.utils.Extensions.gone
import com.makinul.bs23104.utils.Extensions.visible
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()

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

        return binding.root
    }

    private fun prepareObserver() {
        viewModel.products.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                when (it) {
                    is Data.Error -> {
                        binding.statusLay.root.visible()
                        binding.statusLay.statusMessage.text = it.message
                    }

                    Data.Loading -> {
                        binding.statusLay.root.visible()
                        binding.statusLay.progressBar.visibility = View.VISIBLE
                    }

                    is Data.Success -> {
                        binding.statusLay.root.gone()
                        val data = it.data
                        items.clear()
                        if (data.products.isNotEmpty()) {
                            items.addAll(data.products)
                        }
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchProducts()
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
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
            adapter = this@HomeFragment.adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}