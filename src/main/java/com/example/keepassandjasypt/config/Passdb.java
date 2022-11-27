package com.example.keepassandjasypt.config;


import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableEncryptableProperties
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Passdb {
    @Value("${keepass.db}")
    private String db;

    @Value("${keepass.key}")
    private String key;

    @Value("${keepass.pas}")
    private String pas;

    @Value("${keepass.name}")
    private String name;
}
