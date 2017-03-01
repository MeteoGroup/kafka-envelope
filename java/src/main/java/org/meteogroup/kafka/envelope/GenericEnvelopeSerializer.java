package org.meteogroup.kafka.envelope;

import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class GenericEnvelopeSerializer<T> extends EnvelopeSerializer<T> {

  final Serializer<T> payloadSerializer;

  public GenericEnvelopeSerializer(Serializer<T> payloadSerializer) {
    this.payloadSerializer = payloadSerializer;
  }

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
  }

  @Override
  public byte[] serializePayload(String topic, T payload) {
    return payloadSerializer.serialize(topic, payload);
  }

  @Override
  public void close() {
  }
}
