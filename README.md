# Q-Log
Android 轻量级简单易用的日志组件

##Features  
•  支持输出基本数据类型、数组、Map、List、Intent、Bundle，支持Json、XML格式化输出  
•  支持设置ANR拦截捕获，自动格式化保存  
•  自定义解析器，当输入类型无法满足需求时或无法解析时，可实现Parser接口重写方法来添加自定义解析器  

##Screenshots
![screenshots1](https://github.com/DesignQu/Tool-Log/blob/master/ImageFolder/screenshots1.png "screenshots1")

##Download
Gradle:
```
compile 'com.tool.common.log:Q-Log:0.2.0'
```
or Maven:
```
<dependency>
  <groupId>com.tool.common.log</groupId>
  <artifactId>Q-Log</artifactId>
  <version>0.2.0</version>
  <type>pom</type>
</dependency>
```

##History
v0.2.0  
•  代码重构  
•  抽离ANR捕获功能，减少耦合  
•  参数配置采用Buidler模式  

v0.1.0  
•  支持输出基本数据类型、数组、Map、List、Intent、Bundle，支持Json、XML格式化输出  
•  支持设置异常捕获，自动格式化保存  
•  自定义解析器，当输入类型无法满足需求时或无法解析时，可实现Parser接口重写方法来添加自定义解析器

##Feedback
•  Project  [Submit Bug or Idea](https://github.com/DesignQu/Tool-Log/issues)   

##Thanks
[LogUtils](https://github.com/pengwei1024/LogUtils)  
[DarksLogs](https://github.com/liulhdarks/darks-logs)

##License
```
Copyright 2016-2017 DesignQu

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
