package com.lifeng.layoutmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.lifeng.layoutmanager.R;

import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {

    private Button enterapp,doitapp,addapp,deleteapp,sousuo;
    private String userid;
    private CheckBox skip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        enterapp=(Button)this.findViewById(R.id.button8);
        doitapp=(Button)this.findViewById(R.id.button9);
        addapp=(Button)this.findViewById(R.id.button10);
        deleteapp=(Button)this.findViewById(R.id.button11);
        sousuo=(Button)this.findViewById(R.id.button20);
        skip=(CheckBox)this.findViewById(R.id.checkboxskip);

        Intent intent=getIntent();
        userid= intent.getStringExtra("userid");

        enterapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this,FlowActivity.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                finish();
            }
        });

        doitapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this,DoItApp.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                //finish();
            }
        });

        addapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this,AddApp.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
               // finish();
            }
        });

        deleteapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this,DeleteApp.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                //finish();
            }
        });

        sousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this,SouSuo.class);
                startActivity(intent);
                //finish();
            }
        });

        skip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    saveskipInfo(Home.this,"1");

                }else {
                    saveskipInfo(Home.this,"0");
                }
            }
        });




        SharedPreferences sharedPre=getSharedPreferences("config", MODE_PRIVATE);
        String skipflag=sharedPre.getString("flag", "");

        if (skipflag.trim().equals("1")){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Intent intentent=new Intent(Home.this,FlowActivity.class);
                    intentent.putExtra("userid",userid);
                    startActivity(intentent);
                    finish();

                }
            }).start();




        }




    }





    /**
     * 使用SharedPreferences保存用户跳过信息
     * @param context
     * @param flag
     */
    public static void saveskipInfo(Context context, String flag){
        //获取SharedPreferences对象
        SharedPreferences sharedPre=context.getSharedPreferences("config", context.MODE_PRIVATE);
        //获取Editor对象
        SharedPreferences.Editor editor=sharedPre.edit();
        //设置参数
        editor.putString("flag", flag);
        //提交
        editor.commit();
    }



}
