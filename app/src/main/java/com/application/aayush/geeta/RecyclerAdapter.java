package com.application.aayush.geeta;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Aayush on 8/27/2018.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>{
    Context context;
    LayoutInflater inflater;
    ArrayList<String> items = new ArrayList<>();
    Typeface customFont ,customFont1,customFont2;

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView chapter,header,header2,header3,header4,textView1,textView2,textView3,diary_no,name,note;
        ImageView back,front;
        Button edit,notify,read;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            back = (ImageView)itemView.findViewById(R.id.imageView18);
            front = (ImageView)itemView.findViewById(R.id.imageView21);
            chapter = (TextView)itemView.findViewById(R.id.textView14);
            diary_no = (TextView)itemView.findViewById(R.id.diary_no);
            header = (TextView)itemView.findViewById(R.id.textView13);
            textView1 = (TextView)itemView.findViewById(R.id.textView20);
            header2 = (TextView)itemView.findViewById(R.id.textView40);
            header3 = (TextView)itemView.findViewById(R.id.textView42);
            header4 = (TextView)itemView.findViewById(R.id.textView44);
            textView2 = (TextView)itemView.findViewById(R.id.textView41);
            textView3 = (TextView)itemView.findViewById(R.id.textView43);
            note = (TextView)itemView.findViewById(R.id.note_text_view);
            edit = (Button) itemView.findViewById(R.id.editbutton);
            notify = (Button)itemView.findViewById(R.id.notification_button2);
            read = (Button)itemView.findViewById(R.id.button20);

        }
    }

    public RecyclerAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);


    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.content_user_menu, parent, false);

        RecyclerViewHolder viewHolder = new RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
