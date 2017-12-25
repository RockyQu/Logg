# Logg
This is a lightweight Android log component. 

[![](https://img.shields.io/badge/GitHub%20Pages-HOME-red.svg)](https://designqu.github.io/)
[![Build Status](https://travis-ci.org/DesignQu/Logg.svg?branch=master)](https://travis-ci.org/DesignQu/Logg)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/11369b4297bc49e18e8f5d30d7ad552c)](https://www.codacy.com/app/DesignQu/Logg?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=DesignQu/Logg&amp;utm_campaign=Badge_Grade)
[![](https://img.shields.io/badge/License-Apache%202.0%20-orange.svg)](https://github.com/DesignQu/Logg/blob/master/LICENSE.md)
[![](https://img.shields.io/badge/API-14%2B-brightgreen.svg)](https://android-arsenal.com/api?level=14)
[![](https://img.shields.io/github/release/DesignQu/Logg.svg)](https://github.com/DesignQu/Logg/releases)
<a href="http://www.methodscount.com/?lib=com.logg%3ALogg%3A1.5.1"><img src="https://img.shields.io/badge/Methods and size-296 | 29 KB-e91e63.svg"/></a>

[中文介绍](https://github.com/RockyQu/Logg/wiki)   

## Features  
- [x] Support for all basic data types, arrays、Map、List、Bundle、Intent、Reference、Throwable、Collection  
- [x] Support Json, XML formatting  
- [x] Not dependent on any third-party framework, pure native  
- [x] Custom parsers that implement the Parser interface override method to add custom parsers when the input type does not meet requirements or can not be parsed   
- [x] Support for adding interceptors, add interceptors it will be in the final printout before interception, log information can be reconstructed  
- [x] Support for adding a global callback, without affecting the underlying output, all messages will be callback to the global interface, where you can save the information you need to various levels of type

## Screenshots
![screenshots](https://github.com/DesignQu/Logg/blob/master/ImageFolder/screenshots.png "screenshots")

## Download
Gradle:
```
compile 'com.logg:Logg:1.5.1'
```

## History
[UpdateLog](https://github.com/RockyQu/Logg/releases)   

## Feedback
* Project  [Submit Bug or Idea](https://github.com/RockyQu/Logg/issues)   

## Thanks
[LogUtils](https://github.com/pengwei1024/LogUtils)  
[DarksLogs](https://github.com/liulhdarks/darks-logs)

## About Me
* Email [china.rocky.coder@gmail.com](china.rocky.coder@gmail.com)  
* Home [https://designqu.github.io](https://rockyqu.github.io)  
* GitHub [https://github.com/DesignQu](https://github.com/RockyQu)  

## License
```
Copyright 2016-2018 RockyQu

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
