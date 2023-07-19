## 1. 실습 내용

   1. 두 domain 사이를 넘나들 때는 Usecase 를 사용
   2. Push Model은 공간 복잡도를 희생, Pull Model은 시간 복잡도를 희생
   3. ★★★★ 상황, 자원, 정책 등 여러가지를 고려해 트레이드 오프 해야한다!!! ★★★★

 
 ## 2. Timeline
- Facebook, Twitter 처럼 내가 팔로우한 사람의 피드를 시간순으로 보는 것
     
      
### 2-1. Fan Out On Read(Pull Model) / FaceBook
- 조회 시점에 부하가 발생
- Facebook에서 최대 5,000명의 친구를 보유할 수 있다. 5,000명 이상의 사람들과 연결해야 하는 경우 개인 계정을 변경 해야한다. -> 공간복잡도로 인해

### 2-2. Fan Out On Write(Push Model) / Twitter
- Pull Model 조회시점의 부하를 쓰기시점의 부하로 치환
- 게시물 작성시, 해당 회원을 팔로우하는 회원들에게 데이터 배달
- Timeline table 생성 -> 게시물 작성 시점에 insert
- Twitter에서 5,000명이 최대치이나 나를 팔로우하는 사람이 늘어나면 더 팔로우 할 수 있다.
- 타임라인 배달의 정합성 보장에 대한 고민이 필요!!
  
      
      
