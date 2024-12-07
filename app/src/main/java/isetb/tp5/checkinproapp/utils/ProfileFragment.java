package isetb.tp5.checkinproapp.utils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import isetb.tp5.checkinproapp.R;
import isetb.tp5.checkinproapp.model.Employee;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private ApiService apiService;
    private String email = "user@example.com"; // Replace with the actual email
    private EditText editNom, editPrenom, editEmail, editPhone, editAge, editPoste, editPassword;
    private ImageView genderIcon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_profile, container, false);

        // Initialize UI components
        editNom = view.findViewById(R.id.edit_nom);
        editPrenom = view.findViewById(R.id.edit_prenom);
        editEmail = view.findViewById(R.id.edit_email);
        editPhone = view.findViewById(R.id.edit_phone);
        editAge = view.findViewById(R.id.edit_age);
        editPoste = view.findViewById(R.id.edit_poste);
        editPassword = view.findViewById(R.id.edit_password);
        genderIcon = view.findViewById(R.id.ivGenderIcon);

        // Initialize API service
        apiService = ApiClient.getRetrofitInstance().create(ApiService.class);


        // Fetch employee data
        fetchEmployeeData();

        return view;
    }

    private void fetchEmployeeData() {
        apiService.getEmployeeByEmail(email).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Employee employee = response.body();

                    // Populate fields
                    editNom.setText(employee.getNom());
                    editPrenom.setText(employee.getPrenom());
                    editEmail.setText(employee.getEmail());
                    editPhone.setText(employee.getNumPhone());
                    editAge.setText(String.valueOf(employee.getAge()));
                    editPoste.setText(employee.getPoste());
                    editPassword.setText(employee.getPassword());

                    // Set icon
                    if ("Male".equalsIgnoreCase(employee.getGenre())) {
                        genderIcon.setImageResource(R.drawable.ic_male);
                    } else if ("Female".equalsIgnoreCase(employee.getGenre())) {
                        genderIcon.setImageResource(R.drawable.ic_female);
                    }
                } else {
                    Toast.makeText(getContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                Toast.makeText(getContext(), "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
