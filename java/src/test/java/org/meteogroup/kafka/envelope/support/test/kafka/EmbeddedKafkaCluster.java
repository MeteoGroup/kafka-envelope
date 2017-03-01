package org.meteogroup.kafka.envelope.support.test.kafka;

import kafka.server.KafkaConfig;
import kafka.server.KafkaServerStartable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class EmbeddedKafkaCluster {

  private final List<Integer> ports;
  private final String zkConnection;
  private final Properties baseProperties;

  private final String brokerList;

  private final List<KafkaServerStartable> brokers;
  private final List<File> logDirs;

  public EmbeddedKafkaCluster(String zkConnection) {
    this(zkConnection, new Properties());
  }

  public EmbeddedKafkaCluster(String zkConnection, Properties baseProperties) {
    this(zkConnection, baseProperties, Collections.singletonList(-1));
  }

  public EmbeddedKafkaCluster(String zkConnection, Properties baseProperties, List<Integer> ports) {
    this.zkConnection = zkConnection;
    this.ports = resolvePorts(ports);
    this.baseProperties = baseProperties;

    this.brokers = new ArrayList<>();
    this.logDirs = new ArrayList<>();

    this.brokerList = constructBrokerList(this.ports);
  }

  private List<Integer> resolvePorts(List<Integer> ports) {
    List<Integer> resolvedPorts = new ArrayList<Integer>();
    for (Integer port : ports) {
      resolvedPorts.add(resolvePort(port));
    }
    return resolvedPorts;
  }

  private int resolvePort(int port) {
    if (port == -1) {
      return TestUtils.getAvailablePort();
    }
    return port;
  }

  private String constructBrokerList(List<Integer> ports) {
    StringBuilder sb = new StringBuilder();
    for (Integer port : ports) {
      if (sb.length() > 0) {
        sb.append(",");
      }
      sb.append("localhost:").append(port);
    }
    return sb.toString();
  }

  public void startup() {
    for (int i = 0; i < ports.size(); i++) {
      Integer port = ports.get(i);
      File logDir = TestUtils.constructTempDir("kafka-local");

      Properties properties = new Properties();
      properties.putAll(baseProperties);
      properties.setProperty("zookeeper.connect", zkConnection);
      properties.setProperty("broker.id", String.valueOf(i + 1));
      properties.setProperty("host.name", "localhost");
      properties.setProperty("port", Integer.toString(port));
      properties.setProperty("log.dir", logDir.getAbsolutePath());
      properties.setProperty("log.flush.interval.messages", String.valueOf(1));

      KafkaServerStartable broker = startBroker(properties);

      brokers.add(broker);
      logDirs.add(logDir);
    }
  }

  private KafkaServerStartable startBroker(Properties props) {
    KafkaServerStartable server = new KafkaServerStartable(new KafkaConfig(props));
    server.startup();
    return server;
  }

  public String getBrokerList() {
    return brokerList;
  }

  public void shutdown() {
    for (KafkaServerStartable broker : brokers) {
      try {
        broker.shutdown();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    for (File logDir : logDirs) {
      try {
        TestUtils.deleteFile(logDir);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("EmbeddedKafkaCluster{");
    sb.append("brokerList='").append(brokerList).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
