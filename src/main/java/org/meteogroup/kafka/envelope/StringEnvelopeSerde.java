package org.meteogroup.kafka.envelope;

public class StringEnvelopeSerde extends GenericSerde<Envelope<String>> {
  public StringEnvelopeSerde() {
    super(new StringEnvelopeSerializer(), new StringEnvelopeDeserializer());
  }
}
