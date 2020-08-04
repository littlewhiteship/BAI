package cn.edu.seu.bai_backend.ibuy.entity;

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
@TableName("t_allmprice")
public class Allmprice implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ampname;

    private Float ampprice;

    private String link;

    private Integer monthlysales;

    private String platform;

    private String ampimage;

    private Float pricescore;

    @TableId(value = "ampid", type = IdType.AUTO)
    private Integer ampid;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAmpname() {
        return ampname;
    }

    public void setAmpname(String ampname) {
        this.ampname = ampname;
    }

    public Float getAmpprice() {
        return ampprice;
    }

    public void setAmpprice(Float ampprice) {
        this.ampprice = ampprice;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getMonthlysales() {
        return monthlysales;
    }

    public void setMonthlysales(Integer monthlysales) {
        this.monthlysales = monthlysales;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getAmpimage() {
        return ampimage;
    }

    public void setAmpimage(String ampimage) {
        this.ampimage = ampimage;
    }

    public Float getPricescore() {
        return pricescore;
    }

    public void setPricescore(Float pricescore) {
        this.pricescore = pricescore;
    }

    public Integer getAmpid() {
        return ampid;
    }

    public void setAmpid(Integer ampid) {
        this.ampid = ampid;
    }
}
