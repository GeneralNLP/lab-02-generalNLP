package com.example.listycity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    private int selectedPos = -1; // to make sure no element beyond the array vals are selected by default



    private final ActivityResultLauncher<Intent> addCityLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String name = result.getData().getStringExtra(AddCityActivity.city_name_added);
                    if (name != null && !name.trim().isEmpty()) {
                        dataList.add(name.trim());
                        cityAdapter.notifyDataSetChanged();
                    }
                }
            });

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        cityList = findViewById(R.id.city_list);
        

        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        cityList.setSelector(android.R.color.darker_gray); // when u select on a item in the listview this will be the highlighted color..
        
        String []cities = {"Calgary", "Edmonton", "Vancouver", "Toronto", "Montreal","Delhi","Mumbai"};
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        Button ADD_CITY_BTN = (Button) findViewById(R.id.btn_add_city);
        ADD_CITY_BTN.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddCityActivity.class);
            addCityLauncher.launch(intent);
        });


        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPos = position;

                cityList.clearChoices();
                cityList.setItemChecked(position, true);
                cityList.setSelection(position);
            }
        });

        // Set up delete button
        Button DELETE_CITY_BTN = (Button) findViewById(R.id.btn_delete_city);
        DELETE_CITY_BTN.setOnClickListener(view -> {
            if (selectedPos >= 0 && selectedPos < dataList.size()) {
                String deletedCity = dataList.get(selectedPos);
                dataList.remove(selectedPos);
                cityAdapter.notifyDataSetChanged();
                

                cityList.clearChoices();
                selectedPos = -1;
            } else {
                Toast.makeText(MainActivity.this, "Please select a city to delete", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
}