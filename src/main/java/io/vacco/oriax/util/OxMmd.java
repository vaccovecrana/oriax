package io.vacco.oriax.util;

import io.vacco.oriax.core.OxGrph;
import io.vacco.oriax.core.OxVtx;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class OxMmd {

  private static String sanitizeId(String id) {
    return id.replaceAll("[^a-zA-Z0-9_]", "_");
  }

  public static String apply(OxGrph<?, ?> g) {
    var sb = new StringBuilder();
    sb.append("flowchart TD\n");

    var grouped = new LinkedHashMap<String, List<OxVtx<?, ?>>>();
    var ungrouped = new ArrayList<OxVtx<?, ?>>();

    for (var vtx : g.vtx) {
      if (vtx.group != null) {
        grouped.computeIfAbsent(vtx.group, k -> new ArrayList<>()).add(vtx);
      } else {
        ungrouped.add(vtx);
      }
    }

    for (var vtx : ungrouped) {
      sb.append("    ").append(sanitizeId(vtx.id.toString()));
      if (vtx.label != null) {
        sb.append("[").append(vtx.label).append("]");
      }
      sb.append("\n");
    }

    for (var entry : grouped.entrySet()) {
      var groupId = sanitizeId(entry.getKey());
      sb.append("    \n");
      sb.append("    subgraph ").append(groupId).append(" [").append(entry.getKey()).append("]\n");
      for (var vtx : entry.getValue()) {
        sb.append("        ").append(sanitizeId(vtx.id.toString()));
        if (vtx.label != null) {
          sb.append("[").append(vtx.label).append("]");
        }
        sb.append("\n");
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
