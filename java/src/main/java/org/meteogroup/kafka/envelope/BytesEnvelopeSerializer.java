package org.meteogroup.kafka.envelope;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;

public class BytesEnvelopeSerializer extends GenericEnvelopeSerializer<Bytes> {

  public BytesEnvelopeSerializer() {
    super(Serdes.Bytes().serializer());
  }
}
