package drap.dsr.dispms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dispms.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ComplainManagment extends AppCompatActivity implements ComplainAdapter.OnListItemClicked2 {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    RecyclerView recyclerVieww;
    LinearLayoutManager linearLayoutManagerr;
    private ComplainAdapter complainAdapter;
    String useridd;
    TextView textView51, textView59, textView55;
    String cityy;
    String phonenumber, username, designation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_managment);
        Intent intent = getIntent();

        // retrieve the string extra from intent
        useridd = intent.getStringExtra("uid");
        cityy = intent.getStringExtra("cityy");
        phonenumber = intent.getStringExtra("phonenumer");
        designation = intent.getStringExtra("designation");
        username = intent.getStringExtra("username");
        textView59 = findViewById(R.id.textView59);
        textView51 = findViewById(R.id.textView51);
        textView55 = findViewById(R.id.textView55);

        textView51.setText(username);
        textView59.setText(phonenumber);
        textView55.setText(designation);

        recyclerVieww = findViewById(R.id.recyclerview2);
        linearLayoutManagerr = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        Query query = db.collection("Complain")
                .whereEqualTo("inspector_id", useridd)
                .whereEqualTo("STATUS", "PENDING")
                .orderBy("Complain_Date", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Complainmodel> options = new FirestoreRecyclerOptions.Builder<Complainmodel>()
                .setQuery(query, Complainmodel.class)
                .build();

        complainAdapter = new ComplainAdapter(options, ComplainManagment.this);
        recyclerVieww.setHasFixedSize(true);
        recyclerVieww.setLayoutManager(linearLayoutManagerr);
        recyclerVieww.setAdapter(complainAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        complainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        complainAdapter.stopListening();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("uid", useridd);
        outState.putString("cityy", cityy);
        outState.putString("phonenumer", phonenumber);
        outState.putString("designation", designation);
        outState.putString("username", username);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        useridd = savedInstanceState.getString("uid");
        cityy = savedInstanceState.getString("cityy");
        phonenumber = savedInstanceState.getString("phonenumer");
        designation = savedInstanceState.getString("designation");
        username = savedInstanceState.getString("username");

        textView51.setText(username);
        textView59.setText(phonenumber);
        textView55.setText(designation);
    }


    @Override
    public void onitemclicked2(DocumentSnapshot documentSnapshot, int position) {

    }
}
