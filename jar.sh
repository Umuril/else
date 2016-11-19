#!/bin/bash
rm ELSE.jar
jar cvmf MANIFEST.mf ELSE.jar -C bin . -C lib .
