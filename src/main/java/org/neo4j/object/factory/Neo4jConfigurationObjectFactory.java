package org.neo4j.object.factory;

import org.neo4j.ogm.config.Configuration;

import javax.naming.*;
import javax.naming.spi.ObjectFactory;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by Nikolay Groshkov on 04-Aug-2019.
 */
public class Neo4jConfigurationObjectFactory implements ObjectFactory {

    private static final String DRIVER_CLASS_NAME = "driverClassName";
    private static final String URI = "uri";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String CONNECTION_POOL_SIZE = "connectionPoolSize";
    private static final String CONNECTION_LIVENESS_CHECK_TIMEOUT = "connectionLivenessCheckTimeout";
    private static final String ENCRYPTION_LEVEL = "encryptionLevel";
    private static final String TRUST_STRATEGY = "trustStrategy";
    private static final String TRUST_CERT_FILE = "trustCertFile";

    public Object getObjectInstance(Object obj, Name name2, Context nameCtx, Hashtable environment)
            throws NamingException{

        Configuration configuration = new Configuration();
        Reference ref = (Reference) obj;
        Enumeration addrs = ref.getAll();
        String username = null, password = null;
        while (addrs.hasMoreElements()) {
            RefAddr addr = (RefAddr) addrs.nextElement();
            String name = addr.getType();
            if (DRIVER_CLASS_NAME.equals(name)) {
                configuration.driverConfiguration().setURI((String) addr.getContent());
            } else if (URI.equals(name)) {
                configuration.driverConfiguration().setURI((String) addr.getContent());
            } else if (CONNECTION_POOL_SIZE.equals(name)) {
                configuration.driverConfiguration().setConnectionPoolSize(Integer.parseInt((String) addr.getContent()));
            } else if (ENCRYPTION_LEVEL.equals(name)) {
                configuration.driverConfiguration().setEncryptionLevel((String) addr.getContent());
            } else if (TRUST_STRATEGY.equals(name)) {
                configuration.driverConfiguration().setTrustStrategy((String) addr.getContent());
            } else if (TRUST_CERT_FILE.equals(name)) {
                configuration.driverConfiguration().setTrustCertFile((String) addr.getContent());
            } else if (CONNECTION_LIVENESS_CHECK_TIMEOUT.equals(name)) {
                configuration.driverConfiguration().setConnectionLivenessCheckTimeout(Integer.parseInt((String) addr.getContent()));
            } else if (USERNAME.equals(name)) {
                username = (String) addr.getContent();
            } else if (PASSWORD.equals(name)) {
                password = (String) addr.getContent();
            }
        }

        if (username == null) {
            throw new NamingException("Username has to be defined");
        }
        if (password == null) {
            throw new NamingException("Password has to be defined");
        }

        configuration.driverConfiguration().setCredentials(username, password);
        return configuration;
    }
}
