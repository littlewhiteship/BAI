package cn.edu.seu.bai_backend.my.entity;

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
@TableName("t_userinfo")
public class Userinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uiaccount;

    private String uipassword;

    private String uinickname;

    private String uiimage;

    private Integer uiidenfication;
    //用户身份信息，0表示管理员，1表示顾客，2表示商家。

    @TableId(value = "uiid", type = IdType.AUTO)
    private Integer uiid;

    public Userinfo(){

    }

    public static long getSerialVersionUID() {

        return serialVersionUID;
    }

    public String getUiaccount() {

        return uiaccount;
    }

    public void setUiaccount(String uiaccount) {

        this.uiaccount = uiaccount;
    }

    public String getUipassword() {

        return uipassword;
    }

    public void setUipassword(String uipassword) {

        this.uipassword = uipassword;
    }

    public String getUinickname() {

        return uinickname;
    }

    public void setUinickname(String uinickname) {

        this.uinickname = uinickname;
    }

    public String getUiimage() {

        return uiimage;
    }

    public void setUiimage(String uiimage) {

        this.uiimage = uiimage;
    }

    public Integer getUiidenfication() {

        return uiidenfication;
    }

    public void setUiidenfication(Integer uiidenfication) {

        this.uiidenfication = uiidenfication;
    }

    public Integer getUiid() {

        return uiid;
    }

    public void setUiid(Integer uiid) {

        this.uiid = uiid;
    }
}