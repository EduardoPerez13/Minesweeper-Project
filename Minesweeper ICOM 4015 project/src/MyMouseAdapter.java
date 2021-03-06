import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class MyMouseAdapter extends MouseAdapter {	
	public void mousePressed(MouseEvent e) {

		Component c = e.getComponent();
		while (!(c instanceof JFrame)) {
			c = c.getParent();
			if (c == null) {
				return;
			}
		}
		JFrame myFrame = (JFrame) c;
		MinesweeperPanel myPanel = (MinesweeperPanel) myFrame.getContentPane().getComponent(0);
		Insets myInsets = myFrame.getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		e.translatePoint(-x1, -y1);
		int x = e.getX();
		int y = e.getY();
		myPanel.x = x;
		myPanel.y = y;
		myPanel.mouseDownGridX = myPanel.getGridX(x, y);
		myPanel.mouseDownGridY = myPanel.getGridY(x, y);
		myPanel.repaint();
	}
	
	public void mouseReleased(MouseEvent e) {
		
		Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame)c;
			MinesweeperPanel myPanel = (MinesweeperPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			int gridX = myPanel.getGridX(x, y);
			int gridY = myPanel.getGridY(x, y);
		switch (e.getButton()) {
		case 1:		//Left mouse button
			
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} else {
				if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside
					//Do nothing
				} else {
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
					} else {
						//Released the mouse button on the same cell where it was pressed

						Color newColor = null;
						if(myPanel.mineLocation[myPanel.mouseDownGridX][myPanel.mouseDownGridY] == true){
							for (int i=0; i<9; i++){
								for(int j=0; j<9; j++){
									if(myPanel.mineLocation[i][j]==true){
										newColor = Color.BLACK;
										myPanel.colorArray[i][j] = newColor;
										myPanel.repaint();
									}
								}

							} 
							JOptionPane.showMessageDialog(null, "KABOOOM! GAME OVER!");
							System.exit(0);
						}
						else{
							if(!myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY].equals(Color.LIGHT_GRAY) ||!myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY].equals(Color.BLUE) ||!myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY].equals(Color.GREEN )){
								if(myPanel.surroundedByMine(myPanel.mouseDownGridX, myPanel.mouseDownGridY ) == true){ 
									if(myPanel.scanForNearBombs(myPanel.mouseDownGridX, myPanel.mouseDownGridY) == 1){
										newColor = Color.BLUE;
										myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
										myPanel.repaint();							
										myPanel.win();	
									}
									else{
										newColor = Color.GREEN;
										myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
										myPanel.repaint();							
										myPanel.win();
									}																												
								}
								else{
									newColor = Color.LIGHT_GRAY;									
									myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
									myPanel.repaint();
									myPanel.win();
									myPanel.uncoveringForLoop(myPanel.mouseDownGridX, myPanel.mouseDownGridY);
								}
							}
						}
					}
				}
			}
			myPanel.repaint();
			break;
		case 3:		//Right mouse button
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} else {
				if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside
					//Do nothing
				} else {
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
					} else {
						//Released the mouse button on the same cell where it was pressed
						Color newColor = null;
						newColor = Color.RED;
						myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
						myPanel.repaint();
					}
				}
			}
			break;

		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
}
