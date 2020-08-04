package cn.edu.seu.bai_backend.ishop.service.impl;

import cn.edu.seu.bai_backend.ishop.service.Msstore;
import cn.edu.seu.bai_backend.ishop.entity.Msstorecredit;
import cn.edu.seu.bai_backend.ishop.entity.Msstoresales;
import cn.edu.seu.bai_backend.ishop.mapper.MsstorecreditMapper;
import cn.edu.seu.bai_backend.ishop.service.IMsstorecreditService;
import cn.edu.seu.bai_backend.ishop.service.IMsstoresalesService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class MsstorecreditServiceImpl extends ServiceImpl<MsstorecreditMapper, Msstorecredit> implements IMsstorecreditService {

    @Autowired
    IMsstoresalesService iMsstoresalesService;

    @Override
    public List<Msstorecredit> getMsstoreCreditList(String type) {
        QueryWrapper<Msstorecredit> queryWrapper = new QueryWrapper<>();
        //按照信誉分数降序排序
        queryWrapper.eq("mssctype",type);
        queryWrapper.orderByDesc("mssccreditscore");
        List<Msstorecredit> msstorecreditList = list(queryWrapper);
        return msstorecreditList;
    }

    /*
    @Override
    public List<Msstore> tmpgetIntroStoreList() {
        List<Msstore> msstoreList = new ArrayList<>();
        return msstoreList;
    }

     */


    @Override
    public List<Msstore> comBineTwoList(List<Msstorecredit> msstorecreditList, List<Msstoresales> msstoresalesList) {
        List<Msstore> finalList = new ArrayList<>();
        //遍历msstoresalesList,观察是否有店铺与msstorecreditList重复
        for(int i=0;i<msstoresalesList.size();i++) {
            System.out.println("i="+i+"以下为内容");
            Msstoresales msstoresales = msstoresalesList.get(i);
            System.out.println("msstoresales="+msstoresales);
            for(int j=0;j<msstorecreditList.size();j++){
                System.out.println("j="+j+"以下为内容");
                Msstorecredit msstorecredit = msstorecreditList.get(j);
                System.out.println("msstorecredit="+msstorecredit);
                //如果出现重复进行合并操作
                float score =0;
                float a =0;
                boolean flag = false;
                System.out.println(msstoresales.getMsssshopname()+'.');
                System.out.println(msstorecredit.getMsscshopname()+'.');
                System.out.println(msstoresales.getMsssshopname().equals(msstorecredit.getMsscshopname()));
                if(msstoresales.getMsssshopname().equals(msstorecredit.getMsscshopname())){
                    //返回时msstorecredit的分数设为两者之和，msstoresales分数设为0
                    score = msstoresales.getMssssalesscore()+msstorecredit.getMssccreditscore();
                    msstorecredit.setMssccreditscore(score);
                    System.out.println("进行合并");
                    System.out.println("msstorecredit="+msstorecredit);
                    msstorecreditList.set(j,msstorecredit);
                    msstoresales.setMssssalesscore(a);
                    System.out.println("msstoresales="+msstoresales);
                    msstoresalesList.set(i,msstoresales);
                    //出现两组数据为同一家店对数据做了改动后再对数据进行重新排序处理
                    flag = true;
                }


                //重新排序：j上浮，i下降
                //使用冒泡排序思想
                //如果j不在第1位则可能上浮
                while(j>0&&flag==true)
                {
                    System.out.println("满足j不在第1位则可能上浮");
                    //如果比其上一位分数高，则两个互换,否则不改变
                    float jcreditscore = msstorecreditList.get(j).getMssccreditscore();
                    float jcreditminusscore = msstorecreditList.get(j-1).getMssccreditscore();
                    if(jcreditscore>jcreditminusscore)
                    {
                        msstorecreditList.get(j).setMssccreditscore(jcreditminusscore);
                        msstorecreditList.get(j-1).setMssccreditscore(jcreditscore);
                        j--;
                    }
                    else{
                        //如果j位比其前一位小则跳出循环
                        break;
                    }
                }
                //如果i不在最后则可能下降
                while(i<msstoresalesList.size()-1&&flag==true)
                {
                    //如果比其下一位分数低则两个互换，否则不改变
                    float isalescore = msstoresalesList.get(i).getMssssalesscore();
                    float iplussalesscore = msstoresalesList.get(i+1).getMssssalesscore();
                    if(isalescore<iplussalesscore)
                    {
                        msstoresalesList.get(i).setMssssalesscore(iplussalesscore);
                        msstoresalesList.get(i).setMssssalesscore(isalescore);
                        i++;
                    }
                    else {
                        //如果i位比下一位数据高则不改变,退出循环
                        break;
                    }
                }
                System.out.println(msstorecreditList);
                System.out.println(msstoresalesList);


            }
        }

        //与归并排序的merge类似，合并两个有序数组
        int salesP = 0,creditP = 0;
        //遍历
        //for(int i=0;i<(msstoresalesList.size()+msstorecreditList.size());i++){
        System.out.println("开始两路归并");
        while ((salesP<msstoresalesList.size())||(creditP<msstorecreditList.size())){
            System.out.println("至少还有一组数据还没有结束");
            if ((salesP>=msstoresalesList.size())||(creditP>=msstorecreditList.size())) {
                //如果有过界的数据，则对另一个没过界的遍历，拼接非零项
                System.out.println("有过界数据");
                if(salesP<msstoresalesList.size())
                {
                    System.out.println("salesP没过界");
                    while (salesP<msstorecreditList.size()){
                        if (msstoresalesList.get(salesP).getMssssalesscore()!=0){
                            //若分数非0进行拼接
                            //转换类型，进行拼接
                            Msstoresales msstoresales= msstoresalesList.get(salesP);
                            Msstore msstore = changeMSTypeToMsstore(msstoresales);
                            System.out.println(msstore);
                            salesP++;
                            finalList.add(msstore);
                        }
                        else {
                            return finalList;
                        }
                    }
                }
                else {
                    while (creditP<msstorecreditList.size()){
                        if (msstorecreditList.get(creditP).getMssccreditscore()!=0){
                            //若分数非0进行拼接
                            //类型相同可直接进行拼接
                            Msstore msstore =changeMCTypeToMsstore(msstorecreditList.get(creditP));
                            System.out.println(msstore);
                            creditP++;
                            finalList.add(msstore);
                        }
                        else {
                            return finalList;
                        }
                    }
                }
                break;
            }else {
                Msstoresales msstoresales = msstoresalesList.get(salesP);
                Msstorecredit msstorecredit = msstorecreditList.get(creditP);
                //两个都没有没有过界且两个该处值不为0
                if ((msstoresales.getMssssalesscore()>0)&&(msstorecredit.getMssccreditscore()>0))
                {
                    System.out.println("两个都没有没有过界且两个该处值不为0");
                    //找出两者中大的
                    float tmpscore=0;
                    float msssalesscore=msstoresales.getMssssalesscore();
                    System.out.println("msssalesscore"+msssalesscore);
                    float msscreditscore=msstorecredit.getMssccreditscore();
                    System.out.println("msscreditscore"+msscreditscore);
                    //msstorecredit.getMssccreditscore()>msstoresales.getMssssalesscore()?tmpscore=msstorecredit.getMssccreditscore():tmpscore=msstoresales.getMssssalesscore();

                    //将更大的数据添加至finalList中，并调整对应指针p的位置
                    if(msscreditscore>msssalesscore)
                    {
                        System.out.println("msscreditscore>msssalesscore");
                        Msstore msstore = changeMCTypeToMsstore(msstorecredit);
                        finalList.add(msstore);
                        creditP++;
                    }
                    else {
                        System.out.println("msscreditscore<=msssalesscore");
                        //转换类型，将msstoresales类型数据转换为msstore存放
                        Msstore msstore = changeMSTypeToMsstore(msstoresales);
                        finalList.add(msstore);
                        salesP++;
                    }
                }
                else {
                    System.out.println("有为0的数据");
                    //有为0的数据
                    //找到当前数据不为0的数组，添加入其中非0数据
                    if(msstoresales.getMssssalesscore()>0){
                        System.out.println("非零数组为msstoresales");
                        //1.-------------------------------
                        while(salesP<msstoresalesList.size()){
                            if (msstoresales.getMssssalesscore()!=0){
                                msstoresales = msstoresalesList.get(salesP);
                                Msstore msstore = changeMSTypeToMsstore(msstoresales);
                                finalList.add(msstore);
                                System.out.println("非零数组为msstoresales");
                                System.out.println(msstore.getMssdescriptionpoints());
                                System.out.println(msstore.getMsslogisticsspeedpoints());
                                System.out.println(msstore.getMssservepoints());
                                salesP++;
                            }
                            else {
                                break;
                            }
                        }
                        break;
                    }
                    else if(msstorecredit.getMssccreditscore()>0){
                        System.out.println("非零数组为msstorecredit");
                        while ((creditP<msstorecreditList.size())&&msstorecredit.getMssccreditscore()>0){
                            msstorecredit=msstorecreditList.get(creditP);
                            Msstore msstore = changeMCTypeToMsstore(msstorecredit);
                            finalList.add(msstore);
                            System.out.println("非零数组为msstorecredit");
                            System.out.println(msstore.getMssdescriptionpoints());
                            System.out.println(msstore.getMsslogisticsspeedpoints());
                            System.out.println(msstore.getMssservepoints());
                            creditP++;
                        }
                    }
                }
            }

        }
        System.out.println("结束计算即将返回");
        System.out.println(finalList);
        System.out.println(finalList.get(0));
        System.out.println(finalList.get(1));
        return finalList;
    }

    @Override
    public Msstorecredit changeType(Msstoresales msstoresales) {
        int id = msstoresales.getMsssid();
        String url = msstoresales.getMsssurl();
        Float descriptionpoints = msstoresales.getMsssdescriptionpoints();
        Float logisticsspeedpoints = msstoresales.getMssslogisticsspeedpoints();
        Float servepoints = msstoresales.getMsssservepoints();
        String shopname = msstoresales.getMsssshopname();
        int shopid = msstoresales.getMsssshopid();
        String type = msstoresales.getMssstype();
        Float creditscore = msstoresales.getMssssalesscore();
        String image = msstoresales.getMsssimage();
        Msstorecredit msstorecredit = new Msstorecredit();
        msstorecredit.setMsstorecredit(id,url,descriptionpoints,logisticsspeedpoints,servepoints,shopname,shopid,type,creditscore,image);
        return msstorecredit;
    }

    //变换类型为Msstore列表
    @Override
    public Msstore changeMCTypeToMsstore(Msstorecredit msstorecredit) {
        String url = msstorecredit.getMsscurl();
        Float descriptionpoints = msstorecredit.getMsscdescriptionpoints();
        Float logisticsspeedpoints = msstorecredit.getMssclogisticsspeedpoints();
        Float servepoints = msstorecredit.getMsscservepoints();
        String shopname = msstorecredit.getMsscshopname();
        String type = msstorecredit.getMssctype();
        String image = msstorecredit.getMsscimage();
        Msstore msstore = new Msstore();
        msstore.setMsstore(url,descriptionpoints,logisticsspeedpoints ,servepoints,shopname,type,image);
        System.out.println(msstore);
        System.out.println("changeMCTypeToMsstore");
        System.out.println(msstore.getMssdescriptionpoints());
        System.out.println(msstore.getMsslogisticsspeedpoints());
        System.out.println(msstore.getMssservepoints());
        return msstore;
    }

    //变换类型为Msstore列表
    @Override
    public Msstore changeMSTypeToMsstore(Msstoresales msstoresales) {
        String url = msstoresales.getMsssurl();
        Float descriptionpoints = msstoresales.getMsssdescriptionpoints();
        Float logisticsspeedpoints = msstoresales.getMssslogisticsspeedpoints();
        Float servepoints = msstoresales.getMsssservepoints();
        String shopname = msstoresales.getMsssshopname();
        String type = msstoresales.getMssstype();
        String image = msstoresales.getMsssimage();
        Msstore msstore = new Msstore();
        msstore.setMsstore(url,descriptionpoints,logisticsspeedpoints,servepoints,shopname,type,image);
        System.out.println(msstore);
        System.out.println("changeMSTypeToMsstore");
        System.out.println(msstore.getMssdescriptionpoints());
        System.out.println(msstore.getMsslogisticsspeedpoints());
        System.out.println(msstore.getMssservepoints());
        return msstore;
    }

    //返回值为推荐店铺列表
    @Override
    public List<Msstore> getIntroStoreList(String type,float credit1,float sale1) {

        List<Msstorecredit> msstorecreditList = getMsstoreCreditList(type);
        for (int i=0;i<msstorecreditList.size();i++){
            System.out.println("Msstorecredit进入重赋分");
            Msstorecredit msstorecredit= msstorecreditList.get(i);
            double tmpscore =(msstorecredit.getMssccreditscore()*credit1/100);
            msstorecredit.setMssccreditscore((float) tmpscore);
            msstorecreditList.set(i,msstorecredit);
        }
        System.out.println(msstorecreditList);
        List<Msstoresales> msstoresalesList = iMsstoresalesService.getMsstoreSalesList(type);
        System.out.println("即将进入重赋分");
        System.out.println("msstoresalesList.size()="+msstoresalesList.size());
        for(int i=0;i<msstoresalesList.size();i++){
            System.out.println("Msstoresales进入重赋分");
            Msstoresales msstoresales = msstoresalesList.get(i);
            System.out.println("msstoresales="+msstoresales);
            System.out.println("sale="+sale1);
            double tmpscore = (msstoresales.getMssssalesscore()*sale1/100);
            System.out.println("tmpscore="+tmpscore);
            msstoresales.setMssssalesscore((float) tmpscore);
            msstoresalesList.set(i,msstoresales);
        }
        System.out.println(msstoresalesList);
        List<Msstore> msstoreList =comBineTwoList(msstorecreditList,msstoresalesList);
        return msstoreList;
    }



}
