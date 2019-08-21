import com.hframe.hamster.node.monitor.ConfigMonitor;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        ConfigMonitor list = new ConfigMonitor<String>(5){

            @Override
            public String fetch() throws Exception {
                return null;
            }
        };
        Type type = ((ParameterizedType)list.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        System.out.println((Class)type);
    }
}
