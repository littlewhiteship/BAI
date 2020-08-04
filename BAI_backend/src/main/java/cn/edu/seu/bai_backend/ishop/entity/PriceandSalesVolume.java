package cn.edu.seu.bai_backend.ishop.entity;

public class PriceandSalesVolume {
    private Float mmdiprice;

    private Integer mmdisalesvolume;

    public PriceandSalesVolume(){

    }

    public void setPriceandSalesVolume(Float mmdiprice,Integer mmdisalesvolume){
        this.mmdiprice = mmdiprice;
        this.mmdisalesvolume = mmdisalesvolume;
    }

    public Float getMmdiprice() {
        return mmdiprice;
    }

    public Integer getMmdisalesvolume() {
        return mmdisalesvolume;
    }

    public void setMmdiprice(Float mmdiprice) {
        this.mmdiprice = mmdiprice;
    }

    public void setMmdisalesvolume(Integer mmdisalesvolume) {
        this.mmdisalesvolume = mmdisalesvolume;
    }
}
