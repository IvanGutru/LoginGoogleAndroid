package mx.gutru.logingoogle

import android.accounts.Account
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

const val RC_SIGN_IN : Int = 0
class MainActivity : AppCompatActivity() {

    private lateinit var signInButton : SignInButton
    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var googleSignInOptions: GoogleSignInOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions)

        signInButton = findViewById(R.id.sign_in_button)

        /*signInButton.setOnClickListener{
             val cuenta = GoogleSignIn.getLastSignedInAccount(this)
             mostrarInformacionCuenta(cuenta)
        }*/
        signInButton.setOnClickListener { view:View -> signIn()}
    }
    fun signIn(){
        val singInIntent = googleSignInClient.signInIntent
        startActivityForResult(singInIntent, RC_SIGN_IN)
    }
   /* private fun mostrarInformacionCuenta(cuenta: GoogleSignInAccount?) {
        val intent=  Intent(this,InformacionCuenta::class.java).apply {
            putExtra("posicion",cuenta)
        }
        this.startActivity(intent)
    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val cuenta = completedTask.getResult(ApiException::class.java)
            startActivity(Intent(this,InformacionCuenta::class.java))
        } catch (e: ApiException) {
            Log.w("Google Sign In Error", "SignInResult: failed code = "+e.statusCode)
            Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
        }
    }
     override fun onStart(){
        val cuenta =GoogleSignIn.getLastSignedInAccount(this)
        if(cuenta!=null){
            startActivity(Intent(this,InformacionCuenta::class.java))
        }
        super.onStart()
    }
}
