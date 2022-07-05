//Mustafa Said Çanak	150120020
public abstract class SmartObject {
	private String alias, macID, IP;
	private boolean connectionStatus;

	public SmartObject() {

	}

	public SmartObject(String alias, String macId) {
		this.alias = alias;
		this.macID = macId;
	}

	public boolean connect(String IP) {
		// connect the device
		if (isConnectionStatus() == false) {
			this.IP = IP;
			this.connectionStatus = true;
			System.out.println(this.alias + " connection established");
			return true;
		} else
			return false;
	}

	public boolean disconnect() {
		// disconnect device
		this.IP = null;
		this.connectionStatus = false;
		return true;

	}

	public void SmartObjectToString() {
		// show the object, class of the object, id and ip values of the object
		System.out.println("This is " + this.getClass().getName() + " device " + this.alias);
		System.out.println("MacId: " + this.macID);
		System.out.println("IP: " + this.IP);
	}

	public void controlConnection() {
		// if no connection, display the message
		if (!isConnectionStatus())
			System.out.println("This device is not connected. " + this.getClass().getName() + " -> " + this.alias);
	}

	public abstract boolean testObject();

	public abstract boolean shutDownObject();

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getMacID() {
		return macID;
	}

	public void setMacID(String macID) {
		this.macID = macID;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public boolean isConnectionStatus() {
		return connectionStatus;
	}

	public void setConnectionStatus(boolean connectionStatus) {
		this.connectionStatus = connectionStatus;
	}
}
