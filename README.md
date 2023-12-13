#### EMQX Docker 安装启动

```
docker pull emqx/emqx:5.2.1
```

```
docker run -d --name emqx -p 1883:1883 -p 8083:8083 -p 8084:8084 -p 8883:8883 -p 18083:18083 emqx/emqx:5.2.1
```



#### mq-emqx-simple

- ##### 注入消息发送对象

  ```java
  @Service
  public class UserServiceImpl implements UserService {
  
      @Resource
      WildCardMessage message;
  
      @Override
      public void login() {
          User userInfo = getUserInfo();
          // 发送设置通知信息
          message.send(UserListener.CouponsTopic + "/g0/" + userInfo.getId(),
                  JSONObject.toJSONString(userInfo));
      }
  }
  ```

- ##### 添加监听事件

  ```java
  @EMQX
  @Component
  public class UserListener {
  
      public static final String CouponsTopic = "CouponsTopic";
  
      @EMQXListener(topic = CouponsTopic + "/g0/1")
      public void coupons0(String json) {
          // 设置优惠卷到期通知逻辑
          System.out.println("hello g01");
      }
  }
  ```



#### 消息类型

- ##### 保留消息 

  ```java
  @Service
  public class UserServiceImpl implements UserService {
  
      @Resource
      RetainMessage retainMessage;
  
      @Override
      public void login() {
          User userInfo = getUserInfo();
          // 发送设置通知信息
          retainMessage.send(UserListener.CouponsTopic + "/g0/" + userInfo.getId(),
                  JSONObject.toJSONString(userInfo));
      }
  }
  ```

- ##### 延迟消息

  ```java
  @Service
  public class UserServiceImpl implements UserService {
  
      @Resource
      DelayMessage delayMessage;
  
      @Override
      public void login() {
          User userInfo = getUserInfo();
          // 发送设置通知信息
           delayMessage.send(UserListener.CouponsTopic + "/g0/" + userInfo.getId(),
                  JSONObject.toJSONString(userInfo), 10L);
      }
  }
  ```

- ##### 遗嘱消息 

  ```java
  @Configuration
  public class WillConfig {
  
      @Bean
      public WillMessage willMessage() {
          WillMessage willMessage = new WillMessage();
          willMessage.setTopic("lostBean");
          willMessage.setContent("bye bye");
          willMessage.setQos(QualityOfServiceEnum.QoS0);
          willMessage.setRetained(true);
          return willMessage;
      }
  }
  ```



