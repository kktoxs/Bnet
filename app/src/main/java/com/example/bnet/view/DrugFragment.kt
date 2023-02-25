package com.example.bnet.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.bnet.R
import com.example.bnet.databinding.FragmentDrugBinding
import com.example.bnet.viewModel.MainViewModel

class DrugFragment : Fragment() {

    lateinit var viewModel: MainViewModel
    lateinit var binding: FragmentDrugBinding
    private  var isFavourite = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDrugBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBackButton()
        setupStarButton()
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel.currentDrug.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.progressCircular.visibility = View.GONE
                binding.titleTv.text = it.name
                binding.descriptionTv.text = it.description
                loadImage(it.image)
                loadIcon(it.categories.icon)
            }
        }
    }

    private fun setupBackButton() {
        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setupStarButton() {
        binding.starButton.setOnClickListener {
            isFavourite = !isFavourite
            if(isFavourite) {
                it.setBackgroundResource(R.drawable.ic_baseline_star_outline_24_green)
            } else{
                it.setBackgroundResource(R.drawable.ic_baseline_star_outline_24_grey)
            }
        }
    }

    private fun loadImage(image: String) {
        Glide
            .with(requireContext())
            .load("http://shans.d2.i-partner.ru$image")
            .into(binding.drugImage)
    }

    private fun loadIcon(icon: String) {
        Glide
            .with(requireContext())
            .load("http://shans.d2.i-partner.ru$icon")
            .into(binding.iconIv)
    }

    override fun onStop() {
        super.onStop()
        viewModel.closeCurrDrug()
    }


}