package com.yl.storm.task.storm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import com.yl.storm.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storm.kafka.*;


/**
 * Created by Administrator on 2015/11/1.
 */
public class TaskTp {
    private static final Logger LOG = LoggerFactory.getLogger(TaskTp.class);

    private static final String KAFKA_SPOUT_Id = "KafkaSpout";
    private static final String PARTITION_BOLT_ID = "partitionBolt";
    // private static final String REPORT_BOLT = "reportBolt";
    // private static final String GROUP_FIELD = "sd";


    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
        LOG.info("args len={}", args.length);
        if((args.length != 0) && (args.length != 1) ){
            LOG.error("args.length != 0 and args.length != 1 then return");
            return;
        }

        //-------src-config-begin---------
        String TOPIC = "ylkafkat1";
        String SPOUTID = "taskAnalyzeSp1";
        //--------
        String ZKROOT = "/ylzktest/taskAnalyze1";
        //-------src-config-end---------

        BrokerHosts brokerHosts = new ZkHosts("node1:2181,node2:2181,node3:2181");
        TopologyBuilder builder = new TopologyBuilder();
        Config config = new Config();
        // config.setNumWorkers(3);

        if(args.length == 0){
            String TOPOLOGY_NAME = "taskAnalyze";
            LocalCluster cluster = new LocalCluster();

            SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, TOPIC, ZKROOT, SPOUTID);
            // spoutConfig.scheme = new SchemeAsMultiScheme(new PcuMsgScheme());
            spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
            KafkaSpout kafkaSpout = new KafkaSpout(spoutConfig);

            builder.setSpout(KAFKA_SPOUT_Id, kafkaSpout);
            builder.setBolt(PARTITION_BOLT_ID, new PartitionBolt()).setNumTasks(3).shuffleGrouping(KAFKA_SPOUT_Id);
            // builder.setBolt(REPORT_BOLT, new ReportBolt()).globalGrouping(KAFKA_SPOUT_Id);

            cluster.submitTopology(TOPOLOGY_NAME, config, builder.createTopology());
            ThreadUtil.waitForSeconds(600);
            cluster.killTopology(TOPOLOGY_NAME);
            cluster.shutdown();
        } else if(args.length == 1) {
            final String topoName = args[0];
            LOG.info("topoName={}", topoName);

            //----------------------------------------------------------
            LOG.info("ent src pc");

            SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, TOPIC, ZKROOT, SPOUTID);
            // spoutConfig.scheme = new SchemeAsMultiScheme(new PcuMsgScheme());
            spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
            KafkaSpout kafkaSpout = new KafkaSpout(spoutConfig);

            builder.setSpout(KAFKA_SPOUT_Id, kafkaSpout);
            builder.setBolt(PARTITION_BOLT_ID, new PartitionBolt(), 2).setNumTasks(4).shuffleGrouping(KAFKA_SPOUT_Id);
            // builder.setBolt(REPORT_BOLT, new ReportBolt()).globalGrouping(KAFKA_SPOUT_Id);
            //----------------------------------------------------------

            LOG.info("going to create topo");
            StormSubmitter.submitTopology(topoName, config, builder.createTopology());
        } else {
            LOG.error("args.length={}", args.length);
        }

    }
}
