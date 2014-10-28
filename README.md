jee6poc
=======

Prova de Conceito do uso da plataforma JEE6. Utiliza Deltaspike, JSF com Primefaces e JBoss Logging.

Configuração de ambiente JBoss
------------------------------

A prova de conceito tem como ambiente de referência o JBoss EAP 6.3GA que precisa ser corretamente 
configurado para que a POC funcione como esperado.

### Pré-requisitos ###

* Oracle JDK 7 instalado e configurado - atualmente é o Oracle JDK 7u67 (jdk-7u67-linux-x64.rpm em 
http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)

### Download do JBoss ###

A página de downloads do JBoss fica em http://www.jboss.org/products/eap/download/

Para fazer o download é necessário ter uma conta criada na RedHat (que está fora do escopo deste guia)

Para a instalação de referência, utilizaremos o link http://www.jboss.org/download-manager/file/jboss-eap-6.3.0.GA.zip

### Instalação do JBoss ###

Após descompactar o jboss-eap-6.3.0.GA.zip em /srv criar um link simbólico:

    # ln -s /srv/jboss-eap-6.3 /srv/jboss

### Criação/instalação de módulos do JBoss ###

#### Oracle JDBC Thin Driver ####

Para fazer o download é necessário ter uma conta criada na Oracle (que está fora do escopo deste guia)

Fazer o download do ojdbc6.jar a partir de http://www.oracle.com/technetwork/database/enterprise-edition/jdbc-112010-090769.html após aceitar a licença da Oracle

Dentro do diretório modules do JBoss, criar em oracle/jdbc/main o arquivo module.xml com o conteúdo:

    <?xml version="1.0" encoding="UTF-8" standalone="no"?>
    <module xmlns="urn:jboss:module:1.0" name="oracle.jdbc">
        <resources>
            <resource-root path="ojdbc6.jar"/>
        </resources>
        <dependencies>
            <module name="javax.api"/>
            <module name="javax.transaction.api"/>
        </dependencies>
    </module>

Copiar também para modules/oracle/jdbc/main o arquivo ojdbc6.jar baixado do site da Oracle.

Mais informações em:

* https://access.redhat.com/documentation/en-US/JBoss_Enterprise_Application_Platform/6/html/Administration_and_Configuration_Guide/Install_a_JDBC_Driver_as_a_Core_Module1.html
* https://access.redhat.com/documentation/en-US/JBoss_Enterprise_Application_Platform/6.1/html/Administration_and_Configuration_Guide/JDBC_Driver_Download_Locations1.html

#### RestEasy ####

Em http://resteasy.jboss.org/ selecionar download e fazer o download da versão 3.0.9.Final do RestEasy.

Após fazer o download do RestEasy de http://sourceforge.net/projects/resteasy/files/Resteasy%20JAX-RS/3.0.9.Final/resteasy-jaxrs-3.0.9.Final-all.zip/download
descompactá-lo e localizer dentro dele o arquivo resteasy-jboss-modules-3.0.9.Final.zip, e 
descompactar resteasy-jboss-modules-3.0.9.Final.zip no diretório modules/system/layers/base/

Maiores informações em http://docs.jboss.org/resteasy/docs/3.0.9.Final/userguide/html/Installation_Configuration.html#upgrading-eap61

### Configuração do domain ###

#### Configurando um security domain para não colocar senhas em plain text dentro de arquivos de configuração ####

A configuração de um security domain picketbox permite que uma senha criptografada seja utilizada dentro do domain.xml
quando necessário, em vez da senha plain text.

Para gerar a senha criptografada, execute:

    $ java -cp $JBOSS_HOME/modules/system/layers/base/org/picketbox/main/picketbox-4.0.19.SP8-redhat-1.jar:\
      $JBOSS_HOME/modules/system/layers/base/org/picketbox/main/picketbox-4.0.19SP8-redhat-1.jar:\
      $JBOSS_HOME/modules/system/layers/base/org/jboss/logging/main/jboss-logging-3.1.4.GA-redhat-1.jar:\
      $JBOSS_HOME/modules/system/layers/base/org/picketbox/main/picketbox-commons-1.0.0.final-redhat-2.jar:\
      $CLASSPATH org.picketbox.datasource.security.SecureIdentityLoginModule senha-plain-text

Guarde a senha criptografada emitida no terminal.

Edite o arquivo $JBOSS_HOME/domain/configuration/domain.xml.

Procure por

    <subsystem xmlns="urn:jboss:domain:datasources:1.2">

em cada profile (default, ha, full, full-ha, etc.), e dentro da tag

    <security-domains>

acrescente o domain descrito mais abaixo:

    <security-domain name="encrypted-javaee6poc-ds" cache-type="default">
        <authentication>
            <login-module code="org.picketbox.datasource.security.SecureIdentityLoginModule" flag="required">
                <module-option name="password" value="senha-criptografada"/>
                <module-option name="managedConnectionFactoryName" value="jboss.jca:service=LocalTxCM,name=javaee6pocds"/>
                <module-option name="username" value="javaee6poc"/>
            </login-module>
        </authentication>
    </security-domain>

Na configuração acima, lembrar de adequar ao seu ambiente, substituindo:

* "senha-criptografada" em password para a senha gerada pelo comando anterior
* a substring javaee6pocds em "managedConnectionFactoryName" para o nome do datasource da aplicação
* o nome do security domain de "encrypted-javaee6poc-ds" para um mais adquado à aplicação

No datasource da aplicação, a tag security deve ser configurada da forma abaixo,
lembrando de substituir encrypted-javaee6poc-ds pelo nome do security domain usado
acima:

    <security>
        <security-domain>encrypted-javaee6poc-ds</security-domain>
    </security>

### Configuração do serviço JBoss ###

O JBoss já vem com um script para ser colocado no init.d, porém precisa de ajustes para executar de forma correta no RHEL 6

//ToDo: descrever alterações necessárias no script para funcionar nos ambientes dos TRTs

Publicação da POC em ambiente JBoss remoto
------------------------------------------

Para publicar a aplicação em servidor no JBoss Server Group "test-server-group" usando o Server Profile "full":

    $ mvn -X -P jboss-deploy-war -Djboss-as.hostname=<nome ou IP do host jboss> install

Para modificar o JBoss Server Group, acrescentar à chamada do maven a variável -Dserver.group=\<nome do server group\>

Para modificar o Server Profile, acrescentar à chamada do maven a variável -Dserver.profile=\<nome do server profile\>

Para remover a aplicação do servidor onde foi publicada:

    $ mvn -X -P jboss-deploy-war -Djboss-as.hostname=<nome ou IP do host jboss> jboss-as:undeploy

