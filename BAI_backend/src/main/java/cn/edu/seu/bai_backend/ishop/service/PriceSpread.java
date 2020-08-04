package cn.edu.seu.bai_backend.ishop.service;

public class PriceSpread {

    private int price;

    private int salesVolume;

    public PriceSpread() {

    }

    public void setPriceSpread(int tprice,int tsalesVolume){
        price = tprice;
        salesVolume = tsalesVolume;
    }

    public PriceSpread(int tprice,int tsalesVolume){
        this.price = tprice;
        this.salesVolume = tsalesVolume;
    }

    public int getPrice() {
        return price;
    }

    public int getSalesVolume() {
        return salesVolume;
    }
}
