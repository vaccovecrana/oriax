package io.vacco.oriax.core;

import static java.util.Objects.requireNonNull;

public class OxEdg<K, T> implements Comparable<OxEdg<K, T>> {

  public OxVtx<K, T> src, dst;
  public Object srcData, dstData;
  public String id, label;

  public OxEdg<K, T> set(OxVtx<K, T> src, OxVtx<K, T> dst) {
    this.src = requireNonNull(src);
    this.dst = requireNonNull(dst);
    this.id = String.format("%s%s", this.src.id, this.dst.id);
    return this;
  }

  public OxEdg<K, T> reverse() {
    return new OxEdg<K, T>().set(dst, src);
  }

  public OxEdg<K, T> id(String id) {
    this.id = requireNonNull(id);
    return this;
  }

  public OxEdg<K, T> label(String label) {
    this.label = label;
    return this;
  }

  public OxEdg<K, T> srcData(Object srcData) {
    this.srcData = requireNonNull(srcData);
    return this;
  }

  public OxEdg<K, T> dstData(Object dstData) {
    this.dstData = requireNonNull(dstData);
    return this;
  }

  @Override
  public int compareTo(OxEdg<K, T> tEdg) {
    return id.compareTo(tEdg.id);
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof OxEdg && this.id.equals(((OxEdg<?, ?>) o).id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    return String.format("[%s -%s-> %s]", src.id, label != null ? label : "", dst.id);
  }

}
