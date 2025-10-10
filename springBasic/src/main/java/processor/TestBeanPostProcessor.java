package processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class TestBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		// TODO Auto-generated method stub
		if (beanName.equals("t4")) { // 일단 지정해놓으면 모든 빈이 생성될때 다 적용되기 떄문에, beanName으로 특정이름만 먹도록 할 수
									 // 있음.
			System.out.println("t4 before");
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		// TODO Auto-generated method stub
		if (beanName.equals("t4")) {
			System.out.println("t4 after");
		}
		return bean;
	}
}
