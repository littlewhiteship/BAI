package cn.edu.seu.bai_backend.ishop.service;

public class Msstore {

    //private String mssplatform;

    private String mssurl;

    private Float mssdescriptionpoints;

    private Float msslogisticsspeedpoints;

    private Float mssservepoints;

    private String mssshopname;

    private String msstype;

    private String mssimage;

    public void setMsstore(String url,Float descriptionpoints,Float logisticsspeedpoints,Float servepoints,String shopname,String type,String image)
    {
        //this.mssplatform = platform;
        this.mssurl=url;
        this.mssdescriptionpoints=descriptionpoints;
        this.msslogisticsspeedpoints=logisticsspeedpoints;
        this.mssservepoints=servepoints;
        this.mssshopname=shopname;
        this.msstype=type;
        this.mssimage=image;
    }


    public Msstore(){

    }

    public Float getMssdescriptionpoints() {
        return mssdescriptionpoints;
    }

    public Float getMsslogisticsspeedpoints() {
        return msslogisticsspeedpoints;
    }

    public Float getMssservepoints() {
        return mssservepoints;
    }

    public String getMssshopname() {
        return mssshopname;
    }

    public String getMssimage() {
        return mssimage;
    }

    public String getMsstype() {
        return msstype;
    }

    public String getMssurl() {
        return mssurl;
    }

}
