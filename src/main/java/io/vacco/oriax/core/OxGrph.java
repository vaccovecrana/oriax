package io.vacco.oriax.core;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;

import static java.util.stream.Collectors.toList;

public class OxGrph<K, T> {

  public Set<OxVtx<K, T>> vtx = new LinkedHashSet<>();
  public Set<OxEdg<K, T>> edg = new TreeSet<>();

  public OxGrph<K, T> edge(OxVtx<K, T> from, OxVtx<K, T> to, Consumer<OxEdg<K, T>> onCreate) {
    this.vtx.add(from);
    this.vtx.add(to);
    var e = new OxEdg<K, T>().set(from, to);
    if (onCreate != null) {
      onCreate.accept(e);
    }
    edg.add(e);
    return this;
  }

  public OxGrph<K, T> edge(OxVtx<K, T> from, OxVtx<K, T> to) {
    return edge(from, to, null);
  }

  public OxGrph<K, T> reverse() {
    var g0 = new OxGrph<K, T>();
    g0.vtx.addAll(this.vtx);
    g0.edg.addAll(this.edg.stream().map(OxEdg::reverse).collect(toList()));
    return g0;
  }

  public List<OxEdg<K, T>> incoming(OxVtx<K, T> v) {
    return edg.stream().filter(e -> e.dst.equals(v)).collect(toList());
  }

  public List<OxEdg<K, T>> outgoing(OxVtx<K, T> v) {
    return edg.stream().filter(e -> e.src.equals(v)).collect(toList());
  }

}
