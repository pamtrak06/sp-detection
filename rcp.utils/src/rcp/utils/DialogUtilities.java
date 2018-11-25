package rcp.utils;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public final class DialogUtilities {
	public static final String showFileDialog(String path, String extension,
			String extensionName) {
		FileDialog dialog = new FileDialog(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), SWT.NULL);
		dialog.setFilterPath(path);
		dialog.setFilterExtensions(new String[] { extension });
		dialog.setFilterNames(new String[] { extensionName });
		String file = dialog.open();
		if (!file.endsWith(extension)) {
			file += extension;
		}
		return file;
	}

	public static final String showInputDialog(final Shell shell,final String title, final String message) {
		InputDialog dialog = new InputDialog(shell, title, message, "", null );
		dialog.open();
		return dialog.getValue();
	}

	/**
	 * This class validates a String. It makes sure that the String is between 5
	 * and 8 characters
	 */
	class StringValidator implements IInputValidator {
		/**
		 * Validates the String. Returns null for no error, or an error message
		 * 
		 * @param newText
		 *            the String to validate
		 * @return String
		 */
		public String isValid(String newText) {
			if (!Character.isUpperCase((newText.charAt(0)))) {
				return "First letter must be upper case";
			}
			boolean isSpace = false;
			for (char c : newText.toCharArray()) {
				if (Character.isWhitespace(c)) {
					isSpace = true;
				}
			}
			if (isSpace) {
				return "String must not contain white space";
			}

			// Input must be OK
			return null;
		}
	}

}
