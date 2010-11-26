package net.parostroj.timetable.model;

import java.util.Collections;
import java.util.Map;

/**
 * Text template.
 *
 * @author jub
 */
public abstract class TextTemplate {

    private final String template;

    protected TextTemplate(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }
    
    public abstract String evaluateWithException(Map<String, Object> binding) throws GrafikonException;

    public String evaluateWithException(Object object, Map<String, Object> binding) throws GrafikonException {
        Map<String, Object> bindingObj = this.getBinding(object);
        binding.putAll(bindingObj);
        return this.evaluateWithException(binding);
    }
    
    public String evaluateWithException(Object object) throws GrafikonException {
        return this.evaluateWithException(this.getBinding(object));
    }

    public abstract String evaluate(Map<String, Object> binding);

    public String evaluate(Object object, Map<String, Object> binding) {
        Map<String, Object> bindingObj = this.getBinding(object);
        binding.putAll(bindingObj);
        return this.evaluate(binding);
    }
    
    public String evaluate(Object object) {
        return this.evaluate(this.getBinding(object));
    }

    public abstract Language getLanguage();
    
    protected Map<String, Object> getBinding(Object object) {
        if (object instanceof Train)
            return ((Train) object).createTemplateBinding();
        else
            return Collections.emptyMap();
    }

    public static TextTemplate createTextTemplate(String template, Language language) throws GrafikonException {
        switch(language) {
            case MVEL:
                return new TextTemplateMvel(template);
            case GROOVY:
                return new TextTemplateGroovy(template);
            default:
                throw new IllegalArgumentException("No template for language available.");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TextTemplate other = (TextTemplate) obj;
        if (this.template != other.template && (this.template == null || !this.template.equals(other.template))) {
            return false;
        }
        if (this.getLanguage() != other.getLanguage()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this.template != null ? this.template.hashCode() : 0);
        return hash;
    }
}
