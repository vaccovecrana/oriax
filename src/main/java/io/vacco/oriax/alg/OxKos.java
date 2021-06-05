package io.vacco.oriax.alg;

import io.vacco.oriax.core.OxGrph;
import io.vacco.oriax.core.OxVtx;

import java.util.*;

/**
 * <a href="https://www.cs.princeton.edu/courses/archive/fall12/cos226/lectures/42DirectedGraphs.pdf">42DirectedGraphs.pdf</a>
 */
public class OxKos {

  public static <K, T> Map<Integer, List<OxVtx<K, T>>> apply(OxGrph<K, T> g) {
    List<OxVtx<K, T>> rpo = new ArrayList<>();
    OxDfs.apply(g.reverse(), null, rpo::add);
    Collections.reverse(rpo);

    final int[] count = {0};
    Set<OxVtx<K, T>> visited = new HashSet<>();
    Map<Integer, List<OxVtx<K, T>>> levels = new HashMap<>();

    for (OxVtx<K, T> vtx : rpo) {
      if (!visited.contains(vtx)) {
        OxDfs.apply(vtx, g, visited,
            v0 -> levels.computeIfAbsent(count[0], c -> new ArrayList<>()).add(v0), null
        );
        count[0] = count[0] + 1;
      }
    }
    return levels;
  }
}
