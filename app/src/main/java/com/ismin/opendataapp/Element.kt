package com.ismin.opendataapp

import java.io.Serializable
data class Element(var name: String, var photoURL: String, var posX: Float, var posY: Float, var personnes: ArrayList<Individual>): Serializable