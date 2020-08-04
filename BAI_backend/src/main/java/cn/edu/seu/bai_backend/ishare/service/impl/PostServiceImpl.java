package cn.edu.seu.bai_backend.ishare.service.impl;

import cn.edu.seu.bai_backend.ishare.entity.Comment;
import cn.edu.seu.bai_backend.ishare.entity.Post;
import cn.edu.seu.bai_backend.ishare.entity.Reply;
import cn.edu.seu.bai_backend.ishare.mapper.PostMapper;
import cn.edu.seu.bai_backend.ishare.service.ICommentService;
import cn.edu.seu.bai_backend.ishare.service.IPostService;
import cn.edu.seu.bai_backend.ishare.service.IReplyService;
import cn.edu.seu.bai_backend.my.entity.Postfavorite;
import cn.edu.seu.bai_backend.my.entity.Userinfo;
import cn.edu.seu.bai_backend.my.service.IPostfavoriteService;
import cn.edu.seu.bai_backend.my.service.IUserinfoService;
import cn.edu.seu.bai_backend.notice.entity.Postnotice;
import cn.edu.seu.bai_backend.notice.service.IPostnoticeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.model.IComment;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author songyifan
 * @since 2020-07-20
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements IPostService {

    @Autowired
    private IUserinfoService iUserinfoService;

    @Autowired
    private IPostfavoriteService iPostfavoriteService;

    @Autowired
    private IPostnoticeService iPostnoticeService;

    @Autowired
    private ICommentService iCommentService;

    @Autowired
    private IReplyService iReplyService;

    @Override
    public void posting(Post post) {
        save(post);
    }

    @Override
    public List<Post> getpostlist() {
        QueryWrapper<Post> postlist = new QueryWrapper<>();
        postlist.orderByDesc("postkey");
        List<Post> result = list(postlist);
        return result;
    }

    @Override
    public List<Post> searchpost(String keyword) {
        QueryWrapper<Post> postQueryWrapper = new QueryWrapper<>();
        postQueryWrapper.like("postnickname",keyword).or().like("merchandisename",keyword)
                .or().like("postcontent",keyword);
        return list(postQueryWrapper.orderByDesc("likenum"));
    }

    @Override
    public Post checkpostdetail(Integer postid) {
        QueryWrapper<Post> post = new QueryWrapper<>();
        post.eq("postkey",postid);
        return getOne(post);
    }

    @Override
    public List<Post> deletepost(String account, Integer postid) {
        QueryWrapper<Userinfo>user = new QueryWrapper<>();
        user.eq("uiaccount",account);
        if((iUserinfoService.getOne(user).getUiidenfication()==0) ||
                (getById(postid).getPostaccount().equals(account))) {

            //删除与该贴有关的所有收藏记录
            QueryWrapper<Postfavorite> postfavoriteQueryWrapper = new QueryWrapper<>();
            postfavoriteQueryWrapper.eq("pfid",postid);
            iPostfavoriteService.remove(postfavoriteQueryWrapper);

            //删除该帖子
            removeById(postid);

            //删除该贴所有评论回复
            QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
            commentQueryWrapper.eq("postkey",postid);
            iCommentService.remove(commentQueryWrapper);
            QueryWrapper<Reply> replyQueryWrapper = new QueryWrapper<>();
            replyQueryWrapper.eq("postkey",postid);
            iReplyService.remove(replyQueryWrapper);

            //返回帖子列表
            QueryWrapper<Post> postQueryWrapper = new QueryWrapper<>();
            postQueryWrapper.orderByDesc("postkey");
            return list(postQueryWrapper);
        }else{
            return null;
        }
    }

    @Override
    public Post report(Integer postid) {
        UpdateWrapper<Post> report = new UpdateWrapper<>();
        Post post = getById(postid);
        report.eq("postkey",postid).set("complaintnum", 1 + post.getComplaintnum());
        update(report);
        return getById(postid);
    }

    @Override
    public Post likepost(Integer postid, String account) {
        UpdateWrapper<Post> like = new UpdateWrapper<>();
        Post post = getById(postid);
        like.eq("postkey",postid).set("likenum",1 + post.getLikenum());
        update(like);
        //给被点赞用户发送通知
        QueryWrapper<Userinfo> likeper = new QueryWrapper<>();
        likeper.eq("uiaccount",account);
        Postnotice postnotice = new Postnotice();
        postnotice.setPntype("点赞");
        postnotice.setUsernickname(iUserinfoService.getOne(likeper).getUinickname());
        postnotice.setReceiveraccount(getById(postid).getPostaccount());
        postnotice.setNoticetime(LocalDateTime.now());
        iPostnoticeService.save(postnotice);
        return getById(postid);
    }

    @Override
    public Post cancellike(Integer postid) {
        UpdateWrapper<Post> like = new UpdateWrapper<>();
        Post post = getById(postid);
        like.eq("postkey",postid).set("likenum",post.getLikenum() - 1);
        update(like);
        return getById(postid);
    }

}