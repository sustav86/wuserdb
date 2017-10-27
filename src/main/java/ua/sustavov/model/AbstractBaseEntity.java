package ua.sustavov.model;

import java.io.Serializable;

public abstract class AbstractBaseEntity implements Serializable {

	private static final long serialVersionUID = 191432123531262799L;
	public static final int START_SEQ = 1000;

	private Integer id;

	protected AbstractBaseEntity() {

	}

	protected AbstractBaseEntity(Integer id) {
		this.id = id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public boolean isNew() {
		return (this.id == null);
	}

	@Override
	public String toString() {
		return String.format("Entity %s (%s)", getClass().getName(), getId());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || !getClass().equals(o.getClass())) {
			return false;
		}
		AbstractBaseEntity that = (AbstractBaseEntity) o;
		return getId() != null && getId().equals(that.getId());
	}

	@Override
	public int hashCode() {
		return (getId() == null) ? 0 : getId();
	}

}
