// pages/detailed_post/detailed_post.js
const app = getApp()
var util = require('../../utils/util.js')
Page({

  properties: {
    // 外部传入数据
    
  },

  
  data: {
    id:0,
    like: false,//是否点赞
    collect:false,//是否收藏
    complain:false,
    count: 0,//点赞数
    yesSrc: '../../utils/images/like_red.png', // 点赞时的图片路径
    noSrc: '../../utils/images/like_black.png', // 没有点赞时的图片路径
    yesCollect:'../../utils/images/star_pink.png', //收藏时图片路径
    noCollect:'../../utils/images/star_black.png',//取消收藏时图片路径
    showComment:false,
    showReply:false,
    
      nickname:"",
      time:"",
      avatarUrl:"",
      content:"",
      McdName:"",
      McdUrl:" ",
      McdImages:[
      "",
      ],
      commentDetail:[],
      myCommentContent:"",
      myReplyContent:"",
      replyId:0,
      commentReply:[
        {
          commentkey: 6,
          postkey: 5,
          replieraccount: "oVOSq5cHnDP7WmNMNfMgnR297zWE",
          replierimage: "https://wx.qlogo.cn/mmopen/vi_32/XqnY4N1DCkXTibP2M4ZmNia66kOAyLho4t5FERhyb2d6jKGSXmClvOAict1MegtDanpNr4UkPkzQfhau0kDaxib3wg/132",
          repliernickname: "J",
          replycontent: "yibanban",
          replykey: 3,
          replytime:{
          dayOfMonth: 29,
          dayOfWeek: "WEDNESDAY",
          dayOfYear: 211,
          hour: 10,
          minute: 37,
          month: "JULY",
          monthValue: 7,
          nano: 513000000,
          second: 5,
          year: 2020}
        }
      ]

  },



  //点赞按钮变化
  onLike: function(e) {
    this.setData({ // 更新数据
      like: !this.data.like,
      count:this.data.like ? this.data.count - 1 : this.data.count + 1,
    })
    
    //向数据库传输
    var that = this
    if(this.data.like==true){
      wx.request({  
      url: app.globalData.servicePath+'/api/ishare/like',
      method: 'post',    
      data:{
        "account":app.globalData.openid,
        "postid":that.data.id,
      },
      header: {  
      "Content-Type": "application/x-www-form-urlencoded"
      },
      success: function (res) {
        //打印后台返回的数据
        console.log("点赞详细信息")
        console.log(res.data)
        //如果返回的是获取成功,导入列表
        if(res.data.errcode==0)
        {
          that.setData({
            count:res.data.data.likenum
          })
            
        }

        else if(res.data.errcode==20007)
        {
          console.log(res.data.errmsg)
        }
        else{
          console.log("点赞接口返回未知errcode")
        }
      },
      fail: function() {
        console.log("点赞接口请求数据失败");
      },
      })
    }
    else{//取消点赞
      wx.request({  
        url: app.globalData.servicePath+'/api/ishare/cancellike',
        method: 'post',    
        data:{
          "postid":that.data.id,
        },
        header: {  
        "Content-Type": "application/x-www-form-urlencoded"
        },
        success: function (res) {
          //打印后台返回的数据
          console.log("取消点赞详细信息")
          console.log(res.data)
          //如果返回的是获取成功,导入列表
          if(res.data.errcode==0)
          {
            that.setData({
              count:res.data.data.likenum
            })
              
          }
          else if(res.data.errcode==20008)
          {
            console.log(res.data.errmsg)
          }
          else{
            console.log("取消点赞接口返回未知errcode")
          }
        },
        fail: function() {
          console.log("取消点赞接口请求数据失败");
        },
        })
    }

  
  

    
  },

//收藏按钮变化
  onCollect: function(e) {
    this.setData({ // 更新数据
      collect: !this.data.collect,
    })

    if(this.data.collect==true){//收藏帖子
      //数据库调用添加收藏帖子接口
      var that = this
      wx.request({   //请求地址
      url: app.globalData.servicePath+'/api/my/addfavpost',
      method: 'post',    
      data:{
        "account":app.globalData.openid,
        "postid":that.data.id,
        },
      header: {  //请求头
      "Content-Type": "application/x-www-form-urlencoded"
      },

      success: function (res) {
        //打印后台返回的数据
        console.log("收藏帖子信息")
        console.log(res.data)
        //如果返回的不是错误信息，直接把后台返回的数据赋值
        if(res.data.errcode==0)
        {
          console.log("收藏成功")
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
          console.log("收藏帖子接口返回未知errcode")
        }
      
      },
      fail: function() {
        console.log("收藏帖子接口请求数据失败");
      },

    })
    }
    else{//取消收藏
      //数据库调用取消收藏帖子接口
      var that = this
      wx.request({   //请求地址
      url: app.globalData.servicePath+'/api/my/deletefavpost',
      method: 'post',    
      data:{
        "account  ":app.globalData.openid,
        "postfavid ":that.data.id
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
    }

  
  },

//举报按钮
onComplain: function(e) {
  this.setData({
    complain:true,
  })
  if(this.data.complain==true){
    //数据库调用举报帖子接口
    var that = this
    wx.request({   //请求地址
    url: app.globalData.servicePath+'/api/ishare/reportpost',
    method: 'post',    
    data:{
      "postid":that.data.id
      },
    header: {  //请求头
    "Content-Type": "application/x-www-form-urlencoded"
    },

    success: function (res) {
      //打印后台返回的数据
      console.log("举报帖子信息")
      console.log(res.data)
      //如果返回的不是错误信息，直接把后台返回的数据赋值
      if(res.data.errcode==0)
      {
        console.log(res.data.errmsg)
        
      }
      else if(res.data.errcode==60011)
      {
        console.log(res.data.errmsg)
      }
      else{
        console.log("举报帖子接口返回未知errcode")
      }
    
    },
    fail: function() {
      console.log("举报帖子接口请求数据失败");
    },

  })
}
},

//显示评论框
  showComment() {
    this.setData({ showComment: true });
  },
  closeComment() {
    this.setData({ showComment: false });
  },


  //弹出回复框
  Reply:function(event){
    this.setData({
       showReply: true,
       replyId:event.currentTarget.id,
       });
    console.log("点击回复评论按钮")
    console.log(event)
    console.log(this.data.replyId)
  },

  closeReply() {
    this.setData({ showReply: false });
  },

  //返回按钮
  onback:function(){
    wx.redirectTo({
      url: '../ishare/ishare',
    })
},

//输入评论
changeComment(event) {

  this.setData({
   myCommentContent:event.detail
  })
  console.log(event.detail);
  console.log(this.data.myCommentContent)
},

//确认上传评论
upLoadComment(event) {
  //验证获取到的值不为空
if(this.data.myCommentContent!=null)
{
    //评论帖子接口
  var that = this
  wx.request({  
  url: app.globalData.servicePath+'/api/ishare/comment',
  method: 'post',    
  data:{
    "account":app.globalData.openid,
    "postid":that.data.id,
    "content":that.data.myCommentContent,
  },
  header: {  
  "Content-Type": "application/x-www-form-urlencoded"
  },

  success: function (res) {
    //打印后台返回的数据
    console.log("评论帖子详细信息")
    console.log(res.data)
    //如果返回的是获取成功,刷新评论列表
    if(res.data.errcode==0)
    {
      that.setData({
        commentDetail:res.data.data
      })
          //如果上传成功
      wx.showToast({
        title: '评论成功',
        icon: 'success',
        duration: 2000
      })
      //关闭弹窗
      that.closeComment()
    }
    else if(res.data.errcode==20009)
    {
      console.log(res.data.errmsg)
      //如果上传失败
      wx.showModal({
        title: '提示',
        content: '评论失败',
        showCancel:false,
        success (res) {
          if (res.confirm) {
            console.log('用户点击确定')
          }
        }
      })
    }
    else{
      console.log("评论帖子接口返回未知errcode")
    }
  },
  fail: function() {
    console.log("评论帖子接口请求数据失败");
  },
  })
}
else 
{
  //弹出提示框
  if(this.data.myCommentContent==null)
  {
    wx.showModal({
      title: '提示',
      content: '未输入评论',
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


//输入评论回复
changeReply(event) {

  this.setData({
   myReplyContent:event.detail,
  })
  console.log(event.detail);
  console.log(this.data.myReplyContent)
},


//确认上传评论回复
upLoadCommentReply(event) {

  //验证获取到的值不为空
if(this.data.myReplyContent!=null)
{
  //将获取到的评论回复内容传到数据库
  var that = this
  wx.request({  
  url: app.globalData.servicePath+'/api/ishare/reply',
  method: 'post',    
  data:{
    "account":app.globalData.openid,
    "nickname":app.globalData.userInfo.nickName,
    "image":app.globalData.userInfo.avatarUrl,
    "commentid":that.data.replyId,
    "content":that.data.myReplyContent,
  },
  header: {  
  "Content-Type": "application/x-www-form-urlencoded"
  },

  success: function (res) {
    //打印后台返回的数据
    console.log("回复评论返回信息")
    console.log(res.data)
    //如果返回的是获取成功,导入列表
    if(res.data.errcode==0)
    {
      var reply=that.data.commentReply
      reply.push(res.data.data)
      that.setData({
        commentReply:reply
      })
      console.log(that.data.commentReply)
      //如果上传成功
      wx.showToast({
        title: '回复成功',
        icon: 'success',
        duration: 2000
      })
      that.closeReply()
    }

    else if(res.data.errcode==20003)
    {
      console.log(res.data.errmsg)
      //如果上传失败
      wx.showModal({
        title: '提示',
        content: '回复失败',
        showCancel:false,
        success (res) {
          if (res.confirm) {
            console.log('用户点击确定')
          }
        }
        })
    }
    else{
      console.log("获取帖子详细信息接口返回未知errcode")
    }
  },
  fail: function() {
    console.log("获取帖子详细信息接口请求数据失败");
  },
  })
}
else 
{
  //弹出提示框
  if(this.data.myReplyContent==null)
  {
    wx.showModal({
      title: '提示',
      content: '未输入评论回复',
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



  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //接受上一个界面传输的帖子id
    this.setData({
      id:options.id,
    })

  //调用查看帖子详情页按钮
  var that = this
  wx.request({  
  url: app.globalData.servicePath+'/api/ishare/checkpostdetail',
  method: 'post',    
  data:{
    "account":app.globalData.openid,
    "postid":options.id,
  },
  header: {  
  "Content-Type": "application/x-www-form-urlencoded"
  },

  success: function (res) {
    //打印后台返回的数据
    console.log("获取帖子详细信息")
    console.log(res.data)
    //如果返回的是获取成功,导入列表
    if(res.data.errcode==0)
    {
      that.setData({
        count:res.data.data.likenum,
        nickname:res.data.data.postnickname,
        time:res.data.data.posttime.year+"-"+res.data.data.posttime.monthValue
        +"-"+res.data.data.posttime.dayOfMonth+"  "+res.data.data.posttime.hour+":"
        +res.data.data.posttime.minute,
        avatarUrl:res.data.data.postimage,
        content:res.data.data.postcontent,
        McdName:res.data.data.merchandisename,
        McdUrl:res.data.data.merchandiselink,
       "McdImages[0]":res.data.data.merchandisepicpath,
        commentDetail:res.data.commentlist,
        replyDetail:res.data.replylist,
      })
        
    }

    else if(res.data.errcode==20003)
    {
      console.log(res.data.errmsg)
    }
    else if(res.data.errcode==60011)
    {
      console.log(res.data.errmsg)
    }
    else{
      console.log("获取帖子详细信息接口返回未知errcode")
    }
  },
  fail: function() {
    console.log("获取帖子详细信息接口请求数据失败");
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