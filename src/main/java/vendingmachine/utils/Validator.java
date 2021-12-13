package vendingmachine.utils;

import java.util.ArrayList;
import java.util.HashMap;

import vendingmachine.models.Item;

public class Validator {
	private static final String NUMBER_PATTERN = "^[0-9]+$";
	private static final String NAME_PATTERN = "^[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣]+$";
	private static final int ELEMENT_SIZE = 3;
	private static final int MIN_MONEY_VALUE = 10;

	private static void validateEmptyStr(String input) throws IllegalArgumentException {
		if (input.isEmpty()) {
			throw new IllegalArgumentException(
				Messages.ERROR_BLANK.getValue() + Messages.COMMON_LINE_BREAK_MSG.getValue());
		}
	}

	private static void validatePositive(String input) throws IllegalArgumentException {
		if (Integer.valueOf(input) <= 0) {
			throw new IllegalArgumentException(
				Messages.ERROR_NOT_POSITIVE.getValue() + Messages.COMMON_LINE_BREAK_MSG.getValue());
		}
	}

	private static void validateNumber(String input) throws IllegalArgumentException {
		if (!input.matches(NUMBER_PATTERN)) {
			throw new IllegalArgumentException(
				Messages.ERROR_NOT_NUMBER.getValue() + Messages.COMMON_LINE_BREAK_MSG.getValue());
		}
	}

	private static void validateName(String input) throws IllegalArgumentException {
		if (!input.matches(NAME_PATTERN)) {
			throw new IllegalArgumentException(
				Messages.ERROR_NOT_VALID_NAME.getValue() + Messages.COMMON_LINE_BREAK_MSG.getValue());
		}
	}

	private static void validateRemainder(String input, int minValue) throws IllegalArgumentException {
		if (Integer.valueOf(input) % minValue != 0) {
			throw new IllegalArgumentException(
				Messages.ERROR_NOT_DIVIDABLE_MESSAGE.getValue() + Messages.COMMON_LINE_BREAK_MSG.getValue());
		}
	}

	public static void validateElementSize(String[] itemInput) throws IllegalArgumentException {
		if (itemInput.length != ELEMENT_SIZE)
			throw new IllegalArgumentException(
				Messages.ERROR_NOT_VALID_ELEMENT_SIZE.getValue() + Messages.COMMON_LINE_BREAK_MSG.getValue());
	}

	public static void validateElementCondition(ArrayList<String[]> itemInput) throws IllegalArgumentException {
		for (String[] eachParsedItem : itemInput) {
			validateElementSize(eachParsedItem);
			validateNameInput(eachParsedItem[Indexes.NAME_INDEX.getValue()]);
			validatePriceInput(eachParsedItem[Indexes.PRICE_INDEX.getValue()]);
			validateAmountInput(eachParsedItem[Indexes.AMOUNT_INDEX.getValue()]);
		}
	}

	public static void validateItemSize(String itemInput, char delimiter) throws IllegalArgumentException {
		validateEmptyStr(itemInput);
		int totalSemiColon = (int)itemInput.chars().filter(c -> c == delimiter).count();
		if (itemInput.split(String.valueOf(delimiter)).length != totalSemiColon - 1) {
			throw new IllegalArgumentException(Messages.ERROR_NOT_VALID_ITEM_SIZE.getValue() + Messages.COMMON_LINE_BREAK_MSG.getValue());
		}
	}

	public static void isItemName(HashMap<String, Item> itemList, String inputName) throws IllegalArgumentException {
		if (!itemList.containsKey(inputName)) {
			throw new IllegalArgumentException(
				Messages.ERROR_NOT_CONTAIN_MESSAGE.getValue() + Messages.COMMON_LINE_BREAK_MSG.getValue());
		}
	}

	public static void validatePayAmount(HashMap<String, Item> itemList, String inputName, int pay) throws
		IllegalArgumentException {
		if (itemList.get(inputName).isOverThisPrice(pay)) {
			throw new IllegalArgumentException(
				Messages.ERROR_NOT_ENOUGH_MONEY_MESSAGE.getValue() + Messages.COMMON_LINE_BREAK_MSG.getValue());
		}
	}

	public static void validateBuyAmount(HashMap<String, Item> itemList, String inputName) throws
		IllegalArgumentException {
		if (itemList.get(inputName).isAmountZero()) {
			throw new IllegalArgumentException(
				Messages.ERROR_SOLD_OUT_MESSAGE.getValue() + Messages.COMMON_LINE_BREAK_MSG.getValue());
		}
	}

	public static void validatePriceInput(String input) {
		validateEmptyStr(input);
		validateNumber(input);
		validatePositive(input);
		validateRemainder(input, MIN_MONEY_VALUE);
	}

	public static void validateAmountInput(String input) {
		validateEmptyStr(input);
		validateNumber(input);
		validatePositive(input);
	}

	public static void validateNameInput(String input) {
		validateEmptyStr(input);
		validateName(input);
	}
}
