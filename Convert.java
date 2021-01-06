package gsmClass1;


public class Convert {
	// 코드타입 - 초성, 중성, 종성
	enum CodeType {
		chosung, jungsung, jongsung// switch case문을 사용하기 위한 열거체
	}

	public static void main(String input) {
		Convert c = new Convert();
		String result = c.engToKor(input); // 입력받는 결과 저장
		Frame.setOutput(result);
	}

	/* 영어 -> 한글 */
	public String engToKor(String eng) {
		// StringBuffer 클래스는 java.lang 패키지에 포함되어 제공
		StringBuffer sb = new StringBuffer();// 입력받으면 공간 할당해줌
		int initialCode = 0, medialCode = 0, finalCode = 0;// 초성, 중성, 종성인 변수
		int tempMedialCode, tempFinalCode;// 한 자 or 두 자로 이루어져 있는지 확인하는 변수

		for (int i = 0; i < eng.length(); i++) {// 입력받은 eng의 길이만큼 반복
			// 초성코드 추출
			initialCode = getCode(CodeType.chosung, eng.substring(i, i + 1));// 초성 한글자
			i++; // 다음문자로

			// 중성코드 추출
			tempMedialCode = getDoubleMedial(i, eng); // 두 자로 이루어진 중성코드 추출

			if (tempMedialCode != -1) {
				medialCode = tempMedialCode;// 두 자로 이루어진 종성 코드를 medialCode에 대입
				i += 2; // 두 자로 이루어진 종성 코드이기에 +2를 해줌
			} else { // 없다면
				medialCode = getSingleMedial(i, eng); // 한 자로 이루어진 중성코드 추출
				i++; // 다음 문자로
			}
			// 종성코드 추출
			tempFinalCode = getDoubleFinal(i, eng); // 두 자로 이루어진 종성코드 추출
			if (tempFinalCode != -1) {// 두 자로 이루어진 종성이 있을 경우
				finalCode = tempFinalCode;
				// 그 다음의 중성 문자에 대한 코드를 추출한다.
				// 뒤에 문자가 더 있는지 확인하려고
				tempMedialCode = getSingleMedial(i + 2, eng);// 두 자인 종성 코드를 가져왔기에 +2를 해줌
				if (tempMedialCode != -1) { // 코드 값이 있을 경우
					finalCode = getSingleFinal(i, eng); // 종성 코드 값을 저장한다.
				} else {
					i++;
				}
			} else { // 두 자로 이루어진 종성이 없을 경우
				tempMedialCode = getSingleMedial(i + 1, eng); // 그 다음의 중성 문자에 대한 코드 추출
				if (tempMedialCode != -1) { // 그 다음에 중성 문자가 존재할 경우
					finalCode = 0; // 종성 문자는 없음
					i--;
				} else { // 중성에서 false가 나올 때, ex)모음(ㅓ)에 자음(ㄱ)이 들어간 경우
					finalCode = getSingleFinal(i, eng); // 종성 문자 추출
					if (finalCode == -1) {// 종성이 없는 경우
						finalCode = 0;// 받침이 없다
					}
				}
			}
			// 추출한 초성 문자 코드, 중성 문자 코드, 종성 문자 코드를 합한 후 변환하여 스트링버퍼에 넘김
			sb.append((char) (0xAC00 + initialCode + medialCode + finalCode));
		}
		return sb.toString();// String 형으로 바꿔줌
	}

	private int getCode(CodeType type, String c) {
		// 초성
		String init[] = { "r", "R", "s", "e", "E", "f", "a", "q", "Q", "t", "T", "d", "w", "W", "c", "z", "x", "v",
				"g" };
		// 중성
		String mid[] = { "k", "o", "i", "O", "j", "p", "u", "P", "h", "hk", "ho", "hl", "y", "n", "nj", "np", "nl", "b",
				"m", "ml", "l" };
		// 종성
		String fin[] = { " ", "r", "R", "rt", "s", "sw", "sg", "e", "f", "fr", "fa", "fq", "ft", "fx", "fv", "fg", "a",
				"q", "qt", "t", "T", "d", "w", "c", "z", "x", "v", "g" };
		switch (type) {
		/*
		 * 유니코드 한글은 0xAC00 으로부터 초성 19개, 중상21개, 종성28개로 이루어지고 이들을 조합한 11,172개의 문자를 갖는다.
		 * 유니코드에서 한글의 특징은 아래와 같다. 한글 시작 코드 : 44032 (0xAC00) 한글 끝 코드 : 55199 (0xD79F) 초성
		 * 코드 : 588 중성 코드 : 28
		 */
		case chosung:
			for (int i = 0; i < init.length; i++) {
				if (init[i].equals(c)) {
					return i * 21 * 28;// 초성 반환
				}
			}
			break;
		case jungsung:

			for (int i = 0; i < mid.length; i++) {
				if (mid[i].equals(c)) {
					return i * 28; // 중성 반환
				}
			}
			break;
		case jongsung:
			for (int i = 0; i < fin.length; i++) {
				if (fin[i].equals(c)) {
					return i;// 종성 반환
				}
			}
			break;
		}
		return -1;// switch case문에서 false가 나오면 -1 반환
	}

	// 한 자로 된 중성값을 리턴한다
	// 인덱스를 벗어낫다면 -1을 리턴
	private int getSingleMedial(int i, String eng) {
		if ((i + 1) <= eng.length()) {
			return getCode(CodeType.jungsung, eng.substring(i, i + 1));
		} else {
			return -1;
		}
	}

	// 두 자로 된 중성을 체크하고, 있다면 값을 리턴한다
	// 없으면 리턴값은 -1
	private int getDoubleMedial(int i, String eng) {
		int result;// 값을 돌려주는 변수
		// 다음 문자가 있는지 확인할려고 if문 사용
		if ((i + 2) > eng.length()) {// eng의length를 넘어가면
			return -1;// -1반환
		} else {
			result = getCode(CodeType.jungsung, eng.substring(i, i + 2));
			if (result != -1) {
				return result;// 값이 있으면 결과 반환
			} else {
				return -1;// 값이 없으면 -1 반환
			}
		}
	}

	// 한 자로된 종성값을 리턴한다
	// 인덱스를 벗어낫다면 -1을 리턴
	private int getSingleFinal(int i, String eng) {
		if ((i + 1) <= eng.length()) {// eng의 length 범위 안에 들면
			return getCode(CodeType.jongsung, eng.substring(i, i + 1));// 값 리턴
		} else {
			return -1;// 범위 안에 못 들면 -1반환
		}
	}

	// 두 자로된 종성을 체크하고, 있다면 값을 리턴한다
	// 없으면 리턴값은 -1
	private int getDoubleFinal(int i, String eng) {
		if ((i + 2) > eng.length()) {
			return -1;
		} else {
			return getCode(CodeType.jongsung, eng.substring(i, i + 2));
		}
	}
}