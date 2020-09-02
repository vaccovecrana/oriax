package io.vacco.oriax.core;

import java.util.*;

public class OxVtx<T> {

  public final String id;
  public T data;

  public OxVtx(String id, T data) {
    this.id = Objects.requireNonNull(id);
    this.data = Objects.requireNonNull(data);
  }

  @Override public boolean equals(Object o) {
    return o instanceof OxVtx && this.id.equals(((OxVtx<?>) o).id);
  }

  @Override public int hashCode() {
    return id.hashCode();
  }
  @Override public String toString() {
    return String.format("V{%s}", id);
  }
}
