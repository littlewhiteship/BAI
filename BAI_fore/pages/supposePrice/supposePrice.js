var wxCharts = require('../../utils/wxchart.js');
// pages/supposePrice/supposePrice.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    sales_data:[],
    price_data:[],
    price:0,
  },

  //返回键
  onback:function(){
    wx.navigateBack({
      delta: 1,
    })
  },



  onLoad: function (options) {

    //调用商家获取本店某商品在各个定价下销量接口
    var that = this
    console.log('打印id')
    console.log(app.globalData.shopid)
    console.log('打印data')
    console.log(app.globalData)
   


    console.log("即将开始图片请求")
    wx.request({
      url: app.globalData.servicePath+'/api/ishop/getPriceandSalesVolume?mmid='+options.id,
      success: res => {
        console.log(res.data)
          if(res.data.erroce==0)
          {
            console.log(res.data.data)
            var price= Array()
            var sales =Array()
            var length = res.data.data.length
            for(var i=0;i<length;i++){
                price.push(res.data.data[i].mmdiprice)
                sales.push(res.data.data[i].mmdisalesvolume)
            }
            console.log(price)
            console.log(sales)
            that.setData({
                price_data:price,
                sales_data:sales
            })
            console.log(that.data.price_data)
            console.log(that.data.sales_data)
          }
          else if(res.data.errcode==20010)
          {
            console.log(res.data.errmsg)
          }
        },
        fail:function() {
          console.log("定价销量接口请求数据失败");
        }
    })



 /*   wx.request({
      url: app.globalData.servicePath+'/api/ishop/getPriceandSalesVolume',
        method: 'get', 
        data:{
          "mmid":options.id,
          },
        header: {  //请求头
        "Content-Type": "application/x-www-form-urlencoded"
        },

        success:function(res){
          console.log(res.data)
          if(res.data.erroce==0)
          {
            console.log(res.data.data)
            var price= Array()
            var sales =Array()
            var length = res.data.data.length
            for(var i=0;i<length;i++){
                price.push(res.data.data[i].mmdiprice)
                sales.push(res.data.data[i].mmdisalesvolume)
            }
            console.log(price)
            console.log(sales)
            that.setData({
                price_data:price,
                sales_data:sales
            })
            console.log(that.data.price_data)
            console.log(that.data.sales_data)
          }
          else if(res.data.errcode==20010)
          {
            console.log(res.data.errmsg)
          }
        },
        fail:function() {
          console.log("定价销量接口请求数据失败");
        }
    })

  */  

   console.log("即将开始价格请求")
    //调用建议零售价接口
    wx.request({   //请求地址
    url: app.globalData.servicePath+'/api/ishop/getIntroPrice',
    method: 'get',    
    data:{
    "mmid":options.id,
    },
    header: {  //请求头
    "Content-Type": "application/x-www-form-urlencoded"
    },
    success:function (res) {
    //打印后台返回的数据
    console.log("获取商品定价")
    console.log(res.data)
    //如果返回的是获取成功,导入列表
    if(res.data.erroce==0)
    {
      that.setData({ price: res.data.data})
    }
    else if(res.data.errcode==20006)
    {
      console.log(res.data.errmsg)
    }
    else if(res.data.errcode==20005)
    {
      wx.showModal({
        title: '提示',
        content: res.data.errmsg,
        showCancel:false,
        success (res) {
          if (res.confirm) {
            console.log('用户点击确定')
          }
        }
        })
    }
    else{
      console.log("商品定价返回未知errcode")
    }
    },
    fail:function() {
    console.log("商品定价接口请求数据失败");
    },
    })

        
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

    lineChart = new wxCharts({
      canvasId: 'lineCanvas',
      type: 'line',
      categories:  this.data.price_data,
      animation: true,
      // background: '#f5f5f5',
      series: [{
          name: '销量',
          data: this.data.sales_data,
      }, 
      ],
      xAxis: {
          disableGrid: true
      },
      yAxis: {
          title: '销量（件）',
          format: function (val) {
              return val+'件';
          },
          min: 0
      },
      width: 300,
      height: 200,
      dataLabel: false,
      dataPointShape: true,
      extra: {
          lineStyle: 'curve'
      }
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