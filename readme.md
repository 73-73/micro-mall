##运行环境

启动nginx   
nginx的配置见nginx.conf文件   
```nginx start```  
启动阿里云的搜索引擎和redis  
启动rabbitmq  


### RabbitMq
默认登陆账户密码  
username：guest  
password：guest  
系统使用的是  
域名：/mall  
username：mall  
password：mall  
客户端接口：15672  
RabbitMq监听端口：5672


> 另外需要改一下系统的HOST文件，使域名跳转到本机，不然不改的话以127.0.0.1访问会触发跨域问题
```asp
# 商城
127.0.0.1 manage.mall.com
127.0.0.1 api.mall.com
127.0.0.1 www.mall.com
127.0.0.1 image.mall.com
127.0.0.1 www.mall.com
```
