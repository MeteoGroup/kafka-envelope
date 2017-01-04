kafka-envelope
==============

Kafka messages per-se do not have any mean to carry generic metadata.
This project defines a message metadata format on top of Kafka using
Protobuf3 for envelop serialization and deserialization.

While being implemented in Java the wide availability of Protobuf makes
it easy to create implementations in other languages.


How to build
------------

To build just run plain old

```
mvn clean verify
```

A local installation of `protoc` is _not_ required.


How to install
--------------

To add the artifact to your local maven repository run

```
mvn install
```

License
-------

Copyright © 2016 MeteoGroup Deutschland GmbH

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
