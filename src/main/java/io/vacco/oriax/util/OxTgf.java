package io.vacco.oriax.util;

import io.vacco.oriax.core.*;

public class OxTgf {

  public static String apply(OxGrph<?, ?> g) {
    StringBuilder sb = new StringBuilder();
    for (OxVtx<?, ?> vtx : g.vtx) {
      sb.append(vtx.id).append(" ");
      sb.append(vtx.label == null ? vtx.id : vtx.label);
      sb.append("\n");
    }
    sb.append("#").append("\n");
    for (OxEdg<?, ?> e : g.edg) {
      sb.append(e.src.id).append(" ").append(e.dst.id);
      if (e.label != null) {
        sb.append(" ").append(e.label);
      }
      sb.append("\n");
    }
    return sb.toString();
  }

}
