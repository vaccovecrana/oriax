package io.vacco.oriax.alg;

import io.vacco.oriax.core.OxGrph;
import io.vacco.oriax.core.OxVtx;

import java.util.*;
import java.util.function.Consumer;

public class OxDfs {

  public static <T> void apply(OxVtx<T> v, OxGrph<T> g, Set<OxVtx<T>> visited,
                               Consumer<OxVtx<T>> preCn, Consumer<OxVtx<T>> postCn) {
    if (!visited.contains(v)) {
      visited.add(v);
      if (preCn != null) preCn.accept(v);
      g.edg.stream()
          .filter(e -> e.src.equals(v)).map(e -> e.dst)
          .forEach(ev -> apply(ev, g, visited, preCn, postCn));
      if (postCn != null) postCn.accept(v);
    }
  }

  public static <T> void apply(OxGrph<T> graph, Consumer<OxVtx<T>> preCn, Consumer<OxVtx<T>> postCn) {
    Set<OxVtx<T>> visited = new HashSet<>();
    for (OxVtx<T> vtx : graph.vtx) {
      apply(vtx, graph, visited, preCn, postCn);
    }
  }
}
