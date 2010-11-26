package net.parostroj.timetable.model;

import groovy.text.GStringTemplateEngine;
import groovy.text.Template;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Groovy text template.
 *
 * @author jub
 */
public final class TextTemplateGroovy extends TextTemplate {

    private static final Logger LOG = LoggerFactory.getLogger(TextTemplateGroovy.class);

    private final Template templateGString;
    
    protected TextTemplateGroovy(String template) throws GrafikonException {
        super(template);
        GStringTemplateEngine engine = new GStringTemplateEngine();
        try {
            templateGString = engine.createTemplate(template);
        } catch (Exception e) {
            throw new GrafikonException("Cannot create template: " + e.getMessage(), e, GrafikonException.Type.TEXT_TEMPLATE);
        }
    }

    @Override
    public String evaluateWithException(Map<String, Object> binding) throws GrafikonException {
        try {
            return templateGString.make(binding).toString();
        } catch (Exception e) {
            throw new GrafikonException("Error evaluating template: " + e.getMessage(), e);
        }
    }

    @Override
    public String evaluate(Map<String, Object> binding) {
        try {
            return this.evaluateWithException(binding);
        } catch (GrafikonException e) {
            LOG.warn(e.getMessage());
            return "-- Template error --";
        }
    }

    @Override
    public Language getLanguage() {
        return Language.GROOVY;
    }
}
