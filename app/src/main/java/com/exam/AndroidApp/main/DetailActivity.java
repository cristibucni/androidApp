package com.exam.AndroidApp.main;

import android.os.Bundle;

import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.exam.AndroidApp.data.Data;


public class DetailActivity extends AppCompatActivity {

    TextView textName, textSub, difficulty, description, status;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Data memberData = (Data)getIntent().getExtras().getSerializable("DATA");

        textName = (TextView)findViewById(R.id.title_text);
        textName.setText(memberData.getTaskName());

        textSub = (TextView)findViewById(R.id.sub_text);
        textSub.setText(memberData.getProject());

        difficulty = (TextView)findViewById(R.id.difficulty);
        difficulty.setText(memberData.getDifficulty());

        description = (TextView)findViewById(R.id.description);
        description.setText(memberData.getDescription());

        status = (TextView)findViewById(R.id.status);
        status.setText(memberData.getStatus());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        NavUtils.navigateUpFromSameTask(this);
    }
}
