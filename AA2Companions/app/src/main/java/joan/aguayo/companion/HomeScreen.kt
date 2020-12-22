package joan.aguayo.companion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home_screen.*
import kotlinx.android.synthetic.main.activity_main.*

enum  class ProviderType {
    BASIC,
    FACEBOOK,
    GOOGLE

}

class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        val bundle :Bundle? = intent.extras
        val email: String? = bundle?.getString("email")
        val provider: String? = bundle?.getString("provider")
        setup(email?:"", provider?:"")
    }

    private fun setup(email:String,provider:String) {
        title = "Inicio"
        emailTextView.text = provider;
    }

    private fun setup(){
        title = "Página principal"
        logOut.setOnClickListener(){
            returnH()
        }
    }

    private fun returnH() {
        val authInt = Intent(this,AuthScreen::class.java)
        startActivity(authInt)
    }
}