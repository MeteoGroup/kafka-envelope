package org.meteogroup.kafka.envelope;

import org.apache.kafka.common.serialization.Serdes;

public class StringEnvelopeDeserializer extends GenericEnvelopeDeserializer<String> {

  public StringEnvelopeDeserializer() {
    super(Serdes.String().deserializer());
  }
}
