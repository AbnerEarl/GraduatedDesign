package com.lifeng.layoutmanager.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lifeng.layoutmanager.R;
import com.lifeng.layoutmanager.webservice.RequestFunc;
import com.lifeng.layoutmanager.webservice.WebService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button shiyong,denglu;
    Button fogget,reegiester;
    File path;
    String path1;
    TextView tishi;
    EditText username,userpass;
    ProgressBar denglujiazai;

    WebService http = new WebService(new RequestFunc() {

        @Override
        public void Func() {
            if (http.Result != null) {
                if (http.Result.equals("true")) { // + http.Result
                    denglujiazai.setVisibility(denglujiazai.INVISIBLE);
                    denglu.setVisibility(denglu.VISIBLE);
                    reegiester.setVisibility(reegiester.VISIBLE);
                    fogget.setVisibility(fogget.VISIBLE);
                    shiyong.setVisibility(shiyong.VISIBLE);
                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    saveLoginInfo(MainActivity.this,username.getText().toString().trim(),userpass.getText().toString().trim(),"1");

                    Intent intent=new Intent(MainActivity.this,Home.class);
                    intent.putExtra("userid",username.getText().toString().trim());
                    startActivity(intent);
                    MainActivity.this.finish();
                    //denglu.setText("服务器回复的信息 : " + http.Result);
                } else {
                    denglu.setVisibility(denglu.VISIBLE);
                    reegiester.setVisibility(reegiester.VISIBLE);
                    fogget.setVisibility(fogget.VISIBLE);
                    shiyong.setVisibility(shiyong.VISIBLE);
                    denglujiazai.setVisibility(denglujiazai.INVISIBLE);
                    Toast.makeText(MainActivity.this, "登录失败，请检查输入的邮箱是否正确以及网络环境是否异常-后重试！"+http.Result, Toast.LENGTH_SHORT).show();
                    saveLoginInfo(MainActivity.this,username.getText().toString().trim(),userpass.getText().toString().trim(),"0");


                    //tishi.setText("服务器回复的信息 : " + http.Result);
                }
            }
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().
                detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().
                detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());



        shiyong=(Button)findViewById(R.id.button4);
        denglu=(Button)findViewById(R.id.button3);
        fogget=(Button)findViewById(R.id.button6);
        reegiester=(Button)findViewById(R.id.button5);
        tishi=(TextView) findViewById(R.id.textView2);
        username=(EditText) findViewById(R.id.editText);
        userpass=(EditText) findViewById(R.id.editText2);
        denglujiazai=(ProgressBar) findViewById(R.id.progressBar);



        shiyong.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(MainActivity.this).setTitle("系统提示").setMessage("试用不能体验完整的功能，只能体验部分功能！注册后登录即可体验完整的功能！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MainActivity.this,Home.class);
                        intent.putExtra("userid","1272275196@qq.com");
                        startActivity(intent);
                        MainActivity.this.finish();
                    }
                }).setNegativeButton("取消",null).show();




            }
        });


        fogget.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,ForggetPassword.class);
                startActivity(intent);


            }
        });



        reegiester.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,Regesiter.class);
                startActivity(intent);


            }
        });


        denglu.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                String userin=username.getText().toString().trim();
                String userps=userpass.getText().toString().trim();
                Map<String, String> values = new HashMap<String, String>();


                if (userin==null||userin.length()<2){

                    tishi.setText("请输入用户名称！");
                    Toast.makeText(MainActivity.this,"请输入用户名称！",Toast.LENGTH_SHORT).show();
                    username.requestFocus();

                }
                else if (userps==null||userps.length()<2){

                    tishi.setText("请输入用户密码！");
                    Toast.makeText(MainActivity.this,"请输入用户密码！",Toast.LENGTH_SHORT).show();
                    userpass.requestFocus();
                }
                else {

                    values.put("userid", userin);
                    values.put("userpass",userps);
                    denglujiazai.setVisibility(denglujiazai.VISIBLE);
                    denglu.setVisibility(denglu.INVISIBLE);
                    reegiester.setVisibility(reegiester.INVISIBLE);
                    fogget.setVisibility(fogget.INVISIBLE);
                    shiyong.setVisibility(shiyong.INVISIBLE);
                    http.Request("dengluxinxi", values);


                    //无网络环境测试
                    /*Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,Home.class);
                    intent.putExtra("userid",userin);
                    startActivity(intent);
                    MainActivity.this.finish();*/

                }





            }
        });



        SharedPreferences sharedPre=getSharedPreferences("config", MODE_PRIVATE);
        String personusername=sharedPre.getString("username", "");
        String personpassword=sharedPre.getString("password", "");
        String personflag=sharedPre.getString("flag", "");

        if (personusername.trim().length()>2&&personpassword.trim().length()>2){

            username.setText(personusername);
            userpass.setText(personpassword);

            if (personflag.trim().equals("1")){
                String userin=username.getText().toString().trim();
                String userps=userpass.getText().toString().trim();
                Map<String, String> values = new HashMap<String, String>();
                values.put("userid", userin);
                values.put("userpass",userps);
                denglujiazai.setVisibility(denglujiazai.VISIBLE);
                denglu.setVisibility(denglu.INVISIBLE);
                reegiester.setVisibility(reegiester.INVISIBLE);
                fogget.setVisibility(fogget.INVISIBLE);
                shiyong.setVisibility(shiyong.INVISIBLE);
                http.Request("dengluxinxi", values);

            }


        }


    }




    /**
     * 从assets目录下读取个人文件信息文件内容
     * @param fileName
     * @return
     */
    public String getFromAssets(String fileName){
        String result="";
        try {
            InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);

            if((result = bufReader.readLine()) != null){
               result= result.trim();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }






    /**
     * 使用SharedPreferences保存用户登录信息
     * @param context
     * @param username
     * @param password
     *  @param flag
     */
    public static void saveLoginInfo(Context context, String username, String password,String flag){
        //获取SharedPreferences对象
        SharedPreferences sharedPre=context.getSharedPreferences("config", context.MODE_PRIVATE);
        //获取Editor对象
        SharedPreferences.Editor editor=sharedPre.edit();
        //设置参数
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putString("flag", flag);
        //提交
        editor.commit();
    }




}
