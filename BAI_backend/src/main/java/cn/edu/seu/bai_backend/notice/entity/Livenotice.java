package cn.edu.seu.bai_backend.notice.entity;

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
 * @since 2020-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_livenotice")
public class Livenotice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "noticekey", type = IdType.AUTO)
    private Integer noticekey;

    private LocalDateTime livetime;

    private String livername;

    private String merchandisetype;

    private String url;

    private LocalDateTime noticetime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getNoticekey() {
        return noticekey;
    }

    public void setNoticekey(Integer noticekey) {
        this.noticekey = noticekey;
    }

    public LocalDateTime getLivetime() {
        return livetime;
    }

    public void setLivetime(LocalDateTime livetime) {
        this.livetime = livetime;
    }

    public String getLivername() {
        return livername;
    }

    public void setLivername(String livername) {
        this.livername = livername;
    }

    public String getMerchandisetype() {
        return merchandisetype;
    }

    public void setMerchandisetype(String merchandisetype) {
        this.merchandisetype = merchandisetype;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getNoticetime() {
        return noticetime;
    }

    public void setNoticetime(LocalDateTime noticetime) {
        this.noticetime = noticetime;
    }
}
