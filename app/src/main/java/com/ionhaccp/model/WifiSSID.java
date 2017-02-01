package com.ionhaccp.model;

/**
 * Created by MehterM on 06-05-2015.
 */
public class WifiSSID {
    String SSID;
    String Security;

    public String getSSID(){
        return SSID;
    }
    public void setSSID(String SSID){
        this.SSID = SSID;
    }
    public String getSecurity(){
        return Security;
    }
    public void setSecurity(String Security){
        this.Security = Security;
    }
}
