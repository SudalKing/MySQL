## 1. 실습 내용

1. 
 
 ## 2. 데이터베이스 인덱스
DB성능의 핵심은 디스크I/O(접근)을 최소화 하는 것!!
<br>
<br>
1. 최대한 메모리에 올라온 데이터로 요청을 처리 => 메모리 캐시 히트율을 높임
2. 쓰기도 디스크가 아닌 메모리에 쓰기
3. 메모리 데이터 유실을 고려해 WAL(Write Ahead Log)사용(랜덤I/O - 여기저기서 가져오는 것, 순차I/O - 연속된 데이터를 가져오는 것)
   3.1) 대부분의 트랜잭션은 무작위하게 write가 발생
   3.2) 이를 지연시켜 랜덤I/O 횟수를 줄이는 대신 순차I/O를 발생시켜 정합성 유지

