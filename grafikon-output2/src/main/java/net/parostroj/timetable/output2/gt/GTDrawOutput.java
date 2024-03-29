package net.parostroj.timetable.output2.gt;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.OutputStream;
import java.util.*;

import net.parostroj.timetable.model.TrainDiagram;
import net.parostroj.timetable.output2.OutputException;
import net.parostroj.timetable.output2.OutputParams;

/**
 * GTDraw output.
 *
 * @author jub
 */
public class GTDrawOutput extends DrawOutput {

    private final GTDrawFactory drawFactory;

    public GTDrawOutput(Locale locale) {
        super(locale);
        drawFactory = new NormalGTDrawFactory();
    }

    @Override
    protected void writeTo(OutputParams params, OutputStream stream, TrainDiagram diagram) throws OutputException {
        Collection<GTDraw> draws = this.getDraws(params);
        if (draws == null) {
            Collection<GTDrawParams> gtParamList = this.getParams(params, diagram);
            draws = new ArrayList<GTDraw>(gtParamList.size());
            for (GTDrawParams gtParams : gtParamList) {
                draws.add(drawFactory.createInstance(gtParams.getType(), gtParams.getSettings(), gtParams.getRoute(), new GTStorage()));
            }
        }
        this.draw(this.getFileOutputType(params), stream, draws, new DrawLayout(DrawLayout.Orientation.TOP_DOWN));
    }

    private Collection<GTDraw> getDraws(OutputParams params) {
        Collection<?> draws = params.getParamValue(GT_DRAWS, Collection.class);
        if (draws != null && !draws.isEmpty()) {
            return this.convert(draws, GTDraw.class);
        } else {
            return null;
        }
    }

    private Collection<GTDrawParams> getParams(OutputParams params, TrainDiagram diagram) throws OutputException {
        Collection<?> gtParamList = params.getParamValue(GT_PARAMS, Collection.class);
        if (gtParamList == null || gtParamList.isEmpty()) {
            // create default values
            if (diagram.getRoutes().isEmpty()) {
                throw new OutputException("Routes missing");
            }
            return Collections.singletonList(new GTDrawParams(diagram.getRoutes().get(0)));
        } else {
            return this.convert(gtParamList, GTDrawParams.class);
        }
    }

    private void draw(FileOutputType outputType, OutputStream stream, final Collection<GTDraw> draws, DrawLayout layout) throws OutputException {
        this.draw(this.createImages(draws), outputType, stream, layout);
    }

    private Collection<Image> createImages(Collection<GTDraw> draws) {
        Collection<Image> images = new ArrayList<Image>(draws.size());
        for (GTDraw draw : draws) {
            images.add(this.createImage(draw));
        }
        return images;
    }

    private Image createImage(final GTDraw draw) {
        return new Image() {
            @Override
            public Dimension getSize(Graphics2D g) {
                return draw.getSize();
            }

            @Override
            public void draw(Graphics2D g) {
                draw.draw(g);
            }
        };
    }
}
