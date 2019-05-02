package com.application.aayush.geeta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static java.lang.System.out;

/**
 * Created by Aayush on 9/25/2018.
 */

public class Diary1 extends AppCompatActivity implements View.OnClickListener {
    Spinner chapters;
    Button save ,cancel;
    ImageButton back;
    ArrayAdapter arrayAdapter;
    List<Shlokas> shlokases ;
    DataBaseHandlerShloka dataBaseHandlerShloka ;
    String chapterArray[];
    int i = 0,flag = 0 ,n = 0;
    TextView textView;
    EditText note;
    Bundle bundle;
    SharedPreferences sharedPreferences ;
    String name = "",m = "",e = "",a = "",c = "" , default_value = "";
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_diary1);

        chapters = (Spinner)findViewById(R.id.spinner2);

        save = (Button)findViewById(R.id.button13);

        cancel = (Button)findViewById(R.id.button12);

        back = (ImageButton) findViewById(R.id.button14);

        note = (EditText)findViewById(R.id.editText3);

        textView = (TextView)findViewById(R.id.toobar_title) ;
        dataBaseHandlerShloka = new DataBaseHandlerShloka(this);

        shlokases = dataBaseHandlerShloka.getReadableChapters();

        chapterArray = new String[shlokases.size()];

        for (Shlokas up:shlokases) {

            chapterArray[i] = String.valueOf(up.getId());
            i++;
            String log = "ID:" + up.getId();
            Log.d("Entry:",log);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        bundle = new Bundle();
        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name",default_value);
        m = sharedPreferences.getString("mobile_no",default_value);
        e = sharedPreferences.getString("email_id",default_value);
        a = sharedPreferences.getString("address",default_value);
        c = sharedPreferences.getString("city",default_value);

        bundle.putString("name",name);
        bundle.putString("mobile_number",m);
        bundle.putString("email",e);
        bundle.putString("address",a);
        bundle.putString("city",c);
        //specify the layout when the dropdown appears
        arrayAdapter = new ArrayAdapter(Diary1.this,android.R.layout.simple_spinner_item,chapterArray);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chapters.setAdapter(arrayAdapter);
        chapters.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                n = Integer.valueOf(parent.getItemAtPosition(position).toString());
                chapters.setSelection(position);
                flag = 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        save.setOnClickListener(Diary1.this);
        cancel.setOnClickListener(Diary1.this);
        back.setOnClickListener(Diary1.this);
/*
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 1){
                    chapters.setEnabled(false);
                    textView.setTextSize(14);
                    textView.setText(textView.getText().toString()+"  "+n);
                    save.setEnabled(false);
                }


            }
        });
*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button13:onSave(note,String.valueOf(n));
                break;
            case R.id.button14:
                Diary1.super.onBackPressed();
                break;
            case R.id.button12:
                diaryFragment diaryFragment1 = new diaryFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                diaryFragment1.setArguments(bundle);
                /*if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack();}
                */fragmentManager
                        .beginTransaction()

                        .addToBackStack(diaryFragment1.getTag())
                        .replace(R.id.diary1activity,diaryFragment1,diaryFragment1.getTag())

                        .commit();
            default:
                break;
        }
    }
    private void onSave(final EditText editText, String chapter_no){

        if(flag == 1){
            chapters.setEnabled(false);
            textView.setTextSize(14);
            textView.setText(textView.getText().toString()+"  "+chapter_no);
            save.setEnabled(false);
        }


        String data_to_be_saved = editText.getText().toString();
        saveData(data_to_be_saved,chapter_no);
        editText.setText(data_to_be_saved);
        Toast.makeText(this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();

        //finish();
        diaryFragment diaryFragment1 = new diaryFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        diaryFragment1.setArguments(bundle);
        /*if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();}
        */fragmentManager
                .beginTransaction()

                .addToBackStack(diaryFragment1.getTag())
                .replace(R.id.diary1activity,diaryFragment1,diaryFragment1.getTag())

                .commit();
//        startActivity(new Intent(this,diaryFragment.class));
        // diary_no.setText(chapter.getText().toString());*/
    }
    private void saveData(String data_to_be_saved, String chapter) {
        DiaryDatabaseHandler diaryDatabaseHandler = new DiaryDatabaseHandler(this);
        DataBaseHandlerShloka dataBaseHandlerShloka = new DataBaseHandlerShloka(this);
        int chapter_no = Integer.parseInt(chapter);
        diaryDatabaseHandler.addContent(new Diary(chapter_no,data_to_be_saved));
        int v = dataBaseHandlerShloka.addChapter(new Shlokas(chapter_no,chapter_no));
        List<Diary> profileList = diaryDatabaseHandler.getAllUsers();
        for (Diary up:profileList){
            String log = "ID:"+up.getChapter_no()+",Name:"+up.getDiary_details();
            Log.d("Diary Entry :",log);
        }
        out.print("**************"+v);
        //edit.setVisibility(View.VISIBLE);
        //save.setVisibility(View.GONE);

    }

}