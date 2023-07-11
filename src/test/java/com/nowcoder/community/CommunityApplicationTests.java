package com.nowcoder.community;

import com.nowcoder.community.dao.AlphaDao;
import com.nowcoder.community.service.AlphaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class CommunityApplicationTests implements ApplicationContextAware {

	// 加入成员变量 applicationContext, 用于记住这个 Spring 容器
	private ApplicationContext applicationContext;

	@Override
	// ApplicationContext 是 HierarchicalBeanFactory、BeanFactory 的子接口，功能更强
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// 如果一个类实现了 ApplicationContextAware 接口的 setApplicationContext 方法
		// 那么 Spring 容器会检测到，且在扫描容器时会检测到 Bean 并调用它的 setApplicationContext 方法...
		// 把容器传进来
		// 所以我们需要先把容器记录暂存引用起来，即 private ApplicationContext applicationContext;...
		// 并在下面这句话中使用

		// 这样就可以在其他地方使用这个 applicationContext 了
		this.applicationContext = applicationContext;
	}

	@Test
	public void testApplicationContext() {
		// 在测试方法中使用 Spring 容器
		System.out.println(applicationContext);

		// 从 spring 容器中获取自动装配的 bean
		// 这里用到了向上转型为接口的概念，AlphaDao alphaDao = ...
		// getBean() 方法可以通过名字获取，也可以通过类型获取，写成接口类型也行
		AlphaDao alphaDao = applicationContext.getBean(AlphaDao.class);
		// 调用 alphaDao 这个 bean 的查询方法，并将结果输出
		System.out.println(alphaDao.select());

		// 给 AlphaDaoHibernateImpl 这个 bean 重新自定义好名字后，
		// 我们就可以用这个名字 alphaHibernate 强制容器返回这个 bean
		// 这里如果不指定类型，它返回的是 Object 类型的，就需要强制转型了
		alphaDao = applicationContext.getBean("alphaHibernate", AlphaDao.class);
		System.out.println(alphaDao.select());
	}

	// 测试一下 bean 的管理方式
	@Test
	public void testBeanManagement() {
		// 通过类型，让容器获取 AlphaService
		AlphaService alphaService = applicationContext.getBean(AlphaService.class);
		// 打印这个 bean，看是否存在
		System.out.println(alphaService);

		// 加上 @Scope("prototype") 后 bean 就不是单例的了
		alphaService = applicationContext.getBean(AlphaService.class);
		System.out.println(alphaService);
	}

	@Test
	public void testBeanConfig() {
		// 从容器中取 simpleDateFormat
		// 这种主动获取的方式比较笨拙，所以我们要学习依赖注入 DI
		SimpleDateFormat simpleDateFormat =
				applicationContext.getBean(SimpleDateFormat.class);
		// 使用 simpleDateFormat 来格式化当前的日期
		System.out.println(simpleDateFormat.format(new Date()));
	}

	@Autowired
	// 如果我们不希望按照默认优先级注入，@Qualifier("bean的名字")
	@Qualifier("alphaHibernate")
	// @Autowired 注解加在一个成员变量之前，表示当前的 Bean 要使用 AlphaDao
	// 即 Spring 容器要把 AlphaDao 注入给这个属性 alphaDao，这样就可以直接使用 alphaDao 这个属性了
	private AlphaDao alphaDao;

	// 同理
	@Autowired
	private AlphaService alphaService;

	// 同理
	@Autowired
	private SimpleDateFormat simpleDateFormat;

	@Test
	public void testDI() {
		// 在测试方法中直接使用前面的成员变量，看看能不能取到我们需要的 Bean
		System.out.println(alphaDao);
		System.out.println(alphaService);
		System.out.println(simpleDateFormat);
	}

}
