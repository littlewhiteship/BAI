package cn.edu.seu.bai_backend.ishare.service;

import cn.edu.seu.bai_backend.ishare.entity.Post;
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
public interface IPostService extends IService<Post> {

    void posting(Post post);

    List<Post> getpostlist();

    List<Post> searchpost(String keyword);

    Post checkpostdetail(Integer postid);

    List<Post> deletepost(String account, Integer postid);

    Post report(Integer postid);

    Post likepost(Integer postid, String account);

    Post cancellike(Integer postid);
}
