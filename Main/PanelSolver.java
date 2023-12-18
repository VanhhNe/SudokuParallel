package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Queue;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.text.AbstractDocument;

import Helper.HoverButton;
import Helper.IntegerDocumentFilter;

public class PanelSolver extends JPanel {

	private static final long serialVersionUID = 1L;
	SudokuSolver game;
	private Timer timer;
	private JButton nbtn = new JButton("New Game");
	private static JTextField[][] boxes;
	private JPasswordField pass = new JPasswordField("");
	private JLabel label = new JLabel("Thời gian: 00:00:00");
	private JPanel[][] paneles;
	private JPanel center, bPanel, timePanel, headerPanel, difPanel;
	private JMenuBar menuBar;
	private HoverButton nBtn, cBtn, solve, undo, redo;
	private JComboBox<String> difficultyLevel;
	private int[][] temp = new int[9][9];
	private int[][] grid = new int[9][9];
	private int counter = 0;
	int currentLevel = 2;
	boolean gameStart = false;
	boolean correct;
	boolean pause;
	Queue historyAction;

	public Font getFont(String fontName, int size, int style) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fontNames = ge.getAvailableFontFamilyNames();
		boolean fontAvailable = false;

		for (String fName : fontNames) {
			if (fName.equalsIgnoreCase(fontName)) {
				fontAvailable = true;
				break;
			}
		}

		if (fontAvailable) {
			return new Font(fontName, style, size);
		} else {
			// If the specified font is not available, return a default font
			return new Font(Font.DIALOG, style, size);
		}
	}

	public JTextField newTextField() {
		int desiredSize = 30;
		int fontStyle = Font.CENTER_BASELINE;
		String fontName = "Varela";
		JTextField jText = new JTextField("");
		Font fontText = getFont(fontName, desiredSize, fontStyle);
		jText.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		jText.setFont(fontText);
		jText.setHorizontalAlignment(JTextField.CENTER);

		// Create MountListener for text
		jText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (jText.isEditable()) {
					((JTextField) e.getSource()).setBorder(BorderFactory.createLineBorder(Color.WHITE));
					((JTextField) e.getSource()).setBackground(Color.decode("#5e87ff"));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (jText.isEditable()) {
					((JTextField) e.getSource()).setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
					((JTextField) e.getSource()).setBackground(Color.WHITE);
				}
			}
		});

		jText.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (jText.isEditable()) {
					((JTextField) e.getSource()).setForeground(Color.decode("#0c4"));
					((JTextField) e.getSource()).setBackground(Color.decode("#a9c6f5"));
				} else {
					((JTextField) e.getSource()).setForeground(Color.BLACK);
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});
		((AbstractDocument) jText.getDocument()).setDocumentFilter(new IntegerDocumentFilter());
		return jText;
	}

	public PanelSolver() {
		initComponents();
		setupMenuOptions();
		/*------------------------ HEADER-PANEL -------------------------------------*/
		JPanel paddingHeader = new JPanel();
		paddingHeader.setLayout(new GridLayout(3, 1));
		headerPanel = new JPanel();
		headerPanel.setLayout(new GridLayout(1, 2, 30, 10));
		difPanel = new JPanel();
		difPanel.setLayout(new GridLayout(1, 2, 10, 10));
		difficultyLevel = new JComboBox<String>(new String[] { "Nhập môn", "Cơ bản", "Cao thủ" });
		difficultyLevel.setFont(getFont("Montserrat", 15, Font.PLAIN));
		difficultyLevel.setName("Cấp độ");
		difficultyLevel.setFocusable(false);
		JLabel difficultyLabel = new JLabel("Cấp độ");
		difficultyLabel.setFont(getFont("Montserrat", 20, Font.BOLD));
		difficultyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		difPanel.add(difficultyLabel);
		difPanel.add(difficultyLevel);
//		difPanel.add(new JLabel());
		headerPanel.add(difPanel);
		paddingHeader.add(new JLabel(""));
		paddingHeader.add(headerPanel);
		paddingHeader.add(new JPanel());
		/*------------------------ MAIN-PANEL -------------------------------------*/
		center = new JPanel(); // main panel
		center.setLayout(new GridLayout(3, 3, 4, 4));
		setLayout(new BorderLayout());
		add(center); // add main panel to frame

		boxes = new JTextField[9][9];
		paneles = new JPanel[3][3];
		label.setForeground(Color.BLACK);
		label.setForeground(Color.RED);
		label.setFont(getFont("Montserrat", 20, Font.CENTER_BASELINE));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				paneles[i][j] = new JPanel();
//				paneles[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				paneles[i][j].setLayout(new GridLayout(3, 3));
				center.add(paneles[i][j]);
			}
		}

		/*------------------------ TEXT BOXES -------------------------------------*/
		for (int n = 0; n < 9; n++) {
			for (int i = 0; i < 9; i++) {
				boxes[n][i] = newTextField();
				int fm = (n + 1) / 3;
				if ((n + 1) % 3 > 0) {
					fm++;
				}
				int cm = (i + 1) / 3;
				if ((i + 1) % 3 > 0) {
					cm++;
				}
				paneles[fm - 1][cm - 1].add(boxes[n][i]); // Add box to panel
			}
		}
		/*------------------------ PANEL FOR BUTTONS -------------------------------------*/

		bPanel = new JPanel();
		bPanel.setBackground(Color.white);
//		BoxLayout btnLayout = new BoxLayout(bPanel, BoxLayout.X_AXIS);
		bPanel.setLayout(new GridLayout(1, 2, 20, 20));

		/*------------------------ ACTION FOR BUTTON -------------------------------------*/
		ActionListener action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				label.setText(TimeFormat(counter));
				counter += 1;
			}
		};
		label.setPreferredSize(new Dimension(300, 40));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVisible(false);
		/*------------------------ PANEL FOR NEW GAME BUTTON -------------------------------------*/

		nBtn = new HoverButton("Tạo ván mới");
		nBtn.setSize(20, 50);
		setButtonStyle(nBtn);
		timer = new Timer(1000, action);
		nBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int result = showYesNoDialog("Bạn có chắc muốn làm mới?");
				if (result == JOptionPane.YES_OPTION) {
					String selectedDifficulty = (String) difficultyLevel.getSelectedItem();
					currentLevel = mapDifficultyToLevel(selectedDifficulty);
					SudokuSolver.setLevel(currentLevel);
					gameStart = true;
					correct = false;
					pause = false;
					counter = 0;
					timer.start();
					restGame();
					SudokuSolver.newName();
					label.setVisible(true);
				}
			}

			private int showYesNoDialog(String message) {
				return JOptionPane.showOptionDialog(null, message, "Confirmation", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.YES_OPTION);
			}
		});
		/*------------------------ PANEL FOR CHECK GAME BUTTON -------------------------------------*/
		cBtn = new HoverButton("Kiểm thử (+30s)");
		setButtonStyle(cBtn);
		cBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!gameStart) {
					JOptionPane.showMessageDialog(center, "Chưa bắt đầu chơi!");
					return;
				}
				correct = true;
				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 9; j++) {
						if (!boxes[i][j].isEditable()) {
							continue;
						} else if (boxes[i][j].getText().equals(String.valueOf(grid[i][j]))) {
							boxes[i][j].setBackground(Color.decode("#C0DCD9"));
						} else if (boxes[i][j].getText().isEmpty()) {
							boxes[i][j].setBackground(Color.WHITE);
							correct = false;
						} else {
							boxes[i][j].setBackground(Color.RED);
							correct = false;
						}
					}
				}
				if (correct) {
					JOptionPane.showMessageDialog(center,
							"Xin chúc mừng!\nBạn cần: " + formatOutputTime(counter) + " để hoàn thành");
					timer.stop();
				} else {
					counter += 30;
				}
			}
		});

		/*------------------------ PANEL FOR SOLUTION BUTTON -------------------------------------*/
		solve = new HoverButton("Hiện đáp án");
		setButtonStyle(solve);
		solve.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!gameStart) {
					JOptionPane.showMessageDialog(center, "Chưa bắt đầu chơi!");
					return;
				}

				if (correct) {
					JOptionPane.showMessageDialog(center, "Trò chơi đã kết thúc");
					return;
				}

				Object[] message = { "Nhập mật khẩu:", pass };
				int option = JOptionPane.showConfirmDialog(center, message, "Yêu cầu mật khẩu:",
						JOptionPane.OK_CANCEL_OPTION);

				if (option == JOptionPane.OK_OPTION) {
					char[] enteredPasswordChars = pass.getPassword();
					String enteredPassword = new String(pass.getPassword());
					System.out.println(enteredPassword);

					if (enteredPassword.equals("dsa")) {
						timer.stop();
						counter = 0;
						label.setText(TimeFormat(counter));
						try {
							Thread.sleep(1000);
							for (int i = 0; i < 9; i++) {
								for (int j = 0; j < 9; j++) {
									boxes[i][j].setText(String.valueOf(grid[i][j]));
								}
							}
							pass.setText("");
						} catch (InterruptedException ex) {
							Thread.currentThread().interrupt();
						}

					} else {
						JOptionPane.showMessageDialog(center, "Incorrect password!");
					}
				}

			}
		});

		/*------------------------ ADD BUTTONS TO FRAME -------------------------------------*/
		JPanel newBtnPanel = new JPanel();
		newBtnPanel.setLayout(new GridLayout(1, 2));
		newBtnPanel.add(nBtn);
		headerPanel.add(newBtnPanel);

		add(paddingHeader, "North");
		bPanel.add(cBtn);
		bPanel.add(solve);
		timePanel = new JPanel();
		timePanel.setBackground(Color.WHITE);
		timePanel.setLayout(new GridLayout(1, 3));
//		timePanel.add(new JLabel());
		timePanel.add(label);
//		timePanel.add(new JLabel());
		JPanel tempPanel = new JPanel();
		tempPanel.setBackground(Color.WHITE);
		tempPanel.setLayout(new GridLayout(3, 1, 0, 5));
		JPanel padding10 = new JPanel();
		padding10.setBackground(Color.WHITE);
		tempPanel.add(padding10);
		tempPanel.add(bPanel);
		tempPanel.add(timePanel);
		add(tempPanel, "South");
	}

	public void setupMenuOptions() {
		/*------------------------ MENU OPTIONS -------------------------------------*/
		JMenuBar menuBar = new JMenuBar();

		JMenu selectMenu = new JMenu("Tùy chọn");
		JMenuItem exitOption = new JMenuItem("Thoát");
		JMenuItem aboutOption = new JMenuItem("Giới thiệu");
		JMenuItem pauseOption = new JMenuItem("Tạm dừng");
		JMenuItem resumeOption = new JMenuItem("Tiếp tục");
		selectMenu.setFont(getFont("Montserrat", 15, Font.PLAIN));
//		selectMenu.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
		selectMenu.add(exitOption);
		selectMenu.add(aboutOption);
		selectMenu.add(pauseOption);
		selectMenu.add(resumeOption);
		exitOption.setFont(getFont("Montserrat", 15, Font.PLAIN));
		aboutOption.setFont(getFont("Montserrat", 15, Font.PLAIN));
		pauseOption.setFont(getFont("Montserrat", 15, Font.PLAIN));
		resumeOption.setFont(getFont("Montserrat", 15, Font.PLAIN));
		resumeOption.setVisible(false);
		exitOption.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		aboutOption.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(center, "Hello World!");
			}
		});
		pauseOption.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				if (gameStart) {
					pause = true;
					timer.stop();
					center.setVisible(false);
					resumeOption.setVisible(true);
					pauseOption.setVisible(false);
				}
			}
		});
		resumeOption.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (gameStart) {
					pause = false;
					timer.start();
					center.setVisible(true);
					pauseOption.setVisible(true);
					resumeOption.setVisible(false);
				}
			}
		});
		menuBar.add(selectMenu);
		SudokuSolver.frame.setJMenuBar(menuBar);
	}

	public void setArray(int[][] grid, int[][] temp) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				this.temp[i][j] = temp[i][j];
				this.grid[i][j] = grid[i][j];
			}
		}
	}

	public void setTextLabel() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (this.temp[i][j] != 0) {
					boxes[i][j].setText(String.valueOf(this.temp[i][j]));
					boxes[i][j].setEditable(false);
					boxes[i][j].setBackground(Color.decode("#CDDCC0"));
				} else {
					boxes[i][j].setText("");
				}
			}
		}
	}

	protected void restGame() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				boxes[i][j].setForeground(Color.BLACK);
				boxes[i][j].setEditable(true);
				boxes[i][j].setBackground(Color.white);
			}
		}

	}

	protected String TimeFormat(int count) {
		int hours = count / 3600;
		int minutes = (count - hours * 3600) / 60;
		int seconds = count - minutes * 60;

		return String.format("Thời gian: " + "%02d", hours) + " : " + String.format("%02d", minutes) + " : "
				+ String.format("%02d", seconds);
	}

	protected String formatOutputTime(int seconds) {
		if (seconds < 0) {
			throw new IllegalArgumentException("Duration must be a non-negative value");
		}

		long hours = seconds / 3600;
		long minutes = (seconds % 3600) / 60;
		long remainingSeconds = seconds % 60;

		StringBuilder formattedDuration = new StringBuilder();

		if (hours > 0) {
			formattedDuration.append(hours).append("h");
		}

		if (minutes > 0) {
			if (formattedDuration.length() > 0) {
				formattedDuration.append(" ");
			}
			formattedDuration.append(minutes).append("'");
		}

		if (remainingSeconds > 0) {
			if (formattedDuration.length() > 0) {
				formattedDuration.append(" ");
			}
			formattedDuration.append(remainingSeconds).append("\"");
		}

		return formattedDuration.toString();
	}

	private void initComponents() {
		setLayout(null);
	}

	private int mapDifficultyToLevel(String difficulty) {
		switch (difficulty) {
		case "Nhập môn":
			return 2;
		case "Có kinh nghiệp":
			return 3;
		case "Cao thủ":
			return 4;
		default:
			return 2;
		}
	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}

	public void setButtonStyle(JButton button) {
		button.setForeground(Color.BLACK);
		button.setBackground(Color.decode("#e4ebf5"));
		button.setBorder(null);
//		button.setOpaque(false);
		button.setFont(getFont("Montserrat", 20, Font.BOLD));
		button.setRolloverEnabled(true);
	}
}
