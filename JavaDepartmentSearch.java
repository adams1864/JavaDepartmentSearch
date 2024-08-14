import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
//importing packages
public class DepartmentSearches implements ActionListener {
    private JPanel p1;
    private JTextField txtdepartment_code;
    private JButton SEARCHButton;
    private JLabel resultLabel;
//instantiation of components of GUI
    DepartmentSearches() {
        JFrame f1 = new JFrame();
        f1.setTitle("Search Department");
        f1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f1.setSize(400, 200);
        p1 = new JPanel();
        txtdepartment_code = new JTextField(10);
        SEARCHButton = new JButton("SEARCH");
        resultLabel = new JLabel("Result: ");

        p1.add(new JLabel("Department Code:"));
        p1.add(txtdepartment_code);
        p1.add(SEARCHButton);
        p1.add(resultLabel);

        SEARCHButton.addActionListener(this);

        f1.add(p1);
        f1.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == SEARCHButton) {
            searchData();
        }
    }

    private void searchData() {
        String dc = txtdepartment_code.getText();
        String path = "jdbc:mysql://localhost:3306/university";
        String un = "root";
        String pw = "";

        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(path, un, pw);
            stat = conn.createStatement();
            String query = "SELECT department_name FROM dept WHERE department_code='" + dc + "'";
            rs = stat.executeQuery(query);

            if (rs.next()) {
                String department_name = rs.getString("department_name");
                resultLabel.setText("Result: " + department_name);
                JOptionPane.showMessageDialog(null, "Department Found: " + department_name);
            } else {
                resultLabel.setText("Result: Department Not Found!");
                JOptionPane.showMessageDialog(null, "Department Not Found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stat != null) stat.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DepartmentSearches());
    }
}
