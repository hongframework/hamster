package com.hframe.hamster.node.cannal;

import com.hframe.ext.bean.HBaseData;
import com.hframe.ext.bean.StatisticsData;
import com.hframe.ext.bean.SubscribeData;
import com.hframe.hamster.node.cannal.bean.EventData;

import java.util.List;

/**
 * Created by zhangquanhong on 2016/9/29.
 */
public class Message {

    private Long messageId;

    private List<EventData> datas;

    private List<SubscribeData> transDatas;

    private List<StatisticsData> statisticsDatas;

    private List<HBaseData> hbaseDatas;

    public Message(){
    }

    public Message(Long messageId, List<EventData> datas){
        this.messageId = messageId;
        this.datas = datas;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public List<EventData> getDatas() {
        return datas;
    }

    public void setDatas(List<EventData> datas) {
        this.datas = datas;
    }

    public List<SubscribeData> getTransDatas() {
        return transDatas;
    }

    public void setTransDatas(List<SubscribeData> transDatas) {
        this.transDatas = transDatas;
    }

    public List<StatisticsData> getStatisticsDatas() {
        return statisticsDatas;
    }

    public void setStatisticsDatas(List<StatisticsData> statisticsDatas) {
        this.statisticsDatas = statisticsDatas;
    }

    public List<HBaseData> getHbaseDatas() {
        return hbaseDatas;
    }

    public void setHbaseDatas(List<HBaseData> hbaseDatas) {
        this.hbaseDatas = hbaseDatas;
    }
}
