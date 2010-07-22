package org.sonar.plugin.dotnet.partcover;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class PartCover4ParsingStrategy extends AbstractParsingStrategy {

	private final static Logger log = LoggerFactory.getLogger(PartCover2ParsingStrategy.class);

	public PartCover4ParsingStrategy() {
		setFilePath("/*/File");
    setMethodPath("/*/Type/Method");
	}

	@Override
	String findAssemblyName(Element methodElement) {
		Element typeElement = (Element) methodElement.getParentNode();
		String assemblyRef = typeElement.getAttribute("asmref");
		return evaluateAttribute(typeElement, "../Assembly[@id=\""+assemblyRef+"\"]/@name");
	}

	@Override
	boolean isCompatible(Element element) {
		String version = element.getAttribute("version");
		// Evaluates the part cover version
		final boolean result;
		if (version.startsWith("4.")) {
			log.debug("Using PartCover 4 report format");
			result = true;
		} else {
			log.debug("Not using PartCover 4 report format");
			result = false;
		}
		return result;
	}

}