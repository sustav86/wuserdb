package ua.sustavov.model;

import java.io.Serializable;

import org.springframework.lang.NonNull;

public abstract class AbstractNamedEntity extends AbstractBaseEntity implements Serializable {

	private static final long serialVersionUID = -5298960226564700525L;

	protected String name;

	public AbstractNamedEntity() {

	}

	public AbstractNamedEntity(Integer id, String name) {
		super(id);
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return String.format("Entity %s (%s, '%s')", getClass().getName(), getId(), name);
	}

}
