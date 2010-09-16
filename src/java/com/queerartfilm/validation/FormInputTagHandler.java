/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queerartfilm.validation;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author ch67dotnet
 */
public class FormInputTagHandler extends SimpleTagSupport implements DynamicAttributes {

    private static final String ATTR_FMT = " %s=\"%s\"";
    private static final int DEFAULT_INPUT_SIZE = 7;
    private String name;
    private String label;
    private String value;
    private String message;
    private int size = DEFAULT_INPUT_SIZE;
    private boolean disabled;
    private boolean required;
    private Map<String, Object> attrMap = new HashMap<String, Object>();

    /**
     * Called by the container to invoke this tag. 
     * The implementation of this method is provided by the tag library developer,
     * and handles all tag processing, body iteration, etc.
     */
    @Override
    public void doTag() throws JspException {
        JspWriter out = getJspContext().getOut();
        StringBuilder sb = new StringBuilder();
    //  label tag
        sb.append(String.format(
                "<label" + ATTR_FMT + "%s>%s</label>%n",
                "for", name,
                required
                    ? String.format(ATTR_FMT, "class", "required")
                    : "",
                label));
    //  begin div tag
        sb.append(String.format(
                "<div" + ATTR_FMT + ">", "class", "inputbox"));
    //  text input tag
        sb.append(String.format(
                "<input" + ATTR_FMT + ATTR_FMT + "%n" + ATTR_FMT + ATTR_FMT +
                "%n" + ATTR_FMT + "%s%n",
                "type", "text",
                "name", name,
                "size", size,
                "id", name,
                "value", value,
                disabled
                    ? String.format(ATTR_FMT + ATTR_FMT,
                    "disabled", "disabled", "class", "disabled")
                    : "" ));

    //  Add any dynamic attributes
        for(String key : attrMap.keySet()) {
            sb.append(String.format("%n" + ATTR_FMT, key, attrMap.get(key)));
        }
    //  close input tag
        sb.append("/>\n");
    //  span tag with any error message
        sb.append(String.format(
                "<br/><span" + ATTR_FMT + ">%s</span>%n",
                "class", "error", message));
    //  close div tag
        sb.append("</div>");
        try {
            out.println(sb.toString());
        } catch (java.io.IOException ex) {
            throw new JspException("Error in FormInputTagHandler tag", ex);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public void setSize(int size) {
        this.size = size;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void setDynamicAttribute(String uri, String name, Object value) throws JspException {
         attrMap.put(name, value);
    }
}
