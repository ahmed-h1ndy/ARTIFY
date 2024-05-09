package com.ahmed.artify.Explore.Style

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmed.artify.Helpers.Style
import com.ahmed.artify.R
import com.ahmed.artify.RetrofitClass.ApiRequests
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class style_fragment : Fragment(R.layout.fragment_style) {

    lateinit var style_recycler: RecyclerView
    var styles: ArrayList<Style> = ArrayList()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        style_recycler = view.findViewById(R.id.style_recycler)
        val api = ApiRequests()


        if (styles.size!=0) {
            style_recycler.adapter = StylesAdapter(styles, requireContext())
            style_recycler.layoutManager = GridLayoutManager(context, 2)
        } else {

            GlobalScope.launch {
                Log.i("doing it", "inside coroutine")
                styles = api.initialize_styles {  }
                Log.i("doing it", "list size = ${styles.size}")
                style_recycler.adapter = StylesAdapter(styles,requireContext())
                style_recycler.layoutManager = GridLayoutManager(context, 2)
            }

        }

    }

}