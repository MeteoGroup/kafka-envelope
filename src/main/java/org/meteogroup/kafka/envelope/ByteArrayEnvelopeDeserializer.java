package org.meteogroup.kafka.envelope;

import org.apache.kafka.common.serialization.Serdes;

public class ByteArrayEnvelopeDeserializer extends GenericEnvelopeDeserializer<byte[]> {

  public ByteArrayEnvelopeDeserializer() {
    super(Serdes.ByteArray().deserializer());
  }
}
