# Q-Log
Android Log日志组件

##Features
•  支持打印基本数据类型、数组、Map、List、Intent、Bundle等  
•  支持Json、XML格式化打印  
•  全局异常捕获，自动格式化保存至文件  
•  悬浮窗打印日志，方便施工现场调试分析  

##Screenshots

##Simple
``` python
// 基本数据类型 byte short int long float double char boolean
QLog.v(3.1415926);
// 数组
QLog.d(DataHelper.getArray());
// Map
QLog.i(DataHelper.getMap());
// List
QLog.w(DataHelper.getList());
// Intent
QLog.e(DataHelper.getIntent());

// Json
QLog.json(DataHelper.getJson());

// XML
QLog.xml(DataHelper.getXml());

// 超大文本
QLog.d(DataHelper.getBigString(MainActivity.this));
```

##History
v0.1.0  
•  支持打印基本数据类型、数组、Map、List、Intent、Bundle等  
•  支持Json、XML格式化打印  
•  全局异常捕获，自动格式化保存至文件 

##Feedback
•  Project  [Submit Bug or Idea](https://github.com/DesignQu/Tool-Log/issues)   

##Thanks
[LogUtils](https://github.com/pengwei1024/LogUtils)
[DarksLogs](https://github.com/liulhdarks/darks-logs)

