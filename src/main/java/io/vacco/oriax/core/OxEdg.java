package io.vacco.oriax.core;

import static java.util.Objects.requireNonNull;

public class OxEdg<K, T> implements Comparable<OxEdg<K, T>> {

  public final OxVtx<K, T> src;
  public final OxVtx<K, T> dst;
  public String label;

  public OxEdg(OxVtx<K, T> src, OxVtx<K, T> dst) {
    this.src = requireNonNull(src);
    this.dst = requireNonNull(dst);
  }

  public OxEdg<K, T> reverse() {
    return new OxEdg<>(dst, src);
  }

  public OxEdg<K, T> withLabel(String label) {
    this.label = requireNonNull(label);
    return this;
  }

  private String id() {
    return String.format("%s%s", this.src.id, this.dst.id);
  }

  @Override public int compareTo(OxEdg<K, T> tEdg) {
    return id().compareTo(tEdg.id());
  }

  @Override public boolean equals(Object o) {
    return o instanceof OxEdg && this.id().equals(((OxEdg<?, ?>) o).id());
  }

  @Override public int hashCode() { return id().hashCode(); }

  @Override public String toString() {
    return String.format("[%s -%s-> %s]", src.id, label != null ? label : "", dst.id);
  }
}
