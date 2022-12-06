package com.ismin.opendataapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        //définition des variables
        val intent = this.intent
        val bundle = intent.extras
        val backButton: ImageButton

        //on récupère les données
        val data = bundle!!.getSerializable("Element") as Element
        val (name, imageURL, posX, posY, personnes) = data

        //on affiche les données
        this.findViewById<TextView>(R.id.description).text = name
        var textBiography: StringBuffer = StringBuffer()
        for(personne in personnes){
            textBiography.append("<Strong>Nom :</Strong> ${personne.name}\"")
            textBiography.append("<br><Strong>Activité</Strong> ${personne.activité}\"")
            textBiography.append("<br><Strong>date naissance</Strong> ${personne.date_naissance}\"")
            textBiography.append("<br><Strong>date décès :</Strong> ${personne.date_mort}")
            textBiography.append("<br><a href = ${personne.lien_wikipedia}> en savoir plus : ${personne.lien_wikipedia} </a> <br><br>")
        }
        this.findViewById<TextView>(R.id.biography).text = Html.fromHtml(textBiography.toString())

        Picasso.get()
            .load(imageURL)
            .into(this.findViewById<ImageView>(R.id.photoDesc))

        //Listener qui termine l'activity et retourne au MainActivity
        backButton = this.findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
    }
}
