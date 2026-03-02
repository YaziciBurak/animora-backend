package com.animora.season;

import com.animora.common.pagination.PageResponse;
import com.animora.season.controller.SeasonController;
import com.animora.season.dto.SeasonRequest;
import com.animora.season.dto.SeasonResponse;
import com.animora.season.service.SeasonService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = SeasonController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtAuthenticationFilter.class
        ))
class SeasonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SeasonService seasonService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    @WithMockUser(authorities = "SEASON_CREATE")
    void createSeason_success() throws Exception{

        SeasonRequest request = SeasonRequest.builder()
                .seasonNumber(1)
                .releaseYear(2020)
                .build();

        SeasonResponse response = SeasonResponse.builder()
                .id(1L)
                .seasonNumber(1)
                .episodeCount(12)
                .build();

        when(seasonService.createSeason(eq(1L), any(SeasonRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/animes/1/seasons").with(csrf())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.seasonNumber").value(1))
                .andExpect(jsonPath("$.episodeCount").value(12));
    }

    @Test
    @WithMockUser
    void createSeason_forbidden() throws Exception{

        SeasonRequest request = SeasonRequest.builder()
                .seasonNumber(1)
                .releaseYear(2020)
                .build();

        mockMvc.perform(post("/api/animes/1/seasons")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void getSeasons_success() throws Exception{

        SeasonResponse response = SeasonResponse.builder()
                .id(1L)
                .seasonNumber(1)
                .episodeCount(12)
                .build();

        PageResponse<SeasonResponse> pageResponse =
                PageResponse.<SeasonResponse>builder()
                        .content(List.of(response))
                        .page(0)
                        .size(1)
                        .totalElements(1)
                        .totalPages(1)
                        .hasNext(false)
                        .hasPrevious(false)
                        .build();

        when(seasonService.getSeasonsByAnimeId(eq(1L), any(Pageable.class)))
                .thenReturn(pageResponse);

        mockMvc.perform(get("/api/animes/1/seasons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].seasonNumber").value(1));
    }

    @Test
    @WithMockUser(authorities = "SEASON_UPDATE")
    void updateSeason_success() throws Exception{

        SeasonRequest request = SeasonRequest.builder()
                .seasonNumber(2)
                .releaseYear(2021)
                .build();

        SeasonResponse response = SeasonResponse.builder()
                .id(1L)
                .seasonNumber(2)
                .episodeCount(24)
                .build();

        when(seasonService.updateSeason(eq(1L), eq(1L), any(SeasonRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/animes/1/seasons/1").with(csrf())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seasonNumber").value(2));
    }

    @Test
    @WithMockUser(authorities = "SEASON_DELETE")
    void deleteSeason_success() throws Exception{

        doNothing().when(seasonService).deleteSeason(1L, 1L);

        mockMvc.perform(delete("/api/animes/1/seasons/1").with(csrf()))
                .andExpect(status().isNoContent());
    }
}
