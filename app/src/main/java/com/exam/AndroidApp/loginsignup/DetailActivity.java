package com.exam.AndroidApp.loginsignup;

import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.exam.AndroidApp.data.Data;


public class DetailActivity extends AppCompatActivity {

    TextView textName, textSub, salary, qualifications;
    ImageView photoMember;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Data memberData = (Data)getIntent().getExtras().getSerializable("DATA");

        textName = (TextView)findViewById(R.id.title_text);
        textName.setText(memberData.getTitle());

        textSub = (TextView)findViewById(R.id.sub_text);
        textSub.setText(memberData.getLocation());

        salary = (TextView)findViewById(R.id.salary);
        salary.setText(memberData.getSalary());

        qualifications = (TextView)findViewById(R.id.qualifications);
        qualifications.setText(memberData.getQualification());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        NavUtils.navigateUpFromSameTask(this);
    }
}
