package net.parostroj.timetable.model.imports;

import net.parostroj.timetable.model.LineClass;
import net.parostroj.timetable.model.ObjectWithId;
import net.parostroj.timetable.model.TrainDiagram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Import line classes.
 *
 * @author jub
 */
public class LineClassImport extends Import {

    private static final Logger log = LoggerFactory.getLogger(LineClassImport.class);

    public LineClassImport(TrainDiagram diagram, TrainDiagram libraryDiagram, ImportMatch match) {
        super(diagram, libraryDiagram, match);
    }

    @Override
    protected ObjectWithId importObjectImpl(ObjectWithId o) {
        // check class
        if (!(o instanceof LineClass))
            return null;
        LineClass importedLineClass = (LineClass)o;

        // check existence
        LineClass checkedLineClass = this.getLineClass(importedLineClass);
        if (checkedLineClass != null) {
            String message = "line class already exists";
            this.addError(importedLineClass, message);
            log.debug("{}: {}", message, checkedLineClass);
            return null;
        }

        // create new line class
        LineClass lineClass = new LineClass(this.getId(importedLineClass), importedLineClass.getName());

        // add to diagram
        this.getDiagram().getNet().addLineClass(lineClass);
        this.addImportedObject(lineClass);
        log.trace("Successfully imported line class: " + lineClass);
        return lineClass;
    }
}
