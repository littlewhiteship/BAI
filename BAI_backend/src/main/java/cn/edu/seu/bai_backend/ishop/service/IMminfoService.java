package cn.edu.seu.bai_backend.ishop.service;

import cn.edu.seu.bai_backend.ishop.entity.Mminfo;
import cn.edu.seu.bai_backend.my.entity.Merchantextrainfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author nqr
 * @since 2020-07-20
 */
public interface IMminfoService extends IService<Mminfo> {

    //返回店家登录展示的ishop界面的商品信息
    public List<Mminfo> IshopMerchandise(int ishopid);

    //进行每日商品销售量更新
    public boolean DaylyRenew() throws InterruptedException;

    //返回所有商家额外信息
    public List<Merchantextrainfo> allMerchant();

    //返回当日时间
    public String getDateTime();

    //返回日期
    public Date getDate1();

    //寻找该店铺内商品列表并按照价格升序排列
    public List<PriceSpread> getPriceSpread(int shopid);

    //寻找对应该店铺的推荐主播
    //public List<Blogger> getIntroBlogger(int shopid);

    //获得店铺最高价格与最低价格
    public void getStoreHLPrice(int shopid);

    //获取店铺内搜索内容
    public List<Mminfo> searchIshopM(int shopid,String content);

    //获取店铺type
    public String getShopType(int shopid);

}