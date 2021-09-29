package com.epam.servise;

import com.epam.entity.Genre;

import java.util.List;

/**
 * Interface declares methods for work with current entity
 * and contains specific logic
 */
public interface GenreService {
    List<Genre> findAllGenres();
}
