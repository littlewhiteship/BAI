// pages/notice/notice.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    active: 0,
    live:[
      {
      id:0,
      anchor:"薇娅"+"带着",
      liveTime:"晚上七点"+"! ",
      McdType:"服饰类"+"好物在直播间等你，快去围观吧！直播链接：",
      liveLink:'http://39.98.59.185:3000/group21/BAI',
      },
      {
        "id":1,
        "anchor":"李佳琦"+"带着",
        "liveTime":"晚上七点"+"! ",
        "McdType":"服饰类"+"好物在直播间等你，快去围观吧！直播链接：",
        "liveLink":"http://39.98.59.185:3000/group21/BAI",
        }
    ],

    reviewList:[]
},

onback:function(){
  wx.redirectTo({
    url: '../my/my',
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
    //获取评论点赞列表
    var that = this
     wx.request({   //请求地址
     url: app.globalData.servicePath+'/api/my/checkpostnotice',
     method: 'post',    
     data:{
        "account":app.globalData.openid,
       },
     header: {  //请求头
      "Content-Type": "application/x-www-form-urlencoded"
     },
   
     success: function (res) {
       //打印后台返回的数据
       console.log("获取通知列表接口")
       console.log(res.data)
       //如果返回的不是错误信息，直接把后台返回的数据赋值
       if(res.data.errcode==0)
       {
         that.setData({ reviewList: res.data.data })
         console.log(res.data.data)
       }
       else if(res.data.errcode==30020)
       {
         console.log(res.data.errmsg)
       }
       else{
         console.log("获取通知接口返回未知errcode")
       }
     },
     fail: function() {
       console.log("获取通知接口请求数据失败");
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