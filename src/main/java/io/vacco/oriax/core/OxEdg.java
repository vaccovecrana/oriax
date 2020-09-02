package io.vacco.oriax.core;

import static java.util.Objects.requireNonNull;

public class OxEdg<T> implements Comparable<OxEdg<T>> {

  public final OxVtx<T> src;
  public final OxVtx<T> dst;
  public String label;

  public OxEdg(OxVtx<T> src, OxVtx<T> dst) {
    this.src = requireNonNull(src);
    this.dst = requireNonNull(dst);
  }

  public OxEdg<T> reverse() {
    return new OxEdg<>(dst, src);
  }

  public OxEdg<T> withLabel(String label) {
    this.label = requireNonNull(label);
    return this;
  }

  private String id() {
    return String.format("%s%s", this.src.id, this.dst.id);
  }

  @Override public int compareTo(OxEdg<T> tEdg) {
    return id().compareTo(tEdg.id());
  }

  @Override public String toString() {
    return String.format("[%s -%s-> %s]",
        src.id, label != null ? label : "", dst.id
    );
  }
}
