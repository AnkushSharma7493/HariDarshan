package com.example.haridarshan.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;

public class AuthService {
    private static final String TAG = "AuthService";
    private static final int RC_SIGN_IN = 100;

    private static final String WEB_CLIENT_ID = "838164622165-1ubitjh7u92tks9t92vdbstet17vtkua.apps.googleusercontent.com";
    private final FirebaseAuth firebaseAuth;
    private final GoogleSignInClient googleSignInClient;
    private final Activity activity;

    private SharedPreferences sharedPreferences;

    public AuthService(Activity activity) {
        this.activity = activity;
        firebaseAuth = FirebaseAuth.getInstance();
        sharedPreferences = activity.getApplicationContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        // Google Sign-In Configuration
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(WEB_CLIENT_ID)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(activity, gso);
    }

    // Google Sign-In
    public void signInWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void signOut() {
        firebaseAuth.signOut();
        sharedPreferences.edit().clear().apply();
    }

    // Handle Google Sign-In Result
    public void handleGoogleSignInResult(Intent data, OnAuthCompleteListener listener) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            firebaseAuthWithGoogle(account.getIdToken(), listener);
        } catch (ApiException e) {
            Log.e(TAG, "Google Sign-In Failed: " + e.getMessage());
            listener.onFailure(e.getMessage());
        }
    }

    private void firebaseAuthWithGoogle(String idToken, OnAuthCompleteListener listener) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.onSuccess(firebaseAuth.getCurrentUser());
                    } else {
                        listener.onFailure(task.getException().getMessage());
                    }
                });
    }

    // Phone Authentication
    public void sendOtp(String phoneNumber, OnVerificationStateChangedListener listener) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber("+91"+phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                        listener.onVerificationCompleted(credential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        listener.onVerificationFailed(e);
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                        listener.onCodeSent(verificationId, token);
                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public void verifyOtp(String verificationId, String otp, OnAuthCompleteListener listener) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.onSuccess(firebaseAuth.getCurrentUser());
                    } else {
                        listener.onFailure(task.getException().getMessage());
                    }
                });
    }

    public interface OnAuthCompleteListener {
        void onSuccess(Object user);
        void onFailure(String errorMessage);
    }

    public interface OnVerificationStateChangedListener {
        void onVerificationCompleted(PhoneAuthCredential credential);
        void onVerificationFailed(FirebaseException e);
        void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token);
    }
}
