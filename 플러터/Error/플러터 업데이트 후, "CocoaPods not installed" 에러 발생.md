# 1. 에러 내용 확인

몇 분 전까지도 프로젝트가 잘 됐음에도 불구하고 `retrofit` 라이브러리를 설치 및 `flutter upgrade` 명령어 실행 후, 프로젝트를 다시 실행하려고하니 아래와 같은 에러가 발생했다.

- 에러 내용
	```
	Warning: CocoaPods not installed. Skipping pod install. CocoaPods is used to retrieve the iOS and macOS platform side's plugin code that responds to your plugin usage on the Dart side. Without CocoaPods, plugins will not work on iOS or macOS. For more info, see https://flutter.dev/platform-plugins To install see https://guides.cocoapods.org/using/getting-started.html#installation for instructions.
	
	CocoaPods not installed or not in valid state.
	
	Error launching application on iPhone SE (3rd generation).
	```

## 1-1. CocoaPod 다시 설치하기

- CocoaPod 다시 설치하기
	```css
	sudo gem install cocoapods // 1번쨰 시도
	sudo gem install cocoapods --pre // 2번째 시도
	```

위와 같이 다시 CocoaPod 를 다시 설치했음에도 불구하고 프로젝트가 실행되지 않았다 ..


## 1-2. Flutter 다시 설치하기

Flutter 를 Homebrew로 설치했기 때문에 플러터를 아래의 명령어로 삭제 후 다시 설치해보았다.

- Flutter 다시 설치하기
	```shell
	brew remove flutter
	brew install flutter
	```


## 1-3. 문제 해결 !

[Flutter Install Documentation](https://docs.flutter.dev/get-started/install/macos ) 에서 플러터를 설치했던 과정을 다시 살펴보았더니 "Apple Silicon Mac"의 경우 CocoaPods 설치 시, 추가로 설치해주어야했던게 있었다.

- Flutter Install Documentation
	![[플러터/Error/플러터 업데이트 후, "CocoaPods not installed" 에러 발생/이미지/플러터 업데이트 후, "CocoaPods not installed" 에러 발생 1.jpg]]

- M1 애플 이슈
	```
	sudo gem install cocoapods
	
	// M1 애플 아래 코드 추가로 실행
	sudo gem uninstall ffi && sudo gem install ffi -- --enable-libffi-alloc
	```