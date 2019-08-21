import com.hframe.hamster.node.task.common.TaskData;
import com.hframework.common.util.message.JsonUtils;
import com.hframework.datacenter.hamster.workes.bean.BatchDataSet;

import java.io.IOException;

public class DataSetReader {

    public static TaskData read(String json) throws IOException {
        TaskData taskData = JsonUtils.readValue(json, TaskData.class, BatchDataSet.class);
        return taskData;
    }
}
