package com.example.WebsiteTraining.controllers;

import com.example.WebsiteTraining.models.Image;
import com.example.WebsiteTraining.repositories.ImageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageController imageController;

    private MockMvc mockMvc;

    @Test
    void getImageById_ShouldReturnImage_WhenImageExists() throws Exception {
        // Arrange
        long testId = 1L;
        Image testImage = new Image();
        testImage.setId(testId);
        testImage.setOriginalFileName("test.jpg");
        testImage.setContentType("image/jpeg");
        testImage.setSize(1024L);
        testImage.setBytes(new byte[]{1, 2, 3});

        when(imageRepository.findById(testId))
                .thenReturn(Optional.of(testImage));

        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();

        // Act & Assert
        mockMvc.perform(get("/images/{id}", testId))
                .andExpect(status().isOk())
                .andExpect(header().string("fileName", "test.jpg"))
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andExpect(header().longValue("Content-Length", 1024))
                .andExpect(result -> {
                    MockHttpServletResponse response = result.getResponse();
                    assertArrayEquals(new byte[]{1, 2, 3}, response.getContentAsByteArray());
                });
    }

    @Test
    void getImageById_ShouldReturnNotFound_WhenImageDoesNotExist() throws Exception {
        // Arrange
        long testId = 999L;
        when(imageRepository.findById(testId))
                .thenReturn(Optional.empty());

        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();

        // Act & Assert
        mockMvc.perform(get("/images/{id}", testId))
                .andExpect(status().isNotFound());
    }
}