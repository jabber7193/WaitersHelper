//WaiterselperGUI.java created by Alonzo Couture
//Text field verification code thanks to Bhavesh Sanghvi http://openbiomind-gui.blogspot.com/2008/07/creating-numonly-text-field-using.html

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.events.*;

public class WaitersHelperGUI extends ApplicationWindow {
	private Text amtMadeTB;
	private Text amtDepTB;
	private static Text totMadeTB;
	private static Text totDepTB;
	private static List list;

	/**
	 * Create the application window.
	 */
	public WaitersHelperGUI() {
		super(null);
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
	}

	/**
	 * Create contents of the application window.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);
		{
			Label amtMadeLbl = new Label(container, SWT.NONE);
			amtMadeLbl.setFont(SWTResourceManager.getFont("Segoe UI Semibold",
					12, SWT.NORMAL));
			amtMadeLbl.setBounds(10, 10, 124, 25);
			amtMadeLbl.setText("Amount Made:");
		}
		{
			amtMadeTB = new Text(container, SWT.BORDER);
			amtMadeTB.setBounds(10, 41, 156, 25);
			amtMadeTB.addVerifyListener(new VerifyListener() { //Allow only digits as input into text box  				  
			    @Override  
			    public void verifyText(final VerifyEvent event) {  
			        switch (event.keyCode) {  
			            case SWT.BS:           // Backspace  
			            case SWT.DEL:          // Delete  
			            case SWT.HOME:         // Home  
			            case SWT.END:          // End  
			            case SWT.ARROW_LEFT:   // Left arrow  
			            case SWT.ARROW_RIGHT:  // Right arrow  
			                return;  
			        }  
			  
			        if (!Character.isDigit(event.character)) {  
			            event.doit = false;  // disallow the action  
			        }  
			    }  
			  
			}); 
		}
		{
			amtDepTB = new Text(container, SWT.BORDER);
			amtDepTB.setBounds(10, 128, 156, 25);
			amtDepTB.addVerifyListener(new VerifyListener() {  //Allow only digits as input into text box				  
			    @Override  
			    public void verifyText(final VerifyEvent event) {  
			        switch (event.keyCode) {  
			            case SWT.BS:           // Backspace  
			            case SWT.DEL:          // Delete  
			            case SWT.HOME:         // Home  
			            case SWT.END:          // End  
			            case SWT.ARROW_LEFT:   // Left arrow  
			            case SWT.ARROW_RIGHT:  // Right arrow  
			                return;  
			        }  
			  
			        if (!Character.isDigit(event.character)) {  
			            event.doit = false;  // disallow the action  
			        }  
			    }  
			  
			}); 
		}
		{
			Button editBtn = new Button(container, SWT.NONE);
			editBtn.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						EditWindow window = new EditWindow();
						window.open();
					} catch (Exception x) {
						x.printStackTrace();
					}
					refresh();
				}
			});
			editBtn.setBounds(10, 223, 150, 25);
			editBtn.setText("&Edit");
		}
		{
			Button exitBtn = new Button(container, SWT.NONE);
			exitBtn.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					System.exit(0);
				}
			});
			exitBtn.setBounds(10, 309, 150, 25);
			exitBtn.setText("E&xit");
		}
		{
			Button delBtn = new Button(container, SWT.NONE);
			delBtn.setBounds(10, 265, 150, 25);
			delBtn.setText("&Delete");
		}
		{
			Button updateBtn = new Button(container, SWT.PUSH);
			updateBtn.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {  //disallows null entries
					if (amtMadeTB.getText().equals("")
							| amtDepTB.getText().equals("")) {
						MessageDialog.openError(new Shell(),
								"No Data Entered!",
								"Please enter data into BOTH fields!");

					} else {
						String tempMade = amtMadeTB.getText();//get user entries
						String tempDep = amtDepTB.getText();

						float made = Float.parseFloat(tempMade);
						float dep = Float.parseFloat(tempDep);
						
						if( made < dep | made < 0 | dep < 0) { //check for negative
							MessageDialog.openError(new Shell(), "Error!", "Please re-check entries for accuracy. Amount deposited" + 
						    " cannot be more than amount made, or amount cannot be less than 0.");
						} else {
                            MoneyDB.insertDB(dep, made);
                            clearForm();
						}
					}

					amtMadeTB.setFocus();

					refresh();

				}

			});
			parent.getShell().setDefaultButton(updateBtn);// set Update button
															// as default
			updateBtn.setBounds(10, 183, 150, 25);
			updateBtn.setText("&Update");
		}

		Label amtDepLbl = new Label(container, SWT.NONE);
		amtDepLbl.setFont(SWTResourceManager.getFont("Segoe UI Semibold", 12,
				SWT.NORMAL));
		amtDepLbl.setBounds(10, 97, 156, 25);
		amtDepLbl.setText("Amount Deposited:");

		Label totDepLbl = new Label(container, SWT.NONE);
		totDepLbl.setBounds(193, 102, 150, 15);
		totDepLbl.setText("Total Amount Deposited:");

		Label totMadeLbl = new Label(container, SWT.NONE);
		totMadeLbl.setBounds(193, 15, 130, 15);
		totMadeLbl.setText("Total Amount Made:");
		{
			totMadeTB = new Text(container, SWT.BORDER);
			totMadeTB.setEnabled(false);
			totMadeTB.setText(Float.toString(MoneyDB.sumColDB("AMTMADE")));
			totMadeTB.setBounds(193, 45, 100, 21);
		}
		{
			totDepTB = new Text(container, SWT.BORDER);
			totDepTB.setEnabled(false);
			totDepTB.setText(Float.toString(MoneyDB.sumColDB("AMTDEP")));
			totDepTB.setBounds(193, 128, 100, 21);
		}
		{
			list = new List(container, SWT.BORDER | SWT.V_SCROLL);
			list.setBounds(341, 10, 273, 349);
			MoneyDB.printDB();

		}

		return container;
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Create the menu manager.
	 * 
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager("menu");
		return menuManager;
	}

	/**
	 * Create the toolbar manager.
	 * 
	 * @return the toolbar manager
	 */
	@Override
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);
		return toolBarManager;
	}

	/**
	 * Create the status line manager.
	 * 
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {

		new MoneyDB();

		try {
			WaitersHelperGUI window = new WaitersHelperGUI();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell.
	 * 
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Waiter's Helper");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(640, 480);
	}

	public static void populate(int id, String date, float amtmade, float amtdep) {// adds
																					// DB
																					// items
																					// to
																					// a
																					// list
		list.add(id + "   " + date + "   " + amtmade + "   " + amtdep);
	}// end poulate method

	private void refresh() {// refreshes GUI list and totals
		
		
		totMadeTB.setText(Float.toString(MoneyDB.sumColDB("AMTMADE")));// refresh
																		// totals
		totDepTB.setText(Float.toString(MoneyDB.sumColDB("AMTDEP")));

		list.removeAll();// refresh list
		MoneyDB.printDB();
	}// end refresh
	
	private void clearForm(){
		amtMadeTB.setText("");
		amtDepTB.setText("");
	}
}
