package joan.aguayo.companion

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class AuthScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message","In.Fib completa");
        analytics.logEvent("InitScreen", bundle);

        setup();
        /*
        rules_version = '2';

service cloud.firestore {

  match /databases/{database}/documents {

    match /{document=**} {

      allow read, write: if false;

    }

  }

}

        **/
    }

    private fun setup() {
        title = "Autenticación"
        buttonSign.setOnClickListener(){
            Log.d("TAG", "button pressed")
            if (emailLog.text.isNotEmpty() && loginPass.text.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailLog.text.toString(),loginPass.text.toString())
                    .addOnCompleteListener{
                        if(it.isSuccessful){

                            Log.d("TAG", "everything working as normal")
                            showHome(it.result?.user?.email?:"",ProviderType.BASIC);
                        }
                        else{

                            Log.d("TAG", "error of system")
                            showAlert()

                        }
                    }
            }
        }
        buttonRes.setOnClickListener(){
            showHome("",ProviderType.BASIC);
            if(emailLog.text.isNotEmpty() && loginPass.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailLog.text.toString(),loginPass.text.toString()).addOnCompleteListener{
                        if(it.isSuccessful){

                            Log.d("TAG", "everything working as normal")
                            showHome(it.result?.user?.email?:"",ProviderType.BASIC);
                        }
                        else{

                            Log.d("TAG", "error of system")
                            showAlert()

                        }
                    }
            }
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Debe introducir un email y contraseña válidos")
        builder.setPositiveButton("Aceptar", null)
        val dialog = builder.create()
        dialog.show()
    }
    private fun showHome(email: String, provider: ProviderType){

        val homInt = Intent(this,HomeScreen::class.java).apply{
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homInt)
    }
}