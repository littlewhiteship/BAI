var wxCharts = require('../../utils/wxchart.js');
const app = getApp()
// pages/shopAnalysis/shopAnalysis.js
Page({
  properties: {
    // 外部传入数据
    
  },
  data: {
    option: [
      { text: '店铺销量分析', value: 0 },
      { text: '同类商铺参考', value: 1 },
      { text: '优质带货博主推荐', value: 2 },
    ],
    value:0,
    //下拉菜单的选择
    showview1:true,
    showview2:false,
    showview3:false,
    //销量与信誉权重
    currentValueAmount: 50,
    currentValueTrust:50,
    shopList:[],
    bloggerList:[],
   // salesCategories:[],
  //  salesData:[],
     //初始默认价格销量值(数组形式)
     sales_data: [15, 20, 45, 37],
     price_data: [20, 30, 40, 50],
     //初始默认价格销量值（字典数组形式）
     price_sales_data: [{
       name: '价格1',
       data: 15,
    },
    {
       name: '价格2',
       data: 35,
   }, 
   {
       name: '价格3',
       data: 78,
   }],
  },

  //销量权重赋值
  onDrag1(event) {
    this.setData({
      currentValueAmount: event.detail.value,
    });
  },
  //信誉权重赋值
  onDrag2(event) {
    this.setData({
      currentValueTrust: event.detail.value,
    });
  },

//下拉框选择
onchoice:function (e) {
  var value=e.detail
  if(value==0){
    this.setData({
      showview1:true,
      showview2:false,
      showview3:false,
    })
  }
  else if(value==1){
    this.setData({
      showview1:false,
      showview2:true,
      showview3:false,
    })
  }
  else{//主播界面
    this.setData({
      showview1:false,
      showview2:false,
      showview3:true,
    })

      //调用推荐博主接口
      var that = this
      wx.request({   //请求地址
      url: app.globalData.servicePath+'/api/ishop/getIntroBlogger',
      method: 'get',    
      data:{
      "shopid":app.globalData.shopid,
      },
      header: {  //请求头
      "Content-Type": "application/x-www-form-urlencoded"
      },

      success: function (res) {
      //打印后台返回的数据
      console.log("获取主播信息")
      console.log(res.data)
      //如果返回的是获取成功,导入列表
      if(res.data.erroce==0)
      {
        that.setData({ bloggerList: res.data.data})
      }
      else if(res.data.errcode==20012)
      {
        console.log(res.data.errmsg)
      }
      else{
        console.log("主播接口返回未知errcode")
      }
      },
      fail: function() {
      console.log("主播接口请求数据失败");
      },
      })
  }
},


//返回按钮
onback:function(){
  wx.redirectTo({
    url: '../ishop/ishop',
  })
},

//确定后获取同类型店铺信息
onCheck:function(){
      //调用获取同类型店铺信息接口
      var that = this
      wx.request({   //请求地址
      url: app.globalData.servicePath+'/api/ishop/getsimilarstore',
      method: 'get',    
      data:{
      "shopid":app.globalData.shopid,
      "creditscore":that.data.currentValueTrust,
      "salescore":that.data.currentValueAmount,
      },
      header: {  //请求头
      "Content-Type": "application/x-www-form-urlencoded"
      },

    success: function (res) {
    //打印后台返回的数据
    console.log("获取类似店铺信息")
    console.log(app.globalData.shopid)
    console.log(that.data.currentValueTrust)
    console.log(that.data.currentValueAmount)
    console.log(res.data)
    //如果返回的是获取成功,导入列表
    if(res.data.erroce==0)
    {
      that.setData({ shopList: res.data.data})
    }
    else if(res.data.errcode==20002)
    {
      console.log(res.data.errmsg)
    }
    else{
      console.log("类似店铺接口返回未知errcode")
    }
    },
    fail: function() {
    console.log("类似店铺接口请求数据失败");
    },

    })
},


  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    console.log('打印id')
    console.log(app.globalData.shopid)
    console.log('打印data')
    console.log(app.globalData)
   
    wx.request({
      url: app.globalData.servicePath+'/api/ishop/getPriceSpread',
        method: 'get', 
        data:{
          "shopid":1,
          },
        header: {  //请求头
        "Content-Type": "application/x-www-form-urlencoded"
        },

        success:function(res){
          console.log(res.data)
          if(res.data.erroce==0)
          {
            console.log(res.data.data)
            var price = Array()
            var sales = Array()
            var length = res.data.data.length
            var price_sales = Array()
            console.log(length)

            for(var i=0; i<length;i++){
              var dic = {}
              dic['name']=res.data.data[i].price+'元'
              dic['data']=res.data.data[i].salesVolume
              // dic.add('name',res.data.data[i].price+'元')
              // dic.add('data',res.data.data[i].salesVolume)
              price_sales.push(dic)
              price.push(res.data.data[i].price)
              sales.push(res.data.data[i].salesVolume)
            }
            console.log(price)
            console.log(sales)
            console.log(price_sales)
            that.setData({
              price_data:price,
              sales_data:sales,
              price_sales_data:price_sales
            })
            console.log(that.data.price_data)
            console.log(that.data.sales_data)
            console.log(that.data.price_sales_data)
          }
          else if(res.data.errcode==20003)
          {
            console.log(res.data.errmsg)
          }
        },
        fail: function() {
          console.log("价格销量接口请求数据失败");
        }
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    columnChart = new wxCharts({
      canvasId: 'columnCanvas',
      type: 'column',
      animation: true,
      categories:this.data.price_data,
      series: [{
          name: '销量-价格直方图',
          data: this.data.sales_data,
          format: function (val, name) {
              return val + '件';
          }//直方图键
      }],
      yAxis: {
          format: function (val) {
              return val + '件';
          },
          title: '销量',
          min: 0
      },
      xAxis: {
          format:function(val){
            return val+'元';
          },
          title:'价格',
          disableGrid: false,
          type: 'calibration'
      },
      extra: {
          column: {
              width: 15
          }
      },
      width: 300 ,
      height: 200 ,
  });
  },

  onChange(){
    pieChart = new wxCharts({
      animation: true,
      canvasId: 'pieCanvas',
      type: 'pie',
      series: this.data.price_sales_data,
      width: 300,
      height: 300,
      dataLabel: true,
  });
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  }
})