// pages/history/history.js
const app = getApp()
Page({

  data: {
    postList:[],
  },

  //返回键
onback:function(){
  wx.navigateBack({
    delta: 1,
  })
},

//删帖
onDeletePost:function(event){

  //数据库里删除帖子
  var that = this
  wx.request({   //请求地址
  url: app.globalData.servicePath+'/api/ishare/deletepost',
  method: 'post',
  data:{
     "account":app.globalData.openid,
     "postid":event.target.id,
    },
  header: {  //请求头
   "Content-Type": "application/x-www-form-urlencoded"
  },

  success: function (res) {
    //打印后台返回的数据
    console.log("自己删帖信息")
    console.log(res.data)
    //如果返回的不是错误信息，直接把后台返回的数据赋值
    if(res.data.errcode==0)
    {
      that.setData({postList: res.data.data })
      console.log(res.data.data)
    }
    else if(res.data.errcode==20004)
    {
      console.log(res.data.errmsg)
    }
    else if(res.data.errcode==20005)
    {
      console.log(res.data.errmsg)
    }
    else{
      console.log("自己删帖接口返回未知errcode")
    }
  
  },
  fail: function() {
    console.log("自己删帖接口请求数据失败");
  },
  })
},


  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

    //获取历史帖子初始列表
    var that = this
     wx.request({   //请求地址
     url: app.globalData.servicePath+'/api/my/checkposthis',
     method: 'post',    
     data:{
        "account":app.globalData.openid,
       },
     header: {  //请求头
      "Content-Type": "application/x-www-form-urlencoded"
     },
   
     success: function (res) {
       //打印后台返回的数据
       console.log("获取历史帖子列表接口")
       console.log(res.data)
       //如果返回的不是错误信息，直接把后台返回的数据赋值
       if(res.data.errcode==0)
       {
         that.setData({ postList: res.data.data })
         console.log(res.data.data)
         console.log(that.data.postList)
       }
       else if(res.data.errcode==60015)
       {
         console.log(res.data.errmsg)
       }
       else if(res.data.errcode==60035)
       {
         console.log(res.data.errmsg)
       }
       else{
         console.log("获取历史帖子接口返回未知errcode")
       }
     
     },
     fail: function() {
       console.log("获取历史帖子接口请求数据失败");
     },
    })

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