package com.lifeng.layoutmanager.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lifeng.layoutmanager.R;
import com.lifeng.layoutmanager.adapter.RecyclerAdapter;
import com.lifeng.layoutmanager.been.Been;
import com.lifeng.layoutmanager.callback.MoveDeleteTouchCallback;
import com.lifeng.layoutmanager.callback.MoveTouchCallback;
import com.lifeng.layoutmanager.click.OnRecyclerItemClickListener;
import com.lifeng.layoutmanager.decoration.DividerGridItemDecoration;
import com.lifeng.layoutmanager.utils.VibratorUtils;
import com.lifeng.layoutmanager.webservice.RequestFunc;
import com.lifeng.layoutmanager.webservice.WebService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteApp extends AppCompatActivity {

    private ListView lv;
    private int[]selecapp;
    private boolean[] checks; //用于保存checkBox的选择状态  
    private Button selectall,cancelall,selectpart,deleteapp;
    private MyAdapter mAdapter;
    private String userid;
    private int[]appid;
    private int[]useappid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_app);

        lv = (ListView) findViewById(R.id.lvdeleteapp);
        selectall=(Button)this.findViewById(R.id.button32);
        cancelall=(Button)this.findViewById(R.id.button34);
        selectpart=(Button)this.findViewById(R.id.button33);
        deleteapp=(Button)this.findViewById(R.id.btndelete);

        Intent intent=getIntent();
        userid=intent.getStringExtra("userid");

        mAdapter = new MyAdapter(this);//得到一个MyAdapter对象
        lv.setAdapter(mAdapter);//为ListView绑定Adapter

        checks=new boolean[lv.getCount()];
        selecapp=new int[lv.getCount()];

        for (int p=0;p<selecapp.length;p++){
            selecapp[p]=0;
        }




        selectall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectAll();
            }
        });

        cancelall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cancelAll();
            }
        });
        selectpart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reverse();
            }
        });
        deleteapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String myappid="";
                for (int k=0;k<selecapp.length;k++){
                    if (selecapp[k]>100){
                        myappid+=selecapp[k]+":";
                    }
                }

                Map<String, String> values = new HashMap<String, String>();
                values.put("userid", userid);
                values.put("appid", myappid);
                http.Request("deleteapp", values);


               /* new  AlertDialog.Builder(DeleteApp.this)
                        .setTitle("系统提示")
                        .setMessage("删除应用成功，可进入“添加应用”进行添加新的应用！"+myappid)
                        .setPositiveButton("确定",
                                new  DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public  void  onClick(DialogInterface dialog, int  which)
                                    {

                                    }
                                }).show();*/
            }
        });



    }





    //    取消所有选择
    private void cancelAll() {

        for (int i = 0; i < checks.length; i++) {
            checks[i]=false;
            selecapp[i]=0;
        }
        mAdapter.notifyDataSetChanged();
    }

    //    反选
    private void reverse() {
        for (int i = 0; i < checks.length; i++) {
            if (checks[i]) {
                checks[i]=false;
                selecapp[i]=0;
            } else {
                checks[i]=true;
                selecapp[i]=useappid[i];
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    //    全选
    private void selectAll() {
        for (int i = 0; i < checks.length; i++) {
            checks[i]=true;
            selecapp[i]=useappid[i];
        }
        mAdapter.notifyDataSetChanged();
    }






    /*添加一个得到数据的方法，方便使用*/
    private ArrayList<HashMap<String, Object>> getDate(){

        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String,     Object>>();



        String[] appname=new String[]{"网易邮箱","360搜索","爱奇艺","爱淘宝","百度搜索","百度音乐","百度翻译","百度百科","暴风影音","汽车之家","高德地图"
                ,"手机京东","快递100","美团","QQ","腾讯门户","QQ邮箱","QQ音乐","QQ空间","搜狐","神马搜索","淘宝","淘票票","腾讯微博","腾讯视屏","天猫"
                ,"百度贴吧","问啊","神马小说","携程旅行","新浪微博","亚马逊","优酷视频"};


        int[] apppicture=new int[]{R.drawable.phone163mail,R.drawable.phone360browse,R.drawable.phoneaiqiyi,R.drawable.phoneaitaobao,R.drawable.phonebaidubrowse
                ,R.drawable.phonebaidumusic,R.drawable.phonebaidutranslate,R.drawable.phonebaike,R.drawable.phonebaofengyingyin,R.drawable.phonecarhome,R.drawable.phonegaodemap
                ,R.drawable.phonejingdong,R.drawable.phonekuaidi100,R.drawable.phonemeituan,R.drawable.phoneqq,R.drawable.phoneqqbrowse,R.drawable.phoneqqmail
                ,R.drawable.phoneqqmusice,R.drawable.phoneqqzone,R.drawable.phonesouhu,R.drawable.phonesousuo,R.drawable.phonetaobao,R.drawable.phonetaopiaopiao
                ,R.drawable.phonetencentblog,R.drawable.phonetencentvideo,R.drawable.phonetianmao,R.drawable.phonetieba,R.drawable.phonewen
                ,R.drawable.phonexiaoshuo,R.drawable.phonexiecheng,R.drawable.phonexinlangblog,R.drawable.phoneyamaxun,R.drawable.phoneyouku};

        String[] applink=new String[]{"http://smart.mail.163.com/","https://www.so.com/","http://www.iqiyi.com/","http://ai.m.taobao.com/index.html?pid=mm_32464889_3262733_48306701"
                ,"https://www.phonebaidubrowse.com/","http://music.phonebaidubrowse.com/","http://fanyi.baidu.com/#en/zh/object","https://wapbaike.baidu.com/?bk_fr=channel_uc_app"
                ,"http://www.baofeng.com/","http://m.autohome.com.cn/?pvareaid=103175&origin=browser.uc.com","http://m.amap.com/","https://www.jd.com/","http://m.kuaidi100.com/"
                ,"http://i.meituan.com/?nodwon&utm_source=uchtml5&utm_medium=wap","http://w.phoneqq.com/","http://info.3g.qq.com/g/s?aid=index&g_ut=3&g_f=22316","https://ui.ptlogin2.qq.com/cgi-bin/login?style=9&appid=522005705&daid=4&s_url=https%3A%2F%2Fw.mail.qq.com%2Fcgi-bin%2Flogin%3Fvt%3Dpassport%26vm%3Dwsk%26delegate_url%3D%26f%3Dxhtml%26target%3D&hln_css=http%3A%2F%2Fmail.qq.com%2Fzh_CN%2Fhtmledition%2Fimages%2Flogo%2Fqqmail%2Fqqmail_logo_default_200h.png&low_login=1&hln_autologin=%E8%AE%B0%E4%BD%8F%E7%99%BB%E5%BD%95%E7%8A%B6%E6%80%81&pt_no_onekey=1"
                ,"https://y.phoneqq.com/","http://ui.ptlogin2.qq.com/cgi-bin/login?style=9&pt_ttype=1&appid=549000929&pt_no_auth=1&pt_wxtest=1&daid=5&s_url=https%3A%2F%2Fh5.qzone.qq.com%2Fmqzone%2Findex"
                ,"http://m.sohu.com/?_once_=000093_uc9gongge","https://so.m.sm.cn/?uc_param_str=dnntnwvepffrgibijbprsv&gp=AAQYHiECo%2Fmrx2afvBRPrQpOJgnAtV%2FJCDLJb%2B2UvrJ3Lg%3D%3D&from=ucjgg"
                ,"https://m.taobao.com/?ttid=2uc0074#index","https://h5.m.taobao.com/app/movie/pages/index/index.html","http://ui.ptlogin2.qq.com/cgi-bin/login?appid=46000101&style=8&lang=&low_login=1&hide_title_bar=1&hide_close_icon=1&daid=6&hln_css=http://mat1.gtimg.com/www/mb/images/logo244x100.png&s_url=http%3A%2F%2Fw.t.qq.com%2Ftouch"
                ,"http://m.v.qq.com/index.html","https://www.tmall.com/?ali_trackid=2:mm_32464889_8264856_54352793,ucgw1:1489402349_2k9_1760235709&e=MM4jjnXRYW5w4vFB6t2Z2iperVdZeJvipRe_8jaAHci5VBFTL4hn2Rit1JlfDA1LLzyWwQxzkU9RJ0McG0ohS2X4HfLoBQ_1FaBUGH5r42DB-3ofxFvTSMx2jT2Y_rreai-0GPnn_jQZ5LcE1daWU13x5JIfav5vy68Ugwc8pj_fWRFwkirhSo6yEmmEWDVTMGVl8sFEIPnOlbd-EjrGUQ&type=2&tkFlag=1"
                ,"https://wapp.baidu.com/?page=like","http://ask.uc.cn/?uc_biz_str=S:custom|7CC:iflow_ncmt&uc_param_str=frpfvedncpssntnwbieime&time=1489402533162&sdkdeep=2&sdksid=35ef9207-b7d8-36fc-badc-81a8d99832fb&sdkoriginal=35ef9207-b7d8-36fc-badc-81a8d99832fb#!/index"
                ,"http://t.shuqi.com/?from=webapp","http://m.ctrip.com/webapp/myctrip/?from=http%3A%2F%2Fm.ctrip.com%2Fhtml5%2F","http://m.weibo.cn/?wm=5399_0012","https://www.amazon.cn/gp/aw?tag=uc-jgg-android-23","http://www.youku.com/?tpa=dW5pb25faWQ9MTAzMjU1XzEwMDAwMl8wMV8wMg"
        };




        appid=new int[appname.length];useappid=new int[appname.length];
        for (int j=0;j<appid.length;j++){
            appid[j]=1000001+j;
        }
        Map<String, String> values = new HashMap<String, String>();
        values.put("userid", userid);
        http1.Request("chaxunuserappids", values);




        /*为动态数组添加数据*/
        int tt=0;
        for(int i=0;i<appname.length&&i<apppicture.length&&i<applink.length;i++){
            if (appid[i]!=0){
                useappid[tt++]=appid[i];
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("ItemImage", apppicture[i]);//加入图片
                map.put("ItemTitle", appname[i]);
                map.put("ItemAppid", appid[i]);
                listItem.add(map);
            }
        }
        return listItem;

    }
    /*
         * 新建一个类继承BaseAdapter，实现视图与数据的绑定
         */
    private class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局



        /*构造函数*/
        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {


            return getDate().size();//返回数组的长度
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
        /*书中详细解释该方法*/
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            //观察convertView随ListView滚动情况

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_deleteapp,null);
                holder = new ViewHolder();
                /*得到各个控件的对象*/
                holder.image=(ImageView) convertView.findViewById(R.id.appimageView);
                holder.name = (TextView) convertView.findViewById(R.id.apptitle);
                holder.appid = (TextView) convertView.findViewById(R.id.appid);
                holder.introduce = (Button) convertView.findViewById(R.id.appdetail);
                holder.deletea=(Button) convertView.findViewById(R.id.appdelete);
                holder.select = (CheckBox) convertView.findViewById(R.id.appselect);
                convertView.setTag(holder);//绑定ViewHolder对象
            }
            else{
                holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
            }

            /*设置TextView显示的内容，即我们存放在动态数组中的数据*/
            // holder.image.setImageDrawable(getDate().get(position).get("ItemImage"));
            holder.image.setBackgroundResource((Integer)getDate().get(position).get("ItemImage"));
            holder.name.setText(getDate().get(position).get("ItemTitle").toString());
            holder.appid.setText(getDate().get(position).get("ItemAppid").toString());
            //holder.discuss.setText(getDate().get(position).get("ItemDiscuss").toString());
            // holder.text.setText(getDate().get(position).get("ItemText").toString());

            holder.introduce.setTag(position);
            holder.deletea.setTag(position);
            holder.select.setTag(position);

            final int pos=position;


            /*为Button添加点击事件*/


            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });



            holder.introduce.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //打印Button的点击信息
                    new  AlertDialog.Builder(DeleteApp.this)
                            .setTitle("系统提示")
                            .setMessage("查看详情！")
                            .setPositiveButton("确定",
                                    new  DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public  void  onClick(DialogInterface dialog, int  which)
                                        {
                                        }
                                    }).show();

                }
            });


            holder.deletea.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {


                    Map<String, String> values = new HashMap<String, String>();
                    values.put("userid", userid);
                    values.put("appid", holder.appid.getText().toString().trim()+":");
                    http.Request("deleteapp", values);







                    //打印Button的点击信息
                    /*new  AlertDialog.Builder(DeleteApp.this)
                            .setTitle("系统提示")
                            .setMessage("删除应用成功！")
                            .setPositiveButton("确定",
                                    new  DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public  void  onClick(DialogInterface dialog, int  which)
                                        {
                                        }
                                    }).show();*/
                }
            });


            //给CheckBox设置事件监听
            holder.select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                    // TODO Auto-generated method stub
                    if(isChecked){

                        checks[pos]=true;
                        selecapp[pos]=Integer.parseInt(holder.appid.getText().toString().trim()) ;

                    }else{

                        checks[pos]=false;
                        selecapp[pos]=0;

                    }


                }
            });

            holder.select.setChecked(checks[pos]);



       /*     holder.select.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (holder.select.isChecked()){
                        holder.select.setChecked(false);
                    }else {
                        holder.select.setChecked(true);
                    }
                }
            });*/

            return convertView;
        }

    }
    /*存放控件*/
    public final class ViewHolder{
        public TextView name;
        public TextView appid;
        public Button introduce;
        public Button deletea;
        public CheckBox select;
        public ImageView image;
    }



    WebService http=new WebService(new RequestFunc() {
        @Override
        public void Func() {
            if (http.Result != null) {
                if (http.Result.equals("true")) {
                    Toast.makeText(DeleteApp.this, "删除应用成功！" + http.Result, Toast.LENGTH_SHORT).show();
                    //Intent intent=new Intent(Main2Activity.this,Main22Activity.class);
                    //intent.putExtra("userid",username.getText().toString().trim());
                    //startActivity(intent);
                    DeleteApp.this.finish();
                    //denglu.setText("服务器回复的信息 : " + http.Result);
                } else {
                    Toast.makeText(DeleteApp.this, "删除应用失败！请检查网络连接是否正确，稍后重试！"+http.Result, Toast.LENGTH_SHORT).show();
                }
            }
        }
    });




    WebService http1=new WebService(new RequestFunc() {
        @Override
        public void Func() {
            if (http1.Result != null) {

                String[] userappid=http1.Result.toString().trim().split(":");
                int k=0;
                for (int i=0;i<userappid.length-1;i++){
                    for (int j=0;j<appid.length;j++){
                        if (!userappid[i].trim().equals(Integer.toString(appid[j]) )){
                            appid[j]=0;
                        }
                    }
                }


            } else {
                Toast.makeText(DeleteApp.this, "获取用户信息失败，无法完成删除应用！请检查网络连接是否正确，退出后重试！"+http1.Result, Toast.LENGTH_SHORT).show();
                DeleteApp.this.finish();
            }

        }
    });





}
