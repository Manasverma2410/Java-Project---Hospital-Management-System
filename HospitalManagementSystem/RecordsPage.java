package HospitalManagementSystem;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class RecordsPage {
    public static String JDBC_URL = "jdbc:mysql://localhost:3306/HOSPITAL_MS";
    public static String user = "root";
    public static String password = "2410";

    public static JTable jt;
    public static DefaultTableModel mod;
    public static JFrame fr;

    public static void main(String[] args) {
        fr = new JFrame();
        fr.setSize(700, 550);
        fr.setLayout(new BorderLayout());
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setLocationRelativeTo(null);

        JPanel bgPnl = new JPanel() {
            Image bgImg = new ImageIcon("images\\hospital1.jpg").getImage();

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bgImg != null) {
                    g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        bgPnl.setLayout(new BorderLayout());
        fr.setContentPane(bgPnl);

        JPanel btnPnl = new JPanel();
        btnPnl.setLayout(new BoxLayout(btnPnl, BoxLayout.Y_AXIS));
        btnPnl.setOpaque(false);
        btnPnl.setBorder(BorderFactory.createEmptyBorder(100, 20, 0, 0));

        JPanel a = new JPanel();
        a.setLayout(new BorderLayout());
        a.setBackground(new Color(70, 0, 30, 100));
        a.setOpaque(true);
        a.setPreferredSize(new Dimension(159, 450));

        JButton addPatient = new JButton("ADD PATIENT");
        addPatient.setBackground(Color.RED);
        addPatient.setForeground(Color.WHITE);
        JButton viewPatient = new JButton("VIEW PATIENT");
        viewPatient.setBackground(Color.RED);
        viewPatient.setForeground(Color.WHITE);
        JButton editPatient = new JButton("EDIT PATIENT");
        editPatient.setBackground(Color.RED);
        editPatient.setForeground(Color.WHITE);

        btnPnl.add(addPatient);
        btnPnl.add(Box.createVerticalStrut(20));
        btnPnl.add(viewPatient);
        btnPnl.add(Box.createVerticalStrut(20));
        btnPnl.add(editPatient);
        btnPnl.add(Box.createVerticalStrut(20));

        a.add(btnPnl, BorderLayout.CENTER);
        fr.add(a, BorderLayout.WEST);

        JPanel det = new JPanel(new BorderLayout());
        det.setBackground(new Color(0, 0, 0, 150));
        det.setOpaque(true);
        det.setPreferredSize(new Dimension(427, 450));

        addPatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                det.removeAll();
                det.repaint();
                det.revalidate();

                JPanel p = new JPanel();
                p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
                p.setOpaque(false);

                JLabel patName = new JLabel("Patient Name", SwingConstants.CENTER);
                patName.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 16));
                patName.setForeground(Color.WHITE);
                JTextField txtpatName = new JTextField();
                txtpatName.setAlignmentX(Component.LEFT_ALIGNMENT);
                txtpatName.setMaximumSize(new Dimension(300, 30));

                JLabel diag = new JLabel("Diagnosed with", SwingConstants.CENTER);
                diag.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 16));
                diag.setForeground(Color.WHITE);
                JTextField txtdiag = new JTextField();
                txtdiag.setAlignmentX(Component.LEFT_ALIGNMENT);
                txtdiag.setMaximumSize(new Dimension(300, 30));

                JLabel docName = new JLabel("Attended by", SwingConstants.CENTER);
                docName.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 16));
                docName.setForeground(Color.WHITE);
                JTextField txtdocName = new JTextField();
                txtdocName.setAlignmentX(Component.LEFT_ALIGNMENT);
                txtdocName.setMaximumSize(new Dimension(300, 30));

                JLabel contact = new JLabel("Contact Number", SwingConstants.CENTER);
                contact.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 16));
                contact.setForeground(Color.WHITE);
                JTextField txtcontact = new JTextField();
                txtcontact.setAlignmentX(Component.LEFT_ALIGNMENT);
                txtcontact.setMaximumSize(new Dimension(300, 30));

                JButton submit = new JButton("SUBMIT");
                submit.setBackground(Color.RED);
                submit.setForeground(Color.WHITE);

                p.add(patName);
                p.add(txtpatName);
                p.add(Box.createVerticalStrut(20));
                p.add(diag);
                p.add(txtdiag);
                p.add(Box.createVerticalStrut(20));
                p.add(docName);
                p.add(txtdocName);
                p.add(Box.createVerticalStrut(20));
                p.add(contact);
                p.add(txtcontact);
                p.add(Box.createVerticalStrut(40));
                p.add(submit);

                p.setBorder(BorderFactory.createEmptyBorder(50, 90, 0, 0));

                det.add(p, BorderLayout.CENTER);
                det.add(submit, BorderLayout.SOUTH);
                fr.add(det, BorderLayout.CENTER);

                fr.repaint();
                fr.revalidate();

                submit.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Connection con = DriverManager.getConnection(JDBC_URL, user, password);
                            PreparedStatement ps1 = con.prepareStatement("USE HOSPITAL_MS");
                            ps1.execute();
                            PreparedStatement ps2 = con.prepareStatement(
                                    "INSERT INTO PATIENT_RECORDS(PATIENT_NAME, DIAGNOSED_WITH, ATTENDED_BY, NUMBER) VALUES (?, ?, ?, ?)");
                            ps2.setString(1, txtpatName.getText());
                            ps2.setString(2, txtdiag.getText());
                            ps2.setString(3, txtdocName.getText());
                            ps2.setString(4, txtcontact.getText());
                            ps2.execute();
                            JOptionPane.showMessageDialog(fr, "DATA ADDED SUCCESSFULLY");
                        } catch (SQLException exc) {
                            exc.printStackTrace();
                        }

                    }
                });

            }
        });

        viewPatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remove det panel from frame
                det.removeAll();
                det.repaint();
                det.revalidate();

                // Update det panel with new content
                drawTable("PATIENT_RECORDS", det);

                // Add updated det panel back to frame
                fr.add(det);
                fr.revalidate();
                fr.repaint();
            }
        });

        editPatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                det.removeAll();
                det.repaint();
                det.revalidate();

                // Update det panel with new content
                drawTable("PATIENT_RECORDS", det);

                JButton edit = new JButton("EDIT");
                edit.setBackground(Color.RED);
                edit.setForeground(Color.WHITE);

                jt.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent me) {

                        edit.addMouseListener(new MouseAdapter() {
                            public void mouseClicked(MouseEvent ae) {

                                int row = jt.rowAtPoint(me.getPoint());
                                int col = jt.columnAtPoint(me.getPoint());
                                if (row >= 0 && col >= 0) {
                                    JFrame f = new JFrame();
                                    f.setSize(400, 350);
                                    f.setVisible(true);
                                    f.setLocationRelativeTo(null);

                                    JPanel b = new JPanel();
                                    b.setLayout(new BoxLayout(b, BoxLayout.Y_AXIS));

                                    String oldname = (String) mod.getValueAt(row, 0);
                                    String oldcontact = (String) mod.getValueAt(row, 3);

                                    JTextField newName = new JTextField((String) mod.getValueAt(row, 0));
                                    newName.setMaximumSize(new Dimension(340, 30));
                                    JTextField newDiag = new JTextField((String) mod.getValueAt(row, 1));
                                    newDiag.setMaximumSize(new Dimension(340, 30));
                                    JTextField newDoc = new JTextField((String) mod.getValueAt(row, 2));
                                    newDoc.setMaximumSize(new Dimension(340, 30));
                                    JTextField newContact = new JTextField((String) mod.getValueAt(row, 3));
                                    newContact.setMaximumSize(new Dimension(340, 30));

                                    JButton save = new JButton("SAVE");
                                    save.setBackground(Color.RED);
                                    save.setForeground(Color.WHITE);

                                    b.add(newName);
                                    b.add(Box.createVerticalStrut(20));
                                    b.add(newDiag);
                                    b.add(Box.createVerticalStrut(20));
                                    b.add(newDoc);
                                    b.add(Box.createVerticalStrut(20));
                                    b.add(newContact);
                                    b.add(Box.createVerticalStrut(20));

                                    b.setBorder(BorderFactory.createEmptyBorder(20, 40, 0, 0));

                                    f.add(b, BorderLayout.CENTER);
                                    f.add(save, BorderLayout.SOUTH);

                                    save.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent ae) {
                                            try {
                                                Connection con = DriverManager.getConnection(JDBC_URL, user, password);
                                                PreparedStatement ps1 = con.prepareStatement("USE HOSPITAL_MS");
                                                ps1.execute();
                                                PreparedStatement ps2 = con.prepareStatement(
                                                        "UPDATE PATIENT_RECORDS SET PATIENT_NAME='" + newName.getText()
                                                                + "', DIAGNOSED_WITH='" + newDiag.getText()
                                                                + "', ATTENDED_BY='"
                                                                + newDoc.getText() + "', NUMBER='"
                                                                + newContact.getText()
                                                                + "' WHERE PATIENT_NAME='"
                                                                + oldname + "' AND NUMBER='" + oldcontact + "'");

                                                ps2.execute();

                                                JOptionPane.showMessageDialog(f, "EDITS MADE SUCCESSFULLY");
                                                f.dispose();
                                                mod.setRowCount(0);
                                                jt.revalidate();
                                                jt.repaint();
                                                refreshData();
                                            } catch (SQLException exc) {
                                                exc.printStackTrace();
                                            }
                                        }
                                    });

                                }
                            }
                        });

                    }
                });

                det.add(edit, BorderLayout.SOUTH);

                // Add updated det panel back to frame
                fr.add(det);
                fr.revalidate();
                fr.repaint();
            }
        });

        fr.setVisible(true);
    }

    public static void drawTable(String tablename, JPanel det) {
        mod = new DefaultTableModel();
        jt = new JTable();
        jt.setModel(mod);

        mod.addColumn("PATIENT NAME");
        mod.addColumn("DIAGNOSIS");
        mod.addColumn("ATTENDED BY");
        mod.addColumn("PATIENT CONTACT");

        try {
            Connection con = DriverManager.getConnection(JDBC_URL, user, password);
            PreparedStatement info = con.prepareStatement("Select * FROM " + tablename);
            ResultSet rs = info.executeQuery();

            while (rs.next()) {
                Object[] rowData = {
                        rs.getString("PATIENT_NAME"),
                        rs.getString("DIAGNOSED_WITH"),
                        rs.getString("ATTENDED_BY"),
                        rs.getString("NUMBER"),
                };
                mod.addRow(rowData);
            }

            jt.setBackground(Color.LIGHT_GRAY);
            jt.setForeground(Color.WHITE);
            jt.setSelectionBackground(Color.RED);
            jt.setSelectionForeground(Color.WHITE);

            jt.setFont(new Font("Sans-Serif", Font.BOLD, 15));
            jt.setRowHeight(25);

            JScrollPane scrl = new JScrollPane(jt);
            det.add(scrl, BorderLayout.CENTER);
            det.revalidate();
            det.repaint();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void refreshData() {
        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, user, password);
            Statement statement = connection.createStatement();
            PreparedStatement p = connection.prepareStatement("USE HOSPITAL_MS");
            p.execute();
            String query = "SELECT * FROM PATIENT_RECORDS";
            ResultSet resultSet = statement.executeQuery(query);

            PreparedStatement info = connection.prepareStatement("Select*from PATIENT_RECORDS");
            ResultSet rs = info.executeQuery();

            while (rs.next()) {
                Object[] rowData = {
                        rs.getString("PATIENT_NAME"),
                        rs.getString("DIAGNOSED_WITH"),
                        rs.getString("ATTENDED_BY"),
                        rs.getString("NUMBER")
                };
                mod.addRow(rowData);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(fr, "Error retrieving data from the database.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}