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
@TableName("t_allmsales")
public class Allmsales implements Serializable {

    private static final long serialVersionUID = 1L;

    private String amsname;

    private Float price;

    private String link;

    private Integer monthlysales;

    private String platform;

    private String amsimage;

    private Float salesscore;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAmsname() {
        return amsname;
    }

    public Float getPrice() {
        return price;
    }

    public String getLink() {
        return link;
    }

    public Integer getMonthlysales() {
        return monthlysales;
    }

    public String getPlatform() {
        return platform;
    }

    public String getAmsimage() {
        return amsimage;
    }

    public Float getSalesscore() {
        return salesscore;
    }

    public Integer getId() {
        return id;
    }

    public void setAmsname(String amsname) {
        this.amsname = amsname;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setMonthlysales(Integer monthlysales) {
        this.monthlysales = monthlysales;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setAmsimage(String amsimage) {
        this.amsimage = amsimage;
    }

    public void setSalesscore(Float salesscore) {
        this.salesscore = salesscore;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
