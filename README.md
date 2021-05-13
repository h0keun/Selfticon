# 기프티콘 서비스

스크린샷에서 막힌 상태 이전에 사용했던방식이 deprecated.. 우선사항이 아니니까 큐알코드생성부터 

### error
1. RoomDB를 사용하는데 한액티비티에서 데이터를 roomdb에 저장하고 다른 액티비티의 프래그먼트(리사이클러뷰)위에 데이터를 불러와야한다.
2. 이거아마 MVVM까지 가야하는 영역인듯? 
3. 아닌방법도있을듯함 그방법도 확인하고 넘어가자  
  >> 지금은 firebase realtime database & storage 로 구성중인데 혹시나 앱 출시 까지 가게되면 MVVM 학습하고  
    Room + liveData + Viewmodel + recyclerview로 바꿔서 내부db이용해서 하는것으로.
    제공하는 기능이 그냥 아는사이끼리 공유하는거면 룸이 훨씬 좋지만
    사용자끼리 상호작용하는 단위로 가면 서버통해서 공유해야함
    
    
### 남은거
리사이클러뷰 각 아이템 클릭하면 기프티콘 페이지 나오는데 여기서 수정이나 삭제버튼 누르면 firebase에서 수정 삭제 되도록 하고,  
공유기능추가해서 사람들에게 공유가 가능하도록  
근데 공유해야하는것은 이미지 파일이어야 하니까 공유버튼누를 때 스크린샷? 해서 기프티콘화면 저장하고 (백그라운드에서 처리) 그이미지공유 가능토록 안대면 다른방법  
zxing라이브러리통해서 큐알코드 생성해서 기존 데이터랑 함께 firebase에 담고, 큐알코드 스캔시 해당 기프티콘창이 뜨고  
그 기프티콘을 사용할것인지 클릭하면 firebase에서 자동으로 삭제되도록 등등  
기프티콘 페이지 그럴듯하게 꾸미기 

### 기획
시중에 돌아다니는 기프티콘 쿠폰들을 보면 전부 프랜차이즈사의 상품들만 기프티콘이 존재한다.  
소상공인들도 쉽게 기프티콘을 제작하여 사람들이 사용할 수 있다면?


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
 

+ 위의 내용 완성 이후에 추가적으로 도전할 기능 = 상점(소상공인서비스)
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
[UI](https://play.google.com/store/apps/details?id=com.zlgoon.dailycafe)  
[본](http://www.zlgoon.co.kr/)  
[UI](https://www.behance.net/gallery/85889397/NyamNyam-Foodie-Bucket-List-App)


