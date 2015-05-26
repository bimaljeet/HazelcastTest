package com.beans;

import com.hazelcast.config.InMemoryXmlConfig;
import com.hazelcast.core.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: bnayar
 * Date: 11/21/13
 * Time: 11:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class HazelCastBean {

    private String confFile;
    private String topicName;
    private HazelcastInstance instance;
    private TopicMessageListener topicMessageListener;

    public String getConfFile() {
        return confFile;
    }

    public void setConfFile(String confFile) {
        this.confFile = confFile;
    }

    public TopicMessageListener getTopicMessageListener() {
        return topicMessageListener;
    }

    public void setTopicMessageListener(TopicMessageListener topicMessageListener) {
        this.topicMessageListener = topicMessageListener;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public void addListenerToTopic(String topicName, MessageListener messageListener){
        getTopic(topicName).addMessageListener(messageListener);
    }

    private ITopic getTopic(String topicName){
        return instance.getTopic(topicName);
    }

    /**
     * Publish a message to the listening Topics
     * @param topicName
     * @param s
     */
    public void publishMessage(String topicName, Object s){
        getTopic(topicName).publish(s);
    }

    public void init() {
        try {
            instance = Hazelcast.newHazelcastInstance(new InMemoryXmlConfig(getRouteBodyFromFile(getConfFile())));
            addListenerToTopic(getTopicName(), getTopicMessageListener());
        } catch (Exception e) {
            System.out.println("Exception in getRouteBodyFromFile :" + e);
            e.printStackTrace();
        }
    }

    /**
     * Add a value to the hazel cast map.
     * @param mapName
     * @param mapKey
     * @param mapValue
     */
    public void addToMap(String mapName, String mapKey, String mapValue) {
        try {
            final IMap<Object, Object> map = instance.getMap(mapName);
            map.put(mapKey, mapValue);
        } catch (Exception e) {
            System.out.println("Exception in getRouteBodyFromFile :" + e);
            e.printStackTrace();
        }
    }

    /**
     * Gets the value from the map
     * @param mapName
     * @param mapKey
     * @return
     */
    public String getMap(String mapName, int mapKey) {
        String mapValue = null;
        try {
            final IMap<Object, Object> map = instance.getMap(mapName);
            EntryView entry = map.getEntryView(mapKey);
            mapValue = (String)entry.getValue();
        } catch (Exception e) {
            System.out.println("Exception in getRouteBodyFromFile :" + e);
            e.printStackTrace();
        }
        return mapValue;
    }

    /**
     * Gets the value from the map
     * @param mapName
     * @param mapKey
     * @return
     */
    public String getMap(String mapName, String mapKey) {
        String mapValue = null;
        try {
            final IMap<Object, Object> map = instance.getMap(mapName);
            EntryView entry = map.getEntryView(mapKey);
            mapValue = (String)entry.getValue();
        } catch (Exception e) {
            System.out.println("Exception in getRouteBodyFromFile :" + e);
            e.printStackTrace();
        }
        return mapValue;
    }

    private String getRouteBodyFromFile(String routeFile) {
        StringBuffer routeBody = new StringBuffer();
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(this.confFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = br.readLine()) != null) {
                routeBody.append(line);
            }
            br.close();

        } catch (Exception e) {
            System.out.println("Exception in getRouteBodyFromFile :" + e);
            e.printStackTrace();
        }
        return routeBody.toString();
    }
}
