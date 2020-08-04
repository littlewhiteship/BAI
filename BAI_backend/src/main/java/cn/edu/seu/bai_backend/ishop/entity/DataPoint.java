package cn.edu.seu.bai_backend.ishop.entity;

public class DataPoint {
    public float x;
    public float y;
    public DataPoint(float x,float y){  //DataPoint类的构造函数
        this.x = x;
        this.y = y;
    }

    public DataPoint(){

    }

    public void setDataPoint(float x,float y){
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
