package Clients;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;


public class EnregistrementClient extends JFrame {
    // les variables
    ConnectionClients con = new ConnectionClients();
    String path = null;
    byte[] userimage = null;
    Statement pst;
    ResultSet rs;
    JLabel lblTitre, lblMail, lblnom, lblclasse, lblsexe, image1;
    JTextField txtmail, txtnom;
    JComboBox<String> combosexe, comboclasse;
    JButton btnenregistrer, btnsupprimer, btntelecharger;
    JTable table, table1;
    JScrollPane scroll, scroll1;

    public EnregistrementClient() {
        this.setTitle("Enregistrement des Clients");
        this.setSize(780, 480);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel pn = new JPanel();
        pn.setLayout(null);
        add(pn);
        pn.setBackground(new Color(220, 210, 220));

        lblTitre = new JLabel("ENREGISTREMENT ETUDIANT");
        lblTitre.setBounds(220, 10, 800, 30);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitre.setForeground(new Color(0, 0, 205));
        pn.add(lblTitre);

        //CodeEtudiant
        lblMail = new JLabel("EMAIL");
        lblMail.setBounds(60, 60, 800, 30);
        lblMail.setFont(new Font("Arial", Font.BOLD, 16));
        pn.add(lblMail);

        txtmail = new JTextField();
        txtmail.setBounds(200, 60, 150, 30);
        txtmail.setFont(new Font("Arial", Font.PLAIN, 14));
        pn.add(txtmail);

        //NomEtudiant
        lblnom = new JLabel("NOM et PRENOM");
        lblnom.setBounds(60, 100, 800, 30);
        lblnom.setFont(new Font("Arial", Font.BOLD, 16));
        lblnom.setForeground(new Color(0, 0, 0));
        pn.add(lblnom);

        txtnom = new JTextField();
        txtnom.setBounds(200, 100, 150, 30); // Correction de l'emplacement

        txtnom.setFont(new Font("Arial", Font.PLAIN, 14));
        pn.add(txtnom);

        //SexeEtudiant
        lblsexe = new JLabel("SEXE");
        lblsexe.setBounds(60, 140, 800, 30);
        lblsexe.setFont(new Font("Arial", Font.BOLD, 16));
        lblsexe.setForeground(new Color(0, 0, 0));
        pn.add(lblsexe);

        combosexe = new JComboBox<>();
        combosexe.setBounds(200, 140, 100, 30);
        combosexe.addItem("");
        combosexe.addItem("Masculin");
        combosexe.addItem("Feminin");
        pn.add(combosexe);

        //ClasseEtudiant
        lblclasse = new JLabel(("CLASSE"));
        lblclasse.setBounds(60, 180, 800, 30);
        lblclasse.setFont(new Font("Arial", Font.BOLD, 16));
        lblclasse.setForeground(new Color(0, 0, 0));
        pn.add(lblclasse);

        comboclasse = new JComboBox<>();
        comboclasse.setBounds(200, 180, 100, 30);
        comboclasse.setFont(new Font("Arial", Font.PLAIN, 14));
        comboclasse.addItem("");
        comboclasse.addItem("BTS SIO SLAM INIT 1");
        comboclasse.addItem("BTS SIO SLAM INIT 1");
        comboclasse.addItem("BTS SIO SISR INIT 1");
        comboclasse.addItem("BTS SIO SISR INIT 1");
        pn.add(comboclasse);



        //bouton d'enregistrement
        btnenregistrer = new JButton("ENREGISTRER");
        btnenregistrer.setBounds(200, 230, 150, 30);
        btnenregistrer.setFont(new Font("Arial", Font.BOLD, 15));
        btnenregistrer.setForeground(Color.BLACK);
        btnenregistrer.setBackground(new Color(173, 216, 230));

        btnenregistrer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                String mail, nom, sexe, classe;

                mail = txtmail.getText();
                nom = txtnom.getText(); // Correction du champ de texte utilisé
                sexe = combosexe.getSelectedItem().toString();
                classe = comboclasse.getSelectedItem().toString();

                String rq = "insert into tb_etudiant(mail,nom,sexe,classe) values(?,?,?,?)";
                try {
                    PreparedStatement ps = con.maConnection().prepareStatement(rq);
                    ps.setString(1, mail);
                    ps.setString(2, nom);
                    ps.setString(3, sexe);
                    ps.setString(4, classe);

                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Etudiant Enregistrer!", null, JOptionPane.INFORMATION_MESSAGE);
                    con.maConnection().close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur!" + ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
                }
                dispose();
                setVisible(true); // Correction de l'instanciation de la classe
            }
        });
        pn.add(btnenregistrer);

        //bouton suppression
        btnsupprimer = new JButton("SUPPRIMER");
        btnsupprimer.setBounds(370, 230, 150, 30);
        btnsupprimer.setFont(new Font("Arial", Font.BOLD, 15));
        btnsupprimer.setForeground(Color.BLACK);
        btnsupprimer.setBackground(new Color(173, 216, 230));
        btnsupprimer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                String mail;
                mail = txtmail.getText();
                String rq = "delete from tb_etudiant where mail = '" + mail + "'";
                try {
                    pst = con.maConnection().createStatement();
                    pst.executeUpdate(rq);
                    JOptionPane.showMessageDialog(null, "Etudiant Supprimer!", null, JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur!" + ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
                }
                dispose();
                setVisible(true); // Correction de l'instanciation de la classe
            }
        });
        pn.add(btnsupprimer);

        //bouton rechercher
        btntelecharger = new JButton("RECHERHCE");
        btntelecharger.setBounds(360, 60, 150, 30);
        btntelecharger.setFont(new Font("Arial", Font.BOLD, 14));
        btntelecharger.setForeground(Color.BLACK);
        btntelecharger.setBackground(new Color(173, 216, 230));
        btntelecharger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntelechargerActionPerformed(evt);
            }

            private void btntelechargerActionPerformed(ActionEvent evt) {
                String mail;
                mail = txtmail.getText();

                try {
                    String rq = "select * from tb_etudiant where mail =?";
                    PreparedStatement ps = con.maConnection().prepareStatement(rq);
                    ps.setString(1, mail);
                    rs = ps.executeQuery();
                    if (rs.next() == false) {

                        JOptionPane.showMessageDialog(null, "Cet etudiant n'existe pas", null, JOptionPane.ERROR_MESSAGE);
                        txtmail.setText("");
                    } else {
                        txtnom.setText(rs.getString(2).trim());
                        combosexe.setSelectedItem(rs.getString(3).trim());
                        comboclasse.setSelectedItem(rs.getString(4).trim());
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur!" + ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        pn.add(btntelecharger);

        //Liste des etudiants
        DefaultTableModel model = new DefaultTableModel();
        table1 = new JTable();
        JScrollPane scroll1 = new JScrollPane(table1);
        scroll1.setBounds(60, 280, 570, 80);
        pn.add(scroll1);
        model.addColumn("Mail");
        model.addColumn("Nom et Prenom");
        model.addColumn("Sexe");
        model.addColumn("Classe");

        table1.setModel(model);
        String sql = "select * from tb_etudiant order by id desc";
        try {
            pst = con.maConnection().createStatement();
            rs = pst.executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("mail"),
                        rs.getString("nom"),
                        rs.getString("sexe"),
                        rs.getString("classe")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur", null, JOptionPane.ERROR_MESSAGE);
        }

        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                table1MouseReleased(evt);
            }

            private void table1MouseReleased(MouseEvent evt) {
                int selectionner = table1.getSelectedRow();
                DefaultTableModel model = (DefaultTableModel) table1.getModel();
                txtmail.setText(model.getValueAt(selectionner, 0).toString());
                txtnom.setText(model.getValueAt(selectionner, 1).toString());
                combosexe.setSelectedItem(model.getValueAt(selectionner, 2).toString());
                comboclasse.setSelectedItem(model.getValueAt(selectionner, 3).toString());
            }
        });
    }

    public static void main(String[] args) {
        EnregistrementClient en = new EnregistrementClient();
        en.setVisible(true);
    }

    // Méthode pour instancier la classe
    private void init() {
        // Logique d'initialisation ici
    }
}
