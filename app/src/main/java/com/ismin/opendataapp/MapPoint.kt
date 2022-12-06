package com.ismin.opendataapp

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class MapPoint  : ClusterItem {
    private var mSnippet: String = ""

    private var mTitle: String = ""
    private var mPosition: LatLng = LatLng(0.0, 0.0)


    constructor(lat: Double, lng: Double, title: String, snippet: String) {
        mPosition = LatLng(lat, lng)
        mTitle = title
        mSnippet = snippet
    }


    override fun getTitle(): String? {
        return mTitle
    }

    override fun getSnippet(): String? {
        return mSnippet
    }

    override fun getPosition(): LatLng? {
        return mPosition
    }
}