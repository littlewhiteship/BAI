package cn.edu.seu.bai_backend.ishop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

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
@TableName("t_mmdetailedinfo")
public class Mmdetailedinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "mmdiid", type = IdType.AUTO)
    private Integer mmdiid;

    private String mmdiname;

    private LocalDate mmdidate;

    private Float mmdiprice;

    private Integer mmdisalesvolume;

    private Integer mmdimmid;

    public Float getMmdiprice() {
        return mmdiprice;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getMmdiid() {
        return mmdiid;
    }

    public void setMmdiid(Integer mmdiid) {
        this.mmdiid = mmdiid;
    }

    public String getMmdiname() {
        return mmdiname;
    }

    public void setMmdiname(String mmdiname) {
        this.mmdiname = mmdiname;
    }

    public LocalDate getMmdidate() {
        return mmdidate;
    }

    public void setMmdidate(LocalDate mmdidate) {
        this.mmdidate = mmdidate;
    }

    public void setMmdiprice(Float mmdiprice) {
        this.mmdiprice = mmdiprice;
    }

    public Integer getMmdisalesvolume() {
        return mmdisalesvolume;
    }

    public void setMmdisalesvolume(Integer mmdisalesvolume) {
        this.mmdisalesvolume = mmdisalesvolume;
    }

    public Integer getMmdimmid() {
        return mmdimmid;
    }

    public void setMmdimmid(Integer mmdimmid) {
        this.mmdimmid = mmdimmid;
    }
}
