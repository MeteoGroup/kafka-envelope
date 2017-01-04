package org.meteogroup.kafka.envelope;

import org.apache.kafka.common.serialization.Serdes;

import java.nio.ByteBuffer;

public class ByteBufferEnvelopeDeserializer extends GenericEnvelopeDeserializer<ByteBuffer> {

  public ByteBufferEnvelopeDeserializer() {
    super(Serdes.ByteBuffer().deserializer());
  }
}
