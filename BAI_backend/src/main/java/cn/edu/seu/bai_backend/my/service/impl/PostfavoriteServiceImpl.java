package cn.edu.seu.bai_backend.my.service.impl;

import cn.edu.seu.bai_backend.ishare.entity.Post;
import cn.edu.seu.bai_backend.ishare.mapper.PostMapper;
import cn.edu.seu.bai_backend.ishare.service.IPostService;
import cn.edu.seu.bai_backend.my.entity.Postfavorite;
import cn.edu.seu.bai_backend.my.entity.Userinfo;
import cn.edu.seu.bai_backend.my.mapper.PostfavoriteMapper;
import cn.edu.seu.bai_backend.my.mapper.UserinfoMapper;
import cn.edu.seu.bai_backend.my.service.IPostfavoriteService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author songyifan
 * @since 2020-07-20
 */
@Service
public class PostfavoriteServiceImpl extends ServiceImpl<PostfavoriteMapper, Postfavorite> implements IPostfavoriteService {

    @Autowired
    private IPostService iPostService;

    @Override
    public List<Post> checkPostList(String account) {
        //用用户账号索引收藏帖子id
        QueryWrapper<Postfavorite> fpquery = new QueryWrapper<Postfavorite>();
        //设置搜索属性
        fpquery.eq("pfaccount",account).orderByDesc("pffavid");
        //搜索account用户的收藏信息并按id降序
        List<Postfavorite> pfidList = list(fpquery);
        //创建用户account收藏列表数组
        List<Post> favpostlist = new ArrayList<>();
        System.out.println("生成新数组");
       if(pfidList.size()!=0) {
           Post temp = new Post();
           for (int i = 0; i < pfidList.size(); i++) {
               temp = iPostService.getById(pfidList.get(i).getPfid());
               favpostlist.add(temp);
           }
           return favpostlist;
       }
       else {
           return null;
       }
    };

    @Override
    public List<Post> deletePostList(String account, Integer postfavid) {
        QueryWrapper<Postfavorite> pfquery = new QueryWrapper<>();
        pfquery.eq("pfaccount",account).eq("pfid",postfavid);
        remove(pfquery);
        return checkPostList(account);
    }

    @Override
    public void addFavPostList(String account, Integer postid) {
        Postfavorite favpost = new Postfavorite();
        QueryWrapper<Post> postQueryWrapper = new QueryWrapper<>();
        postQueryWrapper.eq("postkey",postid);
        Post post = iPostService.getOne(postQueryWrapper);
        favpost.setPfid(post.getPostkey());
        favpost.setPfaccount(account);
        //获取当前收藏时间
        LocalDateTime pftime = LocalDateTime.now();
        favpost.setPftime(pftime);
        save(favpost);
    }
}