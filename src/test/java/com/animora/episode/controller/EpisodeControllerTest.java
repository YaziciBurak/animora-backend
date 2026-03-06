package com.animora.episode.controller;

import com.animora.common.pagination.PageResponse;
import com.animora.episode.dto.EpisodeRequest;
import com.animora.episode.dto.EpisodeResponse;
import com.animora.episode.service.EpisodeService;
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
import org.springframework.data.domain.Pageable;
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

@WebMvcTest(controllers = EpisodeController.class,
            excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                    classes = JwtAuthenticationFilter.class
            ))
@EnableMethodSecurity
class EpisodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EpisodeService episodeService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    @WithMockUser(authorities = "EPISODE_CREATE")
    void createEpisode_success() throws Exception {
        EpisodeRequest request = EpisodeRequest.builder()
                .episodeNumber(1)
                .title("Episode 1")
                .duration(24)
                .videoUrl("https://cdn.animora.com/episode1.mp4")
                .build();

        EpisodeResponse response = EpisodeResponse.builder()
                .id(1L)
                .episodeNumber(1)
                .title("Episode 1")
                .duration(24)
                .build();

        when(episodeService.createEpisodeToSeason(eq(1L), any(EpisodeRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/seasons/1/episodes").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.episodeNumber").value(1))
                .andExpect(jsonPath("$.title").value("Episode 1"));
    }

    @Test
    @WithMockUser
    void createEpisode_forbidden() throws Exception {
        EpisodeRequest request = EpisodeRequest.builder()
                .episodeNumber(1)
                .title("Episode 1")
                .duration(24)
                .videoUrl("https://cdn.animora.com/episode1.mp4")
                .build();

        mockMvc.perform(post("/api/seasons/1/episodes").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void getEpisodes_success() throws Exception {
        EpisodeResponse response = EpisodeResponse.builder()
                .id(1L)
                .episodeNumber(1)
                .title("Episode 1")
                .duration(24)
                .build();

        PageResponse<EpisodeResponse> pageResponse = PageResponse.<EpisodeResponse>builder()
                .content(List.of(response))
                .page(0)
                .size(1)
                .totalElements(1)
                .totalPages(1)
                .hasNext(false)
                .hasPrevious(false)
                .build();

        when(episodeService.getEpisodesBySeasonId(eq(1L), any(Pageable.class)))
                .thenReturn(pageResponse);

        mockMvc.perform(get("/api/seasons/1/episodes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].episodeNumber").value(1));
    }

    @Test
    @WithMockUser(authorities = "EPISODE_UPDATE")
    void updateEpisode_success() throws Exception {
        EpisodeRequest request = EpisodeRequest.builder()
                .episodeNumber(2)
                .title("Episode 2")
                .duration(25)
                .videoUrl("https://cdn.animora.com/episode2.mp4")
                .build();

        EpisodeResponse response = EpisodeResponse.builder()
                .id(1L)
                .episodeNumber(2)
                .title("Episode 2")
                .duration(25)
                .build();

        when(episodeService.updateEpisode(eq(1L), eq(1L), any(EpisodeRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/seasons/1/episodes/1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.episodeNumber").value(2));
    }

    @Test
    @WithMockUser(authorities = "EPISODE_DELETE")
    void deleteEpisode_success() throws Exception {
        doNothing().when(episodeService).deleteEpisode(1L, 1L);

        mockMvc.perform(delete("/api/seasons/1/episodes/1").with(csrf()))
                .andExpect(status().isNoContent());
    }
}
