package org.meteogroup.kafka.envelope;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;

public class BytesEnvelopeDeserializer extends GenericEnvelopeDeserializer<Bytes> {

  public BytesEnvelopeDeserializer() {
    super(Serdes.Bytes().deserializer());
  }
}
