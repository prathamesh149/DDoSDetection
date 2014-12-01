package project;

public class UDPNormalizedFeature {

	private String sourceIP;
	private String sourcePort;
	private String destIP;
	private String destPort;

	private Double packets;
	private Double bytes;
	private Double averagePacketSize;
	private Double packetRate;
	private Double byteRate;
	private Double timeIntervalVariance;
	private Double packetSizeVariance;
	private String packetType;

	public String getSourceIP() {
		return sourceIP;
	}

	public void setSourceIP(String sourceIP) {
		this.sourceIP = sourceIP;
	}

	public String getSourcePort() {
		return sourcePort;
	}

	public void setSourcePort(String sourcePort) {
		this.sourcePort = sourcePort;
	}

	public String getDestIP() {
		return destIP;
	}

	public void setDestIP(String destIP) {
		this.destIP = destIP;
	}

	public String getDestPort() {
		return destPort;
	}

	public void setDestPort(String destPort) {
		this.destPort = destPort;
	}

	public Double getPackets() {
		return packets;
	}

	public void setPackets(Double packets) {
		this.packets = packets;
	}

	public Double getBytes() {
		return bytes;
	}

	public void setBytes(Double bytes) {
		this.bytes = bytes;
	}

	public Double getAveragePacketSize() {
		return averagePacketSize;
	}

	public void setAveragePacketSize(Double averagePacketSize) {
		this.averagePacketSize = averagePacketSize;
	}

	public Double getPacketRate() {
		return packetRate;
	}

	public void setPacketRate(Double packetRate) {
		this.packetRate = packetRate;
	}

	public Double getByteRate() {
		return byteRate;
	}

	public void setByteRate(Double byteRate) {
		this.byteRate = byteRate;
	}

	public Double getTimeIntervalVariance() {
		return timeIntervalVariance;
	}

	public void setTimeIntervalVariance(Double timeIntervalVariance) {
		this.timeIntervalVariance = timeIntervalVariance;
	}

	public Double getPacketSizeVariance() {
		return packetSizeVariance;
	}

	public void setPacketSizeVariance(Double packetSizeVariance) {
		this.packetSizeVariance = packetSizeVariance;
	}

	public String getPacketType() {
		return packetType;
	}

	public void setPacketType(String packetType) {
		this.packetType = packetType;
	}

	public double distance(UDPNormalizedFeature f) {
		return Math.pow(getPackets() - f.getPackets(), 2)
				+ Math.pow(getPacketSizeVariance() - f.getPacketSizeVariance(),
						2)
				+ Math.pow(getAveragePacketSize() - f.getAveragePacketSize(), 2)
				+ Math.pow(getByteRate() - f.getByteRate(), 2)
				+ Math.pow(getPacketRate() - f.getPacketRate(), 2)
				+ Math.pow(
						getTimeIntervalVariance() - f.getTimeIntervalVariance(),
						2)
				+ Math.pow(
						getTimeIntervalVariance() - f.getTimeIntervalVariance(),
						2);
	}

	public String toStringEntry() {
		int type = this.packetType.equals("attack") ? 0 : -1;

		return "" + this.packets + "," + this.bytes + ","
				+ this.averagePacketSize + "," + this.packetRate + ","
				+ this.byteRate + "," + this.timeIntervalVariance + ","
				+ this.packetSizeVariance + "," + type;
	}
	
	public double[] toDoubleEntry() {
		int type = this.packetType.equals("attack")?0:-1;
		
		double[] arr = new double[8];
		
		arr[0] = type;
		arr[1] = this.packets;
		arr[2] = this.bytes;
		arr[3] = this.averagePacketSize;
		arr[4] = this.packetRate;
		arr[5] = this.byteRate;
		arr[6] = this.timeIntervalVariance;
		arr[7] = this.packetSizeVariance;
  		
		return arr;
	}

}