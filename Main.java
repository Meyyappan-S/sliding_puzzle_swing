import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

class Puzzle implements ActionListener {
	JFrame f;
	JButton b[][];
	JButton start,restart;
	Integer size = 3;
	List<Integer> arr = new ArrayList<>();
	Puzzle(){
 		f = new JFrame("Puzzle");
		start = new JButton("PLAY");
		start.addActionListener(this);
		restart = new JButton("Restart");
		restart.addActionListener(this);
		restart.setEnabled(false);
		
		start.setBounds(20,20,80,20);
		restart.setBounds(130,20,90,20);
		
		f.add(start);
		f.add(restart);
		f.setSize(300,300);
		f.setLayout(null);
		f.setVisible(true);
		f.setResizable(false);
	}
	public static void main(String args[]){
		new Puzzle();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == start){
			b = new JButton[size][size];
			int x=40,y=70;
			for(int i=0;i<size;i++){
				for(int j=0;j<size;j++){
					b[i][j] = new JButton();
					b[i][j].setBounds(x, y, 50, 50);
					f.add(b[i][j]);
				    b[i][j].addActionListener(this);
				    b[i][j].setEnabled(false);
				    x+=50;
				}
				x = 40; y += 50;
			}
			start();
		}
		else if(e.getSource() == restart){
			f.setVisible(false);
			new Puzzle();
		}
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				if(e.getSource() == b[i][j]){
					moveTile(i,j);
					if(gameOver()){
						int a = JOptionPane.showConfirmDialog(f,"You Won! Do you want to continue?","Result",JOptionPane.YES_NO_OPTION);
						if(a == JOptionPane.YES_OPTION){
							f.setVisible(false);
							new Puzzle();
						}
						else{
							System.exit(0);
						}
					}
				}
			}
		}
	}
	
	public void start(){
		for(int i=0;i<size*size-1;i++){
			arr.add(i+1);
		}
		Collections.shuffle(arr);
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				b[i][j].setEnabled(true);
				if((i*3)+j != 8)
					b[i][j].setText(Integer.toString(arr.get((i*3)+j)));
				else
					b[i][j].setText("");
			}
		}
		restart.setEnabled(true);
		start.setEnabled(false);
	}
	
	public void moveTile(int i,int j){
		String temp = "";
		if(i-1 >= 0 && b[i-1][j].getText().equals("")){
			temp = b[i][j].getText();
			b[i][j].setText(b[i-1][j].getText());
			b[i-1][j].setText(temp);
		}
		else if(j-1 >= 0 && b[i][j-1].getText().equals("")){
			temp = b[i][j].getText();
			b[i][j].setText(b[i][j-1].getText());
			b[i][j-1].setText(temp);
		}
		else if(j+1 < size && b[i][j+1].getText().equals("")){
			temp = b[i][j].getText();
			b[i][j].setText(b[i][j+1].getText());
			b[i][j+1].setText(temp);
		}
		else if(i+1 < size && b[i+1][j].getText().equals("")){
			temp = b[i][j].getText();
			b[i][j].setText(b[i+1][j].getText());
			b[i+1][j].setText(temp);
		}
	}
	
	public boolean gameOver(){
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				if((i*3)+j != 8){
					if(!(b[i][j].getText().equals(Integer.toString((i*size)+j+1)))){
						return false;
					}
				}
			}
		}
		return true;
	}
}