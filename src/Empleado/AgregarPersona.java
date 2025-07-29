/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Empleado;

/**
 *
 * @author Ana
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import loginform.RegistrarEmpleado;
import loginform.RegistroPersona;

public class AgregarPersona extends JFrame {

    public AgregarPersona() {
        setTitle("Seleccionar Tipo de Persona");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Seleccione el tipo de persona a agregar:", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JButton btnRegistro = new JButton("Persona Registrada");
        btnRegistro.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistroPersona().setVisible(true);
                dispose();
            }
        });

        JButton btnEmpleado = new JButton("Empleado");
        btnEmpleado.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnEmpleado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistrarEmpleado().setVisible(true);
                dispose();
            }
        });

        panel.add(lblTitulo);
        panel.add(btnRegistro);
        panel.add(btnEmpleado);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AgregarPersona().setVisible(true));
    }
}