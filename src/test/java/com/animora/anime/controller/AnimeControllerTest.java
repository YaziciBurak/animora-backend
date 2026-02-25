package com.animora.anime.controller;

import com.animora.anime.dto.AnimeRequest;
import com.animora.anime.dto.AnimeResponse;
import com.animora.anime.service.AnimeService;
import com.animora.security.jwt.JwtAuthenticationFilter;
import com.animora.security.jwt.JwtService;
import com.animora.security.userdetails.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AnimeController.class)
@AutoConfigureMockMvc(addFilters = false)
class AnimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AnimeService animeService;

    @MockBean
    private JwtService jwtService;
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    @WithMockUser
    void createAnime_success() throws Exception {
        AnimeRequest request = AnimeRequest.builder()
                .title("Bleach")
                .build();

        AnimeResponse response = AnimeResponse.builder()
                .id(1L)
                .title("Bleach")
                .build();

        when(animeService.createAnime(any())).thenReturn(response);

        mockMvc.perform(post("/api/animes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Bleach"));
    }

    @Test
    void createAnime_unauthorized() throws Exception {
        AnimeRequest request = AnimeRequest.builder().title("Bleach").build();
        AnimeResponse response = AnimeResponse.builder().id(1L).title("Bleach").build();

        when(animeService.createAnime(any())).thenReturn(response);

        mockMvc.perform(post("/api/animes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Bleach"));
    }

    @Test
    @WithMockUser
    void getAnimeById_success() throws Exception {
        AnimeResponse response = AnimeResponse.builder()
                .id(1L)
                .title("Bleach")
                .build();

        when(animeService.getAnimeById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/animes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Bleach"));
    }

    @Test
    @WithMockUser
    void deleteAnime_success() throws Exception {
        doNothing().when(animeService).deleteAnime(1L);

        mockMvc.perform(delete("/api/animes/1"))
                .andExpect(status().isNoContent());
    }
}