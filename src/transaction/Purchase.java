package transaction;

/**
 * Purchase class represents a interaction of buying an object from a client.
 * 
 * @author concox
 *
 */
public class Purchase {
	private int mPurchaseId;
	private String mCondition;
	private Double mCost;
	private	int mItemId;
	private int mClientId;

	/**
	 * The purchase object that holds information about a recent purchase.
	 * @param purchaseId the Id for the sale
	 * @param commission of the sale
	 * @param cost of the sal e
	 * @param itemId that was included in the sale
	 * @param clientId that was included in the sale
	 */
	public Purchase(String condition, double cost, 
			int itemId, int clientId) {
		if (condition == null) {
			throw new IllegalArgumentException("Invalid condition for Item");
		}
		mCondition = condition;
		mCost = cost;
		mItemId = itemId;
		mClientId = clientId;
	}

	/**
	 * @return the mSaleId
	 */
	public int getPurchaseId() {
		return mPurchaseId;
	}

	/**
	 * @param mSaleId the mSaleId to set
	 */
	public void setPurchaseId(int mSaleId) {
		this.mPurchaseId = mSaleId;
	}

	/**
	 * @return the mCommission
	 */
	public String getCondition() {
		return mCondition;
	}

	/**
	 * @param mCommission the mCommission to set
	 */
	public void setContition(String mCondition) {
		this.mCondition = mCondition;
	}

	/**
	 * @return the mPrice
	 */
	public Double getCost() {
		return mCost;
	}

	/**
	 * @param mPrice the mPrice to set
	 */
	public void setCost(Double cost) {
		this.mCost = cost;
	}

	/**
	 * @return the mItemId
	 */
	public int getItemId() {
		return mItemId;
	}

	/**
	 * @param mItemId the mItemId to set
	 */
	public void setItemId(int mItemId) {
		this.mItemId = mItemId;
	}

	/**
	 * @return the mClientId
	 */
	public int getClientId() {
		return mClientId;
	}

	/**
	 * @param mClientId the mClientId to set
	 */
	public void setClientId(int mClientId) {
		this.mClientId = mClientId;
	}
	
	

}	
