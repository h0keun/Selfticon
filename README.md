## 📸 Selfticon
+ 개발환경 : Android Studio, Kotlin, Git
+ 개발기간 : 2021.04 ~ 2021.05
+ 기술스텍 및 라이브러리  
  + Material Design : Custom Bottom Navigation, Material textfield
  + Glide + Content Provider
  + RecyclerView + Firebase : Realtime Database, Storage
  + zxing Library, Web, swiperefreshlayout

## 🎤 소개
'Selfticon'은 기프티콘을 만들어주는 앱이다.
데이터는 파이어베이스 서버에 저장되며, 각각의 액티비티에서는 다음과 같은 기능을 담당한다.

+ 기프티콘 생성  
  : 상품명, 상품설명, 가격, 교환처, 상품사진 에 해당하는 데이터는 Firebase에 저장되며, 이 정보들을 취합해 QR코드를 만든다.
+ 기프티콘 목록  
  : Firebase에 저장된 데이터들을 취합하여 리사이클러뷰를 통해 리스트화 하여 보여준다.
+ 기프티콘 스캔
  : Firebase에 저장된 데이터와 기프티콘 스캔결과를 실시간으로 확인하여 사용한 기프티콘과 미사용한 기프티콘을 구분지으며,
  조회된 기프티콘은 삭제 및 스크린샷 공유가 가능하다. 기존에 존재하는 상용 바코드나 QR코드들은 WebView를 통해 그 정보를 제공한다.

## ✏ 기획
시중에 돌아다니는 기프티콘 쿠폰들을 보면 전부 프랜차이즈사의 상품들만 기프티콘이 존재한다. 소상공인분들 혹은 일반 개인들도 쉽게 기프티콘을 만들어서 서로 다른 사람들끼리 사용할 수 있도록 한다면 어떨까 하는 취지에서 시작하게 되었다.

+ Target 1 : 개인카페, 베이커리, 패스트푸드 등을 운영하는 소상공인
+ Target 2 : 연인이나 친구, 가족사이

## 💰 기대효과
일반 사용자들은 개인이 직접만드는 기프티콘을 통해 재미를 얻을 수 있고, 소상공인분들은 자체서비스를 이용한다거나 카카오톡 기프트샾, 당근마켓 등 의 플랫폼을 활용한다던가 함으로써 매출상승 효과를 얻을 수 있다.

## 🎇 상세설명
+ MainActivity - ListFragment, ScanFragment
  ```
  💡 생성한 기프티콘 리스트들이 보여지는 ListFragment와 기프티콘을 스캔하는 ScanFragment
  ListFragment의 아이템들은 Firebase에 저장된 정보들을 가져와서 뿌려주었고,
  ScanFragment는 Zxing 라이브러리를 통해 QR코드 스캐너를 구현하였다.
  MakeActivity로 넘어가는 FloatingActionButton 은 따로 커스텀하여,  
  바텀네비게이션 중앙에 위치하도록 구현
  ```
+ MakeActivity - Firebase
  ```
  💡 기프티콘 생성을 위한 액티비티로 여기서 입력하는 상품 정보들을 Firebase Realtime Database에 저장하고,
  Glide를 통해 내부저장소의 이미지파일들을 불러와서 Firebase Storage에 저장하였다.
  ```
+ GifticonActivity
  ```
  💡 1. ListFragment에서 각 아이템들을 클릭하였을 때 아이템 정보들을 기프티콘의 형태로 보여주는 액티비티
     2. ScanFragment에서 기프티콘을 스캔하였을 때 사용가능여부를 보여주는 액티비티
  
  1.의 과정에선 입력된 정보들을 토대로 Zxing 라이브러리를 이용해 QR코드가 생성되며, 
  기프티콘 정보에대한 삭제와 공유가 이루어진다. 삭제시에는 Firebase에 해당하는 정보들을 제거하고, 
  공유시에는 특정뷰(기프티콘 레이아웃 영역)을 캡쳐하여 내부저장소에 저장하지 않고 바로 공유가 가능하도록 하였다.
  
  2.의 과정또한 동일하며 다만 보여지는 xml레이아웃이 다르기 때문에, 
  SharedPreference 를 이용하여 상태를 구분지음 으로써 listFragment에서 아이템을 클릭했을 때와 
  ScanFragment에서 아이템을 스캔하였을 때 다른 xml이 보여지도록 하였다.
  ```
+ WebViewActivity
  ```
  💡 ScanFragment에서 큐알코드나 바코드 스캔시 해당 정보들을 나타내기위한 웹뷰
  시중에 사용되고 있는 바코드의 경우 웹뷰의 형태로 해당 아이템에대한 정보를 보여주며,
  개인이 직접 만든 기프티콘 스캔시에는 Firebase에서 해당 정보를 불러와 경우에 따른 이벤트 처리를 구현하였다.
  1. 이전에 사용완료한 기프티콘
  2. 사용 가능한 기프티콘
  3. 상품 바코드
  4. QR 링크
  ```
  
## 📷 스크린샷
<img src="https://user-images.githubusercontent.com/63087903/119095476-4bafe980-ba4d-11eb-9337-594b1b0dd69e.jpg" width="200" height="430"><img src="https://user-images.githubusercontent.com/63087903/119096133-27a0d800-ba4e-11eb-8539-a476a0078c12.jpg" width="200" height="430"><img src="https://user-images.githubusercontent.com/63087903/119095489-4eaada00-ba4d-11eb-9d5b-25723a5ef5c6.jpg" width="200" height="430"><img src="https://user-images.githubusercontent.com/63087903/119095492-4f437080-ba4d-11eb-9ce4-d2ac3794efd4.jpg" width="200" height="430">

<img src="https://user-images.githubusercontent.com/63087903/119228343-8c8f2780-bb4d-11eb-89c3-634459fc4db6.jpg" width="200" height="430"><img src="https://user-images.githubusercontent.com/63087903/119297952-f7984580-bc96-11eb-8bac-8602afcab8cf.jpg" width="200" height="430"><img src="https://user-images.githubusercontent.com/63087903/119228345-8e58eb00-bb4d-11eb-9990-6ec544b90f2a.jpg" width="200" height="430"><img src="https://user-images.githubusercontent.com/63087903/119095490-4eaada00-ba4d-11eb-994b-d9dd73586418.jpg" width="200" height="430">

<img src="https://user-images.githubusercontent.com/63087903/119095471-4a7ebc80-ba4d-11eb-8255-ecfb98e4ad40.jpg" width="200" height="430"><img src="https://user-images.githubusercontent.com/63087903/119763538-af716100-beea-11eb-94e7-0379863c5233.jpg" width="200" height="430"><img src="https://user-images.githubusercontent.com/63087903/119763534-ae403400-beea-11eb-9323-d3dfb38fd019.jpg" width="200" height="430"><img src="https://user-images.githubusercontent.com/63087903/119126589-2b445700-ba6e-11eb-9a67-a1092f9590c9.jpg" width="200" height="430">

<img src="https://user-images.githubusercontent.com/63087903/119126603-30090b00-ba6e-11eb-858d-ffbcd0e8259c.jpg" width="200" height="430">

## 🛠 개발과정 겪었던 에러와 해결방안 (코드보며 확인할것!)
+ Firebase Storage, Realtime Database를 다루는 부분에서 에러가 많이 발생
```KOTLIN
1. 프래그먼트 이동시 RecyclerView 의 item이 중복 생성되는 문제

👉 RecyclerView의 item들이 보여지는 fragment는 listFragment 이다.
MakeActivity에서 아이템을 생성하여 파이어베이스에 해당 내용을 업로드하게되면,
listFragment에서는 파이어베이스 addChildEventListener의 onChildAdded() 메소드를 상속받아 읽은후 
아래의 과정을 거처 리사이클러뷰에 추가하게 된다. 

articleList.add(articleModel)
articleAdapter.submitList(articleList)

이때, onChildAdded()는 onViewCreated()에서 addChildEventListener를 호출하여 실행된다.
때문에 다른 프래그먼트로 이동했다가 다시 돌아오게되면 addChildEventListener가 한번더 호출되기 때문에
위의 코드가 한번더 수행된다. 개인적으로는 이러한 이유때문에 아이템이 중복생성되어 리사이클러뷰에 추가되었다고 생각하였고
이를 해결하기위해 onViewCreated() 에 articleList.clear() 를 추가는 방식을 사용하였다.
```
```KOTLIN
✔ 짚고 넘어가야할 내용 ---------------------------------------------------------------------------------------
* scanFragment에서 스캔한 아이템이 존재하는지 여부는 onDataChanged() 를 통해 이루어진다.

onDataChanged()를 사용한 이유
1. 생성된 아이템의 QR코드는 아이템의 이름, 가격, 설명 등의 정보를 문자열로 이어 붙인 값이다.
   : 때문에 QR코드를 스캔하면 위의 텍스트 정보를 보여주게 된다.
2. 여기서 발생되는 문제 
   : QR코드 스캔 가능/불가능 여부는 리사이클러뷰에 존재하는 아이템 인지/아닌지 여부와 상관이 없다!! 
   🥕 (QR코드는 정보를 담고 있을뿐!! 정보의 유뮤가 아님!!)
   때문에 리사이클러뷰에 아이템이 없더라도 QR코드를 스캔하면 이름, 가격, 설명 드의 정보가 그대로 텍스트로 보여진다.
3. 내가 원하던 방식은 QR스캔결과 아이템 존재 여부를 구분짓는것 이다. 
   : QR코드 스캔시 리사이클러뷰에 존재하는 아이템이면 텍스트정보를 읽지만, 
   리사이클러뷰에 존재하지 않는 아이템이면 QR코드 스캔 자체를 진행하면 안된다.
    
리사이클러뷰의 아이템은 Firebase에서 받아오는것이기 때문에
아이템의 존재여부를 확인하기 위해 실시간으로 firebase를 들여다 보는것이 필요했고, 
그것이 onDataChanged를 사용하는 이유이다.(실시간으로 데이터를 읽어들임!)
-------------------------------------------------------------------------------------------------------------

2. ScanFragment 실행후 MakeActivity에서 아이템을 생성하면 강제종료되는 현상

👉 onDataChanged()가 실시간 + 비동기로 호출되는 방식이라 발생한 문제로
해결방안은 아래 '⚙ 찾은 버그' 에 기술해 두었음!
 
```
+ 발생한 에러는 아니지만 기프티콘 공유하기 부분에서 이러한 방법이 있구나 하는것을 기록  
[링크 참조 - FileProvider](https://keykat7.blogspot.com/2021/02/android-fileprovider.html)  
[링크 참조 - View 영역지정](https://hongdroid.tistory.com/6)
```KOTLIN
* firebase 외에 저장소는 최대한 쓰지 않으려고 노력했다.
  
처음에는 생성한 기프티콘을 공유하기위해 
  
1. 화면을 캡쳐하고 
2. 갤러리에 저장된 캡쳐화면에서 기프티콘영역만 따로 잘라낸 후
3. 공유하기

위의 단계를 거치려고 하였으나, 사용자 입장에서 해당 앱 이외에 또 다른 앱을 사용해야 한다는 번거로움과
추가적인 저장공간을 필요로 하기 때문에 더 나은 방안이 있을까 구글링 하게되었고, 
위의 링크를 참조하여 저장소에 저장하는것 없이 특정View(기프티콘 영역) 만 캡쳐하여 
바로 공유하는 방식을 구현하였다.
```
### ⚙ 찾은 버그
1. 기프티콘 만들 때 입력정보 없이 만들게되면 앱 오류나서 꺼짐 + 다시 들어갈때도 강종됨  
  : 파이어베이스 데이터 정보를 확인해보니 리얼타임DB 의 model 값들이 전부 null로 초기화되어서 아이템이 Null값으로 1개만존재하게 되어 생긴 문제  
  ✔ 모든 정보의 기입이 완료되어야 기프티콘 생성이 가능하도록 하여 해결  
2. 생성한 큐알코드가 파이어베이스내에 존재하지 않더라도 스캔하게되면 역으로 정보가 보여지는 문제 발생.  
  : 사실 큐알코드 스캔결과 보여주는 거니까 파이어베이스랑 관련 없이 정보뜨는게 당연함(이미지는 URL 값이라 캐쉬까지 삭제하면 빈 영역으로 보이는데 한글들은 다 보임)  
  ✔ onDataChange 를 통해 실시간으로 데이터를 읽어들여서(파이어베이스 내에 삭제한 정보를 검색하면 null이 뜨는것 이용)  
  큐알코드가 인식되더라도 파이어베이스에 존재하는지 여부에 따라 사용가능한 큐알코드인지 사용완료한 큐알코드인지를 구분 지음.  
3. onDataChange 가 비동기 방식이라서 아이템이 추가될 때마다 계속 호출되어서 의도와는 다른 이벤트가 실행되는 문제  
[2021-05-20] 삽질시작  
[2021-05-21] 삽질 끝. onDataChange가 비동기 방식이라 콜백처리에 문제가 있었음  
  : ValueEventListener 가 한번 호출이 되고나면, 데이터 변화 감지될때 마다 onDataChange가 계속 호출된다고 함. 이를 해결하기 위해서  
  ```
  ValueEventListener를 add하고 remove하는 메소드를 적절히 사용해야 했는데 나의 경우에는 스캔된 기프티콘 정보를
  onActivityResult를 통해 받아왔기 때문에 firebase에 저장된 정보와 실시간으로 비교하기 위한 
  ValueEventListener 처리를 전부 onActivityResult 내부에서 처리하게 되었고, 이는 전역적으로 설정된것이 아니기 때문에 
  onDestoryView에서 remove메소들르 호출하는게 불가능 했다. 그렇다고 ValueEventListener를 전역으로 설정하면 
  onActivityResult를 통해 받아온 변수(정보) 들을 onDataChange에서 사용하는것이 불가능 했다.
  
  >> 삽질결과 해결방법
  : companion object를 통해 미리 널러블한 기프티콘 변수(정보) 들을 선언하고 onActivityResult를 통해 받아온 정보들을 변수값에 할당하였다. 
  이어서 addValueEventListener를 호출함으로써 onDataChange에서 값이 할당된 companion object 변수들을 사용할 수 있었고,
  변수들을 조합해 Firebase 데이터베이스와 비교가 가능하도록 하였다(위의 2. 과정) 
  이후 최종적으로 onDestroyView에서 removeValueEventListener 를 호출함으로써 ScanFragment를 벗어났을 때 이벤트를 아예 종료시켜
  onDataChange가 호출되는것을 방지하였고, 이를통해 의도와는 다른 이벤트가 실행되는 것을 해결하였다.
  ```

## 💡 개선 가능한 부분 및 추가로 알아볼 부분
+ ver.2 MVVM으로 만들어보기 : Room + ViewModel + LiveData + RecyclerView
+ 리사이클러뷰는 다양한 방식으로 만들어 낼 수 있기 때문에 개발자마다 만들어내는 방식이 제각각인데
  그렇기 때문에 구현할수 있음에 그칠게 아니라 완져어언 디테일하게 들어가서 동작방식 구성 등 확실히 체크할 필요가 있을거 같다.
+ 리펙토링
+ object 활용
+ coordinatorlayout
+ custom UI

## 📌 사용한 오픈소스
[Zxing](https://github.com/zxing/zxing)

## 📝 License
```
   Copyright [2021] [HoKeun.LEE]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
