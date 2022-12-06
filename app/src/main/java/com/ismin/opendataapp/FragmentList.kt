package com.ismin.opendataapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class FragmentList : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_liste, container, false)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.liste)

        //on récupère les données transmise par MainActivity
        val data = arguments!!.getSerializable("element") as ArrayList<Element>

        //définition du layoutManager
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        //définition de l'adapter
        val adapter = ElementAdapter(data, ::onItemClick)
        recyclerView.adapter = adapter
        recyclerView.adapter?.notifyDataSetChanged()

        return(rootView)
    }

    //fonction passé en argument à l'adapter qui doit être executé lors de l'appuie sur un item
    private fun onItemClick(position: Int){
        listener?.openDescription(position)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun openDescription(position: Int)
    }
}
