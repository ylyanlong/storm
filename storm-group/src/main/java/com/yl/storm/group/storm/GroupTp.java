package com.yl.storm.group.storm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import com.yl.storm.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storm.kafka.*;


/**
 * Created by Administrator on 2015/11/1.
 */
public class GroupTp {
    private static final Logger LOG = LoggerFactory.getLogger(GroupTp.class);

    private static final String KAFKA_SPOUT_Id = "KafkaSpout";
    private static final String USER_ADD_BOLT_ID = "userAddBolt";
    // private static final String REPORT_BOLT = "reportBolt";
    private static final String GROUP_FIELD = "uid";


    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
        LOG.info("args len={}", args.length);
        if((args.length != 0) && (args.length != 1) ){
            LOG.error("args.length != 0 and args.length != 1 then return");
            return;
        }

        //-------src-config-begin---------
        String TOPIC = "ylkafkat1";
        String SPOUTID = "stormGroupSp1";
        //--------
        String ZKROOT = "/ylzktest/stormGroup";
        //-------src-config-end---------

        BrokerHosts brokerHosts = new ZkHosts("node1:2181,node2:2181,node3:2181");
        TopologyBuilder builder = new TopologyBuilder();
        Config config = new Config();
        // config.setNumWorkers(3);

        if(args.length == 0){
            String TOPOLOGY_NAME = "groupAnalyze";
            LocalCluster cluster = new LocalCluster();

            SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, TOPIC, ZKROOT, SPOUTID);
            // spoutConfig.scheme = new SchemeAsMultiScheme(new PcuMsgScheme());
            spoutConfig.scheme = new SchemeAsMultiScheme(new UserMsgScheme());
            KafkaSpout kafkaSpout = new KafkaSpout(spoutConfig);

            //-------------------------Shuffle-grouping-begin-------------------------------------------------
            /*builder.setSpout(KAFKA_SPOUT_Id, kafkaSpout);
            builder.setBolt(USER_ADD_BOLT_ID, new AddUserBolt()).setNumTasks(3).shuffleGrouping(KAFKA_SPOUT_Id);*/
            //-------------------------Shuffle-grouping-end---------------------------------------------------

            //-------------------------Shuffle-grouping-begin-------------------------------------------------
            /*builder.setSpout(KAFKA_SPOUT_Id, kafkaSpout);
            builder.setBolt(USER_ADD_BOLT_ID, new AddUserBolt()).setNumTasks(3).fieldsGrouping(KAFKA_SPOUT_Id, new Fields(GROUP_FIELD) );*/
            //-------------------------Shuffle-grouping-end---------------------------------------------------

            //-------------------------All-grouping-begin-------------------------------------------------
            /*builder.setSpout(KAFKA_SPOUT_Id, kafkaSpout);
            builder.setBolt(USER_ADD_BOLT_ID, new AddUserBolt()).setNumTasks(3).allGrouping(KAFKA_SPOUT_Id);*/
            //-------------------------All-grouping-end---------------------------------------------------

            //------------------------Local-Shuffle-grouping-begin-------------------------------------------------
            /*builder.setSpout(KAFKA_SPOUT_Id, kafkaSpout);
            builder.setBolt(USER_ADD_BOLT_ID, new AddUserBolt()).setNumTasks(3).localOrShuffleGrouping(KAFKA_SPOUT_Id);*/
            //------------------------Local-Shuffle-grouping-end---------------------------------------------------


            //-------------------------All-grouping-begin-------------------------------------------------
            builder.setSpout(KAFKA_SPOUT_Id, kafkaSpout);
            builder.setBolt(USER_ADD_BOLT_ID, new AddUserBolt()).setNumTasks(3).globalGrouping(KAFKA_SPOUT_Id);
            //-------------------------All-grouping-end---------------------------------------------------

            cluster.submitTopology(TOPOLOGY_NAME, config, builder.createTopology());
            ThreadUtil.waitForSeconds(180);
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

            /*builder.setSpout(KAFKA_SPOUT_Id, kafkaSpout);
            builder.setBolt(PARTITION_BOLT_ID, new PartitionBolt(), 2).setNumTasks(4).shuffleGrouping(KAFKA_SPOUT_Id);*/
            // builder.setBolt(REPORT_BOLT, new ReportBolt()).globalGrouping(KAFKA_SPOUT_Id);
            //----------------------------------------------------------

            LOG.info("going to create topo");
            StormSubmitter.submitTopology(topoName, config, builder.createTopology());
        } else {
            LOG.error("args.length={}", args.length);
        }

    }
}
