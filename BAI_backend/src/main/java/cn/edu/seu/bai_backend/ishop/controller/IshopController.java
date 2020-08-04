package cn.edu.seu.bai_backend.ishop.controller;

import cn.edu.seu.bai_backend.ishop.entity.Blogger;
import cn.edu.seu.bai_backend.ishop.entity.DailySV;
import cn.edu.seu.bai_backend.ishop.entity.Mminfo;
import cn.edu.seu.bai_backend.ishop.entity.PriceandSalesVolume;
import cn.edu.seu.bai_backend.ishop.service.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author nqr
 * @since 2020-07-20
 */
@RestController
@RequestMapping("/ishop")
public class IshopController {

    @Autowired
    private IMminfoService mminfoService;
    @Autowired
    private IMmdetailedinfoService mmdetailedinfoService;
    @Autowired
    private IMsstorecreditService msstorecreditService;

    @Autowired
    private IBloggerService bloggerService;



    //获取ishop内商品内容
    @RequestMapping(value = "/getIshopMerchandise",method = RequestMethod.GET)
    public JSONObject getIshopMerchandise(int shopid) {
        JSONObject jsonObject = new JSONObject();
        try {
            List<Mminfo> mminfoList = mminfoService.IshopMerchandise(shopid);
            if(mminfoList.size()==0){
                jsonObject.put("errcode","20015");
                jsonObject.put("errmsg","无该店铺数据");
                return jsonObject;
            }
            //mminfoService.getStoreHLPrice(shopid);
            jsonObject.put("erroce","0");
            jsonObject.put("data",mminfoList);
        }catch (Exception ex){
            jsonObject.put("errcode","20001");
            jsonObject.put("errmsg","数据查询错误");
        }
        return jsonObject;
    }

    //搜索ishop内商品内容
    @RequestMapping(value = "/searchIshopMerchandise",method = RequestMethod.GET)
    public JSONObject searchIshopMerchandise(int shopid,String content) {
        JSONObject jsonObject = new JSONObject();
        try {
            List<Mminfo> mminfoList = mminfoService.searchIshopM(shopid,content);
            if(mminfoList.size()==0){
                jsonObject.put("errcode","20014");
                jsonObject.put("errmsg","无该店铺数据");
                return jsonObject;
            }
            //mminfoService.getStoreHLPrice(shopid);
            jsonObject.put("erroce","0");
            jsonObject.put("data",mminfoList);
        }catch (Exception ex){
            jsonObject.put("errcode","20013");
            jsonObject.put("errmsg","数据查询错误");
        }
        return jsonObject;
    }


    //获取类似店铺的信息
    @RequestMapping(value = "/getsimilarstore", method = RequestMethod.GET)
    public JSONObject getSimilarStore(int shopid,double creditscore,double salescore) {
        JSONObject jsonObject = new JSONObject();
        try {
            String type = mminfoService.getShopType(shopid);
            List<Msstore> msstoreList = msstorecreditService.getIntroStoreList(type,(float) creditscore,(float)salescore);
            List<Msstore> msstoreList1 = new ArrayList<>();
            if(msstoreList.size()==0){
                jsonObject.put("errcode","20016");
                jsonObject.put("errmsg","无该类型类似店铺");
                return jsonObject;
            }
            for(int i=0;i<5;i++){
                Msstore msstore = msstoreList.get(i);
                msstoreList1.add(msstore);
            }
            jsonObject.put("erroce","0");
            jsonObject.put("data",msstoreList1);
        }catch (Exception ex){
            jsonObject.put("errcode","20002");
            jsonObject.put("errmsg","数据查询错误");
        }
        return jsonObject;
    }




    //获取本店铺价格与销量分析
    @RequestMapping(value = "/getPriceSpread",method = RequestMethod.GET)
    public JSONObject getPriceSpread(int shopid) {
        JSONObject jsonObject = new JSONObject();
        try {
            List<PriceSpread> priceSpreadList = mminfoService.getPriceSpread(shopid);
            if (priceSpreadList.size()==0){
                jsonObject.put("errcode","20016");
                jsonObject.put("errmsg","无分析数据");
            }
            jsonObject.put("erroce","0");
            jsonObject.put("data",priceSpreadList);
        }catch (Exception ex){
            jsonObject.put("errcode","20003");
            jsonObject.put("errmsg","数据查询错误");
        }
        return jsonObject;
    }



    //获取本店铺推荐主播信息
    @RequestMapping(value = "/getIntroBlogger",method = RequestMethod.GET)
    public JSONObject getIntroBlogger(int shopid) {
        JSONObject jsonObject = new JSONObject();
        try {
            List<Blogger> bloggerList = bloggerService.getIntoBlogger(shopid);
            List<Blogger> bloggerList1 = new ArrayList<>();
            if(bloggerList.size()==0){
                jsonObject.put("errcode","20017");
                jsonObject.put("errmsg","无该类型博主推荐");
                return jsonObject;
            }
            for(int i=0;i<4;i++){
                Blogger blogger = bloggerList.get(i);
                bloggerList1.add(blogger);
            }
            jsonObject.put("erroce","0");
            jsonObject.put("data",bloggerList1);
        }catch (Exception ex){
            jsonObject.put("errcode","20004");
            jsonObject.put("errmsg","数据查询错误");
        }
        return jsonObject;
    }






    //获取店铺推荐价格分析
    @RequestMapping(value = "/getIntroPrice",method = RequestMethod.GET)
    public JSONObject getIntroPrice(int mmid) {
        JSONObject jsonObject = new JSONObject();
        try {
            float introPrice = mmdetailedinfoService.getIntroPrice(mmid);
            System.out.println(introPrice);
            if (introPrice==-1){
                jsonObject.put("errcode","20005");
                jsonObject.put("errmsg","商家入驻未满10日无分析数据");
                return jsonObject;
            }
            jsonObject.put("erroce","0");
            jsonObject.put("data",introPrice);
        }catch (Exception ex){
            jsonObject.put("errcode","20012");
            jsonObject.put("errmsg","数据查询错误");
        }
        System.out.println(jsonObject);
        return jsonObject;
    }


    //获取店铺商品价格和销量关系，各个定价下的销量列表
    @RequestMapping(value = "/getPriceandSalesVolume",method = RequestMethod.GET)
    public JSONObject getPriceandSalesVolume(int mmid) {
        JSONObject jsonObject = new JSONObject();
        try {
            List<PriceandSalesVolume> priceandSalesVolumeList= mmdetailedinfoService.getPriceandVolume(mmid);
            if(priceandSalesVolumeList.size()==0){
                jsonObject.put("errcode","20017");
                jsonObject.put("errmsg","无该商品销量分布表");
                return jsonObject;
            }
            jsonObject.put("erroce","0");
            jsonObject.put("data",priceandSalesVolumeList);
        }catch (Exception ex){
            jsonObject.put("errcode","20010");
            jsonObject.put("errmsg","数据查询错误");
        }
        return jsonObject;
    }



    //获取店铺下一周内推荐进货量
    @RequestMapping(value = "/getIntroVolume",method = RequestMethod.GET)
    public JSONObject getIntroVolume(int mmid) {
        JSONObject jsonObject = new JSONObject();
        try {
            int introVolume = mmdetailedinfoService.getIntroVolume(mmid);
            if (introVolume==-1){
                jsonObject.put("errcode","20007");
                jsonObject.put("errmsg","商家入驻未满10日无分析数据");

            }else {
                jsonObject.put("erroce","0");
                jsonObject.put("data",introVolume);
            }
        }catch (Exception ex){
            jsonObject.put("errcode","20008");
            jsonObject.put("errmsg","数据查询错误");
        }
        return jsonObject;
    }



    //获取店铺该商品最近的销售量与时间列表
    @RequestMapping(value = "/getDailySalesVolume",method = RequestMethod.GET)
    public JSONObject getDailySalesVolume(int mmid) {
        JSONObject jsonObject = new JSONObject();
        try {
            List<DailySV> dailySVList = mmdetailedinfoService.getDailySV(mmid);
            if (dailySVList.size()==0){
                jsonObject.put("errcode","200011");
                jsonObject.put("errmsg","无该商品销量数据");
                return jsonObject;
            }
            List<DailySV> dailySVList1 = new ArrayList<>();
            for(int i=0;i<7;i++){
                DailySV dailySV = dailySVList.get(i);
                dailySVList1.add(dailySV);
            }

            jsonObject.put("erroce","0");
            jsonObject.put("data",dailySVList);
        }catch (Exception ex){
            jsonObject.put("errcode","200011");
            jsonObject.put("errmsg","数据查询错误");
        }
        return jsonObject;
    }





    //管理员每日使用按钮一键刷新
    @RequestMapping(value = "/daylyRenew",method = RequestMethod.GET)
    public JSONObject daylyRenew(int mmid) {
        JSONObject jsonObject = new JSONObject();
        try {
            boolean dalyRenew = mminfoService.DaylyRenew();
            //返回刷新是否成功结果
            jsonObject.put("erroce","0");
            jsonObject.put("data",dalyRenew);
        }catch (Exception ex){
            jsonObject.put("errcode","20009");
            jsonObject.put("errmsg","数据查询错误");
        }
        return jsonObject;
    }
}
