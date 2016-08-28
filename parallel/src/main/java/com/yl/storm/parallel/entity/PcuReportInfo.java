package com.yl.storm.parallel.entity;

import backtype.storm.tuple.Tuple;

import java.util.Date;

/**
 * Created by Administrator on 2015/10/29.
 */
public class PcuReportInfo {
    private long ot = 0;
    private long sbd = 0;
    private long sd = 0;
    private int te = 0;
    private String cv = null;
    private String sed = null;
    private long ud = 0;
    private long dr = 0;
    private long srvstamp = 0;
    private String wy = null;
    private String imei = null;
    private String mac = null;

    private String vr = null;
    private String ak = null;

    private Date beginTime;
    private Date endTime;

    // private String uri;

    public PcuReportInfo(Tuple tuple){
        // (sed, te, cv, sd, sbd, ud, ot, dr, srvstamp)
        // sed = tuple.getLongByField("sed");
        sed = tuple.getStringByField("sed");
        te = tuple.getIntegerByField("te");
        cv = tuple.getStringByField("cv");
        sd = tuple.getLongByField("sd");
        sbd = tuple.getLongByField("sbd");
        ud = tuple.getLongByField("ud");
        ot = tuple.getLongByField("ot");
        dr = tuple.getLongByField("dr");
        srvstamp = tuple.getLongByField("srvstamp");
        wy = tuple.getStringByField("wy");
        imei = tuple.getStringByField("ii");
        mac = tuple.getStringByField("mc");

        vr = tuple.getStringByField("vr");
        ak = tuple.getStringByField("ak");  // uri
        // uri = tuple.getStringByField("uri");
    }

    public long getOt() {
        return ot;
    }

    public long getSbd() {
        return sbd;
    }

    public long getSd() {
        return sd;
    }

    public int getTe() {
        return te;
    }

    public String getCv() {
        return cv;
    }

    public String getSed() {
        return sed;
    }

    public long getUd() {
        return ud;
    }

    public long getDr() {
        return dr;
    }

    public void setDr(long dr) {
        this.dr = dr;
    }

    public long getSrvstamp() {
        return srvstamp;
    }

    public String getWy() {
        return wy;
    }

    public String getImei() {
        return imei;
    }

    public String getMac() {
        return mac;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getVr() {
        return vr;
    }

    public void setVr(String vr) {
        this.vr = vr;
    }

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "PcuReportInfo{" +
                "ot=" + ot +
                ", sbd=" + sbd +
                ", sd=" + sd +
                ", te=" + te +
                ", cv='" + cv + '\'' +
                ", sed='" + sed + '\'' +
                ", ud=" + ud +
                ", dr=" + dr +
                ", srvstamp=" + srvstamp +
                ", wy='" + wy + '\'' +
                ", imei='" + imei + '\'' +
                ", mac='" + mac + '\'' +
                ", vr='" + vr + '\'' +
                ", ak='" + ak + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                '}';
    }
}
