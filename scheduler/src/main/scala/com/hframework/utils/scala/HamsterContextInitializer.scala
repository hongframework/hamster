package com.hframework.utils.scala

import org.springframework.beans.factory.config.AutowireCapableBeanFactory
import org.springframework.beans.factory.support.DefaultListableBeanFactory
import org.springframework.context.support.ClassPathXmlApplicationContext

object HamsterContextInitializer extends Logging{

  logger.info("#########################################################################")
  logger.info("loading hamster context ..")
  logger.info("#########################################################################")
  val context: ClassPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring/spring.xml") {
    @Override override def customizeBeanFactory(beanFactory : DefaultListableBeanFactory) {
      super.customizeBeanFactory(beanFactory)
      //TODO 启动报错，临时先注掉
      //beanFactory.setAllowBeanDefinitionOverriding(false);
    }
  }
  logger.info("#########################################################################")
  logger.info("load hamster context ok ..")
  logger.info("#########################################################################")

//  def getHamsterController: Nothing = context.getBean(classOf[Nothing])

  def getBean[T](beanName: String): T = context.getBean(beanName).asInstanceOf[T]

  def getBean[T](clazz: java.lang.Class[T]): T = context.getBean(clazz)

  def autowire(bean: Any): Unit = {
    context.getAutowireCapableBeanFactory.autowireBeanProperties(bean, AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, false)
  }

}
