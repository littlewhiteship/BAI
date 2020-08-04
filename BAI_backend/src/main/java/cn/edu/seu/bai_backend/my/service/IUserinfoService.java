package cn.edu.seu.bai_backend.my.service;

import cn.edu.seu.bai_backend.my.entity.Userinfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author songyifan
 * @since 2020-07-20
 */
public interface IUserinfoService extends IService<Userinfo> {
    Userinfo SignIn( String account, String password, String nickname, String picture);
    //用户注册
    Userinfo LogIn(String account, String password);
    //用户登录
}
