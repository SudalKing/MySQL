## 1. 실습 내용

1. Join을 최대한 미루며 관계 생성
2. MySQL의 bulkInsert 기능 사용
   jpa의 ```saveAll()``` 은 __loop__ 를 돌며 ```save()``` 를 호출해 query를 단건으로 날리지만
   <br>
   ```bulkInsert``` 는 여러 개의 값을 바인딩해 1건의 쿼리로 여러개의 데이터를 삽입
   
   - bulkInsert 사용x
      Insert query 여러 개 생성
     ![image](https://github.com/SudalKing/Spring-MySQL/assets/87001865/74a0a582-0746-4bac-a43a-75411d02684a)

   - bulkInsert 사용
      Inser query 한 개로 여러 개의 데이터 삽입
     ![image](https://github.com/SudalKing/Spring-MySQL/assets/87001865/326d9c61-555c-4d52-b830-a4095718973c)
     ![image](https://github.com/SudalKing/Spring-MySQL/assets/87001865/6b1cb840-aed8-4b91-8414-b2bcf21ba87c)

3. IntelliJ - Ctrl + Shift + A -> show memory indicator -> on
   <br>
   힙 메모리 영역의 용량 확인 가능
      ![image](https://github.com/SudalKing/Spring-MySQL/assets/87001865/f0716730-74d8-4263-9b61-54cb5b0f9224)
      ![image](https://github.com/SudalKing/Spring-MySQL/assets/87001865/763c3436-d9b6-426f-bfc4-d6df405eba36)
 
4. 힙 메모리 영역 용량 조절
      ![image](https://github.com/SudalKing/Spring-MySQL/assets/87001865/c5d29da9-d95a-42c8-8c2c-46c6d1d05325)
      ![image](https://github.com/SudalKing/Spring-MySQL/assets/87001865/90040083-f740-4ee8-9771-913bbdb5e1ec)
      ![image](https://github.com/SudalKing/Spring-MySQL/assets/87001865/4fb883e9-6e64-4d2a-a43d-69187375d613)
      ![image](https://github.com/SudalKing/Spring-MySQL/assets/87001865/4bb74e8a-752f-4a96-8fc6-6c10143b3b86)

6. asd
7.  
 
 ## 2. 데이터베이스 인덱스
     - DB성능의 핵심은 디스크I/O(접근)을 최소화!
     - 인덱스의 핵심은 자료탐색의 최소화!
     
     1. 최대한 메모리에 올라온 데이터로 요청을 처리 -> 메모리 캐시 히트율을 높임
     2. 쓰기도 디스크가 아닌 메모리에 쓰기
     3. 메모리 데이터 유실을 고려해 WAL(Write Ahead Log)사용(랜덤I/O - 여기저기서 가져오는 것, 순차I/O - 연속된 데이터를 가져오는 것)<br>
        3.1) 대부분의 트랜잭션은 무작위하게 write가 발생<br>
        3.2) 이를 지연시켜 랜덤I/O 횟수를 줄이는 대신 순차I/O를 발생시켜 정합성 유지<br>
        3.3)<br>
           1) HashMap - 단건 검색 속도 O(1), 범위 탐색 O(N), 전방 일치 탐색 불가 ex) like 'AB%'<br>
           2) List - 정렬되지 않은 리스트의 탐색 O(N), 정렬된 리스트 O(logN), 정렬 시간 O(N) ~ O(N*logN), 삽입/삭제 비용이 매우 높음<br>
           3) Tree - 트리 높이에 따라 시간 복잡도 결정 => 트리의 높이 최소화<br>
             - B+ Tree - 삽입/삭제 균형, 한 노드에 여러 개의 자식 노드, 리프노드에만 데이터 존재(연속적인 데이터 접근 시 유리)

### 2-1. 클러스터 인덱스★★★
      - PK를 활용한 검색이 빠름, 특히 범위 검색
      - 세컨더리 인덱스들이 PK를 가지고 있어 커버링에 유리★

      1. 클러스터 인덱스는 데이터 위치를 결정하는 키 값
        - 클러스터 키 순서에 따라 데이터 저장 위치 변경 -> 클러스터 키 삽입/갱신 시에 성능이슈 발생
        
      2. MySQL의 PK는 클러스터 인덱스★
        - PK 순서에 따라 데이터 저장 위치 변경 -> PK 키 삽입/갱신 시에 성능이슈 발생
        
      3. MySQL에서 PK를 제외한 모든 인덱스는 PK를 가짐★
        - PK의 사이즈가 인덱스의 사이즈를 결정


      
