package my.server;

import my.server.exutor.Register;


import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;

public class Verifier {
	
	public static final Logger LOG=Logger.getLogger(Verifier.class);
	
	public static boolean isContainBadCharacters(String text) {
		String cleanText;
		cleanText = StringEscapeUtils.escapeHtml3(text);
		//StringEscapeUtils.escapeH
		LOG.info("cleanText " + cleanText);	
		LOG.info("text " + text);	

		boolean isBad = true;
		if (text.equals(cleanText)) {
			isBad = false;
		}
		//StringEscapeUtils.
		return isBad;
	}

}
