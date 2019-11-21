package com.example.hotelbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button btncal;
    private TextView tvCheckIn, tvCheckOut, tvTotal, tvLocation, tvRoomType, tvTotalRoom,
            tvServiceC, tvVat, tvOutputCheckIn, tvOutputCheckOut;
    private Boolean d1, d2;
    private EditText etAdult, etChildren, etRooms;

    private Spinner spinLocation, spinrtype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinLocation = findViewById(R.id.slocation);
        spinrtype = findViewById(R.id.srtype);
        btncal = findViewById(R.id.btncal);
        tvCheckIn = findViewById(R.id.tvcidate);
        tvCheckOut = findViewById(R.id.tvcodate);
        etAdult = findViewById(R.id.etAdult);
        etChildren = findViewById(R.id.etChildren);
        etRooms = findViewById(R.id.etRoom);
        tvTotal = findViewById(R.id.tvTotal);
        tvLocation = findViewById(R.id.tvLocation);
        tvRoomType = findViewById(R.id.tvRoomT);
        tvTotalRoom = findViewById(R.id.tvNoOfRoom);
        tvServiceC = findViewById(R.id.tvService);
        tvVat = findViewById(R.id.tvVat);
        tvOutputCheckIn = findViewById(R.id.tvDCheckIn);
        tvOutputCheckOut = findViewById(R.id.tvDCheckOut);

        String locations[] = {"Bhaktapur", "Kathmandu", "Chitwan", "Lalitpur"};
        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                locations
        );
        spinLocation.setAdapter(adapter);

        final String Room[] = {"Delux", "AC", "Platinum"};
        ArrayAdapter adapter1 = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                Room
        );
        spinrtype.setAdapter(adapter1);

        tvCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadDatePicker();
                d1 = true;
            }
        });


        tvCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadDatePicker();
                d2 = true;
            }
        });

        btncal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(tvCheckIn.getText())) {
                    tvCheckIn.setError("Enter CheckIn Date");
                    return;
                }

                if (TextUtils.isEmpty(tvCheckOut.getText())) {
                    tvCheckOut.setError("Enter CheckOut Date");
                    return;
                }

                if (TextUtils.isEmpty(etAdult.getText())) {
                    etAdult.setError("Enter No of Adults");
                    return;
                }

                if (TextUtils.isEmpty(etChildren.getText())) {
                    etChildren.setError("Enter No of Children");
                    return;
                }

                if (TextUtils.isEmpty(etRooms.getText())) {
                    etRooms.setError("Enter No of Rooms");
                    return;
                }

                String RoomType = spinrtype.getSelectedItem().toString();
                String Place = spinLocation.getSelectedItem().toString();

                if (Place == "Select Location") {
                    Toast.makeText(MainActivity.this, "Please select your Location", Toast.LENGTH_SHORT).show();
                }

                String CheckIn = tvCheckIn.getText().toString();
                String CheckOut = tvCheckOut.getText().toString();
                int TotalRooms = Integer.parseInt(etRooms.getText().toString());
                int RoomValue = 0;
                int Price;
                int TotalPrice;
                int Vat;
                int ServiceCharge;

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                try {
                    Date date1 = sdf.parse(CheckIn);
                    Date date2 = sdf.parse(CheckOut);
                    long diff = date2.getTime() - date1.getTime();
                    long diffDays = diff / (24 * 60 * 60 * 1000);
                    int days = (int) diffDays;

                    if (RoomType == "Delux") {
                        RoomValue = 2000;
                    } else if (RoomType == "AC") {
                        RoomValue = 3000;
                    } else if (RoomType == "Platinum") {
                        RoomValue = 4000;
                    } else {
                        Toast.makeText(MainActivity.this, "Please Select Room Type", Toast.LENGTH_SHORT).show();
                    }

                    Price = RoomValue * TotalRooms * days;
                    Vat = (Price * 13) / 100;
                    ServiceCharge = (Price * 10) / 100;
                    TotalPrice = Price + Vat + ServiceCharge;

                    tvLocation.setText("Location : ".concat(Place));
                    tvRoomType.setText("Room Type : ".concat(RoomType));
                    tvOutputCheckIn.setText("CheckIn : ".concat(CheckIn));
                    tvOutputCheckOut.setText("CheckOut : ".concat(CheckOut));
                    tvTotalRoom.setText("Total Rooms: ".concat(Integer.toString(TotalRooms)));
                    tvServiceC.setText("Service Charge: ".concat(Integer.toString(ServiceCharge)));
                    tvVat.setText("Vat: ".concat(Integer.toString(Vat)));
                    tvTotal.setText("Total : ".concat(Integer.toString(TotalPrice)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void LoadDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(c.YEAR);
        int month = c.get(c.MONTH);
        int day = c.get(c.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = month + "/" + dayOfMonth + "/" + year;
                if (d1 == true){
                    tvCheckIn.setText(date);
                    d1 = false;
                }
                else if (d2 == true)
                {
                    tvCheckOut.setText(date);
                    d2 = true;
                }
            }
        },year,month,day);
        datePickerDialog.show();
    }
}

