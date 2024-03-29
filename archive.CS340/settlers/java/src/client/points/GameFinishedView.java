package client.points;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;

import client.base.*;
import client.join.JoinGameController;
import client.join.JoinGameView;
import client.join.NewGameView;
import client.join.PlayerWaitingController;
import client.join.PlayerWaitingView;
import client.join.SelectColorView;
import client.login.LoginController;
import client.login.LoginView;
import client.main.Catan;
import client.misc.MessageView;
import client.model.ClientModel;
import client.utils.*;


/**
 * Implementation for the game finished view, which is displayed when the game is over
 */
@SuppressWarnings({"serial", "unused"})
public class GameFinishedView extends OverlayView implements IGameFinishedView {

	private final int LABEL_TEXT_SIZE = 40;
	private final int MESSAGE_TEXT_SIZE = 14;
	private final int BUTTON_TEXT_SIZE = 28;
	private final int BORDER_WIDTH = 10;

	private final int IMAGE_HEIGHT = 200;
	
	private JLabel label;
	private JLabel message;
	private JLabel image;
	private JButton okButton;
	private JPanel buttonPanel;

	public GameFinishedView() {
		
		this.setOpaque(true);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.black, BORDER_WIDTH));
		
		label = new JLabel("Game Finished");
		Font labelFont = label.getFont();
		labelFont = labelFont.deriveFont(labelFont.getStyle(), LABEL_TEXT_SIZE);
		label.setFont(labelFont);
		label.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.add(label, BorderLayout.PAGE_START);

		JPanel middle = new JPanel();
		middle.setLayout(new BoxLayout(middle, BoxLayout.Y_AXIS));
		this.add(middle, BorderLayout.CENTER);

		middle.add(Box.createRigidArea(new Dimension(0,5))); // Spacing
		
		message = new JLabel("***");
		Font messageFont = message.getFont();
		messageFont = messageFont.deriveFont(messageFont.getStyle(), MESSAGE_TEXT_SIZE);
		message.setFont(messageFont);
		message.setAlignmentX(Component.CENTER_ALIGNMENT);
		middle.add(message);
		
		middle.add(Box.createRigidArea(new Dimension(0,5))); // Spacing
		
		image = new JLabel(new ImageIcon(ImageUtils.DEFAULT_IMAGE));
		image.setAlignmentX(Component.CENTER_ALIGNMENT);
		middle.add(image);

		middle.add(Box.createRigidArea(new Dimension(0,5))); // Spacing
		
		okButton = new JButton("OK");
		okButton.addActionListener(actionListener);
		Font buttonFont = okButton.getFont();
		buttonFont = buttonFont.deriveFont(buttonFont.getStyle(), BUTTON_TEXT_SIZE);
		okButton.setFont(buttonFont);
		okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(okButton, BorderLayout.PAGE_END);
	}

	private ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == okButton) {
				closeModal();
				
				
				SwingUtilities.invokeLater(new Runnable()
                {
                    public void run()
                    {
                        Frame[] frames = Frame.getFrames();
                        for(Frame frame: frames) {
                        	frame.dispose();
                        }
                        createNewGame();
                    }
                });
				return;
                //TODO: go to main splash after game
			}
		}	
	};
	private void createNewGame() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				ClientModel.setUpdatableModel(null);
				new Catan();
				
				PlayerWaitingView playerWaitingView = new PlayerWaitingView();
				final PlayerWaitingController playerWaitingController = new PlayerWaitingController(
																									playerWaitingView);
				playerWaitingView.setController(playerWaitingController);
				
				JoinGameView joinView = new JoinGameView();
				NewGameView newGameView = new NewGameView();
				SelectColorView selectColorView = new SelectColorView();
				MessageView joinMessageView = new MessageView();
				final JoinGameController joinController = new JoinGameController(
																				 joinView,
																				 newGameView,
																				 selectColorView,
																				 joinMessageView);
				joinController.setJoinAction(new IAction() {
					@Override
					public void execute()
					{
						playerWaitingController.start();
					}
				});
				joinView.setController(joinController);
				newGameView.setController(joinController);
				selectColorView.setController(joinController);
				joinMessageView.setController(joinController);
				
				LoginView loginView = new LoginView();
				MessageView loginMessageView = new MessageView();
				LoginController loginController = new LoginController(
																	  loginView,
																	  loginMessageView);
				loginController.setLoginAction(new IAction() {
					@Override
					public void execute()
					{
						joinController.start();
					}
				});
				loginView.setController(loginController);
				loginView.setController(loginController);
				
				loginController.start();
				loginView.closeModal();
				joinController.start();
			}
		});
	}
	@Override
	public IPointsController getController() {
		
		return (IPointsController)super.getController();
	}

	@Override
	public void setWinner(String name, boolean isLocalPlayer) {
		String field = "";
		String imagePath = "";

		if (isLocalPlayer)
		{ // We won!
			field = "Congratulations! You won!";
			imagePath = "images/misc/winner2.png";
		} else
		{ // We lost!
			field = name + " won! Better luck next time.";
			imagePath = "images/misc/loser2.png";
		}
		message.setText(field);
		
		BufferedImage b = ImageUtils.loadImage(imagePath);
		int newWidth = b.getWidth() * IMAGE_HEIGHT / b.getHeight();
		image.setIcon(new ImageIcon(b.getScaledInstance(newWidth, IMAGE_HEIGHT, BufferedImage.SCALE_FAST)));
	}

}


