package com.hframe.hamster.node.task.common;

/**
 * Created by zhangquanhong on 2016/10/1.
 * 过程有序：如写数据库要求必须严格有序
 * 结果有序：只需要保证每个process处理结果有序
 *
 * 假定：一个流程任务中有5个原子任务组成，即：1,2,3,4,5
 * CASE1: 五任务完成无序(None)，当前任务在执行时，只要process可执行就直接执行，无需参考前面是否有process未执行完成
 * p1: 1 -> 2 -> 3  -> 4     -> 5
 * p2:  1  -> 2 -> 3 -> 4 -> 5
 * p3:   1-> 2 -> 3   -> 4     -> 5
 * p4:    1 -> 2 -> 3  -> 4 -> 5
 *
 * CASE2: 结果有序(RESULT)，
 * p1: 1 -> 2 -> 3 -> 4 -> 5
 * p2:  1 -> 2 -> 3 -> 4 -> 5
 * p3:   1 -> 2 -> 3 -> 4 -> 5
 * p4:    1 -> 2 -> 3 -> 4 -> 5
 *
 * CASE3: 过程有序(PROCESS) 当前任务在执行时，及时有一批process可以处理，需要再执行process前面的process都已经处理完毕
 * p1: 1 -> 2 -> 3 -> 4 -> 5
 * p2:      1 -> 2 -> 3 -> 4 -> 5
 * p3:           1 -> 2 -> 3 -> 4 -> 5
 * p4:                1 -> 2 -> 3 -> 4 -> 5
 * 应用1：如果数据的执行不需要将就顺序，比如统计人总数，金额总数，那么直接完全无序即可
 * 应用2：如果数据的执行过程中需要严格的顺序，比如数据库的增删改，需要严格有序
 * 应用3：一个任务流程中某些原子任务无状态，有些原子任务需要严格顺序，各原子任务按照自己的需求执行即可
 *
 * otter的实现可以设置为：S,E,T都为完全无需执行（无状态），L为严格有序执行，实际上otter目前都为严格有序执行
 */
public enum TaskOrderMode {
    /**无*/
    NONE,
    /**过程有序*/
    PROCESS,
    /**结果有序*/
    RESULT;

    public boolean isNone(){
        return this.equals(NONE);
    }

    public boolean isProcess(){
        return this.equals(PROCESS);
    }

    public boolean iResult(){
        return this.equals(RESULT);
    }
}
