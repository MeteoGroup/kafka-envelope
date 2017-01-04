package org.meteogroup.kafka.envelope;

import com.google.protobuf.ByteString;
import org.apache.kafka.common.serialization.Serializer;

public abstract class EnvelopeSerializer<T> implements Serializer<Envelope<T>> {

  @Override
  public byte[] serialize(String topic, Envelope<T> envelope) {
    if (envelope == null) {
      return EnvelopeProtoBuf.Envelope.getDefaultInstance().toByteArray();
    }
    return EnvelopeProtoBuf.Envelope.newBuilder()
        .putAllHeaders(envelope.headers)
        .setPayload(ByteString.copyFrom(serializePayload(topic, envelope.payload)))
        .build().toByteArray();
  }

  protected abstract byte[] serializePayload(String topic, T data);
}
