package brainacad.org.ticketordery.ProfileTest;

import brainacad.org.ticketordery.TicketOrderyApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = TicketOrderyApplication.class)
@ActiveProfiles("test")
public class ProfileTestWithTestProfile
{
    @Autowired
    private Environment environment;

    @Test
    void testActiveProfile_ReturnProfile()
    {
        System.setProperty("spring.profiles.active", "test");
        String[] activeProfiles = environment.getActiveProfiles();
        System.out.println("Active profile: " + String.join(", ", activeProfiles));
        assertEquals("test", activeProfiles[0], "Active profile is not 'test'!");
    }

    @Test
    void checkActiveProfile_ReturnProfile()
    {
        String[] activeProfiles = environment.getActiveProfiles();
        assertTrue(activeProfiles.length > 0, "No active profiles found!");

        assertEquals("test", activeProfiles[0], "Active profile is not 'test'!");
        System.out.println("Active profile: " + activeProfiles[0]);
    }

    @Test
    void logPropertySources()
    {
        if (environment instanceof ConfigurableEnvironment)
        {
            ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) environment;
            for (PropertySource<?> propertySource : configurableEnvironment.getPropertySources())
            {
                System.out.println("Property Source: " + propertySource.getName());
                if (propertySource instanceof EnumerablePropertySource<?>)
                {
                    EnumerablePropertySource<?> eps = (EnumerablePropertySource<?>) propertySource;
                    for (String propertyName : eps.getPropertyNames())
                    {
                        System.out.println(propertyName + " = " + eps.getProperty(propertyName));
                    }
                } else {
                    System.out.println("Property source is not EnumerablePropertySource: " + propertySource.getClass().getName());
                }
            }
        } else {
            System.out.println("Environment is not a ConfigurableEnvironment instance.");
        }
    }
}