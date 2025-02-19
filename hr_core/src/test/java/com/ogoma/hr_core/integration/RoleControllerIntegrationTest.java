//package com.ogoma.hr_core.integration;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ogoma.hr_core.VueStarterApplication;
//import com.ogoma.hr_core.boundaries.access_control.controllers.RolesController;
//import com.ogoma.hr_core.boundaries.access_control.entities.Role;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import javax.servlet.Filter;
//
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
//
//
//@SpringBootTest(classes = VueStarterApplication.class)
//@ActiveProfiles("test")
//@AutoConfigureMockMvc
//@RunWith(SpringRunner.class)
//public class RoleControllerIntegrationTest {
//    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
//    @Autowired
//    protected Filter springSecurityFilterChain;
//    @Autowired
//    MockMvc mockMvc;
//    @Autowired
//    RolesController rolesController;
//    //Used for changing and Object to Json
//    ObjectMapper objectMapper = new ObjectMapper();
//
//    @Autowired
//    private WebApplicationContext wac;
//
//    @Before// runs before each test method
//    public void setup() throws Exception {
//        // required if you would want to test a controller with spring security
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
//                .apply(springSecurity(springSecurityFilterChain))
//                .build();
//    }
//
//    // creates an authenticated user with user test, without this, there will be
//    // a redirect to login since path /api/roles is protected
//    @WithMockUser(username = "test")
//    @Test
//    public void an_authenticated_user_can_create_role() throws Exception {
//        Role role = new Role("test", "test");
//        mockMvc.perform(
//                post("/api/roles", 1)
//                        .with(csrf().asHeader()).secure(true).contentType(CONTENT_TYPE)
//                        .content(
//                                objectMapper.writeValueAsString(role)
//                        )
//        ).andDo(print()).andExpect(status().isOk());
//    }
//
//    @Test
//    public void user_not_authenticated_cannot_create_role_and_will_be_redirected_to_login() throws Exception {
//        Role role = new Role("test", "test");
//        mockMvc.perform(
//                post("/api/roles", 1)
//                        .with(csrf().asHeader()).secure(true).contentType(CONTENT_TYPE)
//                        .content(
//                                objectMapper.writeValueAsString(role)
//                        )
//        ).andDo(print()).andExpect(status().is(302));
//    }
//}
