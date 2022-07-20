package com.Creswellcrags.Model;

import java.io.Serializable;

public class BeaconModel implements Serializable {

    public String Title="";

    @Override
    public String toString() {
        return "BeaconModel{" +
                "Title='" + Title + '\'' +
                ", Url='" + Url + '\'' +
                ", uid='" + uid + '\'' +
                ", image=" + image +
                ", Date='" + Date + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

    public String Url="";
    public String uid="";
    public int image;
    public String Date="";
    public String  desc="";

}
