package cn.edu.seu.bai_backend.notice.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2020-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_postnotice")
public class Postnotice implements Serializable {

    private static final long serialVersionUID = 1L;

    private String pntype;

    private String usernickname;

    private String receiveraccount;

    private LocalDateTime noticetime;

    private String content;

    @TableId(value = "noticekey", type = IdType.AUTO)
    private Integer noticekey;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPntype() {
        return pntype;
    }

    public void setPntype(String pntype) {
        this.pntype = pntype;
    }

    public String getUsernickname() {
        return usernickname;
    }

    public void setUsernickname(String usernickname) {
        this.usernickname = usernickname;
    }

    public String getReceiveraccount() {
        return receiveraccount;
    }

    public void setReceiveraccount(String receiveraccount) {
        this.receiveraccount = receiveraccount;
    }

    public LocalDateTime getNoticetime() {
        return noticetime;
    }

    public void setNoticetime(LocalDateTime noticetime) {
        this.noticetime = noticetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getNoticekey() {
        return noticekey;
    }

    public void setNoticekey(Integer noticekey) {
        this.noticekey = noticekey;
    }
}
