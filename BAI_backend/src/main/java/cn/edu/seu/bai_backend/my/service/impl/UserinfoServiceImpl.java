package cn.edu.seu.bai_backend.my.service.impl;

import cn.edu.seu.bai_backend.my.entity.Userinfo;
import cn.edu.seu.bai_backend.my.mapper.UserinfoMapper;
import cn.edu.seu.bai_backend.my.service.IUserinfoService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author songyifan
 * @since 2020-07-17
 */
@Service
public class UserinfoServiceImpl extends ServiceImpl<UserinfoMapper, Userinfo> implements IUserinfoService {

    @Override
    public Userinfo SignIn(String account, String password, String nickname, String image) {
        QueryWrapper<Userinfo> user = new QueryWrapper<>();
        user.eq("uiaccount",account);
        if (getOne(user) == null) {
            Userinfo newUser = new Userinfo();
            newUser.setUiaccount(account);
            newUser.setUiidenfication(1);//身份1为顾客
            newUser.setUinickname(nickname);
            newUser.setUiimage(image);
            newUser.setUipassword(password);
            save(newUser);
            return newUser;
        } else
            return null;
    }

    @Override
    public Userinfo LogIn(String account, String password) {
        QueryWrapper<Userinfo>qw = new QueryWrapper<>();
        qw.eq("uiaccount",account).eq("uipassword",password);
        return getOne(qw);
        //System.out.println("生成userlogin实体2");
        //System.out.println(user.getUiaccount());
    }
}