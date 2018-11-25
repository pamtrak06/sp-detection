package rcp.utils;

import java.io.File;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public final class UiUtilities {
	public final static Shell getShell() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	}

	public final static void createLabelTextBrowseDirectory(String label,
			final Composite parent, final int minimumWidth) {
		final Text txt = createLabelText(label, parent, minimumWidth);
		Button btBrowse = new Button(parent, SWT.PUSH);
		btBrowse.setText("Browse...");
		GridData gd_btBrowse = new GridData(SWT.LEFT, SWT.CENTER, true, false,
				1, 1);
		btBrowse.setLayoutData(gd_btBrowse);
		btBrowse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(PlatformUI
						.getWorkbench().getActiveWorkbenchWindow().getShell(),
						SWT.NULL);
				String path = dialog.open();
				if (path != null) {
					File file = new File(path);
					if (file.isDirectory()) {
						txt.setText(file.getPath());
						txt.setToolTipText(file.getPath());
					}
				}
			}
		});

	}

	public final static Text createLabelTextBrowseFile(final String label,
			final String filterPath, final Composite parent,
			final int minimumWidth) {
		final Text txt = createLabelText(label, parent, minimumWidth);
		Button btBrowse = new Button(parent, SWT.PUSH);
		btBrowse.setText("Browse...");
		btBrowse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), SWT.NULL);
				dialog.setFilterPath(filterPath);
				String path = dialog.open();
				if (path != null) {

					File file = new File(path);
					if (file.isFile()) {
						txt.setText(file.getPath());
						txt.setToolTipText(file.getPath());
					}

				}
			}
		});
		return txt;
	}

	public static Text createLabelText(final String label,
			final Composite container, final int minimumWidth) {
		Label lblText = new Label(container, SWT.NONE);
		lblText.setText(label);
		GridData gridDataTxt = new GridData(SWT.RIGHT, SWT.CENTER, true, false,
				1, 1);
		lblText.setLayoutData(gridDataTxt);

		final Text txt = new Text(container, SWT.BORDER);
		GridDataFactory gdf = GridDataFactory.fillDefaults().grab(true, false);
		// .span(2, 1);
		gdf.minSize(minimumWidth, 0);
		gdf.applyTo(txt);

		return txt;
	}
}
