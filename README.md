# SpringBoot 整合 TrueLicense实现服务启动license认证



## 使用JDK自带的 keytool 工具生成公私钥证书库

公钥库密码为：public_password1234，私钥库密码为：private_password1234

生成命令

```shell
keytool -genkeypair -keysize 1024 -validity 3650 -alias "privateKey" -keystore "privateKeys.keystore" -storepass "public_password1234" -keypass "private_password1234" -dname "CN=localhost, OU=localhost, O=localhost, L=SH, ST=SH, C=CN"
```

导出命令

```shell
keytool -exportcert -alias "privateKey" -keystore "privateKeys.keystore" -storepass "public_password1234" -file "certfile.cer"
```

导入命令

```shell
keytool -import -alias "publicCert" -file "certfile.cer" -keystore "publicCerts.keystore" -storepass "public_password1234"
```

上述命令执行完成之后，会在当前路径下生成三个文件，分别是：privateKeys.keystore、publicCerts.keystore、certfile.cer。其中文件certfile.cer不再需要可以删除，文件privateKeys.keystore用于当前的 ServerDemo项目给客户生成license文件，而文件publicCerts.keystore则随应用代码部署到客户服务器，用户解密license文件并校验其许可信息。

## my-license-server

启动服务my-license-server

请求如下地址，获取安装证书的硬件信息

```shell
curl localhost:8751/license/getServerInfos?osName=windows
```

响应信息

```json
{
    "ipAddress": [
        "192.168.1.10",
        "172.23.176.1"
    ],
    "macAddress": [
        "28-16-AD-4F-0A-C4",
        "00-15-5D-35-CD-27"
    ],
    "cpuSerial": "BFEBFBFF000406E3",
    "mainBoardSerial": "L2HF73H03T2"
}
```

请求如下地址，生成license

```shell
curl localhost:8751/license/generateLicense -h 'application/json' -d '{"subject":"license_demo","privateAlias":"privateKey","keyPass":"private_password1234","storePass":"public_password1234","licensePath":"D:/data/license.lic","privateKeysStorePath":"D:/data/privateKeys.keystore","issuedTime":"2018-07-10 00:00:01","expiryTime":"2022-12-31 23:59:59","consumerType":"User","consumerAmount":1,"description":"这是证书描述信息","licenseCheckModel":{"ipAddress":["192.168.1.10","172.23.176.1"],"macAddress":["28-16-AD-4F-0A-C4","00-15-5D-35-CD-27"],"cpuSerial":"BFEBFBFF000406E3","mainBoardSerial":"L2HF73H03T2"}}'
```

请求参数格式化

```shell
{
	"subject": "license_demo",
	"privateAlias": "privateKey",
	"keyPass": "private_password1234",
	"storePass": "public_password1234",
	"licensePath": "D:/data/license.lic",
	"privateKeysStorePath": "D:/data/privateKeys.keystore",
	"issuedTime": "2018-07-10 00:00:01",
	"expiryTime": "2022-12-31 23:59:59",
	"consumerType": "User",
	"consumerAmount": 1,
	"description": "这是证书描述信息",
	"licenseCheckModel": {
		"ipAddress": [
			"192.168.1.10",
			"172.23.176.1"
		],
		"macAddress": [
			"28-16-AD-4F-0A-C4",
			"00-15-5D-35-CD-27"
		],
		"cpuSerial": "BFEBFBFF000406E3",
		"mainBoardSerial": "L2HF73H03T2"
	}
}
```

