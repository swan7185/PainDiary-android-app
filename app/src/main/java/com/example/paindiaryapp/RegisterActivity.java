package com.example.paindiaryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.paindiaryapp.databinding.RegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private RegisterBinding binding;
    private FirebaseAuth mAuth;
    private String TAG = "register activity";
    private ProgressDialog progressDialog;
    private void createAccount(String email, String password) {
        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(RegisterActivity.this, "Sign up success.",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent(RegisterActivity.this, LoginInActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        //binding.accountLayout.setError();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        binding.signUpButton.setOnClickListener((View v) -> {
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
            if(!Util.isValidPassword(password))
            {
                binding.passwordLayout.setError("password must contains at lease a lowercase character, a uppercase letter and a digit.");
                return;
            } else
            {
                binding.accountLayout.setError(null);
            }
            this.createAccount(email, password);

        });
        setContentView(view);
    }
}