package HospitalManagementSystem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

public class Homepage {
    public static String JDBC_URL = "jdbc:mysql://localhost:3306/";
    public static String user = "root";
    public static String password = "2410";
    public static int fl = 0;

    public static Connection con;
    public static Statement st;

    public static void main(String[] args) {
        try {
            con = DriverManager.getConnection(JDBC_URL, user, password);
            st = con.createStatement();
            st.executeUpdate("USE HOSPITAL_MS");

            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS EMPLOYEETABLE (ID INT AUTO_INCREMENT UNIQUE, EMPLOYEE_CODE VARCHAR(5) PRIMARY KEY, EMPLOYEE_NAME VARCHAR(20), PASSWORD VARCHAR(20))");
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS PATIENT_RECORDS (ID INT AUTO_INCREMENT UNIQUE, PATIENT_NAME VARCHAR(50), DIAGNOSED_WITH VARCHAR(30), ATTENDED_BY VARCHAR(50), NUMBER VARCHAR(10))");

            JFrame fr = new JFrame("HOSPITAL MANAGEMENT SYSTEM");
            fr.setLayout(new BorderLayout());
            fr.setSize(800, 600);
            fr.setBackground(Color.BLACK);
            fr.setLocationRelativeTo(null);
            fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            fr.setVisible(true);

            JPanel bgPnl = new JPanel() {
                Image bgImg = new ImageIcon("HospitalManagementSystem\\images\\hospital.jpg").getImage();

                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if (bgImg != null) {
                        g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
                    }
                }
            };
            bgPnl.setLayout(new BorderLayout());
            fr.setContentPane(bgPnl);

            JPanel log = new JPanel();
            log.setLayout(new BoxLayout(log, BoxLayout.Y_AXIS));
            log.setOpaque(true);
            log.setBackground(new Color(0, 0, 0, 200)); // Semi-transparent black
            log.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            log.setPreferredSize(new Dimension(300, 370)); // Set preferred size to reduce height

            JLabel empl = new JLabel("EMPLOYEE LOGIN");
            empl.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
            empl.setAlignmentX(Component.CENTER_ALIGNMENT);
            empl.setForeground(Color.RED);

            JLabel emplcode = new JLabel("ENTER EMPLOYEE CODE:");
            emplcode.setFont(new Font("Dialog", Font.PLAIN, 16));
            emplcode.setAlignmentX(Component.CENTER_ALIGNMENT);
            emplcode.setForeground(Color.WHITE);

            JTextField txtemplcode = new JTextField(20);
            Dimension fieldSize = new Dimension(200, 25); // Set desired width and height
            txtemplcode.setPreferredSize(fieldSize);
            txtemplcode.setMaximumSize(fieldSize);
            txtemplcode.setMinimumSize(fieldSize);

            JLabel pass = new JLabel("ENTER PASSWORD:");
            pass.setAlignmentX(Component.CENTER_ALIGNMENT);
            pass.setForeground(Color.WHITE);
            pass.setFont(new Font("Dialog", Font.PLAIN, 16));

            JPasswordField txtpassword = new JPasswordField(20);
            txtpassword.setPreferredSize(fieldSize);
            txtpassword.setMaximumSize(fieldSize);
            txtpassword.setMinimumSize(fieldSize);

            JPanel btnPnl = new JPanel();
            btnPnl.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
            JButton login = new JButton("Login");
            login.setBackground(Color.RED);
            login.setForeground(Color.WHITE);
            JButton newEm = new JButton("Add New");
            newEm.setBackground(Color.RED);
            newEm.setForeground(Color.WHITE);
            btnPnl.add(login);
            btnPnl.add(newEm);
            btnPnl.setOpaque(false);

            JPanel log1 = new JPanel();
            log1.setLayout(new BorderLayout());
            log1.setBorder(BorderFactory.createEmptyBorder(70, 20, 20, 20)); // Adjusted border
            log1.setOpaque(false);

            log.add(Box.createVerticalStrut(20));
            log.add(empl, BorderLayout.NORTH);
            log.add(Box.createVerticalStrut(50));
            log.add(emplcode);
            log.add(txtemplcode);
            log.add(Box.createVerticalStrut(30));
            log.add(pass);
            log.add(txtpassword);
            log.add(Box.createVerticalStrut(50));
            log.add(btnPnl);

            log1.add(log, BorderLayout.NORTH);

            bgPnl.add(log1, BorderLayout.EAST);

            login.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String emplstr = txtemplcode.getText();
                    String pwd = new String(txtpassword.getPassword());
                    checkDB(emplstr, pwd);
                    if (fl == 1) {
                        fr.dispose();
                        new RecordsPage().main(args);
                    } else {
                        JOptionPane.showMessageDialog(fr, "NO EMPLOYEE WITH SUCH EMPLOYEE CODE EXISTS.");
                    }
                }
            });

            newEm.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFrame f = new JFrame();
                    f.setSize(250, 300);
                    f.setLayout(new BorderLayout());
                    f.setLocationRelativeTo(null);
                    f.setVisible(true);

                    JPanel pnl = new JPanel();
                    pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));

                    JLabel a = new JLabel("ENTER EMPLOYEE CODE");
                    a.setAlignmentX(Component.CENTER_ALIGNMENT);
                    JLabel b = new JLabel("ENTER EMPLOYEE NAME");
                    b.setAlignmentX(Component.CENTER_ALIGNMENT);
                    JLabel c = new JLabel("ENTER PASSWORD");
                    c.setAlignmentX(Component.CENTER_ALIGNMENT);
                    JTextField ecode = new JTextField();
                    ecode.setMaximumSize(fieldSize);
                    JTextField ename = new JTextField();
                    ename.setMaximumSize(fieldSize);
                    JPasswordField paswd = new JPasswordField();
                    paswd.setMaximumSize(fieldSize);

                    JButton sbm = new JButton("SUBMIT");
                    sbm.setAlignmentX(Component.CENTER_ALIGNMENT);

                    pnl.add(a);
                    pnl.add(Box.createVerticalStrut(10));
                    pnl.add(ecode);
                    pnl.add(Box.createVerticalStrut(10));
                    pnl.add(b);
                    pnl.add(Box.createVerticalStrut(10));
                    pnl.add(ename);
                    pnl.add(Box.createVerticalStrut(10));
                    pnl.add(c);
                    pnl.add(Box.createVerticalStrut(10));
                    pnl.add(paswd);
                    pnl.add(Box.createVerticalStrut(30));
                    pnl.add(sbm);

                    f.add(pnl, BorderLayout.CENTER);

                    sbm.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            try (PreparedStatement ps = con.prepareStatement(
                                    "INSERT INTO EMPLOYEETABLE (EMPLOYEE_CODE, EMPLOYEE_NAME, PASSWORD) VALUES (?, ?, ?)")) {
                                ps.setString(1, ecode.getText());
                                ps.setString(2, ename.getText());
                                ps.setString(3, new String(paswd.getPassword()));
                                ps.executeUpdate();
                                JOptionPane.showMessageDialog(f, "ADDED SUCCESSFULLY");
                                f.dispose();
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void checkDB(String emplstr, String pwd) {
        try (PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM EMPLOYEETABLE WHERE EMPLOYEE_CODE = ? AND PASSWORD = ?")) {
            ps.setString(1, emplstr);
            ps.setString(2, pwd);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    fl = 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
