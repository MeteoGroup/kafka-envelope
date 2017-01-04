package org.meteogroup.kafka.envelope;

import org.apache.kafka.common.serialization.Serdes;

import java.nio.ByteBuffer;

public class ByteBufferEnvelopeSerializer extends GenericEnvelopeSerializer<ByteBuffer> {

  public ByteBufferEnvelopeSerializer() {
    super(Serdes.ByteBuffer().serializer());
  }
}
