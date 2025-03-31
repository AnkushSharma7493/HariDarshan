package com.example.haridarshan.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.haridarshan.R;
import com.example.haridarshan.service.AuthService;

public class VerifyOtpActivity extends AppCompatActivity {

    private EditText otpEditText;
    private Button verifyOtpButton;
    private AuthService authService;
    private ProgressDialog progressDialog;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.haridarshan.R.layout.activity_verify_otp);

        otpEditText = findViewById(R.id.otp_edit_text);
        verifyOtpButton = findViewById(R.id.verify_otp_button);
        authService = new AuthService(this);

        verificationId = getIntent().getStringExtra("verificationId");

        verifyOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = otpEditText.getText().toString();
                if (TextUtils.isEmpty(otp) || otp.length() < 6) {
                    Toast.makeText(VerifyOtpActivity.this, "Enter a valid OTP", Toast.LENGTH_SHORT).show();
                } else {
                    verifyOtp(verificationId,otp);
                }
            }
        });
    }

    private void verifyOtp(String verificationId, String otp) {
        progressDialog = ProgressDialog.show(this, "Verifying OTP", "Please wait...");
        authService.verifyOtp(verificationId,otp, new AuthService.OnAuthCompleteListener() {
            @Override
            public void onSuccess(Object user) {
                progressDialog.dismiss();
                Toast.makeText(VerifyOtpActivity.this, "OTP Verified", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(VerifyOtpActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onFailure(String errorMessage) {
                progressDialog.dismiss();
                Toast.makeText(VerifyOtpActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
