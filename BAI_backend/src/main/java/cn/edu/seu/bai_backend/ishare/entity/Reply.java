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
@TableName("t_reply")
public class Reply implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer postkey;

    private Integer commentkey;

    private String replieraccount;

    private String repliernickname;

    private String replierimage;

    private String replycontent;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getPostkey() {
        return postkey;
    }

    public void setPostkey(Integer postkey) {
        this.postkey = postkey;
    }

    public Integer getCommentkey() {
        return commentkey;
    }

    public void setCommentkey(Integer commentkey) {
        this.commentkey = commentkey;
    }

    public String getReplieraccount() {
        return replieraccount;
    }

    public void setReplieraccount(String replieraccount) {
        this.replieraccount = replieraccount;
    }

    public String getRepliernickname() {
        return repliernickname;
    }

    public void setRepliernickname(String repliernickname) {
        this.repliernickname = repliernickname;
    }

    public String getReplierimage() {
        return replierimage;
    }

    public void setReplierimage(String replierimage) {
        this.replierimage = replierimage;
    }

    public String getReplycontent() {
        return replycontent;
    }

    public void setReplycontent(String replycontent) {
        this.replycontent = replycontent;
    }

    public LocalDateTime getReplytime() {
        return replytime;
    }

    public void setReplytime(LocalDateTime replytime) {
        this.replytime = replytime;
    }

    public Integer getReplykey() {
        return replykey;
    }

    public void setReplykey(Integer replykey) {
        this.replykey = replykey;
    }

    private LocalDateTime replytime;

    @TableId(value = "replykey", type = IdType.AUTO)
    private Integer replykey;

}
