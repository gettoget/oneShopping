/**
 * 图片访问地址前缀:   http://119.23.242.234:9092/

 *所有接口请求地址为 :    http://119.23.242.234:8088

 *附: 后台管理中的系统管理功能的接口基本与 OA 一直 , 接口的返回也是一样的 , 如果文档看不懂 , 可以看以前的

 *所有请求需要在 Header 添加  userid  和  token   否则 会返回 999 授权认证失败  需要重新登录

 *文件上传接口 http://119.23.242.234:8088/upload?targetPath=cover

 *参数:  targetPath (必填,谢谢)  此字段用于设置文件上传保存的路径 , 如果你传的是封面图 请传 targetPath = cover (这只是举个例子, 你随便 取名字)  为了区分文件不一样 , 请上传文件时一定要上传此字段 , 好区分每个文件的不同谢谢

 *例如: 你要将 aa.jpg 存到 D://static//common//cover 下面 请传入 target=cover 谢谢

  返回:
  {
    "code": 200,
    "message": "/cover/aa.jpg",   文件路径
    "success": true
  }
**/


// 1. 后台管理用户登录接口
/**
POST:   /login
参数:    username  用户名     password   密码           ( 超级管理员   admini   123456  )

返回:
{
  "code": 200,
  "message": "操作成功!",
  "result": {
  "userInfo": {                 // 用户信息
    "yhid": "1",
      "xm": "超级管理员",       // 登录用户昵称
      "jgdm": "100",            // 用户机构代码
      "type": "su"              // 登录用户类型  su 为 超级管理员
  },
  "jgmc": "管理平台",           // 用户机构名称
    "accessToken": {
    "userId": "1",            // id
      "username": "admini",     // 用户名
      "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3Y3BtcyIsImF1ZCI6IndjcG1zIiwibG9naW5OYW1lIjoiIiwiaXNzIjoid2NwbXMiLCJ1c2VySWQiOiIxIn0.89Ums9AI10wvcgWkPavQu7Ry2wOAUTUG179bKi_k0KU",    // token
      "expired_time": 0
  },
  "menuTree": [      // 用户权限 , 可查看菜单
    {
      "id": "system",
      "pid": null,
      "icon": "md-menu",
      "name": "system",
      "path": "/system",
      "title": "系统管理",
      "children": [
        {
          "id": "system-user",
          "pid": "system",
          "icon": "md-person",
          "name": "system-user",
          "path": "/system-user",
          "title": "用户管理",
          "children": null
        },
        {
          "id": "system-role",
          "pid": "system",
          "icon": "ios-people",
          "name": "system-role",
          "path": "/system-role",
          "title": "角色管理",
          "children": null
        },
        {
          "id": "system-framework",
          "pid": "system",
          "icon": "md-git-network",
          "name": "system-framework",
          "path": "/system-framework",
          "title": "组织机构",
          "children": null
        },
        {
          "id": "system_Jurisdiction",
          "pid": "system",
          "icon": " iconfont icon-quanxian",
          "name": "system_Jurisdiction",
          "path": "/system_Jurisdiction",
          "title": "功能管理",
          "children": null
        },
        {
          "id": "system-daily",
          "pid": "system",
          "icon": " iconfont icon-rizhi",
          "name": "system-daily",
          "path": "/system-daily",
          "title": "日志管理",
          "children": null
        },
        {
          "id": "dictionaries",
          "pid": "system",
          "icon": " iconfont icon-zidian",
          "name": "dictionaries",
          "path": "/dictionaries",
          "title": "字典管理",
          "children": null
        },
        {
          "id": "system-ShortMessage",
          "pid": "system",
          "icon": " iconfont icon-lilun",
          "name": "system-ShortMessage",
          "path": "/system-ShortMessage",
          "title": "短信管理",
          "children": null
        }
      ]
    }
  ],
    "dictList": []                // 字典内容
},
  "success": true
}
 **/
// 2. 新增用户接口
/**
POST:  /api/yh/save
参数:   zh 账号(必填, 必须是字母和数字 )
xm 姓名(必填)
sjh 手机号(必填)
mm 密码(不填默认为 123456 )
jgdm 机构代码(不填默认为当前登录用户相同机构)

返回:
{
  "code": 200,
  "message": "操作成功!",
  "result": "580404122344226816",   // 用户的userid
  "success": true
}
**/
// 3. 用户查询接口
/**
POST: /api/yh/pager

参数:   无     可以根据返回字段查询数据    例:  模糊查询账号  只需要加入参数  zhLike = test  即可查询所有包含 test的用户  ,  如果需要进去查询 则只需要 传入参数  zh = test  即可 , 下面所有的查询接口都是这个规则 ,
  例:   查询多个状态  可以使用   ztIn = 00,01  每个状态用逗号隔开 即可查询 状态为 00 和 01 的所有用户

返回:

{
  "code": 200,
  "message": "操作成功!",
  "page": {
  "pageNum": 1,
    "pageSize": 8,
    "size": 2,
    "startRow": 1,
    "endRow": 2,
    "total": 2,
    "pages": 1,
    "list": [
    {
      "yhid": "580404122344226816",
      "zh": "test",
      "mm": "4DA3BB20330A34F4",
      "sjh": "0213474",
      "cjr": "1",
      "cjsj": "2019-05-21T06:38:46.156+0000",
      "xgr": null,
      "xgsj": null,
      "zt": "01",
      "jgdm": "100",
      "xm": "测试账户",
      "lx": null,
      "xb": null,
      "zjhm": null,
      "mmyxq": null,
      "zw": null,
      "roleId": "ur580404122344226816",
      "jgdms": null
    },
    {
      "yhid": "1",
      "zh": "admini",
      "mm": "4DA3BB20330A34F4",
      "sjh": "13387599856",
      "cjr": "admini",
      "cjsj": "2019-04-16T08:16:11.000+0000",
      "xgr": null,
      "xgsj": null,
      "zt": "01",
      "jgdm": "100",
      "xm": "超级管理员",
      "lx": "su",
      "xb": "1",
      "zjhm": "465465498",
      "mmyxq": null,
      "zw": null,
      "roleId": "",
      "jgdms": null
    }
  ],
    "prePage": 0,
    "nextPage": 0,
    "isFirstPage": true,
    "isLastPage": true,
    "hasPreviousPage": false,
    "hasNextPage": false,
    "navigatePages": 8,
    "navigatepageNums": [
    1
  ],
    "navigateFirstPage": 1,
    "navigateLastPage": 1,
    "lastPage": 1,
    "firstPage": 1
},
  "success": true
}
**/
// 4.用户信息删除接口
/**POST:  /api/yh/remove/{pkid}

参数:   pkid为 路径参数 , 只需要传入用户的id 即可删除  例 :  /api/yh/remove/1   即可删除用户id 为 1 的用户信息

返回:
{
  "code": 200,
  "message": "操作成功!",
  "success": true
}
**/
// 5.用户信息修改
/**
POST: /app/yh/update

参数:  查询到哪些参数 , 哪些参数就是可以修改的  你懂的  , 但是不要改密码等东西哦

返回:
{
  "code": 200,
  "message": "操作成功!",
  "success": true
}
**/
// 6. 商品基础信息添加
/**
POST:  /api/probaseinfo/save

参数:
proType  商品类目(必填)
proName  商品名称(必填)
proPrice 商品单价(必填)
proStore 商品库存(必填)
proSign  商品标签(选填)  如 你搜索电脑的时候 搜 16G 这样的标签
rType    商品抢购类型(必填 , 后期是可以改的)  1 为人类有可能中奖  2 为机器人必中奖

 urls     图片url , 用逗号隔开
coverUrl  封面url
refUrl    推荐图url

返回:
{
  "code": 200,
  "message": "操作成功!",
  "success": true
}
 **/
// 7.商品基础信息查询  库存
/**
POST: /api/probaseinfo/pager

参数: 我不想多说了 , 如果不懂就问我吧

{
  "code": 200,
  "message": "操作成功!",
  "page": {
  "pageNum": 1,
    "pageSize": 8,
    "size": 8,
    "startRow": 1,
    "endRow": 8,
    "total": 9,
    "pages": 2,
    "list": [
    {
      "id": "576456206038597632",                                             // 商品id
      "proType": "iphone",                                                    // 商品类目
      "proName": "Apple iPhone XS Max (A2104) 512GB 深空灰色 移动联通电信4G手机 双卡双待",      // 商品名称
      "proPrice": "150",                                                      // 商品价格
      "proStore": "98",                                                       // 商品库存
      "proSign": "512G",                                                      //  商品标签
      "rType": "2",                                                           // 商品类型  1 为人类有可能中奖  2 为机器人必中奖
      "cjsj": "2019-05-10 17:11:09.558",                                      // 创建时间
      "urls": "http://img10.360buyimg.com/n1/s450x450_jfs/t1/2733/38/3470/138660/5b997bf2E48637bd7/d52740dde6d04976.jpg,http://img10.360buyimg.com/n1/s450x450_jfs/t1/4718/29/3495/118997/5b997bf2E096d1aa1/ea66eebb78fd89f5.jpg",  // 图片url
      "coverUrl": "http://img10.360buyimg.com/n1/s450x450_jfs/t1/2733/38/3470/138660/5b997bf2E48637bd7/d52740dde6d04976.jpg",   // 推荐图url
      "refUrl": "http://img10.360buyimg.com/n1/s450x450_jfs/t1/2733/38/3470/138660/5b997bf2E48637bd7/d52740dde6d04976.jpg",     // 封面图url
      "bz1": null,
      "bz2": null,
      "bz3": null,
      "imgUrls": [
        "http://img10.360buyimg.com/n1/s450x450_jfs/t1/2733/38/3470/138660/5b997bf2E48637bd7/d52740dde6d04976.jpg",
        "http://img10.360buyimg.com/n1/s450x450_jfs/t1/4718/29/3495/118997/5b997bf2E096d1aa1/ea66eebb78fd89f5.jpg"
      ],
      "coverUrls": [
        "http://img10.360buyimg.com/n1/s450x450_jfs/t1/2733/38/3470/138660/5b997bf2E48637bd7/d52740dde6d04976.jpg"
      ],
      "refUrls": [
        "http://img10.360buyimg.com/n1/s450x450_jfs/t1/2733/38/3470/138660/5b997bf2E48637bd7/d52740dde6d04976.jpg"
      ]
    },
    {
      "id": "576455355190476800",
      "proType": "文学",
      "proName": "鲁迅文学全集（小说、杂文、散文、诗全集 套装全7册）",
      "proPrice": "268",
      "proStore": "98",
      "proSign": "鲁迅",
      "rType": "2",
      "cjsj": "2019-05-10 17:07:46.700",
      "urls": "http://img10.360buyimg.com/n1/jfs/t19849/305/1941635429/994012/25dc9d3f/5b2b3265N87213cbb.jpg,http://img10.360buyimg.com/n1/jfs/t20386/307/1467899416/277634/e4630d07/5b2b3270Ne8858cd9.jpg,http://img10.360buyimg.com/n1/jfs/t22276/232/1483714423/449388/30856d29/5b2b3285N76dffc9f.jpg,http://img10.360buyimg.com/n1/jfs/t23728/210/283770164/644934/4895943f/5b2b328bN3971df21.jpg,http://img10.360buyimg.com/n1/jfs/t22270/242/1456128126/148524/332dae05/5b2b328fN28d53c22.jpg",
      "coverUrl": "http://img10.360buyimg.com/n1/jfs/t19849/305/1941635429/994012/25dc9d3f/5b2b3265N87213cbb.jpg",
      "refUrl": "http://img10.360buyimg.com/n1/jfs/t19849/305/1941635429/994012/25dc9d3f/5b2b3265N87213cbb.jpg",
      "bz1": null,
      "bz2": null,
      "bz3": null,
      "imgUrls": [
        "http://img10.360buyimg.com/n1/jfs/t19849/305/1941635429/994012/25dc9d3f/5b2b3265N87213cbb.jpg",
        "http://img10.360buyimg.com/n1/jfs/t20386/307/1467899416/277634/e4630d07/5b2b3270Ne8858cd9.jpg",
        "http://img10.360buyimg.com/n1/jfs/t22276/232/1483714423/449388/30856d29/5b2b3285N76dffc9f.jpg",
        "http://img10.360buyimg.com/n1/jfs/t23728/210/283770164/644934/4895943f/5b2b328bN3971df21.jpg",
        "http://img10.360buyimg.com/n1/jfs/t22270/242/1456128126/148524/332dae05/5b2b328fN28d53c22.jpg"
      ],
      "coverUrls": [
        "http://img10.360buyimg.com/n1/jfs/t19849/305/1941635429/994012/25dc9d3f/5b2b3265N87213cbb.jpg"
      ],
      "refUrls": [
        "http://img10.360buyimg.com/n1/jfs/t19849/305/1941635429/994012/25dc9d3f/5b2b3265N87213cbb.jpg"
      ]
    },
    {
      "id": "576453964334104576",
      "proType": "休闲男鞋",
      "proName": "跨洋休闲鞋男2019夏季新款透气鞋子男潮流男鞋刀锋战士小白鞋男运动鞋跑步鞋 8001白红 41",
      "proPrice": "226",
      "proStore": "98",
      "proSign": "新品",
      "rType": "2",
      "cjsj": "2019-05-10 17:02:15.094",
      "urls": "http://img11.360buyimg.com/n1/jfs/t1/39550/4/307/149436/5cb931e2E3b50b32c/97714d1967ea7d0d.jpg,http://img11.360buyimg.com/n1/jfs/t1/31998/36/12886/184889/5cb931c5Ecb2aad64/6b31d3ec5d1ac98d.jpg,http://img11.360buyimg.com/n1/jfs/t1/36435/30/3673/144332/5cb931c6Ee55fc294/4a31eb9f93463a6a.jpg,http://img11.360buyimg.com/n1/jfs/t1/31046/18/12857/148839/5cb931c6E92c7ef51/7cc51577d3e16d0b.jpg,http://img11.360buyimg.com/n1/jfs/t1/33457/9/5082/179424/5cb931c6E66896091/50dbe887d8ad1e07.jpg",
      "coverUrl": "http://img11.360buyimg.com/n1/jfs/t1/39550/4/307/149436/5cb931e2E3b50b32c/97714d1967ea7d0d.jpg",
      "refUrl": "http://img11.360buyimg.com/n1/jfs/t1/39550/4/307/149436/5cb931e2E3b50b32c/97714d1967ea7d0d.jpg",
      "bz1": null,
      "bz2": null,
      "bz3": null,
      "imgUrls": [
        "http://img11.360buyimg.com/n1/jfs/t1/39550/4/307/149436/5cb931e2E3b50b32c/97714d1967ea7d0d.jpg",
        "http://img11.360buyimg.com/n1/jfs/t1/31998/36/12886/184889/5cb931c5Ecb2aad64/6b31d3ec5d1ac98d.jpg",
        "http://img11.360buyimg.com/n1/jfs/t1/36435/30/3673/144332/5cb931c6Ee55fc294/4a31eb9f93463a6a.jpg",
        "http://img11.360buyimg.com/n1/jfs/t1/31046/18/12857/148839/5cb931c6E92c7ef51/7cc51577d3e16d0b.jpg",
        "http://img11.360buyimg.com/n1/jfs/t1/33457/9/5082/179424/5cb931c6E66896091/50dbe887d8ad1e07.jpg"
      ],
      "coverUrls": [
        "http://img11.360buyimg.com/n1/jfs/t1/39550/4/307/149436/5cb931e2E3b50b32c/97714d1967ea7d0d.jpg"
      ],
      "refUrls": [
        "http://img11.360buyimg.com/n1/jfs/t1/39550/4/307/149436/5cb931e2E3b50b32c/97714d1967ea7d0d.jpg"
      ]
    },
    {
      "id": "569837309701128192",
      "proType": "家居",
      "proName": "志邦厨柜 整体厨房橱柜定制 美厨UV漆现代简约 石英石台面 全屋家具 多彩阳光 3米地柜+3米台面+1米上柜+铰链踢脚板",
      "proPrice": "7199",
      "proStore": "89",
      "proSign": "",
      "rType": "2",
      "cjsj": "2019-04-22 10:50:01.698",
      "urls": "http://img13.360buyimg.com/n1/jfs/t1/22056/38/15386/192687/5cb001e8E5648da2b/5695f4fe0bf405fb.jpg,http://img13.360buyimg.com/n1/jfs/t2857/244/3723749263/305148/46092485/57995f4cNea257259.jpg,http://img13.360buyimg.com/n1/jfs/t2668/157/3786135137/302553/60bb361e/57995f6eNe43fe161.jpg,http://img13.360buyimg.com/n1/jfs/t2869/238/3771355756/437406/c03a9ee0/57995f4aNd07e7e8b.jpg,http://img13.360buyimg.com/n1/jfs/t2824/94/3757242225/359032/afef57e5/57996541Nfea6888f.jpg",
      "coverUrl": "http://img13.360buyimg.com/n1/jfs/t1/22056/38/15386/192687/5cb001e8E5648da2b/5695f4fe0bf405fb.jpg",
      "refUrl": "http://img13.360buyimg.com/n1/jfs/t1/22056/38/15386/192687/5cb001e8E5648da2b/5695f4fe0bf405fb.jpg",
      "bz1": null,
      "bz2": null,
      "bz3": null,
      "imgUrls": [
        "http://img13.360buyimg.com/n1/jfs/t1/22056/38/15386/192687/5cb001e8E5648da2b/5695f4fe0bf405fb.jpg",
        "http://img13.360buyimg.com/n1/jfs/t2857/244/3723749263/305148/46092485/57995f4cNea257259.jpg",
        "http://img13.360buyimg.com/n1/jfs/t2668/157/3786135137/302553/60bb361e/57995f6eNe43fe161.jpg",
        "http://img13.360buyimg.com/n1/jfs/t2869/238/3771355756/437406/c03a9ee0/57995f4aNd07e7e8b.jpg",
        "http://img13.360buyimg.com/n1/jfs/t2824/94/3757242225/359032/afef57e5/57996541Nfea6888f.jpg"
      ],
      "coverUrls": [
        "http://img13.360buyimg.com/n1/jfs/t1/22056/38/15386/192687/5cb001e8E5648da2b/5695f4fe0bf405fb.jpg"
      ],
      "refUrls": [
        "http://img13.360buyimg.com/n1/jfs/t1/22056/38/15386/192687/5cb001e8E5648da2b/5695f4fe0bf405fb.jpg"
      ]
    },
    {
      "id": "569836091092238336",
      "proType": "家居",
      "proName": "贝克巴斯（BECBAS）垃圾处理器厨房厨余粉碎机 Element40（E40）",
      "proPrice": "1999",
      "proStore": "83",
      "proSign": "",
      "rType": "2",
      "cjsj": "2019-04-22 10:45:11.159",
      "urls": "http://img10.360buyimg.com/n1/jfs/t15859/239/920603345/300113/f80676cc/5a4359d1N5027f4a2.jpg,http://img10.360buyimg.com/n1/jfs/t18484/242/401553816/303898/6b78e6e4/5a72bef3Na4492094.jpg,http://img10.360buyimg.com/n1/jfs/t6298/40/647891003/303898/6b78e6e4/5943429fN8fd9bbc9.jpg,http://img10.360buyimg.com/n1/jfs/t5908/129/2664754984/315070/ad737fd0/5943429dNbbca88bc.jpg,http://img10.360buyimg.com/n1/jfs/t17002/1/398695721/258128/b43513b9/5a72bc92Na613027f.jpg",
      "coverUrl": "http://img10.360buyimg.com/n1/jfs/t15859/239/920603345/300113/f80676cc/5a4359d1N5027f4a2.jpg",
      "refUrl": "http://img10.360buyimg.com/n1/jfs/t15859/239/920603345/300113/f80676cc/5a4359d1N5027f4a2.jpg",
      "bz1": null,
      "bz2": null,
      "bz3": null,
      "imgUrls": [
        "http://img10.360buyimg.com/n1/jfs/t15859/239/920603345/300113/f80676cc/5a4359d1N5027f4a2.jpg",
        "http://img10.360buyimg.com/n1/jfs/t18484/242/401553816/303898/6b78e6e4/5a72bef3Na4492094.jpg",
        "http://img10.360buyimg.com/n1/jfs/t6298/40/647891003/303898/6b78e6e4/5943429fN8fd9bbc9.jpg",
        "http://img10.360buyimg.com/n1/jfs/t5908/129/2664754984/315070/ad737fd0/5943429dNbbca88bc.jpg",
        "http://img10.360buyimg.com/n1/jfs/t17002/1/398695721/258128/b43513b9/5a72bc92Na613027f.jpg"
      ],
      "coverUrls": [
        "http://img10.360buyimg.com/n1/jfs/t15859/239/920603345/300113/f80676cc/5a4359d1N5027f4a2.jpg"
      ],
      "refUrls": [
        "http://img10.360buyimg.com/n1/jfs/t15859/239/920603345/300113/f80676cc/5a4359d1N5027f4a2.jpg"
      ]
    },
    {
      "id": "569833749596864512",
      "proType": "电脑办公",
      "proName": "戴尔DELLXPS13.3英寸超轻薄窄边框笔记本电脑(i7-8550U 16G 512GPCIe IPS 72%高色域 背光)银",
      "proPrice": "9999",
      "proStore": "84",
      "proSign": "i7,16G,13.3",
      "rType": "2",
      "cjsj": "2019-04-22 10:35:52.903",
      "urls": "http://img14.360buyimg.com/n1/s450x450_jfs/t13111/226/725425281/398153/5e8849fc/5a128b86Nfaea0521.jpg,http://img14.360buyimg.com/n1/s450x450_jfs/t8851/317/983075208/125393/c30ac09a/59b26335N09df3b35.jpg,http://img14.360buyimg.com/n1/s450x450_jfs/t12091/194/1246893214/125038/a33c30a3/5a1d1654Neede98dd.jpg,http://img14.360buyimg.com/n1/s450x450_jfs/t9424/39/978899027/45247/7f7da37a/59b26346N56fa534b.jpg,http://img14.360buyimg.com/n1/s450x450_jfs/t7807/209/2672480040/41078/c20f342e/59b2634dN54a6f6be.jpg,http://img14.360buyimg.com/n1/s450x450_jfs/t8434/48/1013872056/67297/e32af6f4/59b26354N2b8c67a0.jpg,http://img14.360buyimg.com/n1/s450x450_jfs/t7891/64/2644641794/80185/922a099c/59b2636cN5764e865.jpg,http://img14.360buyimg.com/n1/s450x450_jfs/t9064/67/984397589/99532/bfa100d7/59b2637bN3403ee98.jpg,http://img14.360buyimg.com/n1/s450x450_jfs/t9184/5/962507922/87172/82b67412/59b26389Nef7a5fa8.jpg",
      "coverUrl": "http://img14.360buyimg.com/n1/s450x450_jfs/t13111/226/725425281/398153/5e8849fc/5a128b86Nfaea0521.jpg",
      "refUrl": "http://img14.360buyimg.com/n1/s450x450_jfs/t13111/226/725425281/398153/5e8849fc/5a128b86Nfaea0521.jpg",
      "bz1": null,
      "bz2": null,
      "bz3": null,
      "imgUrls": [
        "http://img14.360buyimg.com/n1/s450x450_jfs/t13111/226/725425281/398153/5e8849fc/5a128b86Nfaea0521.jpg",
        "http://img14.360buyimg.com/n1/s450x450_jfs/t8851/317/983075208/125393/c30ac09a/59b26335N09df3b35.jpg",
        "http://img14.360buyimg.com/n1/s450x450_jfs/t12091/194/1246893214/125038/a33c30a3/5a1d1654Neede98dd.jpg",
        "http://img14.360buyimg.com/n1/s450x450_jfs/t9424/39/978899027/45247/7f7da37a/59b26346N56fa534b.jpg",
        "http://img14.360buyimg.com/n1/s450x450_jfs/t7807/209/2672480040/41078/c20f342e/59b2634dN54a6f6be.jpg",
        "http://img14.360buyimg.com/n1/s450x450_jfs/t8434/48/1013872056/67297/e32af6f4/59b26354N2b8c67a0.jpg",
        "http://img14.360buyimg.com/n1/s450x450_jfs/t7891/64/2644641794/80185/922a099c/59b2636cN5764e865.jpg",
        "http://img14.360buyimg.com/n1/s450x450_jfs/t9064/67/984397589/99532/bfa100d7/59b2637bN3403ee98.jpg",
        "http://img14.360buyimg.com/n1/s450x450_jfs/t9184/5/962507922/87172/82b67412/59b26389Nef7a5fa8.jpg"
      ],
      "coverUrls": [
        "http://img14.360buyimg.com/n1/s450x450_jfs/t13111/226/725425281/398153/5e8849fc/5a128b86Nfaea0521.jpg"
      ],
      "refUrls": [
        "http://img14.360buyimg.com/n1/s450x450_jfs/t13111/226/725425281/398153/5e8849fc/5a128b86Nfaea0521.jpg"
      ]
    },
    {
      "id": "569831856363536384",
      "proType": "电脑办公",
      "proName": "惠普（HP）战66 二代",
      "proPrice": "5999",
      "proStore": "87",
      "proSign": "2G独显,i5,8G,14",
      "rType": "2",
      "cjsj": "2019-04-22 10:28:21.521",
      "urls": "http://img11.360buyimg.com/n1/s450x450_jfs/t1/39945/27/361/167584/5cb954f5E9fd382fc/d01abcbf20e31e40.jpg,http://img11.360buyimg.com/n1/s450x450_jfs/t1/19820/26/11832/160165/5c94a707E91e2c095/b793d006376ec922.png,http://img11.360buyimg.com/n1/s450x450_jfs/t1/31242/14/7175/208230/5c94a708Ea908d231/c8d12c6c607008d3.png,http://img11.360buyimg.com/n1/s450x450_jfs/t1/12225/17/12182/194715/5c94a709E26746211/2bc749a4d729aeec.png,http://img11.360buyimg.com/n1/s450x450_jfs/t1/22513/13/12102/311199/5c94a70bEb1ce4377/a24da0ad79c8b446.png,http://img11.360buyimg.com/n1/s450x450_jfs/t1/29095/19/11937/303784/5c94a70eEba4bb142/ef92a8cf896158b0.png,http://img11.360buyimg.com/n1/s450x450_jfs/t1/11337/5/12919/101391/5c94a70fE8c94d1a6/6f1489b91ad092d5.jpg,http://img11.360buyimg.com/n1/s450x450_jfs/t1/25101/29/12092/188440/5c94a710Eea7089a6/28ec3ffa42c55b35.jpg",
      "coverUrl": "http://img11.360buyimg.com/n1/s450x450_jfs/t1/39945/27/361/167584/5cb954f5E9fd382fc/d01abcbf20e31e40.jpg",
      "refUrl": "http://img11.360buyimg.com/n1/s450x450_jfs/t1/39945/27/361/167584/5cb954f5E9fd382fc/d01abcbf20e31e40.jpg",
      "bz1": null,
      "bz2": null,
      "bz3": null,
      "imgUrls": [
        "http://img11.360buyimg.com/n1/s450x450_jfs/t1/39945/27/361/167584/5cb954f5E9fd382fc/d01abcbf20e31e40.jpg",
        "http://img11.360buyimg.com/n1/s450x450_jfs/t1/19820/26/11832/160165/5c94a707E91e2c095/b793d006376ec922.png",
        "http://img11.360buyimg.com/n1/s450x450_jfs/t1/31242/14/7175/208230/5c94a708Ea908d231/c8d12c6c607008d3.png",
        "http://img11.360buyimg.com/n1/s450x450_jfs/t1/12225/17/12182/194715/5c94a709E26746211/2bc749a4d729aeec.png",
        "http://img11.360buyimg.com/n1/s450x450_jfs/t1/22513/13/12102/311199/5c94a70bEb1ce4377/a24da0ad79c8b446.png",
        "http://img11.360buyimg.com/n1/s450x450_jfs/t1/29095/19/11937/303784/5c94a70eEba4bb142/ef92a8cf896158b0.png",
        "http://img11.360buyimg.com/n1/s450x450_jfs/t1/11337/5/12919/101391/5c94a70fE8c94d1a6/6f1489b91ad092d5.jpg",
        "http://img11.360buyimg.com/n1/s450x450_jfs/t1/25101/29/12092/188440/5c94a710Eea7089a6/28ec3ffa42c55b35.jpg"
      ],
      "coverUrls": [
        "http://img11.360buyimg.com/n1/s450x450_jfs/t1/39945/27/361/167584/5cb954f5E9fd382fc/d01abcbf20e31e40.jpg"
      ],
      "refUrls": [
        "http://img11.360buyimg.com/n1/s450x450_jfs/t1/39945/27/361/167584/5cb954f5E9fd382fc/d01abcbf20e31e40.jpg"
      ]
    },
    {
      "id": "569830125332332544",
      "proType": "电脑办公",
      "proName": "小米 (MI)Ruby 2019款",
      "proPrice": "4299",
      "proStore": "81",
      "proSign": "2G独显,i5,8G,15.6",
      "rType": "2",
      "cjsj": "2019-04-22 10:21:28.812",
      "urls": "http://img13.360buyimg.com/n1/s450x450_jfs/t1/11353/28/15017/191244/5ca59a7bE2723f02f/ea65b0cd6282fa42.jpg,http://img13.360buyimg.com/n1/s450x450_jfs/t1/11353/28/15017/191244/5ca59a7bE2723f02f/ea65b0cd6282fa42.jpg,http://img13.360buyimg.com/n1/s450x450_jfs/t1/11353/28/15017/191244/5ca59a7bE2723f02f/ea65b0cd6282fa42.jpg,http://img13.360buyimg.com/n1/s450x450_jfs/t1/11353/28/15017/191244/5ca59a7bE2723f02f/ea65b0cd6282fa42.jpg",
      "coverUrl": "http://img14.360buyimg.com/n0/jfs/t1/16326/15/10418/319090/5c9c2fdbE992e95df/de733d18b43b7a2f.jpg",
      "refUrl": "http://img14.360buyimg.com/n0/jfs/t1/16326/15/10418/319090/5c9c2fdbE992e95df/de733d18b43b7a2f.jpg",
      "bz1": null,
      "bz2": null,
      "bz3": null,
      "imgUrls": [
        "http://img13.360buyimg.com/n1/s450x450_jfs/t1/11353/28/15017/191244/5ca59a7bE2723f02f/ea65b0cd6282fa42.jpg",
        "http://img13.360buyimg.com/n1/s450x450_jfs/t1/11353/28/15017/191244/5ca59a7bE2723f02f/ea65b0cd6282fa42.jpg",
        "http://img13.360buyimg.com/n1/s450x450_jfs/t1/11353/28/15017/191244/5ca59a7bE2723f02f/ea65b0cd6282fa42.jpg",
        "http://img13.360buyimg.com/n1/s450x450_jfs/t1/11353/28/15017/191244/5ca59a7bE2723f02f/ea65b0cd6282fa42.jpg"
      ],
      "coverUrls": [
        "http://img14.360buyimg.com/n0/jfs/t1/16326/15/10418/319090/5c9c2fdbE992e95df/de733d18b43b7a2f.jpg"
      ],
      "refUrls": [
        "http://img14.360buyimg.com/n0/jfs/t1/16326/15/10418/319090/5c9c2fdbE992e95df/de733d18b43b7a2f.jpg"
      ]
    }
  ],
    "prePage": 0,
    "nextPage": 2,
    "isFirstPage": true,
    "isLastPage": false,
    "hasPreviousPage": false,
    "hasNextPage": true,
    "navigatePages": 8,
    "navigatepageNums": [
    1,
    2
  ],
    "navigateFirstPage": 1,
    "navigateLastPage": 2,
    "lastPage": 2,
    "firstPage": 1
},
  "success": true
}
**/
// 8.商品基本信息修改
/**
POST:  /api/probaseinfo/update

参数:  参数为你查询出来的那些个参数 , 都可以改 , 但是尽量不要随便都改

返回:
{
  "code": 200,
  "message": "操作成功!",
  "success": true
}
**/
// 9. 上架商品信息查询--end
/**
POST:  /api/proinfo/pager

参数: 所有你查询出来的字段均可用来查询   例:  要查询商品状态为 已中奖的 可以查询 proZt = 4  ,  如果需要查询 待开奖 和 已开奖的商品  可以查询 proZtIn = 3,4   如果有什么需要查询的查询不到可以询问我

特殊字段: proLx  商品类型  这个字段可能存储多个状态   例 proLx: 2,3   表示商品既是 新品 又是 热门   此时 查询 只能用  proLxLike = 3 查询热门商品

返回:
{
  "code": 200,
  "message": "操作成功!",
  "page": {
  "pageNum": 1,
    "pageSize": 8,
    "size": 8,
    "startRow": 1,
    "endRow": 8,
    "total": 36,
    "pages": 5,
    "list": [
    {
      "id": "580396891137638400",                                                             // 上架商品 id
      "proBaseid": "576456206038597632",                                                      // 商品基本信息的id
      "proType": "iphone",                                                                    // 商品类目
      "proName": "Apple iPhone XS Max (A2104) 512GB 深空灰色 移动联通电信4G手机 双卡双待",    // 商品姓名
      "proPrice": "150",                                                                      // 商品单价
      "proStore": "1",                                                                        // 商品库存
      "proZt": "1",                                                                           // 商品状态  1 为上架 2 为下架  3 为 待开奖 4 为已开奖
      "proLx": "2",                                                                           // 商品类型  1 为推荐  2 为上新  3 为热门
      "proSign": "512G",									// 商品标签
      "rePrice": "110",									// 商品剩余名额   原价150 已经卖出去40个号码 剩余 110 个
      "rType": "2",                                                                           // 商品类型  1 为人类有可能中奖  2 为机器人必中奖
      "cjsj": "2019-05-21 14:10:02.100",							// 上架时间
      "kjsj": null,                                                                           // 开奖时间  当号码卖完时 , 开奖时间自动出现
      "gxsj": "2019-05-21 15:10:00.007",						        // 上一次更新时间
      "urls": "http://img10.360buyimg.com/n1/s450x450_jfs/t1/2733/38/3470/138660/5b997bf2E48637bd7/d52740dde6d04976.jpg,http://img10.360buyimg.com/n1/s450x450_jfs/t1/4718/29/3495/118997/5b997bf2E096d1aa1/ea66eebb78fd89f5.jpg",  //
      "coverUrl": "http://img10.360buyimg.com/n1/s450x450_jfs/t1/2733/38/3470/138660/5b997bf2E48637bd7/d52740dde6d04976.jpg",
      "refUrl": "http://img10.360buyimg.com/n1/s450x450_jfs/t1/2733/38/3470/138660/5b997bf2E48637bd7/d52740dde6d04976.jpg",
      "cyyhs": "0",                                                                           // 参与用户人数
      "zjhm": null,                                                                           // 中奖号码  开奖后出现
      "userId": null,                                                                         // 中奖人id
      "bz1": null,
      "bz2": null,
      "bz3": null,
      "userName": null,                                                                       // 中奖人姓名
      "cycs": 0,
      "winRecord": null,
      "nums": null,
      "imgUrls": [
        "http://img10.360buyimg.com/n1/s450x450_jfs/t1/2733/38/3470/138660/5b997bf2E48637bd7/d52740dde6d04976.jpg",
        "http://img10.360buyimg.com/n1/s450x450_jfs/t1/4718/29/3495/118997/5b997bf2E096d1aa1/ea66eebb78fd89f5.jpg"
      ],
      "coverUrls": [
        "http://img10.360buyimg.com/n1/s450x450_jfs/t1/2733/38/3470/138660/5b997bf2E48637bd7/d52740dde6d04976.jpg"
      ],
      "refUrls": [
        "http://img10.360buyimg.com/n1/s450x450_jfs/t1/2733/38/3470/138660/5b997bf2E48637bd7/d52740dde6d04976.jpg"
      ],
      "zjfs": null
    },
    {
      "id": "580396889745129472",
      "proBaseid": "576456206038597632",
      "proType": "iphone",
      "proName": "Apple iPhone XS Max (A2104) 512GB 深空灰色 移动联通电信4G手机 双卡双待",
      "proPrice": "150",
      "proStore": "1",
      "proZt": "1",
      "proLx": "2",
      "proSign": "512G",
      "rePrice": "128",
      "rType": "2",
      "cjsj": "2019-05-21 14:10:01.768",
      "kjsj": null,
      "gxsj": "2019-05-21 15:09:55.004",
      "urls": "http://img10.360buyimg.com/n1/s450x450_jfs/t1/2733/38/3470/138660/5b997bf2E48637bd7/d52740dde6d04976.jpg,http://img10.360buyimg.com/n1/s450x450_jfs/t1/4718/29/3495/118997/5b997bf2E096d1aa1/ea66eebb78fd89f5.jpg",
      "coverUrl": "http://img10.360buyimg.com/n1/s450x450_jfs/t1/2733/38/3470/138660/5b997bf2E48637bd7/d52740dde6d04976.jpg",
      "refUrl": "http://img10.360buyimg.com/n1/s450x450_jfs/t1/2733/38/3470/138660/5b997bf2E48637bd7/d52740dde6d04976.jpg",
      "cyyhs": "0",
      "zjhm": null,
      "userId": null,
      "bz1": null,
      "bz2": null,
      "bz3": null,
      "userName": null,
      "cycs": 0,
      "winRecord": null,
      "nums": null,
      "imgUrls": [
        "http://img10.360buyimg.com/n1/s450x450_jfs/t1/2733/38/3470/138660/5b997bf2E48637bd7/d52740dde6d04976.jpg",
        "http://img10.360buyimg.com/n1/s450x450_jfs/t1/4718/29/3495/118997/5b997bf2E096d1aa1/ea66eebb78fd89f5.jpg"
      ],
      "coverUrls": [
        "http://img10.360buyimg.com/n1/s450x450_jfs/t1/2733/38/3470/138660/5b997bf2E48637bd7/d52740dde6d04976.jpg"
      ],
      "refUrls": [
        "http://img10.360buyimg.com/n1/s450x450_jfs/t1/2733/38/3470/138660/5b997bf2E48637bd7/d52740dde6d04976.jpg"
      ],
      "zjfs": null
    },
    {
      "id": "580396888423923712",
      "proBaseid": "576455355190476800",
      "proType": "文学",
      "proName": "鲁迅文学全集（小说、杂文、散文、诗全集 套装全7册）",
      "proPrice": "268",
      "proStore": "1",
      "proZt": "1",
      "proLx": "2",
      "proSign": "鲁迅",
      "rePrice": "232",
      "rType": "2",
      "cjsj": "2019-05-21 14:10:01.453",
      "kjsj": null,
      "gxsj": "2019-05-21 15:12:10.004",
      "urls": "http://img10.360buyimg.com/n1/jfs/t19849/305/1941635429/994012/25dc9d3f/5b2b3265N87213cbb.jpg,http://img10.360buyimg.com/n1/jfs/t20386/307/1467899416/277634/e4630d07/5b2b3270Ne8858cd9.jpg,http://img10.360buyimg.com/n1/jfs/t22276/232/1483714423/449388/30856d29/5b2b3285N76dffc9f.jpg,http://img10.360buyimg.com/n1/jfs/t23728/210/283770164/644934/4895943f/5b2b328bN3971df21.jpg,http://img10.360buyimg.com/n1/jfs/t22270/242/1456128126/148524/332dae05/5b2b328fN28d53c22.jpg",
      "coverUrl": "http://img10.360buyimg.com/n1/jfs/t19849/305/1941635429/994012/25dc9d3f/5b2b3265N87213cbb.jpg",
      "refUrl": "http://img10.360buyimg.com/n1/jfs/t19849/305/1941635429/994012/25dc9d3f/5b2b3265N87213cbb.jpg",
      "cyyhs": "0",
      "zjhm": null,
      "userId": null,
      "bz1": null,
      "bz2": null,
      "bz3": null,
      "userName": null,
      "cycs": 0,
      "winRecord": null,
      "nums": null,
      "imgUrls": [
        "http://img10.360buyimg.com/n1/jfs/t19849/305/1941635429/994012/25dc9d3f/5b2b3265N87213cbb.jpg",
        "http://img10.360buyimg.com/n1/jfs/t20386/307/1467899416/277634/e4630d07/5b2b3270Ne8858cd9.jpg",
        "http://img10.360buyimg.com/n1/jfs/t22276/232/1483714423/449388/30856d29/5b2b3285N76dffc9f.jpg",
        "http://img10.360buyimg.com/n1/jfs/t23728/210/283770164/644934/4895943f/5b2b328bN3971df21.jpg",
        "http://img10.360buyimg.com/n1/jfs/t22270/242/1456128126/148524/332dae05/5b2b328fN28d53c22.jpg"
      ],
      "coverUrls": [
        "http://img10.360buyimg.com/n1/jfs/t19849/305/1941635429/994012/25dc9d3f/5b2b3265N87213cbb.jpg"
      ],
      "refUrls": [
        "http://img10.360buyimg.com/n1/jfs/t19849/305/1941635429/994012/25dc9d3f/5b2b3265N87213cbb.jpg"
      ],
      "zjfs": null
    },
    {
      "id": "580396887119495168",
      "proBaseid": "576455355190476800",
      "proType": "文学",
      "proName": "鲁迅文学全集（小说、杂文、散文、诗全集 套装全7册）",
      "proPrice": "268",
      "proStore": "1",
      "proZt": "1",
      "proLx": "2",
      "proSign": "鲁迅",
      "rePrice": "237",
      "rType": "2",
      "cjsj": "2019-05-21 14:10:01.142",
      "kjsj": null,
      "gxsj": "2019-05-21 15:09:50.005",
      "urls": "http://img10.360buyimg.com/n1/jfs/t19849/305/1941635429/994012/25dc9d3f/5b2b3265N87213cbb.jpg,http://img10.360buyimg.com/n1/jfs/t20386/307/1467899416/277634/e4630d07/5b2b3270Ne8858cd9.jpg,http://img10.360buyimg.com/n1/jfs/t22276/232/1483714423/449388/30856d29/5b2b3285N76dffc9f.jpg,http://img10.360buyimg.com/n1/jfs/t23728/210/283770164/644934/4895943f/5b2b328bN3971df21.jpg,http://img10.360buyimg.com/n1/jfs/t22270/242/1456128126/148524/332dae05/5b2b328fN28d53c22.jpg",
      "coverUrl": "http://img10.360buyimg.com/n1/jfs/t19849/305/1941635429/994012/25dc9d3f/5b2b3265N87213cbb.jpg",
      "refUrl": "http://img10.360buyimg.com/n1/jfs/t19849/305/1941635429/994012/25dc9d3f/5b2b3265N87213cbb.jpg",
      "cyyhs": "0",
      "zjhm": null,
      "userId": null,
      "bz1": null,
      "bz2": null,
      "bz3": null,
      "userName": null,
      "cycs": 0,
      "winRecord": null,
      "nums": null,
      "imgUrls": [
        "http://img10.360buyimg.com/n1/jfs/t19849/305/1941635429/994012/25dc9d3f/5b2b3265N87213cbb.jpg",
        "http://img10.360buyimg.com/n1/jfs/t20386/307/1467899416/277634/e4630d07/5b2b3270Ne8858cd9.jpg",
        "http://img10.360buyimg.com/n1/jfs/t22276/232/1483714423/449388/30856d29/5b2b3285N76dffc9f.jpg",
        "http://img10.360buyimg.com/n1/jfs/t23728/210/283770164/644934/4895943f/5b2b328bN3971df21.jpg",
        "http://img10.360buyimg.com/n1/jfs/t22270/242/1456128126/148524/332dae05/5b2b328fN28d53c22.jpg"
      ],
      "coverUrls": [
        "http://img10.360buyimg.com/n1/jfs/t19849/305/1941635429/994012/25dc9d3f/5b2b3265N87213cbb.jpg"
      ],
      "refUrls": [
        "http://img10.360buyimg.com/n1/jfs/t19849/305/1941635429/994012/25dc9d3f/5b2b3265N87213cbb.jpg"
      ],
      "zjfs": null
    },
    {
      "id": "580396886721036288",
      "proBaseid": "576453964334104576",
      "proType": "休闲男鞋",
      "proName": "跨洋休闲鞋男2019夏季新款透气鞋子男潮流男鞋刀锋战士小白鞋男运动鞋跑步鞋 8001白红 41",
      "proPrice": "226",
      "proStore": "1",
      "proZt": "1",
      "proLx": "2",
      "proSign": "新品",
      "rePrice": "198",
      "rType": "2",
      "cjsj": "2019-05-21 14:10:01.047",
      "kjsj": null,
      "gxsj": "2019-05-21 15:10:35.004",
      "urls": "http://img11.360buyimg.com/n1/jfs/t1/39550/4/307/149436/5cb931e2E3b50b32c/97714d1967ea7d0d.jpg,http://img11.360buyimg.com/n1/jfs/t1/31998/36/12886/184889/5cb931c5Ecb2aad64/6b31d3ec5d1ac98d.jpg,http://img11.360buyimg.com/n1/jfs/t1/36435/30/3673/144332/5cb931c6Ee55fc294/4a31eb9f93463a6a.jpg,http://img11.360buyimg.com/n1/jfs/t1/31046/18/12857/148839/5cb931c6E92c7ef51/7cc51577d3e16d0b.jpg,http://img11.360buyimg.com/n1/jfs/t1/33457/9/5082/179424/5cb931c6E66896091/50dbe887d8ad1e07.jpg",
      "coverUrl": "http://img11.360buyimg.com/n1/jfs/t1/39550/4/307/149436/5cb931e2E3b50b32c/97714d1967ea7d0d.jpg",
      "refUrl": "http://img11.360buyimg.com/n1/jfs/t1/39550/4/307/149436/5cb931e2E3b50b32c/97714d1967ea7d0d.jpg",
      "cyyhs": "0",
      "zjhm": null,
      "userId": null,
      "bz1": null,
      "bz2": null,
      "bz3": null,
      "userName": null,
      "cycs": 0,
      "winRecord": null,
      "nums": null,
      "imgUrls": [
        "http://img11.360buyimg.com/n1/jfs/t1/39550/4/307/149436/5cb931e2E3b50b32c/97714d1967ea7d0d.jpg",
        "http://img11.360buyimg.com/n1/jfs/t1/31998/36/12886/184889/5cb931c5Ecb2aad64/6b31d3ec5d1ac98d.jpg",
        "http://img11.360buyimg.com/n1/jfs/t1/36435/30/3673/144332/5cb931c6Ee55fc294/4a31eb9f93463a6a.jpg",
        "http://img11.360buyimg.com/n1/jfs/t1/31046/18/12857/148839/5cb931c6E92c7ef51/7cc51577d3e16d0b.jpg",
        "http://img11.360buyimg.com/n1/jfs/t1/33457/9/5082/179424/5cb931c6E66896091/50dbe887d8ad1e07.jpg"
      ],
      "coverUrls": [
        "http://img11.360buyimg.com/n1/jfs/t1/39550/4/307/149436/5cb931e2E3b50b32c/97714d1967ea7d0d.jpg"
      ],
      "refUrls": [
        "http://img11.360buyimg.com/n1/jfs/t1/39550/4/307/149436/5cb931e2E3b50b32c/97714d1967ea7d0d.jpg"
      ],
      "zjfs": null
    },
    {
      "id": "580396886364520448",
      "proBaseid": "576453964334104576",
      "proType": "休闲男鞋",
      "proName": "跨洋休闲鞋男2019夏季新款透气鞋子男潮流男鞋刀锋战士小白鞋男运动鞋跑步鞋 8001白红 41",
      "proPrice": "226",
      "proStore": "1",
      "proZt": "1",
      "proLx": "2",
      "proSign": "新品",
      "rePrice": "184",
      "rType": "2",
      "cjsj": "2019-05-21 14:10:00.962",
      "kjsj": null,
      "gxsj": "2019-05-21 15:12:00.006",
      "urls": "http://img11.360buyimg.com/n1/jfs/t1/39550/4/307/149436/5cb931e2E3b50b32c/97714d1967ea7d0d.jpg,http://img11.360buyimg.com/n1/jfs/t1/31998/36/12886/184889/5cb931c5Ecb2aad64/6b31d3ec5d1ac98d.jpg,http://img11.360buyimg.com/n1/jfs/t1/36435/30/3673/144332/5cb931c6Ee55fc294/4a31eb9f93463a6a.jpg,http://img11.360buyimg.com/n1/jfs/t1/31046/18/12857/148839/5cb931c6E92c7ef51/7cc51577d3e16d0b.jpg,http://img11.360buyimg.com/n1/jfs/t1/33457/9/5082/179424/5cb931c6E66896091/50dbe887d8ad1e07.jpg",
      "coverUrl": "http://img11.360buyimg.com/n1/jfs/t1/39550/4/307/149436/5cb931e2E3b50b32c/97714d1967ea7d0d.jpg",
      "refUrl": "http://img11.360buyimg.com/n1/jfs/t1/39550/4/307/149436/5cb931e2E3b50b32c/97714d1967ea7d0d.jpg",
      "cyyhs": "0",
      "zjhm": null,
      "userId": null,
      "bz1": null,
      "bz2": null,
      "bz3": null,
      "userName": null,
      "cycs": 0,
      "winRecord": null,
      "nums": null,
      "imgUrls": [
        "http://img11.360buyimg.com/n1/jfs/t1/39550/4/307/149436/5cb931e2E3b50b32c/97714d1967ea7d0d.jpg",
        "http://img11.360buyimg.com/n1/jfs/t1/31998/36/12886/184889/5cb931c5Ecb2aad64/6b31d3ec5d1ac98d.jpg",
        "http://img11.360buyimg.com/n1/jfs/t1/36435/30/3673/144332/5cb931c6Ee55fc294/4a31eb9f93463a6a.jpg",
        "http://img11.360buyimg.com/n1/jfs/t1/31046/18/12857/148839/5cb931c6E92c7ef51/7cc51577d3e16d0b.jpg",
        "http://img11.360buyimg.com/n1/jfs/t1/33457/9/5082/179424/5cb931c6E66896091/50dbe887d8ad1e07.jpg"
      ],
      "coverUrls": [
        "http://img11.360buyimg.com/n1/jfs/t1/39550/4/307/149436/5cb931e2E3b50b32c/97714d1967ea7d0d.jpg"
      ],
      "refUrls": [
        "http://img11.360buyimg.com/n1/jfs/t1/39550/4/307/149436/5cb931e2E3b50b32c/97714d1967ea7d0d.jpg"
      ],
      "zjfs": null
    },
    {
      "id": "580396881893392384",
      "proBaseid": "569837309701128192",
      "proType": "家居",
      "proName": "志邦厨柜 整体厨房橱柜定制 美厨UV漆现代简约 石英石台面 全屋家具 多彩阳光 3米地柜+3米台面+1米上柜+铰链踢脚板",
      "proPrice": "7199",
      "proStore": "1",
      "proZt": "1",
      "proLx": "2",
      "proSign": "",
      "rePrice": "6341",
      "rType": "2",
      "cjsj": "2019-05-21 14:09:59.896",
      "kjsj": null,
      "gxsj": "2019-05-21 15:08:55.014",
      "urls": "http://img13.360buyimg.com/n1/jfs/t1/22056/38/15386/192687/5cb001e8E5648da2b/5695f4fe0bf405fb.jpg,http://img13.360buyimg.com/n1/jfs/t2857/244/3723749263/305148/46092485/57995f4cNea257259.jpg,http://img13.360buyimg.com/n1/jfs/t2668/157/3786135137/302553/60bb361e/57995f6eNe43fe161.jpg,http://img13.360buyimg.com/n1/jfs/t2869/238/3771355756/437406/c03a9ee0/57995f4aNd07e7e8b.jpg,http://img13.360buyimg.com/n1/jfs/t2824/94/3757242225/359032/afef57e5/57996541Nfea6888f.jpg",
      "coverUrl": "http://img13.360buyimg.com/n1/jfs/t1/22056/38/15386/192687/5cb001e8E5648da2b/5695f4fe0bf405fb.jpg",
      "refUrl": "http://img13.360buyimg.com/n1/jfs/t1/22056/38/15386/192687/5cb001e8E5648da2b/5695f4fe0bf405fb.jpg",
      "cyyhs": "0",
      "zjhm": null,
      "userId": null,
      "bz1": null,
      "bz2": null,
      "bz3": null,
      "userName": null,
      "cycs": 0,
      "winRecord": null,
      "nums": null,
      "imgUrls": [
        "http://img13.360buyimg.com/n1/jfs/t1/22056/38/15386/192687/5cb001e8E5648da2b/5695f4fe0bf405fb.jpg",
        "http://img13.360buyimg.com/n1/jfs/t2857/244/3723749263/305148/46092485/57995f4cNea257259.jpg",
        "http://img13.360buyimg.com/n1/jfs/t2668/157/3786135137/302553/60bb361e/57995f6eNe43fe161.jpg",
        "http://img13.360buyimg.com/n1/jfs/t2869/238/3771355756/437406/c03a9ee0/57995f4aNd07e7e8b.jpg",
        "http://img13.360buyimg.com/n1/jfs/t2824/94/3757242225/359032/afef57e5/57996541Nfea6888f.jpg"
      ],
      "coverUrls": [
        "http://img13.360buyimg.com/n1/jfs/t1/22056/38/15386/192687/5cb001e8E5648da2b/5695f4fe0bf405fb.jpg"
      ],
      "refUrls": [
        "http://img13.360buyimg.com/n1/jfs/t1/22056/38/15386/192687/5cb001e8E5648da2b/5695f4fe0bf405fb.jpg"
      ],
      "zjfs": null
    },
    {
      "id": "580396877489373184",
      "proBaseid": "569837309701128192",
      "proType": "家居",
      "proName": "志邦厨柜 整体厨房橱柜定制 美厨UV漆现代简约 石英石台面 全屋家具 多彩阳光 3米地柜+3米台面+1米上柜+铰链踢脚板",
      "proPrice": "7199",
      "proStore": "1",
      "proZt": "1",
      "proLx": "2",
      "proSign": "",
      "rePrice": "6225",
      "rType": "2",
      "cjsj": "2019-05-21 14:09:58.846",
      "kjsj": null,
      "gxsj": "2019-05-21 15:11:40.009",
      "urls": "http://img13.360buyimg.com/n1/jfs/t1/22056/38/15386/192687/5cb001e8E5648da2b/5695f4fe0bf405fb.jpg,http://img13.360buyimg.com/n1/jfs/t2857/244/3723749263/305148/46092485/57995f4cNea257259.jpg,http://img13.360buyimg.com/n1/jfs/t2668/157/3786135137/302553/60bb361e/57995f6eNe43fe161.jpg,http://img13.360buyimg.com/n1/jfs/t2869/238/3771355756/437406/c03a9ee0/57995f4aNd07e7e8b.jpg,http://img13.360buyimg.com/n1/jfs/t2824/94/3757242225/359032/afef57e5/57996541Nfea6888f.jpg",
      "coverUrl": "http://img13.360buyimg.com/n1/jfs/t1/22056/38/15386/192687/5cb001e8E5648da2b/5695f4fe0bf405fb.jpg",
      "refUrl": "http://img13.360buyimg.com/n1/jfs/t1/22056/38/15386/192687/5cb001e8E5648da2b/5695f4fe0bf405fb.jpg",
      "cyyhs": "0",
      "zjhm": null,
      "userId": null,
      "bz1": null,
      "bz2": null,
      "bz3": null,
      "userName": null,
      "cycs": 0,
      "winRecord": null,
      "nums": null,
      "imgUrls": [
        "http://img13.360buyimg.com/n1/jfs/t1/22056/38/15386/192687/5cb001e8E5648da2b/5695f4fe0bf405fb.jpg",
        "http://img13.360buyimg.com/n1/jfs/t2857/244/3723749263/305148/46092485/57995f4cNea257259.jpg",
        "http://img13.360buyimg.com/n1/jfs/t2668/157/3786135137/302553/60bb361e/57995f6eNe43fe161.jpg",
        "http://img13.360buyimg.com/n1/jfs/t2869/238/3771355756/437406/c03a9ee0/57995f4aNd07e7e8b.jpg",
        "http://img13.360buyimg.com/n1/jfs/t2824/94/3757242225/359032/afef57e5/57996541Nfea6888f.jpg"
      ],
      "coverUrls": [
        "http://img13.360buyimg.com/n1/jfs/t1/22056/38/15386/192687/5cb001e8E5648da2b/5695f4fe0bf405fb.jpg"
      ],
      "refUrls": [
        "http://img13.360buyimg.com/n1/jfs/t1/22056/38/15386/192687/5cb001e8E5648da2b/5695f4fe0bf405fb.jpg"
      ],
      "zjfs": null
    }
  ],
    "prePage": 0,
    "nextPage": 2,
    "isFirstPage": true,
    "isLastPage": false,
    "hasPreviousPage": false,
    "hasNextPage": true,
    "navigatePages": 8,
    "navigatepageNums": [
    1,
    2,
    3,
    4,
    5
  ],
    "navigateFirstPage": 1,
    "navigateLastPage": 5,
    "lastPage": 5,
    "firstPage": 1
},
  "success": true
}
**/
// 10. 订单查询接口
/**
POST: /api/order/pager

参数: 你懂的

返回:
{
  "code": 200,
  "message": "操作成功!",
  "page": {
  "pageNum": 1,
    "pageSize": 8,
    "size": 8,
    "startRow": 1,
    "endRow": 8,
    "total": 24965,
    "pages": 3121,
    "list": [
    {
      "id": "580420097831600128",                        // 订单id
      "orderType": "2",                                  // 订单类型   1 直接购买  2 参与抽奖
      "proId": "580396834661335040",                     // 如果为 1 则为 商品基本信息id  如果为 2 则为上架商品id
      "proName": "Dell笔记本",                           // 商品名称
      "userId": "567293382938853376",                    // 购买人id
      "receId": null,                                    // 收货地址 id
      "gmfs": "5",                                       // 购买份数
      "zfje": "5",                                       // 支付金额
      "cjsj": "2019-05-21 15:42:15.007",                 // 订单创建时间
      "zfsj": "2019-05-21 15:42:15.007",                 // 订单支付时间
      "imei": null,                                      // imei
      "ddzt": "0",                                       // 订单状态  0 待开奖 1 已中奖 2 未中奖 3 待支付 4 已支付 5 取消支付
      "zjhm": null,                                      // 中奖号码
      "bz1": null,
      "bz2": null,
      "bz3": null,
      "orderLists": [                                    // 购买的中奖号码详情
        {
          "id": "580420097831600129",
          "orderId": "580420097831600128",
          "proId": "580396834661335040",
          "proName": "Dell笔记本",
          "userid": "567293382938853376",
          "userName": "hendra",
          "yhlx": "1",
          "num": "10003611",
          "cjsj": "2019-05-21 15:42:15.104",
          "bz1": null,
          "bz2": null,
          "bz3": null
        },
      ],
      "userName": "hendra",                     // 用户姓名
      "addr": null,                             // 收货地址
      "kjsj": null,                             // 开奖时间
      "payPwd": null,
      "coverUrl": null,
      "rate": 0,                                // 订单当前商品的参与率
      "nums": [                                 // 此订单购买的号码
        "10003611",
        "10000798",
        "10003406",
        "10000276",
        "10000645"
      ]
    }
  ],
    "navigateFirstPage": 1,
    "navigateLastPage": 8,
    "lastPage": 8,
    "firstPage": 1
},
  "success": true
}
 **/

//
// 接上个文档
//
// 1. 查询商品评论接口
//
// POST:  /api/proeval/pager
//
// 参数:  与上个文档中所说规则一致
//
// 返回:
// {
//   "code": 200,
//   "message": "操作成功!",
//   "page": {
//   "pageNum": 1,
//     "pageSize": 8,
//     "size": 1,
//     "startRow": 1,
//     "endRow": 1,
//     "total": 1,
//     "pages": 1,
//     "list": [
//     {
//       "id": "577120541844963328",                      // 评论id
//       "proId": "572821537917239296",                   // 商品id
//       "userId": "567293418435248128",                  // 评论用户id
//       "userName": "Wiwin",                             // 评论用户名
//       "content": "123",                                // 评论内容
//       "cjsj": "2019-05-12 13:10:59.557",               // 评论时间
//       "img": "winnerShowImage/b3de8fab5c884b599b2a2242ca00aa58.png,winnerShowImage/4136c81fc46f4c0a85b45358ea2bddcd.png,winnerShowImage/89e737295e144def9381f0aa4786f2ae.png,winnerShowImage/b369b29b5cf84da291735c641ef5d1ab.png,winnerShowImage/10c7945fce184e51b07cfe8d5003e95a.png,winnerShowImage/504ed3f038314172b1b2ed3c926d9e01.png,winnerShowImage/7202c731239c40949229b2218e5cf2de.png,winnerShowImage/53504e97079943aaadb5535b7cccee16.png,winnerShowImage/72b66e3681de4cf1a954649dc97fefbd.png,",   // 图片地址
//       "bz1": "1",                                      // 当前评论点赞数
//       "bz2": null,
//       "bz3": null,
//       "thumbs": null,                                  // 当前登录用户是否点赞了    0 未点   1 已点
//       "thumbsSum": 0,                                  // 点赞数
//       "himg": null                                     // 评论用户头像
//     }
//   ],
//     "prePage": 0,
//     "nextPage": 0,
//     "isFirstPage": true,
//     "isLastPage": true,
//     "hasPreviousPage": false,
//     "hasNextPage": false,
//     "navigatePages": 8,
//     "navigatepageNums": [
//     1
//   ],
//     "navigateFirstPage": 1,
//     "navigateLastPage": 1,
//     "firstPage": 1,
//     "lastPage": 1
// },
//   "success": true
// }
//
//
//
// 2. 删除评论
//
// POST: /api/proeval/remove/{id}
//
// 参数:   路径参数  传评论id即可
//
// 返回
// {
//   "code": 200,
//   "message": "操作成功!",
//   "success": true
// }
//
//
//
// 3. 平台注册用户信息
//
// POST: /api/user/pager
//
// 参数: 同上
//
// 返回:
//
//
//
// {
//   "code": 200,
//   "message": "操作成功!",
//   "page": {
//   "pageNum": 1,
//     "pageSize": 8,
//     "size": 8,
//     "startRow": 1,
//     "endRow": 8,
//     "total": 50064,
//     "pages": 6258,
//     "list": [
//     {
//       "id": "579987979284512768",                 // userId
//       "phone": "12312",                           // 手机号 , 账号
//       "pwd": "4DA3BB20330A34F4",                  // 密码
//       "payPwd": null,                             // 支付密码
//       "userName": "11",                           // 用户昵称
//       "source": "0",                              // 来源 0 人  1机
//       "lastTime": "2019-05-20 11:05:34.299",      // 最后登录时间
//       "lastImei": "008796749369364",              // 最后登录的imei
//       "zt": "0",                                  // 状态 0 为正常  1 为禁用
//       "regImei": "008796749369364",               // 注册 imei
//       "hImg": null,                               // 头像
//       "balance": "0",                             // 余额
//       "cjsj": "2019-05-20 11:05:09.917",          // 注册时间
//       "refCode": null,                            // 邀请码
//       "score": "0",                               // 积分
//       "zjcs": "0",                                // 中奖次数
//       "bz1": null,
//       "bz2": null,
//       "bz3": null,
//       "record": null
//     },
//     {
//       "id": "579693223912407040",
//       "phone": "12356",
//       "pwd": "8465FC2A0A16701A",
//       "payPwd": null,
//       "userName": "56",
//       "source": "0",
//       "lastTime": "2019-05-19 15:33:54.760",
//       "lastImei": "008796749369364",
//       "zt": "0",
//       "regImei": "008796749369364",
//       "hImg": null,
//       "balance": "0",
//       "cjsj": "2019-05-19 15:33:54.760",
//       "refCode": null,
//       "score": "0",
//       "zjcs": "0",
//       "bz1": null,
//       "bz2": null,
//       "bz3": null,
//       "record": null
//     },
//     {
//       "id": "579693113547685888",
//       "phone": "12355",
//       "pwd": "8465FC2A0A16701A",
//       "payPwd": null,
//       "userName": "5",
//       "source": "0",
//       "lastTime": "2019-05-19 15:33:28.447",
//       "lastImei": "008796749369364",
//       "zt": "0",
//       "regImei": "008796749369364",
//       "hImg": null,
//       "balance": "0",
//       "cjsj": "2019-05-19 15:33:28.447",
//       "refCode": null,
//       "score": "0",
//       "zjcs": "0",
//       "bz1": null,
//       "bz2": null,
//       "bz3": null,
//       "record": null
//     },
//     {
//       "id": "579590080797081600",
//       "phone": "18602714782",
//       "pwd": "4DA3BB20330A34F4",
//       "payPwd": null,
//       "userName": "1860271478218602714782",
//       "source": "0",
//       "lastTime": "2019-05-20 15:59:07.135",
//       "lastImei": "358240051111110",
//       "zt": "0",
//       "regImei": "99001021737592",
//       "hImg": null,
//       "balance": "996",
//       "cjsj": "2019-05-19 08:44:03.525",
//       "refCode": null,
//       "score": "0",
//       "zjcs": "0",
//       "bz1": null,
//       "bz2": null,
//       "bz3": null,
//       "record": null
//     },
//     {
//       "id": "579586057083813888",
//       "phone": "123456",
//       "pwd": "4DA3BB20330A34F4",
//       "payPwd": null,
//       "userName": "18602714782",
//       "source": "0",
//       "lastTime": "2019-05-19 08:28:04.197",
//       "lastImei": "358240051111110",
//       "zt": "0",
//       "regImei": "358240051111110",
//       "hImg": null,
//       "balance": "1998",
//       "cjsj": "2019-05-19 08:28:04.197",
//       "refCode": null,
//       "score": "0",
//       "zjcs": "0",
//       "bz1": null,
//       "bz2": null,
//       "bz3": null,
//       "record": null
//     },
//     {
//       "id": "577608609014218752",
//       "phone": "15827209983",
//       "pwd": "4DA3BB20330A34F4",
//       "payPwd": null,
//       "userName": "1lll",
//       "source": "0",
//       "lastTime": "2019-05-13 21:30:23.838",
//       "lastImei": "358240051111110",
//       "zt": "0",
//       "regImei": "358240051111110",
//       "hImg": null,
//       "balance": "2497",
//       "cjsj": "2019-05-13 21:30:23.838",
//       "refCode": null,
//       "score": "0",
//       "zjcs": "0",
//       "bz1": null,
//       "bz2": null,
//       "bz3": null,
//       "record": null
//     },
//     {
//       "id": "577138526756274176",
//       "phone": "15827209956",
//       "pwd": "4DA3BB20330A34F4",
//       "payPwd": null,
//       "userName": "Lee",
//       "source": "0",
//       "lastTime": "2019-05-12 14:23:56.510",
//       "lastImei": "358240051111110",
//       "zt": "0",
//       "regImei": "358240051111110",
//       "hImg": null,
//       "balance": "1489",
//       "cjsj": "2019-05-12 14:22:27.494",
//       "refCode": null,
//       "score": "0",
//       "zjcs": "1",
//       "bz1": null,
//       "bz2": null,
//       "bz3": null,
//       "record": null
//     },
//     {
//       "id": "577112734735269888",
//       "phone": "51",
//       "pwd": "0BF5EEF14BCDF313",
//       "payPwd": null,
//       "userName": "slu1",
//       "source": "0",
//       "lastTime": "2019-05-12 12:40:03.338",
//       "lastImei": "008796749369364",
//       "zt": "0",
//       "regImei": "008796749369364",
//       "hImg": "userHead/91e3738c79484abf9541b9b3c269888d.JPG",
//       "balance": "1990",
//       "cjsj": "2019-05-12 12:39:58.197",
//       "refCode": null,
//       "score": "0",
//       "zjcs": "0",
//       "bz1": null,
//       "bz2": null,
//       "bz3": null,
//       "record": null
//     }
//   ],
//     "prePage": 0,
//     "nextPage": 2,
//     "isFirstPage": true,
//     "isLastPage": false,
//     "hasPreviousPage": false,
//     "hasNextPage": true,
//     "navigatePages": 8,
//     "navigatepageNums": [
//     1,
//     2,
//     3,
//     4,
//     5,
//     6,
//     7,
//     8
//   ],
//     "navigateFirstPage": 1,
//     "navigateLastPage": 8,
//     "firstPage": 1,
//     "lastPage": 8
// },
//   "success": true
// }
//
// 4. 平台注册用户信息修改  (只能改部分字段)     ps:  用户信息不可删除 , 可修改状态为禁用来禁止用户登录
//
// POST: /api/user/update
//
//
// 参数:  见分页接口字段
//
// 返回
// {
//   "code": 200,
//   "message": "操作成功!",
//   "success": true
// }
//
// 5. 查询轮播图
//
// POST: /api/popularimgs/pager
//
// 参数:  见文档1中的分页查询参数传递方法
//
// 返回:
// {
//   "code": 200,
//   "message": "操作成功!",
//   "page": {
//   "pageNum": 1,
//     "pageSize": 8,
//     "size": 3,
//     "startRow": 1,
//     "endRow": 3,
//     "total": 3,
//     "pages": 1,
//     "list": [
//     {
//       "id": "570191500369985536",                                   // 轮播图id
//       "proId": "577163915075518464",                                // 商品id
//       "imgUrl": "http://m.360buyimg.com/babel/jfs/t1/30283/22/13082/92611/5cb98fb8E603ba7c3/5e2f2490c9bb08c7.jpg",   // 轮播图 url
//       "imgLx": "2",                                                                                                  // 类型  1 商品推荐  2 活动页面
//       "fwurl": "http://m.360buyimg.com/babel/jfs/t1/30283/22/13082/92611/5cb98fb8E603ba7c3/5e2f2490c9bb08c7.jpg",    // 图片访问地址
//       "cjsj": "2019-04-23 10:17:27.334",
//       "zt": "0",													// 状态 0 正常  1 禁用
//       "bz1": null,
//       "bz2": null,
//       "bz3": null
//     },
//     {
//       "id": "570191065584238592",
//       "proId": "569836091092238336",
//       "imgUrl": "http://img1.360buyimg.com/pop/jfs/t1/15734/1/6445/83238/5c530cb4Eb60af663/22c04b9dd609b45b.jpg",
//       "imgLx": "1",
//       "fwurl": "http://img1.360buyimg.com/pop/jfs/t1/15734/1/6445/83238/5c530cb4Eb60af663/22c04b9dd609b45b.jpg",
//       "cjsj": "2019-04-23 10:15:43.674",
//       "zt": "0",
//       "bz1": null,
//       "bz2": null,
//       "bz3": null
//     },
//     {
//       "id": "570190793843671040",
//       "proId": "567752727216521216",
//       "imgUrl": "http://img1.360buyimg.com/pop/jfs/t1/33955/15/3752/101391/5cb5ada0E62bf446d/13c64b400002587c.jpg",
//       "imgLx": "1",
//       "fwurl": "http://img1.360buyimg.com/pop/jfs/t1/33955/15/3752/101391/5cb5ada0E62bf446d/13c64b400002587c.jpg",
//       "cjsj": "2019-04-23 10:14:38.885",
//       "zt": "0",
//       "bz1": null,
//       "bz2": null,
//       "bz3": null
//     }
//   ],
//     "prePage": 0,
//     "nextPage": 0,
//     "isFirstPage": true,
//     "isLastPage": true,
//     "hasPreviousPage": false,
//     "hasNextPage": false,
//     "navigatePages": 8,
//     "navigatepageNums": [
//     1
//   ],
//     "navigateFirstPage": 1,
//     "navigateLastPage": 1,
//     "firstPage": 1,
//     "lastPage": 1
// },
//   "success": true
// }
//
//
//
//
//
// 5. 推荐 轮播图 添加
//
// POST: /api/popularimgs/save
//
//
// 参数：  url 图片路径（必填）
// 	lx  类型（必填）
// 	proId 商品id(必填)
// zt  状态 默认为0 正常
// 返回：
// {
//   "code": 200,
//   "message": "操作成功!",
//   "success": true
// }
//
//
//
// 6. 推荐图 ， 轮播图修改
//
// POST:  /api/popularimgs/update
//
// 参数：查询参数均可修改
//
// 返回：
// {
//   "code": 200,
//   "message": "操作成功!",
//   "success": true
// }
//
// 7. 推荐 轮播图删除
//
// POST:  /api/popularimgs/remove/{pkId}
//
// 参数：  id 路径参数
//
// 返回
// {
//   "code": 200,
//   "message": "操作成功!",
//   "success": true
// }































