package drap.dsr.dispms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dispms.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

    Button submit;
    EditText uname,uphone,ucnic,udesignation;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser;
    String currentid,NAME,PHONENUMBER,CNIC,PROVINCE,DISTRICT,CITY,DESIGNATION;
    Spinner provincespinner,districtspinner,cityspinner;
    ArrayAdapter<CharSequence> provinceadapter;
    ArrayAdapter <CharSequence> districtadapter;
    ArrayAdapter <CharSequence> cityadapter;
    TextView textView10;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        provincespinner=(Spinner) findViewById(R.id.spinner) ;

        cityspinner=(Spinner) findViewById(R.id.spinner3) ;
        textView10=(TextView) findViewById(R.id.textView10) ;
        submit=(Button) findViewById(R.id.button2);
        uname=(EditText) findViewById(R.id.editTextTextPersonName8);
        uphone=(EditText) findViewById(R.id.editTextPhone);
        ucnic=(EditText) findViewById(R.id.editTextPhone2);
        udesignation=(EditText) findViewById(R.id.editTextTextPersonName10) ;
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        //  spinerrsssssssssssssssssss


        provinceadapter=ArrayAdapter.createFromResource(this,R.array.statespakistan,R.layout.spinner_layout);
        provinceadapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        provincespinner.setAdapter(provinceadapter);
        provincespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                districtspinner=(Spinner) findViewById(R.id.spinner2) ;

               PROVINCE=provincespinner.getSelectedItem().toString();

                int parentID=parent.getId();
                if(parentID==R.id.spinner)
                {
                    switch (PROVINCE){
                        case "Select your province" :
                            districtadapter=ArrayAdapter.createFromResource(parent.getContext(),R.array.default_districts,R.layout.spinner_layout);
                            cityadapter=ArrayAdapter.createFromResource(parent.getContext(),R.array.City,R.layout.spinner_layout);
                            break;
                        case "Punjab" :
                            districtadapter=ArrayAdapter.createFromResource(parent.getContext(),R.array.Punjab,R.layout.spinner_layout);
                            cityadapter=ArrayAdapter.createFromResource(parent.getContext(),R.array.PunjabCities,R.layout.spinner_layout);
                            break;
                        case "Balochistan":
                            districtadapter=ArrayAdapter.createFromResource(parent.getContext(),R.array.Balochistan,R.layout.spinner_layout);
                            cityadapter=ArrayAdapter.createFromResource(parent.getContext(),R.array.BalochistanCities,R.layout.spinner_layout);
                            break;
                        case "Sindh" :
                            districtadapter=ArrayAdapter.createFromResource(parent.getContext(),R.array.Sindh,R.layout.spinner_layout);
                            cityadapter=ArrayAdapter.createFromResource(parent.getContext(),R.array.SindhCities,R.layout.spinner_layout);
                            break;
                        case "Khyber Pakhtunkhwa" :
                            districtadapter=ArrayAdapter.createFromResource(parent.getContext(),R.array.Khyber_Pakhtunkhwa,R.layout.spinner_layout);
                            cityadapter=ArrayAdapter.createFromResource(parent.getContext(),R.array.kpkCities,R.layout.spinner_layout);
                            break;
                        case "Gilgit Baltistan" :
                            districtadapter=ArrayAdapter.createFromResource(parent.getContext(),R.array.Gilgit_Baltistan,R.layout.spinner_layout);
                            cityadapter=ArrayAdapter.createFromResource(parent.getContext(),R.array.GilgitCities,R.layout.spinner_layout);
                            break;
                        case "Azad Jammu and Kashmir" :
                            districtadapter=ArrayAdapter.createFromResource(parent.getContext(),R.array.Azad_Jammu_and_Kashmir,R.layout.spinner_layout);
                            cityadapter=ArrayAdapter.createFromResource(parent.getContext(),R.array.KashmirCities,R.layout.spinner_layout);
                            break;
                        case "Islamabad Capital Territory":
                            districtadapter=ArrayAdapter.createFromResource(parent.getContext(),R.array.ISLAMABAD_Capital_Territory,R.layout.spinner_layout);
                            cityadapter=ArrayAdapter.createFromResource(parent.getContext(),R.array.Islamabad,R.layout.spinner_layout);
                            break;

                        default: break;
                    }
                    districtadapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                    districtspinner.setAdapter(districtadapter);
                    districtspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            DISTRICT=districtspinner.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    cityadapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                    cityspinner.setAdapter(cityadapter);
                    cityspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            CITY=cityspinner.getSelectedItem().toString();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });



                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                     NAME=uname.getText().toString().trim();
                     PHONENUMBER=uphone.getText().toString().trim();
                     CNIC=ucnic.getText().toString().trim();
                     DESIGNATION=udesignation.getText().toString().trim();
                   currentid=firebaseUser.getUid();



                if (TextUtils.isEmpty(NAME) || TextUtils.isEmpty(PHONENUMBER) || TextUtils.isEmpty(CNIC) || TextUtils.isEmpty(DESIGNATION)  ){
                    Toast.makeText(Profile.this, "Fill All The Credentials", Toast.LENGTH_SHORT).show();
                }

                else if(CNIC.length()<13){
                    ucnic.setError("Enter correct details");

                }

                else if (PHONENUMBER.length()<11){
                    uphone.setError("Enter correct phone number");
                    ucnic.setError(null);
                }
                else if (PROVINCE.equals("Select your province"))
                {
                    textView10.setError("Select you Province");
                    uphone.setError(null);

                }
                else {
                    Completeprofile();
                }




            }
        });



    }
    private void Completeprofile() {


        Map<String,String> profile= new HashMap<>();
        profile.put("NAME",NAME);
        profile.put("PHONENUMBER",PHONENUMBER);
        profile.put("CNIC",CNIC);
        profile.put("DESIGNATION",DESIGNATION);
        profile.put("PROVINCE",PROVINCE);
        profile.put("DISTRICT",DISTRICT);
        profile.put("CITY",CITY);


        db.collection("DRUG_INSPECTOR").document(currentid).set(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Profile.this, " Profile Completed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);


                startActivity(intent);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Profile.this, ""+e, Toast.LENGTH_SHORT).show();


            }
        });

    }


}