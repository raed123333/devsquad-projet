package isetb.tp5.checkinproapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import isetb.tp5.checkinproapp.model.Employee;
import isetb.tp5.checkinproapp.model.LoginActivity;
import isetb.tp5.checkinproapp.model.SharedPreferenceManager;
import isetb.tp5.checkinproapp.utils.ApiClient;
import isetb.tp5.checkinproapp.utils.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EmployeeAdapter employeeAdapter;
    private EditText employeeIdField;
    private Button fetchButton, deleteButton;
    private Button btnLogout;
    private SharedPreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        employeeIdField = findViewById(R.id.employeeIdField);
        fetchButton = findViewById(R.id.fetchButton);
        deleteButton = findViewById(R.id.deleteButton);

        fetchButton.setOnClickListener(v -> fetchEmployeeById());
        deleteButton.setOnClickListener(v -> deleteEmployee());
        fetchAllEmployees();
        btnLogout = findViewById(R.id.btn_logout);

        btnLogout.setOnClickListener(v -> handleLogout());


    }
    private void handleLogout() {
        SharedPreferenceManager preferenceManager = new SharedPreferenceManager(this);
        preferenceManager.clearLoginDetails();
        preferenceManager.clearLoginDetails();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear back stack
        startActivity(intent);
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
    }



    private void fetchAllEmployees() {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<List<Employee>> call = apiService.getAllEMPLOYEE();
        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (response.isSuccessful()) {
                    List<Employee> employeeList = response.body();
                    employeeAdapter = new EmployeeAdapter(employeeList);
                    recyclerView.setAdapter(employeeAdapter);
                } else {
                    Toast.makeText(Admin.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                Toast.makeText(Admin.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchEmployeeById() {
        Long id = Long.parseLong(employeeIdField.getText().toString());
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<Employee> call = apiService.getEmployeeById(id);
        call.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (response.isSuccessful()) {
                    List<Employee> employeeList = List.of(response.body());
                    employeeAdapter = new EmployeeAdapter(employeeList);
                    recyclerView.setAdapter(employeeAdapter);
                } else {
                    Toast.makeText(Admin.this, "Employee not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                Toast.makeText(Admin.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteEmployee() {
        Long id = Long.parseLong(employeeIdField.getText().toString());
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<Void> call = apiService.deleteEmployee(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Admin.this, "Employee deleted successfully", Toast.LENGTH_SHORT).show();
                    fetchAllEmployees(); // Refresh the list
                } else {
                    Toast.makeText(Admin.this, "Failed to delete employee", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Admin.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
