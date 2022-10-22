package hello.core.singleton;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {
    @Test
    void statefulServiceSingleton(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // ThreadA : A user, order 10000won
        int userAPrice = statefulService1.order("userA", 10000);
        // ThreadB : B user, order 10000won
        int userBPrice = statefulService2.order("userB", 20000);

        // ThreadA : select userA order price
        int price = statefulService1.getPrice();
        // ThreadA: userA 는 10000원 기대햇으나, 20000원 출력
        //System.out.println("price = " + price);
        System.out.println("price = " + userAPrice);

//      assertThat(statefulService1.getPrice()).isEqualTo(10000);


    }
    static class TestConfig{

        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }

    }


}