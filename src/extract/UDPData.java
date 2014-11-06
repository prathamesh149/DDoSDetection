package extract;

public class UDPData {
	private Integer packetSize;
	private Double time;
	
	public UDPData(Integer size, Double time) {
		this.packetSize = size;
		this.time = time;
		
	}

	public Integer getPacketSize() {
		return packetSize;
	}

	public void setPacketSize(Integer packetSize) {
		this.packetSize = packetSize;
	}

	public Double getTime() {
		return time;
	}

	public void setTime(Double time) {
		this.time = time;
	}
	
}
