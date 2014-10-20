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

Mais informações em 
https://access.redhat.com/documentation/en-US/JBoss_Enterprise_Application_Platform/6/html/Administration_and_Configuration_Guide/Install_a_JDBC_Driver_as_a_Core_Module1.html
e https://access.redhat.com/documentation/en-US/JBoss_Enterprise_Application_Platform/6.1/html/Administration_and_Configuration_Guide/JDBC_Driver_Download_Locations1.html

#### RestEasy ####

Em http://resteasy.jboss.org/ selecionar download e fazer o download da versão 3.0.9.Final do RestEasy.

Após fazer o download do RestEasy de 
http://sourceforge.net/projects/resteasy/files/Resteasy%20JAX-RS/3.0.9.Final/resteasy-jaxrs-3.0.9.Final-all.zip/download
descompactá-lo e localizer dentro dele o arquivo resteasy-jboss-modules-3.0.9.Final.zip, e 
descompactar resteasy-jboss-modules-3.0.9.Final.zip no diretório modules/system/layers/base/

Maiores informações em 
http://docs.jboss.org/resteasy/docs/3.0.9.Final/userguide/html/Installation_Configuration.html#upgrading-eap61

Configuração do domain
----------------------

//ToDo

Configuração do serviço JBoss
-----------------------------

O JBoss já vem com um script para ser colocado no init.d, porém precisa de ajustes para executar de forma correta no RHEL 6

//ToDo

