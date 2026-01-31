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

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static j8spec.J8Spec.describe;
import static j8spec.J8Spec.it;
import static org.junit.Assert.*;

@DefinedOrder
@RunWith(J8SpecRunner.class)
public class OxTest {

  static {

    var out = System.out;

    var v0 = new OxVtx<Integer, Integer>().set(0, 0).label("Zero");
    var v1 = new OxVtx<Integer, Integer>().set(1, 1).label("One");
    var v2 = new OxVtx<Integer, Integer>().set(2, 2).label("Two");
    var v3 = new OxVtx<Integer, Integer>().set(3, 3).label("Three");
    var v4 = new OxVtx<Integer, Integer>().set(4, 4).label("Four");
    var v5 = new OxVtx<Integer, Integer>().set(5, 5);
    var v6 = new OxVtx<Integer, Integer>().set(6, 6);
    var v7 = new OxVtx<Integer, Integer>().set(7, 7);
    var v8 = new OxVtx<Integer, Integer>().set(8, 8);
    var v9 = new OxVtx<Integer, Integer>().set(9, 9);
    var v10 = new OxVtx<Integer, Integer>().set(10, 10);
    var v11 = new OxVtx<Integer, Integer>().set(11, 11);
    var v12 = new OxVtx<Integer, Integer>().set(12, 12);

    assertNotEquals(v0, v1);
    out.println(v0.equals(999));

    var g1 = new OxGrph<Integer, Integer>();
    g1.edge(v0, v1).edge(v0, v5)
      .edge(v2, v0).edge(v2, v3)
      .edge(v3, v2).edge(v3, v5)
      .edge(v4, v2).edge(v4, v3)
      .edge(v5, v4)
      .edge(v6, v0).edge(v6, v4).edge(v6, v8).edge(v6, v9)
      .edge(v7, v6).edge(v7, v9)
      .edge(v8, v6)
      .edge(v9, v10).edge(v9, v11)
      .edge(v10, v12)
      .edge(v11, v12).edge(v11, v4)
      .edge(v12, v9);

    var edges = g1.edg.stream().toList();
    assertEquals(edges.get(0), edges.get(0));
    assertNotEquals(edges.get(0), edges.get(1));

    out.println(edges.get(0).hashCode());
    out.println(edges.get(0).equals(999));

    g1.edg.iterator().next().label("Test");

    describe("Graph dependency traversal", () -> {
      it("Can find strongly connected nodes (Kosaraju-Sharir)", () -> {
        for (OxEdg<Integer, Integer> e : g1.edg) {
          out.println(e);
        }
        int[] level = new int[]{0};
        OxDfs.apply(
          g1.reverse(),
          v -> {
            var sep = IntStream.range(0, level[0]).mapToObj(i -> " ").collect(Collectors.joining(""));
            out.printf("%sdfs(%s)%n", sep, v.id);
            level[0] = level[0] + 1;
          },
          v -> {
            var sep = IntStream.range(0, level[0]).mapToObj(i -> " ").collect(Collectors.joining(""));
            out.printf("%s%s done%n", sep, v.id);
            level[0] = level[0] - 1;
          }
        );

        var levels = OxKos.apply(g1);
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
        var tv0 = new OxVtx<Integer, Integer>().set(0, 0).label("Zero").group0("TestGroup");
        var tv1 = new OxVtx<Integer, Integer>().set(1, 1).label("One").group0("TestGroup");

        OxGrph<Integer, Integer> gt = new OxGrph<>();
        gt.edge(tv0, tv1);

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

        var gv0 = new OxVtx<Integer, Integer>().set(0, 0).label("Zero").group0("Core");
        var gv1 = new OxVtx<Integer, Integer>().set(1, 1).label("One").group0("Core");
        var gv2 = new OxVtx<Integer, Integer>().set(2, 2).label("Two").group0("Core");
        var gv3 = new OxVtx<Integer, Integer>().set(3, 3).label("Three").group0("Peripheral");
        var gv4 = new OxVtx<Integer, Integer>().set(4, 4).label("Four").group0("Peripheral");
        var gv5 = new OxVtx<Integer, Integer>().set(5, 5).label("Five");

        g2.edge(gv0, gv1).edge(gv1, gv2)
          .edge(gv2, gv3).edge(gv3, gv4)
          .edge(gv4, gv5).edge(gv5, gv0);

        out.println("\n=== Mermaid with groups ===");
        var mmd = OxMmd.apply(g2);
        out.println(mmd);
        assertNotNull(mmd);
        assertTrue(mmd.contains("subgraph Core"));
        assertTrue(mmd.contains("subgraph Peripheral"));
      });

      it("Can render edges with labels", () -> {
        var g3 = new OxGrph<Integer, Integer>();

        var ev0 = new OxVtx<Integer, Integer>().set(0, 0).label("Start");
        var ev1 = new OxVtx<Integer, Integer>().set(1, 1).label("Middle");
        var ev2 = new OxVtx<Integer, Integer>().set(2, 2).label("End");

        g3.edge(ev0, ev1, edg -> edg.label("init<0> : init<1>"));
        g3.edge(ev1, ev2, edg -> edg.label("finalize.attr"));

        out.println("\n=== Mermaid with edge labels ===");
        var mmd = OxMmd.apply(g3);
        out.println(mmd);
        assertNotNull(mmd);
        assertTrue(mmd.contains("|init<0> : init<1>|"));
        assertTrue(mmd.contains("|finalize.attr|"));
      });

      it("Can render nested groups with group1 as parent", () -> {
        var g4 = new OxGrph<Integer, Integer>();

        var nv0 = new OxVtx<Integer, Integer>().set(0, 0).label("Backend-API-1").group1("Backend").group0("API");
        var nv1 = new OxVtx<Integer, Integer>().set(1, 1).label("Backend-API-2").group1("Backend").group0("API");
        var nv2 = new OxVtx<Integer, Integer>().set(2, 2).label("Backend-DB-1").group1("Backend").group0("Database");
        var nv3 = new OxVtx<Integer, Integer>().set(3, 3).label("Backend-DB-2").group1("Backend").group0("Database");
        var nv4 = new OxVtx<Integer, Integer>().set(4, 4).label("Frontend-UI-1").group1("Frontend").group0("UI");
        var nv5 = new OxVtx<Integer, Integer>().set(5, 5).label("Frontend-UI-2").group1("Frontend").group0("UI");
        var nv6 = new OxVtx<Integer, Integer>().set(6, 6).label("Frontend-Logic").group1("Frontend");
        var nv7 = new OxVtx<Integer, Integer>().set(7, 7).label("Standalone").group0("Utils");
        var nv8 = new OxVtx<Integer, Integer>().set(8, 8).label("Orphan");

        g4.edge(nv4, nv0).edge(nv5, nv1)
          .edge(nv0, nv2).edge(nv1, nv3)
          .edge(nv6, nv4).edge(nv6, nv5)
          .edge(nv7, nv0).edge(nv8, nv4);

        out.println("\n=== Mermaid with nested groups (group1 as parent) ===");
        var mmd = OxMmd.apply(g4);
        out.println(mmd);
        assertNotNull(mmd);
        assertTrue(mmd.contains("subgraph Backend"));
        assertTrue(mmd.contains("subgraph Frontend"));
        assertTrue(mmd.contains("subgraph API"));
        assertTrue(mmd.contains("subgraph Database"));
        assertTrue(mmd.contains("subgraph UI"));
        assertTrue(mmd.contains("subgraph Utils"));
      });
    });

  }

}
