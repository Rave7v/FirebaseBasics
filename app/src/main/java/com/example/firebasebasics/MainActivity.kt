package com.example.firebasebasics

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONObject

private lateinit var database: DatabaseReference

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myDB = FirebaseDatabase.getInstance()
        database = myDB.reference

        //database.child("hijo").child("nieto1").child("nieto").setValue("Nieto old")
        val userID = findViewById<EditText>(R.id.etUserId)
        val userName = findViewById<EditText>(R.id.etUserName)
        val userMail = findViewById<EditText>(R.id.etUserEmail)
        val userToGet = findViewById<EditText>(R.id.etUserIdToGet)
        val btnSend = findViewById<Button>(R.id.btnSet)
        val btnGet = findViewById<Button>(R.id.btnGet)
        val userNameGet = findViewById<TextView>(R.id.MostrarUsuario)
        val userEmailGet = findViewById<TextView>(R.id.MostrarEmail)

        btnSend.setOnClickListener {
            writeNewUser(userID.text.toString(), userName.text.toString(), userMail.text.toString())
            userID.text.clear()
            userName.text.clear()
            userMail.text.clear()
        }

        btnGet.setOnClickListener {
            getUser(userToGet.text.toString(), userNameGet, userEmailGet)
            userToGet.text.clear()
        }
    }

    fun getUser(userId: String, userNameget: TextView, userEmailget: TextView){
        database.child("usuarios").child(userId).get().addOnSuccessListener { record ->
            val json = JSONObject(record.value.toString())
            Log.d("ValoresFirebase", "got value ${record.value}")
            userNameget.setText("Nombre del usuario: ${json.getString("nombre")}")
            userEmailget.setText("Email del usuario: ${json.getString("correo")}")

        }
    }

    fun writeNewUser(userId: String, name: String, email: String){
        val user = User(name, email)
        database.child("usuarios").child(userId).setValue(user)
    }
}

class User(name: String, email: String){
    val nombre = name
    val correo = email
}