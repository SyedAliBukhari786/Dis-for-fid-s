package drap.dsr.dispms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dispms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Dashboard extends AppCompatActivity {

    public String province, city;
    Button logout, createprofile, editprof;
    TextView username, userphone, userdesignation, textView47;
    ImageView notification;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String userid;


    CardView reporting, complainsactivity, cardview5, cardView6, cardvieww;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        ///////
        cardvieww = (CardView) findViewById(R.id.cardvieww);
        textView47 = (TextView) findViewById(R.id.textView47);
        username = (TextView) findViewById(R.id.textView5);
        notification = (ImageView) findViewById(R.id.notification);

        userphone = (TextView) findViewById(R.id.textView6);
        logout = (Button) findViewById(R.id.logout);
        userdesignation = (TextView) findViewById(R.id.textView8);
        reporting = (CardView) findViewById(R.id.cardView4);
        complainsactivity = (CardView) findViewById(R.id.complainactivity);
        cardview5 = (CardView) findViewById(R.id.cardView5);
        cardView6 = (CardView) findViewById(R.id.cardView6);
        createprofile = (Button) findViewById(R.id.createprofile);
        editprof = (Button) findViewById(R.id.EditProfile);


        complainsactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check;
                check = userphone.getText().toString();
                if (check.equals("")) {
                    Toast.makeText(Dashboard.this, "Complete Your Profile", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), generalpubliccomplainsss.class);
                    intent.putExtra("cityy", city);
                    startActivity(intent);
                }


            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, Notifications.class));

            }
        });
        cardview5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check;
                check = userphone.getText().toString();
                if (check.equals("")) {
                    Toast.makeText(Dashboard.this, "Complete Your Profile", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), ComplainManagment.class);
                    intent.putExtra("cityy", city);
                    intent.putExtra("uid", userid);
                    intent.putExtra("phonenumer", userphone.getText().toString().trim());
                    intent.putExtra("designation", userdesignation.getText().toString().trim());
                    intent.putExtra("username", username.getText().toString().trim());
                    startActivity(intent);
                }


            }
        });
        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check;
                check = userphone.getText().toString();
                if (check.equals("")) {
                    Toast.makeText(Dashboard.this, "Complete Your Profile", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), graphs.class);
                    intent.putExtra("cityy", city);

                    startActivity(intent);
                }

            }
        });


        reporting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check;
                check = userphone.getText().toString();
                if (check.equals("")) {
                    Toast.makeText(Dashboard.this, "Complete Your Profile", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), Proforma.class);
                    intent.putExtra("cityy", city);
                    intent.putExtra("proo", province);
                    startActivity(intent);
                }

            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressBar progressBar2 = findViewById(R.id.progressBar2);
                progressBar2.setVisibility(View.VISIBLE); // Show the progress bar

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Perform logout after 2 seconds
                        progressBar2.setVisibility(View.GONE);
                        mAuth.signOut();
                        startActivity(new Intent(Dashboard.this, MainActivity.class));
                        finish();
                    }
                }, 2000); // Delay of 2 seconds (2000 milliseconds)
            }

        });

        createprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, Profile.class));
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();


        if (user == null) {

            startActivity(new Intent(Dashboard.this, MainActivity.class));
            finish();
        } else {

            userid = user.getUid();
            DocumentReference docRef = db.collection("DRUG_INSPECTOR").document(userid);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {

                        username.setText(documentSnapshot.getString("NAME"));
                        userphone.setText(documentSnapshot.getString("PHONENUMBER"));
                        userdesignation.setText(documentSnapshot.getString("DESIGNATION"));
                        city = (documentSnapshot.getString("CITY"));
                        province = (documentSnapshot.getString("PROVINCE"));

                        Query query2 = db.collection("Complain").whereEqualTo("City",city).whereEqualTo("STATUS","UNCHECKED");
                        AggregateQuery countQuery2 = query2.count();
                        countQuery2.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    // Count fetched successfully
                                    AggregateQuerySnapshot snapshot = task.getResult();
                                    long count = snapshot.getCount();
                                    if (count>0){
                                        //  imageView54.setVisibility(View.VISIBLE);
                                       textView47.setText(Long.toString(count));
                                       cardvieww.setVisibility(View.VISIBLE);
                                    }else {

                                         cardvieww.setVisibility(View.GONE);
                                    }

                                } else {

                                }
                            }
                        });































                    }
                        else {

                                    editprof.setVisibility(View.GONE);
                                    createprofile.setVisibility(View.VISIBLE);

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Dashboard.this, "" + e, Toast.LENGTH_SHORT).show();
                            }
                        });

                    }


                }

            }





