#! /bin/bash


start_dir=target/classes/
dependencies=target/dependency

java -cp "$start_dir:$dependencies/*" org.fresheed.university.ControlConsole $1
