# MyGitHub

- 基于MVVM架构编写的Github客户端
- 使用Kotlin语言
- 网络请求Retrofit + 协程+ LiveData

# 基类

- BaseViewModel 封装了协程请求 + 统一异常处理 + 统一错误toast
DEMO没有区分业务错误，若频繁toast网络异常，有可能是github请求频率限制，可切换网络再行操作

- BaseActivity 封装了进度弹窗

# 项目框架

- [LoginActivity] 登录页面，查看github公有仓库列表 & 跳转搜索页面 & 点击仓库列表项可以查看仓库详情
- [SearchActivity] 搜索页面，输入文本进行搜索，根据star排序，DEMO可选过滤Python语言，DEMO仅支持搜索100个结果。
- [MainActivity] 个人仓库页面，点击仓库列表项可以查看仓库详情
- [RepoContentActivity] 仓库详情页面，登录状态下，支持新增Issues

- 注：DEMO缺失空状态图片资源，上述页面没有数据时，页面留白

# 单元测试

- [ExampleInstrumentedTest] 相关Test方法，可在AndroidStudio下直接运行

# 第三方库：

- [Okhttp] https://github.com/square/okhttp
- [Retrofit] https://github.com/square/retrofit
- [Fresco] https://github.com/facebook/fresco
- [SmartRefreshLayout] https://github.com/scwang90/SmartRefreshLayout

# 使用教程(release apk包在MyGitHub/app/release目录下, 这个包使用开发个人Access Token
# 若需要登录其他账号，请按照以下234指引操作，clone后编译生成新的APK包)

1、基于github指引，在github上面注册OAuth Apps，创建仓库，然后clone下来

2、命令 git clone https://github.com/demokillers/MyGitHub.git

3、基于github限制，需要先获取Access Token方可登录，可以参考下面
https://docs.github.com/cn/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token

4、获取Access Token后，配置到项目根目录下的local.properties文件中，如：
USER_ACCESS_TOKEN="***********"


