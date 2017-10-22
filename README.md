# devent-spring-boot-starter
Dotions event spring boot starter


## Maven 依赖
```
<dependency>
  <groupId>com.dotions</groupId>
  <artifactId>devent-spring-boot-starter</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```

## 声明配置 application.properties 
```
dotions.event.threadPoolSize=10
dotions.event.threadNamePrefix=test-event
```

## 定义 Event Type
```
public interface EventType {
	int STARTUP = 0;
	int STOP = 1;
	int REQUEST = 2;
}
```

## 定义 Listener
实现 *com.dotions.event.Listener* 接口并声明 **@EventListener** ，order 为执行顺序（可省略），value 为 eventType。
```
import com.dotions.event.Event;
import com.dotions.event.Listener;
import com.dotions.event.annotation.EventListener;

@EventListener(value=EventType.REQUEST, order=1)
public class RequestListener implements Listener {
	@Override
	public void onEvent(Event e) {
		System.out.println("[request] -----> e=" + e);
	}
}
```

## 触发事件
```
// 引入事件服务
@Autowired
IEventService eventService;

// 触发事件
Event e = new Event();
e.setSource(“request success.”);
e.setType(EventType.REQUEST);
eventService.fire(e);
```
