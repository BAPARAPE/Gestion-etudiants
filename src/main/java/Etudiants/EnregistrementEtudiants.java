package Etudiants;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.*;

public class EnregistrementEtudiants extends JFrame{

    // les variables
    ConnectionEtudiants con = new ConnectionEtudiants();
    String path = null;
    Statement pst;
    ResultSet rs;
    JLabel lblTitre, lblnom, lblmail, lblclasse, lblsexe, image1;
    JTextField txtnom, txtmail;
    JComboBox<String> combosexe, comboclasse;
    JButton btnenregistrer, btnsupprimer, btntelecharger;
    JTable table, table1;
    JScrollPane scroll, scroll1;

    public EnregistrementEtudiants() {
        this.setTitle("Enregistrements des Etudiants");
        this.setSize(750, 550);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel pn = new JPanel();
        pn.setLayout(null);
        add(pn);
        pn.setBackground(new Color(65, 131, 190));

        lblTitre = new JLabel("GESTION ETUDIANTS");
        lblTitre.setBounds(215, 10, 800, 30);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitre.setForeground(Color.BLACK);
        pn.add(lblTitre);

        //NomEtudiant
        lblnom = new JLabel("NOM PRENOM");
        lblnom.setBounds(60, 70, 800, 30);
        lblnom.setFont(new Font("Arial", Font.BOLD, 16));
        lblnom.setForeground(new Color(0, 0, 0));
        pn.add(lblnom);

        txtnom = new JTextField();
        txtnom.setBounds(200, 70, 170, 30); // Correction de l'emplacement
        //txtnom.setText("Ben Toko");
        txtnom.setFont(new Font("Arial", Font.PLAIN, 14));
        pn.add(txtnom);

        //CodeEtudiant
        lblmail= new JLabel("EMAIL");
        lblmail.setBounds(60, 110, 800, 30);
        lblmail.setFont(new Font("Arial", Font.BOLD, 16));
        pn.add(lblmail);

        txtmail = new JTextField();
        txtmail.setBounds(200, 110, 170, 30);
        txtmail.setFont(new Font("Arial", Font.PLAIN, 14));
        pn.add(txtmail);

        //SexeEtudiant
        lblsexe = new JLabel("SEXE");
        lblsexe.setBounds(60, 150, 800, 30);
        lblsexe.setFont(new Font("Arial", Font.BOLD, 16));
        lblsexe.setForeground(new Color(0, 0, 0));
        pn.add(lblsexe);

        combosexe = new JComboBox<>();
        combosexe.setBounds(200, 150, 170, 30);
        combosexe.addItem("");
        combosexe.addItem("Masculin");
        combosexe.addItem("Feminin");
        pn.add(combosexe);
        Color iconColor = new Color(65, 131, 190); // Couleur spécifiée
        UIDefaults defaults = new UIDefaults();
        defaults.put("ComboBox.buttonBackground", iconColor);
        defaults.put("ComboBox.buttonShadow", iconColor);
        defaults.put("ComboBox.buttonDarkShadow", iconColor);
        defaults.put("ComboBox.buttonHighlight", iconColor);
        combosexe.putClientProperty("Nimbus.Overrides", defaults);
        combosexe.putClientProperty("Nimbus.Overrides.InheritDefaults", true);

        //ClasseEtudiant
        lblclasse = new JLabel(("CLASSE"));
        lblclasse.setBounds(60, 190, 800, 30);
        lblclasse.setFont(new Font("Arial", Font.BOLD, 16));
        lblclasse.setForeground(new Color(0, 0, 0));
        pn.add(lblclasse);

        comboclasse = new JComboBox<>();
        comboclasse.setBounds(200, 190, 170, 30);
        comboclasse.setFont(new Font("Arial", Font.PLAIN, 14));
        comboclasse.addItem("");
        comboclasse.addItem("BTS SIO SLAM INIT 1");
        comboclasse.addItem("BTS SIO SLAM INIT 1");
        comboclasse.addItem("BTS SIO SISR INIT 1");
        comboclasse.addItem("BTS SIO SISR INIT 1");
        pn.add(comboclasse);


        //bouton d'enregistrement
        btnenregistrer = new JButton("ENREGISTRER");
        btnenregistrer.setBounds(200, 242, 150, 33);
        btnenregistrer.setFont(new Font("Arial", Font.BOLD, 15));
        btnenregistrer.setForeground(Color.BLACK);
        btnenregistrer.setBackground(new Color(65, 131, 190));

        btnenregistrer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                String mail, nom, sexe, classe;

                mail = txtmail.getText();
                nom = txtnom.getText();
                sexe = combosexe.getSelectedItem().toString();
                classe = comboclasse.getSelectedItem().toString();

                if (mail.isEmpty() || nom.isEmpty() || sexe.isEmpty() || classe.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs!", null, JOptionPane.WARNING_MESSAGE);
                    return; // Arrêter l'exécution de la méthode si des champs sont vides
                }

                String rq = "insert into tb_etudiant(nom, mail, sexe, classe) values(?, ?, ?, ?)";
                try {
                    PreparedStatement ps = con.maConnection().prepareStatement(rq);
                    ps.setString(1, mail);
                    ps.setString(2, nom);
                    ps.setString(3, sexe);
                    ps.setString(4, classe);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Étudiant enregistré avec succès!", null, JOptionPane.INFORMATION_MESSAGE);
                    con.maConnection().close();
                } catch (SQLException ex) {
                    ex.printStackTrace(); // Imprimer la trace complète de l'exception
                    JOptionPane.showMessageDialog(null, "Erreur lors de l'enregistrement de l'étudiant: " + ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        pn.add(btnenregistrer);

        //bouton suppression
        btnsupprimer = new JButton("SUPPRIMER");
        btnsupprimer.setBounds(385, 242, 150, 33);
        btnsupprimer.setFont(new Font("Arial", Font.BOLD, 15));
        btnsupprimer.setForeground(Color.BLACK);
        btnsupprimer.setBackground(new Color(65, 131, 190));
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
        btntelecharger.setBounds(382, 70, 150, 33);
        btntelecharger.setFont(new Font("Arial", Font.BOLD, 14));
        btntelecharger.setForeground(Color.BLACK);
        btntelecharger.setBackground(new Color(65, 131, 190));
        btntelecharger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntelechargerActionPerformed(evt);
            }

            private void btntelechargerActionPerformed(ActionEvent evt) {
                String num;
                num = txtnom.getText();

                try {
                    String rq = "select * from tb_etudiant where mail =?";
                    PreparedStatement ps = con.maConnection().prepareStatement(rq);
                    ps.setString(1, num);
                    rs = ps.executeQuery();
                    if (rs.next() == false) {

                        JOptionPane.showMessageDialog(null, "Matricule n'existe pas", null, JOptionPane.ERROR_MESSAGE);
                        txtnom.setText("");
                    } else {
                        txtnom.setText(rs.getString(2).trim());
                        combosexe.setSelectedItem(rs.getString(3).trim());
                        comboclasse.setSelectedItem(rs.getString(4).trim());
                        try {
                            Blob blob1 = rs.getBlob("photo");
                            byte[] imagebyte = blob1.getBytes(1, (int) blob1.length());

                            ImageIcon imag = new ImageIcon(new ImageIcon(imagebyte).getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
                            image1.setIcon(imag);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Erreur!" + e.getMessage(), null, JOptionPane.ERROR_MESSAGE);
                        }

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
        scroll1.setBounds(80, 340, 600, 100);
        pn.add(scroll1);
        model.addColumn("Nom et Prenom");
        model.addColumn("Mail");
        model.addColumn("Sexe");
        model.addColumn("Classe");

        table1.setModel(model);
        String sql = "select * from tb_etudiant order by id desc";
        try {
            pst = con.maConnection().createStatement();
            rs = pst.executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("nom"),
                        rs.getString("mail"),
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
                txtnom.setText(model.getValueAt(selectionner, 0).toString());
                txtmail.setText(model.getValueAt(selectionner, 1).toString());
                combosexe.setSelectedItem(model.getValueAt(selectionner, 2).toString());
                comboclasse.setSelectedItem(model.getValueAt(selectionner, 3).toString());
            }
        });
    }

    public static void main(String[] args) {
        EnregistrementEtudiants en = new EnregistrementEtudiants();
        en.setVisible(true);
    }

}
