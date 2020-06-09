package com.example.assesment2.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.assesment2.R;

// This class is just used for deciding if users want to login/signup
// Its just used for organizing things a bit more and isnt really a main feature of the app
public class ChooseLoginOrSignUpActivity extends AppCompatActivity {

    Button goToSignUpButton, goToSignInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login_or_sign_up);

        goToSignUpButton = findViewById(R.id.goToSignUpButton);
        goToSignInButton = findViewById(R.id.goToSignInButton);

        goToSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent proceedToSignUpScreen = new Intent(ChooseLoginOrSignUpActivity.this,MainActivity.class);
                startActivity(proceedToSignUpScreen);
                finish();
                return;
            }
        });
        goToSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent proceedToSignInScreen = new Intent(ChooseLoginOrSignUpActivity.this,LoginActivity.class);
                startActivity(proceedToSignInScreen);
                finish();
                return;
            }
        });
    }
}
