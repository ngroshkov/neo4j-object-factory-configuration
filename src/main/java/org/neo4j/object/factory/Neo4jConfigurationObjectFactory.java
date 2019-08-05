package org.neo4j.object.factory;

import org.neo4j.ogm.config.Configuration;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by Nikolay Groshkov on 04-Aug-2019.
 */
public class Neo4jConfigurationObjectFactory implements ObjectFactory {

    private static final String URI = "uri";
    private static final String CONNECTION_POOL_SIZE = "connectionPoolSize";
    private static final String ENCRYPTION_LEVEL = "encryptionLevel";
    private static final String TRUST_STRATEGY = "trustStrategy";
    private static final String TRUST_CERT_FILE = "trustCertFile";
    private static final String CONNECTION_LIVENESS_CHECK_TIMEOUT = "connectionLivenessCheckTimeout";
    private static final String VERIFY_CONNECTION = "verifyConnection";
    private static final String AUTO_INDEX = "autoIndex";
    private static final String GENERATED_INDEXES_OUTPUT_DIR = "generatedIndexesOutputDir";
    private static final String GENERATED_INDEXES_OUTPUT_FILENAME = "generatedIndexesOutputFilename";
    private static final String NEO4J_CONF_LOCATION = "neo4jConfLocation";
    private static final String NEO4J_HA_PROPERTIES_FILE = "neo4jHaPropertiesFile";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    public Object getObjectInstance(Object obj, Name name2, Context nameCtx, Hashtable environment) {

        Configuration.Builder builder = new Configuration.Builder();
        Reference ref = (Reference) obj;
        Enumeration addrs = ref.getAll();
        String username = null, password = null;
        while (addrs.hasMoreElements()) {
            RefAddr addr = (RefAddr) addrs.nextElement();
            String name = addr.getType();
            if (URI.equals(name)) {
                builder = builder.uri((String) addr.getContent());
            } else if (CONNECTION_POOL_SIZE.equals(name)) {
                builder = builder.connectionPoolSize(Integer.parseInt((String) addr.getContent()));
            } else if (ENCRYPTION_LEVEL.equals(name)) {
                builder = builder.encryptionLevel((String) addr.getContent());
            } else if (TRUST_STRATEGY.equals(name)) {
                builder = builder.trustStrategy((String) addr.getContent());
            } else if (TRUST_CERT_FILE.equals(name)) {
                builder = builder.trustCertFile((String) addr.getContent());
            } else if (CONNECTION_LIVENESS_CHECK_TIMEOUT.equals(name)) {
                builder = builder.connectionLivenessCheckTimeout(Integer.parseInt((String) addr.getContent()));
            } else if (VERIFY_CONNECTION.equals(name)) {
                builder = builder.verifyConnection(Boolean.parseBoolean((String) addr.getContent()));
            } else if (AUTO_INDEX.equals(name)) {
                builder = builder.autoIndex((String) addr.getContent());
            } else if (GENERATED_INDEXES_OUTPUT_DIR.equals(name)) {
                builder = builder.generatedIndexesOutputDir((String) addr.getContent());
            } else if (GENERATED_INDEXES_OUTPUT_FILENAME.equals(name)) {
                builder = builder.generatedIndexesOutputFilename((String) addr.getContent());
            } else if (NEO4J_CONF_LOCATION.equals(name)) {
                builder = builder.neo4jConfLocation((String) addr.getContent());
            } else if (NEO4J_HA_PROPERTIES_FILE.equals(name)) {
                builder = builder.neo4jHaPropertiesFile((String) addr.getContent());
            } else if (USERNAME.equals(name)) {
                username = (String) addr.getContent();
            } else if (PASSWORD.equals(name)) {
                password = (String) addr.getContent();
            }
        }

        builder.credentials(username, password);
        return builder.build();
    }
}
