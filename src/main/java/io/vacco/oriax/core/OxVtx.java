package io.vacco.oriax.core;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class OxVtx<K, T> {

  public K id;
  public T data;
  public String label;
  public String g0, g1; // group hints for Mermaid diagrams

  public OxVtx<K, T> set(K id, T data) {
    this.id = requireNonNull(id);
    this.data = requireNonNull(data);
    return this;
  }

  public OxVtx<K, T> label(String label) {
    this.label = requireNonNull(label);
    return this;
  }

  public OxVtx<K, T> group0(String group0) {
    this.g0 = requireNonNull(group0);
    return this;
  }

  public OxVtx<K, T> group1(String group1) {
    this.g1 = requireNonNull(group1);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof OxVtx && this.id.equals(((OxVtx<?, ?>) o).id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    return format(
      "V{%s%s}",
      id,
      label == null ? "" : format(",%s", label)
    );
  }

}
