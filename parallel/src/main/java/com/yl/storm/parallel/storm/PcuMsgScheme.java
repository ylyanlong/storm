package com.yl.storm.parallel.storm;

import backtype.storm.spout.Scheme;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/30.
 */
public class PcuMsgScheme implements Scheme {
    private static final Logger LOG = LoggerFactory.getLogger(PcuMsgScheme.class);
    @Override
    public List<Object> deserialize(byte[] bytes) {
        // return null;
        // try {
        String msg = null;
        try {
            msg = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error("UnsupportedEncodingException: {}", e.getMessage());
        }

        Map<String, String> inputMap = getPCUReportDataMap(msg);
        // 处理解析失败的情况
        if(inputMap.isEmpty()){
            return null;
        }

        long ot = 0;
        if (inputMap.get("ot") != null) {
            String[] array = inputMap.get("ot").split("\\.");  // 1444374306.410
            try {
                ot = Long.parseLong(array[0]);
            } catch (NumberFormatException e) {
                LOG.debug("ot={} NumberFormatException msg={}", inputMap.get("ot"), msg);
            }
        } else {
            LOG.debug("ot is null, input: {}", msg);
            // return null;
        }

        long sbd = 0;
        if (inputMap.get("sbd") != null) {
            try {
                sbd = Long.parseLong(inputMap.get("sbd"));
            } catch (NumberFormatException e) {
                LOG.debug("sbd={} NumberFormatException msg={}", inputMap.get("sbd"), msg);
            }
        } else {
            LOG.debug("sbd is null, input: {}", msg);
            // return null;
        }

        long sd = 0;
        if (inputMap.get("sd") != null) {
            try {
                sd = Long.parseLong(inputMap.get("sd"));
            } catch (NumberFormatException e) {
                LOG.debug("sd={} NumberFormatException msg={}", inputMap.get("sd"), msg);
            }
        } else {
            LOG.debug("sd is null, input: {}", msg);
            return null;
        }

        int te = 0;
        if (inputMap.get("te") != null) {
            try {
                te = Integer.parseInt(inputMap.get("te"));
            } catch (NumberFormatException e) {
                LOG.debug("te={} NumberFormatException msg={}", inputMap.get("te"), msg);
            }
        } else {
            LOG.debug("te is null, input: {}", msg);
            return null;
        }

        String cv = null;
        if (inputMap.get("cv") != null) {
            cv = inputMap.get("cv");
        } else {
            LOG.debug("cv is null, input: {}", msg);
            // return null;
        }

        // long sed = 0;
        String sed = null;
        if (inputMap.get("sed") != null) {
            // sed = Long.parseLong(inputMap.get("sed"));
            sed = inputMap.get("sed");
        } else {
            LOG.debug("sed is null, input: {}", msg);
            return null;
        }

        long ud = 0;
        if ((inputMap.get("ud") != null) && (!inputMap.get("ud").equals("null") )) {
            try {
                ud = Long.parseLong(inputMap.get("ud"));
            } catch (NumberFormatException e) {
                LOG.debug("ud={} NumberFormatException msg={}", inputMap.get("ud"), msg);
            }
        } else {
            LOG.debug("ud is null, ud={}, input: {}", inputMap.get("ud"), msg);
            // return null;
        }

        long dr = 0;
        if (inputMap.get("dr") != null) {
            try {
                dr = Long.parseLong(inputMap.get("dr"));
            } catch (NumberFormatException e) {
                LOG.debug("dr={} NumberFormatException msg={}", inputMap.get("dr"), msg);
            }
        } else {
            LOG.debug("dr is null, input: {}", msg);
            return null;
        }

        long srvstamp = 0;
        if (inputMap.get("srvstamp") != null) {
            String[] array = inputMap.get("srvstamp").split("\\.");  // 1444374306.410
            try {
                srvstamp = Long.parseLong(array[0]);
            } catch (NumberFormatException e) {
                LOG.debug("srvstamp={} NumberFormatException msg={}", inputMap.get("srvstamp"), msg);
            }
        } else {
            LOG.debug("srvstamp is null, input: {}", msg);
            return null;
        }

        String wy = "";
        if (inputMap.get("wy") != null) {
            wy = inputMap.get("wy");
        } else {
            LOG.debug("wy is null, input: {}", msg);
            // return null;
        }

        String ii = "";
        if (inputMap.get("ii") != null) {
            ii = inputMap.get("ii");
        } else {
            LOG.debug("ii is null, input: {}", msg);
            // return null;
        }

        String mc = "";
        if (inputMap.get("mc") != null) {
            mc = inputMap.get("mc");
        } else {
            LOG.debug("mc is null, input: {}", msg);
            // return null;
        }

        String vr = "";
        if (inputMap.get("vr") != null) {
            vr = inputMap.get("vr");
        } else {
            LOG.debug("vr is null, input: {}", msg);
            // return null;
        }

        String ak = "";
        if (inputMap.get("ak") != null) {
            ak = inputMap.get("ak");
        } else {
            LOG.debug("ak is null, input: {}", msg);
            // return null;
        }


        /*String uri = null;
        if (inputMap.get("uri") != null) {
            // sed = Long.parseLong(inputMap.get("sed"));
            uri = inputMap.get("uri");
        } else {
            LOG.debug("uri is null, input: {}", msg);
            return null;
        }*/

        /*int bte = BgEnum.FRONT_END.getValue();
        if (inputMap.get("bte") != null) {
            try {
                bte = Integer.parseInt(inputMap.get("bte"));
            } catch (NumberFormatException e) {
                LOG.error("bte={} NumberFormatException msg={}", inputMap.get("bte"), msg);
            }
        } else {
            LOG.error("te is null, input: {}", msg);
        }*/

        return new Values(sed, te, cv, sd, sbd, ud, ot, dr, srvstamp, wy, ii, mc, vr, ak);

        /*} catch (UnsupportedEncodingException e) {
            // e.printStackTrace();
            LOG.error("UnsupportedEncodingException input: {}", bytes.toString());  // test it. byte[] vs String
            return null;
        } catch (NumberFormatException e){
            // System.out.println("error NumberFormatException");
            LOG.error("NumberFormatException msg: {}", e.getMessage() );
            return null;
        }*/
    }

    @Override
    public Fields getOutputFields() {
        return new Fields("sed", "te", "cv", "sd", "sbd", "ud", "ot", "dr", "srvstamp", "wy", "ii", "mc", "vr", "ak");
    }

    private static Map<String, String> getPCUReportDataMap(String inputStr) {
        String[] params = inputStr.split("&");
        Map<String, String> map = new HashMap<String, String>();

        try {
            for (String param : params) {
                String[] pair = param.split("=");
                map.put(pair[(pair.length - 1) / 2], pair[pair.length / 2]);
            }
        } catch (Exception e) {
            LOG.info("getPCUReportDataMap out of bound, inputStr:{}", inputStr);
            map = new HashMap<String, String>();
            return map;
        }

        return map;
    }
}
