package com.ahmed.artify.Explore.Style

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmed.artify.Helpers.Style
import com.ahmed.artify.R
import com.ahmed.artify.RetrofitClass.ApiRequests


class style_fragment : Fragment(R.layout.fragment_style) {

    lateinit var style_recycler: RecyclerView
    lateinit var styles: ArrayList<Style>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        style_recycler = view.findViewById(R.id.style_recycler)
        val api = ApiRequests()


        if (api.styles != null) {
            style_recycler.adapter = StylesAdapter(api.styles, requireContext())
            style_recycler.layoutManager = GridLayoutManager(context, 2)
        } else {
            api.initialize_styles { styles ->
                style_recycler.adapter = StylesAdapter(styles,requireContext())
                style_recycler.layoutManager = GridLayoutManager(context, 2)
            }
        }

    }

}