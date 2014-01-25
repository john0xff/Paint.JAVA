package heroesgrave.paint.gui.colourChooser;

import heroesgrave.paint.gui.Menu.CentredJDialog;
import heroesgrave.paint.main.Paint;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class MultiColourChooser
{
	
	/**                                              **/
	/**                                              **/
	/**                                              **/
	/**                 INNER CLASSES                **/
	/**                                              **/
	/**                                              **/
	/**                                              **/
	
	@SuppressWarnings("serial")
	private class ColorChooser_HexColor extends JTextField implements ActionListener
	{
		
		public ColorChooser_HexColor(Container parent)
		{
			super(Integer.toHexString(leftColour).toUpperCase());
			this.addActionListener(this);
			this.setToolTipText("Type in any Hexadecimal Color, then press Enter to apply the change.");
		}
		
		private void reset()
		{
			if(isEditingLeft)
			{
				
				invokeTextChangeLater("FFFFFF");
				leftColour = (leftColour & 0xFF000000) | 0xFFFFFF;
			}
			else
			{
				invokeTextChangeLater("000000");
				rightColour = (rightColour & 0xFF000000) | 0xFFFFFF;
			}
		}
		
		private void invokeTextChangeLater(final String newText)
		{
			SwingUtilities.invokeLater(new Runnable()
			{
				@Override public void run()
				{
					setText(newText);
				}
			});
		}
		
		@Override public void actionPerformed(ActionEvent event)
		{
			
			try
			{
				int newColor = Integer.valueOf(getText().toLowerCase(), 16);
				
				if(isEditingLeft)
				{
					leftColour = (leftColour & 0xFF000000) | newColor;
				}
				else
				{
					rightColour = (rightColour & 0xFF000000) | newColor;
				}
				
			}
			catch(Exception e)
			{
				// Fully ignore all exceptions and just reset things!
				// Resetting things should put everything back in order.
				reset();
			}
			
			updatePaintGUI();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**                                              **/
	/**                                              **/
	/**                                              **/
	/**                 OBJECT FIELDS                **/
	/**                                              **/
	/**                                              **/
	/**                                              **/
	
	JDialog dialog;
	SpringLayout dialogLayout;
	
	JPanel chooserLeft;
	SpringLayout chooserLeftLayout;
	JPanel chooserRight;
	SpringLayout chooserRightLayout;
	
	JPanel chooserRightRGB;
	JPanel chooserRightHEX;
	JPanel chooserRightHSV;
	JPanel chooserRightALPHA;
	
	/**
	 * The 'left'-colour.
	 **/
	private int leftColour = 0xFFFFFF;
	
	/**
	 * The 'right'-colour.
	 **/
	private int rightColour = 0;
	
	/**
	 * Flag that tells us if we are editing the LEFT or RIGHT color.
	 **/
	private boolean isEditingLeft = true;
	
	
	/**                                              **/
	/**                                              **/
	/**                                              **/
	/**               OBJECT CONSTRUCTOR             **/
	/**                                              **/
	/**                                              **/
	/**                                              **/
	
	public MultiColourChooser(JFrame mainFrame)
	{
		
		// ----- Create the Dialog
		dialog = new CentredJDialog(mainFrame, "Colour-Chooser");
		dialogLayout = new SpringLayout();
		
		// ----- Do the typical configurations for it.
		dialog.setSize(400, 300);
		dialog.setResizable(false);
		dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		dialog.setLayout(dialogLayout);
		
		// ----- Build the LEFT/RIGHT components and set their layout.
		// build left
		chooserLeft = new JPanel();
		chooserLeftLayout = new SpringLayout();
		chooserLeft.setLayout(chooserLeftLayout);
		
		// build right
		chooserRight = new JPanel();
		chooserRightLayout = new SpringLayout();
		chooserRight.setLayout(chooserRightLayout);
		
		// temporary stuff
		chooserLeft.setBackground(Color.YELLOW);
		chooserRight.setBackground(Color.GREEN);
		
		// build layout
		buildLayoutForChooserRoot();
		
		// add
		dialog.add(chooserLeft);
		dialog.add(chooserRight);
		
		chooserLeft.add(new JLabel("LEFT"));
		
		// ----- Construction of chooser content LEFT
		// TODO: implement the left side.
		buildLayoutForChooserLeftContent();
		
		// ----- Construction of chooser content RIGHT
		// build
		chooserRightRGB = new JPanel();
		chooserRightHEX = new JPanel();
		chooserRightHSV = new JPanel();
		chooserRightALPHA = new JPanel();
		
		// border
		chooserRightRGB.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2),"RGB"));
		chooserRightHEX.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2),"HEX"));
		chooserRightHSV.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2),"HSV"));
		chooserRightALPHA.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2),"Alpha"));
		
		// inner layout
		chooserRightHEX.setLayout(new BorderLayout());
		chooserRightALPHA.setLayout(new BorderLayout());
		
		// temporary settings and changes to make building easier
		chooserRightRGB.setBackground(Color.RED);
		chooserRightHEX.setBackground(Color.LIGHT_GRAY);
		chooserRightHSV.setBackground(Color.MAGENTA);
		chooserRightALPHA.setBackground(Color.GRAY);
		chooserRightRGB.add(new JLabel("RGB"));
		chooserRightHEX.add(new ColorChooser_HexColor(chooserRightHEX));
		chooserRightHSV.add(new JLabel("HSV"));
		chooserRightALPHA.add(new JSpinner(new SpinnerNumberModel(0,0,255,1)));
		
		buildLayoutForChooserRightContent();
		
		chooserRight.add(chooserRightRGB);
		chooserRight.add(chooserRightHEX);
		chooserRight.add(chooserRightHSV);
		chooserRight.add(chooserRightALPHA);
		
		
		
		// ----- ???
		
		
		
	}
	
	private void buildLayoutForChooserRoot() {
		// (only for construction) Fetch the contentPane from the dialog.
		Container dialogContentPane = dialog.getContentPane();
		
		// layout (top/bottom springs)
		dialogLayout.putConstraint(SpringLayout.NORTH, chooserLeft, 0, SpringLayout.NORTH, dialogContentPane);
		dialogLayout.putConstraint(SpringLayout.NORTH, chooserRight, 0, SpringLayout.NORTH, dialogContentPane);
		dialogLayout.putConstraint(SpringLayout.SOUTH, chooserLeft, 0, SpringLayout.SOUTH, dialogContentPane);
		dialogLayout.putConstraint(SpringLayout.SOUTH, chooserRight, 0, SpringLayout.SOUTH, dialogContentPane);
		
		// layout (left/right springs)
		dialogLayout.putConstraint(SpringLayout.WEST, chooserLeft, 0, SpringLayout.WEST, dialogContentPane);
		dialogLayout.putConstraint(SpringLayout.EAST, chooserRight, 0, SpringLayout.EAST, dialogContentPane);
		
		//split-point
		dialogLayout.putConstraint(SpringLayout.EAST, chooserLeft, 0, SpringLayout.WEST, chooserRight); // MORE LEFT
		dialogLayout.putConstraint(SpringLayout.WEST, chooserRight, -(128+32), SpringLayout.EAST, dialogContentPane); // MORE RIGHT
		
	}
	
	private void buildLayoutForChooserLeftContent() {
		Container contentPane = chooserLeft;
		
		
	}
	
	private void buildLayoutForChooserRightContent() {
		Container contentPane = chooserRight;
		// chooserRightLayout
		
		// link left springs
		chooserRightLayout.putConstraint(SpringLayout.WEST, chooserRightRGB, 0, SpringLayout.WEST, contentPane);
		chooserRightLayout.putConstraint(SpringLayout.WEST, chooserRightHEX, 0, SpringLayout.WEST, contentPane);
		chooserRightLayout.putConstraint(SpringLayout.WEST, chooserRightHSV, 0, SpringLayout.WEST, contentPane);
		chooserRightLayout.putConstraint(SpringLayout.WEST, chooserRightALPHA, 0, SpringLayout.WEST, contentPane);
		
		// link right springs
		chooserRightLayout.putConstraint(SpringLayout.EAST, chooserRightRGB, 0, SpringLayout.EAST, contentPane);
		chooserRightLayout.putConstraint(SpringLayout.EAST, chooserRightHEX, 0, SpringLayout.EAST, contentPane);
		chooserRightLayout.putConstraint(SpringLayout.EAST, chooserRightHSV, 0, SpringLayout.EAST, contentPane);
		chooserRightLayout.putConstraint(SpringLayout.EAST, chooserRightALPHA, 0, SpringLayout.EAST, contentPane);
		
		// link top/bottoms together (nightmare)
		chooserRightLayout.putConstraint(SpringLayout.NORTH, chooserRightRGB, 0, SpringLayout.NORTH, contentPane);
		chooserRightLayout.putConstraint(SpringLayout.NORTH, chooserRightHEX, 0, SpringLayout.SOUTH, chooserRightRGB);
		chooserRightLayout.putConstraint(SpringLayout.NORTH, chooserRightHSV, 0, SpringLayout.SOUTH, chooserRightHEX);
		chooserRightLayout.putConstraint(SpringLayout.SOUTH, chooserRightHSV, 0, SpringLayout.NORTH, chooserRightALPHA);
		chooserRightLayout.putConstraint(SpringLayout.SOUTH, chooserRightALPHA, 0, SpringLayout.SOUTH, contentPane);
		
		
		
	}

	/**                                              **/
	/**                                              **/
	/**                                              **/
	/**             STATE CHANGING METHODS           **/
	/**                                              **/
	/**                                              **/
	/**                                              **/

	/**
	 * This method makes the colour-chooser show up.
	 **/
	public void show()
	{
		// Check if the chooser is already visible.
		// We do this because we don't wan't to spend an awful lot of time on the Swing-side of Java internally changing states.
		if(!dialog.isVisible())
			dialog.setVisible(true);
		
	}
	
	/**
	 * This method makes the colour-chooser invisible again.
	 **/
	public void hide()
	{
		// Check if the chooser is already invisible.
		// We do this because we don't wan't to spend an awful lot of time on the Swing-side of Java internally changing states.
		if(dialog.isVisible())
			dialog.setVisible(false);
		
	}
	
	/**
	 * This method toggles the visibility of the colour-chooser.
	 **/
	public void toggle()
	{
		// If the dialog is visible, make it invisible.
		// If the dialog is invisible, make it visible.
		dialog.setVisible(!dialog.isVisible());
		
	}
	
	public void updatePaintGUI()
	{
		Paint.main.setLeftColour(leftColour);
		Paint.main.setRightColour(rightColour);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**                                              **/
	/**                                              **/
	/**                                              **/
	/**              SETTERS AND GETTERS             **/
	/**                                              **/
	/**                                              **/
	/**                                              **/
	
	/**
	 * Changes the 'Left' (primary) colour to the given color.
	 * 
	 * @param packedColorARGB The colour to change to.
	 **/
	public void setLeftColour(int packedColorARGB)
	{
		leftColour = packedColorARGB;
	}
	
	/**
	 * Changes the 'Right' (secondary) colour to the given color.
	 * 
	 * @param packedColorARGB The colour to change to.
	 **/
	public void setRightColour(int packedColorARGB)
	{
		rightColour = packedColorARGB;
	}
	
	/**
	 * Returns the 'Left' (primary) colour.
	 * @return The 'Left' (primary) colour.
	 **/
	public int getLeftColour()
	{
		return leftColour;
	}
	
	/**
	 * Returns the 'Right' (secondary) colour.
	 * @return The 'Right' (secondary) colour.
	 **/
	public int getRightColour()
	{
		return rightColour;
	}
	
	/**
	 * Return's the actual JDialog instance that is the root for the components of the colour-chooser.<br><br>
	 * The colour-choosers JDialog should <b>never</b> be modified by another class except by itself.<br>
	 * Adding listeners to it is an exception, as they don't actually change the JDialog.<br>
	 * 
	 * @return The colour-choosers JDialog instance.
	 **/
	public JDialog getDialog()
	{
		return dialog;
	}
	
	/**
	 * @return <i>True</i>, if the chooser is currently visible. <i>False</i> if not.
	 **/
	public boolean isVisible()
	{
		return dialog.isVisible();
	}
	
}
