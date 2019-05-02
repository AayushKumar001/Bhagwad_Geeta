package com.application.aayush.geeta;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;


/**
 * Created by Aayush on 5/6/2017.
 */

public class MyProfile extends AppCompatActivity {
    Button save;
    TextInputLayout name,mobileNumber,email,address,city;
    TextInputEditText name_input,mobileNumber_input,email_input,address_input,city_input;
    Bundle bundle;
    public static final String DEFAULT = "N/A";
    int flagValue = 0;
    boolean loginflag = false;
    public boolean dialogflag = false;
    DatabaseHandler dataBaseHandler ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        dataBaseHandler = new DatabaseHandler(this);
        bundle = new Bundle();
        name = (TextInputLayout) findViewById(R.id.input_name_wrapper);
        mobileNumber = (TextInputLayout) findViewById(R.id.input_mobile_wrapper);
        email = (TextInputLayout) findViewById(R.id.input_email_wrapper);
        address = (TextInputLayout) findViewById(R.id.input_address_wrapper);
        city = (TextInputLayout) findViewById(R.id.input_city_wrapper);
        name_input = (TextInputEditText) findViewById(R.id.input_name);
        mobileNumber_input = (TextInputEditText) findViewById(R.id.input_mobile_number);
        email_input = (TextInputEditText) findViewById(R.id.input_email_id);
        address_input = (TextInputEditText) findViewById(R.id.input_address);
        city_input = (TextInputEditText) findViewById(R.id.input_city);
        final SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        final SharedPreferences sharedPreferences1 = getSharedPreferences("app_data", Context.MODE_PRIVATE);
        int count1 = sharedPreferences.getAll().size();
        if(count1 != 0){
            name_input.setText(sharedPreferences.getString("name",DEFAULT));
            mobileNumber_input.setText(sharedPreferences.getString("mobile_no",DEFAULT));
            email_input.setText(sharedPreferences.getString("email_id",DEFAULT));
            address_input.setText(sharedPreferences.getString("address",DEFAULT));
            city_input.setText(sharedPreferences.getString("city",DEFAULT));

        }
            name_input.addTextChangedListener(new MyTextWatcher(name_input));
            mobileNumber_input.addTextChangedListener(new MyTextWatcher(mobileNumber_input));
            email_input.addTextChangedListener(new MyTextWatcher(email_input));
            address_input.addTextChangedListener(new MyTextWatcher(address_input));
            city_input.addTextChangedListener(new MyTextWatcher(city_input));
            save = (Button) findViewById(R.id.btn_save);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Insert", "Inserting...");
                    Toast.makeText(MyProfile.this, "hello", Toast.LENGTH_SHORT).show();
                    submitForm(sharedPreferences,sharedPreferences1);//check
                }
            });

    }
    private void submitForm(SharedPreferences sharedPreferences,SharedPreferences sharedPreferences1){
        if (!validateName()) {
            return;
        }
        if(!validateMobileNumber()){
            return;
        }
        if (!validateEmail()) {
            return;
        }
        if (!validateCity()){
            return;
        }
        if (!validateAddress()) {
            return;
        }
        String n = name_input.getText().toString().trim();
        String m = mobileNumber_input.getText().toString().trim();
        String e = email_input.getText().toString().trim();
        String a = address_input.getText().toString().trim();
        String c = city_input.getText().toString().trim();
        loginflag = true;
        bundle.putString("name",n);
        bundle.putString("mobile_number",m);
        bundle.putString("email",e);
        bundle.putString("address",a);
        bundle.putString("city",c);
        Log.d("Insert","Inserting...");
        //updateDataInsertedFlagForUser(n,m,e,1);
        addUser(n,m,e,a,c,1); //check
        //Toast.makeText(getApplicationContext(),n,Toast.LENGTH_SHORT).show();
//        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //SharedPreferences.Editor editor1 = sharedPreferences1.edit();
        editor.putString("name",n);
        editor.putString("mobile_no",m);
        editor.putString("email_id",e);
        editor.putString("address",a);
        editor.putString("city",c);
        editor.putInt("flag",flagValue);
//        editor1.putBoolean("login_flag",loginflag);
        editor.apply();
        //editor1.apply();
        Intent intent = new Intent(MyProfile.this,SignUp.class);
        /*TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(MyProfile.this);
        taskStackBuilder.addNextIntentWithParentStack(intent);
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentIntent(pendingIntent);*/
     //   finish();
        startActivity(intent);

    }

    private void addUser(String n, String m, String e, String a, String c, int i) {
        DatabaseHandler dataBaseHandler = new DatabaseHandler(this);
        int x = dataBaseHandler.getMobileNumberCount(m);
        System.out.println("$$$$$$$count"+x);
        if( ((new DatabaseHandler(this)).getEmailIdCount(email_input.getText().toString().trim()) ==0)
                &&
                ((new DatabaseHandler(this)).getMobileNumberCount(mobileNumber_input.getText().toString().trim()) ==0)) {

            dataBaseHandler.addUser(new UserProfile(n, m, e, a, c, 1));//check
            try {
                Toast.makeText(MyProfile.this, "Total count in table" + dataBaseHandler.userCount(), Toast.LENGTH_SHORT).show();
                readData();
                finish();
                dialogflag = true;

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        List<UserProfile> userProfiles = dataBaseHandler.getAllUsers();
        //System.out.println("After Update Return Flag "+ret+"+\n");
        for (UserProfile up : userProfiles) {
            String log = "ID:" + up.getUser_id() + ",Name:" + up.getName() + ",Mobile number:" + up.getMobile_no() + ",Email:" + up.getEmail_id() + ",Data Inserted Flag" + up.getDataInserted();
            Log.d("Entry:", log);
        }

    }


    private boolean validateEmail() {
        String email_id = email_input.getText().toString().trim();
        if (email_id.isEmpty() || !isValidEmail(email_id)) {
            email.setError("Enter Valid Email Address");
            requestFocus(email_input);
            return false;
        }
        else {
            email.setErrorEnabled(false);
        }

        return true;
    }


    private boolean validateName() {
        if (name_input.getText().toString().trim().isEmpty()) {
            name.setError("Enter valid name");
            requestFocus(name_input);
            return false;
        } else {
       /*     Toast.makeText(getApplicationContext(),name_input.getText().toString().trim(),Toast.LENGTH_SHORT).show();
       */     name.setErrorEnabled(false);
        }

        return true;
    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private boolean validateCity() {
        if (city_input.getText().toString().trim().isEmpty()) {
            city.setError("Error");
            requestFocus(city_input);
            return false;
        } else {
         /*   Toast.makeText(getApplicationContext(),city_input.getText().toString().trim(),Toast.LENGTH_SHORT).show();
         */   city.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateMobileNumber(){
        if (mobileNumber_input.getText().toString().trim().isEmpty()||mobileNumber_input.getText().toString().trim().length() < 10){
            mobileNumber.setError("Address field is empty");
            requestFocus(mobileNumber_input);
            return false;
        }
        else {
           /* Toast.makeText(getApplicationContext(),address_input.getText().toString().trim(),Toast.LENGTH_SHORT).show();
           */ address.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateAddress() {
        if (address_input.getText().toString().trim().isEmpty()) {
            address.setError("Error");
            requestFocus(address_input);
            return false;
        } else {
     /*       Toast.makeText(getApplicationContext(),address_input.getText().toString().trim(),Toast.LENGTH_SHORT).show();
     */       address.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    protected void readData() throws IOException, JSONException {
        InputStream inputStream = getAssets().open("shloka_details.json");
        Scanner sc = new Scanner(inputStream);
        StringBuilder builder = new StringBuilder();
        while(sc.hasNextLine()){
            builder.append(sc.nextLine());
        }

        /*if( ((new DatabaseHandler(this)).getEmailIdCount(email_input.getText().toString().trim()) ==1)
                &&
                ((new DatabaseHandler(this)).getMobileNumberCount(mobileNumber_input.getText().toString().trim()) ==1)){*/
            parseJSON(builder.toString());
        //}
    }
    private void parseJSON(String s ) throws JSONException {
        String string1="",string2="",string3="",default_value = "false";
        SharedPreferences sharedPreferences1 = getSharedPreferences("app_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        JSONObject root = new JSONObject(s);
        JSONObject shloka = root.getJSONObject("Geeta_Shlokas");//define array
        JSONArray items = shloka.getJSONArray("shlokas");
        for(int i = 0;i<items.length();i++){
            JSONObject item = items.getJSONObject(i);
//            System.out.print(items.length());
            string1 = item.getString("verse");
            string2 = item.getString("translation");
            string3 = item.getString("purpose");
            editor.putString("shloka_id",String.valueOf(i));
            editor.putString("shloka_verse",string1);
            editor.putString("accessed_flag",default_value);
            editor.apply();
            DataBaseHandlerShloka db = new DataBaseHandlerShloka(this);
            db.addShloka(new Shlokas(string1,string2,string3));
            DataBaseHandlerShloka dataBaseHandlerShloka = new DataBaseHandlerShloka(this);
            List<Shlokas> shlokases = dataBaseHandlerShloka.getAllShlokas();
            for (Shlokas up:shlokases) {
                String log = "ID:" + up.getId() + ",Verse:" + up.getVerse_details() + ",Translation:" + up.getVerse_translation() + ",Purpose:" + up.getVerse_purpose() + ",Chapter_id" + up.getChapter_id() +",Access Flag"+up.getAccessFlag();
                Log.d("Entry:",log);
            }
        }
    }
    private int updateDataInsertedFlagForUser(String n, String m, String e, int i) {
        DatabaseHandler dataBaseHandler = new DatabaseHandler(this);
        int ret = dataBaseHandler.updateDataInsertedFlag(new UserProfile(n,m,e,1));
        List<UserProfile> userProfiles = dataBaseHandler.getAllUsers();
        System.out.println("After Update Return Flag "+ret+"+\n");
        for (UserProfile up:userProfiles) {
            String log = "ID:" + up.getUser_id() + ",Name:" + up.getName() + ",Mobile number:" + up.getMobile_no() + ",Email:" + up.getEmail_id() + ",Data Inserted Flag" + up.getDataInserted();
            Log.d("Entry:",log);
        }
        return ret;

    }
    private class MyTextWatcher implements TextWatcher{
        private View view;

        public MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()){
                case R.id.input_name_wrapper:
                    validateName();
                    break;
                case R.id.input_address_wrapper:
                    validateAddress();
                    break;
                case R.id.input_email_wrapper:
                    validateEmail();
                    break;
                case R.id.input_city_wrapper:
                    validateCity();
                    break;
                case R.id.input_mobile_wrapper:
                    validateMobileNumber();
                    break;
            }
        }
    }
}