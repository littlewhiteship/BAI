package cn.edu.seu.bai_backend.ishop.service.impl;

import cn.edu.seu.bai_backend.ishop.entity.Mminfo;
import cn.edu.seu.bai_backend.ishop.mapper.MminfoMapper;
import cn.edu.seu.bai_backend.ishop.service.IMminfoService;
import cn.edu.seu.bai_backend.ishop.service.PriceSpread;
import cn.edu.seu.bai_backend.my.entity.Merchantextrainfo;
import cn.edu.seu.bai_backend.my.mapper.MerchantextrainfoMapper;
import cn.edu.seu.bai_backend.my.service.IMerchantextrainfoService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nqr
 * @since 2020-07-20
 */
@Service
public class MminfoServiceImpl extends ServiceImpl<MminfoMapper, Mminfo> implements IMminfoService {



    private static String REQ_URL = "";
    MminfoMapper newmminfoMapper = new MminfoMapper() {
        @Override
        public int insert(Mminfo entity) {
            return 0;
        }

        @Override
        public int deleteById(Serializable id) {
            return 0;
        }

        @Override
        public int deleteByMap(Map<String, Object> columnMap) {
            return 0;
        }

        @Override
        public int delete(Wrapper<Mminfo> wrapper) {
            return 0;
        }

        @Override
        public int deleteBatchIds(Collection<? extends Serializable> idList) {
            return 0;
        }

        @Override
        public int updateById(Mminfo entity) {
            return 0;
        }

        @Override
        public int update(Mminfo entity, Wrapper<Mminfo> updateWrapper) {
            return 0;
        }

        @Override
        public Mminfo selectById(Serializable id) {
            return null;
        }

        @Override
        public List<Mminfo> selectBatchIds(Collection<? extends Serializable> idList) {
            return null;
        }

        @Override
        public List<Mminfo> selectByMap(Map<String, Object> columnMap) {
            return null;
        }

        @Override
        public Mminfo selectOne(Wrapper<Mminfo> queryWrapper) {
            return null;
        }

        @Override
        public Integer selectCount(Wrapper<Mminfo> queryWrapper) {
            return null;
        }

        @Override
        public List<Mminfo> selectList(Wrapper<Mminfo> queryWrapper) {
            return null;
        }

        @Override
        public List<Map<String, Object>> selectMaps(Wrapper<Mminfo> queryWrapper) {
            return null;
        }

        @Override
        public List<Object> selectObjs(Wrapper<Mminfo> queryWrapper) {
            return null;
        }

        @Override
        public <E extends IPage<Mminfo>> E selectPage(E page, Wrapper<Mminfo> queryWrapper) {
            return null;
        }

        @Override
        public <E extends IPage<Map<String, Object>>> E selectMapsPage(E page, Wrapper<Mminfo> queryWrapper) {
            return null;
        }
    };
    MerchantextrainfoMapper newmerchantextrainfoMapper = new MerchantextrainfoMapper() {
        @Override
        public int insert(Merchantextrainfo entity) {
            return 0;
        }

        @Override
        public int deleteById(Serializable id) {
            return 0;
        }

        @Override
        public int deleteByMap(Map<String, Object> columnMap) {
            return 0;
        }

        @Override
        public int delete(Wrapper<Merchantextrainfo> wrapper) {
            return 0;
        }

        @Override
        public int deleteBatchIds(Collection<? extends Serializable> idList) {
            return 0;
        }

        @Override
        public int updateById(Merchantextrainfo entity) {
            return 0;
        }

        @Override
        public int update(Merchantextrainfo entity, Wrapper<Merchantextrainfo> updateWrapper) {
            return 0;
        }

        @Override
        public Merchantextrainfo selectById(Serializable id) {
            return null;
        }

        @Override
        public List<Merchantextrainfo> selectBatchIds(Collection<? extends Serializable> idList) {
            return null;
        }

        @Override
        public List<Merchantextrainfo> selectByMap(Map<String, Object> columnMap) {
            return null;
        }

        @Override
        public Merchantextrainfo selectOne(Wrapper<Merchantextrainfo> queryWrapper) {
            return null;
        }

        @Override
        public Integer selectCount(Wrapper<Merchantextrainfo> queryWrapper) {
            return null;
        }

        @Override
        public List<Merchantextrainfo> selectList(Wrapper<Merchantextrainfo> queryWrapper) {
            return null;
        }

        @Override
        public List<Map<String, Object>> selectMaps(Wrapper<Merchantextrainfo> queryWrapper) {
            return null;
        }

        @Override
        public List<Object> selectObjs(Wrapper<Merchantextrainfo> queryWrapper) {
            return null;
        }

        @Override
        public <E extends IPage<Merchantextrainfo>> E selectPage(E page, Wrapper<Merchantextrainfo> queryWrapper) {
            return null;
        }

        @Override
        public <E extends IPage<Map<String, Object>>> E selectMapsPage(E page, Wrapper<Merchantextrainfo> queryWrapper) {
            return null;
        }
    };

    @Autowired
    IMerchantextrainfoService iMerchantextrainfoService;

    private Object LocalDate;

    @Override
    public List<Mminfo> IshopMerchandise(int ishopid) {
        getStoreHLPrice(ishopid);
        QueryWrapper<Mminfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mmishopid", ishopid);
        List<Mminfo> MminfoList = list(queryWrapper);
        return MminfoList;
    }

    @Override
    public boolean DaylyRenew() throws InterruptedException {
        //对每一个商家每日更新一次数据
        String dateTime = getDateTime();
        List<Merchantextrainfo> merchantList = allMerchant();
        for(int i = 0;i<merchantList.size();i++)
        {
            //获取店铺链接
            REQ_URL=merchantList.get(i).getMeshoplink();
            //对店铺内容进行爬取
            // mmtaobaospider mmtaobaospider = new mmtaobaospider();
            //设置爬虫所爬店铺链接为REQ_URL
            // mmtaobaospider.setUrl(REQ_URL);
            // mmtaobaospider. getUrlList();
            // mmtaobaospider.requestData();
            //计算两日销量差将信息填入Mmdetailedinfo表格中
            //-------------------------------------
        }
        return true;
    }

    @Override
    public List<Merchantextrainfo> allMerchant() {
        QueryWrapper<Merchantextrainfo> queryWrapper = new QueryWrapper<>();
        List<Merchantextrainfo> merchantextrainfoList = newmerchantextrainfoMapper.selectList(queryWrapper);
        return merchantextrainfoList;
    }

    @Override
    public String getDateTime() {
        String datetime = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
        System.out.println(datetime);
        return datetime;
    }

    @Override
    public Date getDate1() {
        LocalDate localDate = null;
        return null;
    }

    @Override
    public List<PriceSpread> getPriceSpread(int shopid) {
        QueryWrapper<Mminfo> queryWrapper = new QueryWrapper<>();
        //寻找该店铺内商品列表并按照价格升序排列
        queryWrapper.eq("mmishopid",shopid);
        queryWrapper.orderByAsc("mmiprice");
        List<Mminfo> mminfoList = list(queryWrapper);
        System.out.println(mminfoList);
        //计算各个价格区间的销量统计数据，将结果体现在priceSpreadList中

        //获取店铺最高价格与最低价格
        QueryWrapper<Merchantextrainfo> queryWrapper1 = new QueryWrapper<>();
        queryWrapper.eq("meid",shopid);
        Merchantextrainfo merchantextrainfo = iMerchantextrainfoService.getOne(queryWrapper1);
        System.out.println(merchantextrainfo);
        System.out.println("即将计算最低价");
        Mminfo mminfoL = mminfoList.get(0);
        System.out.println("输出最低价格商品"+mminfoL);
        float lP = mminfoL.getMmiprice();
        System.out.println("lp="+lP);
        int lowestPrice = ((mminfoList.get(0).getMmiprice().intValue())-2);//merchantextrainfo.getMeminprice().intValue();
        System.out.println("最低");
        System.out.println(lowestPrice);
        System.out.println("即将计算最高价");
        int highestPrice = ((mminfoList.get(mminfoList.size()-1).getMmiprice().intValue())+2);//merchantextrainfo.getMemaxprice().intValue();
        System.out.println("最高");
        System.out.println(highestPrice);
        //计算五个区间段,放入fiveIntervalSegment中
        List<Integer> fiveIntervalSegment = new ArrayList<>();

        int Interval = ((highestPrice-lowestPrice)/5+1);
        System.out.println("区间");
        System.out.println(Interval);
        for(int i=0;i<6;i++){
            int tmpPrice = 0;
            tmpPrice = lowestPrice+i*Interval;
            fiveIntervalSegment.add(i,tmpPrice);
        }
        System.out.println(fiveIntervalSegment);

        //遍历所有商品，统计各个价格区间段销量
        List<PriceSpread> priceSpreadList = new ArrayList<>();
        for(int i=0;i<5;i++){
            int price = (fiveIntervalSegment.get(i)+fiveIntervalSegment.get(i+1))/2;
            int volume = 0;
            PriceSpread priceSpread = new PriceSpread();
            priceSpread.setPriceSpread(price,volume);
            priceSpreadList.add(priceSpread);
        }
        System.out.println(priceSpreadList);

        int priceIntervalP = 1;
        for(int i=0;i<mminfoList.size();i++){
            //mminfoList中价格按升序排列
            //若当前商品的价格比当前区间的上限高，则区间要变化
            System.out.println("商品价格");
            System.out.println(mminfoList.get(i).getMmiprice().intValue());
            System.out.println("区间上限");
            System.out.println(fiveIntervalSegment.get(priceIntervalP));
            while(mminfoList.get(i).getMmiprice().intValue()>fiveIntervalSegment.get(priceIntervalP)){
                //区间变化
                System.out.println("区间变化");
                priceIntervalP++;
            }
            System.out.println("区间上限");
            System.out.println(fiveIntervalSegment.get(priceIntervalP));
            //否则仍在当前区间中，区间不变
            //当前区间的销量加上当前商品的销量
            int tmpSalesVolume = mminfoList.get(i).getMmisalesvolume()+priceSpreadList.get(priceIntervalP-1).getSalesVolume();
            System.out.println("该段销量变为");
            System.out.println(tmpSalesVolume);
            int price = priceSpreadList.get(priceIntervalP-1).getPrice();
            //int salesVolume = priceSpreadList.get(priceIntervalP-1).getSalesVolume();
            PriceSpread priceSpread = new PriceSpread();
            priceSpread.setPriceSpread(price,tmpSalesVolume);
            priceSpreadList.set(priceIntervalP-1,priceSpread);
        }

        return priceSpreadList;
    }

    /*
    @Override
    public List<Blogger> getIntroBlogger(int shopid) {
        QueryWrapper<Merchantextrainfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("meid",shopid);
        Merchantextrainfo merchantextrainfo = newmerchantextrainfoMapper.selectOne(queryWrapper);
        //String link = merchantextrainfo.getMeshoplink();
        String type = merchantextrainfo.getMemerchandisetype();
        //根据店铺类型进行爬虫
        //返回内容为List<Blogger>,即为商品信息
        return null;
    }

     */

    @Override
    public void getStoreHLPrice(int shopid) {
        QueryWrapper<Mminfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mmishopid", shopid);
        queryWrapper.orderByAsc("mmiprice");
        List<Mminfo> MminfoList = list(queryWrapper);
        Mminfo mminfoL = MminfoList.get(0);
        int size = MminfoList.size();
        Mminfo mminfoH = MminfoList.get(size-1);
        float lowestPrice = mminfoL.getMmiprice();
        float highestPrice = mminfoH.getMmiprice();
        //找到店铺内商品的最低最高价格后存入t_merchantextrainfo表中
        QueryWrapper<Merchantextrainfo> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("meid", shopid);
        Merchantextrainfo merchantextrainfo = iMerchantextrainfoService.getOne(queryWrapper1);
        merchantextrainfo.setMemaxprice(highestPrice);
        merchantextrainfo.setMeminprice(lowestPrice);
        QueryWrapper<Merchantextrainfo> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("meid", shopid);
        //---------------存进去
        //  iMerchantextrainfoService.saveOrUpdate();
        //iMerchantextrainfoService.save(merchantextrainfo);
    }

    @Override
    public List<Mminfo> searchIshopM(int shopid, String content) {
        QueryWrapper<Mminfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mmishopid", shopid);
        queryWrapper.like("mminame",content);
        List<Mminfo> MminfoList = list(queryWrapper);
        return MminfoList;
    }

    @Override
    public String getShopType(int shopid) {

        QueryWrapper<Merchantextrainfo> queryWrapper = new QueryWrapper<>();
        //按照信誉分数降序排序
        //按照信誉分数降序排序
        queryWrapper.eq("meid",shopid);
        String type = iMerchantextrainfoService.getOne(queryWrapper).getMemerchandisetype();
        return type;

    }
}
