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
@TableName("t_allmcredit")
public class Allmcredit implements Serializable {

    private static final long serialVersionUID = 1L;

    private String amcname;

    private Float price;

    private String link;

    private Integer monthlysales;

    private String platform;

    private String amcimage;

    private Float creditscore;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAmcname() {
        return amcname;
    }

    public void setAmcname(String amcname) {
        this.amcname = amcname;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
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

    public String getAmcimage() {
        return amcimage;
    }

    public void setAmcimage(String amcimage) {
        this.amcimage = amcimage;
    }

    public Float getCreditscore() {
        return creditscore;
    }

    public void setCreditscore(Float creditscore) {
        this.creditscore = creditscore;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
