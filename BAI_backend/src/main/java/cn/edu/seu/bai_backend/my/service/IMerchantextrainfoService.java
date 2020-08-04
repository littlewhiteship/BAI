package cn.edu.seu.bai_backend.my.service;

import cn.edu.seu.bai_backend.my.entity.Merchantextrainfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author songyifan
 * @since 2020-07-20
 */
public interface IMerchantextrainfoService extends IService<Merchantextrainfo> {
    boolean VerifyMerchant(String account, String url, String mtype, String platform);
    //进行商家身份验证
    void IFRemoveMerchant();
    //判断商家信誉是否符合要求
}
