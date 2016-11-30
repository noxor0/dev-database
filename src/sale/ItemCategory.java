package sale;

/**
 * Represents a category that the store carries that
 * the item can be under.
 * @author mabraham
 *
 */
public class ItemCategory {
	private String mCategory;

	
	/**
	 * Constructs a new ItemCategory object.
	 * @param category
	 */
	public ItemCategory(String category) {
		mCategory = category;
	}

	/**
	 * Returns the category.
	 * @return the category
	 */
	public String getCategory() {
		return mCategory;
	}
	
	
}
