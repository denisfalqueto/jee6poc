package br.jus.trt3.poc.jee6.rest.application.config;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

/**
 *
 * @author alexadb
 */
public abstract class LogMarker {
    public static final Marker DATABASE = MarkerManager.getMarker("DATABASE");
    public static final Marker DB_UPDATE = MarkerManager.getMarker("DB_UPDATE").setParents(DATABASE);
    public static final Marker DB_QUERY = MarkerManager.getMarker("DB_QUERY").setParents(DATABASE);
    public static final Marker DB_INSERT = MarkerManager.getMarker("DB_INSERT").setParents(DATABASE);
    public static final Marker DB_DELETE = MarkerManager.getMarker("DB_DELETE").setParents(DATABASE);
}
