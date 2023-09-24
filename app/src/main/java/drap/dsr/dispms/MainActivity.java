package drap.dsr.dispms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dispms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button loginbutton;
    EditText loginemail,loginpasword;
    FirebaseAuth mAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginbutton=(Button) findViewById(R.id.loginbutton);
        loginemail=(EditText) findViewById(R.id.loginemail);
        loginpasword=(EditText) findViewById(R.id.loginpassword);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        mAuth=FirebaseAuth.getInstance();


        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password;
                email=loginemail.getText().toString().trim();
                password=loginpasword.getText().toString().trim();
                if(TextUtils.isEmpty(email))
                {
                    loginemail.setError("Please Enter Valid Email Address");
                    loginemail.requestFocus();


                }
                else if (TextUtils.isEmpty(password))
                {
                    loginpasword.setError("Invalid Password");
                    loginemail.requestFocus();


                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    loginbutton.setVisibility(View.INVISIBLE);

                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressBar.setVisibility(View.INVISIBLE);
                                loginbutton.setVisibility(View.VISIBLE);

                                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this,Dashboard.class));
                                finish();

                            }
                            else{
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(MainActivity.this, "Error:"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                loginbutton.setVisibility(View.VISIBLE);
                            }

                        }
                    });
                }


            }
        });
    }
}