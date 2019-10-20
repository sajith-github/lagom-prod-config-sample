#!/bin/bash
rm -rf RUNNING_PID > /dev/null
sbt clean dist
rm -rf hello-impl-0.1.0-SNAPSHOT > /dev/null
unzip hello-impl/target/universal/hello-impl-0.1.0-SNAPSHOT.zip
./hello-impl-0.1.0-SNAPSHOT/bin/hello-impl
