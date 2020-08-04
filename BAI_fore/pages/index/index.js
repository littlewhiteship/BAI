//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    motto: '',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },

  //事件处理函数
  bindViewTap: function() {

    console.log("在index里的获取用户信息中")
    //授权得到用户信息之后，去数据库验证身份
    if(this.data.hasUserInfo==true)
    {
      //注册接口
      wx.request({   //请求地址
      url: app.globalData.servicePath+'/api/my/register',
      method: 'post',    
      data:{
         "account":app.globalData.openid,//向服务器传输openid
         "password":111111,
         "nickname":app.globalData.userInfo.nickName,
         "image":app.globalData.userInfo.avatarUrl,
        },
      header: {  //请求头
        //'content-type': 'application/json'
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success: function (res){
        //打印后台返回的数据
        console.log("注册接口信息")
        console.log(res.data);
        //如果未注册，直接把后台返回的数据 赋值给identity
        if(res.data.errcode==0){
            app.globalData.identity=res.data.data.uiidenfication 
        }
        //用户已经注册，立即登录
        else if(res.data.errcode==30005){
            wx.request({   //请求地址
            url: app.globalData.servicePath+'/api/my/login',
            method: 'post',    
            data:{
                "account":app.globalData.openid,
                "password":111111,
              },
            header: {  //请求头
              //'content-type': 'application/json'
               "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function (res1) {
              //打印后台返回的数据
              console.log("登录接口信息")
              console.log(res1.data)
              //如果返回的不是错误信息，直接把后台返回的数据赋值
              if(res1.data.errcode==0){    

                app.globalData.identity=res1.data.data.uiidenfication 
                if(res1.data.data.uiidenfication==2)
                {
                   app.globalData.shopid=res1.data.shopid

                }
                console.log("身份数据传输")
                console.log(res1.data.data.uiidenfication)
                console.log(app.globalData.identity)
                console.log("shopid")
                console.log(app.globalData.shopid)
              }
              if(res1.data.errcode==30000){
                console.log("登录失败")
              }
            },
            fail: function() {
              console.log("登录请求数据失败");
            },
          })
        }
          
        
      },
      fail: function() {
        console.log("注册请求数据失败");
      },
  
    })
    }
  
    wx.redirectTo({
      url: "../ibuy/ibuy",
    })
  },


  onLoad: function () {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse){
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },



  getUserInfo: function(e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  }

  
})
