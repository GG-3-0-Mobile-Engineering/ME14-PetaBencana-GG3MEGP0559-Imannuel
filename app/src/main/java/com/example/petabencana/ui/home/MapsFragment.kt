package com.example.petabencana.ui.home

import android.content.ContentValues
import android.content.ContentValues.TAG
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import com.example.petabencana.R
import com.example.petabencana.databinding.FragmentMapsBinding
import com.example.petabencana.utils.Resource

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.google.android.material.search.SearchBar

class MapsFragment : Fragment() {

    private var _binding : FragmentMapsBinding? = null
    private val binding get() = _binding!!


    private val boundsBuilder = LatLngBounds.Builder()



    private val viewModel : HomeViewModel by viewModel()

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
//        val sydney = LatLng(-34.0, 151.0)
//        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

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
        mapFragment?.getMapAsync { googleMap ->
            if (googleMap != null) {
                viewModel.result.observe(viewLifecycleOwner){result->
                    when(result){
                        is Resource.Success -> {
                            result.data.result?.objects?.output?.geometries?.forEach {
                                val lat = it.coordinates[1]
                                val lng = it.coordinates[0]
                                Log.e(TAG, "tyoe: ${it.properties?.disasterType}", )
                                Log.e(TAG, "latlnt: /$lat / $lng", )
                                val latLng = LatLng(lat, lng)
                                googleMap.addMarker(MarkerOptions()
                                    .position(latLng)
                                    .title(it.properties?.reportData?.report_type)
                                    .snippet("kedalaman banjir: ${it.properties?.reportData?.flood_depth}")
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
                        }

                        is Resource.Error -> {
                            Log.e(ContentValues.TAG, "err: "+ result.error, )
                        }

                        is Resource.Loading -> {
                            Log.e(ContentValues.TAG, "loading: ", )
                        }
                    }
                }

//

            } else {
                Log.e(ContentValues.TAG, "Google Map is null.")
            }
        }


        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(requireContext(), query, Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        binding.settingsButton.setOnClickListener{
            Toast.makeText(requireContext(), "Option", Toast.LENGTH_SHORT).show()
        }


    }




}