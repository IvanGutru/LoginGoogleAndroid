package mx.gutru.logingoogle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class InformacionCuenta : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacion_cuenta)
        val botonSalir = findViewById<Button>(R.id.buttonSalir)
        val nombreCuenta = findViewById<TextView>(R.id.nombre)
        val emailCuenta = findViewById<TextView>(R.id.email)
        val fotoCuenta  = findViewById<ImageView>(R.id.foto)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        var googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        var cuenta = GoogleSignIn.getLastSignedInAccount(this)

        if(cuenta!=null){
            nombreCuenta.text = cuenta.displayName
            val uri = cuenta.photoUrl
            Glide.with(this).load(uri).into(fotoCuenta)
            emailCuenta.text = cuenta.email

        }
        botonSalir.setOnClickListener{
            signOut(googleSignInClient)
        }
    }

    fun signOut(googleSignInClient: GoogleSignInClient) {
        googleSignInClient.signOut().addOnCompleteListener{
            Toast.makeText(this,"Successfully sogned out", Toast.LENGTH_LONG).show()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }

}
