package com.lifeng.layoutmanager.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import com.lifeng.layoutmanager.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class SouSuo extends AppCompatActivity {

    public WebView myWebView;
    private ProgressDialog dialog=null;
    String urrl;
    String str = urrl;
    private Button baidu,sanliuling,zonghe,biying,youdao,taobao,clearmessage,exitsousuo;
    Context context;
    private EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sou_suo);



        context=SouSuo.this;

        myWebView = (WebView) findViewById(R.id.webviewsousuo);
        baidu=(Button) this.findViewById(R.id.button21);
        sanliuling=(Button) this.findViewById(R.id.button22);
        zonghe=(Button) this.findViewById(R.id.button23);
        biying=(Button) this.findViewById(R.id.button24);
        youdao=(Button) this.findViewById(R.id.button25);
        taobao=(Button) this.findViewById(R.id.button26);
        clearmessage=(Button) this.findViewById(R.id.button27);
        exitsousuo=(Button) this.findViewById(R.id.button28);
        message=(EditText) this.findViewById(R.id.editText8);







        myWebView.setWebViewClient(new WebViewClient());


        WebSettings webSettings = myWebView.getSettings();
        // 设置可以访问文件  
        myWebView.getSettings().setAllowFileAccess(true);
        //如果访问的页面中有Javascript，则webview必须设置支持Javascript  
        myWebView.getSettings().setJavaScriptEnabled(true);
        //myWebView.getSettings().setUserAgentString(MainActivity.getUserAgent());
        myWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        myWebView.getSettings().setAllowFileAccess(true);
        myWebView.getSettings().setAppCacheEnabled(true);
        myWebView.getSettings().setDomStorageEnabled(true);
        myWebView.getSettings().setDatabaseEnabled(true);
        webSettings.setUseWideViewPort(true);// 设置此属性，可任意比例缩放  
        webSettings.setLoadWithOverviewMode(true);// 充满全屏幕  
        webSettings.setBuiltInZoomControls(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        myWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        //设置允许访问文件数据  
        myWebView.getSettings().setAllowFileAccess(true);
        //设置是否支持插件  
        //myWebView.getSettings().setPluginsEnabled(true);
        // 设置缓存模式  
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        // 开启 DOM storage API 功能  
        webSettings.setDomStorageEnabled(true);
        myWebView.setHorizontalScrollBarEnabled(false);// 水平不显示滚动条  
        myWebView.setOverScrollMode(View.OVER_SCROLL_NEVER);// 禁止即在网页顶出现一个空白，又自动回去。  
        myWebView.setWebChromeClient(new SouSuo.webChromClient());
        myWebView.setWebViewClient(new SouSuo.webClient());//防止外部浏览器



        /*dialog = ProgressDialog.show(SouSuo.this, null, "正在加载中，请稍后···");
        myWebView.loadUrl(str);*/


        //监听下载
        myWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                // System.out.println(url: + url);
                if (url.endsWith(".zip")){
                    // 如果传进来url包含.zip文件，那么就开启下载线程
                    // System.out.println(download start...);
                    new SouSuo.DownloadThread(url).start();
                }
            }
        });
        myWebView.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event){
                if(event.getAction()== KeyEvent.ACTION_DOWN){
                    if(keyCode== KeyEvent.KEYCODE_BACK&&myWebView.canGoBack()){
                        myWebView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });







        //搜索模块事件监听

        baidu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //str="https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd="+message.getText().toString().trim()+"&oq="+message.getText().toString().trim()+"&rsv_pq=e76fd946000419fb&rsv_t=9863i4scZoQxYRY3U7fIUzF5hWe3SvRzaFEbSG1BSGVFz7WytBBYdX0wR5A&rqlang=cn&rsv_enter=1&inputT=2&rsv_sug3=22&rsv_sug1=19&rsv_sug7=100&rsv_sug2=0&rsv_sug4=1282&rsv_sug=1";
                //str="https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd="+message.getText().toString().trim()+"&oq="+message.getText().toString().trim()+"&rsv_pq=e76fd946000419fb&rsv_t=9863i4scZoQxYRY3U7fIUzF5hWe3SvRzaFEbSG1BSGVFz7WytBBYdX0wR5A&rqlang=cn&rsv_enter=1&inputT=2&rsv_sug3=22&rsv_sug1=19&rsv_sug7=100&rsv_sug2=0&rsv_sug4=1282&rsv_sug=1";

                String key=message.getText().toString().trim();
                try {
                    key= URLEncoder.encode(key,"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //str="https://m.phonebaidubrowse.com/ssid=1f80d0d2b8a3cac7d2aad3d0c4e3c032/from=2001a/s?word="+message.getText().toString().trim()+"+&ts=6390901&t_kt=0&ie=utf-8&fm_kl=17709454cf&rsv_iqid=3646323252&rsv_t=164bFHSwyuVR4i%252FNbFHI4U%252BxpEHv9SfUOoTb6HgcM0t6nGbBY4QcEPPIBmg&sa=ib&rsv_pq=3646323252&ss=101&inputT=578&rsv_sug4=11800";
                str="http://www.baidu.com.cn/s?wd=" + key + "&cl=3";

                //同步cookie
                //new HttpCookie(mHandler,str).start();
                syncCookie(context,str);
                dialog = ProgressDialog.show(SouSuo.this, null, "正在加载中，请稍后···");
                myWebView.loadUrl(str);
            }
        });

        sanliuling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //str="https://www.so.com/s?ie=utf-8&src=hao_360so_b&shb=1&hsid=60bbdf0c322f9b1d&q="+message.getText().toString().trim();
                str="https://m.so.com/s?q="+message.getText().toString().trim()+"+&src=msearch_next_input&srcg=home_next";

                syncCookie(context,str);
                dialog = ProgressDialog.show(SouSuo.this, null, "正在加载中，请稍后···");
                myWebView.loadUrl(str);
            }
        });



        zonghe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //str="https://www.so.com/s?ie=utf-8&src=hao_search_b&shb=1&hsid=60bbdf0c322f9b1d&q="+message.getText().toString().trim();
                str="http://m.sogou.com/web/searchList.jsp?uID=4mFzzlD9lvjgluCt&v=5&from=index&w=1274&t=1489316942990&s_t=1489316957342&s_from=index&keyword="+message.getText().toString().trim()+"+&pg=webSearchList&suguuid=51565d85-f107-43a7-ad21-e0b50479cead";
                syncCookie(context,str);
                dialog = ProgressDialog.show(SouSuo.this, null, "正在加载中，请稍后···");
                myWebView.loadUrl(str);
            }
        });


        biying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //str="http://cn.bing.com/search?form=SSO360&pc=S360&market=zh-cn&ie=utf-8&q="+message.getText().toString().trim();

                str="http://cn.bing.com/search?q="+message.getText().toString().trim()+"+&qs=n&form=QBLH&sp=-1&pq="+message.getText().toString().trim()+"+&sc=5-8&sk=&cvid=C9C745E737D847AFBE64F5000C7CDA04";

                syncCookie(context,str);
                dialog = ProgressDialog.show(SouSuo.this, null, "正在加载中，请稍后···");
                myWebView.loadUrl(str);
            }
        });



        youdao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //str="http://www.youdao.com/w/eng/"+message.getText().toString().trim()+"/#keyfrom=dict2.index";
                str="http://m.youdao.com/dict?le=eng&q="+message.getText().toString().trim()+"+";

                syncCookie(context,str);
                dialog = ProgressDialog.show(SouSuo.this, null, "正在加载中，请稍后···");
                myWebView.loadUrl(str);
            }
        });


        taobao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //str="https://s.taobao.com/search?q="+message.getText().toString().trim()+"&commend=all&ssid=s5-e&search_type=item&sourceId=tb.index&spm=a21bo.50862.201856-phonetaobao-item.1&ie=utf8&initiative_id=tbindexz_20170312";
                //str="https://s.m.phonetaobao.com/h5?event_submit_do_new_search_auction=1&_input_charset=utf-8&topSearch=1&atype=b&searchfrom=1&action=home%3Aredirect_app_action&from=1&sst=1&n=20&buying=buyitnow&q="+message.getText().toString().trim()+"%20";

                //str="https://s.taobao.com/search?q="+message.getText().toString().trim()+"&commend=all&ssid=s5-e&search_type=item&sourceId=tb.index&spm=a21bo.50862.201856-taobao-item.1&ie=utf8&initiative_id=tbindexz_20170313";

                str="https://s.taobao.com/search?q="+message.getText().toString().trim()+"&commend=all";


                syncCookie(context,str);
                dialog = ProgressDialog.show(SouSuo.this, null, "正在加载中，请稍后···");
                myWebView.loadUrl(str);
            }
        });

        clearmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             message.setText("");
            }
        });

        exitsousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              SouSuo.this.finish();
            }
        });



    }






    private class webChromClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress){
            // TODO Auto-generated method stub  
            super.onProgressChanged(view, newProgress);
        }


        @Override
        public void getVisitedHistory(ValueCallback<String[]> callback){
            super.getVisitedHistory(callback);
            //Log.i(TAG,"getVisitedHistory");
        }

        @Override
        public void onCloseWindow(WebView window){
            super.onCloseWindow(window);
            // Log.i(TAG,"onCloseWindow");
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog,
                                      boolean isUserGesture, Message resultMsg){
            // Log.i(TAG, "onCreateWindow");
            return super.onCreateWindow(view,isDialog,isUserGesture,resultMsg);
        }

        @Override
        @Deprecated
        public void onExceededDatabaseQuota(String url,
                                            String databaseIdentifier, long quota, long estimatedDatabaseSize, long totalQuota,
                                            WebStorage.QuotaUpdater quotaUpdater){
            super.onExceededDatabaseQuota(url,databaseIdentifier,quota, estimatedDatabaseSize,totalQuota,quotaUpdater);
            //Log.i(TAG,"onExceededDatabaseQuota");
        }
        @Override
        public void onReceivedIcon(WebView view, Bitmap icon){
            super.onReceivedIcon(view,icon);
            //Log.i(TAG,"gonReceivedIcon");
        }

        @Override
        public void onReceivedTitle(WebView view, String title){
            super.onReceivedTitle(view,title);
            //Log.i(TAG,"onReceivedTitle");
        }

        @Override
        public void onRequestFocus(WebView view){
            super.onRequestFocus(view);
            // Log.i(TAG,"onRequestFocus");
        }



        public void openFileChooser(ValueCallback<Uri> uploadMsg){
            ValueCallback<Uri> mUploadMessag;
            mUploadMessag = uploadMsg;
            Intent i=new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            SouSuo.this.startActivityForResult(
                    Intent.createChooser(i,"File Chooser"),SouSuo.RESULT_FIRST_USER);
        }
        public void openFileChooser(ValueCallback uploadMsg, String acceptType){

            Intent i=new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            SouSuo.this.startActivityForResult(
                    Intent.createChooser(i,"File Browser"),SouSuo.RESULT_FIRST_USER);
        }
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture){
            ValueCallback<Uri> mUploadMessage;
            mUploadMessage= uploadMsg;
            Intent i=new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            SouSuo.this.startActivityForResult(
                    Intent.createChooser(i,"File Chooser"),SouSuo.RESULT_FIRST_USER);
        }


    }






    private class webClient extends WebViewClient {

        @Override
        public void onPageStarted(final WebView view, String url, Bitmap favicon){
            // TODO Auto-generated method stub  
            super.onPageStarted(view, url,favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url){
            // TODO Auto-generated method stub  
            // super.onPageFinished(view, url);  
            dialog.dismiss();

        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            // TODO Auto-generated method stub  
            super.onReceivedError(view,errorCode,description,failingUrl);

            // guo add  
            //  Toast.makeText(SouSuo.this,"网页加载出错！", Toast.LENGTH_LONG).show();

            //view.loadUrl("file:///android_asset/defaultpage/index1.html");// 加载一个默认的本地网页  
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            // TODO Auto-generated method stub  
            view.loadUrl(url);
            return true;
        }


        @Override
        public void onLoadResource(WebView view, String url){
            super.onLoadResource(view,url);
            // Log.i(TAG,"onLoadResource: ");
        }

        @Override
        public void onReceivedHttpAuthRequest(WebView view,
                                              HttpAuthHandler handler, String host, String realm){
            super.onReceivedHttpAuthRequest(view,handler,host,realm);
            // Log.i(TAG,"onReceivedHttpAuthRequest: ");
        }


        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
            super.onReceivedSslError(view,handler,error);
            //Log.i(TAG,"onReceivedSslError: ");
        }

        @Override
        @Deprecated
        public void onTooManyRedirects(WebView view, Message cancelMsg,
                                       Message continueMsg){
            super.onTooManyRedirects(view,cancelMsg,continueMsg);
            // Log.i(TAG,"onTooManyRedirects");
        }




    }

    @Override
    protected void onResume(){
// TODO Auto-generated method stub  
        super.onResume();
    }

    @Override
    protected void onDestroy(){
        // TODO Auto-generated method stub  
        super.onDestroy();
    }

    // 网络状态判断  
    public boolean isNetworkConnected(Context context){
        if(context!=null){
            ConnectivityManager mConnectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo=mConnectivityManager.getActiveNetworkInfo();
            if(mNetworkInfo!=null){
                return mNetworkInfo.isAvailable();
            }
        }
        return false;

    }



    /**
          * 执行下载的线程
          */
    class DownloadThread extends Thread {
        private String mUrl;

        public DownloadThread(String url) {
            this.mUrl = url;
        }

        @Override
        public void run() {
            try {
                URL httpUrl = new URL(mUrl);
                HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                InputStream in = conn.getInputStream();
                FileOutputStream out = null;
                // 获取下载路径
                File downloadFile;
                File sdFile;
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    downloadFile = Environment.getExternalStorageDirectory();
                    sdFile = new File(downloadFile,".zip");
                    out = new FileOutputStream(sdFile);
                }
                byte[] b = new byte[8 * 1024];
                int len;
                while ((len = in.read(b)) != -1) {
                    if (out != null) {
                        out.write(b, 0, len);
                    }
                }
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                // System.out.println(download success...);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



//cook同步
    /*public static void synCookies(Context context,String url){
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager=CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();//移除  
        cookieManager.setCookie(url,cookies);//cookies是在HttpClient中获得的cookie  
        CookieSyncManager.getInstance().sync();
    }*/



    /**
     * Sync Cookie
     */
    private void syncCookie(Context context, String url){
        try{
            // Log.d("Nat: webView.syncCookie.url", url);
            CookieSyncManager.createInstance(context);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();// 移除
            cookieManager.removeAllCookie();
            String oldCookie = cookieManager.getCookie(url);
            if(oldCookie != null){
                // Log.d("Nat: webView.syncCookieOutter.oldCookie", oldCookie);
            }
            StringBuilder sbCookie = new StringBuilder();
            sbCookie.append(String.format("JSESSIONID=%s","INPUT YOUR JSESSIONID STRING"));
            sbCookie.append(String.format(";domain=%s", "INPUT YOUR DOMAIN STRING"));
            sbCookie.append(String.format(";path=%s","INPUT YOUR PATH STRING"));
            String cookieValue = sbCookie.toString();
            cookieManager.setCookie(url, cookieValue);
            CookieSyncManager.getInstance().sync();
            String newCookie = cookieManager.getCookie(url);
            if(newCookie != null){
                // Log.d("Nat: webView.syncCookie.newCookie", newCookie);
            }
        }catch(Exception e){
            //Log.e("Nat: webView.syncCookie failed", e.toString());
        }
    }





}
