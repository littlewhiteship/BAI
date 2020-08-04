package cn.edu.seu.bai_backend.ishop.service.impl;

import cn.edu.seu.bai_backend.ishop.entity.Blogger;
import cn.edu.seu.bai_backend.ishop.mapper.BloggerMapper;
import cn.edu.seu.bai_backend.ishop.service.IBloggerService;
import cn.edu.seu.bai_backend.my.entity.Merchantextrainfo;
import cn.edu.seu.bai_backend.my.service.IMerchantextrainfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nqr
 * @since 2020-07-29
 */
@Service
public class BloggerServiceImpl extends ServiceImpl<BloggerMapper, Blogger> implements IBloggerService {

    @Autowired
    IMerchantextrainfoService merchantextrainfoService;

    @Override
    public List<Blogger> getIntoBlogger(int shopid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("meid",shopid);
        System.out.println("店铺id"+shopid);
        //获得按照店铺信息
        Merchantextrainfo merchantextrainfo = merchantextrainfoService.getOne(queryWrapper);
        //获取店铺销售商品种类
        System.out.println("merchantextrainfo"+merchantextrainfo);

        String type = merchantextrainfo.getMemerchandisetype();
        //获取主播信息
        System.out.println("type="+type);
        type=type+"博主";
        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("type","女装博主");
        List<Blogger> bloggerList = list(queryWrapper1);
        //Blogger blogger = getById(1);
        //System.out.println(blogger);
        System.out.println(bloggerList);
        return bloggerList;

    }
}
