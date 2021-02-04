package com.purge.nueral;

import android.content.Intent;
import android.os.Bundle;
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

public class RegisterActivity extends AppCompatActivity {
    EditText edtEmail, edtPassword;
    String registerEmail, registerPassword;
    FirebaseAuth firebaseAuth ;
    Button btnRegister;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        firebaseAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerEmail = edtEmail.getText().toString();
                registerPassword = edtPassword.getText().toString();
                firebaseAuth.createUserWithEmailAndPassword(registerEmail,registerPassword).
                        addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                // Checking if user is registered successfully.
                                if(task.isSuccessful()){

                                    // If user registered successfully then show this toast message.
                                    Toast.makeText(RegisterActivity.this,"User Registration Successfully",Toast.LENGTH_LONG).show();

                                    firebaseAuth.signOut();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);

                                }else{

                                    // If something goes wrong.
                                    Toast.makeText(RegisterActivity.this,"Something Went Wrong.",Toast.LENGTH_LONG).show();
                                }


                            }
                        });
            }
        });

    }

}
