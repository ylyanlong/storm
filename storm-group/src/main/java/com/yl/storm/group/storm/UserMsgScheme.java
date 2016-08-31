package com.yl.storm.group.storm;

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
 * 示例: uid=123&sid=456&name=tom
 *
 */
public class UserMsgScheme implements Scheme {
    private static final Logger LOG = LoggerFactory.getLogger(UserMsgScheme.class);
    @Override
    public List<Object> deserialize(byte[] bytes) {

        String msg = null;
        try {
            msg = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error("UnsupportedEncodingException: {}", e.getMessage());
        }

        Map<String, String> inputMap = getUserReportDataMap(msg);
        // 处理解析失败的情况
        if(inputMap.isEmpty()){
            return null;
        }

        long uid = 0;
        if (inputMap.get("uid") != null) {
            try {
                uid = Long.parseLong(inputMap.get("uid"));
            } catch (NumberFormatException e) {
                LOG.debug("uid={} NumberFormatException msg={}", inputMap.get("uid"), msg);
            }
        } else {
            LOG.debug("uid is null, input: {}", msg);
            return null;
        }

        long sid = 0;
        if (inputMap.get("sid") != null) {
            try {
                sid = Long.parseLong(inputMap.get("sid"));
            } catch (NumberFormatException e) {
                LOG.debug("sid={} NumberFormatException msg={}", inputMap.get("sid"), msg);
            }
        } else {
            LOG.debug("sid is null, input: {}", msg);
            return null;
        }

        String name = null;
        if (inputMap.get("name") != null) {
            name = inputMap.get("name");
        } else {
            LOG.debug("name is null, input: {}", msg);
            // return null;  // 非必须字段
        }

        return new Values(uid, sid, name);
    }

    @Override
    public Fields getOutputFields() {
        return new Fields("uid", "sid", "name");
    }

    private static Map<String, String> getUserReportDataMap(String inputStr) {
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
