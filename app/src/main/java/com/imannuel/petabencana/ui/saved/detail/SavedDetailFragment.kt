package com.imannuel.petabencana.ui.saved.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.imannuel.petabencana.data.model.Properties
import com.imannuel.petabencana.databinding.FragmentSavedDetailBinding
import com.imannuel.petabencana.utils.TimeUtils
import org.koin.androidx.viewmodel.ext.android.viewModel


class SavedDetailFragment : Fragment() {

    private var _binding: FragmentSavedDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var prop: Properties

    private val viewModel: SavedDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prop = arguments?.getParcelable("key_data")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (prop.imageUrl != null) {
            Glide.with(binding.root).load(prop.imageUrl).into(binding.imageViewDisaster)
        }

        binding.createdAtTv.text = "Created At: " + TimeUtils.getTimeAgo(prop.createdAt.toString())
        binding.sourceTv.text = "Source: " + prop.source
        binding.statusTv.text = "Status: " + prop.status
        binding.sisasterTypeTv.text = "Disaster Type: " + prop.disasterType

        viewModel.isUrunDayaExist(prop.pkey.toString()).observe(viewLifecycleOwner) {
            if (it == 1) {
                binding.savedActionBtn.text = "DELETE FROM SAVED"
                binding.savedActionBtn.setOnClickListener {
                    viewModel.deleteUrunDaya(prop.pkey.toString())
                }
            } else {
                binding.savedActionBtn.text = "SAVE"
                binding.savedActionBtn.setOnClickListener {
                    viewModel.saveUrunDaya(prop)
                }
            }
        }

    }

}