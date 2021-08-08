## 2021.8.8更新 v0.2
1.批量模块更改为python批量，默认用的python3，所以需要配置一下python3命令。(emmm后续再看看改成go试试)

2.添加finalshell密码解密

3.添加seeyon数据库密码解密


注：

1.增加了pythonexp/poc2jarpiliang.py、url.txt文件，最好重新下载一遍项目里的txt

2.property/config.properties增加python2、python3，python脚本默认python2执行
### python批量
![image](https://user-images.githubusercontent.com/48286013/128634771-d1ddc92d-16f3-46da-ba62-3976baf4d6cc.png)

### finalshell密码解密(支持批量)
![image](https://user-images.githubusercontent.com/48286013/128634782-af2dd0a8-5673-4614-9f9f-cd9e717b3de0.png)

### seeyon数据库密码解密(支持批量)
![image](https://user-images.githubusercontent.com/48286013/128634792-cc3121e2-941f-4b6c-9aff-35e0f02734cf.png)


# 源介绍
# poc2jar
个人写的很简陋的poc工具，每次遇到新的漏洞爆发出来，都要去复现，复现好以后保存到哪里就成了一个问题了，写这个工具，就是为了解决这个问题，希望能集合到个人主机内，然后可以根据不同的poc来使用

工具介绍


# poc保存模块
举一个很简单的例子，hikvision的漏洞
![image](https://user-images.githubusercontent.com/48286013/124343105-9baccf00-dbfb-11eb-9032-75a4f538ac9d.png)
截取到数据包如下：
```
GET /onvif-http/snapshot?auth=YWRtaW46MTEK HTTP/1.1
Host:x.x.x.x
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:89.0) Gecko/20100101 Firefox/89.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
Accept-Language: zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2
Accept-Encoding: gzip, deflate
Connection: keep-alive
Upgrade-Insecure-Requests: 1
```


直接放入到poc保存模块里，进行发包
![image](https://user-images.githubusercontent.com/48286013/124343125-d57dd580-dbfb-11eb-8359-6eaa389a9b83.png)
可以看到返回的为二进制的图片，那我们可以把漏洞关键字设置为Content-Type:image/jpeg，只要请求这个路径返回这个，就判断为存在漏洞

![image](https://user-images.githubusercontent.com/48286013/124343262-c3506700-dbfc-11eb-8701-ea5afeb3379c.png)

满足条件，可以保存为yml文件

填入exp名称、yml文件的名称(这里名称需要带入组件，如这里应该为hikvision/test.yml)
![image](https://user-images.githubusercontent.com/48286013/124343275-d6633700-dbfc-11eb-8ccc-c3f74bbf54fc.png)
至此，保存yml文件成功


# exp利用模块
直接选择刚刚保 存的yml文件
![image](https://user-images.githubusercontent.com/48286013/124343279-ebd86100-dbfc-11eb-8d91-256e228f0fc3.png)

输入刚刚的url，进行测试
![image](https://user-images.githubusercontent.com/48286013/124343292-fdba0400-dbfc-11eb-8c61-851a8f73860f.png)
存在相应回显即为存在漏洞

## 批量利用（这里多线程没有处理好，暂时不能太多目标，后期想更换为python多线程）
![image](https://user-images.githubusercontent.com/48286013/124343398-b8e29d00-dbfd-11eb-97de-4f2a9448ef0b.png)



# tasklist进程搜索模块
这里需要tasklist /svc格式的输入
![image](https://user-images.githubusercontent.com/48286013/124343033-14f7f200-dbfb-11eb-8e23-221be6cb7b52.png)


# 常用命令模块
![image](https://user-images.githubusercontent.com/48286013/124342992-d7936480-dbfa-11eb-8a42-06d04e410e95.png)


# python利用模块
![image](https://user-images.githubusercontent.com/48286013/124342827-7c14a700-dbf9-11eb-8750-07a2f2886ef7.png)


## 不足 todo
①.对于一些复杂请求的没有做到兼容测试，还是依赖于python模块去执行

②.对于多线程没有处理好

③.对于单个目标可以把对应模块的poc全部测一遍

## 对文件的说明
①.poc文件夹存放poc文件，即yml文件

②.property文件夹存放cmdlists.txt，即常用命令模块；config.properties，即python路径，或者环境变量下python的调用命令；exetest.txt，即tasklist /svc查找的敏感进程；test.txt是保存的请求包

③.pythonexp文件夹存放的python脚本，usage放在了文件第一行，可以进行调用


## 运行

### Mac OS
直接运行jar即可

### Windows
需要在命令行加入参数编码参数，否则会乱码，即

java -jar -Dfile.encoding=utf-8 poc2jar.jar


# 免责声明
请勿将本项目技术或代码应用在恶意软件制作、软件著作权/知识产权盗取或不当牟利等非法用途中。实施上述行为或利用本项目对非自己著作权所有的程序进行数据嗅探将涉嫌违反《中华人民共和国刑法》第二百一十七条、第二百八十六条，《中华人民共和国网络安全法》《中华人民共和国计算机软件保护条例》等法律规定。本项目提及的技术仅可用于私人学习测试等合法场景中，任何不当利用该技术所造成的刑事、民事责任均与本项目作者无关。
