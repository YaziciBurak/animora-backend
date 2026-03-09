package com.animora.comment.controller;

import com.animora.comment.dto.CommentRequest;
import com.animora.comment.dto.CommentResponse;
import com.animora.comment.service.CommentService;
import com.animora.security.jwt.JwtAuthenticationFilter;
import com.animora.security.jwt.JwtService;
import com.animora.security.userdetails.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = CommentController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtAuthenticationFilter.class
        )
)
@EnableMethodSecurity
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    @WithMockUser(authorities = "COMMENT_CREATE")
    void createComment_success() throws Exception {

        CommentRequest request = CommentRequest.builder()
                .content("Great episode!")
                .build();

        CommentResponse response = CommentResponse.builder()
                .id(1L)
                .content("Great episode!")
                .userName("testUser")
                .build();

        when(commentService.createComment(eq(1L), any(CommentRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/animes/1/comments").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Great episode!"));
    }

    @Test
    @WithMockUser
    void createComment_forbidden() throws Exception {

        CommentRequest request = CommentRequest.builder()
                .content("Great anime!")
                .build();

        mockMvc.perform(post("/api/animes/1/comments").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void getComments_success() throws Exception {

        CommentResponse response = CommentResponse.builder()
                .id(1L)
                .content("Great episode!")
                .userName("testUser")
                .build();

        when(commentService.getCommentsByAnime(1L))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/animes/1/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("Great episode!"));
    }

    @Test
    @WithMockUser(authorities = "COMMENT_DELETE_ANY")
    void deleteComment_success() throws Exception {

        doNothing().when(commentService).deleteComment(1L);

        mockMvc.perform(delete("/api/animes/comments/1").with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void deleteComment_forbidden() throws Exception {

        mockMvc.perform(delete("/api/animes/comments/1").with(csrf()))
                .andExpect(status().isForbidden());
    }
}
