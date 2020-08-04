package cn.edu.seu.bai_backend.my.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@TableName("t_customerfavorite")
public class Customerfavorite implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cffavid", type = IdType.AUTO)
    private Integer cffavid;

    private LocalDateTime cftime;

    private String cfimage;

    private String cfmname;

    private String cfmurl;

    private Float cfmprice;

    private String cfmplatform;

    private String cfuseraccount;

    public static long getSerialVersionUID() {

        return serialVersionUID;
    }

    public Integer getCffavid() {

        return cffavid;
    }

    public void setCffavid(Integer cffavid) {

        this.cffavid = cffavid;
    }

    public LocalDateTime getCftime() {

        return cftime;
    }

    public void setCftime(LocalDateTime cftime) {

        this.cftime = cftime;
    }

    public String getCfimage() {

        return cfimage;
    }

    public void setCfimage(String cfimage) {

        this.cfimage = cfimage;
    }

    public String getCfmname() {

        return cfmname;
    }

    public void setCfmname(String cfmname) {

        this.cfmname = cfmname;
    }

    public String getCfmurl() {

        return cfmurl;
    }

    public void setCfmurl(String cfmurl) {

        this.cfmurl = cfmurl;
    }

    public Float getCfmprice() {

        return cfmprice;
    }

    public void setCfmprice(Float cfmprice) {
        this.cfmprice = cfmprice;
    }

    public String getCfmplatform() {

        return cfmplatform;
    }

    public void setCfmplatform(String cfmplatform) {

        this.cfmplatform = cfmplatform;
    }

    public String getCfuseraccount() {

        return cfuseraccount;
    }

    public void setCfuseraccount(String cfuseraccount) {

        this.cfuseraccount = cfuseraccount;
    }
}