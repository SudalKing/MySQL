## 1. 실습 내용

1. '''offset''' 과 '''limit''' 을 활용한 pagination
2. Cursor 기반 pagination: 커서를 선정할 때는 index가 있어야 하고 중복키가 없어야 함(unique)
   <br>
   ```bulkInsert``` 는 여러 개의 값을 바인딩해 1건의 쿼리로 여러개의 데이터를 삽입
   
   - bulkInsert 사용x
      Insert query 여러 개 생성
     
   - bulkInsert 사용
      Inser query 한 개로 여러
     
3. 


4. 

5. 

 
 ## 2. Pagination
     - 4번 Offset부터 시작하기 위해서는 0~3번 Offset까지 데이터를 읽음 -> 불필요한 데이터 조회
     - 커서기반 페이징 -> offset기반이 아닌 key값을 이용 -> 데이터 탐색범위를 최소화 -> UI구현은 힘듦
     
     1. 
     2. 
     3. <br>
        3.1) <br>
        3.2) <br>
        3.3)<br>
           1) HashMap - 단건 검색 속도 O(1), 범위 탐색 O(N), 전방 일치 탐색 불가 ex) like 'AB%'<br>
           2) List - 정렬되지 않은 리스트의 탐색 O(N), 정렬된 리스트 O(logN), 정렬 시간 O(N) ~ O(N*logN), 삽입/삭제 비용이 매우 높음<br>
           3) Tree - 트리 높이에 따라 시간 복잡도 결정 => 트리의 높이 최소화<br>
             - B+ Tree - 삽입/삭제 균형, 한 노드에 여러 개의 자식 노드, 리프노드에만 데이터 존재(연속적인 데이터 접근 시 유리)
     4. 
      
### 2-1. ★
      - 
      - ★

      1. 
        - 
        
      2. 
        - 
        
      3. 
        - 


      
