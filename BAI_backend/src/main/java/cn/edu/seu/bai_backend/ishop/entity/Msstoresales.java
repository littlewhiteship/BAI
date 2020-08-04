package cn.edu.seu.bai_backend.ishop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author nqr
 * @since 2020-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_msstoresales")
public class Msstoresales implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "msssid", type = IdType.AUTO)
    private Integer msssid;

    private String msssurl;

    private Float msssdescriptionpoints;

    private Float mssslogisticsspeedpoints;

    private Float msssservepoints;

    private String msssshopname;

    private Integer msssshopid;

    private String mssstype;

    private Float mssssalesscore;

    private String msssimage;

    //public Integer getMsssid() {return msssid;}

    //public String getMsssurl() {return msssurl;}

    //public Float getMsssdescriptionpoints() {return msssdescriptionpoints;}

    //public Float getMssslogisticsspeedpoints() {return mssslogisticsspeedpoints;}

    //public Float getMsssservepoints() {return msssservepoints;}

    //public String getMsssshopname() {return msssshopname;}

    //public Integer getMsssshopid() {return msssshopid;}

    //public String getMssstype() {return mssstype;}

    //public String getMsssimage() {return msssimage;}

    //public Float getMssssalesscore() {return mssssalesscore;}

    //public void setMssssalesscore(Float mssssalesscore) {this.mssssalesscore = mssssalesscore;}


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getMsssid() {
        return msssid;
    }

    public void setMsssid(Integer msssid) {
        this.msssid = msssid;
    }

    public String getMsssurl() {
        return msssurl;
    }

    public void setMsssurl(String msssurl) {
        this.msssurl = msssurl;
    }

    public Float getMsssdescriptionpoints() {
        return msssdescriptionpoints;
    }

    public void setMsssdescriptionpoints(Float msssdescriptionpoints) {
        this.msssdescriptionpoints = msssdescriptionpoints;
    }

    public Float getMssslogisticsspeedpoints() {
        return mssslogisticsspeedpoints;
    }

    public void setMssslogisticsspeedpoints(Float mssslogisticsspeedpoints) {
        this.mssslogisticsspeedpoints = mssslogisticsspeedpoints;
    }

    public Float getMsssservepoints() {
        return msssservepoints;
    }

    public void setMsssservepoints(Float msssservepoints) {
        this.msssservepoints = msssservepoints;
    }

    public String getMsssshopname() {
        return msssshopname;
    }

    public void setMsssshopname(String msssshopname) {
        this.msssshopname = msssshopname;
    }

    public Integer getMsssshopid() {
        return msssshopid;
    }

    public void setMsssshopid(Integer msssshopid) {
        this.msssshopid = msssshopid;
    }

    public String getMssstype() {
        return mssstype;
    }

    public void setMssstype(String mssstype) {
        this.mssstype = mssstype;
    }

    public Float getMssssalesscore() {
        return mssssalesscore;
    }

    public void setMssssalesscore(Float mssssalesscore) {
        this.mssssalesscore = mssssalesscore;
    }

    public String getMsssimage() {
        return msssimage;
    }

    public void setMsssimage(String msssimage) {
        this.msssimage = msssimage;
    }
}