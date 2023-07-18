## 1. 실습 내용

   1. ```offset``` 과  ```limit```  을 활용한 pagination
   2. Cursor 기반 pagination: 커서를 선정할 때는 index가 있어야 하고 중복키가 없어야 함(unique)


 
 ## 2. Pagination
- 4번 Offset부터 시작하기 위해서는 0~3번 Offset까지 데이터를 읽음 -> 불필요한 데이터 조회
  <br><br>
  ```
  select *
  from POST
  where memberId = 2
  order by createdDate desc
  limit 2
  offset 3;
  ```
     
      
### 2-1. Cursor-based pagination
- 커서기반 페이징 -> offset기반이 아닌 key값을 이용 -> 데이터 탐색범위를 최소화 -> UI구현은 힘듦
  <br><br>
  ```
  select *
  from POST
  where memberId = 1 and id > 1000;
  ```

## 3. 커버링 인덱스
- SQL -> Index -> Table
     Index가 잘 생성 되어있다면 Table을 직접 조회하는 것보다 Index Table을 거치는게 더 빠름 -> 그렇다면 index 에서만 데이터를 내려줄 수는 없을까?
- MySQL 에서 id는 클러스터 index이므로 커버링 인덱스에 유리
- LIMIT, OFFSET, ORDER BY가 query에 들어가기 시작하면 불필요한 랜덤 액세스가 발생 -> 커버링 인덱스로 최소화 시킴
  
      
