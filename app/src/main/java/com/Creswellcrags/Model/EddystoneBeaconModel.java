package com.Creswellcrags.Model;

/**
 * Created by Rebecca McBath
 * on 2019-07-16.
 */
public class EddystoneBeaconModel  {

	String nameSpace;
	String instanceId;
	String bluetoothName;
	String bluetoothAddress;

	@Override
	public String toString() {
		return "EddystoneBeaconModel{" +
				"nameSpace='" + nameSpace + '\'' +
				", instanceId='" + instanceId + '\'' +
				", bluetoothName='" + bluetoothName + '\'' +
				", bluetoothAddress='" + bluetoothAddress + '\'' +
				", rssi='" + rssi + '\'' +
				", txPower='" + txPower + '\'' +
				", distance='" + distance + '\'' +
				'}';
	}

	String rssi;
	String txPower;
	String distance;


	public String getNameSpace() {
		return nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getBluetoothName() {
		return bluetoothName;
	}

	public void setBluetoothName(String bluetoothName) {
		this.bluetoothName = bluetoothName;
	}

	public String getBluetoothAddress() {
		return bluetoothAddress;
	}

	public void setBluetoothAddress(String bluetoothAddress) {
		this.bluetoothAddress = bluetoothAddress;
	}

	public String getRssi() {
		return rssi;
	}

	public void setRssi(String rssi) {
		this.rssi = rssi;
	}

	public String getTxPower() {
		return txPower;
	}

	public void setTxPower(String txPower) {
		this.txPower = txPower;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	/*@Override
	public int getType() {
		return BeaconTypes.TYPE_EDDYSTONE;
	}*/

}
