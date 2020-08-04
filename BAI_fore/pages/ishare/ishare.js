let col1H = 0;
let col2H = 0;
// pages/ishare/ishare.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    activePage:"../ishare/ishare",
    searchValue: '',
    scrollH: 0,
    imgWidth: 0,
    loadingCount: 0,
    images: [],
    col1: [],
    col2: [],
    posts : [
       {key:0, pic:"",height:0,content:""  }
    ],
      allPostLength:0,
  },

//切换界面
onChangePage(event) {
  this.setData({ activePage: event.detail });
  console.log(this.data.activePage)
  wx.redirectTo({
    url: this.data.activePage,
  })
},

//搜索
onSearch(event) {
  this.setData({
    searchValue:event.detail
  });
  
  console.log(this.data.searchValue)

  //调用搜索帖子接口
   var that = this
   wx.request({   //请求地址
   url: app.globalData.servicePath+'/api/ishare/searchpost',
   method: 'post',    
   data:{
   "keyword":that.data.searchValue,
   },
   header: {  //请求头
   "Content-Type": "application/x-www-form-urlencoded"
   },

   success: function (res) {
     //打印后台返回的数据
     console.log("搜索帖子信息")
     console.log(res.data)
     //如果返回的是获取成功,导入列表
     if(res.data.errcode==0)
     {
       console.log(res.data.data.length)
       for(var i=0;i<res.data.data.length;i++)
       {
         var stringPic ="posts["+i+"].pic"
         var stringheight ="posts["+i+"].height"
         var stringContent ="posts["+i+"].content"
         var stringKey="posts["+i+"].key"
         that.setData({
           [stringPic]:res.data.data[i].merchandisepicpath,
           [stringheight]:0,
           [stringContent]:res.data.data[i].postcontent,
           [stringKey]:res.data.data[i].postkey,
         })
        }
        console.log(that.data.allPostLength)
         for( var i=res.data.data.length;i<that.data.allPostLength;i++)
         {
           var stringPic ="posts["+i+"].pic"
           var stringheight ="posts["+i+"].height"
           var stringContent ="posts["+i+"].content"
           var stringKey="posts["+i+"].key"
           that.setData({
             [stringPic]:"",
             [stringheight]:0,
             [stringContent]:"",
             [stringKey]:0,
           })
          }
         console.log(that.data.posts)

         wx.getSystemInfo({
          success: (res) => {
              let ww = res.windowWidth;
              let wh = res.windowHeight;
              let imgWidth = ww * 0.48;
              let scrollH = wh;
              that.setData({
                  scrollH: scrollH,
                  imgWidth: imgWidth
              });
      
              that.loadImages();
          }
        })
       
       }
     else if(res.data.errcode==20002)
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

//发帖按钮
  onClickAdd() {
    wx.navigateTo({
      url: '../ishare_post/ishare_post',
      success:()=>{
        console.log('ok')
      }, 
      fail: () => {
        console.log('err')
      }
    })
  },
  
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

    this.setData({
      //获取身份
      identity:app.globalData.identity
    })

    //获取帖子列表接口
      var that = this
      wx.request({   //请求地址
      url: app.globalData.servicePath+'/api/ishare/getpostlist',
      method: 'post',    
      data:{

      },
      header: {  //请求头
      "Content-Type": "application/x-www-form-urlencoded"
      },

      success: function (res) {
        //打印后台返回的数据
        console.log("获取帖子列表信息")
        console.log(res.data)
        //如果返回的是获取成功,导入列表
        if(res.data.errcode==0)
        {
          console.log(res.data.data.length)
          for(var num=0;num<res.data.data.length;num++)
          {
            var stringPic ="posts["+num+"].pic"
            var stringheight ="posts["+num+"].height"
            var stringContent ="posts["+num+"].content"
            var stringKey="posts["+num+"].key"
            that.setData({
              allPostLength:res.data.data.length,
              [stringPic]:res.data.data[num].merchandisepicpath,
              [stringheight]:0,
              [stringContent]:res.data.data[num].postcontent,
              [stringKey]:res.data.data[num].postkey,
            })
          }
          console.log(that.data.posts)
        }

        else if(res.data.errcode==20001)
        {
          console.log(res.data.errmsg)
        }
        else{
          console.log("获取帖子接口返回未知errcode")
        }
      },
      fail: function() {
        console.log("获取帖子接口请求数据失败");
      },

      })
  },

  

  onImageLoad: function (e) {

    let imageId = e.currentTarget.id;
    let oImgW = e.detail.width;         //图片原始宽度
    let oImgH = e.detail.height;        //图片原始高度
    let imgWidth = this.data.imgWidth;  //图片设置的宽度
    let scale = imgWidth / oImgW;        //比例计算
    let imgHeight = oImgH * scale;      //自适应高度

    let images = this.data.images;
    let imageObj = null;
    let textHeight= 0 ;
    for (let i = 0; i < images.length; i++) {
        let img = images[i];
        if(img.content!="")
        {
           textHeight=5 ;
        }
        if (img.id === imageId) {
            imageObj = img;
            break;
        }
        if(img.pic==""){
          imgHeight= 0;
        }
    }

    imageObj.height = imgHeight;

    let loadingCount = this.data.loadingCount - 1;
    let col1 = this.data.col1;
    let col2 = this.data.col2;

    if (col1H <= col2H) {
        col1H += imgHeight+textHeight;
        col1.push(imageObj);
    } else {
        col2H += imgHeight+textHeight;
        col2.push(imageObj);
    }

    let data = {
        loadingCount: loadingCount,
        col1: col1,
        col2: col2
    };

    if (!loadingCount) {
        data.images = [];
    }

    this.setData(data);
},

loadImages: function () {
    let images = this.data.posts;
    console.log("打印images")
    console.log(images)
    let baseId = "img-" + (+new Date());

    for (let i = 0; i < images.length; i++) {
        images[i].id = baseId + "-" + i;
    }

    this.setData({
        loadingCount: images.length,
        images: images
    });
},



//帖子跳转
tapPost:function(event){
  console.log("打印返回的id")
  console.log(event.currentTarget.id)
  var num=event.currentTarget.id
  wx.redirectTo({
    url: '../detailed_post/detailed_post?id='+num,
  })
},

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    
    wx.getSystemInfo({
      success: (res) => {
          let ww = res.windowWidth;
          let wh = res.windowHeight;
          let imgWidth = ww * 0.48;
          let scrollH = wh;
          this.setData({
              scrollH: scrollH,
              imgWidth: imgWidth
          });

          this.loadImages();
      }
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