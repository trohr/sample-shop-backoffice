/**
 * 
 */
package cz.osu.oop3.proj.domain.sorting;

/**
 * Class that encapsulates property by which ordering is to be made and its order.
 * 
 * @author Tomáš Rohrbacher (rohrbacher[at]elvoris.cz)
 */
public class OrderBy {
	
	static public enum Direction {
		ASC, DESC
	}
	
	final private String property;
	final private Direction direction;

	public OrderBy(String property, Direction direction) {
		if (property == null) {
			throw new IllegalArgumentException("Property name has to be specified!");
		}
		if (direction == null) {
			throw new IllegalArgumentException("The sort direction has to be specified!");
		}
		this.property = property;
		this.direction = direction;
	}

	public String getProperty() {
		return property;
	}

	public Direction getDirection() {
		return direction;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + ((property == null) ? 0 : property.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof OrderBy))
			return false;
		OrderBy other = (OrderBy) obj;
		if (direction != other.direction)
			return false;
		if (property == null) {
			if (other.property != null)
				return false;
		} else if (!property.equals(other.property))
			return false;
		return true;
	}
	
	
}
