package drap.dsr.dispms;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dispms.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class ComplainAdapter  extends FirestoreRecyclerAdapter <Complainmodel,ComplainAdapter.ComplainHolder>{


  private   OnListItemClicked2 onListItemClicked2;


    public ComplainAdapter(@NonNull FirestoreRecyclerOptions<Complainmodel> options, OnListItemClicked2 onListItemClicked2){
        super(options);
        this.onListItemClicked2=onListItemClicked2;


    }

    @Override
    protected void onBindViewHolder(@NonNull ComplainHolder holder, int position, @NonNull Complainmodel model) {
       // TextView Ccity,Ctype,Cdate,Cdrugname,Cdosageform,Ccomplainedby,Cphcontact,Cid,Caddress,CRemarks;
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        String id=user.getUid();
        holder.Ccity.setText(model.getCity());
        holder.Ctype.setText(model.getTYPE());
     //   holder.Cdate.setText(model.getComplain_Date());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(model.getComplain_Date());
        holder.Cdate.setText(date);


        holder.Cdrugname.setText(model.getName_of_Drug());
        holder.Cdosageform.setText(model.getDosage_Form());
        holder.Ccomplainedby.setText(model.getName());
        holder.Cphcontact.setText(model.getContact());
        holder.Cid.setText(model.getID());
        holder.Caddress.setText(model.getAddress());
        holder.CRemarks.setText(model.getRemarks());

        holder.undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,Object> updater=new HashMap<>();
                String documentId = getSnapshots().getSnapshot(holder.getAdapterPosition()).getId();
                updater.put("STATUS", "UNCHECKED");
                updater.put("inspector_id","NULL");
                db.collection("Complain").document(documentId).update(updater);



            }
        });
        holder.Completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> updater=new HashMap<>();
                String documentId = getSnapshots().getSnapshot(holder.getAdapterPosition()).getId();
                updater.put("STATUS", "COMPLETED");
                db.collection("Complain").document(documentId).update(updater);

            }
        });
        holder.invalid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> updater=new HashMap<>();
               // updater.put("inspector_id",userid);
                String documentId = getSnapshots().getSnapshot(holder.getAdapterPosition()).getId();
                updater.put("STATUS", "INVALID");
                db.collection("Complain").document(documentId).update(updater);

            }
        });

        holder.Cphcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  String phoneNumber = holder.Cphcontact.getText().toString();
                Context context = v.getContext();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Dialer app not found", Toast.LENGTH_SHORT).show();
                }  */
            }
        });
        holder.cardView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   String address = holder.Caddress.getText().toString();
                Context context = v.getContext();
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(mapIntent);
                } else {
                    Toast.makeText(context, "Google Maps app not found", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        holder.Caddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  String address = holder.Caddress.getText().toString();
                Context context = v.getContext();
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(mapIntent);
                } else {
                    Toast.makeText(context, "Google Maps app not found", Toast.LENGTH_SHORT).show();
                }*/
            }
        });










    }


    @NonNull
    @Override
    public ComplainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item2,parent,false);
        return new ComplainHolder(view);
    }
    public void deleteitem2( int position) {




        getSnapshots().getSnapshot(position).getReference().delete();



    }

    class ComplainHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView Ccity,Ctype,Cdate,Cdrugname,Cdosageform,Ccomplainedby,Cphcontact,Cid,Caddress,CRemarks;
        Button undo,Completed,invalid;
        CardView cardView8;

        public ComplainHolder(@NonNull View itemView) {
            super(itemView);
            Ccity=itemView.findViewById(R.id.Ccity);
            Ctype=itemView.findViewById(R.id.Ctype);
            Cdate=itemView.findViewById(R.id.Cdate);
            Cdrugname=itemView.findViewById(R.id.Cdrugname);
            Cdosageform=itemView.findViewById(R.id.textView53);
            Ccomplainedby=itemView.findViewById(R.id.textView56);
            Cphcontact=itemView.findViewById(R.id.textView57);
            Cid=itemView.findViewById(R.id.textView58);
            Caddress=itemView.findViewById(R.id.textView60);
            CRemarks=itemView.findViewById(R.id.textView61);
            undo=itemView.findViewById(R.id.button5);
            Completed=itemView.findViewById(R.id.button3);
            invalid=itemView.findViewById(R.id.button4);
            Completed.setOnClickListener(this);
            itemView.setOnClickListener(this);

            cardView8=itemView.findViewById(R.id.cardView8);

            //  multiiple listonerssssss;;;;
        }

        @Override
        public void onClick(View v) {

            //  multiiple listonerssssss;;;;
            int position=getAdapterPosition();

            onListItemClicked2.onitemclicked2(getSnapshots().getSnapshot(position),position);

        }
    }
    public  interface OnListItemClicked2 {
        void onitemclicked2(DocumentSnapshot documentSnapshot, int position);
    }
}
