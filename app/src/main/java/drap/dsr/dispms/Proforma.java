package drap.dsr.dispms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dispms.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Proforma extends AppCompatActivity {
    String city, province;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    Button reportsubmit,curentlocationbutton;
    EditText nameofdrug, genericname, dosageform, strength,
            packing_pu, manufacturedby, importedby, price_pp,
            price_a, Short, notavailble, date, nameofoutlet, nameofhospital,
            reason, areaofs, remarks;
    String Name_Of_Drug, Generic_Name, Dosage_form, Strength, Packing_u_pp,
            Manufactured_By, Imported_by, Price_mentioned, PriceatA, ProductShort,
            Productnotavailble, Period, Name_of_outlet,
            Name_of_hospital, Reason, Area_of_survey, Remarks;
    String useriddd,Locationss;
    ImageView  cancelcurrentlocation;
    ProgressBar progressBar3,PPP;
    DatePickerDialog picker;


    TextView textaddress,ADDRESSC,lattitudetextview,logitudetextview;
    ////location
    FusedLocationProviderClient fusedLocationProviderClient;
    String lattitude,logitude,addressssc;
    private static final int REQUEST_CODE=100;

    ///radioooo
    RadioGroup arg,srg;
    RadioButton ayes,ano,syes,sno;
    String submitted_date;

    Geocoder geocoder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proforma);
      //  ayes,ano,syes,sno;

        Intent intent = getIntent();
        province = intent.getStringExtra("proo");
        city = intent.getStringExtra("cityy");


        ayes=(RadioButton) findViewById(R.id.ayes);
        ano=(RadioButton) findViewById(R.id.ano);
        syes=(RadioButton) findViewById(R.id.syes);
        sno=(RadioButton) findViewById(R.id.sno);
        arg=(RadioGroup) findViewById(R.id.arg);
        srg=(RadioGroup) findViewById(R.id.srg);
        Date d = new Date();
        submitted_date=DateFormat.getDateInstance(DateFormat.SHORT).format(d);




        arg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.ayes){
                    notavailble.setText(ayes.getText());

                }
                else if (checkedId==R.id.ano){
                    notavailble.setText(ano.getText());

                }

            }
        });
        srg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.syes){
                   Short.setText(syes.getText());

                }
                else if (checkedId==R.id.sno){
                    Short.setText(sno.getText());

                }
            }
        });



        PPP=(ProgressBar) findViewById(R.id.PPP);
        progressBar3=(ProgressBar) findViewById(R.id.progressBar3);
        curentlocationbutton=(Button) findViewById(R.id.button);
        cancelcurrentlocation=(ImageView) findViewById(R.id.cancelcurrentlocation);

        reportsubmit = (Button) findViewById(R.id.reportsumbit);
        nameofdrug = (EditText) findViewById(R.id.nameofdrug);
        genericname = (EditText) findViewById(R.id.genericname);
        dosageform = (EditText) findViewById(R.id.dosageform);
        strength = (EditText) findViewById(R.id.strength);
        packing_pu = (EditText) findViewById(R.id.packing_pu);
        manufacturedby = (EditText) findViewById(R.id.manufacturedby);
        importedby = (EditText) findViewById(R.id.importedby);
        price_pp = (EditText) findViewById(R.id.price_pp);
        price_a = (EditText) findViewById(R.id.price_a);
        Short = (EditText) findViewById(R.id.Short);
        notavailble = (EditText) findViewById(R.id.notavailble);
        date = (EditText) findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  Calendar calendar= Calendar.getInstance();
                int DAY= calendar.get(Calendar.DAY_OF_MONTH);
                int MONTH= calendar.get(Calendar.MONTH);
                int YEAT= calendar.get(Calendar.YEAR);
                picker= new DatePickerDialog(Proforma.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText((month+1)+"/"+dayOfMonth+"/"+year);
                    }
                },YEAT,MONTH,DAY);
                picker.getDatePicker().setMaxDate(System.currentTimeMillis());
                picker.show();

            }
        });
        nameofoutlet = (EditText) findViewById(R.id.nameofoutlet);
        nameofhospital = (EditText) findViewById(R.id.nameofhospital);
        reason = (EditText) findViewById(R.id.reason);
        areaofs = (EditText) findViewById(R.id.areaofs);
        remarks = (EditText) findViewById(R.id.remarks);
        mAuth=FirebaseAuth.getInstance();
        cancelcurrentlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelcurrentlocationfuntion();
            }
        });
        //LOCATION
        textaddress=(TextView) findViewById(R.id.textaddress);
        ADDRESSC=(TextView) findViewById(R.id.ADDRESSC);
        lattitudetextview=(TextView) findViewById(R.id.textView39);

        logitudetextview=(TextView) findViewById(R.id.textView40);

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);


        curentlocationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar3.setVisibility(View.VISIBLE);
                getLastLocation();



            }
        });

        reportsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //nameofdrug genericname dosageform strength;
                Name_Of_Drug = nameofdrug.getText().toString().trim();
                Generic_Name = genericname.getText().toString().trim();
                Dosage_form = dosageform.getText().toString().trim();
                Strength = strength.getText().toString().trim();
                Packing_u_pp = packing_pu.getText().toString().trim();
                Manufactured_By = manufacturedby.getText().toString().trim();

                Imported_by = importedby.getText().toString().trim();
                if (TextUtils.isEmpty(Imported_by)) {
                    Imported_by = "NULL";

                }

                Price_mentioned = price_pp.getText().toString().trim();
                PriceatA = price_a.getText().toString().trim();
                ProductShort = Short.getText().toString().trim();
                Productnotavailble = notavailble.getText().toString().trim();
                Period = date.getText().toString().trim();
                if (TextUtils.isEmpty(Period)) {
                   Period = "NULL";
                }
                Name_of_outlet = nameofoutlet.getText().toString().trim();
                if (TextUtils.isEmpty(Name_of_outlet)) {
                    Name_of_outlet = "NULL";
                }
                Name_of_hospital = nameofhospital.getText().toString().trim();
                if (TextUtils.isEmpty(Name_of_hospital)) {
                    Name_of_hospital = "NULL";
                }
                Reason = reason.getText().toString().trim();
                Area_of_survey = areaofs.getText().toString().trim();
                Remarks = remarks.getText().toString().trim();
                if (TextUtils.isEmpty(addressssc)){
                  addressssc = "NULL";
                }
                if (TextUtils.isEmpty(Locationss))
                {
                 Locationss= "NULL";
                }




                if (TextUtils.isEmpty(Name_Of_Drug) || TextUtils.isEmpty(Generic_Name) || TextUtils.isEmpty(Dosage_form) ||
                        TextUtils.isEmpty(Strength) || TextUtils.isEmpty(Packing_u_pp) || TextUtils.isEmpty(Manufactured_By) ||
                        TextUtils.isEmpty(Price_mentioned) || TextUtils.isEmpty(PriceatA) || TextUtils.isEmpty(ProductShort) ||
                        TextUtils.isEmpty(Productnotavailble) ||  TextUtils.isEmpty(Reason) ||
                        TextUtils.isEmpty(Area_of_survey) || TextUtils.isEmpty(Remarks)) {
                    Toast.makeText(Proforma.this, " Some important fields are missing", Toast.LENGTH_SHORT).show();
                }
                else if (!(ProductShort.equals("YES") || ProductShort.equals("NO"))) {
                    Short.setError("Choose any one option");

                } else if (!(Productnotavailble.equals("YES") || Productnotavailble.equals("NO"))) {
                    Short.setError("Choose any one option");
                    Short.setError(null);

                } else {
                    notavailble.setError(null);

                    PPP.setVisibility(View.VISIBLE);
                    submitreport();

                }


            }
        });


    }

    private void cancelcurrentlocationfuntion() {
        cancelcurrentlocation.setVisibility(View.GONE);
        ADDRESSC.setText(null);
        ADDRESSC.setVisibility(View.GONE);
        textaddress.setVisibility(View.GONE);
    }

    private void getLastLocation() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){

            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<android.location.Location>() {
                @Override
                public void onSuccess(android.location.Location location) {
                     if ( location != null){

                         Geocoder geocoder=new Geocoder(Proforma.this, Locale.getDefault());
                         List<Address> addresses= null;
                         try {

                             addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                             lattitudetextview.setText(""+addresses.get(0).getLatitude());
                             logitudetextview.setText(""+addresses.get(0).getLongitude());
                             ADDRESSC.setText(""+addresses.get(0).getAddressLine(0));
                             textaddress.setVisibility(View.VISIBLE);
                             ADDRESSC.setVisibility(View.VISIBLE);
                             cancelcurrentlocation.setVisibility(View.VISIBLE);
                             lattitude=lattitudetextview.getText().toString().trim();
                             logitude=logitudetextview.getText().toString().trim();
                             Locationss=lattitude+","+logitude;
                             addressssc=ADDRESSC.getText().toString().trim();

                             progressBar3.setVisibility(View.GONE);
                             ///for city getlocalcityname  getcountryname
                         } catch (IOException e) {
                             e.printStackTrace();
                             progressBar3.setVisibility(View.GONE);

                         }




                     }
                }
            });
        }else {
            progressBar3.setVisibility(View.GONE);
            askpermission();
        }
    }

    private void askpermission() {
        ActivityCompat.requestPermissions(Proforma.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode== REQUEST_CODE){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }
            else {
                Toast.makeText(Proforma.this, "Please provide the Requested Permission", Toast.LENGTH_SHORT).show();


            }
        }
    }



    private void submitreport() {

        Date d = new Date();
        reportsubmit.setEnabled(false);
        firebaseUser=mAuth.getCurrentUser();
        useriddd=firebaseUser.getUid();

        String input = Name_Of_Drug;
        String[] words = input.split("\\s+");

        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                String firstLetter = word.substring(0, 1).toUpperCase();
                String remainingLetters = word.substring(1).toLowerCase();
                result.append(firstLetter).append(remainingLetters).append(" ");
            }
        }

        String convertedText = result.toString().trim();



        Map<String,Object> REPORT=new HashMap<>();
        REPORT.put("NAME_OF_DRUG",convertedText);
        REPORT.put("Generic_Name",Generic_Name);
        REPORT.put("Dosage_Form",Dosage_form);
        REPORT.put("Strength",Strength);
        REPORT.put("Packing_Unit_per_Pack",Packing_u_pp);
        REPORT.put("Manufactured_By",Manufactured_By);
        REPORT.put("Imported_By",Imported_by);
        REPORT.put("Price_mentioned_Per_Pack",Price_mentioned);
        REPORT.put("Price_of_availablity",PriceatA);
        REPORT.put("Shortage",ProductShort);
        REPORT.put("Available",Productnotavailble);
        REPORT.put("Period_of_Shortage",Period);
        REPORT.put("Name_of_Outlets",Name_of_outlet);
        REPORT.put("City", city);
        REPORT.put("Province", province);
        REPORT.put("Name_of_Hospital",Name_of_hospital);
        REPORT.put("Reason_Statement_of_seller",Reason);
        REPORT.put("Area_of_Survey",Area_of_survey);

        REPORT.put("Submitted_date",d);
        REPORT.put("Submitted_BY",useriddd);
        REPORT.put("Remarks",Remarks);
        if (Locationss.equals("NULL")){
            geocoder = new Geocoder(this);
            String locationnn=province+" "+" "+city+" "+ Area_of_survey;
            try {
                List<Address> addresses = geocoder.getFromLocationName(locationnn, 1);

                if (addresses.size() > 0) {
                    double latitudeee = addresses.get(0).getLatitude();
                    double longitudeee = addresses.get(0).getLongitude();
                    Locationss = latitudeee+","+longitudeee;
                    REPORT.put("Location",Locationss);
                    REPORT.put("ADDRESS_OF_COMPLAIN",locationnn);

                } else {
                    REPORT.put("Location","NULL");
                    REPORT.put("ADDRESS_OF_COMPLAIN","NULL");

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else {
            REPORT.put("Location",Locationss);
            REPORT.put("ADDRESS_OF_COMPLAIN",addressssc);
        }




        db.collection("REPORTS").add(REPORT).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(Proforma.this, " Added", Toast.LENGTH_SHORT).show();
                reportsubmit.setEnabled(true);
                //nameofdrug genericname dosageform strength;
                nameofdrug.setText("");
                genericname.setText("");
                dosageform.setText("");
                strength.setText("");
                Locationss="NULL";
                PPP.setVisibility(View.INVISIBLE);
                Map<String, Object> counter = new HashMap<>();
                Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                long compl = 1;
                long year = cal.get(Calendar.YEAR);
                long month = cal.get(Calendar.MONTH) + 1; // Add 1 to get the month in numbers (January = 1)
                System.out.println("Year: " + year);
                System.out.println("Month: " + month);
                db.collection("Report_Counter").whereEqualTo("Year", year).whereEqualTo("Month", month).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                            counter.put("Year", year);
                            counter.put("Month", month);
                            counter.put("Complains", compl);
                            db.collection("Report_Counter").add(counter);
                            progressBar3.setVisibility(View.GONE);
                        } else {

                            DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                            long currentComplains = documentSnapshot.getLong("Complains");
                            counter.put("Complains", currentComplains + compl);
                            documentSnapshot.getReference().update(counter);
                            progressBar3.setVisibility(View.GONE);
                        }

                    }
                });








            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Proforma.this, "Error check your internet connection", Toast.LENGTH_SHORT).show();
                reportsubmit.setEnabled(true);
                PPP.setVisibility(View.INVISIBLE);
            }
        });




    }

}