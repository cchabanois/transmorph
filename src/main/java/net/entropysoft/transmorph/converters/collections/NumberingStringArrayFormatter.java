package net.entropysoft.transmorph.converters.collections;

public class NumberingStringArrayFormatter implements IStringArrayFormatter {

	public String format(String[] strings) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strings.length; i++) {
			if (i != 0) {
				sb.append('\n');
			}
			sb.append('[').append(i).append("]=").append(strings[i]);
			
		}
		return sb.toString();
	}

}
