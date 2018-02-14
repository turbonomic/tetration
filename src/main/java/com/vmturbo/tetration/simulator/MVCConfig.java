package com.vmturbo.tetration.simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.tomcat.util.net.Constants;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class MVCConfig extends WebMvcConfigurerAdapter {
    /**
     * The GSON.
     */
    private static final Gson GSON = new GsonBuilder().enableComplexMapKeySerialization()
                                                      .setPrettyPrinting().create();

    /**
     * Add a new instance of the {@link GsonHttpMessageConverter} to the list of available
     * {@link HttpMessageConverter}s in use.
     *
     * @param converters is the list of {@link HttpMessageConverter}s to which the new converter
     *                   instance is added.
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // Handle text-plain.
        final StringHttpMessageConverter stringMessageConverter =
                new StringHttpMessageConverter(Charset.forName("UTF-8"));
        converters.add(stringMessageConverter);
        // GSON for application-json serialization.
        final GsonHttpMessageConverter msgConverter = new GsonHttpMessageConverter();
        msgConverter.setGson(GSON);
        converters.add(msgConverter);
    }

    /**
     * Redirect HTTP to HTTPS.
     *
     * @return The TomcatEmbeddedServletContainerFactory.
     */
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return container -> {
            TomcatEmbeddedServletContainerFactory tomcat =
                    (TomcatEmbeddedServletContainerFactory)container;
            Connector connector =
                    new Connector(TomcatEmbeddedServletContainerFactory.DEFAULT_PROTOCOL);
            connector.setPort(9443);
            connector.setSecure(true);
            connector.setScheme("https");

            Http11NioProtocol proto = (Http11NioProtocol)connector.getProtocolHandler();
            proto.setSSLEnabled(true);

            proto.setSSLProtocol(Constants.SSL_PROTO_TLSv1_2);
            proto.setSSLCipherSuite("TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256, " +
                                    "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, " +
                                    "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384, " +
                                    "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384");
            proto.setUseServerCipherSuitesOrder("true");
            proto.setSSLHonorCipherOrder("true");
            proto.setKeystoreFile("/home/turbonomic/data/keys/keystore.p12");
            proto.setKeystorePass("jumpy-crazy-experience");
            proto.setKeystoreType("PKCS12");
            proto.setKeyAlias("vmt");
            tomcat.addAdditionalTomcatConnectors(connector);
        };
    }
}
