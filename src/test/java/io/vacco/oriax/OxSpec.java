package io.vacco.oriax;

import io.vacco.oriax.alg.*;
import io.vacco.oriax.core.*;
import io.vacco.oriax.util.OxTgf;
import j8spec.annotation.DefinedOrder;
import j8spec.junit.J8SpecRunner;
import org.junit.runner.RunWith;
import java.io.PrintStream;
import java.util.*;
import java.util.stream.*;

import static j8spec.J8Spec.*;
import static org.junit.Assert.*;

@DefinedOrder
@RunWith(J8SpecRunner.class)
public class OxSpec {

  static {

    PrintStream out = System.out;

    OxVtx<Integer, Integer> v0 = new OxVtx<>(0, 0).withLabel("Zero");
    OxVtx<Integer, Integer> v1 = new OxVtx<>(1, 1).withLabel("One");
    OxVtx<Integer, Integer> v2 = new OxVtx<>(2, 2).withLabel("Two");
    OxVtx<Integer, Integer> v3 = new OxVtx<>(3, 3).withLabel("Three");
    OxVtx<Integer, Integer> v4 = new OxVtx<>(4, 4).withLabel("Four");
    OxVtx<Integer, Integer> v5 = new OxVtx<>(5, 5);
    OxVtx<Integer, Integer> v6 = new OxVtx<>(6, 6);
    OxVtx<Integer, Integer> v7 = new OxVtx<>(7, 7);
    OxVtx<Integer, Integer> v8 = new OxVtx<>(8, 8);
    OxVtx<Integer, Integer> v9 = new OxVtx<>(9, 9);
    OxVtx<Integer, Integer> v10 = new OxVtx<>(10, 10);
    OxVtx<Integer, Integer> v11 = new OxVtx<>(11, 11);
    OxVtx<Integer, Integer> v12 = new OxVtx<>(12, 12);

    assertNotEquals(v0, v1);
    System.out.println(v0.equals(999));

    OxGrph<Integer, Integer> g1 = new OxGrph<>();
    g1.addEdge(v0, v1).addEdge(v0, v5)
        .addEdge(v2, v0).addEdge(v2, v3)
        .addEdge(v3, v2).addEdge(v3, v5)
        .addEdge(v4, v2).addEdge(v4, v3)
        .addEdge(v5, v4)
        .addEdge(v6, v0).addEdge(v6, v4).addEdge(v6, v8).addEdge(v6, v9)
        .addEdge(v7, v6).addEdge(v7, v9)
        .addEdge(v8, v6)
        .addEdge(v9, v10).addEdge(v9, v11)
        .addEdge(v10, v12)
        .addEdge(v11, v12).addEdge(v11, v4)
        .addEdge(v12, v9);

    List<OxEdg<Integer, Integer>> edges = g1.edg.stream().collect(Collectors.toList());
    assertEquals(edges.get(0), edges.get(0));
    assertNotEquals(edges.get(0), edges.get(1));

    System.out.println(edges.get(0).hashCode());
    System.out.println(edges.get(0).equals(999));

    g1.edg.iterator().next().withLabel("Test");

    describe("Graph dependency traversal", () -> {
      it("Can find strongly connected nodes (Kosaraju-Sharir)", () -> {
        for (OxEdg<Integer, Integer> e : g1.edg) {
          out.println(e);
        }
        int[] level = new int[]{0};
        OxDfs.apply(
            g1.reverse(),
            v -> {
              String sep = IntStream.range(0, level[0]).mapToObj(i -> " ").collect(Collectors.joining(""));
              out.printf("%sdfs(%s)%n", sep, v.id);
              level[0] = level[0] + 1;
            },
            v -> {
              String sep = IntStream.range(0, level[0]).mapToObj(i -> " ").collect(Collectors.joining(""));
              out.printf("%s%s done%n", sep, v.id);
              level[0] = level[0] - 1;
            }
        );

        Map<Integer, List<OxVtx<Integer, Integer>>> levels = OxKos.apply(g1);
        levels.forEach((key, value) -> out.printf("%s => %s%n", key, value));

        assertEquals(1, levels.get(0).size());
        assertTrue(levels.get(0).contains(v1));

        assertEquals(5, levels.get(1).size());
        assertTrue(levels.get(1).contains(v0));
        assertTrue(levels.get(1).contains(v2));
        assertTrue(levels.get(1).contains(v3));
        assertTrue(levels.get(1).contains(v4));
        assertTrue(levels.get(1).contains(v5));

        assertEquals(4, levels.get(2).size());
        assertTrue(levels.get(2).contains(v9));
        assertTrue(levels.get(2).contains(v10));
        assertTrue(levels.get(2).contains(v11));
        assertTrue(levels.get(2).contains(v12));

        assertEquals(2, levels.get(3).size());
        assertTrue(levels.get(3).contains(v6));
        assertTrue(levels.get(3).contains(v8));

        assertEquals(1, levels.get(4).size());
        assertTrue(levels.get(4).contains(v7));
      });

      it("Can output to TGF", () -> out.println(OxTgf.apply(g1)));
    });
  }
}
