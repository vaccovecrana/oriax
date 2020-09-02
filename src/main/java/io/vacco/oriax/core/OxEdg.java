package io.vacco.oriax.core;

import java.util.Objects;

public class OxEdg<T> implements Comparable<OxEdg<T>> {

  public final OxVtx<T> src;
  public final OxVtx<T> dst;

  public OxEdg(OxVtx<T> src, OxVtx<T> dst) {
    this.src = Objects.requireNonNull(src);
    this.dst = Objects.requireNonNull(dst);
  }

  public OxEdg<T> reverse() {
    return new OxEdg<>(dst, src);
  }

  private String label() {
    return String.format("%s%s", this.src.id, this.dst.id);
  }

  @Override public int compareTo(OxEdg<T> tEdg) {
    return label().compareTo(tEdg.label());
  }

  @Override public String toString() {
    return String.format("[%s -> %s]", src.id, dst.id);
  }
}
