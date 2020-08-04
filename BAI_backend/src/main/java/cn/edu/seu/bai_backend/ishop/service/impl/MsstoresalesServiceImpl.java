package cn.edu.seu.bai_backend.ishop.service.impl;

import cn.edu.seu.bai_backend.ishop.entity.Msstoresales;
import cn.edu.seu.bai_backend.ishop.mapper.MsstoresalesMapper;
import cn.edu.seu.bai_backend.ishop.service.IMsstoresalesService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nqr
 * @since 2020-07-20
 */
@Service
public class MsstoresalesServiceImpl extends ServiceImpl<MsstoresalesMapper, Msstoresales> implements IMsstoresalesService {


    @Override
    public List<Msstoresales> getMsstoreSalesList(String type) {
        System.out.println("进入getMsstoreSalesList");
        QueryWrapper<Msstoresales> queryWrapper = new QueryWrapper<>();
        //按照信誉分数降序排序
        queryWrapper.eq("mssstype",type);
        queryWrapper.orderByDesc("mssssalesscore");
        List<Msstoresales> msstoresalesList = list(queryWrapper);
        System.out.println("得到List<Msstoresales>");
        return msstoresalesList;
    }

}