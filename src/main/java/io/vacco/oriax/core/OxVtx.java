package io.vacco.oriax.core;

import java.util.*;
import static java.lang.String.format;

public class OxVtx<K, T> {

  public final K id;
  public T data;
  public String label;

  public OxVtx(K id, T data) {
    this.id = Objects.requireNonNull(id);
    this.data = Objects.requireNonNull(data);
  }

  public OxVtx<K, T> withLabel(String label) {
    this.label = Objects.requireNonNull(label);
    return this;
  }

  @Override public boolean equals(Object o) {
    return o instanceof OxVtx && this.id.equals(((OxVtx<?, ?>) o).id);
  }

  @Override public int hashCode() { return id.hashCode(); }

  @Override public String toString() {
    return format("V{%s%s}", id, label == null ? "" : format(",%s", label));
  }
}
