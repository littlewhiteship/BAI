package cn.edu.seu.bai_backend.ibuy.controller;

import cn.edu.seu.bai_backend.ibuy.entity.IntroMerchandise;
import cn.edu.seu.bai_backend.ibuy.service.IAllmcreditService;
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
@RequestMapping("/ibuy")
public class IbuyController {
    @Autowired
    IAllmcreditService iAllmcreditService;

    //获取搜索结果信息
    @RequestMapping(value = "/getIntroMerchandise",method = RequestMethod.GET)
    //public JSONObject getIntroMerchandise(int creditscore, int pricescore, int salescredit,String content,boolean ispriceStrict,int lowest,int highest) {
    //public JSONObject getIntroMerchandise(float creditscore, float pricescore, float salescredit,String content,boolean ispriceStrict,int lowest,int highest) {
    public JSONObject getIntroMerchandise(int cs, int ps, int ss,String content,boolean ispriceStrict,int lowest,int highest) {
        float creditscore = (float)  cs;
        float pricescore = (float) ps;
        float salesscore = (float) ss;
        JSONObject jsonObject = new JSONObject();
        String platform = null;
        try {
            List<IntroMerchandise> introMerchandiseList = new ArrayList<>();
            List<IntroMerchandise> introMerchandiseList1 = new ArrayList<>();
            for(int i=0;i<3;i++){
                introMerchandiseList=null;
                if (i==0){
                    platform="京东商城";
                }else if (i==1){
                    platform="苏宁易购";
                }else if(i==2){
                    platform = "天猫商城";
                }
                introMerchandiseList=iAllmcreditService.getIntroMList(creditscore, pricescore, salesscore,content,platform,ispriceStrict,lowest,highest);
                for (int j=0;j<4;j++){
                    introMerchandiseList1.add(introMerchandiseList.get(j));
                }
            }
            if(introMerchandiseList1.size()==0){
                jsonObject.put("errcode","40002");
                jsonObject.put("errmsg","无相应数据");
                return null;
            }
            //输入的三个分数以百分计算
            jsonObject.put("erroce","0");
            jsonObject.put("data",introMerchandiseList1);
        }catch (Exception ex){
            jsonObject.put("errcode","40001");
            jsonObject.put("errmsg","数据查询错误");
        }
        return jsonObject;
    }
}
