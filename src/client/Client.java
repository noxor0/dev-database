package client;

/**
 * Client class represents a single client with name, address, 
 * city, and zipcode.
 * 
 * @author concox
 *
 */
public class Client {
	private String mId;
	private String mFirstName;
	private String mLastName;
	private char mMid;
	private String mAddress;
	private String mCity;
	private String mState;
	private String mZipcode;
	
	//Excluding final params to stay consistant with your code.
	/**
	 * Creates a client with all the information.
	 * @param first Name of the client
	 * @param last Name of the client
	 * @param mid Letter of the client
	 * @param address of the client
	 * @param city of the client
	 * @param zip of the client
	 */
	public Client(String first, String last, char mid, String address,
			 String city, String state, String zip) {
		this(first, last, mid);
		this.mAddress = address;
		this.mCity = city;
		this.mState = state;
		this.mZipcode = zip;
		
	}
	
	/**
	 * Creates a client with less information. 
	 * @param first Name of the client
	 * @param last Name of the client
	 * @param mid Letter of the client
	 */
	public Client(String first, String last, char mid) {
		if (first == null || last == null) {
			throw new IllegalArgumentException("Invalid name for Item");
		}
		this.mFirstName = first;
		this.mMid = mid;
		this.mLastName = last;
	}

	/**
	 * @param mId the mId to set
	 */
	public void setId(String mId) {
		this.mId = mId;
	}

	/**
	 * @return the mId
	 */
	public String getId() {
		return mId;
	}

	/**
	 * @return the mFirst
	 */
	public String getFirstName() {
		return mFirstName;
	}

	/**
	 * @return the mLast
	 */
	public String getLastName() {
		return mLastName;
	}

	/**
	 * @return the mMid
	 */
	public char getMid() {
		return mMid;
	}

	/**
	 * @return the mAddress
	 */
	public String getAddress() {
		return mAddress;
	}

	/**
	 * @return the mCity
	 */
	public String getCity() {
		return mCity;
	}

	/**
	 * @return the mZipcode
	 */
	public String getZipcode() {
		return mZipcode;
	}
	
	/**
	 * @return the mState
	 */
	public String getState() {
		return mState;
	}
	
	/**
	 * @param mState the mState to set
	 */
	public void setState(String state) {
		this.mState = state;
	}
	
	/**
	 * @param mFirst the mFirst to set
	 */
	public void setFirstName(String mFirst) {
		this.mFirstName = mFirst;
	}

	/**
	 * @param mLast the mLast to set
	 */
	public void setLastName(String mLast) {
		this.mLastName = mLast;
	}

	/**
	 * @param mMid the mMid to set
	 */
	public void setMid(char mMid) {
		this.mMid = mMid;
	}

	/**
	 * @param mAddress the mAddress to set
	 */
	public void setAddress(String mAddress) {
		this.mAddress = mAddress;
	}

	/**
	 * @param mCity the mCity to set
	 */
	public void setCity(String mCity) {
		this.mCity = mCity;
	}

	/**
	 * @param mZipcode the mZipcode to set
	 */
	public void setZipcode(String mZipcode) {
		this.mZipcode = mZipcode;
	}
	
	
}
