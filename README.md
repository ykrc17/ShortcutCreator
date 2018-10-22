# Android快捷方式生成工具
在思考如何无侵入地替换APP icon，快捷方式是其中一种方案

## 功能
- 通过label、icon、intent生成快捷方式

## 难点
- 只有在生成器存活时才能进行跳转，所以需要先启动生成器，再启动目标Intent

## TODO
- 减小启动目标Intent之前的画面抖动