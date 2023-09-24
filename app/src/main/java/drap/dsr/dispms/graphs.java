package drap.dsr.dispms;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dispms.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class graphs extends AppCompatActivity {


    private LineChart lineChart;
    FirebaseAuth mauth=FirebaseAuth.getInstance();
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    String city;
    CollectionReference complainRef;
    Timestamp endTimestamp;
    Timestamp startTimestamp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);
        Intent intent = getIntent();

      // retrieve the string extra from intent
         city = intent.getStringExtra("cityy");
        lineChart = findViewById(R.id.line_chart);
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startDate = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endDate = calendar.getTime();

       startTimestamp = new Timestamp(startDate);
        endTimestamp = new Timestamp(endDate);




        complainRef = db.collection("Complain");

        fetchDataAndProcessData(lineChart);





    }
    private void fetchDataAndProcessData(LineChart lineChart) {


        complainRef.whereEqualTo("City",city).whereGreaterThanOrEqualTo("Complain_Date", startTimestamp).whereLessThanOrEqualTo("Complain_Date", endTimestamp).get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<String> drugsList = new ArrayList<>();

            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                String drugName = documentSnapshot.getString("Name_of_Drug");
                drugsList.add(drugName);
            }

            processDrugData(drugsList, lineChart);
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Error fetching data: ", e);
        });
    }


    private void processDrugData(List<String> drugsList, LineChart lineChart) {
        Map<String, Integer> drugCountMap = new HashMap<>();

        for (String drug : drugsList) {
            Integer count = drugCountMap.get(drug);
            if (count == null) {
                drugCountMap.put(drug, 1);
            } else {
                drugCountMap.put(drug, count + 1);
            }
        }

        List<Map.Entry<String, Integer>> sortedDrugsList = new ArrayList<>(drugCountMap.entrySet());

        // Sort the drugs by occurrence count in descending order
        Collections.sort(sortedDrugsList, (entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Extract the top 5 drugs
        List<String> top5Drugs = new ArrayList<>();
        List<Integer> top5Counts = new ArrayList<>();
        int count = 0;
        for (Map.Entry<String, Integer> entry : sortedDrugsList) {
            if (count >= 5) {
                break;
            }
            top5Drugs.add(entry.getKey());
            top5Counts.add(entry.getValue());
            count++;
        }

        // Prepare the data for the line chart
        ArrayList<Entry> entries = new ArrayList<>();

        for (int i = 0; i < top5Drugs.size(); i++) {
            String drugName = top5Drugs.get(i);
            int occurrenceCount = top5Counts.get(i);
            entries.add(new Entry(i, occurrenceCount));
        }

// Create a dataset with the data entries
        LineDataSet dataSet = new LineDataSet(entries, "Top 5 Drugs");
        dataSet.setColor(Color.BLUE);
        dataSet.setLineWidth(2f);
        dataSet.setCircleColor(Color.BLUE);
        dataSet.setCircleRadius(4f);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);

        // Create a LineData object with the datasets
        LineData lineData = new LineData(dataSets);

        // Set the LineData to the LineChart
        lineChart.setData(lineData);

        // Customize the appearance of the chart
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(top5Drugs));
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getLegend().setEnabled(false);

        TextView descriptionTextView = findViewById(R.id.top5DrugsTextView);
        descriptionTextView.setText("Current month's top drug complains of "+city);

        lineChart.getDescription().setText("");

// Position the TextView at the center and on the top of the chart


        // Refresh the chart
        lineChart.invalidate();

        // Display the top 5 drugs below the chart
        StringBuilder top5DrugsText = new StringBuilder("Top 5 Drugs: ");
        for (int i = 0; i < top5Drugs.size(); i++) {
            String drugName = top5Drugs.get(i);
            int occurrenceCount = top5Counts.get(i);
            top5DrugsText.append(drugName).append(" (").append(occurrenceCount).append(" times)");
            if (i < top5Drugs.size() - 1) {
                top5DrugsText.append(", ");
            }
        }
    //    TextView top5DrugsTextView = findViewById(R.id.top5DrugsTextView);
      //  top5DrugsTextView.setText(top5DrugsText.toString());
    }



}
