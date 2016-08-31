package com.yl.storm.group.entity;

/**
 * Created by Administrator on 2016/8/28.
 */
public class UserInfo {
    private long uid;
    private long sid;
    private String name;

    public UserInfo() {
    }

    public UserInfo(long uid, long sid, String name) {
        this.uid = uid;
        this.sid = sid;
        this.name = name;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "uid=" + uid +
                ", sid=" + sid +
                ", name='" + name + '\'' +
                '}';
    }
}
