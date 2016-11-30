package sale;

/**
 * Item class represents a single antique item with name, description, price and
 * condition of a certain category.
 * 
 * @author mabraham
 */
public class Item {
	private String mId;
	private String mName;
	private String mDescription;
	private double mPrice;
	private String mCondition;
	private ItemCategory mItemCategory;

	/**
	 * Create an item with the given name, description, price and condition. 
	 * @param name Name of the item 
	 * @param description Description of the item 
	 * @param price Price of the item 
	 * @param condition Condition of the item 
	 * @param itemCategory Category that the item belongs to
	 */
	public Item(String name, String description, double price,
			String condition, ItemCategory itemCategory) {
		this(name, itemCategory);
		setDescription(description);
		mPrice = price;
		mCondition = condition;
	}

	/**
	 * Create an item with only name since the other fields are optional. 
	 * Item name must be at least 3 characters long. 
	 * @param name Name of the
	 * item
	 */
	public Item(String name, ItemCategory itemCategory) {
		if (name == null || name.length() < 3) {
			throw new IllegalArgumentException("Invalid name for Item");
		}
		this.mName = name;
		this.mItemCategory = itemCategory;
	}

	/** * @return the mDescription */
	public String getDescription() {
		return mDescription;
	}

	/**
	 * * @param mDescription the mDescription to set 
	 */
	public void setDescription(String description) {
//		if (description == null || description.length() < 6) {
//			throw new IllegalArgumentException("Invalid Description");
//		}
		mDescription = description;

	}

	/** * @return the price */
	public double getPrice() {
		return mPrice;
	}

	/** * @param price the price to set */
	public void setPrice(double price) {
		if (price < 0) {
			throw new IllegalArgumentException("Price cannot be negative");
		}
		mPrice = price;
	}

	/** * @return the condition */
	public String getCondition() {
		return mCondition;
	}

	/** * @param condition the condition to set */
	public void setCondition(String condition) {
		mCondition = condition;
	}

	/** * @return the id */
	public String getId() {
		return mId;
	}

	/**
	 * @param mId the mId to set
	 */
	public void setId(String id) {
		this.mId = id;
	}

	/** * @return the name */
	public String getName() {
		return mName;
	}

	/** * @return the mItemCategory */
	public ItemCategory getItemCategory() {
		return mItemCategory;
	}

	/** * @param category the Category to set */
	public void setItemCategory(ItemCategory category) {
		this.mItemCategory = category;
	}
}