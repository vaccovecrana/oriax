package io.vacco.oriax;

import io.vacco.oriax.alg.OxDfs;
import io.vacco.oriax.alg.OxKos;
import io.vacco.oriax.core.OxEdg;
import io.vacco.oriax.core.OxGrph;
import io.vacco.oriax.core.OxVtx;
import io.vacco.oriax.util.OxTgf;
import j8spec.annotation.DefinedOrder;
import j8spec.junit.J8SpecRunner;
import org.junit.runner.RunWith;
import java.io.PrintStream;
import java.util.stream.*;

import static j8spec.J8Spec.*;

@DefinedOrder
@RunWith(J8SpecRunner.class)
public class OxSpec {

  static {

    PrintStream out = System.out;

    OxVtx<Integer> v0 = new OxVtx<>("0", 0).withLabel("Zero");
    OxVtx<Integer> v1 = new OxVtx<>("1", 1).withLabel("One");
    OxVtx<Integer> v2 = new OxVtx<>("2", 2).withLabel("Two");
    OxVtx<Integer> v3 = new OxVtx<>("3", 3).withLabel("Three");
    OxVtx<Integer> v4 = new OxVtx<>("4", 4).withLabel("Four");
    OxVtx<Integer> v5 = new OxVtx<>("5", 5);
    OxVtx<Integer> v6 = new OxVtx<>("6", 6);
    OxVtx<Integer> v7 = new OxVtx<>("7", 7);
    OxVtx<Integer> v8 = new OxVtx<>("8", 8);
    OxVtx<Integer> v9 = new OxVtx<>("9", 9);
    OxVtx<Integer> v10 = new OxVtx<>("10", 10);
    OxVtx<Integer> v11 = new OxVtx<>("11", 11);
    OxVtx<Integer> v12 = new OxVtx<>("12", 12);

    OxGrph<Integer> g1 = new OxGrph<>();
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

    g1.edg.iterator().next().withLabel("Test");

    describe("Graph dependency traversal", () -> {
      it("Can find strongly connected nodes (Kosaraju-Sharir)", () -> {
        for (OxEdg<Integer> e : g1.edg) {
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
        OxKos.apply(g1).forEach((key, value) -> out.printf("%s => %s%n", key, value));
      });

      it("Can output to TGF", () -> {
        out.println(OxTgf.apply(g1));
      });
    });
  }
}
