package com.github.sanjayrawat1.cms.security;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Sanjay Singh Rawat
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Security security = new Security();

    @Getter
    public static class Security {

        private final Authentication authentication = new Authentication();

        @Getter
        public static class Authentication {

            private final Jwt jwt = new Jwt();

            private final OAuth2 oauth2 = new OAuth2();

            @Getter
            @Setter
            public static class Jwt {

                private String secret;

                private Duration validity;
            }

            @Getter
            @Setter
            public static class OAuth2 {

                private final Map<String, Provider> provider = new HashMap<>();

                @Getter
                @Setter
                public static class Provider {

                    private String issuerUri;
                }
            }
        }
    }
}
