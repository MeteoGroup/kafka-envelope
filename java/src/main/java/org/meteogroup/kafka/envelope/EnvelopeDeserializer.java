package org.meteogroup.kafka.envelope;

import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.serialization.Deserializer;

public abstract class EnvelopeDeserializer<T> implements Deserializer<Envelope<T>> {

  @Override
  public Envelope<T> deserialize(String topic, byte[] data) {
    try {
      if (data == null) {
        return new Envelope<T>(deserializePayload(topic, null));
      }
      EnvelopeProtoBuf.Envelope protobuf = EnvelopeProtoBuf.Envelope.parseFrom(data);
      return new Envelope<>(protobuf.getHeadersMap(),
                            deserializePayload(topic, protobuf.getPayload().toByteArray()));
    } catch (InvalidProtocolBufferException e) {
      throw new KafkaException(e);
    }
  }

  protected abstract T deserializePayload(String topic, byte[] data);
}
