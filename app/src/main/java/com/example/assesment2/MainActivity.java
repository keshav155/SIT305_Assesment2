package com.example.assesment2;


import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText emailIDEditText, passwordEditText;
    Button signUpButton;
    TextView signInMessageTextView;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailIDEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        signUpButton = findViewById(R.id.signUpButton);
        signInMessageTextView = findViewById(R.id.signInMessage);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredEmail = emailIDEditText.getText().toString();
                String enteredPassword = passwordEditText.getText().toString();
                if(enteredEmail.isEmpty()){
                    emailIDEditText.setError("Please enter email id");
                    emailIDEditText.requestFocus();
                }
                else  if(enteredPassword.isEmpty()){
                    passwordEditText.setError("Please enter your password");
                    passwordEditText.requestFocus();
                }
                else  if(enteredEmail.isEmpty() && enteredPassword.isEmpty()){
                    Toast.makeText(MainActivity.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
                }
                else  if(!(enteredEmail.isEmpty() && enteredPassword.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(enteredEmail, enteredPassword).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this,"SignUp Unsuccessful, Please Try Again",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(MainActivity.this,"Error Occurred!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        signInMessageTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
