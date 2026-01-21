package io.vacco.oriax.alg;

import io.vacco.oriax.core.OxGrph;
import io.vacco.oriax.core.OxVtx;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class OxDfs {

  public static <K, T> void apply(OxVtx<K, T> v, OxGrph<K, T> g,
                                  Set<OxVtx<K, T>> visited,
                                  Consumer<OxVtx<K, T>> preConsumer,
                                  Consumer<OxVtx<K, T>> postConsumer) {
    if (!visited.contains(v)) {
      visited.add(v);
      if (preConsumer != null) preConsumer.accept(v);
      g.edg.stream()
        .filter(e -> e.src.equals(v)).map(e -> e.dst)
        .forEach(ev -> apply(ev, g, visited, preConsumer, postConsumer));
      if (postConsumer != null) postConsumer.accept(v);
    }
  }

  public static <K, T> void apply(OxGrph<K, T> graph,
                                  Consumer<OxVtx<K, T>> preConsumer,
                                  Consumer<OxVtx<K, T>> postConsumer) {
    var visited = new HashSet<OxVtx<K, T>>();
    for (var vtx : graph.vtx) {
      apply(vtx, graph, visited, preConsumer, postConsumer);
    }
  }

}
