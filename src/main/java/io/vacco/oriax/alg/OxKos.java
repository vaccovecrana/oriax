package io.vacco.oriax.alg;

import io.vacco.oriax.core.OxGrph;
import io.vacco.oriax.core.OxVtx;

import java.util.*;

/**
 * <a href="https://www.cs.princeton.edu/courses/archive/fall12/cos226/lectures/42DirectedGraphs.pdf" />
 */
public class OxKos {

  public static <T> Map<Integer, List<OxVtx<T>>> apply(OxGrph<T> g) {
    List<OxVtx<T>> rpo = new ArrayList<>();
    OxDfs.apply(g.reverse(), null, rpo::add);
    Collections.reverse(rpo);

    final int[] count = {0};
    Set<OxVtx<T>> visited = new HashSet<>();
    Map<Integer, List<OxVtx<T>>> levels = new HashMap<>();

    for (OxVtx<T> vr : rpo) {
      if (!visited.contains(vr)) {
        OxDfs.apply(vr, g, visited,
            v0 -> levels.computeIfAbsent(count[0], c -> new ArrayList<>()).add(v0),
            null);
        count[0] = count[0] + 1;
      }
    }
    return levels;
  }
}
