# XPLUS Music Addons

两个给 [Music HUD](https://modrinth.com/mod/music-hud)（`music_hud`）用的辅助模组，做于 Minecraft **26.3** / Fabric。

| 模组 | 端 | 作用 |
|---|---|---|
| `xplus_musiccmd` | 客户端 | 注册 `/music` 指令，打开 Music HUD 主界面 |
| `xplus_musicguard` | 服务端 | 只允许白名单玩家点歌 / 删歌 / 投票跳歌 |

## xplus_musiccmd

Music HUD 默认用 `M` 键开界面，但 `M` 常被 Xaero 小地图之类的模组占掉。装了这个就用 `/music`，随时能开。

实现上有个坑：指令执行时聊天界面还没关闭，直接 `setScreen` 会被聊天关闭逻辑顶掉，所以延后一 tick 再打开。

## xplus_musicguard

Music HUD 本身不限制谁能改播放队列——任何玩家都能点歌、删歌、投票跳过。这个模组在服务端拦截三种请求，只放行 `Allow.java` 里列出的名字：

- `ClientPushMusicToQueueMessage` —— 点歌
- `ClientRemoveMusicFromQueueMessage` —— 删歌
- `VoteSkipCurrentMusicMessage` —— 投票跳过

**默认名单只有 `LOU0122` 一个**，改 `musicguard/src/uno/xplus/musicguard/Allow.java` 重新编译即可。想让所有人都能用，那就别装这个模组——Music HUD 的默认行为就是人人可点。

## 下载

| 文件 | 端 | 从哪拿 |
|---|---|---|
| `xplus_musiccmd-1.3.0.jar` | 客户端 | 本仓库 Release |
| `xplus_musicguard-1.1.0.jar` | 服务端 | 本仓库 Release |
| `Music HUD 1.3.1+26.3` | 双端 | [Modrinth 官方页面](https://modrinth.com/mod/music-hud)（作者 indi.etern，LGPL-3.0） |

Music HUD 本体是别人做的，这里只放指向官方页面的链接，不重复分发它的 jar——官方页面本身支持 26.3，直接下最新版就行。

## 依赖

- Minecraft **26.3**
- Fabric Loader `>=0.16.0`
- Fabric API
- Music HUD `1.3.1+26.3`（两个模组都依赖它的内部类）

## 安装

编译好的 jar 直接丢进 `mods/`：`xplus_musiccmd` 进客户端，`xplus_musicguard` 进服务端。

## 编译

这两个模组没有用 Gradle，是单文件级的 javac 编译。`build.sh` 里改掉几个路径变量即可：

```bash
./build.sh musiccmd
./build.sh musicguard
```

classpath 上需要：Fabric Loader、Fabric API、Minecraft 26.3 客户端 jar、Music HUD jar、Mixin。

## 稳定性说明

`xplus_musicguard` 是 Mixin 注入到 Music HUD 的内部类（`...ClientPushMusicToQueueMessage$RegisterImpl` 的 lambda），**Music HUD 大版本更新后类名或方法名一变就会失效**。失效表现是服务端启动时报 Mixin 注入失败，或者白名单悄悄不生效。升级 Music HUD 后建议进服试一下点歌还在不在。

## 许可

MIT
