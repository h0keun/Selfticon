### 진행상황
<img src="https://user-images.githubusercontent.com/63087903/118218738-06227800-b4b3-11eb-8307-d4cccf51fff0.jpg" width="200" height="430"><img src="https://user-images.githubusercontent.com/63087903/118218744-091d6880-b4b3-11eb-9163-93e64b9b7459.jpg" width="200" height="430"><img src="https://user-images.githubusercontent.com/63087903/118218747-0ae72c00-b4b3-11eb-8380-bb8e60cd5e66.jpg" width="200" height="430"><img src="https://user-images.githubusercontent.com/63087903/118218752-0d498600-b4b3-11eb-81c9-7a21d57de3f0.jpg" width="200" height="430">

<img src="https://user-images.githubusercontent.com/63087903/118218760-0e7ab300-b4b3-11eb-968f-55efe429e99c.jpg" width="200" height="430"><img src="https://user-images.githubusercontent.com/63087903/118444049-9ff25b00-b727-11eb-9c8e-03c81a551609.jpg" width="200" height="430"><img src="https://user-images.githubusercontent.com/63087903/118218765-1175a380-b4b3-11eb-912a-79826991d6a0.jpg" width="200" height="430"><img src="https://user-images.githubusercontent.com/63087903/118445580-95d15c00-b729-11eb-9771-85d35f4bc841.jpg" width="200" height="430">

<img src="https://user-images.githubusercontent.com/63087903/118445583-97028900-b729-11eb-8108-77535e2adc27.jpg" width="200" height="430">

+ Material Design, Glide
+ Firebase : Realtime Database와 Storage 를 이용해 아이템을 추가하고 리사이클러뷰에 받아오는중
+ zxing Library : QR코드 생성과 스캔을 위한 라이브러리 사용중

### ERROR
큐알코드 스캔시 경우에따라 처리해야할 내용들이 많음  
1. 만든 큐알코드가 아닌 기존에 존재하는 바코드나 큐알 코드스캔시 > detail : 사이트 이동(load url or Image search)
2. 삭제한 기프티콘의 큐알코드 스캔시 > 기존에 존재하는 기프티콘 처럼 스캔이 됨  
  : 로컬에 캐쉬정보가 남아있어서 생기는 문제같음 그렇다고 캐쉬데이터를 다 지우면 인터넷 연결이 되어있지 않을 때 기존에 있는 아이템들도 이미지가 안보임
  이 부분 아무래도 캐쉬가 계속 쌓여서 30mb까지 찬거보니 ChildEventListener 에서 캐쉬값 삭제하던가 해야할듯?? 이긴한데 인스타나 페북도 캐시 계속쌓이긴하넹..
3. 어찌저찌 구현을 완료하였는데(기존에 존재하는 바코드나 큐알 코드 스캔 가능 + 삭제한 기프티콘 큐알코드 스캔시 이미 사용한 기프티콘이라는 알람)  
  이게 과연 올바른 코드인지는 잘 모르겠음,, ChildEventListener나 DatachangeListener 쪽 더 주의깊게 보고 로컬캐쉬 관리하는 부분 
  
### 찾은 버그
1. 기프티콘 만들 때 입력정보 없이 만들게되면 앱 오류나서 꺼짐 + 다시 들어갈때도 강종됨 파이어베이스 확인해보니 리얼타임db의 model 값들이 전부 null로 초기화되어서 1개만존재
  : 이거는 기프티콘 생성하기 버튼을 정보 입력후에 보이도록하면 간편하게 해결 가능할듯
2. 인식한 기프티콘 사용하기 버튼 눌럿을 때 10번중 2번은 강종되는경우 있음 혹은 쌔까만 화면나왔다가 전환됨
  : 하나는 액티비티 재시작과정에서 기존거 finish 없이 해서 그런것 같고, 다른 하나는 ChildEventListener나 DatachangeListener 이쪽관련 문제같음
  사실 둘이 같은 역할인데 각각 다른곳에서 사용해서 그런거 같기도하고... 데이터 변화값을 받는 부분인데 버튼을 통해 데이터가 변화하면 에러발생하는거니까 이쪽에도 문제 있을 거같기도함 
3. 체크해본건 아닌데 내가 저장한 큐알코드명이나 사진명을 입력한 정보를 순서대로 붙여서 만든 건데 이게 스캔과정에서 불러오는 값은 배열에 trim으로 " " 스페이스를 나눈거라 실제 입력정보가
  5개라고해서 배열길이가 5가아니라 훨씬 길 수도 있음 근데 이거는 지금생각으로 에러는 없앨수있는데 스캔할 때 정보들이 띄어쓰기가 없는 정보가 될듯.. 어케하지? 
  
### 남은거
+ 공유버튼 클릭시 기프티콘 이미지가 보이는 view만 따서 스크린샷 공유
+ GifticonActiviy 레이아웃부분 수정 : 리스트프래그먼트에서 아이템클릭해서 들어가는것과, 스캔프래그먼트에서 스캔후 들어가는데에 레이아웃 차이를 주어야함  
  visible 속성으로 가능할듯??
+ Firebase가 아니라 Room - ViewModel - LiveData - recyclerView (MVVM) 으로 구현할 필요가 있어보임(목적상)


### 기획
시중에 돌아다니는 기프티콘 쿠폰들을 보면 전부 프랜차이즈사의 상품들만 기프티콘이 존재한다.  
소상공인들도 쉽게 기프티콘을 제작하여 사람들이 사용할 수 있다면? 아니면 일반 사람들이 재미있게 기프티콘을 주고받는다묜?
지금 내 수준에서 구현하고자하면 전자의 방식은 Firebase를 최대한 사용하는거고, 후자의 경우는 RoomDB 최대한 

유형 : 카페, 베이커리, 패스트푸드 (타겟층 기프티콘 많이쓰는 젊은층) why? 기프티콘 서비스는 프랜차이즈 한정임.  
플랫폼 : 자체서비스로할지 아니면 배달의민족이나 당근마켓, 카카오톡 기프티콘샾 같은 서비스위에 얹혀지는 서비스로 할지  
  - 다른 플랫폼위에 얹혀지는거면 그과정이 귀찮아서 사람들이 이용 안할지도?
  - 자체 플랫폼으로 하면 구현해야할게 상당히 많아짐 
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
  
### 아마 사용해야할 기술?
+ Zxing : 바코드 생성 및 스캔 라이브러리
+ Firebase : 상점서비스까지 갔을 때
+ Glide : 디바이스사진 업로드할때
+ Room : 리사이클러뷰 저장
+ 그냥 시작부터 로그인기능 넣고 firebase연동할까(상점까지 안가고 개인단위에서 가족.친구들 사이에서 쓰기엔 과함)
+ 일단 생각나는거는 이정도
  
## 실무능력 적용할 부분
retrofit 안쓰는게 아쉽긴 하지만 그런대로 최대한 다른것들 많이 적용해보자  
예를들어  
아키텍쳐 패턴 : MVC, MVP, MVVM 중 하나  
JetPack 활용 : dataBinding, livedata, viewmodel 등 무엇이든.  
Glide 활용  
recyclerView 활용 등


