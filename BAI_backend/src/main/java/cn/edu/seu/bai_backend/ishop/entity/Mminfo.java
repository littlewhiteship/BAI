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
@TableName("t_mminfo")
public class Mminfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "mmiid", type = IdType.AUTO)
    private Integer mmiid;

    private String mmiurl;

    private Float mmiprice;

    private String mminame;

    private Integer mmisalesvolume;

    private Integer mmishopid;

    private String mmiimg;

    //public Float getMmiprice() { return mmiprice; }

    //public Integer getMmisalesvolume() {return mmisalesvolume;}


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getMmiid() {
        return mmiid;
    }

    public void setMmiid(Integer mmiid) {
        this.mmiid = mmiid;
    }

    public String getMmiurl() {
        return mmiurl;
    }

    public void setMmiurl(String mmiurl) {
        this.mmiurl = mmiurl;
    }

    public Float getMmiprice() {
        return mmiprice;
    }

    public void setMmiprice(Float mmiprice) {
        this.mmiprice = mmiprice;
    }

    public String getMminame() {
        return mminame;
    }

    public void setMminame(String mminame) {
        this.mminame = mminame;
    }

    public Integer getMmisalesvolume() {
        return mmisalesvolume;
    }

    public void setMmisalesvolume(Integer mmisalesvolume) {
        this.mmisalesvolume = mmisalesvolume;
    }

    public Integer getMmishopid() {
        return mmishopid;
    }

    public void setMmishopid(Integer mmishopid) {
        this.mmishopid = mmishopid;
    }

    public String getMmiimg() {
        return mmiimg;
    }

    public void setMmiimg(String mmiimg) {
        this.mmiimg = mmiimg;
    }
}