package com.chinhae.newsfeed.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class StringUtil {

  static public String getOrDefaultEmpty(String raw) {
    return raw == null ? "" : raw;
  }
}
