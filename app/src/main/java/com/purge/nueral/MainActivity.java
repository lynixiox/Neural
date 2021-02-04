package com.purge.nueral;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mapbox.mapboxsdk.Mapbox;

public class MainActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    String email, password;
    Button btnLogin, btnSignUp;
    Boolean edtTextEmptyCheck = false;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        //set content view to the login screen//
        setContentView(R.layout.activity_login);
        Mapbox.getInstance(getApplicationContext(), getString(R.string.mapbox_access_token));

        //Assign ID's to Var//
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        progressDialog = new ProgressDialog(MainActivity.this);
        firebaseAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);

        //Check if the user has logged out previously//
        if(firebaseAuth.getCurrentUser() != null){
            //finish the current login activity//
            finish();
            //open UserProfileActivity//
            Intent intent = new Intent(MainActivity.this,PlaceActivity.class);
            startActivity(intent);
        }
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEditTextIsEmpty();

                //if not empty//
                if(edtTextEmptyCheck){
                    Login();
                }else{
                    Toast.makeText(MainActivity.this, "Please fill in all the fields", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void Login(){
        //setup message//
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        //call firebase//
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    finish();
                    Intent intent = new Intent(MainActivity.this, PlaceActivity.class);
                    startActivity(intent);
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Email or password not found", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void CheckEditTextIsEmpty(){
        // Getting value form Email's EditText and fill into EmailHolder string variable.//
        email = edtEmail.getText().toString().trim();

        // Getting value form Password's EditText and fill into PasswordHolder string variable.//
        password= edtPassword.getText().toString().trim();

        // Checking Both EditText is empty or not.//
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
        {

            // If any of EditText is empty then set value as false.//
            edtTextEmptyCheck= false;

        }
        else {

            // If any of EditText is empty then set value as true.//
            edtTextEmptyCheck = true ;

        }
    }


}
