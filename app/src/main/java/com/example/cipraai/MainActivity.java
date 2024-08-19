package com.example.cipraai;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cipraai.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ApiService apiService;
    private ActivityMainBinding binding;
    private boolean isPasswordVisible = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Initialize ApiService
        apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        // Set up the click listener for the sign-in button
        binding.btnSignIn.setOnClickListener(v -> {
            // Fetch email and password from the input fields
            String email = binding.etEmailInput.getText().toString().trim();
            String password = binding.etPasswordInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            // Make the API call
            makeApiCall(email, password);
        });

        // Set up text watchers for email and password
        binding.etEmailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Change email icon based on text input
                binding.imgEmail.setImageResource(s.length() > 0 ? R.drawable.person_filled : R.drawable.person);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        binding.etPasswordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Change password icon based on text input
                binding.imgPassword.setImageResource(s.length() > 0 ? R.drawable.key_fill : R.drawable.key);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Set up the end icon click listener for password visibility toggle
        binding.etPassword.setEndIconOnClickListener(v -> {
            if (isPasswordVisible) {
                // Switch to password hidden
                binding.etPasswordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                binding.etPassword.setEndIconDrawable(R.drawable.eye);
            } else {
                // Switch to password visible
                binding.etPasswordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                binding.etPassword.setEndIconDrawable(R.drawable.visible);
            }
            isPasswordVisible = !isPasswordVisible;
            // Move cursor to the end of the text
            binding.etPasswordInput.setSelection(binding.etPasswordInput.getText().length());
        });
    }

    private void makeApiCall(String email, String password) {
        Call<String> call = apiService.signIn(email, password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                // Log detailed response information
                Log.d("ApiResponse", "Response Code: " + response.code());
                Log.d("ApiResponse", "Response Body: " + response.body());
                Log.d("ApiResponse", "Response Headers: " + response.headers());

                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body();
                    Toast.makeText(MainActivity.this, "Success: " + responseBody, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("ApiResponse", "Error: " + response.message());
                    Toast.makeText(MainActivity.this, "Error: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("ApiResponse On Failure", "Failure: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Failure: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
