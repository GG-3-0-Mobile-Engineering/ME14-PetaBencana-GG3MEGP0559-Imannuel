package com.example.petabencana.ui.home

import android.content.ContentValues
import android.content.ContentValues.TAG
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petabencana.R
import com.example.petabencana.databinding.FragmentMapsBinding
import com.example.petabencana.ui.home.adapter.LatestDisasterAdapter
import com.example.petabencana.utils.Resource

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapsFragment : Fragment() {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    private val boundsBuilder = LatLngBounds.Builder()

    private val viewModel: HomeViewModel by viewModel()

    private lateinit var adapter: LatestDisasterAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        adapter = LatestDisasterAdapter {
            Toast.makeText(requireContext(), it.properties?.disasterType, Toast.LENGTH_SHORT).show()
        }
        binding.latestDisasterRv.layoutManager = LinearLayoutManager(requireContext())
        binding.latestDisasterRv.adapter = adapter


        mapFragment?.getMapAsync { googleMap ->
            if (googleMap != null) {
                viewModel.result.observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Resource.Success -> {
                            googleMap.clear()

                            if (!result.data.result?.objects?.output?.geometries.isNullOrEmpty()) {
                                result.data.result?.objects?.output?.geometries?.forEach {
                                    val lat = it.coordinates[1]
                                    val lng = it.coordinates[0]
                                    Log.e(TAG, "tyoe: ${it.properties?.disasterType}")
                                    Log.e(TAG, "latlnt: /$lat / $lng")
                                    val latLng = LatLng(lat, lng)
                                    googleMap.addMarker(
                                        MarkerOptions()
                                            .position(latLng)
                                            .title(it.properties?.disasterType)
                                    )
                                    boundsBuilder.include(latLng)
                                }

                                val bounds: LatLngBounds = boundsBuilder.build()
                                googleMap.animateCamera(
                                    CameraUpdateFactory.newLatLngBounds(
                                        bounds,
                                        resources.displayMetrics.widthPixels,
                                        resources.displayMetrics.heightPixels,
                                        300
                                    )
                                )

                                adapter.setData(result.data.result?.objects?.output?.geometries!!)
                            } else{
                                Toast.makeText(requireContext(), "No disaster found", Toast.LENGTH_SHORT).show()
                            }


                        }

                        is Resource.Error -> {
                            googleMap.clear()

                            Log.e(ContentValues.TAG, "err: " + result.error)
                        }

                        is Resource.Loading -> {
                            Log.e(ContentValues.TAG, "loading: ")
                        }
                    }
                }

//

            } else {
                Log.e(ContentValues.TAG, "Google Map is null.")
            }
        }


        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(requireContext(), query, Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        binding.settingsButton.setOnClickListener {
            Toast.makeText(requireContext(), "Option", Toast.LENGTH_SHORT).show()
        }

        for (i in 0 until binding.disasterChipGroup.childCount) {
            val chip: Chip = binding.disasterChipGroup.getChildAt(i) as Chip

            chip.setOnCheckedChangeListener{ selectedChip, isChecked ->
                if(isChecked){
                    viewModel.setQuery(selectedChip.text.toString().toLowerCase())
                    Log.e(TAG, "onViewCreated: selected ${selectedChip.text}", )
                }else{
                    viewModel.setQuery("")
                    Log.e(TAG, "onViewCreated: unselected ${selectedChip.text}", )

                }

            }

        }






    }


}