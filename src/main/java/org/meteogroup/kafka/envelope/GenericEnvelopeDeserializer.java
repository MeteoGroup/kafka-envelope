package org.meteogroup.kafka.envelope;

import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class GenericEnvelopeDeserializer<T> extends EnvelopeDeserializer<T> {

  final Deserializer<T> payloadDeserializer;

  public GenericEnvelopeDeserializer(Deserializer<T> payloadDeserializer) {
    this.payloadDeserializer = payloadDeserializer;
  }

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
  }

  @Override
  public T deserializePayload(String topic, byte[] payload) {
    return payloadDeserializer.deserialize(topic, payload);
  }

  @Override
  public void close() {
  }
}
