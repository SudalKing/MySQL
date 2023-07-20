## 1. 실습 내용

   1. 

 
## 2. Transaction
- 여러 SQL문을 마치 하나의 오퍼레이션으로 묶는 것!
     
      
### 2-1. @Transactional
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

### 2-2. 
- 
  
      
      
      
