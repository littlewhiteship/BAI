package cn.edu.seu.bai_backend.ishare.entity;

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
 * @since 2020-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_post")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    private String postaccount;

    private String postnickname;

    private String postimage;

    private Integer likenum;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPostaccount() {
        return postaccount;
    }

    public void setPostaccount(String postaccount) {
        this.postaccount = postaccount;
    }

    public String getPostnickname() {
        return postnickname;
    }

    public void setPostnickname(String postnickname) {
        this.postnickname = postnickname;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public Integer getLikenum() {
        return likenum;
    }

    public void setLikenum(Integer likenum) {
        this.likenum = likenum;
    }

    public Integer getCommentnum() {
        return commentnum;
    }

    public void setCommentnum(Integer commentnum) {
        this.commentnum = commentnum;
    }

    public Integer getComplaintnum() {
        return complaintnum;
    }

    public void setComplaintnum(Integer complaintnum) {
        this.complaintnum = complaintnum;
    }

    public String getMerchandisename() {
        return merchandisename;
    }

    public void setMerchandisename(String merchandisename) {
        this.merchandisename = merchandisename;
    }

    public String getPostcontent() {
        return postcontent;
    }

    public void setPostcontent(String postcontent) {
        this.postcontent = postcontent;
    }

    public String getMerchandiselink() {
        return merchandiselink;
    }

    public void setMerchandiselink(String merchandiselink) {
        this.merchandiselink = merchandiselink;
    }

    public LocalDateTime getPosttime() {
        return posttime;
    }

    public void setPosttime(LocalDateTime posttime) {
        this.posttime = posttime;
    }

    public String getMerchandisepicpath() {
        return merchandisepicpath;
    }

    public void setMerchandisepicpath(String merchandisepicpath) {
        this.merchandisepicpath = merchandisepicpath;
    }

    private Integer commentnum;

    private Integer complaintnum;

    private String merchandisename;

    private String postcontent;

    private String merchandiselink;

    private LocalDateTime posttime;

    private String merchandisepicpath;

    @TableId(value = "postkey", type = IdType.AUTO)
    private Integer postkey;

}
