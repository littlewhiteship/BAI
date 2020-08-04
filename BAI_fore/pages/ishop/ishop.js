// pages/ishop/ishop.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    activePage:"../ishop/ishop",
    merchandiseInShop:[],
    searchValue:"",
  },

//跳转到店铺分析
  clickAnalysis(){
    wx.redirectTo({
      url: '../shopAnalysis/shopAnalysis',
    })
  },

//导航栏切换界面
onChangePage(event) {
  this.setData({ activePage: event.detail });
  console.log(this.data.activePage)
  wx.redirectTo({
    url: this.data.activePage,
  })
},

//搜索商品
  onSearch(event) {

    this.setData({
      searchValue:event.detail
    });
    console.log(event.detail)
    console.log(this.data.searchValue)

    //调用搜索店铺商品接口
    var that = this
    wx.request({   //请求地址
    url: app.globalData.servicePath+'/api/ishop/searchIshopMerchandise',
    method: 'get',    
    data:{

    "content":that.data.searchValue,
    "shopid":app.globalData.shopid,

    },
    header: {  //请求头
    "Content-Type": "application/x-www-form-urlencoded"
    },

 success: function (res) {
   //打印后台返回的数据
   console.log("搜索店内商品信息")
   console.log(res.data)
   //如果返回的是获取成功,导入列表
   if(res.data.erroce==0)
   {
    that.setData({ merchandiseInShop: res.data.data})
    console.log("打印merchandise")
    console.log(that.data.merchandise)
   }
   else if(res.data.errcode==20013)
   {
     console.log(res.data.errmsg)
   }
   else{
     console.log("搜索店内商品接口返回未知errcode")
   }
 },
 fail: function() {
   console.log("搜索店内商品接口请求数据失败");
 },

 })
  },

  //跳转到建议定价
  onPrice(event){
    var num=event.currentTarget.id//商品id
    wx.navigateTo({
      url: '../supposePrice/supposePrice?id='+num,
    })
  },
  
  //跳转到建议进货量
  onAmount(event){
    var num=event.currentTarget.id//商品id
    wx.navigateTo({
      url: '../supposeAmount/supposeAmount?id='+num,
    })
  },



  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      //获取头像昵称
      identity:app.globalData.identity
    })
    
    //获取店内商品列表
    var that = this
    wx.request({   //请求地址
    url: app.globalData.servicePath+'/api/ishop/getIshopMerchandise',
    method: 'get',   
    data:{
      "shopid":app.globalData.shopid
    }, 
    header: {  //请求头
      "Content-Type": "application/x-www-form-urlencoded"
    },
    success: function (res) {
      //打印后台返回的数据
      console.log("所有店铺内商品列表信息")
      console.log(res.data)
      //如果返回的不是错误信息，直接把后台返回的数据赋值
      if(res.data.erroce==0)
      {
        that.setData({
           merchandiseInShop: res.data.data,
          })
      }
      else if(res.data.erroce==20001)
      {
        console.log(res.data.errmsg)
      }
      else{
        console.log("获取店铺内所有商品接口返回未知errcode")
      }
    },
    fail: function() {
      console.log("请求店铺内商品数据失败");
    },
    
  })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

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