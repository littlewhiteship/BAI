package cn.edu.seu.bai_backend.ishare.controller;

import cn.edu.seu.bai_backend.ishare.entity.Comment;
import cn.edu.seu.bai_backend.ishare.entity.Post;
import cn.edu.seu.bai_backend.ishare.entity.Reply;
import cn.edu.seu.bai_backend.ishare.service.ICommentService;
import cn.edu.seu.bai_backend.ishare.service.IPostService;
import cn.edu.seu.bai_backend.ishare.service.IReplyService;
import cn.edu.seu.bai_backend.my.entity.Userinfo;
import cn.edu.seu.bai_backend.my.service.IUserinfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.sf.json.JSONObject;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author songyifan
 * @since 2020-07-20
 */
@RestController
@RequestMapping("/ishare")
public class IshareController {

    @Autowired
    private IPostService iPostService;

    @Autowired
    private IUserinfoService iUserinfoService;

    @Autowired
    private ICommentService iCommentService;

    @Autowired
    private IReplyService iReplyService;

    @RequestMapping(value = "/posting",method = RequestMethod.POST)
    public JSONObject posting(String account, String nickname,String postimage,String merchandisename,
                              String postcontent,String merchandiselink,String merchandisepicpath){
        JSONObject jsonObj = new JSONObject();
        try {
            if(account == null||account.equals("")) {
                jsonObj.put("errcode", "60015");
                jsonObj.put("errmsg", "用户账号为空");
                return jsonObj;
            }
            if(nickname == null||nickname.equals("")) {
                jsonObj.put("errcode", "60016");
                jsonObj.put("errmsg", "用户昵称为空");
                return jsonObj;
            }
            if(postimage == null||postimage.equals("")) {
                jsonObj.put("errcode", "60019");
                jsonObj.put("errmsg", "用户头像为空");
                return jsonObj;
            }
            if(merchandisename == null||merchandisename.equals("")) {
                jsonObj.put("errcode", "60017");
                jsonObj.put("errmsg", "推荐商品名不能为空，请重新填写。");
                return jsonObj;
            }
            if(postcontent == null||postcontent.equals("")) {
                jsonObj.put("errcode", "60018");
                jsonObj.put("errmsg", "帖子内容不能为空，请重新填写。");
                return jsonObj;
            }
            if(merchandiselink == null||merchandiselink.equals("")) {
                jsonObj.put("errcode", "60020");
                jsonObj.put("errmsg", "商品链接不能为空，请重新填写");
                return jsonObj;
            }
            Post post = new Post();
            post.setPostaccount(account);
            post.setPostnickname(nickname);
            post.setPostimage(postimage);
            post.setMerchandisename(merchandisename);
            post.setPostcontent(postcontent);
            post.setMerchandiselink(merchandiselink);
            post.setMerchandisepicpath(merchandisepicpath);
            post.setLikenum(0);
            post.setCommentnum(0);
            post.setComplaintnum(0);
            LocalDateTime postingtime = LocalDateTime.now();
            post.setPosttime(postingtime);
            iPostService.posting(post);
            jsonObj.put("errcode","0");
            jsonObj.put("errmsg","发帖成功！");
            jsonObj.put("data",post);
        }catch (Exception ex) {
            jsonObj.put("errcode","20000");
            jsonObj.put("errmsg","发帖失败，发生未知错误");
        }
        return jsonObj;
    }

    @RequestMapping(value = "/getpostlist",method = RequestMethod.POST)
    public JSONObject getpostlist(){
        JSONObject jsonObj = new JSONObject();
        try {
            List<Post> result = iPostService.getpostlist();
            jsonObj.put("errcode","0");
            jsonObj.put("data",result);
        }catch (Exception ex) {
            jsonObj.put("errcode","20001");
            jsonObj.put("errmsg","查看帖子失败，发生未知错误");
        }
        return jsonObj;
    }

    @RequestMapping(value = "/searchpost",method = RequestMethod.POST)
    public JSONObject searchpost(String keyword){
        JSONObject jsonObj = new JSONObject();
        try{
            if(keyword == null||keyword.equals("")) {
                jsonObj.put("errcode", "60021");
                jsonObj.put("errmsg", "搜索关键词为空，请重新输入");
                return jsonObj;
            }
            List<Post> result = iPostService.searchpost(keyword);
            jsonObj.put("errcode","0");
            jsonObj.put("data",result);
        }catch (Exception ex) {
            jsonObj.put("errcode","20002");
            jsonObj.put("errmsg","搜索失败，发生未知错误！");
        }
        return jsonObj;
    }

    @RequestMapping(value = "/checkpostdetail",method = RequestMethod.POST)
    public JSONObject checkpostdetail(String account,Integer postid){
        JSONObject jsonObject = new JSONObject();
        try{
            if(account == null||account.equals("")) {
                jsonObject.put("errcode", "60015");
                jsonObject.put("errmsg", "用户账号为空");
                return jsonObject;
            }
            if(postid == null||postid.equals("")) {
                jsonObject.put("errcode", "60024");
                jsonObject.put("errmsg", "帖子id为空，传输失败");
                return jsonObject;
            }
            Post result = iPostService.checkpostdetail(postid);
            QueryWrapper<Userinfo> userinfoQueryWrapper = new QueryWrapper<>();
            userinfoQueryWrapper.eq("uiaccount",account);
            jsonObject.put("errcode","0");
            jsonObject.put("data",result);
            jsonObject.put("currentuser",iUserinfoService.getOne(userinfoQueryWrapper));
            List<Comment> comlist =iCommentService.checkcomment(postid);
            jsonObject.put("commentlist",iCommentService.checkcomment(postid));
       /*     List<Reply> replyList = new ArrayList<>();
            if (comlist.size()!=0) {
                for (int i = 0; i < comlist.size();i++){
                    Integer comkid = comlist.get(i).getCommentkey();
                    QueryWrapper<Reply> replyQueryWrapper = new QueryWrapper<>();
                    replyQueryWrapper.eq("commentkey",comkid);
                    replyList.add(iReplyService.getOne(replyQueryWrapper));
                }
                jsonObject.put("replylist",replyList);
            }else{
                jsonObject.put("replylist","");
            }*/
        }catch(Exception ex) {
            jsonObject.put("errcode","20003");
            jsonObject.put("errmsg","查看失败，发生未知错误！");
        }
        return jsonObject;
    }

    @RequestMapping(value = "/deletepost",method = RequestMethod.POST)
    public JSONObject deletepost(String account,Integer postid){
        JSONObject jsonObject = new JSONObject();
        try{
            if(account == null||account.equals("")) {
                jsonObject.put("errcode", "60015");
                jsonObject.put("errmsg", "用户账号为空");
                return jsonObject;
            }
            if(postid == null||postid.equals("")) {
                jsonObject.put("errcode", "60025");
                jsonObject.put("errmsg", "帖子id为空");
                return jsonObject;
            }
            QueryWrapper<Userinfo>user = new QueryWrapper<>();
            user.eq("uiaccount",account);
            //判断用户删除权限，必须是管理员或者发帖人。
            if((iUserinfoService.getOne(user).getUiidenfication()==0) ||
                    (iPostService.getById(postid).getPostaccount().equals(account))) {
                List<Post> postList = iPostService.deletepost(account, postid);
                jsonObject.put("errcode","0");
                jsonObject.put("data",postList);
            }else{
                jsonObject.put("errcode","20005");
                jsonObject.put("errmsg","请求失败，您没有删帖权限！");
                return jsonObject;
            }
        }catch(Exception ex) {
            jsonObject.put("errcode","20004");
            jsonObject.put("errmsg","删除失败，发生未知错误！");
        }
        return jsonObject;
    }

    @RequestMapping(value = "/reportpost",method = RequestMethod.POST)
    public JSONObject reportpost(Integer postid){
        JSONObject jsonObject = new JSONObject();
        try{
            if(postid == null) {
                jsonObject.put("errcode", "60025");
                jsonObject.put("errmsg", "帖子id为空");
                return jsonObject;
            }
            Post newpost = iPostService.report(postid);
            jsonObject.put("errcode","0");
            jsonObject.put("errmsg","举报成功，管理员将会处理。");
            jsonObject.put("data",newpost);//返回帖子详情页
        }catch (Exception ex){
            jsonObject.put("errcode","20006");
            jsonObject.put("errmsg","举报失败，发生未知错误。");
        }
        return jsonObject;
    }

    @RequestMapping(value = "/like",method = RequestMethod.POST)
    public JSONObject likepost(Integer postid,String account) {
        JSONObject jsonObject = new JSONObject();
        try{
            if(postid == null) {
                jsonObject.put("errcode", "60025");
                jsonObject.put("errmsg", "帖子id为空");
                return jsonObject;
            }
            if(account == null||account.equals("")) {
                jsonObject.put("errcode", "60015");
                jsonObject.put("errmsg", "用户账号为空");
                return jsonObject;
            }
            Post post = iPostService.likepost(postid,account);
            jsonObject.put("errcode","0");
            jsonObject.put("data",post);
        }catch (Exception ex){
            jsonObject.put("errcode","20007");
            jsonObject.put("errmsg","点赞失败，发生未知错误。");
        }
        return jsonObject;
    }

    @RequestMapping(value = "/cancellike",method = RequestMethod.POST)
    public JSONObject cancellike(Integer postid){
        JSONObject jsonObject = new JSONObject();
        try{
            if(postid == null) {
                jsonObject.put("errcode", "60025");
                jsonObject.put("errmsg", "帖子id为空");
                return jsonObject;
            }
            Post post = iPostService.cancellike(postid);
            jsonObject.put("errcode","0");
            jsonObject.put("data",post);
        }catch (Exception ex){
            jsonObject.put("errcode","20008");
            jsonObject.put("errmsg","取消点赞失败，发生未知错误。");
        }
        return jsonObject;
    }

    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public JSONObject comment(String account,Integer postid,String content){
        JSONObject jsonObject = new JSONObject();
        try{
            if(account == null||account.equals("")) {
                jsonObject.put("errcode", "60015");
                jsonObject.put("errmsg", "用户账号为空");
                return jsonObject;
            }
            if(postid == null) {
                jsonObject.put("errcode", "60025");
                jsonObject.put("errmsg", "帖子id为空");
                return jsonObject;
            }
            if(content == null||content.equals("")) {
                jsonObject.put("errcode", "60025");
                jsonObject.put("errmsg", "评论内容不能为空，请重新填写！");
                return jsonObject;
            }
            List<Comment> list = iCommentService.addcomment(account,postid,content);
            jsonObject.put("errcode","0");
            jsonObject.put("data",list);
        }catch(Exception ex){
            jsonObject.put("errcode","20009");
            jsonObject.put("errmsg","评论失败，发生未知错误。");
        }
        return jsonObject;
    }

    @RequestMapping(value = "/deletecomment",method = RequestMethod.POST)
    public JSONObject deletecomment(String account,Integer commentid){
        JSONObject jsonObject = new JSONObject();
        try {
            if(account == null||account.equals("")) {
                jsonObject.put("errcode", "60015");
                jsonObject.put("errmsg", "用户账号为空");
                return jsonObject;
            }
            if(commentid == null) {
                jsonObject.put("errcode", "60025");
                jsonObject.put("errmsg", "评论id为空");
                return jsonObject;
            }
            //判断操作人是否有权限
            if(iCommentService.getById(commentid).getCommentatoraccount().equals(account)
                    ||iPostService.getById(iCommentService.getById(commentid).getPostkey()).getPostaccount().equals(account)) {
                List<Comment>clist = iCommentService.deletecomment(commentid);
                jsonObject.put("errcode","0");
                jsonObject.put("data",clist);
            }else{
                jsonObject.put("errcode","20011");
                jsonObject.put("errmsg","删除失败，您没有删除权限！");
                return jsonObject;
            }
        }catch (Exception ex){
            jsonObject.put("errcode","20010");
            jsonObject.put("errmsg","未知错误，删除失败！");
        }
        return jsonObject;
    }

    @RequestMapping(value = "/reply",method = RequestMethod.POST)
    public JSONObject reply(String account,String nickname, String image, Integer commentid,String content){
        JSONObject jsonObject = new JSONObject();
        try{
            if(account == null||account.equals("")) {
                jsonObject.put("errcode", "60015");
                jsonObject.put("errmsg", "用户账号为空");
                return jsonObject;
            }
            if(nickname == null||nickname.equals("")) {
                jsonObject.put("errcode", "60026");
                jsonObject.put("errmsg", "用户昵称为空");
                return jsonObject;
            }
            if(image == null||image.equals("")) {
                jsonObject.put("errcode", "60027");
                jsonObject.put("errmsg", "用户头像为空");
                return jsonObject;
            }
            if(commentid == null||commentid.equals("")) {
                jsonObject.put("errcode", "60028");
                jsonObject.put("errmsg", "评论id为空");
                return jsonObject;
            }
            if(content == null||content.equals("")) {
                jsonObject.put("errcode", "60029");
                jsonObject.put("errmsg", "回复内容不能为空，请重新填写！");
                return jsonObject;
            }
            Reply replyinfo = iReplyService.replying(account,nickname,image,commentid,content);
            jsonObject.put("errcode","0");
            jsonObject.put("data",replyinfo);
        }catch (Exception ex) {
            jsonObject.put("errcode","20012");
            jsonObject.put("errmsg","未知错误，回复失败！");
        }
        return jsonObject;
    }

    @RequestMapping(value = "/deletereply",method = RequestMethod.POST)
    public JSONObject deletereply(String account,Integer replyid){
        JSONObject jsonObject = new JSONObject();
        try{
            if(account == null||account.equals("")) {
                jsonObject.put("errcode", "60015");
                jsonObject.put("errmsg", "用户账号为空");
                return jsonObject;
            }
            if(replyid == null) {
                jsonObject.put("errcode", "60025");
                jsonObject.put("errmsg", "回复id为空");
                return jsonObject;
            }
            //判断删除人是否有权限
            if(iReplyService.getById(replyid).getReplieraccount().equals(account)) {
                iReplyService.removeById(replyid);
                jsonObject.put("errcode", "0");
                jsonObject.put("errmsg", "删除成功！");
            }else{
                jsonObject.put("errcode","20014");
                jsonObject.put("errmsg","删除失败，您没有删除权限！");
            }
        }catch (Exception ex){
            jsonObject.put("errcode","20013");
            jsonObject.put("errmsg","删除失败，未知错误。");
        }
        return jsonObject;
    }

}