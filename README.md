# 4주차 미션 - 편의점

## 🤔 3주차 회고

- 3주차의 목표는 다양한 테스트 메서드 활용하기, 단위 테스트 도입으로 커버리지 100% 달성하기, `ReadMe`에 주요 메서드의 기능을 정리하기였다.
- 테스트에서 시간이 많이 소요되었지만 굉장히 유익했고, 앞으로도 유용하게 사용할 것이라는 확신이 들었다.

![참 잘했어요](greatJob.png)

- 참 잘했어요

### 아쉬웠던 점

- 1주차에 비해서는 굉장히 클린하고 아름다운 코드로 발전했지만, 만족하지 못하는 부분이 있었다.
    1. `ObjectFactory`를 사용해 의존관계를 클라이언트로부터 숨겼지만, 클라이언트는 여러 객체를 꺼내서 직접 호출해야 했다. 완전히 분리된 것 같지 않다.
    2. `Service`에서 응답 데이터를 조립하기 위해 `StringBuilder`에 하나씩 더하는 코드가 있었다. 좀 더 깔끔하게 분리할 순 없을까?
    3. `LotteryMachineModel` 이 통계 관련 책임을 분리했음에도 너무 비대한 느낌이다.
- 마지막 4주차에서는 지금까지 새롭게 학습하면서 열심히 쌓아올렸던 것을 좀 더 단단하게 다질 계획이다.
- TDD도 추천해주셨지만, 할애할 수 있는 시간이 매우 부족한 상황이라 기반 다지기에 집중할 것이다.

## 😠 4주차 목표

- 클라이언트($Application)로부터 내부 로직 완벽하게 숨기기
- **요구 사항 4번(책임 분리) 잘 지켜보기**
    - Service 클래스 분리 더 잘해보기, 특히 출력 관련 로직을 어떻게 더 깔끔하게 넣을 수 있을지 고민하기
    - Model 책임 쪼개기
- 과제를 끝내고 간단한 클래스 다이어그램도 넣어, 좀 더 쉽게 이해할 수 있도록 문서를 작성해보기

## 🤓 3주차 공통 피드백 - 내가 지키지 못한 것

- 비즈니스 로직과 UI 로직을 분리한다
  > 출력을 담당하는 클래스는 잘 분리했으나, 이 출력을 위해 서비스 클래스는 1. 데이터를 처리하고, 2. 데이터를 조립하는 역할을 지니게 되었다.
- 객체는 객체답게 사용한다
  > 서비스 클래스가 난잡했던 또다른 이유는 저장된 데이터를 꺼내서 모든 행동을 대신 처리했기 때문도 있는 것 같다.
- 필드의 수를 줄이기 위해 노력한다
  > `LotteryMachineModel`에서 여러 데이터를 저장하여 너무 비대해졌다는 느낌을 받았다.
  >
  > `StatisticModel`에서 편의를 위해 총 상금을 저장하는 필드를 추가했다.
- private 함수를 테스트 하고 싶다면 클래스(객체) 분리를 고려한다
  > private 메서드를 테스트하기 위해 리플렉션을 이용했다. 이를 지양하고 클래스 분리를 고민하자.

## 🔍 프로그래밍 요구사항

- 메서드의 길이가 10라인을 넘어가지 않도록, 한 가지 일만 하도록 구현한다.
- 입출력을 담당하는 `InputView`, `OutputView` 클래스를 구현한다.

## ✔️ 기능 요구사항

- 할인 혜택과 재고 상황을 고려하여 최종 결제 금액을 계산 및 안내하는 결제 시스템

<br>

- 전체
    - 사용자가 입력한 상품 가격과 수량을 기반으로 최종 결제 금액을 계산한다
        - 총구매액 = 상품별 가격 x 수량
        - 프로모션 및 멤버십 할인 정책을 반영하여 최종 결제 금액을 산출한다
    - 구매 내역과 금액 정보를 영수증으로 출력한다
        - 출력 후 추가 구매를 진행할 수 있다
- 재고 관리
    - 재고 수량에 따라 결제 가능 여부를 확인한다
    - 상품을 구매하면 재고에서 차감하여 최신 재고 상태를 유지한다
- 프로모션 할인
    - 오늘 날짜가 프로모션 기간에 포함된 경우 할인을 적용한다
    - 프로모션은 N+1 형태다
    - 동일 상품에 여러 프로모션이 적용되지 않는다
    - 프로모션 재고가 떨어지면 일반 재고를 사용한다
    - 프로모션 수량을 충족하면 혜택을 받을 수 있고, 재고 부족 시 일부 수량이 정가로 결제됨을 안내한다
- 멤버십 할인
    - 프로모션 미적용 금액에 대해 30%를 할인받는다
    - 최대한도는 8000원이다
- 영수증 출력
    - 고객의 구매 내역과 할인을 요약하여 출력한다

## 📜 기능명세서

- [x] 환영 인사를 출력한다

<br>

- [x] 프로모션 정보를 불러와 저장한다
- [x] 상품 정보를 불러와 저장한다
- [x] 상품 정보를 출력한다

<br>

- [x] 구매할 상품명과 수량을 입력받고, 검증한다
- [x] 다른 조건을 고려하지 않고, 일반 상품을 구매 처리한다

<br>

- [x] 구매, 증정, 금액 영수증을 출력한다
- [x] 추가 구매 여부를 확인하고 반복 여부를 처리한다

<br>

- [x] 구매 시 프로모션을 적용한다

<br>

- [x] 프로모션 혜택 적용이 가능하면 안내하고 추가 여부를 처리한다

<br>

- [ ] 프로모션 재고가 부족하면 안내하고 구매 여부를 처리한다

<br>

- [ ] 멤버십 할인을 안내하고 할인 여부를 처리한다
