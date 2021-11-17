## 2021.11.17更新v0.41

1.优化listview选择列表事件，可通过上下方向键进行选择，

由于使用方向键可以移动光标位置，所以可以通过监听onKeyReleased事件来实现，具体实现逻辑如下：

添加监听事件：

```java
onKeyReleased="#mListView1Click2"
```

事件定义为：

```java
public void mListView1Click2(KeyEvent event)
```

函数为：

```java
mListView1.getSelectionModel().getSelectedItem()
```

通过读取光标来进行获取listview选中的内容

2.去除了文件中会显示.DS_store文件

3.增加druid1.0.16版本及以后的解密方式

4.增加编码模块(Unicode、URL、Base64、Hex、Html、ascii编码)，由于平时编码工具很少，要么是burp的编码模块，很难用，要么是某些在线网站，在线网站进行编码，需要有互联网，就导致了在一些内网环境中无法进行编码、解码，所以写了进去，简单介绍下：

Unicode模块
![image](https://user-images.githubusercontent.com/48286013/142168767-f1c1d0a0-bd5e-4cf6-ac81-614ad2097729.png)
![image](https://user-images.githubusercontent.com/48286013/142168787-4cecf2f2-a438-441a-9284-1e2023568e0d.png)

URL模块
![image](https://user-images.githubusercontent.com/48286013/142168842-636decd2-8274-40b7-94cc-71cf3310a6a3.png)
![image](https://user-images.githubusercontent.com/48286013/142168853-52ebffb1-db60-4bb0-90a8-6b4df9351cc5.png)

Base64模块
![image](https://user-images.githubusercontent.com/48286013/142168894-7646ae1c-49bd-43c9-a4d3-55d5ea38de32.png)
![image](https://user-images.githubusercontent.com/48286013/142168912-696026a1-062c-435a-87fb-d18adf56696c.png)

Hex模块
![image](https://user-images.githubusercontent.com/48286013/142168964-9fdefedb-2aac-4f82-ae2a-eaee5769ccf7.png)
![image](https://user-images.githubusercontent.com/48286013/142168988-c4a3c648-bfa6-4f80-8650-a004dd31490b.png)

Html模块
![image](https://user-images.githubusercontent.com/48286013/142169035-04363225-dc93-4723-81ba-93df392d441b.png)
![image](https://user-images.githubusercontent.com/48286013/142169056-c8e3587f-044c-4bdb-909f-a246afac9d8d.png)

ascii模块
![image](https://user-images.githubusercontent.com/48286013/142169215-d5441ec3-2c00-4fd4-8ea5-8cc3a800fe35.png)
![image](https://user-images.githubusercontent.com/48286013/142169093-f7fe18f0-8cfe-4002-bd9b-619abecaf93d.png)



## 2021.11.15更新 v0.4
1.完善密码模块对druid密码的解密，在druid 1.0.16版本及之前
![image](https://user-images.githubusercontent.com/48286013/141799695-c135ea10-7a0c-4276-bc9c-4ecbec16e465.png)
发现有些时候poc利用的还不是很好，emmm在思考怎么优化

## 2021.8.14更新 v0.3
1.完善对于单个目标进行全部poc检测
![image](https://user-images.githubusercontent.com/48286013/129446750-8814104e-5916-4efe-b56a-763faa6fc711.png)

2.python运行命令可在调用python脚本手动设置。

3.将python固定至jar包内部，减少config.properties文件；将test.txt固定至jar包内部，减少test.txt文件(对macos来说，windows还未适配)

后续看看windows怎么减少config.properties文件和test.txt文件，文件说明在最下面都有解释

基本已经完成我所需要的功能了，后续看有必要再次更新

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
~~①.对于一些复杂请求的没有做到兼容测试，还是依赖于python模块去执行~~ 暂时没找到好方法替代

~~②.对于多线程没有处理好~~ 用python替代了

~~③.对于单个目标可以把对应模块的poc全部测一遍~~ 

④.所需要的文件有点多，考虑办法不需要文件，直接写进jar包内 2021.8.14(部分完成)

⑤.考虑和goby、xray格式相同(长久来看)

## 对文件的说明
①.poc文件夹存放poc文件，即yml文件

②.property文件夹存放cmdlists.txt，即常用命令模块；config.properties，即python路径，或者环境变量下python的调用命令；exetest.txt，即tasklist /svc查找的敏感进程；test.txt是保存的请求包

③.pythonexp文件夹存放的python脚本，usage放在了文件第一行，可以进行调用

## 安装说明
安装pyyaml库 5.3.1


## 运行

### Mac OS
直接运行jar即可

### Windows
需要在命令行加入参数编码参数，否则会乱码，即

java -jar -Dfile.encoding=utf-8 poc2jar.jar


## 编写过程的难点
其实真正的难点在于发请求，尤其是发POST请求，但是没有Content-type这样的漏洞payload的时候，有些漏洞请求是要POST，但是Content-type是不需要的，这就导致了该项目当时停滞不前，遇到一位大佬帮我解决问题，真的太感谢了，此处艾特董神
主要在于setIfNotSet自动加上了，所以我们重写这个方法
下面给出解决的demo

![image](https://user-images.githubusercontent.com/48286013/129446994-8390f4a3-e8de-4278-9573-82fe20e9974e.png)

test类下定义一个setIfNotSet方法
```
  public synchronized void setIfNotSet(String arg0, String arg1) {
//        System.out.println("hook: " + arg0);
      if ("Content-type".equals(arg0)) {
      return;
  }

      if ("Connection".equals(arg0)) {
      return;
  }

      if ("Accept".equals(arg0)) {
      return;
  }

      super.setIfNotSet(arg0, arg1);
}
```

继承test类
```
HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
// 设置通用的请求属性

Object target = connection;
if (!target.getClass().equals(sun.net.www.protocol.http.HttpURLConnection.class)) {
    //https
    Field field1 = connection.getClass().getDeclaredField("delegate");
    field1.setAccessible(true);
    target = field1.get(connection);
}
Field field2 = sun.net.www.protocol.http.HttpURLConnection.class.getDeclaredField("requests");
field2.setAccessible(true);
test customMessageHeader = new test();
```


# 免责声明
请勿将本项目技术或代码应用在恶意软件制作、软件著作权/知识产权盗取或不当牟利等非法用途中。实施上述行为或利用本项目对非自己著作权所有的程序进行数据嗅探将涉嫌违反《中华人民共和国刑法》第二百一十七条、第二百八十六条，《中华人民共和国网络安全法》《中华人民共和国计算机软件保护条例》等法律规定。本项目提及的技术仅可用于私人学习测试等合法场景中，任何不当利用该技术所造成的刑事、民事责任均与本项目作者无关。
