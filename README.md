# Android快捷方式生成工具
在思考如何无侵入地替换APP icon，快捷方式是其中一种方案

## 功能
- 通过label、icon、intent生成快捷方式
- 通过快捷方式打开指定应用

## 难点
- 只有在生成器存活时才能进行跳转，所以需要先启动生成器，再启动目标Intent

## TODO
- 从相册选择快捷方式图标
- 应用列表搜索
- 打开指定Activity，但是Activity需要注册有action