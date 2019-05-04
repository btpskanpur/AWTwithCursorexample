import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class Test {
	static int id = 0;
	static int id2;

	public static void main(String[] args) throws Exception, SQLException {

		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		Connection con = DriverManager.getConnection(url, "system", "root");

		Frame f1 = new Frame("AWT Oracle");
		Font font1 = new Font("Verdana", Font.BOLD, 25);
		Font font2 = new Font("Verdana", Font.PLAIN, 20);
		Font font3 = new Font("Verdana", Font.PLAIN, 15);

		f1.setFont(font2);
		f1.setBackground(new Color(175, 206, 255));
		Label l0 = new Label("Student Data");
		l0.setFont(font1);

		Label l1 = new Label("Your Name");
		Label l2 = new Label("Your Email");
		Label l3 = new Label("Your Address");
		Label l4 = new Label("Your Mobile No");
		Label l5 = new Label("Enter ID : ");
		Label l6 = new Label();

		l6.setForeground(Color.RED);
		l6.setFont(font3);

		l0.setBounds(200, 50, 300, 100);
		l1.setBounds(100, 150, 150, 30);
		l2.setBounds(100, 190, 150, 30);
		l3.setBounds(100, 230, 150, 30);
		l4.setBounds(100, 270, 150, 30);
		l5.setBounds(100, 480, 100, 30);
		l6.setBounds(250, 370, 200, 30);

		TextField tf1 = new TextField();
		TextField tf2 = new TextField();
		TextField tf3 = new TextField();
		TextField tf4 = new TextField();
		TextField tf5 = new TextField();

		tf1.setBounds(300, 150, 150, 30);
		tf2.setBounds(300, 190, 150, 30);
		tf3.setBounds(300, 230, 150, 30);
		tf4.setBounds(300, 270, 150, 30);
		tf5.setBounds(250, 480, 100, 30);

		Button b1 = new Button("Submit");
		Button b2 = new Button("First");
		Button b3 = new Button("Previous");
		Button b4 = new Button("Next");
		Button b5 = new Button("Last");
		Button b6 = new Button("Find");

		b1.setBounds(250, 330, 100, 40);
		b2.setBounds(40, 410, 100, 40);
		b3.setBounds(180, 410, 100, 40);
		b4.setBounds(320, 410, 100, 40);
		b5.setBounds(460, 410, 100, 40);
		b6.setBounds(400, 480, 80, 30);

		f1.add(l0);
		f1.add(l1);
		f1.add(l2);
		f1.add(l3);
		f1.add(l4);
		f1.add(l5);
		f1.add(l6);

		f1.add(tf1);
		f1.add(tf2);
		f1.add(tf3);
		f1.add(tf4);
		f1.add(tf5);

		f1.add(b1);
		f1.add(b2);
		f1.add(b3);
		f1.add(b4);
		f1.add(b5);
		f1.add(b6);

		b1.addActionListener((ActionEvent e) -> {
			if (!(tf1.getText().equals("") || tf2.getText().equals("") || tf3.getText().equals("")
					|| tf4.getText().equals(""))) {
				System.out.println("Not empty");
				String name = tf1.getText();
				String email = tf2.getText();
				String address = tf3.getText();
				String mobile = tf4.getText();

				try {
					insertData(con, name, email, address, mobile);
					l6.setText("Data submitted Successfully");
					tf1.setText("");
					tf2.setText("");
					tf3.setText("");
					tf4.setText("");
				} catch (Exception ex) {
					System.out.println("Exception" + ex);
				}
			} else {
				System.out.println("Empty");
				l6.setText("Some data field is empty");
			}
		});

		b2.addActionListener((ActionEvent e) -> {
			System.out.println("First");
			try {
				Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet res = st.executeQuery("select distinct id from student order by id");

				res.first();

				id2 = Integer.parseInt(res.getString(1));
			} catch (Exception ex) {
				System.out.println("Exception 1 :" + ex);
			}

			try {
				String[] result = findData(con, id2);
				tf1.setText(result[1]);
				tf2.setText(result[2]);
				tf3.setText(result[3]);
				tf4.setText(result[4]);
				tf5.setText(result[0]);
			} catch (Exception ex) {
				System.out.println("Exception" + ex);
			}
		});

		b3.addActionListener((ActionEvent e) -> {
			--id2;
			if (id2 >= 0) {
				try {
					String[] result = findData(con, id2);
					tf1.setText(result[1]);
					tf2.setText(result[2]);
					tf3.setText(result[3]);
					tf4.setText(result[4]);
					tf5.setText(result[0]);
				} catch (Exception ex) {
					System.out.println("Exception" + ex);
				}
			} else {
				System.out.println("Not valid");
				l6.setText("Not Valid");
			}
		});

		b4.addActionListener((ActionEvent e) -> {
			++id2;
			try {
				String[] result = findData(con, id2);
				if (result[0] != null) {
					tf1.setText(result[1]);
					tf2.setText(result[2]);
					tf3.setText(result[3]);
					tf4.setText(result[4]);
					tf5.setText(result[0]);
					System.out.println(result[0]);
				} else {
					System.out.println("Not valid");
					l6.setText("Not Valid");
					--id2;
				}
			} catch (Exception ex) {
				System.out.println("Exception" + ex);
			}
		});

		b5.addActionListener((ActionEvent e) -> {
			System.out.println("Last");
			try {
				Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet res = st.executeQuery("select distinct id from student order by id");

				res.last();

				id2 = Integer.parseInt(res.getString(1));
			} catch (Exception ex) {
				System.out.println("Exception 1 :" + ex);
			}

			try {
				String[] result = findData(con, id2);
				tf1.setText(result[1]);
				tf2.setText(result[2]);
				tf3.setText(result[3]);
				tf4.setText(result[4]);
				tf5.setText(result[0]);
			} catch (Exception ex) {
				System.out.println("Exception" + ex);
			}
		});

		b6.addActionListener((ActionEvent e) -> {
			if (!(tf5.getText().equals("") || Integer.parseInt(tf5.getText()) < 0)) {
				System.out.println("Valid");
				id2 = Integer.parseInt(tf5.getText());
				try {
					String[] result = findData(con, id2);

					tf1.setText(result[1]);
					tf2.setText(result[2]);
					tf3.setText(result[3]);
					tf4.setText(result[4]);
					tf5.setText(result[0]);
				} catch (Exception ex) {
					System.out.println("Exception" + ex);
				}
			} else {
				System.out.println("Not Valid");
				l6.setText("Not Valid");
			}
		});

		f1.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		f1.setLayout(null);
		f1.setVisible(true);
		f1.setSize(600, 650);
	}

	public static void insertData(Connection con, String name, String email, String address, String mobile)
			throws SQLException {

		try {
			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = st.executeQuery("select distinct id from student order by id");

			res.last();
			id = Integer.parseInt(res.getString(1)) + 1;
		} catch (Exception ex) {
			System.out.println("Exception 1 :" + ex);
		}

		System.out.print(name + " ");
		System.out.print(email + " ");
		System.out.print(address + " ");
		System.out.print(mobile + " ");

		PreparedStatement pst = con.prepareStatement("insert into student values(?,?,?,?,?)");

		pst.setInt(1, id);
		pst.setString(2, name);
		pst.setString(3, email);
		pst.setString(4, address);
		pst.setString(5, mobile);
		int i = pst.executeUpdate();

		if (i > 0) {
			System.out.println("data inserted");
		} else {
			System.out.println("not inserted");
		}
	}

	public static String[] findData(Connection con, int id2) throws SQLException {
		PreparedStatement pst = con.prepareStatement("select * from student where id = " + id2);

		ResultSet res = pst.executeQuery();
		String[] result = new String[5];

		while (res.next()) {
			for (int i = 0; i < 5; i++) {
				result[i] = res.getString(i + 1);
				System.out.print(res.getString(i + 1) + " ");
			}
		}
		return result;
	}
}
