package com.example.keepassandjasypt.config;

import com.example.keepassandjasypt.objects.CredentialObject;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import de.slackspace.openkeepass.KeePassDatabase;
import de.slackspace.openkeepass.domain.Entry;
import de.slackspace.openkeepass.domain.KeePassFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;

@Configuration
@EnableEncryptableProperties
public class JpaConfig {

    @Autowired
    private Environment env;

    @Value("${keepass.db}")
    private String db;

    @Value("${keepass.key}")
    private String key;

    @Value("${keepass.pas}")
    private String pas;

    @Value("${keepass.name}")
    private String name;

    private KeePassFile database;
    private Entry l1;

    public CredentialObject getPass (String name) {
        File file = null;
        File dbFile = null;
        try {
            file = ResourceUtils.getFile("classpath:"+key.trim());
            dbFile = ResourceUtils.getFile("classpath:"+db.trim());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(pas.trim()+"-"+dbFile.getPath()+"-"+file.getPath());
        database= KeePassDatabase.getInstance(dbFile.getPath()).openDatabase(pas.trim(),file);
        l1 = database.getEntryByTitle(name);
        CredentialObject obj = new CredentialObject(l1.getUsername(), l1.getPassword());
        return obj;
    }

    @Bean
    public DataSource getDataSource()
    {
        String pass = getPass(env.getRequiredProperty("keepass.name")).getPassword();
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(env.getRequiredProperty("dbdriver"));
        dataSourceBuilder.url(env.getRequiredProperty("dburl"));
        dataSourceBuilder.username(env.getRequiredProperty("dbuser"));
        dataSourceBuilder.password(pass);
        return dataSourceBuilder.build();
    }
}
