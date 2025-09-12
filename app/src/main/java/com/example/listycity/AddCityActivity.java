package com.example.listycity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddCityActivity extends AppCompatActivity{

    public static final String city_name_added = "EXTRA_CITY_NAME";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        EditText city_name = (EditText) findViewById(R.id.city_name_editor);
        Button add_city_button = (Button) findViewById(R.id.confirmation_button);

        add_city_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cityName = city_name.getText().toString();
                Intent replyIntent = new Intent();
                replyIntent.putExtra(city_name_added, cityName);
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });
    }

}
