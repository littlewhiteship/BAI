package cn.edu.seu.bai_backend.ishare.service.impl;

import cn.edu.seu.bai_backend.ishare.entity.Comment;
import cn.edu.seu.bai_backend.ishare.entity.Post;
import cn.edu.seu.bai_backend.ishare.entity.Reply;
import cn.edu.seu.bai_backend.ishare.mapper.CommentMapper;
import cn.edu.seu.bai_backend.ishare.service.ICommentService;
import cn.edu.seu.bai_backend.ishare.service.IPostService;
import cn.edu.seu.bai_backend.ishare.service.IReplyService;
import cn.edu.seu.bai_backend.my.entity.Userinfo;
import cn.edu.seu.bai_backend.my.service.IUserinfoService;
import cn.edu.seu.bai_backend.notice.entity.Postnotice;
import cn.edu.seu.bai_backend.notice.service.IPostnoticeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Autowired
    private IUserinfoService iUserinfoService;

    @Autowired
    private IPostService iPostService;

    @Autowired
    private IReplyService iReplyService;

    @Autowired
    private IPostnoticeService iPostnoticeService;

    @Override
    public List<Comment> addcomment(String account, Integer postid, String content) {

        //获取评论人信息
        QueryWrapper<Userinfo> user = new QueryWrapper<>();
        user.eq("uiaccount",account);
        Userinfo commentor = iUserinfoService.getOne(user);

        //添加一条评论信息
        Comment newcom = new Comment();
        newcom.setCommentatoraccount(account);
        newcom.setCommentatorimage(commentor.getUiimage());
        newcom.setPostkey(postid);
        newcom.setCommentatornickname(commentor.getUinickname()) ;
        newcom.setCommentcontent(content);
        newcom.setCommenttime(LocalDateTime.now());
        save(newcom);

        //原帖子评论数+1
        UpdateWrapper<Post> postUpdateWrapper = new UpdateWrapper<>();
        postUpdateWrapper.eq("postkey",postid).set("commentnum",
                iPostService.getById(postid).getCommentnum() + 1);
        iPostService.update(postUpdateWrapper);

        //返回该帖子的评论列表
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("postkey",postid).orderByDesc("commentkey");
        List<Comment> commentslist = list(commentQueryWrapper);

        //通知原po主被评论
        Postnotice comnotice = new Postnotice();
        comnotice.setPntype("评论");
        comnotice.setUsernickname(commentor.getUinickname());
        comnotice.setReceiveraccount(iPostService.getById(postid).getPostaccount());
        comnotice.setNoticetime(LocalDateTime.now());
        comnotice.setContent(content);
        iPostnoticeService.save(comnotice);

        return commentslist;
    }

    @Override
    public List<Comment> checkcomment(Integer postid) {
        QueryWrapper<Comment> clist = new QueryWrapper<>();
        clist.eq("postkey",postid).orderByDesc("commentkey");
        List<Comment> commentslist = list(clist);
        return commentslist;
    }

    @Override
    public List<Comment> deletecomment(Integer commentid) {
        //清除该评论下的回复
        QueryWrapper<Reply> replyQueryWrapper = new QueryWrapper<>();
        replyQueryWrapper.eq("commentkey",commentid);
        iReplyService.remove(replyQueryWrapper);

        //获取评论原帖信息
        Integer postid = getById(commentid).getPostkey();
        Post post = iPostService.getById(postid);

        //清除评论，帖子评论数-1
        removeById(commentid);
        UpdateWrapper<Post> postUpdateWrapper = new UpdateWrapper<>();
        postUpdateWrapper.eq("postkey",postid).set("commentnum",post.getCommentnum() - 1);
        iPostService.update(postUpdateWrapper);

        //删除该条评论
        removeById(commentid);

        //返回原帖评论列表
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("postkey",postid).orderByDesc("commentkey");
        List<Comment> comlist = list(commentQueryWrapper);
        return comlist;
    }

}