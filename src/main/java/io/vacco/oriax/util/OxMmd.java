package io.vacco.oriax.util;

import io.vacco.oriax.core.OxGrph;
import io.vacco.oriax.core.OxVtx;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OxMmd {

  private static String sanitizeId(String id) {
    return id.replaceAll("[^a-zA-Z0-9_]", "_");
  }

  private static void renderEntry(StringBuilder sb, Map.Entry<String, List<OxVtx<?, ?>>> g0Entry) {
    for (var vtx : g0Entry.getValue()) {
      sb.append("        ").append(sanitizeId(vtx.id.toString()));
      if (vtx.label != null) {
        sb.append("(").append(vtx.label).append(")");
      }
      sb.append("\n");
    }
  }

  public static String apply(OxGrph<?, ?> g) {
    var sb = new StringBuilder();
    sb.append("flowchart TD\n");

    var group1 = new LinkedHashMap<String, LinkedHashMap<String, List<OxVtx<?, ?>>>>();
    var group0 = new LinkedHashMap<String, List<OxVtx<?, ?>>>();
    var noGroup = new ArrayList<OxVtx<?, ?>>();

    for (var vtx : g.vtx) {
      if (vtx.g1 != null) {
        var g1 = group1.computeIfAbsent(vtx.g1, k -> new LinkedHashMap<>());
        if (vtx.g0 != null) {
          g1.computeIfAbsent(vtx.g0, k -> new ArrayList<>()).add(vtx);
        } else {
          g1.computeIfAbsent("", k -> new ArrayList<>()).add(vtx);
        }
      } else if (vtx.g0 != null) {
        group0.computeIfAbsent(vtx.g0, k -> new ArrayList<>()).add(vtx);
      } else {
        noGroup.add(vtx);
      }
    }

    for (var vtx : noGroup) {
      sb.append("    ").append(sanitizeId(vtx.id.toString()));
      if (vtx.label != null) {
        sb.append("(").append(vtx.label).append(")");
      }
      sb.append("\n");
    }

    for (var entry : group0.entrySet()) {
      var groupId = sanitizeId(entry.getKey());
      sb.append("    \n");
      sb.append("    subgraph ").append(groupId).append(" [").append(entry.getKey()).append("]\n");
      renderEntry(sb, entry);
      sb.append("    end\n");
    }

    for (var g1Entry : group1.entrySet()) {
      var group1Id = sanitizeId(g1Entry.getKey());
      sb.append("    \n");
      sb.append("    subgraph ").append(group1Id).append(" [").append(g1Entry.getKey()).append("]\n");

      for (var g0Entry : g1Entry.getValue().entrySet()) {
        if (g0Entry.getKey().isEmpty()) {
          renderEntry(sb, g0Entry);
        } else {
          var group0Id = sanitizeId(g0Entry.getKey());
          sb.append("        \n");
          sb.append("        subgraph ").append(group0Id).append(" [").append(g0Entry.getKey()).append("]\n");
          for (var vtx : g0Entry.getValue()) {
            sb.append("            ").append(sanitizeId(vtx.id.toString()));
            if (vtx.label != null) {
              sb.append("(").append(vtx.label).append(")");
            }
            sb.append("\n");
          }
          sb.append("        end\n");
        }
      }

      sb.append("    end\n");
    }

    sb.append("    \n");
    for (var e : g.edg) {
      sb.append("    ").append(sanitizeId(e.src.id.toString()));
      sb.append(" -->");
      if (e.label != null) {
        sb.append("|").append(e.label).append("|");
      }
      sb.append(" ").append(sanitizeId(e.dst.id.toString()));
      sb.append("\n");
    }

    return sb.toString();
  }

}
