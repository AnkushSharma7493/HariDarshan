package com.example.haridarshan.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.haridarshan.R;
import com.example.haridarshan.service.AuthService;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private Button btnGoogleSignIn, btnSendOtp;
    private EditText etPhoneNumber;
    private ProgressDialog progressDialog;
    private String verificationId;
    private AuthService authService;
    private static final int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authService = new AuthService(this);

        setContentView(com.example.haridarshan.R.layout.activity_login);

        btnGoogleSignIn = findViewById(R.id.btnGoogleSignIn);
        btnSendOtp = findViewById(R.id.btnSendOtp);
        etPhoneNumber = findViewById(R.id.phone_number);

        // Trigger Google sign in
        btnGoogleSignIn.setOnClickListener(v -> authService.signInWithGoogle());

        // Trigger send otp
        btnSendOtp.setOnClickListener(v -> {
            String phoneNumber = etPhoneNumber.getText().toString();
            if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() < 10) {
                Toast.makeText(LoginActivity.this, "Enter a valid phone number", Toast.LENGTH_SHORT).show();
            } else {
                hideKeyboard();
                sendOtp(phoneNumber);
            }
        });

    }

    private void sendOtp(String phoneNumber) {
        progressDialog = ProgressDialog.show(this, "Sending OTP", "Please wait...");

        authService.sendOtp(phoneNumber,new AuthService.OnVerificationStateChangedListener(){
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Verification Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                progressDialog.dismiss();
                Log.d("LoginActivity", Objects.requireNonNull(e.getMessage()));
                Toast.makeText(LoginActivity.this, "Verification Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "OTP Sent", Toast.LENGTH_SHORT).show();
                LoginActivity.this.verificationId = verificationId;
                Intent intent = new Intent(LoginActivity.this, VerifyOtpActivity.class);
                intent.putExtra("verificationId", verificationId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            authService.handleGoogleSignInResult(data, new AuthService.OnAuthCompleteListener() {
                @Override
                public void onSuccess(Object user) {
                    FirebaseUser firebaseUser = (FirebaseUser) user;
                    storeUserInMemory(firebaseUser);
                    Log.d("LoginActivity", "User Signed In: " + firebaseUser.getEmail());
                    Toast.makeText(LoginActivity.this, "User Signed In: " + firebaseUser.getEmail(), Toast.LENGTH_SHORT).show();
                    // save this signed in user in device memory for future
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void onFailure(String errorMessage) {
                    Log.e("LoginActivity", "Sign-In Failed: " + errorMessage);
                }
            });
        }
    }

    private void storeUserInMemory(FirebaseUser firebaseUser) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userId", firebaseUser.getUid());
        editor.putString("userEmail", firebaseUser.getEmail());
        editor.putString("userPhone", firebaseUser.getPhoneNumber());
        editor.apply();
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
