package cn.edu.seu.bai_backend.my.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("t_postfavorite")
public class Postfavorite implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "pffavid", type = IdType.AUTO)
    private Integer pffavid;

    private Integer pfid;

    private LocalDateTime pftime;

    private String pfaccount;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getPffavid() {
        return pffavid;
    }

    public void setPffavid(Integer pffavid) {
        this.pffavid = pffavid;
    }

    public Integer getPfid() {
        return pfid;
    }

    public void setPfid(Integer pfid) {
        this.pfid = pfid;
    }

    public LocalDateTime getPftime() {
        return pftime;
    }

    public void setPftime(LocalDateTime pftime) {
        this.pftime = pftime;
    }

    public String getPfaccount() {
        return pfaccount;
    }

    public void setPfaccount(String pfaccount) {
        this.pfaccount = pfaccount;
    }
}
