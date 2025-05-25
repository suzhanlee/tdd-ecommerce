# e-commerce tdd deliberate practice

- 의식적 연습 프로세스
    - DeliberateTDD Coach GPTs 에게 하루 목표 시나리오 학습 세트 요청
    - 요청한 학습 세트를 codeRabbit yaml gen GPTs 에게 yaml 파일 생성 요청
    - branch 변경해 md 파일에 하루 시나리오, 학습 세트 붙여넣기 및 yaml 파일 커밋하고 시작
    - 학습을 완료하고 PR 요청
    - yaml 을 기반으로 codeRabbit이 피드백을 주면, 수정
    - 이 과정 반복한 후, 기준을 통과하면 다음 step으로 넘어가기
    - 모든 step이 끝나면 다음 하루 목표 시나리오로 이동

## 하루 시나리오

```markdown
하루 목표 시나리오
“음식 재고가 충분하면 주문 수량만큼 차감된다”

학습 세트 구조
✅ Step 1. 음식 도메인에 ‘재고 차감’ 정책을 추가한다

✅ Step 2. 음식 주문 요청을 처리하는 서비스 로직을 구현한다

✅ Step 3. 주문 처리 중, 재고 부족 여부를 검증한다

✅ Step 4. 재고 수량 차감 후 Repository를 통해 저장한다

✅ Step 5. 이벤트를 발행하여 ‘주문 성공’을 외부로 전달한다

✅ Step 6. 전체 흐름을 통합 테스트로 검증한다
```

### step1 문제

```markdown
**Step 1: 도메인에서 재고 차감 정책을 구현하자
🧭 STICC 기반 미션 세트
Situation:
도메인에 Food 엔티티가 있으며, stockQuantity 필드를 갖고 있다. 재고를 줄이거나, 부족하면 예외를 발생시켜야 한다.

Task:
음식의 재고가 주문 수량보다 적으면 예외를 발생시키고, 그렇지 않으면 재고를 차감한다.

Intent:
서비스 레이어 이전에 도메인이 자기 책임을 다하도록 만든다.

Concerns:
예외 처리를 어디서 해야 할지 헷갈릴 수 있다. 책임은 도메인이 가져야 한다.

Calibration:
기존 수준보다 i + 1 난이도는 "예외를 던지는 도메인 로직 설계"와 "상태 변경 책임을 도메인에 두는 설계 감각"이다.**
```

### step1 요구 사항 정리

```markdown
1. 상품은 재고를 차감할 수 있다.
   재고보다 많은 수량 차감 시 예외가 발생한다.
2. 재고가 충분하지 않으면 예외를 던진다.
3. 음식은 이름은 비어있을 수 없으며, 재고 수량은 음수일 수 없다.
```

### step2 문제

```markdown
Step 2: 음식 주문 처리 서비스 로직을 TDD로 구현하자

🧭 STICC 기반 미션 세트
Situation:
사용자가 주문을 하면, 음식 재고를 확인하고 차감하는 서비스 로직이 필요하다.

Task:
서비스는 음식 ID와 주문 수량을 받아, 도메인 객체를 활용해 재고를 차감한다.

Intent:
도메인을 조립하는 서비스 계층의 역할을 TDD로 연습하며 익숙해진다.

Concerns:
도메인과 서비스 책임 분리, Repository를 어떻게 mock 할지, 예외를 어디서 처리해야 할지 감이 안 올 수 있다.

Calibration:
기존 도메인 중심 테스트에서 한 단계 나아가, mock/stub을 활용한 서비스 계층 테스트로 난이도를 i+1 상승시킨다.

🛠 [작업 지시] Instruction
서비스 클래스 이름: OrderService

역할: 음식 주문 요청을 처리하며, 재고를 줄이는 작업을 수행한다.

클래스 및 메서드 시그니처 예시:
```

```java
public class OrderService {
private final FoodRepository foodRepository;

    public OrderService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public void order(Long foodId, int quantity) {
        Food food = foodRepository.findById(foodId)
            .orElseThrow(() -> new FoodNotFoundException());
        food.decreaseStock(quantity);
        foodRepository.save(food);
    }

}
```

✅ 명확한 통과 기준
음식 ID와 수량을 전달하면, 해당 음식의 재고가 차감된다
→ 도메인 메서드 decreaseStock이 호출되고, 저장이 수행된다.

음식이 존재하지 않으면 FoodNotFoundException이 발생한다

재고가 부족하면 NotEnoughStockException이 서비스에서 전파된다 (도메인에서 발생)


```java
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private FoodRepository foodRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void 음식이_존재하면_재고를_차감하고_저장한다() {
        Food food = new Food("치킨", 10);
        given(foodRepository.findById(1L)).willReturn(Optional.of(food));

        orderService.order(1L, 3);

        assertThat(food.getStockQuantity()).isEqualTo(7);
        then(foodRepository).should().save(food);
    }

    @Test
    void 음식이_없으면_예외가_발생한다() {
        given(foodRepository.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.order(1L, 3))
            .isInstanceOf(FoodNotFoundException.class);
    }

    @Test
    void 재고가_부족하면_예외가_전파된다() {
        Food food = new Food("치킨", 2);
        given(foodRepository.findById(1L)).willReturn(Optional.of(food));

        assertThatThrownBy(() -> orderService.order(1L, 5))
            .isInstanceOf(NotEnoughStockException.class);
    }

}
```

💡 [힌트] Strategic Hint
Repository는 mock 처리한다: 실제 저장이 아니라, 메서드 호출만 검증

도메인에서 발생한 예외는 서비스에서 그대로 전파한다 (잡지 않는다)

테스트 이름은 행동 단위로 명확하게 작성하자

🧠 [전문가 사고 순서] Expert Micro-Sequence
테스트 작성 우선: "음식 ID를 주면 재고가 줄어야 해" → 테스트로 먼저 명세

mock으로 Repository의 동작을 stub한다

도메인 로직은 실제 객체로 사용한다 (mock 아님)

서비스는 도메인 메서드를 연결만 한다

Repository는 save()가 호출되는지 should()로 검증
