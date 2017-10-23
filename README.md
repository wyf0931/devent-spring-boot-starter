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

## 配置 application.properties 或 application.yml
```
// 处理事件的线程池大小，默认值：5
dotions.event.threadPoolSize=5

// 处理事件的线程前缀，默认值：devent
dotions.event.threadNamePrefix=devent
```

## 定义 Event Type
```
public interface EventType {
	int REQUEST = 1;    // 接口请求事件
}
```

## 定义 Listener
定义一个listener 需要两步：
    * 实现 *com.dotions.event.Listener* 接口
    * 声明 **@EventListener** 注解，其中 order 属性为执行 listener 顺序（可省略），value 为 eventType。
```
import com.dotions.event.Event;
import com.dotions.event.Listener;
import com.dotions.event.annotation.EventListener;

@EventListener(value=EventType.REQUEST, order=1)
public class RequestListener implements Listener {
	@Override
	public void onEvent(Event e) {
		String uid = e.getSource();
		System.out.println("[request] -----> uid=" + uid);
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
e.setSource("121231231231");
e.setType(EventType.REQUEST);
eventService.fire(e);
```
