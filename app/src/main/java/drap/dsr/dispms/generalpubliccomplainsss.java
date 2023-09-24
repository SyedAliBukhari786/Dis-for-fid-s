package drap.dsr.dispms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dispms.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class generalpubliccomplainsss extends AppCompatActivity {
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
   String userid,aaaa;
   Button allcomplains;

   private  NoteAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generalpubliccomplainsss);
        recyclerView=(RecyclerView)  findViewById(R.id.rttttt);
        allcomplains=(Button)  findViewById(R.id.button7);
        Intent intent = getIntent();
        // retrieve the string extra from intent

        aaaa = intent.getStringExtra("cityy");
        if (!aaaa.isEmpty()){


            Query query = db.collection("Complain").whereEqualTo("City",aaaa).whereEqualTo("STATUS","UNCHECKED");
            FirestoreRecyclerOptions<UserModel> options = new FirestoreRecyclerOptions.Builder<UserModel>()
                    .setQuery(query, UserModel.class)
                    .build();
            adapter = new NoteAdapter(options);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);
            adapter.startListening();
        }else {

            Toast.makeText(this, "Network error", Toast.LENGTH_SHORT).show();
        }


        allcomplains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(generalpubliccomplainsss.this, all_country_complains.class));
                finish();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}