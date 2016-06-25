
/* 
 * By Chris Schultz
 * Comp424 Project 1
 * Caesar Cipher program
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

public class CeasarCipher extends JFrame {

	private JEditorPane inputText;
	private JEditorPane outputText;
	private JEditorPane keyText;
	private JComboBox<Integer> shiftNumber; //combobox for caesar cipher
	private JComboBox<Integer> roundNumber; //combobox to choose rounds for feistel
	private JScrollPane inputScrollPane;
	private JScrollPane outputScrollPane;
	private JScrollPane keyScrollPane;
	private String finishedFeistel; //string with the final encrypted or decrypted message
	
	
	public static void main(String[] args) {
		new CeasarCipher();

	}

	public CeasarCipher() {
		
		//All of panel setup
		setTitle("Cipher Text 1.1");

		JPanel panelTop = new JPanel();
		panelTop.setBackground(new Color(0,0,0));
		
		this.setSize(500, 800);
		this.setLocationRelativeTo(null);
		panelTop.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(0, 25, 60, 25), new EtchedBorder()));
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		inputText = new JEditorPane();
		inputText.setEditable(true);
		inputText.setToolTipText("Click here and enter text you want to encrypt");
		inputText.setFont(new Font(inputText.getName(), Font.PLAIN, 18));
		inputScrollPane = new JScrollPane(inputText);
		inputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		inputScrollPane.setPreferredSize(new Dimension(400, 195));
		inputScrollPane.setMinimumSize(new Dimension(10, 10));
		
		outputText = new JEditorPane();
		outputText.setEditable(true);
		outputText.setToolTipText("Click here and enter text you want to decrypt");
		outputText.setFont(new Font(outputText.getName(), Font.PLAIN, 18));
		outputScrollPane = new JScrollPane(outputText);
		outputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		outputScrollPane.setPreferredSize(new Dimension(400, 195));
		outputScrollPane.setMinimumSize(new Dimension(10, 10));
		
		keyText = new JEditorPane();
		keyText.setEditable(true);
		keyText.setToolTipText("Enter key for Feistel Cipher");
		keyText.setFont(new Font(keyText.getName(), Font.PLAIN, 18));
		keyScrollPane = new JScrollPane(keyText);
		keyScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		keyScrollPane.setPreferredSize(new Dimension(400, 50));
		keyScrollPane.setMinimumSize(new Dimension(10, 10));
		
		
		
		
		JButton encryptButton = new JButton("Caesar Encrypt");
		JButton decryptButton = new JButton("Caesar Decrypt");
		
		JButton bruteForce = new JButton("Brute Force Caesar");
		JButton clearText = new JButton("Clear all text");
		
		JButton feistelEncryptButton = new JButton("Feistel Encrypt");
		
		JButton feistelDecryptButton = new JButton("Feistel Decrypt");
		
		Integer[] numbers = {0,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25};
		shiftNumber = new JComboBox<>(numbers);
		shiftNumber.setSelectedIndex(3);
		
		Integer[] evenNumbers = {0,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30};
		roundNumber = new JComboBox<>(evenNumbers);
		roundNumber.setSelectedIndex(4);
		
		//action listeners for feistel cipher
		feistelEncryptButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				feistelEncrypt(inputText.getText().toString().trim());
				outputText.setText(finishedFeistel);
				inputText.setText("");
			}
        });
		feistelDecryptButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				feistelEncrypt(outputText.getText().toString().trim());
				//feistelEncrypt(finishedFeistel);
				inputText.setText(finishedFeistel);
				outputText.setText("");
				
			}
        });
		
		//action listeners for caesar cipher
		encryptButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				encryptText();
				inputText.setText("");
			}
        });
		decryptButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e)
            {
				decryptText();
				outputText.setText("");
            }
        }); 
		
		//brute force for caesar cipher
		bruteForce.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				bruteForce();
				inputScrollPane.getVerticalScrollBar().setValue(0);
				outputScrollPane.getVerticalScrollBar().setValue(0);
			}
        });
		
		//clears both text boxes but not key
		clearText.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				inputText.setText("");
				outputText.setText("");
			}
        });
		
		//all the text all the panel and formatting
		
		JLabel title = new JLabel("Cipher Text 1.1");
		title.setForeground(Color.cyan);
		title.setFont(new Font(title.getName(), Font.BOLD, 40));
		title.setBorder(new EmptyBorder(0, 80, 20, 80));
		
		
		JLabel comboLabel = new JLabel("Caesar Key:");
		comboLabel.setFont(new Font(comboLabel.getName(), Font.BOLD, 18));
		comboLabel.setForeground(Color.cyan);
		
		JLabel roundLabel = new JLabel("Feistel Rounds: ");
		roundLabel.setFont(new Font(comboLabel.getName(), Font.BOLD, 18));
		roundLabel.setForeground(Color.cyan);
		roundLabel.setBorder(new EmptyBorder(0, 15, 0, 0));
		
		JLabel encryptMessage = new JLabel("Enter the message you want to encrypt here:");
		encryptMessage.setFont(new Font(encryptMessage.getName(), Font.BOLD, 18));
		encryptMessage.setForeground(Color.cyan);
		encryptMessage.setBorder(new EmptyBorder(15, 80, 0, 80));
		
		JLabel decryptMessage = new JLabel("Enter the message you want to decrypt here:");
		decryptMessage.setFont(new Font(encryptMessage.getName(), Font.BOLD, 18));
		decryptMessage.setForeground(Color.cyan);
		decryptMessage.setBorder(new EmptyBorder(15, 80, 0, 80));
		
		JLabel keyMessage = new JLabel("Feistel Key (Numeric)");
		keyMessage.setForeground(Color.cyan);
		keyMessage.setFont(new Font(title.getName(), Font.BOLD, 18));
		keyMessage.setBorder(new EmptyBorder(15, 100, 0, 100));
		
		
		//adds all components to the panel 
		
		panelTop.add(title);
		panelTop.add(comboLabel);
		panelTop.add(shiftNumber);
		panelTop.add(roundLabel);
		panelTop.add(roundNumber);
		panelTop.add(keyMessage);
		panelTop.add(keyScrollPane);
		panelTop.add(encryptMessage);
		
		panelTop.add(inputScrollPane);
		panelTop.add(encryptButton);
		panelTop.add(feistelEncryptButton);
		panelTop.add(clearText);
		panelTop.add(decryptMessage);
		panelTop.add(outputScrollPane);
		panelTop.add(decryptButton);
		panelTop.add(feistelDecryptButton);
		panelTop.add(bruteForce);
		this.add(panelTop);
		this.setVisible(true);

	}
	
	//encrypt for caesar cipher
	public void encryptText(){
		String tempInputText = inputText.getText();
		int keyLength = shiftNumber.getSelectedIndex();
		String encrypted= "";
		for(int i=0;i<tempInputText.length();i++)
	    {
	       
	        int c=tempInputText.charAt(i);
	        
	        if(Character.isUpperCase(c))
	        {
	            c+=(keyLength%26);
	         
	            if(c>'Z')
	                c-=26;
	        }	        
	        else if(Character.isLowerCase(c))
	        {
	            c+=(keyLength%26);
	            
	            if(c>'z')
	                c-=26;
	        }
	        encrypted=encrypted+(char) c;
	    }
		outputText.setText(encrypted);
		inputText.setCaretPosition(0);
		outputText.setCaretPosition(0);
	}
	
	//decrypt for caesar cipher
	
	public void decryptText(){
		String tempOutputText = outputText.getText();
		int keyLength = shiftNumber.getSelectedIndex();
		String decrypted= "";
	    for(int i=0;i<tempOutputText.length();i++)
	    {
	        
	        int c=tempOutputText.charAt(i);
	        
	        if(Character.isUpperCase(c))
	        {
	            c-=(keyLength%26);
	            
	            if(c<'A')
	                c+=26;
	        }
	        
	        else if(Character.isLowerCase(c))
	        {
	            c-=(keyLength%26);
	            
	            if(c<'a')
	                c+=26;
	        }
	        
	        decrypted+=(char) c;
    }
	    inputText.setText(decrypted);
	    inputText.setCaretPosition(0);
		outputText.setCaretPosition(0);
	}
	
	//brute force for caesar cipher
	
	public void bruteForce(){
		String tempOutputText = outputText.getText();
		String decrypted="";
		int keyLength = 0;
		
		while(keyLength<26){
			decrypted+=keyLength+ ": ";
			for(int i=0;i<tempOutputText.length();i++){
		        int c=tempOutputText.charAt(i);
		        
		        if(Character.isUpperCase(c))
		        {
		            c-=(keyLength%26);
		            
		            if(c<'A')
		                c+=26;
		        }
		        
		        else if(Character.isLowerCase(c))
		        {
		            c-=(keyLength%26);
		            
		            if(c<'a')
		                c+=26;
		        }
		        
		        decrypted+=(char) c;
		        
		        inputText.setText(decrypted);
		        
			    
			} //end for loop

		decrypted+="\n\n"; //double space
		keyLength++; //check the next key
    } //end while
		inputText.setCaretPosition(0);
		outputText.setCaretPosition(0);

	}
	
	//feistel encrypt and decrypt method
	
	public void feistelEncrypt(String message){
		String hello = "Hello";
		byte[] cipherString = message.getBytes();
		int rounds = (int) roundNumber.getSelectedItem(); //get round from combobox
		
		
		char[] key = keyText.getText().toString().toCharArray(); //get the key the user entered
		//int[] key = {1,2,3};
		
		byte[] rightSide = new byte[cipherString.length/2]; //set size of rightside
		byte[] leftSide = new byte[cipherString.length - rightSide.length]; //set size of leftside
		
		//populate rightside array
		for(int i = 0; i < rightSide.length; i++){
			rightSide[i] = cipherString[i];
	      }
		
		//populate leftside array
	      for(int i = 0; i < leftSide.length; i++){
	    	  leftSide[i] = cipherString[i+rightSide.length];
	      }
		
	      //xor the bits and swap the sides
		for(int i=0; i<rounds;i++){
			for(int j=0; j<rightSide.length;j++){
				rightSide[j] = (byte) ((rightSide[j] ^ key[i%key.length]));
			}
			byte[] temp = leftSide;
			leftSide = rightSide;
			rightSide = temp;
		}
		
		//put the rightside back into cipherstring
		for(int i = 0; i < rightSide.length; i++){
			cipherString[i] = (byte) rightSide[i];
	      }
		
		//put the leftside back into cipherstring
	      for(int i = 0; i < leftSide.length; i++){
	    	  cipherString[i+rightSide.length] = (byte) leftSide[i];
	      }
	      
	      //set String finishedFeistel with the results
	      finishedFeistel = new String(cipherString);
	      
		
		
	}
	
	


}
