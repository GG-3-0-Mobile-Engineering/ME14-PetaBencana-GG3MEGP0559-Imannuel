package com.imannuel.petabencana.ui.saved.detail

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.gms.maps.model.LatLng
import com.imannuel.petabencana.data.model.Properties
import com.imannuel.petabencana.databinding.FragmentSavedDetailBinding
import com.imannuel.petabencana.utils.TimeUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class SavedDetailFragment : Fragment() {

    private var _binding: FragmentSavedDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var prop: Properties
    private var lat: Double = 0.0
    private var lon: Double = 0.0

    private val viewModel: SavedDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prop = arguments?.getParcelable("key_data")!!
        lat = arguments?.getDouble("lat") ?: 0.0
        lon = arguments?.getDouble("lon") ?: 0.0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (prop.imageUrl != null) {
            Glide.with(binding.root).load(prop.imageUrl).into(binding.imageViewDisaster)
        }


        val latLng = LatLng(lat, lon)
        val address = getAddress(latLng)

        binding.createdAtTv.text = "Created At: " + TimeUtils.getTimeAgo(prop.createdAt.toString())
        binding.sourceTv.text = "Source: " + prop.source
        binding.statusTv.text = "Status: " + prop.status
        binding.sisasterTypeTv.text = "Disaster Type: " + prop.disasterType

        if (address == "") {
            binding.addressTv.visibility = View.GONE
        } else {
            binding.addressTv.text = "Address: $address"

        }

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

    private fun getAddress(latLng: LatLng): String {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]
                return address.getAddressLine(0)
            }

            return ""
        } catch (e: Exception) {
            return ""
        }
    }

}