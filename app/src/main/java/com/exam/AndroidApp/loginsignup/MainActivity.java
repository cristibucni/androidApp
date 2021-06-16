package com.exam.AndroidApp.loginsignup;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button LogInButton, RegisterButton, GoogleButton ;
    EditText Email, Password ;
    String EmailHolder, PasswordHolder;
    String UserName;
    FirebaseAuth fAuth;

    URL url;
    public static final String UserEmail = "";
    public static final String userName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        LogInButton = (Button)findViewById(R.id.buttonLogin);
        RegisterButton = (Button)findViewById(R.id.buttonRegister);
        GoogleButton = (Button)findViewById(R.id.buttonGoogle);

        Email = (EditText)findViewById(R.id.editEmail);
        Password = (EditText)findViewById(R.id.editPassword);

        //Get firebaste instance
        fAuth = FirebaseAuth.getInstance();



        //Adding click listener to log in button.

        LogInButton.setOnClickListener(view -> LoginFunction());

        // Adding click listener to register button.
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Opening new user registration activity using intent on button click.
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });


//        GoogleButton.setOnClickListener();

    }
    public void LoginFunction(){

        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Email.setError("Email is Required.");
            return;
        }

        if(TextUtils.isEmpty(password)){
            Password.setError("Password is Required.");
            return;
        }

        if(password.length() < 6){
            Password.setError("Password Must be >= 6 Characters");
            return;
        }

        // authenticate the user

        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Login Successfully",Toast.LENGTH_LONG).show();
                    // Going to Dashboard activity after login success message.
                    Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                    // Sending Email to Dashboard Activity using intent.
                    intent.putExtra(userName, email);
                    startActivity(intent);
//                            startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                }else {
                    Toast.makeText(MainActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}