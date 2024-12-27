package fr.gtandu.controller;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestPropertySource;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

@ImportAutoConfiguration(RefreshAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@TestPropertySource(locations = "classpath:bootstrap.yml")
public abstract class SetupControllerTest {
    protected final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor JWT_REQUEST_POST_PROCESSOR = jwt()
            .authorities(new SimpleGrantedAuthority("ROLE_USER"));
}
