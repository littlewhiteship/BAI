package cn.edu.seu.bai_backend.my.controller;

import cn.edu.seu.bai_backend.ibuy.entity.Allmsales;
import cn.edu.seu.bai_backend.ibuy.entity.IntroMerchandise;
import cn.edu.seu.bai_backend.ibuy.service.IAllmsalesService;
import cn.edu.seu.bai_backend.ishare.entity.Post;
import cn.edu.seu.bai_backend.ishare.service.IPostService;
import cn.edu.seu.bai_backend.my.entity.Customerfavorite;
import cn.edu.seu.bai_backend.my.entity.Merchantextrainfo;
import cn.edu.seu.bai_backend.my.entity.Postfavorite;
import cn.edu.seu.bai_backend.my.entity.Userinfo;
import cn.edu.seu.bai_backend.my.service.ICustomerfavoriteService;
import cn.edu.seu.bai_backend.my.service.IMerchantextrainfoService;
import cn.edu.seu.bai_backend.my.service.IPostfavoriteService;
import cn.edu.seu.bai_backend.my.service.IUserinfoService;
import cn.edu.seu.bai_backend.notice.entity.Postnotice;
import cn.edu.seu.bai_backend.notice.service.IPostnoticeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author songyifan
 * @since 2020-07-20
 */
@RestController
@RequestMapping("/my")
//@CrossOrigin(origins = {"http://"},allowCredentials = "true")
public class MyController {

    @Autowired
    private IAllmsalesService iAllmsalesService;
    @Autowired
    private IUserinfoService userinfoService;
    @Autowired
    private IMerchantextrainfoService merchantextrainfoService;
    @Autowired
    private ICustomerfavoriteService customerfavoriteService;
    @Autowired
    private IPostfavoriteService iPostfavoriteService;
    @Autowired
    private IPostService iPostService;
    @Autowired
    private IPostnoticeService iPostnoticeService;

    @RequestMapping(value="/register",method = RequestMethod.POST)
    public JSONObject register(String account,String password,String nickname,String image ){
        JSONObject jsonObj = new JSONObject();
        try{
            if(account==null || account.equals("")){
                jsonObj.put("errcode","30001");
                jsonObj.put("errmsg","账号为空");
                return jsonObj;
            }

            if(password==null || password.equals("")){
                jsonObj.put("errcode","30002");
                jsonObj.put("errmsg","密码为空");
                return jsonObj;
            }

            if(nickname==null || nickname.equals("")){
                jsonObj.put("errcode","30003");
                jsonObj.put("errmsg","昵称为空");
                return jsonObj;
            }

            if(image == null || image.equals("")){
                jsonObj.put("errcode","30004");
                jsonObj.put("errmsg","头像获取失败");
                return jsonObj;
            }

            Userinfo user = userinfoService.SignIn(account,password,nickname,image);
            if(user!=null) {
                user.setUipassword("");
                jsonObj.put("errcode", "0");
                jsonObj.put("data", user);
                return jsonObj;
            } else{
                jsonObj.put("errcode","30005");
                jsonObj.put("errmsg","用户已注册");
            }
        }catch(Exception ex){
            jsonObj.put("errcode","30000");
            jsonObj.put("errmsg","注册失败");
        }
        return jsonObj;
    }

    @RequestMapping(value="/login",method = RequestMethod.POST)
    public JSONObject login(String account, String password){
        JSONObject jsonObj = new JSONObject();
        try{
            if(account == null||account.equals("")) {
                jsonObj.put("errcode","30001");
                jsonObj.put("errmsg","账号为空");
                return jsonObj;
            }
            Userinfo user = userinfoService.LogIn(account, password);
            if(user!=null) {
                jsonObj.put("errcode","0");
                jsonObj.put("data",user);
                //如果身份是商家，会返回店铺id，用于ishop店铺分析
                if(user.getUiidenfication()==2) {
                    QueryWrapper<Merchantextrainfo> mquerywrapper=new QueryWrapper<>();
                    mquerywrapper.eq("meuseraccount",account);
                    jsonObj.put("shopid", merchantextrainfoService.getOne(mquerywrapper).getMeid());
                }
                return jsonObj;
            }else{
                jsonObj.put("errcode","30002");
                jsonObj.put("errmsg","密码错误");
                return jsonObj;
            }
        }catch (Exception ex){
            jsonObj.put("errcode","30000");
            jsonObj.put("errmsg","登录失败");
            ex.printStackTrace();
        }
        return jsonObj;
    }

    @RequestMapping(value="/verify",method = RequestMethod.POST)
    public JSONObject verify(String account, String url, String mtype, String platform) {
        JSONObject jsonObj = new JSONObject();
        try{
            if(url == null||url.equals("")) {
                jsonObj.put("errcode","30006");
                jsonObj.put("errmsg","店铺链接为空");
                return jsonObj;
            }
            if(platform == null || platform.equals("")) {
                jsonObj.put("errcode","30007");
                jsonObj.put("errmsg","尚未选择店铺来源平台");
                return jsonObj;
            }
            if(mtype == null || mtype.equals("")){
                jsonObj.put("errcode","30008");
                jsonObj.put("errmsg","尚未设置店铺所售商品类型");
                return jsonObj;
            }
            QueryWrapper<Merchantextrainfo> tempmer = new QueryWrapper<>();
            tempmer.eq("meshoplink",url);
            Merchantextrainfo temp = merchantextrainfoService.getOne(tempmer);
            if(temp!=null){
                jsonObj.put("errcode","30099");
                jsonObj.put("errmsg","认证失败，该店铺已注册成为商家。");
                return jsonObj;
            }
            QueryWrapper<Merchantextrainfo> temper2 = new QueryWrapper<>();
            temper2.eq("meuseraccount",account);
            if(merchantextrainfoService.list(temper2).size()!=0){
                jsonObj.put("errcode","30088");
                jsonObj.put("errmsg","认证失败，该用户已认证为商家。");
            }
            boolean ifmerchant = merchantextrainfoService.VerifyMerchant(account, url, mtype, platform);
            if(ifmerchant) {
                QueryWrapper<Merchantextrainfo> merchantextrainfoQueryWrapper = new QueryWrapper<>();
                merchantextrainfoQueryWrapper.eq("meuseraccount",account);
                jsonObj.put("errcode","0");
                jsonObj.put("account",account);
                jsonObj.put("merchantid",merchantextrainfoService.getOne(merchantextrainfoQueryWrapper).getMeid());
                jsonObj.put("errmsg","认证成功，重新进入小程序即可查看店铺分析！");
                return jsonObj;
            }else{
                jsonObj.put("errcode","30009");
                jsonObj.put("account",account);
                jsonObj.put("errmsg","店铺信誉未达到4.8，认证失败！请努力提升店铺信誉度再来认证哦~");
                return jsonObj;
            }
        }catch (Exception ex){
            jsonObj.put("errcode","30010");
            jsonObj.put("errmsg","发生未知错误，认证失败。");
        }
        return jsonObj;
    }

    @RequestMapping(value = "/deletemerchant",method = RequestMethod.POST)
    public JSONObject deletemerchant(){
        JSONObject jsonObj = new JSONObject();
        try{
            merchantextrainfoService.IFRemoveMerchant();
            jsonObj.put("errcode","0");
            jsonObj.put("errmsg","商家信誉度更新完毕！");
            return jsonObj;
        } catch (Exception e) {
            jsonObj.put("errcode","30011");
            jsonObj.put("errmsg","未知错误！");
            return jsonObj;
        }
    }

    @RequestMapping(value = "/addfavm",method = RequestMethod.POST)
    public JSONObject addfavm(String account, String url){
        JSONObject jsonObj = new JSONObject();
        try{
            if(url == null||url.equals("")) {
                jsonObj.put("errcode", "60001");
                jsonObj.put("errmsg", "商品链接为空，请输入");
                return jsonObj;
            }
            if(account == null||account.equals("")) {
                jsonObj.put("errcode", "60002");
                jsonObj.put("errmsg", "用户账号为空，发生未知错误");
                return jsonObj;
            }

            //判断该商品是否已被收藏
            QueryWrapper<Customerfavorite> customerfavoriteQueryWrapper = new QueryWrapper<>();
            customerfavoriteQueryWrapper.eq("cfuseraccount",account).eq("cfmurl",url);
            if (customerfavoriteService.list(customerfavoriteQueryWrapper).size()!=0){
                jsonObj.put("errcode","30097");
                jsonObj.put("errmsg","添加收藏失败！该商品已在您的收藏列表中。");
                return jsonObj;
            }
            customerfavoriteService.addmfav(account, url);
            jsonObj.put("errcode","0");
            jsonObj.put("errmsg","添加收藏成功！");
            return jsonObj;
        } catch (Exception e) {
            jsonObj.put("errcode","30012");
            jsonObj.put("errmsg","收藏失败，发生未知错误。");
            return jsonObj;
        }
    }

    @RequestMapping(value = "/checkfavm",method = RequestMethod.POST)
    public JSONObject checkfavm(String account){
        JSONObject jsonObj = new JSONObject();
        try{
            if(account == null||account.equals("")) {
                jsonObj.put("errcode","60007");
                jsonObj.put("errmsg","用户账号为空，传输错误");
                return jsonObj;
            }
            List<Customerfavorite> result =customerfavoriteService.MyFavorite(account);
            if(result.size()!=0){
                jsonObj.put("errcode","0");
                jsonObj.put("data",result);
            }else {
                jsonObj.put("errcode","0");
                jsonObj.put("errmsg","收藏列表为空");
            }
            return jsonObj;
        }catch (Exception ex) {
            jsonObj.put("errcode","30013");
            jsonObj.put("errmsg","查看收藏失败，发生未知错误。");
            return jsonObj;
        }
    }

    @RequestMapping(value = "/deletefavm",method = RequestMethod.POST)
    public JSONObject deletefavm(String account,Integer FavoriteID) {
        JSONObject jsonObj = new JSONObject();
        try{
            if(account == null||account.equals("")) {
                jsonObj.put("errcode", "60008");
                jsonObj.put("errmsg", "用户账号为空");
                return jsonObj;
            }
            if(FavoriteID == null) {
                jsonObj.put("errcode", "60009");
                jsonObj.put("errmsg", "收藏商品id为空，数据传输错误");
                return jsonObj;
            }
            List<Customerfavorite>result = customerfavoriteService.MFdelete(account, FavoriteID);
            jsonObj.put("errcode","0");
            jsonObj.put("errmsg","删除成功！");
            jsonObj.put("data",result);
        }catch (Exception ex)
        {
            jsonObj.put("errcode","30014");
            jsonObj.put("errmsg","删除失败，发生未知错误。");
        }
        return jsonObj;
    }

    @RequestMapping(value = "/addfavpost",method = RequestMethod.POST)
    public JSONObject addfavpost(String account, Integer postid) {
        JSONObject jsonObj = new JSONObject();
        try{
            if(account == null||account.equals("")) {
                jsonObj.put("errcode", "60010");
                jsonObj.put("errmsg", "用户账号为空，传输错误");
                return jsonObj;
            }
            if(postid == null) {
                jsonObj.put("errcode", "60011");
                jsonObj.put("errmsg", "帖子id为空");
                return jsonObj;
            }
            //判断该贴是否已被收藏
            QueryWrapper<Postfavorite> postfavoriteQueryWrapper = new QueryWrapper<>();
            postfavoriteQueryWrapper.eq("pfaccount",account).eq("pfid",postid);
            if(iPostfavoriteService.list(postfavoriteQueryWrapper).size()==0) {
                iPostfavoriteService.addFavPostList(account, postid);
                System.out.println("添加完毕！");

                List<Post> fav = iPostfavoriteService.checkPostList(account);
                jsonObj.put("errcode", "0");
                jsonObj.put("data", fav);
            }else{
                jsonObj.put("errcode","30098");
                jsonObj.put("errmsg","添加失败，该贴已被收藏");
                return jsonObj;
            }
        }catch (Exception ex) {
            jsonObj.put("errcode","30015");
            jsonObj.put("errmsg","添加失败，发生未知错误！");
        }
        return jsonObj;
    }

    @RequestMapping(value = "/checkfavpost",method = RequestMethod.POST)
    public JSONObject checkfavpost(String account){
        JSONObject jsonObj = new JSONObject();
        try{
            if(account == null||account.equals("")) {
                jsonObj.put("errcode", "60012");
                jsonObj.put("errmsg", "用户账号为空");
                return jsonObj;
            }
            List<Post> result = iPostfavoriteService.checkPostList(account);
            QueryWrapper<Postfavorite> postfavoriteQueryWrapper = new QueryWrapper<>();
            postfavoriteQueryWrapper.eq("pfaccount",account);
            List<Postfavorite> favlist = iPostfavoriteService.list(postfavoriteQueryWrapper);
            jsonObj.put("errcode","0");
            jsonObj.put("favlist",favlist);
            jsonObj.put("data",result);
        }catch (Exception ex)
        {
            jsonObj.put("errcode","30016");
            jsonObj.put("errmsg","查看失败，发生未知错误。");
        }
        return jsonObj;
    }

    @RequestMapping(value = "/deletefavpost",method = RequestMethod.POST)
    public JSONObject deletefavpost(String account, Integer postid){
        JSONObject jsonObj = new JSONObject();
        try{
            if(account == null||account.equals("")) {
                jsonObj.put("errcode", "60013");
                jsonObj.put("errmsg", "用户账号为空");
                return jsonObj;
            }
            if(postid == null) {
                jsonObj.put("errcode", "60014");
                jsonObj.put("errmsg", "收藏列表帖子id为空");
                return jsonObj;
            }
            List<Post> result = iPostfavoriteService.deletePostList(account, postid);
            QueryWrapper<Postfavorite> postfavoriteQueryWrapper = new QueryWrapper<>();
            postfavoriteQueryWrapper.eq("pfaccount",account);
            List<Postfavorite> favlist = iPostfavoriteService.list(postfavoriteQueryWrapper);
            jsonObj.put("errcode","0");
            jsonObj.put("errmsg","删除成功！");
            jsonObj.put("favlist",favlist);
            jsonObj.put("data",result);
        }catch (Exception ex){
            jsonObj.put("errcode","30017");
            jsonObj.put("errmsg","删除失败，发生未知错误。");
        }
        return jsonObj;
    }

    @RequestMapping(value = "/checkreport",method = RequestMethod.POST)
    public JSONObject checkreport(String account){
        JSONObject jsonObject = new JSONObject();
        try{
            if(account == null||account.equals("")) {
                jsonObject.put("errcode", "60015");
                jsonObject.put("errmsg", "用户账号为空");
                return jsonObject;
            }
            //判断当前帐号是否具有操作权限
            QueryWrapper<Userinfo>userinfoQueryWrapper = new QueryWrapper<>();
            userinfoQueryWrapper.eq("uiaccount",account);
            Userinfo user = userinfoService.getOne(userinfoQueryWrapper);
            if(user.getUiidenfication()==0) {
                QueryWrapper<Post> postQueryWrapper = new QueryWrapper<>();
                postQueryWrapper.gt("complaintnum",0).orderByDesc("complaintnum");
                List<Post> reportlist = iPostService.list(postQueryWrapper);
                jsonObject.put("errcode", "0");
                jsonObject.put("data", reportlist);
            }else{
                jsonObject.put("errcode","30019");
                jsonObject.put("errmsg","查看失败，您没有权限");
                return jsonObject;
            }
        }catch (Exception ex){
            jsonObject.put("errcode","30018");
            jsonObject.put("errmsg","查看失败，未知错误");
        }
        return jsonObject;
    }

    @RequestMapping(value = "/checkpostnotice",method = RequestMethod.POST)
    public JSONObject checkpostnotice(String account){
        JSONObject jsonObject = new JSONObject();
        try {
            if(account == null||account.equals("")) {
                jsonObject.put("errcode", "60016");
                jsonObject.put("errmsg", "用户账号为空");
                return jsonObject;
            }
            QueryWrapper<Postnotice> postnoticeQueryWrapper = new QueryWrapper<>();
            postnoticeQueryWrapper.eq("receiveraccount",account).orderByDesc("noticekey");
            List<Postnotice> noticelist = iPostnoticeService.list(postnoticeQueryWrapper);
            jsonObject.put("errcode","0");
            jsonObject.put("data",noticelist);
        }catch (Exception ex){
            jsonObject.put("errcode","30020");
            jsonObject.put("errmsg","查看失败，未知错误");
        }
        return jsonObject;
    }

    @RequestMapping(value = "/getMList",method = RequestMethod.POST)
    public JSONObject getMlist() {
        JSONObject jsonObject = new JSONObject();
        try {
            QueryWrapper<Allmsales> allmsalesQueryWrapper = new QueryWrapper<>();
            allmsalesQueryWrapper.orderByDesc("salesscore");
            List<Allmsales> list = iAllmsalesService.list(allmsalesQueryWrapper);
            jsonObject.put("errcode", "0");
            jsonObject.put("data", list);
        } catch (Exception ex) {
            jsonObject.put("errcode", "40002");
            jsonObject.put("errmsg", "查看商品列表失败，发生未知错误。");
        }
        return jsonObject;
    }

    @RequestMapping(value = "/checkposthis",method = RequestMethod.POST)
    JSONObject checkposthis(String account){
        JSONObject jsonObject = new JSONObject();
        try{
            if(account ==null||account.equals("")) {
                jsonObject.put("errcode","60015");
                jsonObject.put("errmsg","用户账号为空，数据传输错误！");
                return jsonObject;
            }
            QueryWrapper<Post> postQueryWrapper = new QueryWrapper<>();
            postQueryWrapper.eq("postaccount",account).orderByDesc("postkey");
            List<Post> list = iPostService.list(postQueryWrapper);
            jsonObject.put("errcode","0");
            jsonObject.put("data",list);
        }catch (Exception ex){
            jsonObject.put("errcode","60035");
            jsonObject.put("errmsg","查看发帖历史失败，未知错误！");
        }
        return jsonObject;
    }
}