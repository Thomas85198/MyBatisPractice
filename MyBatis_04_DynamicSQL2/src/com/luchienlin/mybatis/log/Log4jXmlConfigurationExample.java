package com.luchienlin.mybatis.log;
 
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
 
public class Log4jXmlConfigurationExample
{
    static Logger logger = Logger.getLogger(Log4jXmlConfigurationExample.class);
 
    public static void main(String[] args)
    {
        //DOMConfigurator is used to configure logger from xml configuration file
        DOMConfigurator.configure("log4j.xml");
 
        //Log in console in and log file
        logger.debug("Log4j appender configuration is successful !!");
    }
}