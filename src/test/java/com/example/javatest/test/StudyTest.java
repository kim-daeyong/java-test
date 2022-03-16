package com.example.javatest.test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS) //before after 도 static으로 만들 필요가 없다.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudyTest {

    int val = 1;

    @Test
    @Order(2)
    void test1() {
        System.out.println(this);
        System.out.println(val++);
    }

    @Test
    @Order(1)
    void test2() {
        System.out.println(this);
        System.out.println(val++);
    }


    @Test
    @DisplayName("테스트1")
    void create() {
        System.out.println(System.getenv("HOME"));
        String test_env = "LOCAL";
        assumeTrue("LOCAL".equalsIgnoreCase(test_env));

        assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {
            System.out.println("test");
        });

        TestStudy study = new TestStudy(1);
        assertNotNull(study);
        System.out.println("test1");
    }

    @Test
    @Tag("test2")
    void tagTest() {
        System.out.println("test2");
    }

    @RepeatedTest(10)
    void repeatTest(RepetitionInfo repetitionInfo) {
        System.out.println(repetitionInfo);
    }

    @Test
    void exceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            TestStudy study = new TestStudy(0);
            System.out.println(study.getLimit());
        });
    }

    @Test
    void durationTest() {
        assertTimeout(Duration.ofMillis(100), () -> {
            Thread.sleep(40);
        });
    }

    @DisplayName("test")
    @ParameterizedTest(name = "{index}-{displayName}")
    @ValueSource(ints = {1, 2, 3})
//    @CsvSource({"d ,e", ""})
//    @EmptySource
//    @NullSource
//    @NullAndEmptySource
    void paraTest(@ConvertWith(StudyConverter.class) TestStudy study) {
        System.out.println(study.getLimit());
    }

    static class StudyConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(TestStudy.class, targetType, "convert");
            return new TestStudy(Integer.parseInt(source.toString()));
        }
    }

    @DisplayName("test")
    @ParameterizedTest(name = "{index}-{displayName}")
    @CsvSource({"10 ,'e'" + "20, 'test'"})
    void paraTest2(Integer limit, String name) {
        TestStudy study = new TestStudy(limit, name);
        System.out.println(study.getLimit());
    }

    @DisplayName("test")
    @ParameterizedTest(name = "{index}-{displayName}")
    @CsvSource({"10 ,'e'" + "20, 'test'"})
    void paraTest3(ArgumentsAccessor argumentsAccessor) {
        TestStudy study = new TestStudy(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
        System.out.println(study.getLimit());
    }

    @DisplayName("test")
    @ParameterizedTest(name = "{index}-{displayName}")
    @CsvSource({"10 ,'e'" + "20, 'test'"})
    void paraTest4(@AggregateWith(StudyAggregator.class) TestStudy study) {
        System.out.println(study.getLimit());
    }

    static class StudyAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
            return new TestStudy(accessor.getInteger(0), accessor.getString(1));
        }
    }

    @Disabled
    @FastTest
    @Test
    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "local")
    void test3() {
        System.out.println("test3");
    }

    @BeforeAll
    static void beAll() {
        System.out.println("beall");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("afterAll");
    }

    @BeforeEach
    void beEach() {
        System.out.println("beEach");
    }
}