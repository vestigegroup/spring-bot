package org.finos.springbot.sources.teams.handlers.adaptivecard;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.finos.springbot.workflow.templating.AbstractClassConverter;
import org.finos.springbot.workflow.templating.ElementFormat;
import org.finos.springbot.workflow.templating.Variable;

public abstract class AbstractDropdownConverter extends AbstractClassConverter {

	public AbstractDropdownConverter(int priority, Class<?> c) {
		super(priority, c);
	}
	
	public String renderDropdown(Variable variable, String location, Function<String, String> sourceFunction , Function<String, String> keyFunction, BiFunction<String, String, String> valueFunction) {
		String index = variable.index().getDataPath();
		int indent = variable.depth;
		return formatErrorsAndIndent(variable.getFormFieldName(), variable.depth)
				+ "<select " 
				+ attribute(variable, "name", variable.getFormFieldName())
				+ attribute(variable, "data-placeholder", "Choose "+variable.getDisplayName())
				+ ">"
				+ indent(indent)
				+ "  <#list "+sourceFunction.apply(location)+" as "+index+">"
				+ indent(indent)
				+ "   <option value=\"${"+keyFunction.apply(index)+"}\""
				+ " selected=\"${("+keyFunction.apply(variable.getDataPath())+" == "+keyFunction.apply(index)+")?string('true','false')}\""
				+ ">"
				+ indent(indent)
				+ "    ${"+valueFunction.apply(index, location)+"}"
				+ indent(indent)
				+ "   </option>"
				+ indent(indent)
				+" </#list>"
				+ indent(indent)
				+"</select>";
	}
	
	public String renderDropdown(Variable variable, String location, ElementFormat ef) {
		return renderDropdown(variable, location, ef.getSourceFunction(), ef.getKeyFunction(), ef.getValueFunction());
	}
	
}
