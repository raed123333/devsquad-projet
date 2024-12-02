package isetb.tp5.checkinproapp;

import static isetb.tp5.checkinproapp.R.*;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import isetb.tp5.checkinproapp.model.Employee;
import isetb.tp5.checkinproapp.utils.ApiClient;
import isetb.tp5.checkinproapp.utils.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private EditText nameField, prenomField, emailField, cinField, ageField, genreField, phoneField, posteField, passwordField, iconField;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialisation des champs du formulaire
        nameField = findViewById(R.id.nameField);
        prenomField = findViewById(R.id.prenomField);
        emailField = findViewById(R.id.emailField);
        cinField = findViewById(R.id.cinField);
        ageField = findViewById(R.id.ageField);
        genreField = findViewById(R.id.genreField);
        phoneField = findViewById(R.id.phoneField);
        posteField = findViewById(R.id.posteField);
        passwordField = findViewById(R.id.passwordField);
        iconField = findViewById(R.id.iconField);

        signUpButton = findViewById(R.id.signUpButton);

        // Action lors de l'appui sur le bouton "S'inscrire"
        signUpButton.setOnClickListener(v -> signUp());
    }

    private void signUp() {
        // Récupérer les données saisies dans les champs
        String nom = nameField.getText().toString();
        String prenom = prenomField.getText().toString();
        String email = emailField.getText().toString();
        String cin = cinField.getText().toString();
        String ageStr = ageField.getText().toString();
        String genre = genreField.getText().toString();
        String numPhone = phoneField.getText().toString();
        String poste = posteField.getText().toString();
        String password = passwordField.getText().toString();
        String icon = iconField.getText().toString(); // Utilisé pour un avatar ou une image d'icône

        // Vérification des champs vides
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || cin.isEmpty() || ageStr.isEmpty() || genre.isEmpty() ||
                numPhone.isEmpty() || poste.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageStr); // Convertir l'âge en entier

        // Créer un objet Employee avec les informations saisies
        Employee employee = new Employee(null, nom, prenom, email, cin, age, genre, numPhone, poste, true, password, icon);

        // Créer une instance de l'API
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        // Envoi de la requête pour enregistrer l'employé
        apiService.registerEmployee(employee).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Inscription réussie", Toast.LENGTH_SHORT).show();
                    finish(); // Fermer cette activité et retourner à l'écran de connexion
                } else {
                    Toast.makeText(SignUpActivity.this, "Erreur lors de l'inscription", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
