package io.vacco.oriax.core;

import java.util.*;
import static java.util.stream.Collectors.*;

public class OxGrph<K, T> {

  public final Set<OxVtx<K, T>> vtx = new LinkedHashSet<>();
  public final Set<OxEdg<K, T>> edg = new TreeSet<>();

  public OxGrph<K, T> addEdge(OxVtx<K, T> from, OxVtx<K, T> to) {
    this.vtx.add(from);
    this.vtx.add(to);
    OxEdg<K, T> e = new OxEdg<>(from, to);
    edg.add(e);
    return this;
  }

  public OxGrph<K, T> reverse() {
    OxGrph<K, T> g0 = new OxGrph<>();
    g0.vtx.addAll(this.vtx);
    g0.edg.addAll(this.edg.stream().map(OxEdg::reverse).collect(toList()));
    return g0;
  }
}
