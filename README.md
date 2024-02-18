## 1. 동시성 제어
- 대부분 하나의 웹은 여러 개의 요청을 동시에 처리 가능 -> 작성한 코드 한 줄은 동시에 수행 가능 -> 하나의 자원을 두고 여러 개의 연산들이 경합
  <br> -> 데이터 정합성 파괴
- DB에서 동시성 이슈가 발생하는 일반적인 패턴
  1. 공유자원 조회
  2. 공유자원 갱신

- 동시성 테스트: 디버그 모드에서 우클릭
  ![image](https://github.com/SudalKing/Spring-MySQL/assets/87001865/1f97d89d-ce1d-4bee-80c0-10998a787d46)
  <br>
  
## 2. 락
- 락을 통해 동시성을 제어할 때는 락의 범위를 최소화 하는 것이 중요
- MySQL에서 트랙잭션의 커밋 혹은 롤백시점에 잠금이 풀림 -> 트랜잭션이 곧 락의 범위
- MySQL은 쓰기락, 읽기락 모두 제공

  ### 2-1. 비관적 락
  - 동시성 제어를 위한 가장 보편적인 방법은 락을 통한 줄세우기 -> 비관적 락
  - 실패가 없기에 충돌처리가 없음
  - 락을 통한 동시성 제어는 불필요한 대기 상태를 만듦
  - 동시성이 빈번하지 않은 쿼리마저도 대기해버린다면?
  <br><br>
  
  |잠금|읽기락(Shared Lock)|쓰기락(Exclusive Lock)|
   |:---:|:---:|:---:|
   |읽기락(Shared Lock)|O|대기|
   |쓰기락(Exclusive Lock)|대기|대기|
  
   <br><br>
- 읽기락: SELECT ... FOR SHARE
- 쓰기락: SELECT ... FOR UPDATE 또는 UPDATE, DELETE 쿼리
- 매번 락이 발생할 경우, 성능저하 발생 -> MySQL에서 일반 SELECT는 nonblocking consistent read로 동작
- 테이블을 잠그는 __테이블 락__, 로우 하나를 잠그는 __레코드 락__, 로우와 로우 사이를 잠그는 __갭 락__ 등 여러 락이 존재하지만 MySQL의 큰 특징은 __레코드 락__ !!
  <br>
- MySQL에서 잠금은 row가 아니라 __인덱스 잠금__ -> 인덱스가 없는 조건으로 Locking Read시 불필요한 데이터들이 잠길 수 있음★★★
- 아래와 같이 여러 트랜잭션으로 시도하면 테이블 전체에 락이 잡혀버리는 등 전혀 관련이 없는 데이터들에도 영향을 주게 되어 index를 잘 확인해야함!!
  <br>
      ![image](https://github.com/SudalKing/Spring-MySQL/assets/87001865/db92a89d-706c-4c86-a1af-05be188cb0a1)
      ![image](https://github.com/SudalKing/Spring-MySQL/assets/87001865/fbe84205-cb47-4f85-b649-cd9159d058f8)


  ### 2-2. 낙관적 락
  - CAS(Compare And Set)을 통해 제어(비교해서 맞으면 하고 아니면 안하겠다!)
  - version 필드 추가(version을 통해 비교)
  - 실패에 대한 처리를 직접 구현해야함


## 3. 추가로 공부해볼만한 키워드
- Java에서 동시성 이슈 제어방법
- 분산환경에서 동시성 이슈 제어방법
- MySQL의 넥스트 키락이 등장한 배경
- MySQL 외래키로 인한 잠금
- MySQL 데드락

## 4. 대용량 트래픽 처리 마인드셋
 - 데이터의 성질, 병목지점을 파악하고 적당항 기술들을 도입해 해소(ex. redis ...)

## 5. Next Level
 - 팔로워가 100만명인 유저의 게시물 작성 성능 테스트
    - 비동기 큐
    - Mixed Push / Pull Model
 - 팔로우 승인, 취소 구현
 - MySQL Master / Slave (동작방식까지)
 - 파티셔닝
