# devent-spring-boot-starter
Dotions event manager spring boot starter.


## Maven 依赖
```
<dependency>
    <groupId>com.dotions</groupId>
    <artifactId>devent-spring-boot-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

## 定义事件（Event Type）
```
public interface EventType {
	int REQUEST = 1;    // 此处以接口请求事件为例（请自己业务需要定义）
}
```

## 定义监听器（Event Listener）
定义一个listener 需要两步：

* 实现 *com.dotions.event.Listener* 接口；

* 声明 **@EventListener** 注解，其中 value / eventType 为监听的事件类型，order 为 listener 的执行顺序（可省略）；


如下所示：
```
import com.dotions.event.Event;
import com.dotions.event.Listener;
import com.dotions.event.annotation.EventListener;

// @EventListener(EventType.REQUEST) // order 可省略
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
// 引入事件服务（devent-spring-boot-starter 中已实例化默认实现，可以直接使用）
@Autowired
IEventService eventService;

// 通过构造函数的方式初始化事件
// Event e = new Event(121231231L, EventType.REQUEST);

Event e = new Event();
// 设置要传递给 listener 的参数，可以是任意类型数据
e.setSource(121231231L);
// 设置事件的类型
e.setType(EventType.REQUEST);

// 在业务需要的地方触发事件
eventService.fire(e);
```

---

## 配置 application.properties 或 application.yml（可省略）
```
// 处理事件的线程池大小，默认值：5
dotions.event.threadPoolSize=5

// 处理事件的线程前缀，默认值：devent
dotions.event.threadNamePrefix=devent
```