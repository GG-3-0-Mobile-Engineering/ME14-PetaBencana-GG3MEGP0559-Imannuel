package com.imannuel.petabencana.ui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.imannuel.petabencana.R
import com.imannuel.petabencana.databinding.FragmentSavedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class SavedFragment : Fragment() {

    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SavedDisasterAdapter

    private val viewModel: SavedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SavedDisasterAdapter(
            listener = {
                viewModel.deleteSavedUrunDaya(it.pkey.toString())
            },
            listener2 = {
                val bundle = Bundle()
                bundle.putParcelable("key_data", it)
                findNavController().navigate(
                    R.id.action_savedFragment_to_savedDetailFragment,
                    bundle
                )
            }
        )

        binding.savedDisasterRv.layoutManager = LinearLayoutManager(requireContext())
        binding.savedDisasterRv.adapter = adapter

        viewModel.getSavedUrunDaya().observe(viewLifecycleOwner) {
            adapter.setData(it)
        }


    }


}