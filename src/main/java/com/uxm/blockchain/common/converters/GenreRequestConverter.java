package com.uxm.blockchain.common.converters;

import com.uxm.blockchain.common.Enum.Genre;
import org.springframework.core.convert.converter.Converter;

public class GenreRequestConverter implements Converter<String, Genre> {

  @Override
  public Genre convert(String genre) {
    return Genre.of(genre);
  }
}
