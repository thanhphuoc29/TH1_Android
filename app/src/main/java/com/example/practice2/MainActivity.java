package com.example.practice2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.practice2.models.SpinnerAdapter;
import com.example.practice2.models.Tour;
import com.example.practice2.models.TourAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TourAdapter.TourListener,SearchView.OnQueryTextListener{

    private List<Tour> tours = new ArrayList<>();
    private Spinner spin;
    private RecyclerView recyclerView;
    private TourAdapter tourAdapter;
    private Button btnAdd, btnUpdate;
    private EditText nameTour, schedule,dateInput, timeInput;
    private SearchView searchView;
    private int currentPositionTour;

    private SpinnerAdapter spinnerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        spin = findViewById(R.id.spin);
        spinnerAdapter = new SpinnerAdapter(this);
        spin.setAdapter(spinnerAdapter);
        btnAdd = findViewById(R.id.btnAddTour);
        btnUpdate = findViewById(R.id.btnUpdateTour);
        btnUpdate.setEnabled(false);
        nameTour = findViewById(R.id.txtName);
        schedule = findViewById(R.id.txtSchedule);
        //code them
//        timeInput = findViewById(R.id.timeStart);
//        dateInput = findViewById(R.id.dateStart);
        searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);
//        dateInput.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                Calendar calendar = Calendar.getInstance();
//                int year = calendar.get(Calendar.YEAR);
//                int month = calendar.get(Calendar.MONTH);
//                int day = calendar.get(Calendar.DAY_OF_MONTH);
//                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                        String formatdate = String.format("%02d/%02d/%d",day,month+1,year);
//                        dateInput.setText(formatdate);
//                    }
//                },year,month,day);
//                datePickerDialog.show();
//            }
//        });


        recyclerView = findViewById(R.id.recycleTour);
        tourAdapter = new TourAdapter(this);
        tourAdapter.setTourListener(this);
        LinearLayoutManager layout = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(tourAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int imgResource = Integer.parseInt(spin.getSelectedItem().toString());
                    String name = nameTour.getText().toString();
                    String scheduleTour = schedule.getText().toString();
                    if(name.isEmpty() || scheduleTour.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Vui lòng không để trống!", Toast.LENGTH_SHORT).show();
                        if(name.isEmpty()) {
                            nameTour.setError("Tên tour không được để trống");
                            nameTour.requestFocus();
                        } else if (scheduleTour.isEmpty()) {
                            schedule.setError("Lịch trình không được để trống");
                            schedule.requestFocus();
                        }
                        return;
                    }
                    Tour tour = new Tour(name,scheduleTour,imgResource);
                    tourAdapter.addTour(tour);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int imgResource = Integer.parseInt(spin.getSelectedItem().toString());
                    String name = nameTour.getText().toString();
                    String scheduleTour = schedule.getText().toString();
                    if(name.isEmpty() || scheduleTour.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Vui lòng không để trống!", Toast.LENGTH_SHORT).show();
                        if(name.isEmpty()) {
                            nameTour.setError("Tên tour không được để trống");
                            nameTour.requestFocus();
                        } else if (scheduleTour.isEmpty()) {
                            schedule.setError("Lịch trình không được để trống");
                            schedule.requestFocus();
                        }
                        return;
                    }
                    Tour tour = new Tour(name,scheduleTour,imgResource);
                    Toast.makeText(MainActivity.this, "update", Toast.LENGTH_SHORT).show();
                    tourAdapter.update(currentPositionTour,tour);
                    btnUpdate.setEnabled(false);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        currentPositionTour = position;
        Tour tour = tourAdapter.getItem(position);
        spin.setSelection(spinnerAdapter.getItemPosition(tour.imgResource));
        nameTour.setText(tour.name);
        schedule.setText(tour.schedule);
        btnUpdate.setEnabled(true);
    }
    public void selectDatePicker(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String formatdate = String.format("%02d/%02d/%d",day,month+1,year);
                dateInput.setText(formatdate);
            }
        },year,month,day);
        datePickerDialog.show();
    }
    public void selectTimePicker(View view) {
        Calendar calendar = Calendar.getInstance();
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int m = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int h, int m) {
                String timeFormat = String.format("%02d:%02d",h,m);
                timeInput.setText(timeFormat);
            }
        },h,m,true);
        timePickerDialog.show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        filterTour(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filterTour(newText);
        return false;
    }
    public void filterTour(String key) {
        List<Tour> filter = new ArrayList<>();
        for(Tour tour : tourAdapter.getBackupList()) {
            if(tour.name.toLowerCase().contains(key.toLowerCase())) filter.add(tour);
        }
        tourAdapter.setFilter(filter);
    }
}