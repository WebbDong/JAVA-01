package lesson11.guava.eventbus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * EventBus 允许在组件之间进行发布-订阅式的通信，实现了观察者设计模式，但它解耦了观察者模式中订阅方和事件源之间的强依赖关系。
 * 它不是通用的发布-订阅系统，也不是用于进程间的通信。
 *
 * 在用观察者模式的地方就可以用 EventBus 进行替代
 *
 * @author Webb Dong
 * @date 2021-03-05 6:40 PM
 */
public class EventBusExample {

    @Data
    @Builder
    @ToString
    private static class Event {

        private Integer id;

        private String name;

    }

    private static class HelloEventListener {

        @Subscribe
        public void listen(Event event) {
            System.out.println("HelloEventListener.listen  event = " + event);
        }

        @Subscribe
        public void listen(Integer num) {
            System.out.println("HelloEventListener.listen  num = " + num);
        }

    }

    public static void main(String[] args) {
        EventBus eventBus = new EventBus("EventBus");
        eventBus.register(new HelloEventListener());
        eventBus.post(Event.builder()
                .id(1)
                .name("Ferrari")
                .build());
        eventBus.post(5000);
    }

}
