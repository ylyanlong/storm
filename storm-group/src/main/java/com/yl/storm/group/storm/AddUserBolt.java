package com.yl.storm.group.storm;

import backtype.storm.Config;
import backtype.storm.Constants;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2016/8/26.
 */
public class AddUserBolt extends BaseRichBolt {
    private static final Logger LOG = LoggerFactory.getLogger(AddUserBolt.class);
    private OutputCollector collector;
    private TopologyContext context;


    public AddUserBolt(){

    }

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.context = context;
        this.collector = collector;
    }

    @Override
    public void execute(Tuple tuple) {
        String ComponentId = context.getThisComponentId();
        int taskid = context.getThisTaskId();
        // int taskIndex = context.getThisTaskIndex();
        // String stormid = context.getStormId();

       /* LOG.info("PartitionBolt ComponentId:{}, taskid:{}, taskIndex:{}, stormid:{}, tuple:{}",
                ComponentId, taskid, taskIndex, stormid, tuple.toString() );*/

        LOG.info("PartitionBolt ComponentId:{}, taskid:{}, tuple:{}",
                ComponentId, taskid, tuple.toString() );


        this.collector.ack(tuple);  // 是否可以注释掉此处?
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
