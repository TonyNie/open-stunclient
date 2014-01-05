package com.cloudnie.stunclient;
import java.lang.System;
import java.net.InetAddress;

/*                          Format of STUN Attributes
 *   0                   1                   2                   3
 *   0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 *   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *   |         Type                  |            Length             |
 *   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *   |                         Value (variable)                ....
 *   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 */

public class StunAttribute {
	private int mType;	
	private int mLength;
	private byte[] mValue;

	public StunAttribute(int type, int length, byte[] value) {

		mType = type;
		mLength = length;
		if (value != null)
			mValue = value.clone();
	}

	public StunAttribute(byte[] value) {
		mType = (value[0] << 8) | value[1];
		mLength = (value[2] << 8) | value[3];
		mValue = new byte[mLength];
		System.arraycopy(value, 4, mValue, 0,  mLength);
	}

	public StunAttribute(InetAddress address, int port) {
		byte[] rawAddress = address.getAddress();
		mType = StunConstant.STUN_MAPPED_ADDRESS;
		mLength = rawAddress.length + 4;

		mValue = new byte[mLength];
		mValue[0] = 0;
		mValue[1] = (rawAddress.length > 4) ? (byte) 0x02B : (byte) 0x01B;
		mValue[2] = (byte) (port >> 8);
		mValue[3] = (byte) (port);
		System.arraycopy(rawAddress, 0, mValue, 0, mLength);
	}

	public int getType() {
		return mType;
	}

	public byte[] getValue() {
		return mValue;
	}

	public int getLength() {
		return mLength;
	}

	public byte[] getRaw() {
		byte [] raw;
		int length = mLength + 4;

		if ((mLength % 4) != 0)
			length += 4 - (mLength % 4);

		raw = new byte[length];
		raw[0] = (byte) (mType >> 8);
		raw[1] = (byte) (mType);
		raw[2] = (byte) (mLength >> 8);
		raw[3] = (byte) (mLength);

		System.arraycopy(mValue, 0, raw, 4, mValue.length);

		return raw;
	}

	public int setIP() {
		return 0;
	}

	public int setString(String string) {
		return 0;
	}


	public static void main(String[] args){

	}
};
