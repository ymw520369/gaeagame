##消息协议
### 用户认证
 ```code
 url：http://192.168.7.12:8081/data/vertify/credential
 函数：POST
 请求：json
 {
    "token":string
    "uuid":string
 }
 返回:json
 {
    "result":int
    "credential":{
        "certifyToken":string
        "playerId":long
        "sign":string
    }
    "logicUrl":string
 }
```
### 游戏逻辑
1. 登录游戏  
账号认证成功以后，需要向游戏逻辑服务器建立websocket连接并登录  
```code
登录请求
    messageType:1000
    cmd:1
    data:Credential
返回消息
    messageType:1000
    cmd:2
    data:LoginResult
```
 