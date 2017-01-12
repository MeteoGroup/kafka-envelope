package org.meteogroup.kafka.envelope;

import org.apache.kafka.common.serialization.ByteBufferDeserializer;
import org.apache.kafka.common.serialization.ByteBufferSerializer;

import java.nio.ByteBuffer;

public class ByteBufferEnvelopeSerde extends GenericSerde<Envelope<ByteBuffer>> {
  public ByteBufferEnvelopeSerde() {
    super(new ByteBufferEnvelopeSerializer(), new ByteBufferEnvelopeDeserializer());
  }
}
