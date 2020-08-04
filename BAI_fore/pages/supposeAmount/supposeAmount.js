var wxCharts = require('../../utils/wxchart.js');
const app = getApp()
// pages/supposeAmount/supposeAmount.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
      amount:0,
      date_data:[],
      sales_data:[],
  },

  //返回键
  onback:function(){
    wx.navigateBack({
      delta: 1,
    })
  },


  /**
   * 生命周期函数--监听页面加载
   */
  onLoad:function (options) {

    var that = this
    console.log('打印id')
    console.log(app.globalData.shopid)
    console.log('打印data')
    console.log(app.globalData)

    wx.request({
      url: app.globalData.servicePath+'/api/ishop/getDailySalesVolume?mmid='+options.id,
      success: res => {
        console.log(res.data)
          if(res.data.erroce==0)
          {
            console.log(res.data.data)
            var date= Array()
            var sales =Array()
            var length = res.data.data.length
            for(var i=length-1;i>=0;i--){
                date.push(res.data.data[i].mmdidate.year+'-'+res.data.data[i].mmdidate.monthValue+'-'+res.data.data[i].mmdidate.dayOfMonth)
                sales.push(res.data.data[i].mmdisalesvolume)
            }
            console.log(date)
            console.log(sales)
            that.setData({
                date_data:date,
                sales_data:sales
            })
            console.log(that.data.date_data)
            console.log(that.data.sales_data)
          }
          else if(res.data.errcode==20011)
          {
            console.log(res.data.errmsg)
          }
       
      },
      fail:function() {
        console.log("时间销量接口请求数据失败");
      }
    })
   
 /*   wx.request({
      url: app.globalData.servicePath+'/api/ishop/getDailySalesVolume?mmid=options.id',
        method: 'get', 
        success:function(res){
          console.log(res.data)
          if(res.data.erroce==0)
          {
            console.log(res.data.data)
            var date= Array()
            var sales =Array()
            var length = res.data.data.length
            for(var i=length-1;i>=0;i--){
                date.push(res.data.data[i].mmdidate.year+'-'+res.data.data[i].mmdidate.monthValue+'-'+res.data.data[i].mmdidate.dayOfMonth)
                sales.push(res.data.data[i].mmdisalesvolume)
            }
            console.log(date)
            console.log(sales)
            that.setData({
                date_data:date,
                sales_data:sales
            })
            console.log(that.data.date_data)
            console.log(that.data.sales_data)
          }
          else if(res.data.errcode==20011)
          {
            console.log(res.data.errmsg)
          }
        },
        fail:function() {
          console.log("时间销量接口请求数据失败");
        }
    })*/


    //调用建议销量接口
    var that = this
    wx.request({   //请求地址
    url: app.globalData.servicePath+'/api/ishop/getIntroVolume',
    method: 'get',    
    data:{
    "mmid":options.id,
    },
    header: {  //请求头
    "Content-Type": "application/x-www-form-urlencoded"
    },
    success:function (res) {
    //打印后台返回的数据
    console.log("获取商品每周进货量")
    console.log(res.data)
    //如果返回的是获取成功,导入列表
    if(res.data.erroce==0)
    {
      that.setData({ amount: res.data.data})
    }
    else if(res.data.errcode==20007)
    {
      console.log(res.data.errmsg)
    }
    else if(res.data.errcode==20008)
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
      console.log("商品进货量返回未知errcode")
    }
    },
    fail:function() {
    console.log("商品进货量接口请求数据失败");
    },
    })
     
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
      //图表参数
      lineChart = new wxCharts({
        canvasId: 'lineCanvas',
        type: 'line',
        categories:  this.data.date_data,
        animation: true,
        // background: '#f5f5f5',
        series: [{
            name: '销量',
            data: this.data.sales_data,
        }
        ],
        xAxis: {
            disableGrid: true
        },
        yAxis: {
            title: '销量（件）',
            format: function (val) {
                return val +'件';
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