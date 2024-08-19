package com.example.cipraai;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.cipraai.databinding.ActivityHomeBinding;
import com.example.cipraai.databinding.ActivityMainBinding;

public class HomeActivity extends AppCompatActivity {


    private ActivityHomeBinding binding;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Set the Toolbar title
        // Check if ActionBar is available
        if (getSupportActionBar() != null) {



            getSupportActionBar().setTitle("Personalised Impact of Lifestyle Factor");
//            // Create a new TextView
//            TextView textView = new TextView(this);
//            textView.setText("");
//            textView.setTextSize(20); // Set text size
//            // Get colors from resources
//            int backgroundColor = ContextCompat.getColor(this, android.R.color.white);
//            int textColor = ContextCompat.getColor(this, android.R.color.black);
//
//            // Set background color and text color
//            textView.setBackgroundColor(backgroundColor);
//            textView.setTextColor(textColor);
//
//            // Set the custom view to the ActionBar
//            getSupportActionBar().setCustomView(textView);
//            getSupportActionBar().setCol

            // Enable custom view
//            getSupportActionBar().setDisplayShowCustomEnabled(true);
        }

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, android.R.color.white));
    }
}

