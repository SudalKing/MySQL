## 1. Transaction
- 여러 SQL문을 마치 하나의 오퍼레이션으로 묶는 것!
     
      
## 2. @Transactional
- java와 springframework 둘 다  ```@Transactional``` 을 지원하지만 springframework쪽이 확장성이 더 좋음
- 프록시 방식으로 동작: 객체를 감싸는 방식으로 동작하기 때문에 inner함수의 transaction이 제대로 동작하지 않음
  <br>
  create() 안의 getMember() 는 동작하지 않음!!!
  <br>
 ![image](https://github.com/SudalKing/Spring-MySQL/assets/87001865/62800f60-deab-40f7-92fd-8dd0476e436d)
<br>

- query에서 transaction의 범위
  <br>
 ![image](https://github.com/SudalKing/Spring-MySQL/assets/87001865/7f5d46bc-3cdd-44c2-b930-f82ee1f030a0)

- transaction이 길어지는 것은 Undo log를 길게 잡는 것 -> 비용 발생 -> transaction은 짧게 가져가는 것이 좋음!

## 3. 격리레벨
- Dirty Read: Commit되지 않은 데이터 조회
- Non Repeatable Read: Tx2에서 아직 commit되지 않은 데이터를 Tx1에서 조회하여 값이 잘못 읽혀지는 것
- Phantom Read: 같은 조건문에서 다른 데이터가 읽히는 것
- 격리레벨이 아래로 갈수록 이상현상은 거의 없어지지만 동시 처리량이 낮아짐(보통 READ COMMITTED > REPEATABLE READ 정도로 쓰임)
  <br>
  
   ||Dirty Read|Non Repeatable Read|Phantom Read|
   |---|:---:|:---:|:---:|
   |Read Uncommitted|O|O|O|
   |Read Committed||O|O|
   |Repeatable Committed|||O|
   |Serializable Read||||
  
      
      
      
