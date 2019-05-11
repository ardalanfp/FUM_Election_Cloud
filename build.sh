#!/bin/sh
clear
echo "cleaning master"
mvn clean
echo "installing master"
mvn install
