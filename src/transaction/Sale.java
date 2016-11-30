package transaction;

/**
 * Class to hold all relevant information about a sell that has been made.
 * 
 * @author Concox
 *
 */
public class Sale {
	private int mSaleId;
	private Double mCommission;
	private Double mPrice;
	private	int mItemId;
	private int mClientId;
	
	/**
	 * Holds information for a purchase
	 * @param saleId of the sal e
	 * @param commission of the sale
	 * @param price of the sale
	 * @param itemId of the sale
	 * @param clientId of the sale
	 */
	public Sale(Double commission, Double price, int itemId, int clientId) {
		if (price == null) {
			throw new IllegalArgumentException("Invalid condition for Item");
		}
		mCommission = commission;
		mPrice = price;
		mItemId = itemId;
		mClientId = clientId;
	}
	/**
	 * @return the saleId
	 */
	public int getSaleId() {
		return mSaleId;
	}
	/**
	 * @param saleId the saleId to set
	 */
	public void setSaleId(int saleId) {
		mSaleId = saleId;
	}
	/**
	 * @return the commission
	 */
	public Double getCommission() {
		return mCommission;
	}
	/**
	 * @param commission the commission to set
	 */
	public void setCommission(Double commission) {
		mCommission = commission;
	}
	/**
	 * @return the price
	 */
	public Double getPrice() {
		return mPrice;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		mPrice = price;
	}
	/**
	 * @return the itemId
	 */
	public int getItemId() {
		return mItemId;
	}
	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(int itemId) {
		mItemId = itemId;
	}
	/**
	 * @return the clientId
	 */
	public int getClientId() {
		return mClientId;
	}
	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(int clientId) {
		mClientId = clientId;
	}
}
