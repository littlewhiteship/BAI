package cn.edu.seu.bai_backend.ishare.service.impl;

import cn.edu.seu.bai_backend.ishare.entity.Reply;
import cn.edu.seu.bai_backend.ishare.mapper.ReplyMapper;
import cn.edu.seu.bai_backend.ishare.service.ICommentService;
import cn.edu.seu.bai_backend.ishare.service.IPostService;
import cn.edu.seu.bai_backend.ishare.service.IReplyService;
import cn.edu.seu.bai_backend.my.service.IUserinfoService;
import cn.edu.seu.bai_backend.notice.entity.Postnotice;
import cn.edu.seu.bai_backend.notice.service.IPostnoticeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, Reply> implements IReplyService {

    @Autowired
    private ICommentService iCommentService;

    @Autowired
    private IPostnoticeService iPostnoticeService;

    @Override
    public Reply replying(String account,String nickname,String image, Integer commentid, String content) {
        //新增一条回复记录并返回
        Reply replyinfo = new Reply();
        replyinfo.setPostkey(iCommentService.getById(commentid).getPostkey());
        replyinfo.setCommentkey(commentid);
        replyinfo.setReplieraccount(account);
        replyinfo.setReplierimage(image);
        replyinfo.setRepliernickname(nickname);
        replyinfo.setReplycontent(content);
        replyinfo.setReplytime(LocalDateTime.now());
        save(replyinfo);

        //通知用户被回复
        Postnotice newpn = new Postnotice();
        newpn.setContent(content);
        newpn.setNoticetime(LocalDateTime.now());
        newpn.setPntype("回复");
        newpn.setUsernickname(nickname);
        newpn.setReceiveraccount(iCommentService.getById(commentid).getCommentatoraccount());
        iPostnoticeService.save(newpn);

        return replyinfo;
    }

    @Override
    public List<Reply> checkreply(Integer commentid) {
        QueryWrapper<Reply>replyQueryWrapper = new QueryWrapper<>();
        replyQueryWrapper.eq("commentkey",commentid);
        List<Reply> replyList = list(replyQueryWrapper);
        return replyList;
    }

}
