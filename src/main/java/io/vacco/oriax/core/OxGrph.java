package io.vacco.oriax.core;

import java.util.*;
import static java.util.stream.Collectors.*;

public class OxGrph<T> {

  public final Set<OxVtx<T>> vtx = new LinkedHashSet<>();
  public final Set<OxEdg<T>> edg = new TreeSet<>();

  public OxGrph<T> addEdge(OxVtx<T> from, OxVtx<T> to) {
    this.vtx.add(from);
    this.vtx.add(to);
    OxEdg<T> e = new OxEdg<>(from, to);
    edg.add(e);
    return this;
  }

  public OxGrph<T> reverse() {
    OxGrph<T> g0 = new OxGrph<>();
    g0.vtx.addAll(this.vtx);
    g0.edg.addAll(this.edg.stream().map(OxEdg::reverse).collect(toList()));
    return g0;
  }
}
