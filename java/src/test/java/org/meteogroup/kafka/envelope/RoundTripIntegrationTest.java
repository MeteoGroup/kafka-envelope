package org.meteogroup.kafka.envelope;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.meteogroup.kafka.envelope.support.test.kafka.EmbeddedKafkaCluster;
import org.meteogroup.kafka.envelope.support.test.kafka.EmbeddedZookeeper;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Properties;
import java.util.UUID;

import static java.util.Collections.singleton;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class RoundTripIntegrationTest {

  public static final String TOPIC = "my-amazing-topic";
  private KafkaConsumer<String, Envelope<String>> consumer;
  private Serde<String> keySerde;
  private Serde<Envelope<String>> envelopeSerde;
  private KafkaProducer<String, Envelope<String>> producer;
  private EmbeddedKafkaCluster kafka;
  private EmbeddedZookeeper zookeeper;

  @BeforeMethod
  public void setup() throws Exception {
    zookeeper = new EmbeddedZookeeper();
    zookeeper.startup();
    kafka = new EmbeddedKafkaCluster(zookeeper.getConnection());
    kafka.startup();
    //    kafkaUnit.start();
    keySerde = Serdes.String();
    envelopeSerde = Serdes.serdeFrom(new StringEnvelopeSerializer(), new StringEnvelopeDeserializer());
    consumer = createConsumer();
    producer = createProducer();
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() throws Exception {
    kafka.shutdown();
    zookeeper.shutdown();
  }

  @Test
  public void roundtrip() throws Exception {
    RecordMetadata send = producer.send(new ProducerRecord<>(TOPIC, new Envelope<>(singletonMap("header", "value"), "payload"))).get();

    consumer.subscribe(singleton(TOPIC));

    ConsumerRecords<String, Envelope<String>> records = consumer.poll(2000);

    assertThat(records).hasSize(1);
    ConsumerRecord<String, Envelope<String>> record = records.iterator().next();
    assertMessageReceivedIsMessageSent(record, send);
    assertRecievedEnvelopeAndPayloadAreIntact(record);
  }

  void assertMessageReceivedIsMessageSent(ConsumerRecord<String, Envelope<String>> record, RecordMetadata send) {
    assertThat(record.topic()).isEqualTo(send.topic());
    assertThat(record.partition()).isEqualTo(send.partition());
    assertThat(record.offset()).isEqualTo(send.offset());
  }

  void assertRecievedEnvelopeAndPayloadAreIntact(ConsumerRecord<String, Envelope<String>> record) {
    Envelope<String> envelope = record.value();
    assertThat(envelope.headers).containsOnly(entry("header", "value"));
    assertThat(envelope.payload).isEqualTo("payload");
  }

  KafkaConsumer<String, Envelope<String>> createConsumer() {
    Properties props = new Properties();
    props.put("bootstrap.servers", kafka.getBrokerList());
    props.put("group.id", UUID.randomUUID().toString());
    props.put("auto.offset.reset", "earliest");
    return new KafkaConsumer<>(props, keySerde.deserializer(), envelopeSerde.deserializer());
  }

  KafkaProducer<String, Envelope<String>> createProducer() {
    Properties props = new Properties();
    props.put("bootstrap.servers", kafka.getBrokerList());
    return new KafkaProducer<>(props, keySerde.serializer(), envelopeSerde.serializer());
  }
}
