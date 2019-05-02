package com.application.aayush.geeta;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static java.lang.System.out;

/**
 * Created by Aayush on 6/12/2018.
 */

public class UserNote extends AppCompatActivity implements View.OnClickListener {
    EditText note;
    Button save;
    ImageButton back;
    Bundle bundle;
    String chapter_no = "";
    String defaultValue = "";
    SharedPreferences sharedPreferences ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        bundle = getIntent().getExtras();
        chapter_no = bundle.getString("chapter_number");
        note = (EditText)findViewById(R.id.editText3);
        save = (Button)findViewById(R.id.button10);
        back = (ImageButton)findViewById(R.id.button41);
        note.setText("What do you learn from today's Chapter...");
        back.setOnClickListener(UserNote.this);
        save.setOnClickListener(UserNote.this);
        note.setOnClickListener(UserNote.this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button10:onSave(note,chapter_no);
                break;
            case R.id.button41:
                UserNote.super.onBackPressed();
                break;
            case R.id.editText3:onNoteClick();
                break;
            default:
                break;
        }
    }

    private void onNoteClick() {
        note.setText("");
    }

    private void onSave(final EditText editText,String chapter_no){
        String data_to_be_saved = editText.getText().toString();
        saveData(data_to_be_saved,chapter_no);
        editText.setText(data_to_be_saved);
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
        save.setVisibility(View.GONE);
        Toast.makeText(this, "Your data in the diary is saved successfully", Toast.LENGTH_SHORT).show();
        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);

        String n = sharedPreferences.getString("name",defaultValue);
        String m = sharedPreferences.getString("mobile_no",defaultValue);
        String e = sharedPreferences.getString("email_id",defaultValue);
        String a = sharedPreferences.getString("address",defaultValue);
        String c = sharedPreferences.getString("city",defaultValue);


        Intent intent = new Intent(this,UserMenu.class);
        intent.putExtra("user_name",n);
        intent.putExtra("user_mobilenumber",m);
        intent.putExtra("user_email",e);
        intent.putExtra("user_address",a);
        intent.putExtra("user_city",c);
// Use TaskStackBuilder to build the back stack and get the PendingIntent
      /*  TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(UserNote.this);
        // add all of DetailsActivity's parents to the stack,
        // followed by DetailsActivity itself

        taskStackBuilder.addNextIntentWithParentStack(intent);
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentIntent(pendingIntent);
      */  startActivity(intent);


    }
}