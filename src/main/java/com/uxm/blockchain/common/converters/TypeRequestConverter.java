package com.uxm.blockchain.common.converters;

import com.uxm.blockchain.common.Enum.Type;
import org.springframework.core.convert.converter.Converter;

public class TypeRequestConverter implements Converter<String, Type> {

  @Override
  public Type convert(String type) {
    return Type.of(type);
  }
}
