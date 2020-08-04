package cn.edu.seu.bai_backend.my.service;

import cn.edu.seu.bai_backend.ishare.entity.Post;
import cn.edu.seu.bai_backend.my.entity.Postfavorite;
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
public interface IPostfavoriteService extends IService<Postfavorite> {
    List<Post> checkPostList(String account);
    //查看收藏帖子功能，参数为账号，返回收藏列表。
    List<Post> deletePostList(String account, Integer postid);
    //删除收藏的帖子功能，参数账号，删除帖子的id。
    void addFavPostList(String account,Integer postid);
    //添加收藏帖子功能，参数账号，帖子信息。
}
