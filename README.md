# Logg
这是一个Android 轻量级简单易用的Log组件

[![Sourcegraph for Repo Reference Count](https://img.shields.io/badge/License-Apache%202.0%20-blue.svg)](https://github.com/DesignQu/Logg/blob/master/LICENSE.md)

## Features  
* 支持输出基本数据类型、数组、Map、List、Intent、Bundle，支持Json、XML格式化输出  
* 不依赖任何第三方框架，纯原生编写  
* 自定义解析器，当输入类型无法满足需求时或无法解析时，可实现Parser接口重写方法来添加自定义解析器  
* 支持添加拦截器，添加拦截器后它会在最终打印输出前进行拦截处理，可以对日志信息进行重构处理  
* 支持添加全局回调，在不影响底层输出的情况下，所有信息会回调至全局接口里，可以在这里保存你需要的信息到文件中

## Screenshots
![screenshots1](https://github.com/DesignQu/Tool-Log/blob/master/ImageFolder/screenshots1.png "screenshots1")

## Download
Gradle:
```
compile 'com.logg:Logg:1.5.0'
```

## History
[UpdateLog](https://github.com/DesignQu/Logg/releases)   

## Feedback
* Project  [Submit Bug or Idea](https://github.com/DesignQu/Logg/issues)   

## Thanks
[LogUtils](https://github.com/pengwei1024/LogUtils)  
[DarksLogs](https://github.com/liulhdarks/darks-logs)

## About Me
* Email [china.rocky.coder@gmail.com](china.rocky.coder@gmail.com)  
* Home [https://designqu.github.io](https://designqu.github.io)  
* GitHub [https://github.com/DesignQu](https://github.com/DesignQu)  

## License
```
Copyright 2016-2018 DesignQu

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
