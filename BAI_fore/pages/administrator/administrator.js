// pages/administrator/administrator.js\
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    activePage:"../administrator/administrator",
    nickname:"",
    avatarUrl:"",
    identity:0,//管理员

    postList:[
      {
        id:0,
        postOpenid:"发帖人openid",
        postImageUrl:"https://img.yzcdn.cn/vant/cat.jpeg",
        postContent:"违规帖子内容（）……%%￥￥%%&***&…………%￥",
        posttime:"2020/7/25",
        complaintTimes:100,
      },
      {
        id:1,
        postOpenid:"发帖人openid",
        postImageUrl:"https://img.yzcdn.cn/vant/cat.jpeg",
        postContent:"违规帖子内容（）……%%￥￥%%&***&…………%￥",
        posttime:"2020/7/25",
        complaintTimes:95,
      },
      {
        id:2,
        postOpenid:null,
        postImageUrl:"https://img.yzcdn.cn/vant/cat.jpeg",
        postContent:"违规帖子内容（）……%%￥￥%%&***&…………%￥",
        posttime:"2020/7/25",
        complaintTimes:95,
      }
    ]
  },


//切换界面
onChangePage(event) {
  this.setData({ activePage: event.detail });
  console.log(this.data.activePage)
  wx.redirectTo({
    url: this.data.activePage,
  })
},

//删帖
onRemovePost(event) {
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
    console.log("管理员删帖信息")
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
      console.log("管理员删帖接口返回未知errcode")
    }
  
  },
  fail: function() {
    console.log("管理员删帖接口请求数据失败");
  },
 })

 

},



//封号
//onRemoveAccout(event) {
  //数据库里删除账号
//},


  onLoad: function (options) {
    this.setData({
      //获取头像昵称
      identity:app.globalData.identity,
      nickname:app.globalData.userInfo.nickName,
      avatarUrl:app.globalData.userInfo.avatarUrl,
    })
    console.log(this.data.avatarUrl)
    console.log(app.globalData.identity,)

    
  },


 
  onReady: function () {
    //自动删除信誉分不足的商家
    var that = this
    wx.request({   //请求地址
    url: app.globalData.servicePath+'/api/my/deletemerchant',
    method: 'post',
    data:{
      },
    header: {  //请求头
     "Content-Type": "application/x-www-form-urlencoded"
    },
  
    success: function (res) {
      //打印后台返回的数据
      console.log("筛除商家信息")
      console.log(res.data)
      //如果返回的不是错误信息，直接把后台返回的数据赋值
      if(res.data.errcode==0)
      {
        console.log(res.data.errmsg)
      }
      else if(res.data.errcode==30011)
      {
        console.log(res.data.errmsg)
      }
      else{
        console.log("筛除商家接口返回未知errcode")
      }
    
    },
    fail: function() {
      console.log("筛除商家接口请求数据失败");
    },
   })

   //从数据库获取被举报列表
   var that = this
   wx.request({   //请求地址
   url: app.globalData.servicePath+'/api/my/checkreport',
   method: 'post',
   data:{
      "account":app.globalData.openid,
     },
   header: {  //请求头
    "Content-Type": "application/x-www-form-urlencoded"
   },
 
   success: function (res) {
     //打印后台返回的数据
     console.log("管理员查看被举报帖子列表信息")
     console.log(res.data)
     //如果返回的不是错误信息，直接把后台返回的数据赋值
     if(res.data.errcode==0)
     {
       console.log(res.data.errmsg)
       that.setData({postList: res.data.data })
       console.log(res.data.data)
     }
     else if(res.data.errcode==30018)
     {
       console.log(res.data.errmsg)
     }
     else if(res.data.errcode==30019)
     {
       console.log(res.data.errmsg)
     }
     else{
       console.log("获取被举报帖子列表接口返回未知errcode")
     }
   
   },
   fail: function() {
     console.log("获取被举报帖子列表接口请求数据失败");
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