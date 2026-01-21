package io.vacco.oriax.alg;

import io.vacco.oriax.core.OxGrph;
import io.vacco.oriax.core.OxVtx;

import java.util.*;

/**
 * <a href="https://www.cs.princeton.edu/courses/archive/fall12/cos226/lectures/42DirectedGraphs.pdf">42DirectedGraphs.pdf</a>
 */
public class OxKos {

  public static <K, T> Map<Integer, List<OxVtx<K, T>>> apply(OxGrph<K, T> g) {
    var rpo = new ArrayList<OxVtx<K, T>>();
    OxDfs.apply(g.reverse(), null, rpo::add);
    Collections.reverse(rpo);

    final int[] count = {0};
    var visited = new HashSet<OxVtx<K, T>>();
    var levels = new HashMap<Integer, List<OxVtx<K, T>>>();

    for (var vtx : rpo) {
      if (!visited.contains(vtx)) {
        OxDfs.apply(vtx, g, visited,
          v0 -> levels
            .computeIfAbsent(count[0], c -> new ArrayList<>())
            .add(v0), null
        );
        count[0] = count[0] + 1;
      }
    }
    return levels;
  }

}
