package com.example.Activity;

import static com.example.Activity.SharedPreferenceConfig.Shered_Pref;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.validators.Validator;
import com.example.Constant.Constant;
import com.example.Model.PinCodeData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Toolbar toolbar;
    AwesomeValidation awesomeValidation;
    Spinner gender;
    Button registerBtn,pinCheckBtn;
    EditText pincode,dateOfBirth,mobileNumber,fullName,address1,address2;
    TextView stateValue,districtValue;
    DatePickerDialog.OnDateSetListener setListener;
    ImageView calenderImage;
    SharedPreferenceConfig sharedPreferenceConfig;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    String mobileNumber2,fullName2,adress3,adress4,pincode3,dob1;
    Boolean save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar = findViewById(R.id.toolbar_actionbar);
        gender = findViewById(R.id.genderID);
        registerBtn = findViewById(R.id.registerID);
        pinCheckBtn = findViewById(R.id.pinCodeCheckID);
        districtValue = findViewById(R.id.DistrictValueID);
        dateOfBirth = findViewById(R.id.dateOfBirthID);
        calenderImage = findViewById(R.id.calenderIconID);
        stateValue = findViewById(R.id.StateValueID);
        pincode = findViewById(R.id.pinCodeID);
        fullName = findViewById(R.id.FullName);
        mobileNumber = findViewById(R.id.mobileNumber);
        address1  = findViewById(R.id.addressLine1);
        address2 = findViewById(R.id.addressLine2);

        getSupportActionBar().setTitle("Register");

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.mobileNumber, ".{10}", R.string.mobileNumber);
        awesomeValidation.addValidation(this, R.id.FullName, "^.{1,50}$", R.string.userName);
        awesomeValidation.addValidation(this, R.id.addressLine1, "^.{3,50}$", R.string.address);
        awesomeValidation.addValidation(this, R.id.addressLine2, "^.{1,50}$", R.string.address2);

        Calendar calendar = Calendar.getInstance();
         final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
       final int day = calendar.get(Calendar.DAY_OF_MONTH);

        calenderImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Register.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = day+"/"+month+"/"+year;
                dateOfBirth.setText(date);
            }
        };

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(adapter);
        gender.setOnItemSelectedListener(this);

        sharedPreferences = getSharedPreferences(Shered_Pref, MODE_PRIVATE);
         mobileNumber2 = sharedPreferences.getString("mobileNumber", "");
         fullName2 = sharedPreferences.getString("fullName", "");
         adress3 = sharedPreferences.getString("address1", "");
         adress4 = sharedPreferences.getString("address2", "");
         pincode3 = sharedPreferences.getString("pincode", "");
         dob1 = sharedPreferences.getString("dob", "");
         save = sharedPreferences.getBoolean("loginBooleanValue",false);

        if(save){
               startActivity(new Intent(Register.this,Weather_Today.class));
               finish();
        }



        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) {

                    sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());
                    sharedPreferenceConfig.mobileNumber = mobileNumber.getText().toString();
                    sharedPreferenceConfig.fullName = fullName.getText().toString();
                    sharedPreferenceConfig.DOB = dateOfBirth.getText().toString();
                    sharedPreferenceConfig.Address1 = address1.getText().toString();
                    sharedPreferenceConfig.Address2 = address2.getText().toString();
                    sharedPreferenceConfig.pincode = pincode.getText().toString();
                    sharedPreferenceConfig.saveValue = true;
                    sharedPreferenceConfig.update(Register.this);

                    Intent intent = new Intent(Register.this, Weather_Today.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        pincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                  pinCheckBtn.setEnabled(pincode.length() == 6);
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        pinCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pincode.isEnabled()){
                    checkPincodeData();
                }
            }
        });
    }
    private void checkPincodeData() {

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        String URL = Constant.PIN_CODE+pincode.getText().toString().trim();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                   if(response.length()>0){

                           JSONObject jsonObject = response.getJSONObject(0);
                           JSONArray postofficeArray = jsonObject.getJSONArray("PostOffice");
                           JSONObject state = postofficeArray.getJSONObject(0);
                           PinCodeData pinCodeData = new PinCodeData();
                           pinCodeData.setState(state.getString("State"));
                           pinCodeData.setDistrict(state.getString("District"));
                           stateValue.setText(pinCodeData.getState());
                           districtValue.setText(pinCodeData.getDistrict());

                   }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: ");
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String genderSelect = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}