# ECode
easy code for Android developer

provide some useful and simple tools



#### 使用方法

###### 在Application中的onCreate方法中调用初始化代码
###### ECode.init(this);
    
    


#### 详细介绍

1. adapter包：recycleView Adapter的二次封装 减少重复代码量

2. core包

    * AppManger: Activity管理器 方便在任何位置获取到activity的引用
    * BaseActivity:基础activity 封装了AppManager的管理和网络状态监听在里边
    
3. mvpbase包：简单的mvp模式的封装

4. network包：网络状态监听管理

5. utils包：
    * AndroidUtil:
    * CalendarFormat: 日期和时间的格式化工具
    * DeviceUtil: Android 设备 基本信息 wifi地址 开机时间
    * DialogUtil: 自定义loading dialog
    * FileUtil: 文件压缩/解压 图片本地缓存的保存与获取
    * IOUtil: IO流的基本操作
    * LogUtil: 日志统一管理工具
    * PathUtil: 各种文件的目录管理
    * PNGUtil: PNG图片的简单操作
    * ReflectObjectUtil: 反射工具类
    * SecurityUtil: 安全工具类
    * SPUtil: 偏好设置工具类
    * StringUtil: 字符串及编码工具
    * ToastUtil: toast统一管理类
    * UIUtil: 一些常用的UI操作
    * UrlString: url工具类
    * ValidatorUtil: 正则验证工具类
    
6.view包