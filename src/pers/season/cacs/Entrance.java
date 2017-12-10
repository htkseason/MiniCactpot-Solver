package pers.season.cacs;

import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import pers.season.cacs.Expectation.BenefitPossibility;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Entrance extends JFrame {
	final int size = 3;
	MiniCactpot mc = new MiniCactpot(size);
	JTextField[] txts = new JTextField[size * size];
	JLabel[] lbls = new JLabel[size * 2 + 2]; // row1,row2,row3,col1,col2,col3,lt,rt
	Expectation exp;

	public Entrance() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		this.setTitle("Mini-Cactpot Solver");
		this.setSize(new Dimension(size * 50 + 110, size * 50 + 140));
		this.setLocationRelativeTo(null);
		{
			JButton btn = new JButton("Calculate");
			btn.setBounds(50, size * 50 + 50, size * 50, 30);
			btn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					for (int y = 0; y < size; y++)
						for (int x = 0; x < size; x++)
							mc.put(y, x, Integer.parseInt(txts[y * size + x].getText()));
					exp = mc.calcResult();
					for (int y = 0; y < 3; y++)
						lbls[y].setText(String.format("%.0f", getMathExpectation(exp.rowResult[y])));
					for (int x = 0; x < 3; x++)
						lbls[size + x].setText(String.format("%.0f", getMathExpectation(exp.colResult[x])));
					lbls[lbls.length - 2].setText(String.format("%.0f", getMathExpectation(exp.ltResult)));
					lbls[lbls.length - 1].setText(String.format("%.0f", getMathExpectation(exp.rtResult)));
				}

			});
			getContentPane().add(btn);
		}
		{
			JButton btn = new JButton("Clear");
			btn.setBounds(50, size * 50 + 50+30, size * 50, 30);
			btn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					for (int i = 0; i < txts.length; i++)
						txts[i].setText("0");
					for (int i = 0; i < lbls.length; i++)
						lbls[i].setText("0");
				}

			});
			getContentPane().add(btn);
		}
		for (int y = 0; y < size; y++)
			for (int x = 0; x < size; x++) {
				JTextField tf = new JTextField();
				tf.setText("0");
				tf.setHorizontalAlignment(SwingConstants.CENTER);
				tf.setFont(new Font("", Font.BOLD, 20));
				tf.setBounds(50 + x * 50, 50 + y * 50, 50, 50);
				tf.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent arg0) {
						tf.selectAll();
					}
				});
				getContentPane().add(tf);
				txts[y * size + x] = tf;
			}
		for (int y = 0; y < size; y++) {
			JLabel jl = new JLabel();
			final int index = y;
			jl.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (exp != null)
						showMsgBox(exp.rowResult[index]);
				}
			});
			jl.setText("0");
			jl.setHorizontalAlignment(SwingConstants.CENTER);
			jl.setFont(new Font("", Font.BOLD, 15));
			jl.setBounds(0, 50 + y * 50, 50, 50);
			getContentPane().add(jl);
			lbls[y] = jl;
		}
		for (int x = 0; x < size; x++) {
			JLabel jl = new JLabel();
			final int index = x;
			jl.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					if (exp != null)
						showMsgBox(exp.colResult[index]);
				}
			});
			jl.setText("0");
			jl.setHorizontalAlignment(SwingConstants.CENTER);
			jl.setFont(new Font("", Font.BOLD, 15));
			jl.setBounds(50 + x * 50, 0, 50, 50);
			getContentPane().add(jl);
			lbls[size + x] = jl;
		}
		{
			JLabel jl = new JLabel();
			jl.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					if (exp != null)
						showMsgBox(exp.ltResult);
				}
			});
			jl.setText("0");
			jl.setHorizontalAlignment(SwingConstants.CENTER);
			jl.setFont(new Font("", Font.BOLD, 15));
			jl.setBounds(0, 0, 50, 50);
			getContentPane().add(jl);
			lbls[lbls.length - 2] = jl;
		}
		{
			JLabel jl = new JLabel();
			jl.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					if (exp != null)
						showMsgBox(exp.rtResult);
				}
			});
			jl.setText("0");
			jl.setHorizontalAlignment(SwingConstants.CENTER);
			jl.setFont(new Font("", Font.BOLD, 15));
			jl.setBounds(50 * size + 50, 0, 50, 50);
			getContentPane().add(jl);
			lbls[lbls.length - 1] = jl;
		}

	}

	public double getMathExpectation(List<BenefitPossibility> lbp) {
		double ret = 0;
		for (BenefitPossibility bp : lbp)
			ret += bp.benefit * bp.possibility;
		return ret;
	}

	public void showMsgBox(List<BenefitPossibility> lbp) {
		String msg = new String();
		for (BenefitPossibility bp : lbp)
			msg += bp.toString() + "\n";
		JOptionPane.showMessageDialog(null, msg, "Detail", JOptionPane.PLAIN_MESSAGE);
	}

	public static void main(String[] args) {
		new Entrance().setVisible(true);

	}
}
