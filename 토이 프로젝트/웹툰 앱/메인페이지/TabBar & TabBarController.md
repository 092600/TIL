

# 에러

## TabBar & TabController

- Column의 Height 값이 지정되지 않아 발생하는 에러
> 	Exception has occurred. FlutterError (Horizontal viewport was given unbounded height. Viewports expand in the cross axis to fill their container and constrain their children to match their extent in the cross axis. In this case, a horizontal viewport was given an unlimited amount of vertical space in which to expand.)

```dart
class _WebtoonMainScreenState extends State<WebtoonMainScreen> with TickerProviderStateMixin {
late TabController _tabController;
	
	@override
	void initState() {
		super.initState();
		_tabController = TabController(length: 3, vsync: this);
	}
	
	  
	
	@override
	
	Widget build(BuildContext context) {
	return Scaffold(
		appBar: AppBar(
			elevation: 0,
			title: const Text('WebAppBar'),
			bottom: TabBar(
			controller: _tabController,
			tabs: const <Widget>[
				Text('hi'),
				Text('hi'),
				Text('hi'),
			],
		),
	),
	body: SizedBox(
		width: 500,
		child: Column(
			children: [
				TabBarView(
					controller: _tabController,
					children: const <Widget>[
							Center(
								child: Text('child'),
							),
							Center(
								child: Text('child'),
							),
							Center(
								child: Text('child'),
							),
						],
					),
				],
			),	
		),
	);}

}


%% 아래와 같이 Column 위젯을 Expanded 위젯으로 감싸주기 %%
... 
Expanded(
	Column(
		...
	),
),
```



## Customize TabBar Indicator 

URI : https://pub.dev/packages/tab_indicator_styler/install
Dependency : tab_indicator_styler: ^2.0.0 # tabbar indicator customizer



## TabBar 구현하기

1. TabBarController 구현

```dart
class WebtoonMainScreen extends StatefulWidget {
const WebtoonMainScreen({super.key});

@override
State<WebtoonMainScreen> createState() => _WebtoonMainScreenState();

}

class _WebtoonMainScreenState extends State<WebtoonMainScreen> with TickerProviderStateMixin {

late TabController _tabController;

@override
void initState() {
	super.initState();
	_tabController = TabController(length: 10, vsync: this);
}

@override
Widget build(BuildContext context) {
return Scaffold(
		body : Column(
			children : [
				SizedBox(
					child: WebtoonMainTabBar(tabController: _tabController),
				),
				Expanded(
					child: TabBarView(
					controller: _tabController,
					children: const <Widget>[
					Center(
						child: Text('child'),
					),
								...
					Center(
						child: Text('child'),
					),
				),
			],
		),
}
```


2. WebtoonMainTabBar 위젯 구현
```dart
class WebtoonMainTabBar extends StatelessWidget {

const WebtoonMainTabBar({
	super.key,
	required TabController tabController,
}) : _tabController = tabController;

final TabController _tabController;

@override
Widget build(BuildContext context) {
	return TabBar(
		controller: _tabController, // 위에서 생성한 컨트롤러
		indicatorSize: TabBarIndicatorSize.tab, // indicatorSize 정하기
		isScrollable: true,  // TabBar scroll 기능 추가
		unselectedLabelColor: bodyMainTextColor,
		labelColor: primaryColor,
		tabs: const <Widget>[
			WebtoonMainTab(tabbarName: "신작"),
						...
			WebtoonMainTab(tabbarName: "완결"),
		],
		
		indicator: MaterialIndicator(
			height: 4,
			color: primaryColor,
			topLeftRadius: 0,
			topRightRadius: 0,
			tabPosition: TabPosition.bottom,
			),
		);
		
	}
```

indicatorSize 관련 URI : https://api.flutter.dev/flutter/material/TabBarIndicatorSize.html

- Indicator.label : IndicatorSize = Label Size

![[indicatorSize.label.gif]]


- Indicator.label : IndicatorSize = Tab Size

![[indicatorSize.tab.gif]]
