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
 * @author songyifan
 * @since 2020-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_msstorecredit")
public class Msstorecredit implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "msscid", type = IdType.AUTO)
    private Integer msscid;

    private String msscurl;

    private Float msscdescriptionpoints;

    private Float mssclogisticsspeedpoints;

    private Float msscservepoints;

    private String msscshopname;

    private Integer msscshopid;

    private String mssctype;

    private Float mssccreditscore;

    private String msscimage;

    public void setMsstorecredit(Integer id,String url,Float descriptionpoints,Float logisticsspeedpoints,Float servepoints,String shopname,Integer shopid,String type,Float creditscore,String image)
    {
        msscid=id;
        msscurl=url;
        msscdescriptionpoints=descriptionpoints;
        mssclogisticsspeedpoints=logisticsspeedpoints;
        msscservepoints=servepoints;
        msscshopname=shopname;
        msscshopid=shopid;
        mssctype=type;
        mssccreditscore=creditscore;
        msscimage=image;
    }

    public Msstorecredit() { }

    //public Float getMsscservepoints() {return msscservepoints;}

    //public void setMssccreditscore(Float mssccreditscore) {this.mssccreditscore = mssccreditscore;}

    //public Float getMssccreditscore() {return mssccreditscore;}

    //public String getMsscshopname() {return msscshopname;}

    //public Float getMsscdescriptionpoints() {return msscdescriptionpoints;}

    //public Float getMssclogisticsspeedpoints() {return mssclogisticsspeedpoints;}

    //public Integer getMsscid() {return msscid;}

    //public Integer getMsscshopid() {return msscshopid;}

    //public String getMsscimage() {return msscimage;}

    //public String getMssctype() {return mssctype;}

    //public String getMsscurl() {return msscurl;}


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getMsscid() {
        return msscid;
    }

    public void setMsscid(Integer msscid) {
        this.msscid = msscid;
    }

    public String getMsscurl() {
        return msscurl;
    }

    public void setMsscurl(String msscurl) {
        this.msscurl = msscurl;
    }

    public Float getMsscdescriptionpoints() {
        return msscdescriptionpoints;
    }

    public void setMsscdescriptionpoints(Float msscdescriptionpoints) {
        this.msscdescriptionpoints = msscdescriptionpoints;
    }

    public Float getMssclogisticsspeedpoints() {
        return mssclogisticsspeedpoints;
    }

    public void setMssclogisticsspeedpoints(Float mssclogisticsspeedpoints) {
        this.mssclogisticsspeedpoints = mssclogisticsspeedpoints;
    }

    public Float getMsscservepoints() {
        return msscservepoints;
    }

    public void setMsscservepoints(Float msscservepoints) {
        this.msscservepoints = msscservepoints;
    }

    public String getMsscshopname() {
        return msscshopname;
    }

    public void setMsscshopname(String msscshopname) {
        this.msscshopname = msscshopname;
    }

    public Integer getMsscshopid() {
        return msscshopid;
    }

    public void setMsscshopid(Integer msscshopid) {
        this.msscshopid = msscshopid;
    }

    public String getMssctype() {
        return mssctype;
    }

    public void setMssctype(String mssctype) {
        this.mssctype = mssctype;
    }

    public Float getMssccreditscore() {
        return mssccreditscore;
    }

    public void setMssccreditscore(Float mssccreditscore) {
        this.mssccreditscore = mssccreditscore;
    }

    public String getMsscimage() {
        return msscimage;
    }

    public void setMsscimage(String msscimage) {
        this.msscimage = msscimage;
    }
}
