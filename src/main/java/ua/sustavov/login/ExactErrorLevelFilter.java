package ua.sustavov.login;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;

public class ExactErrorLevelFilter implements IFeedbackMessageFilter {

	private static final long serialVersionUID = -6970157011933942980L;
	private int errorLevel;

	public ExactErrorLevelFilter(int errorLevel) {
		this.errorLevel = errorLevel;
	}

	@Override
	public boolean accept(FeedbackMessage message) {
		return message.getLevel() == errorLevel;
	}

}
