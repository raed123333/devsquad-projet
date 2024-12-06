package isetb.tp5.checkinproapp.model;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import isetb.tp5.checkinproapp.Admin;
import isetb.tp5.checkinproapp.ProfileActivity;
import isetb.tp5.checkinproapp.R;
import isetb.tp5.checkinproapp.SignUpActivity;
import isetb.tp5.checkinproapp.utils.ApiClient;
import isetb.tp5.checkinproapp.utils.ApiService;
import isetb.tp5.checkinproapp.utils.DashboardActivity;
import isetb.tp5.checkinproapp.utils.ProfileFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.widget.CheckBox;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameField, passwordField;
    private CheckBox rememberMeCheckbox;
    private SharedPreferenceManager sharedPreferenceManager;
    private TextView registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        usernameField = findViewById(R.id.usernameField);
        passwordField = findViewById(R.id.passwordField);
        rememberMeCheckbox = findViewById(R.id.rememberMeCheckbox);
        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the RegisterActivity (you need to create this activity)
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }});

        sharedPreferenceManager = new SharedPreferenceManager(this);

        // Auto-login if "Remember Me" is enabled
        if (sharedPreferenceManager.isRememberMeEnabled()) {
            String token = sharedPreferenceManager.getToken();
            String username = sharedPreferenceManager.getUsername();
            String role = sharedPreferenceManager.getRole(); // Assuming 'role' is part of the response
            if ("ADMIN".equals(role)|| "RH".equals(role)) {
                Intent intent = new Intent(LoginActivity.this, Admin.class);
                startActivity(intent);
            } else if ("EMPLOYEE".equals(role) ) {
                Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
            Toast.makeText(this, "Welcome back, " + username, Toast.LENGTH_SHORT).show();
            // Proceed to next activity
        }

        findViewById(R.id.loginButton).setOnClickListener(view -> login());
    }

    private void login() {
        String username = usernameField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService authService = ApiClient.getRetrofitInstance().create(ApiService.class);
        LoginRequest request = new LoginRequest(username, password);

        authService.login(request).enqueue(new Callback<AuthenticationResponse>() {
            @Override
            public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
                if (response.isSuccessful()) {
                    AuthenticationResponse authResponse = response.body();
                    if (authResponse != null) {
                        boolean rememberMe = rememberMeCheckbox.isChecked();

                        // Save login details if "Remember Me" is checked
                        sharedPreferenceManager.saveLoginDetails(authResponse.getToken(), authResponse.getUsername(),authResponse.getRole(), rememberMe);
                        String role = sharedPreferenceManager.getRole(); // Assuming 'role' is part of the response
                        if ("ADMIN".equals(role)|| "RH".equals(role)) {
                            Intent intent = new Intent(LoginActivity.this, Admin.class);
                            startActivity(intent);
                        } else if ("EMPLOYEE".equals(role) ) {
                            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                            startActivity(intent);
                        }

                        finish();
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();


                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

