package com.lifeng.layoutmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lifeng.layoutmanager.R;

public class DoItComplete extends AppCompatActivity {

private TextView address;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_it_complete);

        address=(TextView)this.findViewById(R.id.textView15);

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DoItComplete.this,ApplicationLoad.class);
                intent.putExtra("url",address.getText().toString().trim());
                startActivity(intent);
            }
        });

    }
}
