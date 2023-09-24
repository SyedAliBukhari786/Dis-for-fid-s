package drap.dsr.dispms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.dispms.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class all_country_complains extends AppCompatActivity {

    RecyclerView allcomplains;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
    private  allcountrycomplains adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_country_complains);
        allcomplains=(RecyclerView) findViewById(R.id.newrecycleview);

        Query query = db.collection("Complain").whereEqualTo("STATUS","UNCHECKED");
        FirestoreRecyclerOptions<UserModel> options = new FirestoreRecyclerOptions.Builder<UserModel>()
                .setQuery(query, UserModel.class)
                .build();
        adapter = new  allcountrycomplains(options);
        allcomplains.setHasFixedSize(true);
        allcomplains.setLayoutManager(linearLayoutManager);
        allcomplains.setAdapter(adapter);
        adapter.startListening();

















    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}