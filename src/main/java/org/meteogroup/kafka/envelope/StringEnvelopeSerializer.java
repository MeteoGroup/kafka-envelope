package org.meteogroup.kafka.envelope;

import org.apache.kafka.common.serialization.Serdes;

public class StringEnvelopeSerializer extends GenericEnvelopeSerializer<String> {

  public StringEnvelopeSerializer() {
    super(Serdes.String().serializer());
  }
}
