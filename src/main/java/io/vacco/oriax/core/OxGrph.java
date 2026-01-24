package io.vacco.oriax.core;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import static java.util.stream.Collectors.toList;

public class OxGrph<K, T> {

  public Set<OxVtx<K, T>> vtx = new LinkedHashSet<>();
  public Set<OxEdg<K, T>> edg = new TreeSet<>();

  public OxGrph<K, T> edge(OxVtx<K, T> from, OxVtx<K, T> to, String id, String label) {
    this.vtx.add(from);
    this.vtx.add(to);
    var e = new OxEdg<K, T>().label(label).set(from, to);
    e = id != null ? e.id(id) : e;
    edg.add(e);
    return this;
  }

  public OxGrph<K, T> edge(OxVtx<K, T> from, OxVtx<K, T> to) {
    return edge(from, to, null, null);
  }

  public OxGrph<K, T> reverse() {
    var g0 = new OxGrph<K, T>();
    g0.vtx.addAll(this.vtx);
    g0.edg.addAll(this.edg.stream().map(OxEdg::reverse).collect(toList()));
    return g0;
  }

}
