# -*- coding:utf-8 -*-
# author:f0ngf0ng

# 入参为：yml文件 url.txt  yml文件里有时间条件、关键词条件
# command写死在java里，直接python3 xxxx.py xx.yml即可
# java输出为文件，
# 如http://x.x.x.x ————————vul
#   http://x.x.x.x ———————

import requests
import csv,yaml,os
from concurrent.futures import ThreadPoolExecutor
import sys

proxies = {
    'http':'127.0.0.1:8080',
    'https':'127.0.0.1:8080'
}

def exp(url,ymlfile):
    global words
    # 获取当前脚本所在文件夹路径
    curPath = os.path.dirname(os.path.realpath(__file__))
    # 获取yaml文件路径
    yamlPath = os.path.join(curPath + '/..' , ymlfile)
    # open方法打开直接读出来
    f = open(yamlPath, 'r', encoding='utf-8')
    cfg = f.read()

    d = yaml.load(cfg, Loader=yaml.FullLoader)  # 用load方法转字典

    for _ in d:
        if _ == "method":
            method = d[_]  # 请求方法

        if _ == "uri":
            uri = d[_]  # 请求的uri

        if _ == "param":
            param = d[_]  # 加在uri后面的带入的参数

        if _ == "data":
            data = d[_]  # 请求体，如果没有的话，就为空，后续发请求需要判断

        if _ == "others":
            headers = d[_]  # 将others参数设置为headers头

        if _ == "condition":
            words = d[_]['words']  # 关键字条件
            time = d[_]['time']  # 时间条件

    for value in headers:  # 因为有纯数字，需要转成string
        if type(headers[value]) == int:
            headers[value] = str(headers[value])

    try:
        if method == "GET":
            requests.packages.urllib3.disable_warnings()
            r = requests.get(url + uri + "?" + param, headers=headers, timeout=5, verify=False )
        elif( method == "POST" ):
            requests.packages.urllib3.disable_warnings()
            r = requests.get(url + uri + "?" + param, data=data ,headers=headers, timeout=5, verify=False )
        elif( method == "PUT" ):
            requests.packages.urllib3.disable_warnings()
            r = requests.put(url + uri + "?" + param, data=data ,headers=headers, timeout=5, verify=False )

        # 把响应头和响应体转换成string
        total = ""
        for _ in r.headers:
            total = total + _ + ":" + r.headers[_] +'\n'
        total = total + r.text

        if words != "":
            if words in total:
                url = url  + '…………vul'
        print(url)

    except Exception as e:
        pass

if __name__ == '__main__':
    data = open('pythonexp/url.txt') # 批量IP
    ymlFile = sys.argv[1]
    reader = csv.reader(data) # 50是线程

    with ThreadPoolExecutor(50) as pool:
        for row in reader:
            if 'http' not in row[0]:
                url = 'http://' + row[0]
            else:
                url = row[0]
            pool.submit(exp, url , ymlFile)