package Helper;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class IntegerDocumentFilter extends DocumentFilter {
	@Override
	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
			throws BadLocationException {
		if (isValidInput(fb.getDocument().getText(0, fb.getDocument().getLength()) + string)) {
			super.insertString(fb, offset, string, attr);
		}
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
			throws BadLocationException {
		String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
		String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

		if (isValidInput(newText)) {
			super.replace(fb, offset, length, text, attrs);
		}
	}

	private boolean isValidInput(String text) {
		if (text.isEmpty()) {
			return true;
		}
		try {
			int value = Integer.parseInt(text);
			return value >= 1 && value <= 9;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
