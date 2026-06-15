#!/bin/bash

mkdir -p ~/jmeter
wget https://dlcdn.apache.org//jmeter/binaries/apache-jmeter-5.6.3.zip -P ~/jmeter
unzip ~/jmeter/apache-jmeter-5.6.3.zip -d ~/jmeter/
rm ~/jmeter/apache-jmeter-5.6.3.zip
mv ~/jmeter/apache-jmeter-5.6.3/* ~/jmeter
rm -r ~/jmeter/apache-jmeter-5.6.3
wget https://repo1.maven.org/maven2/kg/apc/jmeter-plugins-manager/1.12/jmeter-plugins-manager-1.12.jar -P ~/jmeter/lib/ext/
sudo ln -s /home/$USER/jmeter/bin/jmeter /usr/bin/jmeter
wget https://repo1.maven.org/maven2/kg/apc/cmdrunner/2.3/cmdrunner-2.3.jar -P ~/jmeter/lib
java -cp /home/$USER/jmeter/lib/ext/jmeter-plugins-manager-1.12.jar org.jmeterplugins.repository.PluginManagerCMDInstaller
/home/$USER/jmeter/bin/PluginsManagerCMD.sh install jpgc-casutg