// pages/my/my.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    activePage:"../my/my",
    nickname:"",
    avatarUrl:"",
    identity:"顾客",
    shopUrl:"",
    shopSourceSite:[
      { text:'淘宝', value:0},
      { text:'天猫', value:1}
    ],
    shopType: [
      { text: '服饰', value: 0 },
      { text: '美妆', value: 1 },
      { text: '生鲜', value: 2 },
      { text: '女装', value: 3 },
      { text: '电子产品电器', value: 4 },
      { text: '医药', value: 5 },
      { text: '家装', value: 6 },
      { text: '日常百货', value: 7 },
    ],
    shopTypeValue:-1,
    shopSSValue:-1,
    showConfirmBox:false,
     
  },
  //底部弹窗弹起和收起
  showPopup() {
    this.setData({ showConfirmBox: true });
    },

  onClose() {
    this.setData({ showConfirmBox: false });
  },

//页面跳转
openMyFav:function(){
    wx.redirectTo({
    url: "../favourite/favourite"
  })
},
openHistory:function(){
  wx.redirectTo({
    url: "../history/history"
  })
},
openNotice:function(){
  wx.redirectTo({
    url: "../notice/notice"
  })
 },

 
 //商家认证信息传输
 onChangeShopUrl(event) {
  this.setData({
   shopUrl:event.detail.value
  })
  console.log(event.detail.value);
  console.log(this.data.shopUrl)
},

changeShopSS(event) {
  this.setData({
   shopSSValue:event.detail
  })
  console.log(event.detail);
  console.log(this.data.shopSSValue)
},

changeShopType(event) {
  this.setData({
    shopTypeValue:event.detail
  });
  console.log(event.detail)

},

//商家身份认证
onShopConfirm(event) {
//验证获取到的值不为空
if(this.data.shopUrl!=null&&this.data.shopSSValue!=-1&&this.data.shopTypeValue!=-1)
{
  //将获取到的数据传到数据库验证
  var that = this
    wx.request({   //请求地址
    url: app.globalData.servicePath+'/api/my/verify',
    method: 'post',
    data:{
       "account":app.globalData.openid,
       "url":that.data.shopUrl,
       "mtype":that.data.shopType[that.data.shopTypeValue].text,
       "platform":that.data.shopSourceSite[that.data.shopSSValue].text,
      },
    header: {  //请求头
     "Content-Type": "application/x-www-form-urlencoded"
    },
    success: function (res) {
      //打印后台返回的数据
      console.log("商家认证信息")
      console.log(res.data)
      //如果返回的不是错误信息，直接把后台返回的数据赋值
      if(res.data.errcode==0)
      {
        console.log(res.data.errmsg)
        app.globalData.identity=2,
        that.setData({identity:2,})
        wx.showToast({
          title: 认证成功,
          icon: 'success',
          duration: 2000
        })
      }
      else if(res.data.errcode==30006)
      {
        console.log(res.data.errmsg)
      }
      else if(res.data.errcode==30007)
      {
        console.log(res.data.errmsg)
      }
      else if(res.data.errcode==30008)
      {
        console.log(res.data.errmsg)
      }
      else if(res.data.errcode==30009)
      {
        console.log(res.data.errmsg)
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
      else if(res.data.errcode==30010)
      {
        console.log(res.data.errmsg)
      }
      else if(res.data.errcode==30099)
      {
        console.log(res.data.errmsg)
        wx.showModal({
          title: '提示',
          content:res.data.errmsg,
          showCancel:false,
          success (res) {
            if (res.confirm) {
              console.log('用户点击确定')
            }
          }
          })
      }
      else{
        console.log("商家认证接口返回未知errcode")
      }
    
    },
    fail: function() {
      console.log("商家认证接口请求数据失败");
    },
   })

}
else 
{
  //弹出提示框
  if(this.data.shopUrl==null)
  {
    wx.showModal({
      title: '提示',
      content: '未输入店铺链接',
      showCancel:false,
      success (res) {
        if (res.confirm) {
          console.log('用户点击确定')
        }
      }
      })
  }
  if(this.data.shopSSValue==-1)
  {
    wx.showModal({
      title: '提示',
      content: '未选择店铺来源平台',
      showCancel:false,
      success (res) {
        if (res.confirm) {
          console.log('用户点击确定')
        }
      }
      })
  }
  if(this.data.shopTypeValue==-1)
  {
    wx.showModal({
      title: '提示',
      content: '未选择店铺类型',
      showCancel:false,
      success (res) {
        if (res.confirm) {
          console.log('用户点击确定')
        }
      }
      })
  }
}
},



//生命周期函数--监听页面加载
onLoad: function (options) {
  
},

//切换界面
onChangePage(event) {
  this.setData({ activePage: event.detail });
  console.log(this.data.activePage)
  wx.redirectTo({
    url: this.data.activePage,
  })
},


  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    this.setData({
      //获取头像昵称身份
      nickname:app.globalData.userInfo.nickName,
      avatarUrl:app.globalData.userInfo.avatarUrl,
      identity:app.globalData.identity
    })
    console.log(this.data.avatarUrl)
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