package cn.edu.seu.bai_backend.ishare.service;

import cn.edu.seu.bai_backend.ishare.entity.Reply;
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
public interface IReplyService extends IService<Reply> {

    Reply replying(String account, String nickname, String image, Integer commentid, String content);

    List<Reply> checkreply(Integer commentid);
}
