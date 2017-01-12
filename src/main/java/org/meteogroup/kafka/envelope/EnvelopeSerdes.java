package org.meteogroup.kafka.envelope;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.utils.Bytes;

import java.nio.ByteBuffer;

public class EnvelopeSerdes {

  static public Serde<Envelope<byte[]>> byteArrayEnvelopSerde() {
    return new ByteArrayEnvelopeSerde();
  }

  static public Serde<Envelope<ByteBuffer>> byteBufferEnvelopSerde() {
    return new ByteBufferEnvelopeSerde();
  }

  static public Serde<Envelope<Bytes>> bytesEnvelopSerde() {
    return new BytesEnvelopeSerde();
  }

  static public Serde<Envelope<String>> stringEnvelopSerde() {
    return new StringEnvelopeSerde();
  }

  @SuppressWarnings("unchecked")
  static public <T> Serde<Envelope<T>> envelopeSerdeFrom(Class<T> type) {
    if (String.class.isAssignableFrom(type)) {
      return (Serde) new StringEnvelopeSerde();
    }

    if (byte[].class.isAssignableFrom(type)) {
      return (Serde) new ByteArrayEnvelopeSerde();
    }

    if (ByteBuffer.class.isAssignableFrom(type)) {
      return (Serde) new ByteBufferEnvelopeSerde();
    }

    if (Bytes.class.isAssignableFrom(type)) {
      return (Serde) new BytesEnvelopeSerde();
    }

    // TODO: we can also serializes objects of type T using generic Java serialization by default
    throw new IllegalArgumentException("Unknown class for built-in serializer");
  }
}
