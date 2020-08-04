package cn.edu.seu.bai_backend.ishare.entity;

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
@TableName("t_comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getPostkey() {
        return postkey;
    }

    public void setPostkey(Integer postkey) {
        this.postkey = postkey;
    }

    public String getCommentatoraccount() {
        return commentatoraccount;
    }

    public String getCommentatornickname() {
        return commentatornickname;
    }

    public void setCommentatornickname(String commentatornickname) {
        this.commentatornickname = commentatornickname;
    }

    public String getCommentatorimage() {
        return commentatorimage;
    }

    public void setCommentatorimage(String commentatorimage) {
        this.commentatorimage = commentatorimage;
    }

    public String getCommentcontent() {
        return commentcontent;
    }

    public void setCommentcontent(String commentcontent) {
        this.commentcontent = commentcontent;
    }

    public LocalDateTime getCommenttime() {
        return commenttime;
    }

    public void setCommenttime(LocalDateTime commenttime) {
        this.commenttime = commenttime;
    }

    public Integer getCommentkey() {
        return commentkey;
    }

    public void setCommentkey(Integer commentkey) {
        this.commentkey = commentkey;
    }

    private Integer postkey;

    private String commentatoraccount;

    private String commentatornickname;

    private String commentatorimage;

    private String commentcontent;

    private LocalDateTime commenttime;

    @TableId(value = "commentkey", type = IdType.AUTO)
    private Integer commentkey;

    public void setCommentatoraccount(String commentatoraccount) {
        this.commentatoraccount = commentatoraccount;
    }
}
