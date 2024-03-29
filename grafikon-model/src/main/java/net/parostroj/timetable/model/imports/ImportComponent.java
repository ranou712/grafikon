package net.parostroj.timetable.model.imports;

import java.util.*;

import net.parostroj.timetable.model.*;

/**
 * Export/Import components.
 *
 * @author jub
 */
public enum ImportComponent {
    NODES("import.stations", Node.class),
    LINE_CLASSES("import.line_classes", LineClass.class),
    LINES("import.lines", Line.class),
    ROUTES("import.routes", Route.class),
    TRAIN_TYPES("import.train_types", TrainType.class),
    ENGINE_CLASSES("import.engine_classes", EngineClass.class),
    TRAINS("import.trains", Train.class),
    TRAINS_CYCLE_TYPES("import.cycle_types", TrainsCycleType.class),
    TRAINS_CYCLES("import.cycles", TrainsCycle.class),
    OUTPUT_TEMPLATES("import.output_templates", OutputTemplate.class);

    private String key;
    private Class<?> clazz;

    private ImportComponent(String key, Class<?> clazz) {
        this.key = key;
        this.clazz = clazz;
    }

    public String getKey() {
        return key;
    }

    public Class<?> getComponentClass() {
        return clazz;
    }

    public Set<ObjectWithId> getObjects(TrainDiagram diagram) {
        if (diagram == null)
            return Collections.emptySet();
        Set<ObjectWithId> map = new LinkedHashSet<ObjectWithId>();
        switch (this) {
            case NODES:
                map.addAll(diagram.getNet().getNodes());
                break;
            case TRAINS:
                map.addAll(diagram.getTrains());
                break;
            case TRAIN_TYPES:
                map.addAll(diagram.getTrainTypes());
                break;
            case LINE_CLASSES:
                map.addAll(diagram.getNet().getLineClasses());
                break;
            case ENGINE_CLASSES:
                map.addAll(diagram.getEngineClasses());
                break;
            case OUTPUT_TEMPLATES:
                map.addAll(diagram.getOutputTemplates());
                break;
            case TRAINS_CYCLES:
                map.addAll(diagram.getCycles());
                break;
            case TRAINS_CYCLE_TYPES:
                for (TrainsCycleType type : diagram.getCycleTypes()) {
                    if (!TrainsCycleType.isDefaultType(type)) {
                        map.add(type);
                    }
                }
                break;
            case LINES:
                map.addAll(diagram.getNet().getLines());
                break;
            case ROUTES:
                map.addAll(diagram.getRoutes());
                break;
        }
        return map;
    }

    public boolean sorted() {
        return this == NODES || this == TRAINS || this == TRAINS_CYCLES || this == LINES || this == ROUTES;
    }

    public static ImportComponent getByComponentClass(Class<?> clazz) {
        for (ImportComponent comp : values()) {
            if (comp.getComponentClass().equals(clazz))
                return comp;
        }
        return null;
    }
}
