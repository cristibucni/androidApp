package com.exam.AndroidApp.main;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

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
    private SignInButton signInButton;
    private int RC_SIGN_IN = 1;

    private GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        LogInButton = (Button)findViewById(R.id.buttonLogin);
        RegisterButton = (Button)findViewById(R.id.buttonRegister);
        SignInButton signInButton =  findViewById(R.id.buttonGoogle);

        Email = (EditText)findViewById(R.id.editEmail);
        Password = (EditText)findViewById(R.id.editPassword);

        //Get firebaste instance
        fAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



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


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleLogin();
            }
        });

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


    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Intent data = result.getData();
//                        doSomeOperations();
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    handleSignInResult(task);
                }else{
                    Toast.makeText(MainActivity.this,"NOPE",Toast.LENGTH_SHORT).show();
                }
            });

    public void GoogleLogin() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        someActivityResultLauncher.launch(signInIntent);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try{

            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(MainActivity.this,"Signed In Successfully",Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);
        }
        catch (ApiException e){
            Toast.makeText(MainActivity.this,"Sign In Failed",Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
        }
    }


    private void FirebaseGoogleAuth(GoogleSignInAccount acc) {
        //check if the account is null
        if (acc != null) {
            AuthCredential authCredential = GoogleAuthProvider.getCredential(acc.getIdToken(), null);
            fAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = fAuth.getCurrentUser();
                        updateUI(user);
                        Toast.makeText(MainActivity.this,"Login Successfully",Toast.LENGTH_LONG).show();
                        // Going to Dashboard activity after login success message.
                        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                        // Sending Email to Dashboard Activity using intent.
                        intent.putExtra(userName,user.getEmail() );
                        startActivity(intent);
                        Log.i("","on complete");
                    } else {
                        Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                        Log.i("","on failed");
                        updateUI(null);
                    }
                }
            });
        }
        else{
            Toast.makeText(MainActivity.this, "acc failed", Toast.LENGTH_SHORT).show();
            Log.i("","on failed 2");
        }
    }
    private void updateUI(FirebaseUser fUser){
//        btnSignOut.setVisibility(View.VISIBLE);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(account !=  null){
            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();
            Toast.makeText(MainActivity.this,"Login Successfully",Toast.LENGTH_LONG).show();
            // Going to Dashboard activity after login success message.
            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
            // Sending Email to Dashboard Activity using intent.
            intent.putExtra(userName, personName );
            startActivity(intent);
            Toast.makeText(MainActivity.this,personName + personEmail ,Toast.LENGTH_SHORT).show();
        }

    }
}