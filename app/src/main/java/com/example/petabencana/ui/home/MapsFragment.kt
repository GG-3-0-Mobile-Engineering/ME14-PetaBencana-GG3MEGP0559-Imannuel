package com.example.petabencana.ui.home

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petabencana.R
import com.example.petabencana.databinding.FragmentMapsBinding
import com.example.petabencana.ui.home.adapter.LatestDisasterAdapter
import com.example.petabencana.utils.AreaList
import com.example.petabencana.utils.Resource
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.chip.Chip
import com.google.android.material.search.SearchView
import org.koin.androidx.viewmodel.ext.android.viewModel


class MapsFragment : Fragment() {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    private val boundsBuilder = LatLngBounds.Builder()

    private val viewModel: HomeViewModel by viewModel()

    private lateinit var recyclerView: RecyclerView
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
        recyclerView = binding.latestDisasterRv
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter


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
            } else {
                Log.e(ContentValues.TAG, "Google Map is null.")
            }
        }

        binding.searchBar.inflateMenu(R.menu.main_menu)
        binding.searchBar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu_settings-> {
                    Toast.makeText(requireContext(), "MENU SETTINGS", Toast.LENGTH_SHORT).show()
                    true

                }

                R.id.menu_period -> {
                    Toast.makeText(requireContext(), "MENU PERIOD", Toast.LENGTH_SHORT).show()
                    true
                }


                else -> {
                    true

                }


            }
        }



        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, AreaList.dataList)
        binding.areaListView.adapter = adapter

        binding.sv.addTransitionListener { searchView, previousState, newState ->
            if (newState == SearchView.TransitionState.SHOWING) {
                searchView.editText.addTextChangedListener(object : TextWatcher{
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                        
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        adapter.filter.filter(s.toString())
                    }

                    override fun afterTextChanged(s: Editable?) {
                    }

                })
            }
        }

        binding.sv
            .getEditText()
            .setOnEditorActionListener { v, actionId, event ->
                binding.searchBar.setText(binding.sv.getText())
                binding.sv.hide()
                viewModel.setArea(AreaList.getAreaId(binding.sv.text.toString()))
                false
            }

        binding.areaListView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = adapter.getItem(position)
            binding.sv.setText(selectedItem.toString())

        }


        for (i in 0 until binding.disasterChipGroup.childCount) {
            val chip: Chip = binding.disasterChipGroup.getChildAt(i) as Chip

            chip.setOnCheckedChangeListener{ selectedChip, isChecked ->
                if(isChecked){
                    viewModel.setDisaster(selectedChip.text.toString().toLowerCase())
                    Log.e(TAG, "onViewCreated: selected ${selectedChip.text}")
                }else{
                    viewModel.setDisaster("")
                    Log.e(TAG, "onViewCreated: unselected ${selectedChip.text}")

                }

            }

        }








    }


}