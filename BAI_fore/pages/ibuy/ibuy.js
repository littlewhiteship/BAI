// pages/ibuy/ibuy.js
const app = getApp()
Page({

data:{

  activePage: '../ibuy/ibuy',
  priceRange:0,//价格区间默认0是全部范围
  searchKeywords:"",
  pricePP: 50,
  salesPP: 50,
  favourableRatePP: 50,
  identity:1,
  lowest:0,
  highest:1000000,
  merchandise:[],

  priceRangeOption: [
    { text: '全部范围', value: 0 },
    { text: '0-100', value: 1 },
    { text: '100-500', value: 2 },
    { text: '500-1000', value: 3 },
    { text: '1000-5000', value: 4 },
    { text: '5000-10000', value: 5 },
    { text: '10000以上', value: 6 },
  ],
},

//切换界面
onChangePage(event) {
  this.setData({ activePage: event.detail });
  console.log(this.data.activePage)
  wx.redirectTo({
    url: this.data.activePage,
  })
},

//价格区间数据传值
  changePriceRange(event) {

    this.setData({
      priceRange:event.detail
    });
    console.log(event.detail)
    if(this.data.priceRange==1){
      this.setData({
        lowest:0,
        highest:100,
      })
    }
    if(this.data.priceRange==2){
      this.setData({
        lowest:100,
        highest:500,
      })
    }
    if(this.data.priceRange==3){
      this.setData({
        lowest:500,
        highest:1000,
      })
    }
    if(this.data.priceRange==4){
      this.setData({
        lowest:1000,
        highest:5000,
      })
    }
    if(this.data.priceRange==5){
      this.setData({
        lowest:5000,
        highest:10000,
      })
    }
    if(this.data.priceRange==6){
      this.setData({
        lowest:10000,
        highest:1000000,
      })
    }
    if(this.data.priceRange==0){
      this.setData({
        lowest:0,
        highest:1000000,
      })
    }

  
  },
  


//获取搜索关键词
  onSearch(event) {
      this.setData({
        searchKeywords:event.detail
      });
      console.log(event.detail)
      console.log(this.data.searchKeywords)

      //调用搜索商品接口
      var that = this
      wx.request({   //请求地址
      url: app.globalData.servicePath+'/api/ibuy/getIntroMerchandise',
      method: 'get',    
      data:{
      "cs":that.data.favourableRatePP,
      "ps":that.data.pricePP,
      "ss":that.data.salesPP,
      "content":that.data.searchKeywords,
      "ispriceStrict":true,
      "lowest":that.data.lowest,
      "highest":that.data.highest,

      },
      header: {  //请求头
      "Content-Type": "application/x-www-form-urlencoded"
      },

   success: function (res) {
     //打印后台返回的数据
     console.log(that.data.favourableRatePP)
      console.log(that.data.pricePP)
      console.log(that.data.salesPP)
      console.log(that.data.lowest)
      console.log(that.data.highest)
     console.log("搜索帖子信息")
     console.log(res.data)
     //如果返回的是获取成功,导入列表
     if(res.data.erroce==0)
     {
      that.setData({ merchandise: res.data.data})
      console.log("打印merchandise")
      console.log(that.data.merchandise)
     }
     else if(res.data.errcode==40001)
     {
       console.log(res.data.errmsg)
     }
     else{
       console.log("搜索帖子接口返回未知errcode")
     }
   },
   fail: function() {
     console.log("搜索帖子接口请求数据失败");
   },

   })
  },

//排序方式传值
//价格
  onDragP(event) {
    this.setData({
      pricePP: event.detail.value,
    });
    console.log(event.detail.value)
    console.log(this.data.pricePP)
  },
  //销量
  onDragS(event) {
    this.setData({
      salesPP: event.detail.value,
    });
  },
  //好评率
  onDragF(event) {
    this.setData({
      favourableRatePP: event.detail.value,
    });
  },


//排序方式确定按钮
onConfirm() {
  this.onSearch()
},

  //收藏
  onChangeFav(event) {
    console.log(event.currentTarget.dataset.value)
    console.log(event.currentTarget.id)
      //调用收藏商品接口
        var that = this
        wx.request({  //请求地址
        url: app.globalData.servicePath+'/api/my/addfavm',
        method: 'post',    
        data:{
          "account":app.globalData.openid,
          "url":event.currentTarget.dataset.value
          },
        header: {  //请求头
        "Content-Type": "application/x-www-form-urlencoded"
        },
  
        success: function (res) {
          //打印后台返回的数据
          console.log("收藏商品信息")
          console.log(res.data)
          //如果返回的不是错误信息，直接把后台返回的数据赋值
          if(res.data.errcode==0)
          {
            console.log(res.data.errmsg)
          }
          else if(res.data.errcode==30015)
          {
            console.log(res.data.errmsg)
          }
          else if(res.data.errcode==30098)
          {
            console.log(res.data.errmsg)
          }
          else{
            console.log("ibuy收藏商品接口返回未知errcode")
          }
        
        },
        fail: function() {
          console.log("ibuy收藏商品接口请求数据失败");
        },
      })
      

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      //获取用户身份
      identity:app.globalData.identity
    })
    //获取商品初始列表
     var that = this
     wx.request({   //请求地址
     url: app.globalData.servicePath+'/api/my/getMList',
     method: 'post',    
     header: {  //请求头
       "Content-Type": "application/x-www-form-urlencoded"
     },
     success: function (res) {
       //打印后台返回的数据
       console.log("所有商品列表信息")
       console.log(res.data)
       //如果返回的不是错误信息，直接把后台返回的数据赋值
       if(res.data.errcode==0)
       {
         that.setData({
            merchandise: res.data.data,
           })
       }
       else if(res.data.errcode==40002)
       {
         console.log(res.data.errmsg)
       }
       else{
         console.log("获取所有商品接口返回未知errcode")
       }
     },
     fail: function() {
       console.log("请求数据失败");
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

