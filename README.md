# 코틀린 언어가 익숙치 않아서 잠시 보류

# 소상공인을 위한 기프티콘 서비스

스스로에게 주는 과제 : ViewBinding, DataBinding 사용, Glide 사용, recyclerview 사용,   ...  

Floating Action Button - bottom sheet dialog(상품명 등 정보 입력가능하게하고 버튼누르면 그정보를 토대로 리사이클러뷰 생성)  
RecyclerView - 클릭이벤트로 기브티콘 생성하기  
큐알코드 구현이 간단하다면 그냥 위에 바텀시트에서 버튼누를때 어뎁터에 같이 담아도댐 - 클릭이벤트 - 기프티콘 이미지 보여주기  
(확인할점은 만약 바텀시트에서 입력한 사진부터 정보까지 전부 동일하다면 큐알코드가 똑같은지 아닌지 아니라면 해쉬맵? 하튼 어케할지)  
큐알코드 스캔시 리사이클러뷰에 동일한 큐알정보들어있는것 삭제하기 어려웃듯..  
paging라이브러리나 glide라이브러리나 room이나 어케저장할지.. 리사이클러뷰 내용을 리스트뷰처럼 안하고 아얘 카드뷰를 키프티콘 카드처럼 만들기도 낫배드??  

firebase ml kit에 qr code 리더 있긴한데 내가만든 바코드를 일긍ㄹ수있나?



### 기획
시중에 돌아다니는 기프티콘 쿠폰들을 보면 전부 브랜드사의 상품들만 기프티콘이 존재한다.  
소상공인들도 쉽게 기프티콘을 제작하여 당근마켓이나 중고나라 등의 매체에 업로드하고 이를 구매한 사용자들은  
해당 업체에가서 기프티콘을 사용한다.

### 구현해야 할 기능
1. 기프티콘 생성
2. 기프티콘 읽기
3. 사용한 기프티콘인지 아닌지 구분하기 
4. 당근마켓, 중고나라 등에 바로 업로드 가능하도록

### 기능상세
1. Zxing이라는 라이브러리를 통해 바코드 생성 및 읽기가 가능한듯 하다.  
2. Glide를 통해 생성한 바코드와 상품이미지 설명 등을 하나의 이미지로 완성시켜준다.  
3. 완성된 이미지(기프티콘)는 갤러리에 저장(특정 디렉토리안에 저장해야 나중에 확인하기 편할 듯)  
  : 이 부분은 서버를 사용해야 할 지, 갤러리를 사용할 지 어플 내 리사이클러뷰에 보이게할지 등 어떻게 할지 생각해보자
3. 생성한 바코드와 읽은 바코드를 구분 짓는다(생성한 바코드의 목록과 사용한 바코드의 목록을 만들어서 목록이 겹치는 상품 삭제하는방식?)
  : 바코드의 정보를 다루는게 관건일듯 본인의 핸드폰만 사용하는게 아니라 생성한 기프티콘을 남의 핸드폰을통해 읽는거기 때문에  
  바코드를 다루어야함 스캔을 하면 생성했던 기프티콘이 보이고 그아래에 '취소, 사용'을 클릭하도록해서 '사용' 을 누르면 없어지도록?
  서버를 사용하는게 맞나? 추후 고민해보자  
4. 생성한 키프티콘을 당근마켓에 올릴 수 있도록? 근데 소상공인이 당근마켓에 올리고 또 거래하기까지 문자하는등 귀찮은 과정이 있으니  
  이과정을 편하게 해주어야할텐데...어떻게?? 당근마켓같은 중간 매체 활용안하고 전부 플랫폼화해서 담기엔 사용자의 유입이 어려울테니  
  당근마켓을 활용하되.. 저장된 이미지를 보내는 방식말고 이미지링크를 보내는 방식으로? 리사이클러뷰 길게 클릭하면 이미지url 복사..
  혹시라도 업로드한 기프티콘이 많아지면 소상공인이 일일히 매칭시켜야하니까 이 방법은 안될듯..이방법을 써야한다면  
  당근마켓에 올린, 바코드가 지워진 기프티콘 이미지에 url을 담고 사용자 아이디 비번 치면 원래의 이미지가 나오도록  
  그렇게하면 소상공인은 올린 당근마켓 페이지에서 이미지 누르고 url들어가면 비번 입력후 나오는 진짜 기프티콘 이미지를 확인 가능
  당근마켓이랑 제휴해서 당근마켓이 기능을 추가해주면 편하긴할텐데 그런식이면 당근에서 아예 자체서비스만들듯  
  오히려 기프티콘 3000원짜리 커피를 구매하는과정이 귀찮아서 안할지도..

5. 어렵다 일단 기프티콘생성 > 기프티콘 읽기 > 생성했던 기프티콘 읽으면 지우기 이단계까지 구현해보자  
  + 바텀 네비게이션으로 [가운데 + 버튼] = 바코드 생성화면
  : 이부분에서 처리할내용으로 상품 이미지 업로드 or 찍기 해서 이미지 그리기, 상품 정보 담기, 바코드 만들기 이내용 묶어서 하나의 이미지로 만들어서 저장
  + 왼쪽버튼 하나
  : 생성한 바코드목록 보여주기
  + 오른쪽버튼 하나
  : 바코드 스캐너
  
  바코드 스캐너로 스캔하면 왼쪽에 바코드목록에서 지워져야함!
  
## 실무능력 적용할 부분
retrofit 안쓰는게 아쉽긴 하지만 그런대로 최대한 다른것들 많이 적용해보자  
예를들어  
아키텍쳐 패턴 : MVC, MVP, MVVM 중 하나  
JetPack 활용 : dataBinding, livedata, viewmodel 등 무엇이든.  
Glide 활용  
recyclerView 활용 등

