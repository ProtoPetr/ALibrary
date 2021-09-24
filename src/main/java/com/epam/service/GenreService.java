package com.epam.service;

import com.epam.entity.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> findAllGenres();
}
