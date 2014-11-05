package extract;

public class UDPKey {
	
	private String sourceIP;
	private String destIP;
	private String sourcePort;
	private String destPort;
	
	public UDPKey(String sourceIP, String destIP, String sourcePort, String destPort) {
		this.sourceIP = sourceIP;
		this.destIP = destIP;
		this.sourcePort = sourcePort;
		this.destPort = destPort;
	}

	public String getSourceIP() {
		return sourceIP;
	}

	public String getDestIP() {
		return destIP;
	}

	public String getSourcePort() {
		return sourcePort;
	}

	public String getDestPort() {
		return destPort;
	}
	
	@Override
	public boolean equals(Object object) {
		
		boolean result = false;
		if (object == null || object.getClass() != getClass()) {
			result = false;
		} else {
			UDPKey key = (UDPKey) object;
			if (this.sourceIP.equals(key.getSourceIP())
					&& this.destIP.equals(key.getDestIP())
					&& this.sourcePort.equals(key.getSourcePort()) 
					&& this.destPort.equals(key.getDestPort())) {
				result = true;
			}
		}
		return result;
	}
	
	@Override
	public int hashCode() {
		int hash = 3;
		hash = 7 * hash + this.sourceIP.hashCode();
		hash = 7 * hash + this.destIP.hashCode();
		hash = 7 * hash + this.sourcePort.hashCode();
		hash = 7 * hash + this.destPort.hashCode();		
		return hash;
	}
	
	
	@Override
	public String toString() {
		
		String  s = "Source: " + this.sourceIP + ":" + this.sourcePort + " Dest: " + this.destIP + ":" + this.destPort;
		return s;
	}
}

