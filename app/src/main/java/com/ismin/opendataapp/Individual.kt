package com.ismin.opendataapp

import java.io.Serializable

data class Individual(var name: String, var activité: String, var date_naissance: String, var date_mort: String, var lien_wikipedia: String):
    Serializable