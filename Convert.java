package gsmClass1;


public class Convert {
	// �ڵ�Ÿ�� - �ʼ�, �߼�, ����
	enum CodeType {
		chosung, jungsung, jongsung// switch case���� ����ϱ� ���� ����ü
	}

	public static void main(String input) {
		Convert c = new Convert();
		String result = c.engToKor(input); // �Է¹޴� ��� ����
		Frame.setOutput(result);
	}

	/* ���� -> �ѱ� */
	public String engToKor(String eng) {
		// StringBuffer Ŭ������ java.lang ��Ű���� ���ԵǾ� ����
		StringBuffer sb = new StringBuffer();// �Է¹����� ���� �Ҵ�����
		int initialCode = 0, medialCode = 0, finalCode = 0;// �ʼ�, �߼�, ������ ����
		int tempMedialCode, tempFinalCode;// �� �� or �� �ڷ� �̷���� �ִ��� Ȯ���ϴ� ����

		for (int i = 0; i < eng.length(); i++) {// �Է¹��� eng�� ���̸�ŭ �ݺ�
			// �ʼ��ڵ� ����
			initialCode = getCode(CodeType.chosung, eng.substring(i, i + 1));// �ʼ� �ѱ���
			i++; // �������ڷ�

			// �߼��ڵ� ����
			tempMedialCode = getDoubleMedial(i, eng); // �� �ڷ� �̷���� �߼��ڵ� ����

			if (tempMedialCode != -1) {
				medialCode = tempMedialCode;// �� �ڷ� �̷���� ���� �ڵ带 medialCode�� ����
				i += 2; // �� �ڷ� �̷���� ���� �ڵ��̱⿡ +2�� ����
			} else { // ���ٸ�
				medialCode = getSingleMedial(i, eng); // �� �ڷ� �̷���� �߼��ڵ� ����
				i++; // ���� ���ڷ�
			}
			// �����ڵ� ����
			tempFinalCode = getDoubleFinal(i, eng); // �� �ڷ� �̷���� �����ڵ� ����
			if (tempFinalCode != -1) {// �� �ڷ� �̷���� ������ ���� ���
				finalCode = tempFinalCode;
				// �� ������ �߼� ���ڿ� ���� �ڵ带 �����Ѵ�.
				// �ڿ� ���ڰ� �� �ִ��� Ȯ���Ϸ���
				tempMedialCode = getSingleMedial(i + 2, eng);// �� ���� ���� �ڵ带 �����Ա⿡ +2�� ����
				if (tempMedialCode != -1) { // �ڵ� ���� ���� ���
					finalCode = getSingleFinal(i, eng); // ���� �ڵ� ���� �����Ѵ�.
				} else {
					i++;
				}
			} else { // �� �ڷ� �̷���� ������ ���� ���
				tempMedialCode = getSingleMedial(i + 1, eng); // �� ������ �߼� ���ڿ� ���� �ڵ� ����
				if (tempMedialCode != -1) { // �� ������ �߼� ���ڰ� ������ ���
					finalCode = 0; // ���� ���ڴ� ����
					i--;
				} else { // �߼����� false�� ���� ��, ex)����(��)�� ����(��)�� �� ���
					finalCode = getSingleFinal(i, eng); // ���� ���� ����
					if (finalCode == -1) {// ������ ���� ���
						finalCode = 0;// ��ħ�� ����
					}
				}
			}
			// ������ �ʼ� ���� �ڵ�, �߼� ���� �ڵ�, ���� ���� �ڵ带 ���� �� ��ȯ�Ͽ� ��Ʈ�����ۿ� �ѱ�
			sb.append((char) (0xAC00 + initialCode + medialCode + finalCode));
		}
		return sb.toString();// String ������ �ٲ���
	}

	private int getCode(CodeType type, String c) {
		// �ʼ�
		String init[] = { "r", "R", "s", "e", "E", "f", "a", "q", "Q", "t", "T", "d", "w", "W", "c", "z", "x", "v",
				"g" };
		// �߼�
		String mid[] = { "k", "o", "i", "O", "j", "p", "u", "P", "h", "hk", "ho", "hl", "y", "n", "nj", "np", "nl", "b",
				"m", "ml", "l" };
		// ����
		String fin[] = { " ", "r", "R", "rt", "s", "sw", "sg", "e", "f", "fr", "fa", "fq", "ft", "fx", "fv", "fg", "a",
				"q", "qt", "t", "T", "d", "w", "c", "z", "x", "v", "g" };
		switch (type) {
		/*
		 * �����ڵ� �ѱ��� 0xAC00 ���κ��� �ʼ� 19��, �߻�21��, ����28���� �̷������ �̵��� ������ 11,172���� ���ڸ� ���´�.
		 * �����ڵ忡�� �ѱ��� Ư¡�� �Ʒ��� ����. �ѱ� ���� �ڵ� : 44032 (0xAC00) �ѱ� �� �ڵ� : 55199 (0xD79F) �ʼ�
		 * �ڵ� : 588 �߼� �ڵ� : 28
		 */
		case chosung:
			for (int i = 0; i < init.length; i++) {
				if (init[i].equals(c)) {
					return i * 21 * 28;// �ʼ� ��ȯ
				}
			}
			break;
		case jungsung:

			for (int i = 0; i < mid.length; i++) {
				if (mid[i].equals(c)) {
					return i * 28; // �߼� ��ȯ
				}
			}
			break;
		case jongsung:
			for (int i = 0; i < fin.length; i++) {
				if (fin[i].equals(c)) {
					return i;// ���� ��ȯ
				}
			}
			break;
		}
		return -1;// switch case������ false�� ������ -1 ��ȯ
	}

	// �� �ڷ� �� �߼����� �����Ѵ�
	// �ε����� ����ٸ� -1�� ����
	private int getSingleMedial(int i, String eng) {
		if ((i + 1) <= eng.length()) {
			return getCode(CodeType.jungsung, eng.substring(i, i + 1));
		} else {
			return -1;
		}
	}

	// �� �ڷ� �� �߼��� üũ�ϰ�, �ִٸ� ���� �����Ѵ�
	// ������ ���ϰ��� -1
	private int getDoubleMedial(int i, String eng) {
		int result;// ���� �����ִ� ����
		// ���� ���ڰ� �ִ��� Ȯ���ҷ��� if�� ���
		if ((i + 2) > eng.length()) {// eng��length�� �Ѿ��
			return -1;// -1��ȯ
		} else {
			result = getCode(CodeType.jungsung, eng.substring(i, i + 2));
			if (result != -1) {
				return result;// ���� ������ ��� ��ȯ
			} else {
				return -1;// ���� ������ -1 ��ȯ
			}
		}
	}

	// �� �ڷε� �������� �����Ѵ�
	// �ε����� ����ٸ� -1�� ����
	private int getSingleFinal(int i, String eng) {
		if ((i + 1) <= eng.length()) {// eng�� length ���� �ȿ� ���
			return getCode(CodeType.jongsung, eng.substring(i, i + 1));// �� ����
		} else {
			return -1;// ���� �ȿ� �� ��� -1��ȯ
		}
	}

	// �� �ڷε� ������ üũ�ϰ�, �ִٸ� ���� �����Ѵ�
	// ������ ���ϰ��� -1
	private int getDoubleFinal(int i, String eng) {
		if ((i + 2) > eng.length()) {
			return -1;
		} else {
			return getCode(CodeType.jongsung, eng.substring(i, i + 2));
		}
	}
}