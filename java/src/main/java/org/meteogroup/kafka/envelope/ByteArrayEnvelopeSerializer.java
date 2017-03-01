package org.meteogroup.kafka.envelope;

import org.apache.kafka.common.serialization.Serdes;

public class ByteArrayEnvelopeSerializer extends GenericEnvelopeSerializer<byte[]> {

  public ByteArrayEnvelopeSerializer() {
    super(Serdes.ByteArray().serializer());
  }
}
