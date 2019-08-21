package com.hframe.hamster.node.arbitrate;

import com.google.common.base.Function;
import com.google.common.collect.MapMaker;
import com.hframe.hamster.node.HamsterContextInitializer;
import com.hframe.hamster.node.arbitrate.interfaces.FlowEndNodeArbitrateEvent;
import com.hframe.hamster.node.arbitrate.interfaces.FlowMiddleNodeArbitrateEvent;
import com.hframe.hamster.node.arbitrate.interfaces.FlowStartNodeArbitrateEvent;
import com.hframe.hamster.node.monitor.MainStemMonitor;
import com.hframe.hamster.node.monitor.Monitor;
import com.hframe.hamster.node.monitor.PrototypeMonitor;
import com.hframe.hamster.node.monitor.bean.PipeLinePrototypeKey;
import com.hframe.hamster.node.monitor.bean.PrototypeKey;
import com.hframe.hamster.node.monitor.listener.FlowEndNodeProcessListener;
import com.hframe.hamster.node.monitor.listener.FlowMiddleNodeProcessListener;
import com.hframe.hamster.node.monitor.listener.FlowStartNodeProcessListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

/**
 * Created by zhangquanhong on 2016/9/26.
 */
public class ArbitratorFactory implements ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(ArbitratorFactory.class);

    private static ApplicationContext applicationContext;

    // taskStory - taskInstance - taskNode - eventType -eventObject
    private static Map<String, Map<PrototypeKey, Map<Class, Map<Class, Object>>>> cache =
            new MapMaker().makeComputingMap(new Function<String, Map<PrototypeKey, Map<Class, Map<Class, Object>>>>() {
                @Override
                public Map<PrototypeKey, Map<Class, Map<Class, Object>>> apply(final String taskStory) {
                    return new MapMaker().makeComputingMap(new Function<PrototypeKey, Map<Class, Map<Class, Object>>>() {
                        @Override
                        public Map<Class, Map<Class, Object>> apply(final PrototypeKey prototypeKey) {
                            return new MapMaker().makeComputingMap(new Function<Class, Map<Class, Object>>() {
                                @Override
                                public Map<Class, Object> apply(final Class taskNodeClass) {
                                    return new MapMaker().makeComputingMap(new Function<Class, Object>() {
                                        @Override
                                        public Object apply(Class eventTypeClass) {
                                            return newInstance(taskStory, prototypeKey, taskNodeClass, eventTypeClass);
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            });


    private static Object newInstance(String taskStory, PrototypeKey taskInstance, Class taskNodeClass, Class eventTypeClass) {
        try {
            logger.info("new instance {}, {}, {}, {}", taskStory, taskInstance.value(), taskNodeClass,eventTypeClass);
            Object object = null;
            if(FlowStartNodeArbitrateEvent.class.isAssignableFrom(eventTypeClass)
                    || FlowMiddleNodeArbitrateEvent.class.isAssignableFrom(eventTypeClass)
                    || FlowEndNodeArbitrateEvent.class.isAssignableFrom(eventTypeClass)
                    || FlowMiddleNodeProcessListener.class.isAssignableFrom(eventTypeClass)
                    || FlowStartNodeProcessListener.class.isAssignableFrom(eventTypeClass)
                    || FlowEndNodeProcessListener.class.isAssignableFrom(eventTypeClass)) {
                Constructor constructor = eventTypeClass.getConstructor(new Class[]{String.class, PrototypeKey.class, Class.class});
                object = constructor.newInstance(taskStory, taskInstance, taskNodeClass);
            }else if(PrototypeMonitor.class.isAssignableFrom(eventTypeClass)) {
                Constructor constructor = eventTypeClass.getConstructor(new Class[]{String.class, PrototypeKey.class});
                object = constructor.newInstance(taskStory, taskInstance);
            }else if(TaskProcessArbitrateEvent.class.isAssignableFrom(eventTypeClass)) {
                Constructor constructor = eventTypeClass.getConstructor();
                object = constructor.newInstance();
            }else{
                Constructor constructor = eventTypeClass.getConstructor(new Class[]{PrototypeKey.class});
                object = constructor.newInstance(taskInstance);
            }
            autowire(object);
            return object;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static  void autowire(Object object) {
        HamsterContextInitializer.autowire(object);
//        applicationContext.getAutowireCapableBeanFactory().autowireBeanProperties(object, AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, true);
    }

    /**
     * prototype-flow-task 获取Event实例
     * @param taskStory
     * @param prototypeKey
     * @param taskNodeClazz
     * @param <T>
     * @return
     */
    public static  <T> T getInstance(String taskStory, PrototypeKey prototypeKey, Class taskNodeClazz, Class taskEventClazz) {
        return (T) cache.get(taskStory).get(prototypeKey).get(taskNodeClazz).get(taskEventClazz);
    }

    /**
     * prototype-flow-task 获取Event实例
     * @param taskStory
     * @param prototypeKey
     * @param taskNodeClazz
     * @param <T>
     * @return
     */
    public static  <T> T remove(String taskStory, PrototypeKey prototypeKey, Class taskNodeClazz, Class taskEventClazz) {
        return (T) cache.get(taskStory).get(prototypeKey).get(taskNodeClazz).remove(taskEventClazz);
    }

    /**
     * prototype-alone-task 获取Event实例
     * @param prototypeKey
     * @param taskNodeClazz
     * @param <T>
     * @return
     */
    public static  <T> T getInstance(PrototypeKey prototypeKey, Class taskNodeClazz, Class taskEventClazz) {
        return (T) cache.get(taskNodeClazz.getSimpleName()).get(prototypeKey).get(taskNodeClazz).get(taskEventClazz);
    }

    /**
     * singleton-flow-task 获取Event实例
     * @param taskStory
     * @param taskNodeClazz
     * @param <T>
     * @return
     */
    public static  <T> T getInstance(String taskStory, Class taskNodeClazz, Class taskEventClazz) {
        return (T) cache.get(taskStory).get(null).get(taskNodeClazz).get(taskEventClazz);
    }

    /**
     * singleton-alone-task 获取Event实例
     * @param taskNodeClazz
     * @param <T>
     * @return
     */
    public static  <T> T getInstance(Class taskNodeClazz, Class taskEventClazz) {
        return (T) cache.get(taskNodeClazz.getSimpleName()).get(null).get(taskNodeClazz).get(taskEventClazz);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static void destroy(String taskStory, PrototypeKey prototypeKey, Class taskNodeClazz){
        Map<Class, Object> eventMap = cache.get(taskStory).get(prototypeKey).remove(taskNodeClazz);
        if(eventMap != null) {
            Collection<Object> events = eventMap.values();
            for (Object event : events) {
                if (event instanceof Monitor) {
                    Monitor monitor = (Monitor) event;
                    monitor.destroy();
                }
            }
        }
    }

    public static void destroy(String taskStory, PrototypeKey prototypeKey){
        Map<Class, Map<Class, Object>> map = cache.get(taskStory).remove(prototypeKey);
        if(map != null) {
            Collection<Map<Class, Object>> values = map.values();
            for (Map<Class, Object> eventMap : values) {
                Collection<Object> events = eventMap.values();
                for (Object event : events) {
                    if (event instanceof Monitor) {
                        Monitor monitor = (Monitor) event;
                        monitor.destroy();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        ArbitratorFactory.getInstance("story1", PipeLinePrototypeKey.value(1000L), Long.class, MainStemMonitor.class);
        ArbitratorFactory.getInstance("story1", PipeLinePrototypeKey.value(1000L), Integer.class, MainStemMonitor.class);
        ArbitratorFactory.getInstance("story1", PipeLinePrototypeKey.value(1000L), String.class, MainStemMonitor.class);

        Map<PipeLinePrototypeKey, String> map = new MapMaker().makeComputingMap(new Function<PipeLinePrototypeKey, String>() {
            @Override
            public String apply(PipeLinePrototypeKey input) {
                return input.value();
            }
        });
        map.get(PipeLinePrototypeKey.value(1000L));
        map.get(PipeLinePrototypeKey.value(1000L));
        System.out.println(map.size());
    }
}
