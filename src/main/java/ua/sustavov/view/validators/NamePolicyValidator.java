package ua.sustavov.view.validators;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class NamePolicyValidator implements IValidator<String> {

	private static final long serialVersionUID = -8296732353114390035L;

	@Override
	public void validate(IValidatable<String> validatable) {
		final String name = validatable.getValue();
		if (name == null || name.length() == 0) {
			error(validatable, "nameEmpty");
		}
		if (name.length() < 3 || name.length() > 50) {
			error(validatable, "nameLength");
		}
	}
	
	private void error(IValidatable<String> validatable, String errorKey) {
		ValidationError error = new ValidationError();
		error.addKey(getClass().getSimpleName() + "." + errorKey);
		validatable.error(error);
	}

}
