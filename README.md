## 1. 실습 내용

   1. 
   2. 


 
 ## 2. Timeline
- 
     
      
### 2-1. Fan Out On Read(Pull Model) / FaceBook
- 조회 시점에 부하가 발생

### 2-2. Fan Out On Write(Push Model) / Twitter
- Pull Model 조회시점의 부하를 쓰기시점의 부하로 치환
- 게시물 작성시, 해당 회원을 팔로우하는 회원들에게 데이터 배달
- Timeline table 생성 -> 게시물 작성 시점에 insert
  
      
      
