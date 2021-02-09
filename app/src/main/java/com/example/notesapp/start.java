package com.example.notesapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class start extends AppCompatActivity {
    private static final String TAG = "start";

    private static int AUTH_UI_REQUEST_CODE = 2020;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
            this.finish();
        }


    }

    public void LoginRegister(View view) {

        List<AuthUI.IdpConfig> provider = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build());

        Intent intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(provider)
                .setTosAndPrivacyPolicyUrls("http://example.com","http://example.com")
                .setAlwaysShowSignInMethodScreen(true)
                .setLogo(R.drawable.notes_icon)
                .build();

        startActivityForResult(intent, AUTH_UI_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AUTH_UI_REQUEST_CODE){
            //new user or old user signed in
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Log.d(TAG, "onActivityResult: " + user.getEmail());
            if(user.getMetadata().getCreationTimestamp() == user.getMetadata().getLastSignInTimestamp()){
                Toast.makeText(this,"Welcome",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"Welcome Back",Toast.LENGTH_LONG).show();
            }

            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            this.finish();
        }else{
            // signing-in failed
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if(response == null){
                Log.d(TAG, "onActivityResult: user calncelled signin request");
            }
            else{
                Log.e(TAG, "onActivityResult: ", response.getError());
            }
        }
    }
}

