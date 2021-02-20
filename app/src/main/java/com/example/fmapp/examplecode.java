package com.example.fmapp;

public class examplecode{
    int mimg;
    String mname,mExperiance,mHowmuchex;
    public examplecode(int img, String name,String experiance,String howmuchex)
    {
        mimg = img;
        mname = name;
        mExperiance = experiance;
        mHowmuchex = howmuchex;
    }

    public int getMimg() {
        return mimg;
    }

    public String getMname(){
        return mname;
    }
    public String getmExperiance()
    {
        return mExperiance;
    }

    public String getmHowmuchex() {
        return mHowmuchex;
    }
}
