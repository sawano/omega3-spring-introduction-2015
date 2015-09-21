package se.omegapoint.spring.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class UserRestServiceIT {

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    public void should_get_all_users() throws Exception {
        mockMvc.perform(get("/users")
                                .accept(APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
               .andExpect(content().json("{users: [{name: donald}, {name: mickey}]}"));
    }

    @Test
    public void should_get_a_specific_user() throws Exception {
        mockMvc.perform(get("/users/1")
                                .accept(APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().json("{name: donald, links: [{rel: self, href: \"http://localhost/users/1\"}, {rel: next, href: \"http://localhost\"}]}"));
    }

    @Test
    public void should_return_not_found_when_getting_an_unknown_user() throws Exception {
        mockMvc.perform(get("/users/123")
                                .accept(APPLICATION_JSON))
               .andExpect(status().isNotFound());
    }
}
