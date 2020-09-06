<a><img src="https://i.ibb.co/W2Lx3Cp/junit-mockito.jpg" alt="junit-mockito" border="0"></a>

## JUnit5 & Mockito testing with Spring Boot

[![](https://img.shields.io/badge/docs-velog-brightgreen)](https://velog.io/@dnjscksdn98/series/JUnit-Mockito)
[![](https://img.shields.io/badge/references-udemy-brightgreen)](https://www.udemy.com/course/learn-unit-testing-with-spring-boot/)

## 🎯 목차

- [Mock이란?](https://github.com/dnjscksdn98/master-java-unit-testing/new/master?readme=1#-mock%EC%9D%B4%EB%9E%80)
- [@Mock & @InjectMocks](https://github.com/dnjscksdn98/master-java-unit-testing/new/master?readme=1#-mock-%EC%9D%84-%EC%82%AC%EC%9A%A9%ED%95%B4%EC%84%9C-mock-object-%EC%83%9D%EC%84%B1)
- [Return Multiple Values](https://github.com/dnjscksdn98/master-java-unit-testing/new/master?readme=1#-return-multiple-values)
- [Argument Matchers](https://github.com/dnjscksdn98/master-java-unit-testing/new/master?readme=1#-argument-matchers)
- [Verify Method Calls](https://github.com/dnjscksdn98/master-java-unit-testing/new/master?readme=1#-verify-method-calls)
- [Testing Controllers](https://github.com/dnjscksdn98/master-java-unit-testing/new/master?readme=1#-testing-controllers)
- [Controller Unit Tests with Service](https://github.com/dnjscksdn98/master-java-unit-testing/new/master?readme=1#-controller-unit-tests-with-service)
- [Integration Test using @SpringBootTest](https://github.com/dnjscksdn98/master-java-unit-testing/new/master?readme=1#-integration-test-using-springboottest)


## 🚀 Mock이란?

실제 객체를 만들기에는 비용과 시간이 많이 들거나 의존성이 크게 걸쳐져 있어서 테스트 시 제대로 구현하기 어려울 경우 가짜 객체를 만들어서 사용하는 기술입니다.

### ✔ Mock을 통해 유닛 테스트하기

**비즈니스 로직 클래스**

```java
public class SimpleService {

    private SimpleDataRepository simpleDataRepository;

    public void setSimpleDataRepository(SimpleDataRepository simpleDataRepository) {
        this.simpleDataRepository = simpleDataRepository;
    }

    public int calculateSumUsingDataService() {
        int sum = 0;
        int[] data = simpleDataRepository.findAll();
        for(int value: data) {
            sum += value;
        }
        return sum;
    }
}
```

**리포지토리 인터페이스**

```java
public interface SimpleDataRepository {

    int[] findAll();
}
```

**Test Stub를 통해서 테스트**

```java
class SimpleDataRepositoryStub implements SimpleDataRepository {

    @Override
    public int[] findAll() {
        return new int[] {1, 2, 3};
    }
}

public class SimpleServiceStubTests {

    @Test
    public void calculateSumUsingDataService_basic() {
        SimpleService simpleService = new SimpleService();
        simpleService.setSimpleDataRepository(new SimpleDataRepositoryStub());

        int actualResult = simpleService.calculateSumUsingDataService();
        int expectedResult = 6;
        assertEquals(expectedResult, actualResult);
    }
}
```

여기서 Test Stub이란, 필요한 인터페이스에 대한 구현 객체로 마치 실제로 동작하는 것처럼 보이게 만들어 놓은 객체입니다. 객체의 특정 상태를 가정해서 만들어 특정 값을 리턴해 주거나 특정 메시지를 출력해 주는 작업을 주로 하게 됩니다. 그러나 특정 상태를 가정해서 Hard Coding 된 형태이기 때문에 로직에 따른 값의 변경은 테스트 할 수 없습니다.

**Mock Object를 통해서 테스트**

```java
public class SimpleServiceMockTests {

    @Test
    public void calculateSumUsingDataService_basic() {
        SimpleService simpleService = new SimpleService();

        // 1. Create mock
        SimpleDataRepository simpleDataRepositoryMock = mock(SimpleDataRepository.class);

        // 2. Specify mock -> when -> then
        when(simpleDataRepositoryMock.findAll()).thenReturn(new int[] {1, 2, 3});

        simpleService.setSimpleDataRepository(simpleDataRepositoryMock);
        int actualResult = simpleService.calculateSumUsingDataService();
        int expectedResult = 6;
        assertEquals(expectedResult, actualResult);
    }
}
```

여기서 ```Mock Object```란, 테스트하고자 하는 코드에서 실제로 구현하기 어려운 객체들을 대신하여 동작하기 위해 만들어진 객체입니다. 테스트 시 Mock Object의 미리 정의된 결과를 통해서 테스트를 수월하게 진행할 수 있게 됩니다.

## 🚀 @Mock 을 사용해서 Mock Object 생성

기존에는 ```SimpleDataRepository simpleDataRepositoryMock = mock(SimpleDataRepository.class);``` 이런 식으로 ```mock()``` 함수를 통해서 매번 Mock Object를 생성해 주면서 테스트를 해줬지만 이 부분을 ```@Mock``` 어노테이션을 통해서 코드량을 줄여줄 수 있습니다.

```java
@ExtendWith(MockitoExtension.class)
public class SimpleServiceMockTests {

    @InjectMocks
    SimpleService simpleService;

    @Mock
    SimpleDataRepository simpleDataRepository;
    ...
}
```

- ```@Mock``` 은 해당 클래스에 대한 Mock Object를 생성해줍니다.
- ```@ExtendWith(MockitoExtension.class)``` 은 Mock 테스트가 필요한 경우 써야하는 어노테이션입니다.
- 또는 다음과 같이 함수를 작성해 줘도 됩니다.
```java
@BeforeEach
public void init() {
    MockitoAnnotations.initMocks(this);
}
```

- ```@InjectMocks``` 는 ```@Mock``` 이 붙은 객체를 주입시키는 어노테이션입니다.

## 🚀 Return Multiple Values

보통은 ```when()``` 과 ```thenReturn()``` 을 사용할 때  

```java
private final List<String> mock = mock(List.class);

@Test
public void returnSingleValue() {
    when(mock.size()).thenReturn(5);
    assertEquals(5, mock.size());
}
```

이런 식으로 하나의 값만을 리턴하는 형태로 Mock Object 를 사용하지만, 연속적으로 리턴값을 확인을 할 때는 다음과 같이 ```thenReturn()``` 을 필요한 만큼 더 붙이면 됩니다.

```java
@Test
public void returnMultipleValues() {
    when(mock.size()).thenReturn(5).thenReturn(10);
    assertEquals(5, mock.size());
    assertEquals(10, mock.size());
}
```

## 🚀 Argument Matchers

다음으로는 해당 Mock Object에 파라미터와 함께 메소드를 사용할 때에는 다음과 같이 작성하시면 되는데, 만약 정해놓은 파라미터를 사용하지 않고 Mock Object를 사용하게 되면 전달한 값에 해당하는 디폴트값이 반환됩니다.

```java
@Test
public void returnWithParameters() {
    when(mock.get(0)).thenReturn("in28minutes");
    assertEquals("in28minutes", mock.get(0));
    assertNull(mock.get(1));
}
```

그리고 위와 같이 값을 하드 코딩하지 않고 동적으로 파라미터 값을 설정하고 싶으시다면, Mockito의 ```Argument Matchers``` 클래스를 사용하시면 됩니다.

```java
@Test
public void returnWithGenericParameters() {
    when(mock.get(anyInt())).thenReturn("in28minutes");
    assertEquals("in28minutes", mock.get(0));
    assertEquals("in28minutes", mock.get(1));
}
```

## 🚀 Verify Method Calls

조금 더 견고하고 정확한 테스트를 진행하기 위해서 가끔은 해당 테스트 안에서 특정 메소드를 호출했는지에 대해서 검증을 할 필요가 있습니다. 이를 위해 Mockito 에서는 ```verify()``` 라는 함수를 지원해줍니다.

```java
private List<String> mock = mock(List.class);

@Test
public void verifyMethod_basic() {
    String value = mock.get(0);
    String value2 = mock.get(1);

    verify(mock).get(0);
    verify(mock, times(2)).get(anyInt());
    verify(mock, atLeast(1)).get(anyInt());
    verify(mock, atLeastOnce()).get(anyInt());
    verify(mock, atMost(2)).get(anyInt());
    verify(mock, never()).get(2);
}
```

- ```verify(mock).method(param);```
  - 해당 Mock Object 의 메소드를 호출했는지 검증
  
- ```verify(mock, times(wantedNumberOfInvocations)).method(param);```
  - 해당 Mock Object 의 메소드가 정해진 횟수만큼 호출됬는지 검증

- ```verify(mock, atLeast(minNumberOfInvocations)).method(param);```
  - 해당 Mock Object 의 메소드가 최소 정해진 횟수만큼 호출됬는지 검증
  
- ```verify(mock, atLeastOnce()).method(param);```
  - 해당 Mock Object 의 메소드가 최소 한번 호출됬는지 검증
  
- ```verify(mock, atMost(maxNumberOfInvocations)).method(param);```
  - 해당 Mock Object 의 메소드가 정해진 횟수보다 적게 호출됬는지 검증
  
- ```verify(mock, never()).method(param);```
  - 해당 Mock Object 의 메소드가 호출이 안됬는지 검증
  
## ✓ Argument Capture

필요에 따라서 가끔은 검증이 필요한 메소드에 전달된 값까지 확인할 필요가 있습니다. 이럴 때에는 Mockito의 ```ArgumentCaptor``` 클래스를 사용하면 됩니다. 

```java
@Test
public void argumentCapture() {
    mock.add("Test String");

    // Argument Capture Verification
    ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
    verify(mock).add(captor.capture());

    assertEquals("Test String", captor.getValue());
}
```

**Multiple Argument Capture**

만약 여러 파라미터 값들을 검증할 필요가 있을 때에는, ```getAllValues()``` 함수를 사용하면 됩니다.

```java
@Test
public void argumentCapture() {
    mock.add("Test String 1");
    mock.add("Test String 2");

    // Argument Capture Verification
    ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
    verify(mock, times(2)).add(captor.capture());

    List<String> allValues = captor.getAllValues();
    assertEquals("Test String 1", allValues.get(0));
    assertEquals("Test String 2", allValues.get(1));
}
```

## 🚀 Testing Controllers

유닛 테스트를 할 때 컨트롤러 단만 테스트 해야하는 경우가 있습니다. 이럴 때에는 스프링 부트에서 제공해주는 ```@WebMvcTest()``` 와 ```MockMvc``` 를 사용하면 됩니다.

- ```@WebMvcTest([ControllerName.class])```
  - 이 어노테이션을 클래스 상단부에 붙여주면 MVC 관련 이외의 설정들을 비활성화 시켜줌으로써 MVC 테스트를 실행시킬 수 있도록 해줍니다.
  
- ```MockMvc```
  - 스프링 MVC 테스트시 서버 사이드 엔트리 객체입니다.
  
```java
@WebMvcTest(HelloWorldController.class)
class HelloWorldControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void helloWorld_basic() throws Exception{
        // create request
        RequestBuilder request = MockMvcRequestBuilders
                .get("/hello-world")
                .accept(MediaType.APPLICATION_JSON);

        // call "/hello-world"
        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"))
                .andReturn();

        // verify
        // assertEquals("Hello World", result.getResponse().getContentAsString());
    }
}
```

먼저 ```RequestBuilder``` 로 Request를 생성해주고 ```MockMvc``` 를 통해서 REST API 테스트를 진행합니다. 이 과정에서 만약 응답이 비교적 간단하면 ```andExpect(ResultMatchers)``` 를 통해서 검증을 해주고, 비교적 복잡하다면 ```Assertion```을 통해서 검증을 합니다.

### ✓ JSON Assertions

만약 Response 의 JSON 객체의 내용을 검증할 경우에는 ```json()``` 함수를 사용하시면 됩니다.

```java
MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"Mac Book Pro\",\"price\":10,\"quantity\":100}"))
                .andReturn();
```

그러나 이 방법을 사용하게 된다면 전달된 키값들만 비교하게 되서 그 외의 값들은 같은지 비교를 하지 않습니다. 그래서 예상하는 JSON 객체의 키값들과 실제 반환되는 JSON 객체의 모든 키값들이 같은지 비교하기 위해서는 ```JSONAssert``` 라이브러리를 사용하면 됩니다.

```java
MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andReturn();

String actualResponse = "{\"id\":1,\"name\":\"Mac Book Pro\",\"price\":10,\"quantity\":100}";
// JSONAssert.assertEquals(expected, actual, strict);
JSONAssert.assertEquals(result.getResponse().getContentAsString(), actualResponse, true);
```

이때 마지막 ```strict``` 파라미터를 ```true``` 로 설정해줘야 합니다. ```false``` 로 설정하게 되면 위의 방법과 동일하게 전달된 키값들만 비교하게 됩니다.

## 🚀 Controller Unit Tests with Service

만약 테스트할 컨트롤러가 의존성이 있는 서비스 빈이 있다면 스프링 부트에서 제공해주는 ```@MockBean``` 어노테이션을 통해서 해당 빈을 주입시키고 Mock Object로 생성해줍니다. 

그다음에 똑같이 해당 Mock Object 가 가질 행동(Behavior)을 지정해주면 됩니다.

```java
@WebMvcTest(ItemController.class)
class ItemControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;
    
    @Test
    public void getItem_fromBusinessService() throws Exception {

        // mock the behavior for the service
        when(itemService.findItem(anyLong()))
                .thenReturn(new Item(1L, "Mac Book Pro", 10, 100));

        RequestBuilder request = MockMvcRequestBuilders
                .get("/service/items/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andReturn();

        String actualResponse = "{\"id\":1,\"name\":\"Mac Book Pro\",\"price\":10,\"quantity\":100}";
        JSONAssert.assertEquals(result.getResponse().getContentAsString(), actualResponse, true);
    }
}
```

## 🚀 Integration Test using @SpringBootTest

해당 클래스가 속한 패키지를 탐색해서 ```@SpringBootApplication``` 어노테이션이 붙은 클래스를 탐색하고 실행시킵니다. 만약 없다면 한 패키지씩 줄여가면서 탐색을 이어나갑니다. 해당 애플리케이션을 실행 시켜주므로 모든 컴포넌트(Bean)들도 실행됩니다.

인-메모리 데이터베이스를 사용해서 모델들을 생성해줍니다.

기존의 Mock Testing은 내장 톰켓 서버를 구동하지 않은체로 테스트를 진행했지만 ```@SpringBootTest``` 를 사용하게 되면 실제 가용한 포트로 내장톰캣을 띄우고 응답을 받아서 테스트를 수행하게 됩니다.

이때 ```MockMvc``` 대신 ```TestRestTemplate``` 을 사용하게 됩니다.

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void contextLoads() throws JSONException {
        String response = this.testRestTemplate.getForObject("/items", String.class);

        JSONAssert.assertEquals("[{id:10001},{id:10002},{id:10003},{id:10004}]", response, false);
    }
}
```

```webEnvironment = StringBootTest.WebEnvironment.RANDOM_PORT```
- 현재 호스트가 사용하지 않는 포트를 랜덤하게 사용합니다.

```testRestTemplate.getForObject(url, return type)```
- 해당 URI 로 GET 요청을 보냅니다.

> 만약 의존성이 있는 특정 컴포넌트(예: Service, Repository)를 Mocking 하고 싶다면(테스트가 너무 커질수 있으므로) ```@MockBean``` 어노테이션을 사용해서 Mock Object를 생성해주면 됩니다.
