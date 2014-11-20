#!/bin/bash

if [[ ! $JBOSS_HOME ]]; then
  echo "A variável JBOSS_HOME não foi definida"
  exit -2
fi

if [[ ! $1 ]]; then
  echo "Uso: $(basename $0) <senha a ser criptografada>"
  exit -1
fi

java -cp $JBOSS_HOME/modules/system/layers/base/org/picketbox/main/picketbox-4.0.19.SP8-redhat-1.jar:\
$JBOSS_HOME/modules/system/layers/base/org/picketbox/main/picketbox-4.0.19SP8-redhat-1.jar:\
$JBOSS_HOME/modules/system/layers/base/org/jboss/logging/main/jboss-logging-3.1.4.GA-redhat-1.jar:\
$JBOSS_HOME/modules/system/layers/base/org/picketbox/main/picketbox-commons-1.0.0.final-redhat-2.jar:\
$CLASSPATH org.picketbox.datasource.security.SecureIdentityLoginModule $1