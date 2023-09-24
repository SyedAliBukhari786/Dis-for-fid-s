package drap.dsr.dispms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dispms.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Generalpubliccomplains extends AppCompatActivity{

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    int positionmacted;
    Boolean test = true;
    private NoteAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, RecyclerView.VERTICAL, false);



    String aaaa;
    TextView textView37;
    String userid;

    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generalpubliccomplains);













    }


    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        // retrieve the string extra from intent
        userid = intent.getStringExtra("uid");
        aaaa = intent.getStringExtra("cityy");
        if (!userid.isEmpty() || !aaaa.isEmpty()){


            Query query = db.collection("Complain");
            FirestoreRecyclerOptions<UserModel> options = new FirestoreRecyclerOptions.Builder<UserModel>()
                    .setQuery(query, UserModel.class)
                    .build();
            adapter = new NoteAdapter(options);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);
            adapter.startListening();



        }else {

            Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show();
        }






    }


    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }



}