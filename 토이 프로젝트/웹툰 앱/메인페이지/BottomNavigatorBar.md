
# 1. Customize BottomNavigatorBar

```dart
class WebtoonMainScreen extends StatefulWidget {
const WebtoonMainScreen({super.key});

@override
State<WebtoonMainScreen> createState() => _WebtoonMainScreenState();
}

  

class _WebtoonMainScreenState extends State<WebtoonMainScreen> {
int currentIndex = 0;

@override
	Widget build(BuildContext context) {
		return Scaffold(
				appBar: AppBar(
				elevation: 0,
				title: const Text('WebAppBar'),
			),
		body: webtoon_screen_list.elementAt(currentIndex),
			bottomNavigationBar: BottomNavigationBar(
				currentIndex: currentIndex,
				unselectedItemColor: bodySubTextColor,
				selectedItemColor: bodyMainTextColor,
				type: BottomNavigationBarType.shifting,
				onTap: (index) {
					setState(() {
						currentIndex = index;
					});
				},
				items: const [
					BottomNavigationBarItem(
						icon: Icon(Icons.calendar_today),
						label: '웹툰',
					),	
					BottomNavigationBarItem(
						icon: Icon(Icons.auto_stories),
						label: '추천완결',
					),
				],
			),
		);
	}
}

List webtoon_screen_list = [
	const DisplayWebtoonWidget(),
	const TmpScreen(),
];
```


1. bottomNavigationBar : BottonNavigatorBar(); 생성
2. items 속성에 BottomNavigationBarItem 위젯을 여러 개 생성한다.
3. 상황에 맞는 onTap 이벤트를 만들어준다.
	1. webtoon_screen_list 리스트에 화면에 보여줄 위젯을 생성하고 클릭한 BottomNavigationBarItem의 인덱스와 일치하는 위젯을 화면에 보여준다.

- BottomNavigationBar

![[BottomNavigationBar.gif]]