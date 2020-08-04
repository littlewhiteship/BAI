package cn.edu.seu.bai_backend.my.service.impl;

import cn.edu.seu.bai_backend.ibuy.entity.Allmcredit;
import cn.edu.seu.bai_backend.ibuy.entity.Allmprice;
import cn.edu.seu.bai_backend.ibuy.entity.Allmsales;
import cn.edu.seu.bai_backend.ibuy.service.IAllmcreditService;
import cn.edu.seu.bai_backend.ibuy.service.IAllmpriceService;
import cn.edu.seu.bai_backend.ibuy.service.IAllmsalesService;
import cn.edu.seu.bai_backend.my.entity.Customerfavorite;
import cn.edu.seu.bai_backend.my.mapper.CustomerfavoriteMapper;
import cn.edu.seu.bai_backend.my.service.ICustomerfavoriteService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
public class CustomerfavoriteServiceImpl extends ServiceImpl<CustomerfavoriteMapper, Customerfavorite> implements ICustomerfavoriteService {

    @Autowired
    private IAllmsalesService iAllmsalesService;

    @Autowired
    private IAllmcreditService iAllmcreditService;

    @Autowired
    private IAllmpriceService iAllmpriceService;

    @Override
    public List<Customerfavorite> MyFavorite(String account) {
        QueryWrapper<Customerfavorite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cfuseraccount",account).orderByDesc("cftime");
        return list(queryWrapper);
    }

    @Override
    public List<Customerfavorite> MFdelete(String account,Integer FavoriteID) {
        removeById(FavoriteID);
        List<Customerfavorite> result = MyFavorite(account);
        return result;
    }

    @Override
    public void addmfav(String account, String url) {
        Customerfavorite newfav = new Customerfavorite();
        QueryWrapper<Allmsales> allmsalesQueryWrapper = new QueryWrapper<>();
        QueryWrapper<Allmcredit> allmcreditQueryWrapper = new QueryWrapper<>();
        QueryWrapper<Allmprice> allmpriceQueryWrapper = new QueryWrapper<>();
        allmsalesQueryWrapper.eq("link", url);
        allmcreditQueryWrapper.eq("link", url);
        allmpriceQueryWrapper.eq("link", url);
        if (iAllmsalesService.list(allmsalesQueryWrapper).size() != 0) {
            Allmsales merchandise = new Allmsales();
            merchandise = iAllmsalesService.getOne(allmsalesQueryWrapper);
            newfav.setCfimage(merchandise.getAmsimage());//商品图片
            newfav.setCfmname(merchandise.getAmsname());//商品名称
            newfav.setCfmplatform(merchandise.getPlatform());//商品平台
            newfav.setCfmprice(merchandise.getPrice());//商品价格
            newfav.setCfmurl(url);//商品链接
            newfav.setCfuseraccount(account);//收藏用户
            LocalDateTime now = LocalDateTime.now();
            newfav.setCftime(now);//收藏时间
            save(newfav);
            return;
        } else if (iAllmcreditService.list(allmcreditQueryWrapper).size() != 0) {
            Allmcredit merchandise = new Allmcredit();
            merchandise = iAllmcreditService.getOne(allmcreditQueryWrapper);
            newfav.setCfimage(merchandise.getAmcimage());//商品图片
            newfav.setCfmname(merchandise.getAmcname());//商品名称
            newfav.setCfmplatform(merchandise.getPlatform());//商品平台
            newfav.setCfmprice(merchandise.getPrice());//商品价格
            newfav.setCfmurl(url);//商品链接
            newfav.setCfuseraccount(account);//收藏用户
            LocalDateTime now = LocalDateTime.now();
            newfav.setCftime(now);//收藏时间
            save(newfav);
            return;
        } else if (iAllmpriceService.list(allmpriceQueryWrapper).size() != 0) {
            Allmprice merchandise = new Allmprice();
            merchandise = iAllmpriceService.getOne(allmpriceQueryWrapper);
            newfav.setCfimage(merchandise.getAmpimage());
            newfav.setCfmname(merchandise.getAmpname());
            newfav.setCfmplatform(merchandise.getPlatform());
            newfav.setCfmprice(merchandise.getAmpprice());
            newfav.setCfmurl(url);
            newfav.setCfuseraccount(account);
            LocalDateTime now = LocalDateTime.now();
            newfav.setCftime(now);
            save(newfav);
            return;
        }
        return;
    }
}