package cn.edu.seu.bai_backend.ishop.service;

import cn.edu.seu.bai_backend.ishop.entity.Msstoresales;
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
public interface IMsstoresalesService extends IService<Msstoresales> {
    //获取推荐店铺按销量排序表
    List<Msstoresales> getMsstoreSalesList(String type);
}
