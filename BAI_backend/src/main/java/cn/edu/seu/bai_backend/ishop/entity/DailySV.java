package cn.edu.seu.bai_backend.ishop.entity;

import java.time.LocalDate;

public class DailySV {

    private Integer mmdisalesvolume;

    private LocalDate mmdidate;

    public LocalDate getMmdidate() {
        return mmdidate;
    }

    public Integer getMmdisalesvolume() {
        return mmdisalesvolume;
    }

    public DailySV(){

    }

    public void setDailySV(Integer mmdisalesvolume,LocalDate mmdidate){
        this.mmdisalesvolume = mmdisalesvolume;
        this.mmdidate = mmdidate;
    }

    public void setMmdisalesvolume(Integer mmdisalesvolume) {
        this.mmdisalesvolume = mmdisalesvolume;
    }

    public void setMmdidate(LocalDate mmdidate) {
        this.mmdidate = mmdidate;
    }
}
