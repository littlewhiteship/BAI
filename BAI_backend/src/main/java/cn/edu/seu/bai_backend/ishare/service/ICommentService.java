package cn.edu.seu.bai_backend.ishare.service;

import cn.edu.seu.bai_backend.ishare.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author songyifan
 * @since 2020-07-20
 */
public interface ICommentService extends IService<Comment> {

    List<Comment> addcomment(String account, Integer postid, String content);

    List<Comment> checkcomment(Integer postid);

    List<Comment> deletecomment(Integer commentid);
}
