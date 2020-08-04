// pages/ishare_post/ishare_post.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    McdName:'',
    McdUrl:"",
    postContent:"",
   // picNum:0,
    postPicList:[],
    postConfirm:false,
  },


 
 //发帖变量传输
 onChangeUrl(event) {
  this.setData({
   McdUrl:event.detail.value
  })
  console.log(event.detail.value);
  console.log(this.data.McdUrl)
},


onChangeName(event) {
  this.setData({
   McdName:event.detail.value
  })
  console.log(event.detail.value);
  console.log(this.data.McdName)
},

onChangeContent(event) {
  this.setData({
    postContent:event.detail.value
  });
  console.log(event.detail.value)
  console.log(this.data.postContent)
},

//加载图片
afterRead(event) {
//  this.setData({
//    picNum:event.detail.index,
//  });
//  var string="postPicList["+this.data.picNum+"].url"
  this.setData({
    "postPicList[0].url":event.detail.file.path
//    [string]:event.detail.file.path
  });
  console.log(event.detail.file.path)
  console.log(this.data.postPicList[0].url)

},

//确认上传帖子
upLoad(event) {
  
  //验证获取到的值不为空
if(this.data.McdName!=null&&this.data.McdUrl!=null)
{
  //将获取到的数据传到数据库
  //数据库调用发帖接口
  var that = this
  wx.request({   //请求地址
  url: app.globalData.servicePath+'/api/ishare/posting',
  method: 'post',    
  data:{
     "account":app.globalData.openid,
     "nickname":app.globalData.userInfo.nickName,
     "postimage":app.globalData.userInfo.avatarUrl,
     "merchandisename":that.data. McdName,
     "postcontent":that.data.postContent,
     "merchandiselink":that.data.McdUrl,
     "merchandisepicpath":that.data. postPicList[0].url,
    },
  header: {  //请求头
   "Content-Type": "application/x-www-form-urlencoded"
  },

  success: function (res) {
    //打印后台返回的数据
    console.log("发帖信息")
    console.log(res.data)
    console.log(res.data.errcode)
    //如果返回的是发帖成功，弹出toast，跳转到瀑布流页面，在瀑布流页面刷新成新帖子列表
    if(res.data.errcode==0)
    {
      console.log(res.data.errmsg)
      wx.showToast({
        title: '发帖成功',
        icon: 'success',
        duration: 2000
      })
      wx.redirectTo({
        url: '../ishare/ishare',
      })
    }

    else if(res.data.errcode==20000)
    {
      console.log(res.data.errmsg)
      //如果发帖失败
        wx.showModal({
          title: '提示',
          content: '发布失败',
          showCancel:false,
          success (res) {
            if (res.confirm) {
              console.log('用户点击确定')
            }
          }
          })
    }
    else{
      console.log("发帖接口返回未知errcode")
    }
  },
  fail: function() {
    console.log("发帖接口请求数据失败");
  },

  })
}
//如果必填信息有空
else 
{
  //弹出提示框
  if(this.data.McdName==null)
  {
    wx.showModal({
      title: '提示',
      content: '未输入商品名',
      showCancel:false,
      success (res) {
        if (res.confirm) {
          console.log('用户点击确定')
        }
      }
      })
  }
  if(this.data.McdUrl==null)
  {
    wx.showModal({
      title: '提示',
      content: '未输入商品链接',
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


  //返回按钮
 onback:function(){
       wx.navigateBack({
         delta: 1,
       })
},


  onLoad: function (options) {

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