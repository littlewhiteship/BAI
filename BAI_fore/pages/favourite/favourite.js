// pages/favourite/favourite.js
const app = getApp()
Page({

 
  data: {
    active:0,//顶部默认先打开的是商品
    McdFavList:[],
    postFavList:[]


  },
  
  //返回按钮
onback:function(){
  wx.redirectTo({
    url: '../my/my',
  })
},


//商品取消收藏
 onChangeFavMcd(event) {
  //数据库调用取消收藏接口，然后列表更新
  var that = this
  wx.request({   //请求地址
  url: app.globalData.servicePath+'/api/my/deletefavm',
  method: 'post',    
  data:{
     "account":app.globalData.openid,
     "FavoriteID":event.currentTarget.id
    },
  header: {  //请求头
   "Content-Type": "application/x-www-form-urlencoded"
  },

  success: function (res) {
    //打印后台返回的数据
    console.log("取消收藏商品信息")
    console.log(res.data)
    //如果返回的不是错误信息，直接把后台返回的数据赋值
    if(res.data.errcode==0)
    {
      console.log(res.data.errmsg)
      that.setData({ McdFavList: res.data.data })
      console.log(res.data.data)
    }
    else if(res.data.errcode==30014)
    {
      console.log(res.data.errmsg)
    }
    else{
      console.log("取消收藏商品接口返回未知errcode")
    }
  
  },
  fail: function() {
    console.log("取消收藏商品接口请求数据失败");
  },

})
},

//帖子取消收藏
onChangeFavPost(event) {
  //数据库调用取消收藏帖子接口，然后列表更新
  var that = this
  wx.request({   //请求地址
  url: app.globalData.servicePath+'/api/my/deletefavpost',
  method: 'post',    
  data:{
     "account":app.globalData.openid,
     "postid":event.currentTarget.id
    },
  header: {  //请求头
   "Content-Type": "application/x-www-form-urlencoded"
  },

  success: function (res) {
    //打印后台返回的数据
    console.log("取消收藏帖子信息")
    console.log(res.data)
    //如果返回的不是错误信息，直接把后台返回的数据赋值
    if(res.data.errcode==0)
    {
      console.log(res.data.errmsg)
      that.setData({ postFavList: res.data.data })
    }
    else if(res.data.errcode==30017)
    {
      console.log(res.data.errmsg)
    }
    else{
      console.log("取消收藏帖子接口返回未知errcode")
    }
  
  },
  fail: function() {
    console.log("取消收藏帖子接口请求数据失败");
  },

})
},



  onLoad: function (options) {

     //获取收藏商品初始列表
     var that = this
     wx.request({   //请求地址
     url: app.globalData.servicePath+'/api/my/checkfavm',
     method: 'post',
     data:{
        "account":getApp().globalData.openid,
       },
     header: {  //请求头
      "Content-Type": "application/x-www-form-urlencoded"
     },
   
     success: function (res) {
       //打印后台返回的数据
       console.log("查看收藏商品列表信息")
       console.log(res.data)
       //如果返回的不是错误信息，直接把后台返回的数据赋值
       if(res.data.errcode==0)
       {
         that.setData({McdFavList: res.data.data })
         console.log(that.data.McdFavList)
         console.log(res.data.data)
       }
       else if(res.data.errcode==30016)
       {
         console.log(res.data.errmsg)
       }
       else{
         console.log("获取收藏商品接口返回未知errcode")
       }
     
     },
     fail: function() {
       console.log("获取收藏商品接口请求数据失败");
     },
    })

    //获取收藏帖子初始列表
    var that = this
     wx.request({   //请求地址
     url: app.globalData.servicePath+'/api/my/checkfavpost',
     method: 'post',    
     data:{
        "account":app.globalData.openid,
       },
     header: {  //请求头
      "Content-Type": "application/x-www-form-urlencoded"
     },
   
     success: function (res) {
       //打印后台返回的数据
       console.log("获取收藏帖子列表接口")
       console.log(res.data)
       //如果返回的不是错误信息，直接把后台返回的数据赋值
       if(res.data.errcode==0)
       {
         that.setData({ postFavList: res.data.data })
         console.log(res.data.data)
       }
       else if(res.data.errcode==30016)
       {
         console.log(res.data.errmsg)
       }
       else{
         console.log("获取收藏帖子接口返回未知errcode")
       }
     
     },
     fail: function() {
       console.log("获取收藏帖子接口请求数据失败");
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