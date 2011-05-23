package org.lb.plc.ams;

import java.util.Arrays;

public class NetId {
	private final byte[] data;

	public static NetId valueOf(final String value) {
		final String[] values = value.split("\\.");
		if (values.length != 6)
			throw new IllegalArgumentException(
					"Invalid textual AMS NetID representation: '" + value + "'");
		final int[] intValues = new int[6];
		for (int i = 0; i < 6; ++i)
			intValues[i] = Integer.valueOf(values[i]);
		return NetId.valueOf(intValues[0], intValues[1], intValues[2],
				intValues[3], intValues[4], intValues[5]);
	}

	public static NetId valueOf(final int b1, final int b2, final int b3,
			final int b4, final int b5, final int b6) {
		return new NetId(b1, b2, b3, b4, b5, b6);
	}

	public static NetId valueOf(final byte[] data) {
		if (data.length != 6)
			throw new IllegalArgumentException("Invalid data size");
		final byte b1 = data[0];
		final byte b2 = data[1];
		final byte b3 = data[2];
		final byte b4 = data[3];
		final byte b5 = data[4];
		final byte b6 = data[5];
		return valueOf(b1, b2, b3, b4, b5, b6);
	}

	private NetId(final int b1, final int b2, final int b3, final int b4,
			final int b5, final int b6) {
		ensureValueFitsByteSize(b1);
		ensureValueFitsByteSize(b2);
		ensureValueFitsByteSize(b3);
		ensureValueFitsByteSize(b4);
		ensureValueFitsByteSize(b5);
		ensureValueFitsByteSize(b6);
		data = new byte[] { (byte) b1, (byte) b2, (byte) b3, (byte) b4,
				(byte) b5, (byte) b6 };
	}

	private static void ensureValueFitsByteSize(final int value) {
		if (value < 0)
			throw new IllegalArgumentException("Value too small, min = 0");
		if (value > 255)
			throw new IllegalArgumentException("Value too large, max = 255");
	}

	public byte[] toBinary() {
		return data.clone();
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 6; ++i) {
			if (i > 0)
				builder.append('.');
			builder.append(data[i]);
		}
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(data);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof NetId))
			return false;
		NetId other = (NetId) obj;
		if (!Arrays.equals(data, other.data))
			return false;
		return true;
	}
}
