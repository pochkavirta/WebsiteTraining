package com.example.WebsiteTraining.repositories;

import com.example.WebsiteTraining.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
