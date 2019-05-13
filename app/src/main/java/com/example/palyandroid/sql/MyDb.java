package com.example.palyandroid.sql;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 走马 on 2019/5/8.
 */


@Entity
public class MyDb {

    @Id(autoincrement = true)
    Long id;
    String url;
    String title;
    String anthor;
    String all;
    String time;
    @Generated(hash = 1835627644)
    public MyDb(Long id, String url, String title, String anthor, String all,
            String time) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.anthor = anthor;
        this.all = all;
        this.time = time;
    }
    @Generated(hash = 98874783)
    public MyDb() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAnthor() {
        return this.anthor;
    }
    public void setAnthor(String anthor) {
        this.anthor = anthor;
    }
    public String getAll() {
        return this.all;
    }
    public void setAll(String all) {
        this.all = all;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }

}
