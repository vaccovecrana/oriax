package io.vacco.oriax;

import io.vacco.oriax.alg.OxDfs;
import io.vacco.oriax.alg.OxKos;
import io.vacco.oriax.core.OxEdg;
import io.vacco.oriax.core.OxGrph;
import io.vacco.oriax.core.OxVtx;
import io.vacco.oriax.util.OxMmd;
import io.vacco.oriax.util.OxTgf;
import j8spec.annotation.DefinedOrder;
import j8spec.junit.J8SpecRunner;
import org.junit.runner.RunWith;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static j8spec.J8Spec.describe;
import static j8spec.J8Spec.it;
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
    out.println(v0.equals(999));

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

    List<OxEdg<Integer, Integer>> edges = g1.edg.stream().toList();
    assertEquals(edges.get(0), edges.get(0));
    assertNotEquals(edges.get(0), edges.get(1));

    out.println(edges.get(0).hashCode());
    out.println(edges.get(0).equals(999));

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

    });

    describe("TGF rendering", () -> {
      it("Can output to TGF", () -> out.println(OxTgf.apply(g1)));

      it("Verifies TGF still works with groups", () -> {
        OxVtx<Integer, Integer> tv0 = new OxVtx<>(0, 0).withLabel("Zero").withGroup("TestGroup");
        OxVtx<Integer, Integer> tv1 = new OxVtx<>(1, 1).withLabel("One").withGroup("TestGroup");

        OxGrph<Integer, Integer> gt = new OxGrph<>();
        gt.addEdge(tv0, tv1);

        out.println("\n=== TGF with groups (should ignore groups) ===");
        String tgf = OxTgf.apply(gt);
        out.println(tgf);
        assertNotNull(tgf);
        assertFalse(tgf.contains("TestGroup"));
      });
    });

    describe("Mermaid rendering", () -> {
      it("Can render graph without groups", () -> {
        out.println("\n=== Mermaid without groups ===");
        String mmd = OxMmd.apply(g1);
        out.println(mmd);
        assertNotNull(mmd);
        assertTrue(mmd.startsWith("flowchart TD"));
      });

      it("Can render graph with groups", () -> {
        OxGrph<Integer, Integer> g2 = new OxGrph<>();

        OxVtx<Integer, Integer> gv0 = new OxVtx<>(0, 0).withLabel("Zero").withGroup("Core");
        OxVtx<Integer, Integer> gv1 = new OxVtx<>(1, 1).withLabel("One").withGroup("Core");
        OxVtx<Integer, Integer> gv2 = new OxVtx<>(2, 2).withLabel("Two").withGroup("Core");
        OxVtx<Integer, Integer> gv3 = new OxVtx<>(3, 3).withLabel("Three").withGroup("Peripheral");
        OxVtx<Integer, Integer> gv4 = new OxVtx<>(4, 4).withLabel("Four").withGroup("Peripheral");
        OxVtx<Integer, Integer> gv5 = new OxVtx<>(5, 5).withLabel("Five");

        g2.addEdge(gv0, gv1).addEdge(gv1, gv2)
          .addEdge(gv2, gv3).addEdge(gv3, gv4)
          .addEdge(gv4, gv5).addEdge(gv5, gv0);

        out.println("\n=== Mermaid with groups ===");
        String mmd = OxMmd.apply(g2);
        out.println(mmd);
        assertNotNull(mmd);
        assertTrue(mmd.contains("subgraph Core"));
        assertTrue(mmd.contains("subgraph Peripheral"));
      });

      it("Can render edges with labels", () -> {
        OxGrph<Integer, Integer> g3 = new OxGrph<>();

        OxVtx<Integer, Integer> ev0 = new OxVtx<>(0, 0).withLabel("Start");
        OxVtx<Integer, Integer> ev1 = new OxVtx<>(1, 1).withLabel("Middle");
        OxVtx<Integer, Integer> ev2 = new OxVtx<>(2, 2).withLabel("End");

        g3.addEdge(ev0, ev1);
        g3.addEdge(ev1, ev2);
        g3.edg.stream().filter(e -> e.src.equals(ev0)).findFirst().get().withLabel("init");
        g3.edg.stream().filter(e -> e.src.equals(ev1)).findFirst().get().withLabel("finalize");

        out.println("\n=== Mermaid with edge labels ===");
        String mmd = OxMmd.apply(g3);
        out.println(mmd);
        assertNotNull(mmd);
        assertTrue(mmd.contains("|init|"));
        assertTrue(mmd.contains("|finalize|"));
      });
    });

  }

}
