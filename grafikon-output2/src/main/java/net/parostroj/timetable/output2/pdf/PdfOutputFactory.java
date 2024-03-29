package net.parostroj.timetable.output2.pdf;

import java.lang.reflect.Constructor;
import java.util.*;

import net.parostroj.timetable.output2.Output;
import net.parostroj.timetable.output2.OutputException;
import net.parostroj.timetable.output2.OutputFactory;

/**
 * Pdf output factory. Uses xsl-fo for creating the output.
 *
 * @author jub
 */
public class PdfOutputFactory extends OutputFactory {

    private static final String TYPE = "pdf";

    private static final Map<String, Class<? extends Output>> OUTPUT_TYPES;

    static {
        OUTPUT_TYPES = new HashMap<String, Class<? extends Output>>();
        OUTPUT_TYPES.put("starts", PdfStartPositionsOutput.class);
//        OUTPUT_TYPES.put("ends", GspEndPositionsOutput.class);
//        OUTPUT_TYPES.put("stations", GspStationTimetablesOutput.class);
//        OUTPUT_TYPES.put("train_unit_cycles", GspTrainUnitCyclesOutput.class);
//        OUTPUT_TYPES.put("engine_cycles", GspEngineCyclesOutput.class);
//        OUTPUT_TYPES.put("driver_cycles", GspDriverCyclesOutput.class);
//        OUTPUT_TYPES.put("trains", GspTrainTimetablesOutput.class);
//        OUTPUT_TYPES.put("diagram", GspDiagramOutput.class);
//        OUTPUT_TYPES.put("custom_cycles", GspCustomCyclesOutput.class);
    }

    public PdfOutputFactory() {
    }

    private Locale getLocale() {
        Locale locale = (Locale) this.getParameter("locale");
        if (locale == null)
            locale = Locale.getDefault();
        return locale;
    }

    @Override
    public Set<String> getOutputTypes() {
        return OUTPUT_TYPES.keySet();
    }

    @Override
    public Output createOutput(String type) throws OutputException {
        Class<? extends Output> outputClass = OUTPUT_TYPES.get(type);
        if (outputClass == null)
            throw new OutputException("Unknown type: " + type);
        try {
            Constructor<? extends Output> constructor = outputClass.getConstructor(Locale.class);
            return constructor.newInstance(this.getLocale());
        } catch (Exception e) {
            throw new OutputException(e);
        }
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
