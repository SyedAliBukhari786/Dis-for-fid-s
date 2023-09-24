package drap.dsr.dispms;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dispms.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class NoteAdapter extends FirestoreRecyclerAdapter<UserModel, NoteAdapter.NoteHolder> {



    public NoteAdapter(@NonNull FirestoreRecyclerOptions<UserModel> options){
        super(options);



    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull UserModel model) {
        //Cityyyy,TYPEE,drugname,dosagename,complainedby,phcontact,IDEEE,Address,Quardinates,Remarksss;
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        FirebaseAuth mauth=FirebaseAuth.getInstance();
        FirebaseUser user=mauth.getCurrentUser();
        String userid= user.getUid();

        holder.Cityyyy.setText(model.getCity());
        holder.TYPEE.setText(model.getTYPE() + ",");
        holder.drugname.setText(model.getName_of_Drug());
        holder.dosagename.setText(model.getDosage_Form());
        holder.complainedby.setText(model.getName());
        holder.phcontact.setText(model.getContact());
        holder.IDEEE.setText(model.getID());
        holder.Address.setText(model.getAddress());
        holder.Quardinates.setText(model.getLocation());
        holder.Remarksss.setText(model.getRemarks());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(model.getComplain_Date());
        holder.Datee.setText(date);

        holder.phcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = holder.phcontact.getText().toString();
                Context context = v.getContext();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Dialer app not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.Quardinates2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coordinates = holder.Quardinates.getText().toString();
                Context context = v.getContext();
                Uri gmmIntentUri = Uri.parse("geo:" + coordinates + "?q=" + coordinates);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(mapIntent);
                } else {
                    Toast.makeText(context, "Google Maps app not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.Address2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = holder.Address.getText().toString();
                Context context = v.getContext();
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(mapIntent);
                } else {
                    Toast.makeText(context, "Google Maps app not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.Quardinates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coordinates = holder.Quardinates.getText().toString();
                Context context = v.getContext();
                Uri gmmIntentUri = Uri.parse("geo:" + coordinates + "?q=" + coordinates);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(mapIntent);
                } else {
                    Toast.makeText(context, "Google Maps app not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = holder.Address.getText().toString();
                Context context = v.getContext();
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(mapIntent);
                } else {
                    Toast.makeText(context, "Google Maps app not found", Toast.LENGTH_SHORT).show();
                }
            }
        });




        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String documentId = getSnapshots().getSnapshot(holder.getAdapterPosition()).getId();
                // add inspector id and status.........
                Map<String ,Object> manger=new HashMap<>();


                manger.put("inspector_id",userid);
                manger.put("STATUS", "PENDING");
                db.collection("Complain").document(documentId).update(manger).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            Toast.makeText(view.getContext(), "Added", Toast.LENGTH_SHORT).show();

                        }
                        else {

                            Toast.makeText(view.getContext(), "Network issue", Toast.LENGTH_SHORT).show();
                        }
                    }
                });





            }
        });




    }







    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        return new NoteHolder(v);
    }





    public void deleteitem( int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class NoteHolder extends RecyclerView.ViewHolder  {

        TextView Cityyyy, TYPEE, drugname, dosagename, complainedby, phcontact, IDEEE, Address,Address2, Quardinates2 ,Quardinates, Remarksss,Datee;
           Button add;
        public NoteHolder(@NonNull View itemView)  {
            super(itemView);
            Cityyyy = itemView.findViewById(R.id.CITYYYY);
            TYPEE = itemView.findViewById(R.id.TYPEE);
            drugname = itemView.findViewById(R.id.drugname);
            dosagename = itemView.findViewById(R.id.dosagename);
            complainedby = itemView.findViewById(R.id.complainedby);
            phcontact = itemView.findViewById(R.id.phcontact);
            IDEEE = itemView.findViewById(R.id.IDEEE);
            Address = itemView.findViewById(R.id.Address);
            Address2 = itemView.findViewById(R.id.textView44);
            Quardinates = itemView.findViewById(R.id.Quardinates);
            Quardinates2 = itemView.findViewById(R.id.quardinates);

            Remarksss = itemView.findViewById(R.id.Remarksss);
            Datee=itemView.findViewById(R.id.Dateee);
            add=itemView.findViewById(R.id.button6);


        }







    }




}
