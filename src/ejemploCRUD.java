import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ejemploCRUD extends JDialog {
        PreparedStatement ps;
    private JPanel contentPane;
    private JButton crearButton;
    private JButton buscarButton;
    private JButton ACTUALIZARButton;
    private JButton ELIMINARButton;
    private JTextField cedulaTxt;
    private JTextField celularTxt;
    private JTextField nombreTxt;
    private JTextField apellidoTxt;

    public ejemploCRUD() {
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con;
                try {
                    con = getConection();
                    ps = con.prepareStatement("INSERT INTO datosCRUD ( cedula, celular, nombre, apellido) VALUES(?,?,?,?) ");
                    ps.setString(1, cedulaTxt.getText());
                    ps.setString(2, celularTxt.getText());
                    ps.setString(3, nombreTxt.getText());
                    ps.setString(4, apellidoTxt.getText());

                    System.out.println(ps);
                    int res = ps.executeUpdate();

                    if (res > 0) {
                        JOptionPane.showMessageDialog(null, "Persona Creada");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al Crear persona");
                    }

                    limpiar();
                    con.close();

                } catch (HeadlessException | SQLException f) {
                    System.err.println(f);
                }
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con;

                try {
                    con = getConection();
                    String query = "select * from datosCRUD;";
                    Statement s = con.createStatement();
                    ps = con.prepareStatement("SELECT * FROM datosCRUD WHERE cedula = ?");
                    ps.setString(1, cedulaTxt.getText());
                    ResultSet rs = s.executeQuery(query);

                    rs = ps.executeQuery();

                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "Persona Encontrada");
                        cedulaTxt.setText(rs.getString("cedula"));
                        celularTxt.setText(rs.getString("celular"));
                        nombreTxt.setText(rs.getString("nombre"));
                        apellidoTxt.setText(rs.getString("apellido"));
                    } else {
                        JOptionPane.showMessageDialog(null, "No existe una persona con ese número de cédula");
                        limpiar();
                    }

                } catch (HeadlessException | SQLException f) {
                    System.err.println(f);
                }
            }
        });

        ACTUALIZARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscar_Estudiante();
                Connection con;

                try {
                    con = getConection();
                    ps = con.prepareStatement("UPDATE datosCRUD SET cedula=?,celular=?, nombre=?, apellido=? Where cedula = ?");

                    ps.setString(1, cedulaTxt.getText());
                    ps.setString(2, celularTxt.getText());
                    ps.setString(3, nombreTxt.getText());
                    ps.setString(4, apellidoTxt.getText());
                    ps.setString(5, cedulaTxt.getText());
                    System.out.println(ps);

                    int res = ps.executeUpdate();

                    if (res > 0) {
                        JOptionPane.showMessageDialog(null, "Persona Modificada");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al Modificar persona, ingrese una cedula válida");
                    }

                    limpiar();
                    con.close();

                } catch (HeadlessException | SQLException f) {
                    System.err.println(f);
                }
            }
        });

        ELIMINARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscar_Estudiante();
                Connection con;

                try {
                    con = getConection();
                    ps = con.prepareStatement("DELETE FROM datosCRUD  Where cedula = ?");
                    ps.setString(1, cedulaTxt.getText());

                    System.out.println(ps);

                    int res = ps.executeUpdate();

                    if (res > 0) {
                        JOptionPane.showMessageDialog(null, "Persona Eliminada Satisfactoriamente");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al Eliminar Persona, ingrese una cedula válida");
                    }

                    limpiar();
                    con.close();

                } catch (HeadlessException | SQLException f) {
                    System.err.println(f);
                }
            }
        });

    }

    private void buscar_Estudiante(){
        Connection con;

        try {
            con = getConection();
            String query = "select * from datosCRUD;";
            Statement s = con.createStatement();
            ps = con.prepareStatement("SELECT * FROM datosCRUD WHERE cedula = ?");
            ps.setString(1, cedulaTxt.getText());
            ResultSet rs = s.executeQuery(query);

            rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Dato Encontrado");
            } else {
                JOptionPane.showMessageDialog(null, "No existe una persona con ese número de cédula");
                limpiar();
            }

        } catch (HeadlessException | SQLException f) {
            System.err.println(f);
        }
    }

    private void limpiar() {
        nombreTxt.setText(null);
        apellidoTxt.setText(null);
        cedulaTxt.setText(null);
        celularTxt.setText(null);
    }
    public static Connection getConection(){
        Connection con = null;
        String url = "jdbc:mysql://localhost/ejemploCRUD",
                user = "root",
                password = "UGPCUGR2002";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e);
        }
        return con;
    }


    public static void main(String[] args) {
        JFrame frame =new JFrame("Néstor Chumania");

        frame.setContentPane(new ejemploCRUD().contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,1000);
        frame.pack();
        frame.setVisible(true);
    }
}