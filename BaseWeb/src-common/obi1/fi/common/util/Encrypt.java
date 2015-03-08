package obi1.fi.common.util;

public class Encrypt {

	private static final String VALID_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWZabcdefghijklmnopqrstuvwxyz";
	
	private static final Integer BYTE_SIZE = 4;
	
	/**
	 * Construtor padrão - não pode ser instanciado.
	 */
	private Encrypt() { }
	
	/**
	 * Criptografa uma string.
	 * @param key chave para criptografia
	 * @param value valor a ser criptografado
	 * @return string criptografada
	 */
	public static String encrypt(String key, String value) {
		
		String result = "";
		String preResult = "";
		
		int fator = 0;
		for (int i = 0; i < key.getBytes().length; i++) {
			fator += key.getBytes()[i] * (i + 1);
		}
		fator = (fator * key.length()) / (key.length() + 1);
		
		int newByte;
		for (int i = 0; i < value.getBytes().length; i++) {
			newByte = (value.getBytes()[i] + fator) - ((i + 1) * String.valueOf(fator).charAt(0));
			preResult += fillZero(String.valueOf(newByte), BYTE_SIZE);
		}

		preResult = preResult.replaceAll("-", "X");
		preResult = preResult.replaceAll("00", "Y");
		
		if ((preResult.length() % 2) == 1) { preResult = preResult.concat("."); }
		
		for (int i = 0; i < preResult.length(); i = i + 2) {
			final String aux = preResult.substring(i, i + 2);
			if ((!"0".equals(String.valueOf(aux.charAt(0)))) 
					&& aux.indexOf("X") < 0 && aux.indexOf("Y") < 0 && aux.indexOf(".") < 0 
					&& Integer.parseInt(aux) < VALID_CHARS.length()) {
				
				result = result.concat(String.valueOf(VALID_CHARS.charAt(Integer.parseInt(aux))));
			}
			else {
				result = result.concat(aux);
			}
		}

		preResult = result.replaceAll("\\.", "");
		result = "";
		
		for (int i = 0; i < preResult.length(); i++) {
			final String aux = String.valueOf(preResult.charAt(i));
			if (aux.indexOf("X") < 0 && aux.indexOf("Y") < 0 && VALID_CHARS.indexOf(aux) < 0) {
				result = result.concat(String.valueOf(VALID_CHARS.charAt(Integer.parseInt(aux))));
			}
			else {
				result = result.concat(aux);
			}
		}

		return result;
	}
	
	/**
	 * Descriptografa uma string.
	 * @param key chave para descriptografia
	 * @param value valor criptografado
	 * @return string descriptografada
	 */
	public static String decrypt(String key, String value) {
		
		String result = "";
		String preResult = "";

		for (int i = 0; i < value.length(); i++) {
			final String aux = String.valueOf(value.charAt(i));
			
			if (VALID_CHARS.indexOf(aux) >= 0) {
				preResult = preResult.concat(String.valueOf(VALID_CHARS.indexOf(aux)));
			}
			else {
				preResult = preResult.concat(aux);
			}
		}
		
		preResult = preResult.replaceAll("X", "-");
		preResult = preResult.replaceAll("Y", "00");

		int fator = 0;
		for (int i = 0; i < key.getBytes().length; i++) {
			fator += key.getBytes()[i] * (i + 1);
		}
		fator = (fator * key.length()) / (key.length() + 1);

		int newByte;
		int count = 0;
		for (int i = 0; i < preResult.length(); i = i + BYTE_SIZE) {
			final int aux = unFillZero(preResult.substring(i, i + BYTE_SIZE));
			newByte = (aux - fator) + ((count + 1) * String.valueOf(fator).charAt(0));
			result += new String(new byte[]{(byte) newByte});
			count++;
		}
		
		return result;
	}
	
	/**
	 * Preenche string com zeros a esquerda para completar um número especifico de caracteres.
	 * @param value string
	 * @param size quantos caracteres a string deve ter no final
	 * @return string com zeros a esquerda
	 */
	private static String fillZero(String value, int size) {
		String result = value;
		while (result.length() < size) {
			result = "0".concat(result);
		}
		return result;
	}
	
	/**
	 * Retorna o valor numérico de um número em formato string.
	 * @param value valor a ser convertido
	 * @return valor numérico
	 */
	private static int unFillZero(String value) {
		final boolean isNegative = value.indexOf("-") >= 0;
		String result = value.replaceAll("-", "");
		if (isNegative) { result = "-".concat(result); }
		return Integer.parseInt(result);
	}

}
