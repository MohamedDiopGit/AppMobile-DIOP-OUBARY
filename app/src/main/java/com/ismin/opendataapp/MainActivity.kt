package com.ismin.opendataapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.Fuel
import org.json.JSONObject


class MainActivity : AppCompatActivity(), CarteFragment.OnFragmentInteractionListener, FragmentList.OnFragmentInteractionListener {

    override fun onFragmentInteraction(uri: Uri) {
         }
    var listElement: ArrayList<Element> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listElement = arrayListOf()

        getBDD()
        val buttInfo = findViewById<Button>(R.id.info_button)
        buttInfo.setOnClickListener {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            val fragment = InfoFragment()
            fragmentTransaction.replace(R.id.fragmentViewDuMain, fragment)
            fragmentTransaction.commit()
        }

        val buttCarte = findViewById<Button>(R.id.carteBouton)
        buttCarte.setOnClickListener {

            // Transmission
            val fragment = CarteFragment()
            val bundle = Bundle()
            bundle.putSerializable("element", listElement)
            fragment.arguments = bundle

            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentViewDuMain, fragment)
            fragmentTransaction.commit()
                //val intent = Intent(this, MapsActivity::class.java)
                //startActivity(intent)
        }

        //listener qui charge le fragment liste
        val listButton =  findViewById<Button>(R.id.listeBouton)
        listButton.setOnClickListener {
            var fragment = FragmentList()

            //data à transmettre
            val bundle = Bundle()
            bundle.putSerializable("element", listElement)
            fragment.arguments = bundle

            var fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentViewDuMain, fragment)
            fragmentTransaction.commit()
        }
    }

    override fun openDescription(position: Int) {
        val intent = Intent(this, InfoActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable("Element", listElement[position])
        intent.putExtras(bundle)
        this.startActivity(intent)
    }

    //on utilise Fuel pour récuperer les données
    fun getBDD() {
        val myurl: String = "https://bookshelfmdp.cleverapps.io/monuments"
        Fuel.get(myurl).responseString { request, response, result ->
            //do something with response
            result.fold({ d ->
                //do something with data
                val response = result.get()
                Toast.makeText(this, "It works, values: $result", Toast.LENGTH_SHORT).show()
                val jsonObject = JSONObject(response)
                val dataArray = jsonObject.getJSONArray("monuments")
                for (i in 0 until dataArray.length()) {
                    val dataobj = dataArray.getJSONObject(i)
                    //les liens ont changé du http au https ... on rajoute donc le s
                    val bonURL: String = dataobj.getJSONObject("image_principale").getString("url_original").substring(0, 4) + "s" + dataobj.getJSONObject("image_principale").getString("url_original").substring(4)

                    //on créer d'abord la table des personnes enterrées à cet endroit
                    var personnes: ArrayList<Individual> = arrayListOf()
                    val dataPersonneArray = dataobj.getJSONArray("personnalites")
                    for(i in 0 until dataPersonneArray.length()){
                        var dataobjPersonne = dataPersonneArray.getJSONObject(i)
                        var personne = Individual(dataobjPersonne.getString("nom"), dataobjPersonne.getString("activite"), dataobjPersonne.getString("date_naissance"), dataobjPersonne.getString("date_deces"), dataobjPersonne.getString("lien_wikipedia"))
                        personnes.add(personne)
                    }
                    val element: Element = Element(dataobj.getString("nom"), bonURL, dataobj.getJSONObject("node_osm").getString("latitude").toFloat(), dataobj.getJSONObject("node_osm").getString("longitude").toFloat(), personnes)
                    listElement.add(element)

                }

            }, { err ->
                //do something with error
            })
        }
    }

}
