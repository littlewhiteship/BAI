package cn.edu.seu.bai_backend.my.entity;

import cn.edu.seu.bai_backend.my.service.IMerchantextrainfoService;
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
@TableName("t_merchantextrainfo")
public class Merchantextrainfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String memerchandisetype;

    private String meshoplink;

    private Float meshopcredit;

    private String meshopplatform;

    @TableId(value = "meid", type = IdType.AUTO)
    private Integer meid;

    private String meuseraccount;

    private Float memaxprice;

    private Float meminprice;

    public Merchantextrainfo(){}

    public static long getSerialVersionUID() {

        return serialVersionUID;
    }

    public String getMemerchandisetype() {

        return memerchandisetype;
    }

    public void setMemerchandisetype(String memerchandisetype) {

        this.memerchandisetype = memerchandisetype;
    }

    public String getMeshoplink() {

        return meshoplink;
    }

    public void setMeshoplink(String meshoplink) {

        this.meshoplink = meshoplink;
    }

    public Float getMeshopcredit() {

        return meshopcredit;
    }

    public void setMeshopcredit(Float meshopcredit) {

        this.meshopcredit = meshopcredit;
    }

    public String getMeshopplatform() {

        return meshopplatform;
    }

    public void setMeshopplatform(String meshopplatform) {

        this.meshopplatform = meshopplatform;
    }

    public Integer getMeid() {

        return meid;
    }

    public void setMeid(Integer meid) {

        this.meid = meid;
    }

    public String getMeuseraccount() {

        return meuseraccount;
    }

    public void setMeuseraccount(String meuseraccount) {

        this.meuseraccount = meuseraccount;
    }

    public Float getMemaxprice() {

        return memaxprice;
    }

    public void setMemaxprice(Float memaxprice) {

        this.memaxprice = memaxprice;
    }

    public Float getMeminprice() {

        return meminprice;
    }

    public void setMeminprice(Float meminprice) {

        this.meminprice = meminprice;
    }
}