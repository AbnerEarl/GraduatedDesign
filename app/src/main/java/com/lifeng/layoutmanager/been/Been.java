package com.lifeng.layoutmanager.been;

/**
 *
 */
public class Been {

    private int src;
    private String name;
    private String url;

    public Been(String name,int src,String url){
        this.name=name;
        this.src=src;
        this.url=url;
    }

    public int getSrc() {
        return src;
    }

    public void setSrc(int src) {
        this.src = src;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
