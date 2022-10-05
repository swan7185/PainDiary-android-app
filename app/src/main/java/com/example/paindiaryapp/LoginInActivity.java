package com.example.paindiaryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.paindiaryapp.databinding.LoginInLayoutBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginInActivity extends AppCompatActivity {
    private LoginInLayoutBinding binding;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    private void userLogin(boolean admin){
        if(admin)
        {
            Toast.makeText(LoginInActivity.this, "Login in success",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.putExtra("email", "superman@test.com");
            finish();
            startActivity(intent);
        }
        String email = binding.accountEdit.getText().toString();
        String password = binding.passwordEdit.getText().toString();
        if(!Util.isValidEmail(email))
        {
            binding.accountLayout.setError("not valid email address");
            return;
        } else
        {
            binding.accountLayout.setError(null);
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Logging In...");
        progressDialog.show();

        //logging in the user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if(task.isSuccessful()){
                            //start the profile activity
                            Toast.makeText(LoginInActivity.this, "Login in success",
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.putExtra("email", email);
                            finish();
                            startActivity(intent);
                        } else
                        {
                            Log.w("Login activity", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginInActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginInLayoutBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        binding.registerButton.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
        binding.signInButton.setOnClickListener((View v) -> {
            userLogin(false);
        });

    }
}