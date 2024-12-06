package isetb.tp5.checkinproapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import isetb.tp5.checkinproapp.model.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Vérifier si l'utilisateur est connecté
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (!isLoggedIn) {
            // Si l'utilisateur n'est pas connecté, rediriger vers LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Fermer l'activité actuelle pour empêcher le retour
        } else {
            // Sinon, charger la page principale
            setContentView(R.layout.activity_main);
        }
    }
}
