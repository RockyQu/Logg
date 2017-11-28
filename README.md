# Logg
This is a lightweight Android log component. 

[![](https://img.shields.io/badge/GitHub%20Pages-HOME-red.svg)](https://designqu.github.io/)
[![Build Status](https://travis-ci.org/DesignQu/Logg.svg?branch=master)](https://travis-ci.org/DesignQu/Logg)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/11369b4297bc49e18e8f5d30d7ad552c)](https://www.codacy.com/app/DesignQu/Logg?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=DesignQu/Logg&amp;utm_campaign=Badge_Grade)
[![](https://img.shields.io/badge/License-Apache%202.0%20-orange.svg)](https://github.com/DesignQu/Logg/blob/master/LICENSE.md)
[![](https://img.shields.io/badge/API-14%2B-brightgreen.svg)](https://android-arsenal.com/api?level=14)
[![](https://img.shields.io/github/release/DesignQu/Logg.svg)](https://github.com/DesignQu/Logg/releases)

[中文介绍](https://github.com/DesignQu/Logg/wiki)   

## Features  
* Support for all basic data types, arrays、Map、List、Bundle、Intent、Reference、Throwable、Collection  
* Support Json, XML formatting  
* Not dependent on any third-party framework, pure native  
* Custom parsers that implement the Parser interface override method to add custom parsers when the input type does not meet requirements or can not be parsed   
* Support for adding interceptors, add interceptors it will be in the final printout before interception, log information can be reconstructed  
* Support for adding a global callback, without affecting the underlying output, all messages will be callback to the global interface, where you can save the information you need to various levels of type

## Screenshots
![screenshots1](https://github.com/DesignQu/Tool-Log/blob/master/ImageFolder/screenshots1.png "screenshots1")

## Download
Gradle:
```
compile 'com.logg:Logg:1.5.1'
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
