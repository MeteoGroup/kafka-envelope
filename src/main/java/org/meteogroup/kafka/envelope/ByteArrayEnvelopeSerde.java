package org.meteogroup.kafka.envelope;

import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;

public class ByteArrayEnvelopeSerde extends GenericSerde<Envelope<byte[]>> {
  public ByteArrayEnvelopeSerde() {
    super(new ByteArrayEnvelopeSerializer(), new ByteArrayEnvelopeDeserializer());
  }
}
