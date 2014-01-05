package com.cloudnie.stunclient;
import java.lang.System;
import java.io.*;
import java.net.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/*			Format of STUN Message
 *  0                   1                   2                   3
 *  0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |0 0|     STUN Message Type     |         Message Length        |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |                         Magic Cookie                          |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |                                                               |
 *  |                     Transaction ID (96 bits)                  |
 *  |                                                               |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 */

public class StunMessage {
	private int mType = 0;
	private int mLength = 0;
	private int mAttriNum = 0;
	private byte mTransID[] = new byte[16];
	private List<StunAttribute> mAttris = new ArrayList<StunAttribute>();
	
	public StunMessage(byte[] value) {
		mAttriNum = 0;
		mType = (value[0] << 8) | value[1];
		mLength = (value[2] << 8) | value[3];
		System.arraycopy(value, 4, mTransID, 0, 16);

		rawToAttributes(value);
	}

	public StunMessage(int type) {
		/* TODO: */
		mType = type;
		mLength = 0;
		mAttriNum = 0;
		getTransactionID();

	}

	private void getTransactionID() {

		/* Magic cookie:0x2112A442 */
		mTransID[0] = (byte) 0x21;
		mTransID[1] = (byte) 0x12;
		mTransID[2] = (byte) 0xA4;
		mTransID[3] = (byte) 0x42;

		Random r = new Random();

		for(int i = 4; i < 20; i++) {
			mTransID[i] = (byte) (r.nextInt(256));
		}

	}

	public byte[] getRaw() {
		byte raw[] = new byte[mLength + 20];

		byte attris[] = attributesToRaw();

		if (mLength != attris.length) {
			/* TODO: throw exception */
		}

		raw[0] = (byte) (mType >> 8);
		raw[1] = (byte) (mType);
		raw[2] = (byte) (mLength >> 8);
		raw[3] = (byte) (mLength);

		System.arraycopy(mTransID, 0, raw, 4, 16);
		System.arraycopy(attris, 0, raw, 20, mLength);

		return raw;
	}

	public int addAttribute(StunAttribute attri) {
		int len = attri.getLength();

		len += (4 - (len % 4)) % 4;
		mLength += len;

		mAttriNum++;
		mAttris.add(attri);
		return 0;
	}

	public StunAttribute getAttribute(int pos) {
		assert(pos < mAttriNum);

		return mAttris.get(pos);
	}

	public List<StunAttribute> getAttributes() {
		return mAttris;
	}

	public int attriNumber() {
		return mAttriNum;
	}

	private void rawToAttributes(byte[] value) {
		int pos = 20;

		assert(value.length >= 20);
		mAttriNum = 0;

		while(pos < value.length) {
			int __len = (value[1] << 8) | value[2];

			if (__len > (value.length - 20 - 4)) {
				/*TODO: throw exception */
			}

			byte attri[] = new byte[__len];
			System.arraycopy(value, 20, attri, 0, __len + 4);
			StunAttribute __attri = new StunAttribute(attri);
			mAttris.add(__attri);
			mAttriNum++;
			pos += 4; /* offset ATTRIBUTE HEADER */
			pos += __len + (4 - (__len % 4)) % 4; /* offset ATTRIBUTE value */
		}
	}

	private byte[] attributesToRaw() {
		int pos = 20;
		byte raw[] = new byte[mLength + 20];
		Arrays.fill(raw, (byte) 0);

		raw = new byte[mLength + 20];
		raw[0] = (byte) (mType >> 8);
		raw[1] = (byte) (mType);
		raw[2] = (byte) (mLength >> 8);
		raw[3] = (byte) (mLength);

		System.arraycopy(mTransID, 0, raw, 4, 16);

		for(StunAttribute attri: mAttris) {
			byte __raw[] = attri.getRaw();
			int __len = __raw.length;
			System.arraycopy(__raw, 0, raw, pos, __len);
			pos += __len;
		}

		return raw;
	}

	public static StunMessage getBindingRequest() {
		return new StunMessage(StunConstant.BINDING_REQUEST);
	}

	public static void main(String[] args) {
	}
};
