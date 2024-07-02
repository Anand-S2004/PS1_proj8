#!/bin/bash
# compile.sh
# Conversion of compile.cmd to shell script.

JX_HOME=/home/dell/Gilhari
/home/dell/TAR/openlogic-openjdk-8u412-b08-linux-x64/bin/javac -d ./bin -target 1.8 -source 1.8 -cp .:$JX_HOME/libs/jxclasses.jar:$JX_HOME/external_libs/json-20160212.jar @sources.txt
