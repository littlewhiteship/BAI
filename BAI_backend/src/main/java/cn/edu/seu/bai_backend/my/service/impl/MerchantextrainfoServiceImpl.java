package cn.edu.seu.bai_backend.my.service.impl;
import cn.edu.seu.bai_backend.my.entity.Merchantextrainfo;
import cn.edu.seu.bai_backend.my.entity.Userinfo;
import cn.edu.seu.bai_backend.my.mapper.MerchantextrainfoMapper;
import cn.edu.seu.bai_backend.my.mapper.UserinfoMapper;
import cn.edu.seu.bai_backend.my.service.IMerchantextrainfoService;
import cn.edu.seu.bai_backend.my.service.IUserinfoService;
import cn.edu.seu.bai_backend.my.spidershoprate;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author songyifan
 * @since 2020-07-17
 */
@Service
public class MerchantextrainfoServiceImpl extends ServiceImpl<MerchantextrainfoMapper, Merchantextrainfo> implements IMerchantextrainfoService {

    @Autowired
    private IUserinfoService userinfoService;

    @Override
    public boolean VerifyMerchant(String account, String url, String mtype, String platform) {
        QueryWrapper<Userinfo> user = new QueryWrapper<>();
        user.eq("uiaccount",account);
        Userinfo selectOne = userinfoService.getOne(user);
        if(selectOne != null)
        {
            spidershoprate spider = new spidershoprate();
            //爬取商家店铺信誉值
            spider.setPlatform(platform);
            //System.out.println("选择平台");
            spider.setUrl(url);
            //System.out.println("输入链接");
            spider.setMmtype(mtype);
            //System.out.println("爬虫获得信誉值");
            spider.setRate();
            float rate = spider.getRate();
            float maxprice = spider.getMaxprice();
            float minprice = spider.getMinprice();
            if(rate>=4.8){
                Merchantextrainfo merchant = new Merchantextrainfo();
                merchant.setMemaxprice(maxprice);
                merchant.setMeminprice(minprice);
                merchant.setMeshopcredit(rate);
                merchant.setMemerchandisetype(mtype);
                merchant.setMeuseraccount(account);
                merchant.setMeshoplink(url);
                merchant.setMeshopplatform(platform);
                //更新user中的身份信息 umapper中update函数使用
                UpdateWrapper<Userinfo> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("uiaccount",account).set("uiidenfication",2);
                userinfoService.update(updateWrapper);
                save(merchant);
                return true;
            }
            else {
                return false;
            }
        }
        else{
            return false;
        }
    }

    @Override
    public void IFRemoveMerchant() {
        QueryWrapper<Merchantextrainfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("meid");
        List<Merchantextrainfo> merchants = list(queryWrapper);
        int all = merchants.size() - 1;//注意数组越界
        while(all>=0)
        {
            System.out.println("进入商家每日信誉认证");
            boolean overrate = VerifyMerchant(merchants.get(all).getMeuseraccount(),merchants.get(all).getMeshoplink(),
                    merchants.get(all).getMemerchandisetype(),merchants.get(all).getMeshopplatform());
            if(overrate == false) {
                removeById(merchants.get(all).getMeid());//从商家列表删除
                UpdateWrapper<Userinfo> up = new UpdateWrapper<>();
                up.eq("uiaccount",merchants.get(all).getMeuseraccount()).set("uiidenfication",1);
                userinfoService.update(up);//更新用户身份信息
            }
            all--;
        }
    }
}