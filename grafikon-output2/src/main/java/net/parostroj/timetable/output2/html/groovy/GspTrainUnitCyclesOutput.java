package net.parostroj.timetable.output2.html.groovy;

import groovy.text.Template;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.parostroj.timetable.model.TextTemplate;
import net.parostroj.timetable.model.TrainDiagram;
import net.parostroj.timetable.output2.DefaultOutputParam;
import net.parostroj.timetable.output2.OutputException;
import net.parostroj.timetable.output2.OutputParams;
import net.parostroj.timetable.output2.impl.TrainUnitCycle;
import net.parostroj.timetable.output2.impl.TrainUnitCyclesExtractor;
import net.parostroj.timetable.output2.util.ResourceHelper;
import net.parostroj.timetable.output2.util.SelectionHelper;

/**
 * Implements html output for train unit cycles.
 *
 * @author jub
 */
public class GspTrainUnitCyclesOutput extends GspOutput {

    public GspTrainUnitCyclesOutput(Locale locale) {
        super(locale);
    }

    @Override
    protected void writeTo(OutputParams params, OutputStream stream, TrainDiagram diagram) throws OutputException {
        try {
            // extract positions
            TrainUnitCyclesExtractor tuce = new TrainUnitCyclesExtractor(SelectionHelper.selectCycles(params, diagram, diagram.getTrainUnitCycleType()), getLocale());
            List<TrainUnitCycle> cycles = tuce.getTrainUnitCycles();

            // call template
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("cycles", cycles);
            ResourceHelper.addTextsToMap(map, "tuc_", this.getLocale(), "texts/html_texts");
            this.addContext(params, map);

            if (params.paramExistWithValue(DefaultOutputParam.TEXT_TEMPLATE)) {
                TextTemplate textTemplate = params.getParam(DefaultOutputParam.TEXT_TEMPLATE).getValue(TextTemplate.class);
                textTemplate.evaluate(stream, map, this.getEncoding(params));
            } else {
                Template template = this.getTemplate(params, "templates/groovy/train_unit_cycles.gsp", this.getClass().getClassLoader());
                this.writeOutput(stream, template, map, this.getEncoding(params));
            }
        } catch (OutputException e) {
            throw e;
        } catch (Exception e) {
            throw new OutputException(e);
        }
    }
}
