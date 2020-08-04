package cn.edu.seu.bai_backend.ishop.service;

/*
import cn.edu.seu.bai_backend.ishop.entity.DailySV;
import cn.edu.seu.bai_backend.ishop.entity.Mmdetailedinfo;
import cn.edu.seu.bai_backend.ishop.entity.PriceandSalesVolume;
import cn.edu.seu.bai_backend.ishop.entity.RegressionLine;
 */

import cn.edu.seu.bai_backend.ishop.entity.DailySV;
import cn.edu.seu.bai_backend.ishop.entity.Mmdetailedinfo;
import cn.edu.seu.bai_backend.ishop.entity.PriceandSalesVolume;
import cn.edu.seu.bai_backend.ishop.entity.RegressionLine;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author nqr
 * @since 2020-07-20
 */
public interface IMmdetailedinfoService extends IService<Mmdetailedinfo> {


    //返回推荐售价
    public float getIntroPrice (int mmid);

    //返回推荐进货量
    public int getIntroVolume(int mmid);

    //按照时间降序返回商品详情信息
    public List<Mmdetailedinfo> getMmDetailedInfo(int mmid);

    //返回价格与销量关系表
    public List<PriceandSalesVolume> getPriceandVolume(int mmid);

    //打印回归方程方法
    public void printLine1(RegressionLine line);

    //打印Sum数据方法
    public void printSums1(RegressionLine line);

    //转化为PriceandSalesVolume类型便于输出
    public PriceandSalesVolume changeMmditoPS(Mmdetailedinfo mmdetailedinfo);

    public List<DailySV> getDailySV(int mmid);

    //类型转换，将输出类型转换为DailySV
    public DailySV changeToDSV(Mmdetailedinfo mmdetailedinfo);


}
