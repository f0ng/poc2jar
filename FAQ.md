# 1.如何使用？
### 使用`jdk8u231`及以下jdk版本启动`java -jar poc2jar.jar`，或者当默认jdk为8的时候双击jar即可进行使用

# 2.有哪些功能？
- 0x01.保存poc、exp利用（批量）
- 0x02.tasklist进程搜索
- 0x03.常用命令备忘
- 0x04.python脚本利用 *`pocsuite调用`*
- 0x05.Finalshell密码解密、seeyon（致远OA）数据库密码解密、druid密码解密
- 0x06.编码（支持Unicode、URL、base64、Hex（十六进制）、Html、ascii）
- 0x07.Bash、Powershell、Python、Perl命令编码
- 0x08.CS上线命令生成
- 0x09.Druid未授权漏洞利用、跨域漏洞利用（生成POC）
- 0x10.Shiro rememberMe参数解密
- 0x11 加解密模块，支持`AES/DES/DESede`模块
- 0x12 提取路径模块（常用于web.xml的提取、Java Spring未授权的提取）
- 0x13 文件转码
- 0x14 文件写入命令


# 3.python模块使用无动静？
### 1)可能python地址没有填对，这里可以python脚本模块进行查看，异常情况如下：

<img src="https://user-images.githubusercontent.com/48286013/143996179-71ed0999-ea92-4ab2-a251-c061fdb5a584.png" width="700" height="600" />

### 这种情况是由于左下角的python地址没有填对，应该为python2，修改完后记得点击修改按钮，正常情况如下：

<img src="https://user-images.githubusercontent.com/48286013/144005135-a0d6a9d7-379b-4c73-9905-87b1ab269ec9.png" width="700" height="600" />

### 2)可能由于一些依赖没装好，可以参考报错信息进行安装
