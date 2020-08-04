//app.js
App({
  onLaunch: function () {
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    var that = this
    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        const code = res.code;
        // 设置appid
        const appId = "wx90b74039162f3f10";
        //设置secret
        const secret = "ad3b783375238677c7455789351bb6e8";
        wx.request({
          url: 'https://api.weixin.qq.com/sns/jscode2session?appid=' + appId 
          + '&secret=' + secret 
          + '&js_code=' + code 
          + '&grant_type=authorization_code',
          data: {},
          header: {
            'content-type': 'json'
          },
          success: function (res) {
            that.globalData.openid = res.data.openid; //返回openid
            console.log( res.data.openid)
            console.log( that.globalData.openid)
          }
      })
    }


          
    })
    
  
    // 获取用户信息
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              // 可以将 res 发送给后台解码出 unionId
              this.globalData.userInfo = res.userInfo
              // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
              // 所以此处加入 callback 以防止这种情况
              if (this.userInfoReadyCallback) {
                this.userInfoReadyCallback(res)
              }
            }
          })
        }
      }
    })

  },
  globalData: {
    userInfo: null,
    identity:0,
    openid:"",
    servicePath:"http://192.168.0.102:8081",//,"http://localhost:8081"
    shopid:0,
  }
})

