

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class EditWindow {
	private Text idTB;
	private Text amtMadeTB;
	private Text amtDepTB;

	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		Display display = Display.getDefault();
		final Shell shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("Edit Entry");
		
		Label lblEntryId = new Label(shell, SWT.NONE);
		lblEntryId.setBounds(178, 10, 55, 15);
		lblEntryId.setText("Entry ID#:");
		
		idTB = new Text(shell, SWT.BORDER);
		idTB.setBounds(145, 41, 125, 25);
		
		Label lblAmountMade = new Label(shell, SWT.NONE);
		lblAmountMade.setBounds(48, 84, 125, 15);
		lblAmountMade.setText("Amount Made:");
		
		amtMadeTB = new Text(shell, SWT.BORDER);
		amtMadeTB.setBounds(48, 115, 125, 25);
		
		Label lblAmountDeposited = new Label(shell, SWT.NONE);
		lblAmountDeposited.setBounds(243, 84, 125, 15);
		lblAmountDeposited.setText("Amount Deposited:");
		
		amtDepTB = new Text(shell, SWT.BORDER);
		amtDepTB.setBounds(243, 115, 125, 25);
		
		Button acceptBtn = new Button(shell, SWT.NONE);
		acceptBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (idTB.getText() == "" || amtMadeTB.getText() == "" || amtDepTB.getText() == ""){
					MessageDialog.openError(new Shell(), "Data Error!", "Please enter data into All fields!");
				} else {
				int id = Integer.parseInt(idTB.getText());
				float amtMade = Integer.parseInt(amtMadeTB.getText());
				float amtDep = Integer.parseInt(amtDepTB.getText());
				
				MoneyDB.updateDB(id, amtMade, amtDep);
				}
				shell.dispose();
			}
		});
		acceptBtn.setBounds(48, 204, 125, 25);
		acceptBtn.setText("&Accept");
		
		Button cancelBtn = new Button(shell, SWT.NONE);
		cancelBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		cancelBtn.setBounds(243, 204, 125, 25);
		cancelBtn.setText("&Cancel");

		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
