package org.meteogroup.kafka.envelope;

import org.apache.kafka.common.utils.Bytes;

public class BytesEnvelopeSerde extends GenericSerde<Envelope<Bytes>> {
  public BytesEnvelopeSerde() {
    super(new BytesEnvelopeSerializer(), new BytesEnvelopeDeserializer());
  }
}
