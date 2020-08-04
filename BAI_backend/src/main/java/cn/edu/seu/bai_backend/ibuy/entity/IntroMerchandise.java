package cn.edu.seu.bai_backend.ibuy.entity;

public class IntroMerchandise {
    private String amsname;

    private Float price;

    private String link;

    private Integer monthlysales;

    private String platform;

    private String amsimage;

    public void setIntroMerchandise(String amsname,Float price,String link,Integer monthlysales,String platform,String amsimage){
        this.amsname = amsname;
        this.price = price;
        this.link = link;
        this.monthlysales = monthlysales;
        this.platform = platform;
        this.amsimage = amsimage;
    }

    public Float getPrice() {
        return price;
    }

    public Integer getMonthlysales() {
        return monthlysales;
    }

    public String getAmsimage() {
        return amsimage;
    }

    public String getAmsname() {
        return amsname;
    }

    public String getLink() {
        return link;
    }

    public String getPlatform() {
        return platform;
    }

    public void setAmsimage(String amsimage) {
        this.amsimage = amsimage;
    }

    public void setAmsname(String amsname) {
        this.amsname = amsname;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setMonthlysales(Integer monthlysales) {
        this.monthlysales = monthlysales;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public IntroMerchandise(){

    }


}
