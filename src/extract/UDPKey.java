package extract;

public class UDPKey {
	
	private String sourceIP;
	private String destIP;
	private String sourcePort;
	private String destPort;
	
	private String packetType;
	
	public UDPKey(String sourceIP, String destIP, String sourcePort, String destPort, String packetType) {
		this.sourceIP = sourceIP;
		this.destIP = destIP;
		this.sourcePort = sourcePort;
		this.destPort = destPort;
		this.packetType = packetType;
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
	
	public String getPacketType() {
		return packetType;
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
					&& this.destPort.equals(key.getDestPort())
					&& this.packetType.equals(key.getPacketType())) {
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
		hash = 7 * hash + this.packetType.hashCode();
		return hash;
	}
	
	
	@Override
	public String toString() {
		
		String  s = "Source: " + this.sourceIP + ":" + this.sourcePort + " Dest: " + this.destIP + ":" + this.destPort + " " + this.packetType;
		return s;
	}
}

