package com.lifeng.layoutmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Button;

import com.lifeng.layoutmanager.R;
import com.lifeng.layoutmanager.adapter.RecyclerAdapter;
import com.lifeng.layoutmanager.been.Been;
import com.lifeng.layoutmanager.callback.MoveDeleteTouchCallback;
import com.lifeng.layoutmanager.callback.MoveTouchCallback;
import com.lifeng.layoutmanager.click.OnRecyclerItemClickListener;
import com.lifeng.layoutmanager.decoration.DividerGridItemDecoration;
import com.lifeng.layoutmanager.utils.VibratorUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：LayoutManager
 * 类描述：流式布局的主界面
 */
public class FlowActivity extends AppCompatActivity implements MoveTouchCallback.OnDragListener{

    private RecyclerView recyclerView;
    private Button button1;
    private Button button2;
    private List<Been> lists;
    private RecyclerAdapter recyclerAdapter;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow);
        initView();
        initData();
        setListener();
    }

    //布局的初始化
    private void initView(){
        recyclerView= (RecyclerView) findViewById(R.id.recycle11);
        /*button1= (Button) findViewById(R.id.button1);
        button2= (Button) findViewById(R.id.button2);*/
    }

    //数据的初始化
    private void initData(){


        lists=new ArrayList<>();

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


        for(int i=0;i<appname.length&&i<apppicture.length&&i<applink.length;i++){
            lists.add(new Been( appname[i], apppicture[i],applink[i]));

        }
        /*lists.add(new Been( "百度音乐", R.drawable.phonebaidumusic,"http://music.phonebaidubrowse.com/"));
        lists.add(new Been( "暴风影音", R.drawable.phonebaofengyingyin,"http://www.baofeng.com/"));
        lists.add(new Been( "360浏览器",R.drawable.phone360browse,"https://www.so.com/"));
        lists.add(new Been( "爱奇艺", R.drawable.phoneaiqiyi,"http://www.iqiyi.com/"));
        lists.add(new Been( "QQ音乐", R.drawable.phoneqqmusice,"https://y.phoneqq.com/"));
        lists.add(new Been( "京东商城", R.drawable.phonejingdong,"https://www.jd.com/"));
        lists.add(new Been( "百度浏览器", R.drawable.phonebaidubrowse,"https://www.phonebaidubrowse.com/"));
        lists.add(new Been( "优酷视频",R.drawable.phoneyouku,"http://www.phoneyouku.com/"));
        lists.add(new Been( "QQ", R.drawable.phoneqq,"http://w.phoneqq.com/"));
        lists.add(new Been( "淘宝商城", R.drawable.phonetaobao,"https://www.phonetaobao.com/"));*/


        recyclerAdapter=new RecyclerAdapter(R.layout.item_flow,lists);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(FlowActivity.this,4));
        recyclerView.addItemDecoration(new DividerGridItemDecoration(FlowActivity.this));
        itemTouchHelper=new ItemTouchHelper(new MoveTouchCallback(recyclerAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        itemTouchHelper=new ItemTouchHelper(new MoveDeleteTouchCallback(0,ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT,
                recyclerAdapter,lists));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(recyclerAdapter);
    }

    //控件的监听
    private void setListener(){

        //添加新的项（在末尾）
        /*button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lists.add(new Been("新加应用",R.drawable.addapplication,"http://qqapp.qq.com/"));
                recyclerAdapter.notifyDataSetChanged();
            }
        });*/

        //删除最后一项
       /* button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lists.size()>0){
                    lists.remove(lists.size()-1);
                    recyclerAdapter.notifyDataSetChanged();
                }
            }
        });*/

        //自定义的拖动效果监听(具体长按还是短按自己控制)
        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerView) {
            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) {
                //这里可以控制不可拖动的布局（此时的情况为最后一个不可拖动）
                if (vh.getLayoutPosition()!=lists.size()-1) {
                    itemTouchHelper.startDrag(vh);
                    VibratorUtils.Vibrate(FlowActivity.this, 70);   //震动70ms
                }
            }
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                /*Been been = lists.get(vh.getLayoutPosition());
                Toast.makeText(FlowActivity.this,been.getName(),Toast.LENGTH_SHORT).show();*/

                Been been = lists.get(vh.getLayoutPosition());
                String url=been.getUrl();

                Intent intent=new Intent(FlowActivity.this,ApplicationLoad.class);
                intent.putExtra("url",url);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onFinishDrag() {

    }

}
