package com.yl.storm.task.storm;

import backtype.storm.Config;
import backtype.storm.Constants;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.util.parsing.combinator.testing.Str;

import java.util.Calendar;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2016/8/26.
 */
public class PartitionBolt extends BaseRichBolt {
    private static final Logger LOG = LoggerFactory.getLogger(PartitionBolt.class);
    private OutputCollector collector;
    private TopologyContext context;

    // private volatile boolean minRunFlg = true;
    private static volatile boolean minRunFlg = true;

    private static AtomicLong counter = new AtomicLong();

    private static AtomicLong objectid = new AtomicLong();



    private long id = 0;

    public PartitionBolt(){
        id = objectid.getAndIncrement();
        LOG.info("PartitionBolt id: {}", id);
    }

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.context = context;
        this.collector = collector;

        Set<String> componentIds = context.getComponentIds();
        for(String ele: componentIds){
            LOG.info("prepare componentId:{}", ele);
        }
    }

    @Override
    public void execute(Tuple tuple) {
        String ComponentId = context.getThisComponentId();
        int taskid = context.getThisTaskId();
        int taskIndex = context.getThisTaskIndex();
        String stormid = context.getStormId();

        LOG.info("PartitionBolt ComponentId:{}, taskid:{}, taskIndex:{}, stormid:{}",
                ComponentId, taskid, taskIndex, stormid);

        String sourceComponent = tuple.getSourceComponent();
        String sourceStreamId = tuple.getSourceStreamId();
        int sourceTask = tuple.getSourceTask();
        LOG.info("PartitionBolt, sourceComponent:{}, sourceStreamId:{}, sourceTask:{}, objectid:{}, tuple:{}",
                sourceComponent, sourceStreamId, sourceTask, id, tuple.toString() );

        //--------------------------------------
        if(tuple.getSourceComponent().equals(Constants.SYSTEM_COMPONENT_ID)
                && tuple.getSourceStreamId().equals(Constants.SYSTEM_TICK_STREAM_ID)){

            Calendar calendar = Calendar.getInstance();
            int curSeconds = calendar.get(Calendar.SECOND);

            if(40 > curSeconds){
                if(true == minRunFlg){
                    minRunFlg = false;
                    //---------todo-begin-------------
                    Long msgNum = counter.get();
                    counter.getAndSet(0);
                    LOG.info("topic counter partitionBolt thread:{}, num:{}", Thread.currentThread().getName(), msgNum);
                    // LOG.info("PartitionBolt GlobleVar num: {}", GlobleVar.getNum());
                    //---------todo-end---------------
                }
            } else {
                minRunFlg = true;
            }

        } else {
            // 正常的 tuple 处理
            counter.getAndIncrement();
            this.collector.ack(tuple);  // 是否可以注释掉此处?
        }
        //--------------------------------------

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        Config conf = new Config();
        conf.put(conf.TOPOLOGY_TICK_TUPLE_FREQ_SECS, 60);  // 60, 50
        return conf;
    }
}
