package com.application.aayush.geeta;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import android.os.StrictMode;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.facebook.FacebookSdk.getApplicationContext;
/*import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.sql.DataSource;

import static java.lang.System.out;*/

public class UserMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    public static final String default_value = "12";
    RatingBar rb;
    Session session = null;
    ProgressDialog pdialog = null;
    TextView chapter,header,header2,header3,header4,textView1,textView2,textView3,diary_no,name,note;
    Button edit,notify,dismiss,submit1;
    String uname,umobilenumber,uemail,uaddress,ucity ,verse,translation,purport,title,diary_entry,font_size = "" , default_chapter = "1",formattedDate = "";

    int id = 0,count = 0,chapter_no = 0,chapter_id = 0,flag ,defaultValue = 2,count1 = 0,ret = 0 ,access_flag = 0 ,read_flag = 0 ,readCurrentChapterFlag = 0  ;

    boolean clicked = false,date_flag = false,loginflag = true ,r1Click = false,r2Click = false,r3Click = false,r4Click = false,r5Click = false;

    TextView editText;
    EditText feedBack;
    Button play_music,read;
    ImageView rate1,rate2,rate3,rate4,rate5,back,front;
    Bundle bundle;
    DiaryDatabaseHandler diaryDatabaseHandler;
    SharedPreferences sharedPreferences,sharedPreferences1,sharedPreferences3,sharedPreferences4;
    SharedPreferences.Editor editor1,editor,editor2 ;
    Typeface customFont ,customFont1,customFont2;
    SimpleDateFormat sdf,sdf1 ;
    Date date ;
    AlertDialog.Builder alertDialogBuilder = null;
    View dialogLayout = null;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    GoogleApiClient mGoogleApiClient;
    private GoogleSignInClient mGoogleSignInClient;
    Shlokas shlokas ;
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);

        Display display = getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display. getSize(size);
        int width = size. x;
        int height = size. y;

      /*  Window window = UserMenu.this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(UserMenu.this,R.color.backgroundcolor1));
*/
        sharedPreferences = getSharedPreferences("app_data", Context.MODE_PRIVATE);
        sharedPreferences1 = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        sharedPreferences3 = getSharedPreferences("app_settings", Context.MODE_PRIVATE);
        sharedPreferences4 = getSharedPreferences("shloka_data", Context.MODE_PRIVATE);
        count1= sharedPreferences.getAll().size();
        sdf = new SimpleDateFormat("MM/dd/yyyy",Locale.ENGLISH);
        sdf1 = new SimpleDateFormat("MM/dd/yyyy",Locale.ENGLISH);
        date = Calendar.getInstance().getTime();
        formattedDate = sdf.format(date);
        final DataBaseHandlerShloka db = new DataBaseHandlerShloka(this);
        diaryDatabaseHandler = new DiaryDatabaseHandler(this);
        Drawable drawable= getResources().getDrawable(R.mipmap.menu3);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Drawable new_drawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 70, 70, true));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(new_drawable);
        back = (ImageView)findViewById(R.id.imageView18);
        front = (ImageView)findViewById(R.id.imageView21);
        chapter = (TextView)findViewById(R.id.textView14);
        diary_no = (TextView)findViewById(R.id.diary_no);
        header = (TextView)findViewById(R.id.textView13);
        textView1 = (TextView)findViewById(R.id.textView20);
        header2 = (TextView)findViewById(R.id.textView40);
        header3 = (TextView)findViewById(R.id.textView42);
        header4 = (TextView)findViewById(R.id.textView44);
        textView2 = (TextView)findViewById(R.id.textView41);
        textView3 = (TextView)findViewById(R.id.textView43);
        note = (TextView)findViewById(R.id.note_text_view);
        edit = (Button) findViewById(R.id.editbutton);
        notify = (Button)findViewById(R.id.notification_button2);
        read = (Button)findViewById(R.id.button20);
        //textView1.setText("Hello Aayush");
       /* NestedScrollView nestedScrollView = (NestedScrollView)findViewById(R.id.nestedScrollView);

        for (int i = 0; i < nestedScrollView.getChildCount(); i++){
            CardView card = (CardView) nestedScrollView.getChildAt(i);
            ViewGroup viewGroup = ((ViewGroup)card.getChildAt(0));
            *//*TextView n = (TextView)viewGroup.findViewById(R.id.textView51);*//*
            for(int j=0;j<viewGroup.getChildCount();j++){
                //String getName = viewGroup.getChildAt(2).getId()
                //int id = viewGroup.getChildAt(1);
            }
        }
       */ customFont = Typeface.createFromAsset(getAssets(),"fonts/Lato-Regular.ttf");
        customFont1 = Typeface.createFromAsset(getAssets(),"fonts/Lato-Heavy.ttf");
        customFont2 = Typeface.createFromAsset(getAssets(),"fonts/Lato-Italic.ttf");
        header.setTypeface(customFont1);
        header2.setTypeface(customFont1);
        header3.setTypeface(customFont1);
        header4.setTypeface(customFont);
        textView1.setTypeface(customFont);
        textView2.setTypeface(customFont);
        textView3.setTypeface(customFont);
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(UserMenu.this)
                .enableAutoManage(UserMenu.this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    System.out.println("*&&&&&&&&&&&(()("+firebaseAuth.getCurrentUser());
               /*     Intent intent = new Intent(UserMenu.this, MyProfile.class);
                    startActivity(intent);*/
                }
            }
        };
        if(count1 == 0){
            font_size = "12";
        }
        else
        {
            font_size = sharedPreferences3.getString("font_size",default_value);
        }
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP,Integer.parseInt(font_size));
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP,Integer.parseInt(font_size));
        textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP,Integer.parseInt(font_size));
        note.setTypeface(customFont2);
        flag = sharedPreferences1.getInt("flag",defaultValue);
        count = db.userCount();
        //Toast.makeText(this, "count"+count, Toast.LENGTH_SHORT).show();
        shlokas = db.getsholka(Integer.valueOf(sharedPreferences.getString("current_chapter",default_chapter)));
//        Shlokas shlokas = db.getsholka(Integer.parseInt(sharedPreferences4.getString("current_chapter",default_chapter)));
        id = shlokas.getId();
        title = "Chapter "+String.valueOf(id);
        verse = shlokas.getVerse_details();
        translation = shlokas.getVerse_translation();
        purport = shlokas .getVerse_purpose();
        chapter_id = shlokas.getChapter_id();
        access_flag = shlokas.getAccessFlag();
        read_flag = shlokas.getReadFlag();
        chapter.setText(String.valueOf(id));
        textView1.setText(verse);
        textView2.setText(translation);
        textView3.setText(purport);
        int r = db.getChapterId(Integer.parseInt(chapter.getText().toString()));
        chapter_no = Integer.parseInt(chapter.getText().toString());
        edit.setVisibility(View.GONE);
        if(flag == 0){
            editor = sharedPreferences1.edit();
            editor2 = sharedPreferences.edit();
            editor2.putBoolean("login_flag",loginflag);
            editor2.apply();
            editor.putInt("flag",1);
            editor.apply();
            alertDialogBuilder = new AlertDialog.Builder(UserMenu.this);
            dialogLayout = getLayoutInflater().inflate(R.layout.activity_why_geeta,null);
            dismiss = (Button)dialogLayout.findViewById(R.id.button7);
            dismiss = (Button)dialogLayout.findViewById(R.id.button7);
            alertDialogBuilder.setView(dialogLayout);
            final AlertDialog alertDialog1 = alertDialogBuilder.create();
            WindowManager.LayoutParams wmlp = alertDialog1.getWindow().getAttributes();
            wmlp.gravity = Gravity.TOP ;
            /*wmlp.x = 6;   //x position
            wmlp.y = 950;*/

            wmlp.x = width;   //x position
            wmlp.y = height;

            alertDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog1.setCancelable(false);
            dismiss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    alertDialog1.dismiss();
                }
            });
            alertDialog1.show();
        }

        List<Shlokas> shlokases1 = db.getAllShlokas();
        for (Shlokas up:shlokases1) {
            String log = "ID: " + up.getId() + ",Verse: " + up.getVerse_details() + ",Translation: " + up.getVerse_translation() + ",Purpose: " + up.getVerse_purpose() + ",Chapter_id: " + up.getChapter_id() +",Access Flag: "+up.getAccessFlag();
            Log.d("Entry:",log);
        }
        if (read_flag == 1){
            read.setBackgroundColor(getResources().getColor(R.color.textcolor));
            read.setEnabled(false);

        }
        else {
            read.setEnabled(true);
            read.setBackgroundColor(getResources().getColor(R.color.backgroundcolor1));

        }
        if(chapter_id == 0 && access_flag == 1){
            note.setText("What do you learn from today's Chapter...");
            note.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UserMenu.this,UserNote.class);
                    intent.putExtra("chapter_number",chapter.getText().toString());
                    startActivity(intent);
                }
            });
        }
        else {

//            read.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);
            //Diary diary = diaryDatabaseHandler.getContent(Integer.parseInt(chapter.getText().toString()));
            Diary diary = diaryDatabaseHandler.getContent(chapter_id);
            final String data = diary.getDiary_details();
            note.setText(data);
            //editText.setInputType(InputType.TYPE_NULL);
            diary_no.setText(chapter.getText().toString());
        }
        read.setOnClickListener(UserMenu.this);
        back.setOnClickListener(UserMenu.this);
        front.setOnClickListener(UserMenu.this);
        edit.setOnClickListener(UserMenu.this);
        play_music = (Button)findViewById(R.id.imageButton2);

        // final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.bgita);
        //drawerLayout = (DrawerLayout)view.findViewById(R.id.drawer_layout);


        bundle = getIntent().getExtras();
        uname = bundle.getString("user_name");
        umobilenumber = bundle.getString("user_mobilenumber");
        uemail = bundle.getString("user_email");
        uaddress = bundle.getString("user_address");
        ucity = bundle.getString("user_city");
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserMenu.this,NotificationActivity.class);
                intent.putExtra("user_name",uname);
                intent.putExtra("user_mobilenumber",umobilenumber);
                intent.putExtra("user_email",uemail);
                intent.putExtra("user_address",uaddress);
                intent.putExtra("user_city",ucity);
                startActivity(intent);
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("EveryDay Geeta");
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(new_drawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        name = (TextView)headerView.findViewById(R.id.user_name1);
        name.setText(uname);
    }

    private void setValues(int chapter_no, String verse, String translation, String purport) {
        chapter.setText(String.valueOf(chapter_no));
        textView1.setText(verse);
        textView2.setText(translation);
        textView3.setText(purport);
    }

    private boolean compareDate(int id,int chapter_id,int read_flag)  {
        String default_date = " ";
        sdf = new SimpleDateFormat("MM/dd/yyyy",Locale.ENGLISH);
        Date c = Calendar.getInstance().getTime();
        String formattedDate = sdf.format(c);
        SharedPreferences sharedPreferences4 = getSharedPreferences("app_data", Context.MODE_PRIVATE);
        String date = sharedPreferences4.getString("date",default_date);
        //String date = "04/01/2018";
        Date date1 = null , date2 = null;
        try {
            date1 = sdf.parse(date);
            date2 = sdf.parse(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date2.after(date1) && read_flag == 1)
        {
            int ret = updateAccessFlagForShloka(id,chapter_id);
            Toast.makeText(this,"Data going in Shared Preference"+chapter.getText().toString() , Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = sharedPreferences4.edit();
            //editor.putString("current_chapter",chapter.getText().toString());
            editor.putString("current_chapter",String.valueOf(id));
            editor.putString("date",formattedDate);
            editor.apply();
            read.setEnabled(true);
            read.setBackgroundColor(getResources().getColor(R.color.backgroundcolor1));
            Toast.makeText(UserMenu.this,"Date2 greater than Date1",Toast.LENGTH_SHORT).show();
            Toast.makeText(UserMenu.this,"Date in SharedPreferences"+date,Toast.LENGTH_SHORT).show();
            Toast.makeText(UserMenu.this,"Current date"+formattedDate,Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(date2.before(date1))
        {
            Toast.makeText(UserMenu.this,"Date2 less than date1",Toast.LENGTH_SHORT).show();
            Toast.makeText(UserMenu.this,"Date in SharedPreferences"+date,Toast.LENGTH_SHORT).show();
            Toast.makeText(UserMenu.this,"Current date"+formattedDate,Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(date2.equals(date1)){
            Toast.makeText(UserMenu.this,"Both date are equals",Toast.LENGTH_SHORT).show();
            return false;

        }
        return false;
    }
    private int updateAccessFlagForShloka(int id, int chapter_id) {
        DataBaseHandlerShloka dataBaseHandlerShloka = new DataBaseHandlerShloka(this);

        int ret = dataBaseHandlerShloka.updateAccessFlag(new Shlokas(id,chapter_id,1));
        List<Shlokas> shlokases = dataBaseHandlerShloka.getAllShlokas();
        for (Shlokas up:shlokases) {
            String log = "ID:" + up.getId() + ",Verse:" + up.getVerse_details() + ",Translation:" + up.getVerse_translation() + ",Purpose:" + up.getVerse_purpose() + ",Chapter_id" + up.getChapter_id() +",Access Flag"+up.getAccessFlag();
            Log.d("Entry:",log);
        }

        if (ret == 1){
            Toast.makeText(UserMenu.this,"Success",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(UserMenu.this,"UnSuccessfull",Toast.LENGTH_SHORT).show();
        }
        return ret;
    }
  /*  private void saveData(String data_to_be_saved, TextView chapter) {
        DiaryDatabaseHandler diaryDatabaseHandler = new DiaryDatabaseHandler(this);
        DataBaseHandlerShloka dataBaseHandlerShloka = new DataBaseHandlerShloka(this);
        int chapter_no = Integer.parseInt(chapter.getText().toString());
        diaryDatabaseHandler.addContent(new Diary(chapter_no,data_to_be_saved));
        //dataBaseHandlerShloka.addChapter(new Shlokas(chapter_no,1));
        int v = dataBaseHandlerShloka.addChapter(new Shlokas(chapter_no,chapter_no));
        List<Diary> profileList = diaryDatabaseHandler.getAllUsers();
        for (Diary up:profileList){
            String log = "ID:"+up.getChapter_no()+",Name:"+up.getDiary_details();
            Log.d("Diary Entry :",log);
        }
        out.print("**************"+v);
        edit.setVisibility(View.VISIBLE);
        read.setVisibility(View.GONE);

    }*/

    private void onRead(TextView chapter, DataBaseHandlerShloka db) {
        int chapter_no = Integer.parseInt(chapter.getText().toString());
        int chapter_id = db.getChapterId(chapter_no);
        int access_flag = db.getaccessFlag(chapter_no);
        int v = db.updateReadFlag(new Shlokas(chapter_no,chapter_id,access_flag,1));
        read.setBackgroundColor(getResources().getColor(R.color.textcolor));
        Toast.makeText(this, "Read flag set to"+db.getReadFlag(chapter_no), Toast.LENGTH_SHORT).show();
        read.setEnabled(false);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return false;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_diary) {
            diaryFragment diaryFragment1 = new diaryFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            diaryFragment1.setArguments(bundle);
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStack();}
            fragmentManager
                    .beginTransaction()

                    .addToBackStack(diaryFragment1.getTag())
                    .replace(R.id.content_user_menu,diaryFragment1,diaryFragment1.getTag())

                    .commit();

        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this,SettingsActivity.class);
            intent.putExtra("user_name",uname);
            intent.putExtra("user_mobilenumber",umobilenumber);
            intent.putExtra("user_email",uemail);
            intent.putExtra("user_address",uaddress);
            intent.putExtra("user_city",ucity);
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(UserMenu.this);
            taskStackBuilder.addNextIntentWithParentStack(intent);
            PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setContentIntent(pendingIntent);

            startActivity(intent);


        } else if (id == R.id.nav_userguide) {

        } else if (id == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
            sendIntent.setType("text/plain");
//                sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);
        } else if (id == R.id.nav_rate) {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UserMenu.this);
            final View dialogLayout = getLayoutInflater().inflate(R.layout.activity_rate_us1,null);
            Button button1 = (Button)dialogLayout.findViewById(R.id.button);
            Button button2 = (Button)dialogLayout.findViewById(R.id.button3);
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(UserMenu.this);
                    View dialogLayout1 = getLayoutInflater().inflate(R.layout.activity_rating,null);
                  /*  rb=(RatingBar)dialogLayout1.findViewById(R.id.ratingBar1);
                    rb.setNumStars(5);
                    rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){

                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float rating,
                                                    boolean fromUser) {
                            // TODO Auto-generated method stub
                            Toast.makeText(getApplicationContext(),Float.toString(rating),Toast.LENGTH_LONG).show();

                        }

                    });*/
                    rate1 = (ImageView) dialogLayout1.findViewById(R.id.rating1);
                    rate2 = (ImageView)dialogLayout1.findViewById(R.id.rating2);
                    rate3 = (ImageView)dialogLayout1.findViewById(R.id.rating3);
                    rate4 = (ImageView)dialogLayout1.findViewById(R.id.rating4);
                    rate5 = (ImageView)dialogLayout1.findViewById(R.id.rating5);
                    submit1 = (Button)dialogLayout1.findViewById(R.id.button10);
                    feedBack = (EditText)dialogLayout1.findViewById(R.id.feedback);
                    feedBack.setOnClickListener(this);
                    rate1.setOnClickListener(this);
                    rate2.setOnClickListener(this);
                    rate3.setOnClickListener(this);
                    rate4.setOnClickListener(this);
                    rate5.setOnClickListener(this);
                    rate1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rate1.setImageDrawable(null);
                            rate1.setImageDrawable(getResources().getDrawable(R.mipmap.chakra_1));

                            //rate1.setColorFilter(R.color.chakra_clicked);
                            submit1.setVisibility(View.VISIBLE);
                            feedBack.setVisibility(View.VISIBLE);
                            addRating(1);
                           // getRating();
                            clicked = true;
                        }
                    });
                    rate1.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            rate2.setImageDrawable(null);
                            rate3.setImageDrawable(null);
                            rate4.setImageDrawable(null);
                            rate5.setImageDrawable(null);
                            rate2.setImageDrawable(getResources().getDrawable(R.mipmap.chakra_2));
                            rate3.setImageDrawable(getResources().getDrawable(R.mipmap.chakra_2));
                            rate4.setImageDrawable(getResources().getDrawable(R.mipmap.chakra_2));
                            rate5.setImageDrawable(getResources().getDrawable(R.mipmap.chakra_2));

                            addRating(1);
                            return false;
                        }
                    });
                    rate2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rate1.setImageDrawable(null);
                            rate1.setImageDrawable(getResources().getDrawable(R.mipmap.chakra_1));
                            rate2.setImageDrawable(null);
                            rate2.setImageDrawable(getResources().getDrawable(R.mipmap.chakra_1));
                            addRating(2);
                            submit1.setVisibility(View.VISIBLE);
                            feedBack.setVisibility(View.VISIBLE);


                        }
                    });
                    rate2.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            rate3.setImageDrawable(null);
                            rate3.setImageDrawable(getResources().getDrawable(R.mipmap.chakra_2));
                            rate4.setImageDrawable(null);
                            rate4.setImageDrawable(getResources().getDrawable(R.mipmap.chakra_2));
                            rate5.setImageDrawable(null);
                            rate5.setImageDrawable(getResources().getDrawable(R.mipmap.chakra_2));

                            addRating(2);
                            return false;
                        }
                    });

                    rate3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rate1.setImageDrawable(null);
                            rate1.setImageDrawable(getResources().getDrawable(R.mipmap.chakra_1));
                            rate2.setImageDrawable(null);
                            rate2.setImageDrawable(getResources().getDrawable(R.mipmap.chakra_1));
                            rate3.setImageDrawable(null);
                            rate3.setImageDrawable(getResources().getDrawable(R.mipmap.chakra_1));
                            addRating(3);
                            submit1.setVisibility(View.VISIBLE);
                            feedBack.setVisibility(View.VISIBLE);

                        }
                    });
                    rate3.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
  //                          rate4.setColorFilter(null);
                            rate4.setImageDrawable(null);
                            rate4.setImageDrawable(getResources().getDrawable(R.mipmap.chakra_2));
                            rate5.setImageDrawable(null);
                            rate5.setImageDrawable(getResources().getDrawable(R.mipmap.chakra_2));

                            addRating(3);
                            return false;
                        }
                    });

                    rate4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rate1.setImageDrawable(null);
                            rate1.setImageDrawable(getResources().getDrawable(R.mipmap.chakra_1));
                            rate2.setImageDrawable(null);
                            rate2.setImageDrawable(getResources().getDrawable(R.mipmap.chakra_1));
                            rate3.setImageDrawable(null);
                            rate3.setImageDrawable(getResources().getDrawable(R.mipmap.chakra_1));
                            rate4.setImageDrawable(null);
                            rate4.setImageDrawable(getResources().getDrawable(R.mipmap.chakra_1));
                            addRating(4);
                            submit1.setVisibility(View.VISIBLE);
                            feedBack.setVisibility(View.VISIBLE);
                        }
                    });
                    rate4.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
    //                        rate5.setColorFilter(null);
                            rate5.setImageDrawable(null);

                            rate5.setImageDrawable(getResources().getDrawable(R.mipmap.chakra_2));
                            addRating(4);
                          //  getRating();
                            //rate5.setColorFilter(R.color.test);
                            return false;
                        }
                    });
                    rate5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rate1.setImageDrawable(null);
                            rate1.setImageDrawable(getResources().getDrawable(R.mipmap.chakra_1));
                            rate2.setImageDrawable(null);
                            rate2.setImageDrawable(getResources().getDrawable(R.mipmap.chakra_1));
                            rate3.setImageDrawable(null);
                            rate3.setImageDrawable(getResources().getDrawable(R.mipmap.chakra_1));
                            rate4.setImageDrawable(null);
                            rate4.setImageDrawable(getResources().getDrawable(R.mipmap.chakra_1));
                            rate5.setImageDrawable(null);
                            rate5.setImageDrawable(getResources().getDrawable(R.mipmap.chakra_1));
                            addRating(5);
                            submit1.setVisibility(View.VISIBLE);
                            feedBack.setVisibility(View.VISIBLE);

                        }
                    });
                    alertDialogBuilder1.setView(dialogLayout1);
                    AlertDialog alertDialog1 = alertDialogBuilder1.create();
                    alertDialog1.show();
                }
            });
            alertDialogBuilder.setView(dialogLayout);
            final AlertDialog alertDialog1 = alertDialogBuilder.create();
            alertDialog1.show();
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog1.cancel();
                }
            });
            /*submit1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //alertDialog1.dismiss();
*//*
                    AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(UserMenu.this);
                    View dialogLayout1 = getLayoutInflater().inflate(R.layout.activity_rating,null);
*//*

                    sendEmail();
                }
            });*/


        } else if (id == R.id.nav_terms) {
            TermsConditionsFragment termsConditionsFragment = new TermsConditionsFragment();
            termsConditionsFragment.setArguments(bundle);
            FragmentManager fragmentManager1 = getSupportFragmentManager();
            if (fragmentManager1.getBackStackEntryCount() > 0) {
                fragmentManager1.popBackStack();}

            fragmentManager1

                    .beginTransaction()
                    .addToBackStack(termsConditionsFragment.getTag())

                    .replace(R.id.content_user_menu,termsConditionsFragment,termsConditionsFragment.getTag())
                    .commit();
        }
        else if (id == R.id.nav_reminder) {
            Intent intent = new Intent(this,SetReminder.class);
            intent.putExtra("user_name",uname);
            intent.putExtra("user_mobilenumber",umobilenumber);
            intent.putExtra("user_email",uemail);
            intent.putExtra("user_address",uaddress);
            intent.putExtra("user_city",ucity);
// Use TaskStackBuilder to build the back stack and get the PendingIntent
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(UserMenu.this);
            // add all of DetailsActivity's parents to the stack,
            // followed by DetailsActivity itself

            taskStackBuilder.addNextIntentWithParentStack(intent);
            PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setContentIntent(pendingIntent);
            startActivity(intent);
        }
        else if (id == R.id.nav_log_out) {
            SharedPreferences sharedPreferences = getSharedPreferences("app_data", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("login_flag",false);
            editor.apply();
            FacebookSdk.sdkInitialize(this.getApplicationContext());
            disconnectFromFacebook();
            signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private int getRating() {
        int defaultValue1 = 0;
        sharedPreferences1 = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        int ret = sharedPreferences1.getInt("rating",defaultValue1);
//        Toast.makeText(this,String.valueOf(ret), Toast.LENGTH_SHORT).show();
        //SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.getInt("rating",i);
        return ret;

    }

    private void addRating(int i) {
        sharedPreferences1 = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putInt("rating",i);
        editor.apply();
    }

    private void signOut() {
        /*mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        //updateUI(null);
                        // [END_EXCLUDE]
                    }
                });
*/


        // Firebase sign out
        mAuth.signOut();
        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback( new ResultCallback<Status>() {
            @Override public void onResult(@NonNull Status status) {
                //put something you want to happen here eg.
                startActivity(new Intent(UserMenu.this, MyProfile.class)); } });

    }

    private void disconnectFromFacebook() {
        if (AccessToken.getCurrentAccessToken() == null) {
            return ;
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();
                Toast.makeText(UserMenu.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UserMenu.this,MyProfile.class));

            }
        }).executeAsync();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //chapter_no = Integer.valueOf(sharedPreferences.getString("current_chapter",default_chapter));
        //Toast.makeText(this, "Current chapter"+sharedPreferences.getString("current_chapter",default_chapter), Toast.LENGTH_SHORT).show();
        if(count1 == 0){
            font_size = "12";
        }
        else
        {
            font_size = sharedPreferences3.getString("font_size",default_value);
        }
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP,Integer.parseInt(font_size));
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP,Integer.parseInt(font_size));
        textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP,Integer.parseInt(font_size));

        Log.d("msg","on resume");

    }
    public void onClick(View v){
        DataBaseHandlerShloka db = new DataBaseHandlerShloka(this);
        switch (v.getId()){
            case R.id.button20:
                onRead(chapter,db);
                break;
            case R.id.imageView18:
                onBack(db);
                break;
            case R.id.imageView21:
                onFront(db);
                break;
            case R.id.editbutton:
                onEdit();
                break;
            case R.id.feedback:
                feedBack.setText("");
                break;
/*
            case R.id.button10:sendEmail();
                break;
*/
            default:break;
        }
    }

    private void sendEmail() {
        if(isOnline()){
            final String username = "bhagwadgeeta12345@gmail.com";
            final String password = "bh@gwadGeeta54321";
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");


            session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
//            pdialog = ProgressDialog.show(UserMenu.this, "", "Sending Mail...", true);

            RetreiveFeedTask task = new RetreiveFeedTask();
            task.execute();

        }
/*        Log.i("Send email", "");
        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();

        String[] TO = {"aayush63kumar@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        String msg = "Rating recevied for the app is :"+ String.valueOf(getRating());
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Apps Rating");
        emailIntent.putExtra(Intent.EXTRA_TEXT, msg);
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
            Log.i("Finished sending ", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();

        }*/

    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private void onEdit() {
        Chapter chapter1 = new Chapter();
        FragmentManager fragmentManager = getSupportFragmentManager();
        bundle.putString("chapter_no",chapter.getText().toString());
        bundle.putString("diary_data",note.getText().toString());
        chapter1.setArguments(bundle);
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();}
        fragmentManager
                .beginTransaction()
                .addToBackStack(chapter1.getTag())
                .replace(R.id.content_user_menu,chapter1,chapter1.getTag())
                .commit();
    }

    private void onFront(DataBaseHandlerShloka db) {
        chapter_no = Integer.parseInt(chapter.getText().toString()) + 1;
        Shlokas shlokas1 = db.getsholka(Integer.parseInt(chapter.getText().toString()));
        read_flag = shlokas1.getReadFlag();
        //chapter_no = Integer.valueOf(sharedPreferences.getString("current_chapter",default_chapter)) + 1;
        Toast.makeText(UserMenu.this,"Total number of shlokas" +String.valueOf(db.userCount())+"previous chapter read flag=+"+read_flag, Toast.LENGTH_SHORT).show();
        if (chapter_no > db.userCount()){
            chapter_no = 1;
            Shlokas shlokas = db.getsholka(chapter_no);
            id = shlokas.getId();
            chapter_id = db.getChapterId(chapter_no);
            date_flag = compareDate(id,chapter_id,read_flag);//added the parameters
            verse = shlokas.getVerse_details();
            translation = shlokas.getVerse_translation();
            purport = shlokas .getVerse_purpose();
            access_flag = db.getaccessFlag(chapter_no);

            //read_flag = db.getReadFlag(chapter_no);
            read.setBackgroundColor(getResources().getColor(R.color.textcolor));
            read.setEnabled(false);
            Toast.makeText(UserMenu.this,String.valueOf(access_flag),Toast.LENGTH_SHORT).show();
            if (chapter_id == 0 && access_flag == 1 /*&& read_flag == 1*/){
                note.setText("What do you learn from today's Chapter...");
                edit.setVisibility(View.GONE);
                note.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(UserMenu.this,UserNote.class);
                        intent.putExtra("chapter_number",chapter.getText().toString());
                        startActivity(intent);
                    }
                });
            }
            else if(chapter_id != 0 && access_flag == 1 /*&& read_flag == 1*/){
                Diary diary = diaryDatabaseHandler.getContent(chapter_no);
                diary_entry = diary.getDiary_details();
                editText.setText(diary_entry);
                //read.setVisibility(View.GONE);
                edit.setVisibility(View.VISIBLE);
                //                  Toast.makeText(UserMenu.this,"Inside else of Front button",Toast.LENGTH_SHORT).show();
            }
            setValues(chapter_no,verse,translation,purport);

        }
        else {
//                    chapter_no = chapter_no + 1;
            Shlokas shlokas = db.getsholka(chapter_no);
            id = shlokas.getId();
            chapter_id = db.getChapterId(chapter_no);
            date_flag = compareDate(id,chapter_id,read_flag);//added the parameters
            verse = shlokas.getVerse_details();
            translation = shlokas.getVerse_translation();
            purport = shlokas.getVerse_purpose();
            access_flag = db.getaccessFlag(chapter_no);
            readCurrentChapterFlag = db.getReadFlag(chapter_no-1);
            Toast.makeText(UserMenu.this,"Read Flag "+readCurrentChapterFlag+chapter_no,Toast.LENGTH_SHORT).show();

            /*if (readCurrentChapterFlag == 1){
                read.setBackgroundColor(getResources().getColor(R.color.textcolor));
                read.setEnabled(false);

            }
            else if (readCurrentChapterFlag == 0){
                read.setEnabled(true);
                read.setBackgroundColor(getResources().getColor(R.color.backgroundcolor1));

            }*/
//            Toast.makeText(UserMenu.this,"Access Flag "+access_flag,Toast.LENGTH_SHORT).show();
            //          Toast.makeText(UserMenu.this,"Chapter id "+chapter_id,Toast.LENGTH_SHORT).show();
            read.setBackgroundColor(getResources().getColor(R.color.textcolor));
            read.setEnabled(false);
            //read.setEnabled(true);
            //read.setBackgroundColor(getResources().getColor(R.color.backgroundcolor1));
            if (chapter_id == 0 && access_flag == 1 /*&& read_flag == 1*/ ){
                note.setText("What do you learn from today's Chapter...");
                edit.setVisibility(View.GONE);
                setValues(chapter_no,verse,translation,purport);
                read.setEnabled(true);
                read.setBackgroundColor(getResources().getColor(R.color.backgroundcolor1));

                note.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(UserMenu.this,UserNote.class);
                        intent.putExtra("chapter_number",chapter.getText().toString());
                        startActivity(intent);
                        Toast.makeText(UserMenu.this,"chapter_id == 0 && access_flag && date_flag",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else if ( chapter_id!= 0 && access_flag == 1 ){
                setValues(chapter_no,verse,translation,purport);
                Diary diary = diaryDatabaseHandler.getContent(chapter_no);
                diary_entry = diary.getDiary_details();
                note.setText(diary_entry);

                //read.setVisibility(View.GONE);
                edit.setVisibility(View.VISIBLE);
                //note.setInputType(InputType.TYPE_NULL);
                Toast.makeText(UserMenu.this,"chapter_id!= 0 && access_flag && date_flag",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void onBack(DataBaseHandlerShloka db) {

        chapter_no = Integer.parseInt(chapter.getText().toString()) - 1;
        read.setBackgroundColor(getResources().getColor(R.color.textcolor));
        read.setEnabled(false);
        if(chapter_no == 0){
            chapter_no = db.userCount();
            Shlokas shlokas = db.getsholka(chapter_no);
            id = shlokas.getId();
            verse = shlokas.getVerse_details();
            translation = shlokas.getVerse_translation();
            purport = shlokas .getVerse_purpose();
            chapter_id = db.getChapterId(chapter_no);
            access_flag = db.getaccessFlag(chapter_no);
            read_flag = db.getReadFlag(chapter_no);
//                    Toast.makeText(UserMenu.this,"Chapter_id = "+chapter_id,Toast.LENGTH_SHORT).show();
            if (chapter_id == 0 && access_flag == 1){
                note.setText("What do you learn from today's Chapter...");
                edit.setVisibility(View.GONE);
                note.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(UserMenu.this,UserNote.class);
                        intent.putExtra("chapter_number",chapter.getText().toString());
                        startActivity(intent);
                    }
                });
                setValues(chapter_no,verse,translation,purport);
            }
            else if (chapter_id != 0 && access_flag == 1){
                Diary diary = diaryDatabaseHandler.getContent(chapter_no);
                diary_entry = diary.getDiary_details();
                //read.setVisibility(View.GONE);
                edit.setVisibility(View.VISIBLE);
                //editText.setInputType(InputType.TYPE_NULL);
                note.setText(diary_entry);

                setValues(chapter_no,verse,translation,purport);
            }
        }
        else {
            Toast.makeText(UserMenu.this,"test"+ String.valueOf(chapter_no), Toast.LENGTH_SHORT).show();

            //chapter_no = chapter_no - 1;
            Shlokas shlokas = db.getsholka(chapter_no);
            id = shlokas.getId();
            verse = shlokas.getVerse_details();
            translation = shlokas.getVerse_translation();
            purport = shlokas.getVerse_purpose();
            chapter_id = db.getChapterId(chapter_no);
            access_flag = db.getaccessFlag(chapter_no);
//                    Toast.makeText(UserMenu.this,"Chapter_id = "+chapter_id,Toast.LENGTH_SHORT).show();
            if (chapter_id == 0 && access_flag == 1){
                note.setText("What do you learn from today's Chapter...");
                edit.setVisibility(View.GONE);
                Toast.makeText(UserMenu.this,"first if",Toast.LENGTH_SHORT).show();
                note.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(UserMenu.this,UserNote.class);
                        intent.putExtra("chapter_number",chapter.getText().toString());
                        startActivity(intent);
                        //editText.setMaxLines(10);
                    }
                });
                setValues(chapter_no,verse,translation,purport);
            }
            else  if (chapter_id != 0 && access_flag == 1){
                Toast.makeText(UserMenu.this,"second if",Toast.LENGTH_SHORT).show();
                Diary diary = diaryDatabaseHandler.getContent(chapter_no);
                diary_entry = diary.getDiary_details();
                // read.setVisibility(View.GONE);
                edit.setVisibility(View.VISIBLE);
                //editText.setInputType(InputType.TYPE_NULL);
                note.setText(diary_entry);
                setValues(chapter_no,verse,translation,purport);

            }
        }
    }
    public class RetreiveFeedTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String textMessage = "Rating Received: "+String.valueOf(getRating());

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("from-email@gmail.com"));
                message.setRecipients(javax.mail.Message.RecipientType.TO,
                        InternetAddress.parse("to-email@gmail.com"));
                message.setSubject("Testing Subject");
                message.setText("Dear Mail Crawler,"
                        + "\n\n No spam to my email, please!"+":"+textMessage);

                MimeBodyPart messageBodyPart = new MimeBodyPart();

                Multipart multipart = new MimeMultipart();

                messageBodyPart = new MimeBodyPart();
                String file = "path of file to be attached";
                String fileName = "attachmentName";
                DataSource source = new FileDataSource(file);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(fileName);
                multipart.addBodyPart(messageBodyPart);
                message.setContent(multipart, "text/html; charset=utf-8");
                Transport.send(message);

                System.out.println("Done");

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
/*
        @Override
        protected void onPostExecute(String result) {
            pdialog.dismiss();
            */
/*reciep.setText("");
            msg.setText("");
            sub.setText("");*//*

            Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();
        }
*/
    }


}