## Selfticon
+ 개발환경 : Android Studio, Kotlin, Git
+ 개발기간 : 2021.04 ~ 2021.05
+ 기술스텍 및 라이브러리  
  + Material Design : Custom Bottom Navigation, Material textfield
  + Glide + Content Provider
  + RecyclerView + Firebase : Realtime Database, Storage
  + zxing Library, Web

## 🎤 소개
'Selfticon'은 기프티콘을 만들어주는 앱이다.
데이터는 파이어베이스 서버에 저장되며, 각각의 액티비티에서는 다음과 같은 기능을 담당한다.

+ 기프티콘 생성
  : 상품명, 상품설명, 가격, 교환처, 상품사진 에 해당하는 데이터는 Firebase에 저장되며, 이 정보들을 취합해 QR코드를 만든다.
+ 기프티콘 목록
  : Firebase에 저장된 데이터들을 취합하여 리사이클러뷰를 통해 리스트화 하여 보여준다.
+ 기프티콘 스캔
  : Firebase에 저장된 데이터들을 실시간으로 확인하여 사용한 기프티콘과 미사용한 기프티콘을 구분짓고 삭제 및 공유가 가능하며, 기존에 존재하는 상용 바코드나 QR코드들은 WebView를 통해 그 정보를 제공한다.

## ✏ 기획
시중에 돌아다니는 기프티콘 쿠폰들을 보면 전부 프랜차이즈사의 상품들만 기프티콘이 존재한다. 소상공인분들 혹은 일반 개인들도 쉽게 기프티콘을 만들어서 서로 다른 사람들끼리 사용할 수 있도록 한다면 어떨까 하는 취지에서 시작하게 되었다.

+ Target 1 : 개인카페, 베이커리, 패스트푸드 등을 운영하는 소상공인
+ Target 2 : 연인이나 친구, 가족사이

## 💰 기대효과
일반 사용자들은 개인이 직접만드는 기프티콘을 통해 재미를 얻을 수 있고, 소상공인분들은 자체서비스를 이용한다거나 카카오톡 기프트샾, 당근마켓 등 의 플랫폼을 활용한다던가 함으로써 매출상승 효과를 얻을 수 있다.

## 🎇 상세설명
기능
+ 액티비티 1개 with 프래그먼트 2개
  - 생성한 기프티콘 리스트들이 보여지는 프래그먼트1과 기프티콘을 스캔하는 프래그먼트2  
+ 또다른 액티비티 1개
  - 기프티콘 생성을 위한 액티비티로 여기서 입력하는 정보들을 다른 액티비티의 프래그먼트1 로 보내서 리사이클러뷰를 만든다     
+ 기프티콘 생성시에 입력해야하는 정보  
  1. glide로 디바이스에서 사진 불러오기
  2. 상품명, 가격, 설명 입력하기
  3. 위의 정보들을 큐알코드에 담아서 생성하기
+ 생성한 기프티콘 처리방법
  1. 생성한 기프티콘은 프래그먼트1의 리사이클려뷰에 존재하고 아이템들에는 각각 다른 위의 정보들이 담겨있음
  2. 아이템을 클릭하면 기프티콘 이미지가 뜨고, 그아래에 삭제버튼 + 공유버튼
  3. 삭제버튼을 누르면 리사이클러뷰에서 삭제하고, 공유버튼을 누르면 다른사람에게 기프티콘 사진을 보낼 수 있지만 삭제되지는 않음 단, 공유되었음을 알 수잇게 표시해줘야함
  4. 기프티콘 사진은 프래그먼트2 에서 스캔이 가능함
  5. 스캔시에는 기프티콘 이미지가 뜨고, 그아래에 사용완료 버튼과 취소버튼이 존재
  6. 사용완료버튼을 누르면 리사이클러뷰에서 해당 기프티콘을 찾아 자동으로 삭제되고 취소버튼을 누르면 아무이벤트처리 없이 스캔화면으로 돌아감
  7. 만약 리사이클러뷰에 존재하지 않는 기프티콘을 스캔하였을 경우 토스트 메시지로 이미 사용하였거나 존재하지 않는 기프티콘 입니다 띄우기
 

+ 위의 내용 완성 이후에 추가적으로 도전할 기능 = Firebase로 계속 갈 때 : 상점(소상공인서비스) 
  : 우선 위내용들은 소상공인이 아닌 개인단위에서 재밋게 사용이 가능한 수준, 소상공인분들을 위해선 조금더 섬세한 기능들을 구현해야함
  1. 상점들어가기 버튼을 기존화면 우측상단에 놓고 누르면 로그인 액티비티1 이나오도록(구글로그인)
  2. 로그인이 완료되면 상점만들기 버튼과 둘러보기 버튼이 존재하는 액티비티2 띄우기
  3. 다음에 들어갈땐 로그인된 상태면 액티비티 2 띄우기
  4. 액티비티 2에는 버튼 2개가 존재함 하나는 상점등록하기, 다른 하나는 둘러보기
  5. 둘러보기를 누르면 네이버맵을 보여주는 액티비티3 을 띄워주고, 상점등록하기를 누르면 프로필 입력하는 것처럼 입력가능
  6. 상점등록이 완료되면 다음에 들어갈 때 버튼은 상점관리하기, 둘러보기 버튼으로 바뀐 상태
  7. 네이버맵을 보여주는 액티비티3에는 에어비엔비 숙박시설 보여주듯이 지도위에 마커가 표시되어있고 바텀 시트에 상점들이 보이도록 
  8. 위 액티비티를 들어갓을 때 나오는 정보들은 앱을 가진 사람들 모두가 공유하는 정보임 - firebase
  9. 위의 기능 구현하려면 상점을 등록한 사람들을 구분짓기위해 로그인 인증 받아서 구분 지어야할듯 - firebase
  10. 상점을 클릭해서 들어가면 프로필이 보이고 그아래 상품 리스트들이 존재 
  11. 둘러보는 사람이 상품 리스트를 클릭하면 상품정보가 나오고 대화하기 버튼이 존재  
  
  💡 잠만 이러면 당근마켓이랑 뭐가다르지? 핵심인 기프티콘이 없는딩  
  💡 이 과정은 조금 생각해야할게 많은거같다. 네이버스토어나 당근 이런거에 비해 특장점이라곤 없고 서비스는 한참 부족할 텐데,  
  💡 판이 커져봣쟈 모방하는 수준으로 갈거같음. 우선은 기프티콘이라는점을 살리자  
  💡 기프티콘에 최대한 정보를 깔끔명료하게 담아서 다른 서비스위에 얹힐 수 있도록하는게 개인으로서 진행하는데는 최선이라고 생각든다  
  💡 과연 소상공인을 위한 서비스이긴 한건가 소상공인 + 기프티콘 어떤 특색을 적용해야할지 고민해보쟝
  

## 📷 스크린샷
<img src="https://user-images.githubusercontent.com/63087903/119095476-4bafe980-ba4d-11eb-9337-594b1b0dd69e.jpg" width="200" height="430"><img src="https://user-images.githubusercontent.com/63087903/119096133-27a0d800-ba4e-11eb-8539-a476a0078c12.jpg" width="200" height="430"><img src="https://user-images.githubusercontent.com/63087903/119095489-4eaada00-ba4d-11eb-9d5b-25723a5ef5c6.jpg" width="200" height="430"><img src="https://user-images.githubusercontent.com/63087903/119095492-4f437080-ba4d-11eb-9ce4-d2ac3794efd4.jpg" width="200" height="430">

<img src="https://user-images.githubusercontent.com/63087903/119228343-8c8f2780-bb4d-11eb-89c3-634459fc4db6.jpg" width="200" height="430"><img src="https://user-images.githubusercontent.com/63087903/119228344-8dc05480-bb4d-11eb-9ac2-494b0c2dfcac.PNG" width="240" height="430"><img src="https://user-images.githubusercontent.com/63087903/119228345-8e58eb00-bb4d-11eb-9990-6ec544b90f2a.jpg" width="200" height="430"><img src="https://user-images.githubusercontent.com/63087903/119095490-4eaada00-ba4d-11eb-994b-d9dd73586418.jpg" width="200" height="430">

<img src="https://user-images.githubusercontent.com/63087903/119095471-4a7ebc80-ba4d-11eb-8255-ecfb98e4ad40.jpg" width="200" height="430"><img src="https://user-images.githubusercontent.com/63087903/119126589-2b445700-ba6e-11eb-9a67-a1092f9590c9.jpg" width="200" height="430"><img src="https://user-images.githubusercontent.com/63087903/119126603-30090b00-ba6e-11eb-858d-ffbcd0e8259c.jpg" width="200" height="430">

## 🎬 구동영상

### ⚙ 찾은 버그
1. 기프티콘 만들 때 입력정보 없이 만들게되면 앱 오류나서 꺼짐 + 다시 들어갈때도 강종됨 파이어베이스 확인해보니 리얼타임db의 model 값들이 전부 null로 초기화되어서 1개만존재
  : ✔ 모든 정보기입 이 완료되어야 기프티콘 생성이 가능하도록 하여 해결
2. 생성한 큐알코드가 파이어베이스내에 존재하지 않더라도 스캔하게되면 역으로 정보가 보여지는 문제 발생.  
  사실 큐알코드 스캔결과 보여주는 거니까 파이어베이스랑 관련 없이 정보뜨는게 당연함(이미지는 URL 값이라 캐쉬까지 삭제하면 빈 영역으로 보이는데 한글들은 다 보임)
  : ✔ onDataChange 를 통해 실시간으로 데이터를 읽어들여서(파이어베이스 내에 삭제한 정보를 검색하면 null이 뜨는것 이용)  
    비동기 방식이라 한번 호출되면 데이터 변화 감지될때마다 계속 호출되서 이부분 삽질좀 했는데 어찌저찌 해결함!
3. 생명주기 및 자잘한 버그  
  : ✔ 현재까지는 문제없음  
   [2021-05-20] 삽질 1
   [2021-05-21] 삽질 끝. onDataChange가 비동기 방식이라 콜백처리에 문제가 있었음


## 💡 개선 가능한 부분
+ ver.2 MVVM으로 만들어보기 : Room + ViewModel + LiveData + RecyclerView
+ WebView형식으로 상용바코드정보 보여주는게 아닌 Retorifit으로 데이터를 받아와서 뿌려주기
+ 리펙토링

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
