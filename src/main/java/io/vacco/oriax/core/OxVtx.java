package io.vacco.oriax.core;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class OxVtx<K, T> {

  public K id;
  public T data;
  public String label;
  public String group;

  public OxVtx<K, T> set(K id, T data) {
    this.id = requireNonNull(id);
    this.data = requireNonNull(data);
    return this;
  }

  public OxVtx<K, T> label(String label) {
    this.label = requireNonNull(label);
    return this;
  }

  public OxVtx<K, T> group(String group) {
    this.group = requireNonNull(group);
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
