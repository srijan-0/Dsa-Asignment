/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.DashController;
import controller.LoginController;
import controller.Posts;
import controller.SocialGraph;
import controller.TreeNode;
import controller.postController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.*;

public class Dashboard extends javax.swing.JFrame {

    public static String name = LoginController.getCurrentUser();
    boolean liked = false;

    public void createLabels() {
        JPanel postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
        setLayout(new BorderLayout());

        SocialGraph sg = new SocialGraph();
        List<Posts> posts = sg.getAllPosts();

        List<JLabel> labels = createPostLabels(posts);

        for (JLabel label : labels) {
            postPanel.add(label);
            postPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add some space between labels
        }

        JScrollPane scrollPane = new JScrollPane(postPanel);
        add(scrollPane, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
    }

    private void refresh() {
        Dashboard d = new Dashboard(name);
        d.setVisible(true);
        d.setLocationRelativeTo(null);
        this.dispose();
    }

    public List<JLabel> createPostLabels(List<Posts> posts) {
        List<JLabel> postLabels = new ArrayList<>();

        for (Posts post : posts) {
            String labelText = "Username: " + post.getUsername() + "\nDescription: " + post.getDescription();
            JLabel label = new JLabel(labelText);
            postLabels.add(label);
        }

        return postLabels;
    }

    private void createTextAreas(List<Posts> posts) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(204, 255, 255));

        postController pc = new postController();

        for (Posts post : posts) {
            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);

            String labelText = post.getUsername() + " posted\n" + post.getDescription() + "\n\nLikes: " + pc.getLikesCount(post.getId());
            textArea.setText(labelText);
            textArea.setBackground(new Color(204, 255, 255));

            JButton likeButton;

            if (!liked) {
                likeButton = new JButton("Like");
                likeButton.setBackground(new Color(102, 0, 204));
            } else {
                likeButton = new JButton("Unlike");
                likeButton.setBackground(Color.RED);
            }

            likeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle the like button click event here
                    // You can implement the logic to handle liking or unliking a post
                    // For example, update the database, increment or decrement like count, etc.
                    System.out.println(liked);
                    if (!liked) {
                        pc.incrementLikes(post.getId());
                        likeButton.setText("Unlike");
                        likeButton.setBackground(Color.RED);
                        liked = true;
                        System.out.println("Liked post by: " + post.getUsername());
                    } else {
                        pc.decrementLikes(post.getId());
                        likeButton.setText("Like");
                        likeButton.setBackground(new Color(102, 0, 204));
                        liked = false;
                        System.out.println("Unliked post by: " + post.getUsername());
                    }
                }
            });

            panel.add(textArea);
            panel.add(likeButton);
            panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add some spacing
        }

        postArea.setViewportView(panel);
    }

    public void createFriendLabels(List<String> usernames) {
        for (String username : usernames) {
            JLabel friendLabel = new JLabel("Friend: " + username);
            friendArea.append(username + "\n");
        }
    }

    private void createFriendList(List<String> friends) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//        panel.setBackground(new Color(102, 0, 204));

        postController pc = new postController();

        for (String friend : friends) {
            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);

            String labelText = friend;
            textArea.setText(labelText);
            textArea.setBackground(new Color(204, 255, 255));

            JButton unfollow;
            unfollow = new JButton("Unfollow");
            unfollow.setBackground(Color.RED);

            unfollow.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle the like button click event here
                    // You can implement the logic to handle liking or unliking a post
                    // For example, update the database, increment or decrement like count, etc.

                    SocialGraph sg = new SocialGraph();
                    sg.detachConnection(name, friend);
                    refresh();

                }
            });

            panel.add(textArea);
            panel.add(unfollow);
            panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add some spacing
        }

        friendScrollPane.setViewportView(panel);
    }

    private void createRecomendationList(List<String> friends) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(204, 255, 255));

        postController pc = new postController();

        for (String friend : friends) {
            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);

            String labelText = friend;
            textArea.setText(labelText);
            textArea.setBackground(new Color(102, 0, 204));

            JButton unfollow;
            unfollow = new JButton("Follow");
            unfollow.setBackground(Color.GREEN);

            unfollow.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle the like button click event here
                    // You can implement the logic to handle liking or unliking a post
                    // For example, update the database, increment or decrement like count, etc.

                    SocialGraph sg = new SocialGraph();
                    sg.establishConnection(name, friend);
                    
                    refresh();

                }
            });

            panel.add(textArea);
            panel.add(unfollow);
            panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add some spacing
        }

        recomendScrollPane.setViewportView(panel);
    }

    /**
     * Creates new form Dashbaoard
     */
    public Dashboard(String user) {
        initComponents();

        String currentUser = user;
        userName.setText(currentUser);
        SocialGraph sg = new SocialGraph();
        List<Posts> posts = sg.getPostsFromFollowingUsers();
        createTextAreas(posts);

//        System.out.println(sg.getPostsFromFollowingUsers());
        List<JLabel> labels = createPostLabels(sg.getAllPosts());

        TreeNode tn = new TreeNode(user);
        System.out.println("followers = " + tn.followers);
        System.out.println("following = " + tn.following);
        System.out.println("mutual = " + tn.mutual);
        System.out.println("recomendation = " + tn.getRecommendation(currentUser));

        createFriendList(tn.mutual);

        createRecomendationList(tn.getRecommendation(currentUser));


    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        userName = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        recomendScrollPane = new javax.swing.JScrollPane();
        recomendArea = new javax.swing.JTextArea();
        friendScrollPane = new javax.swing.JScrollPane();
        friendArea = new javax.swing.JTextArea();
        searchLabel = new javax.swing.JTextField();
        postArea = new javax.swing.JScrollPane();
        newPost = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setText("Create new Post!");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 110, -1));
        getContentPane().add(userName, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 50, 80, 20));

        jButton1.setBackground(new java.awt.Color(102, 153, 255));
        jButton1.setText("POST SOMETHING!!");
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 400, -1));

        recomendArea.setBackground(new java.awt.Color(102, 153, 255));
        recomendArea.setColumns(20);
        recomendArea.setRows(5);
        recomendScrollPane.setViewportView(recomendArea);

        getContentPane().add(recomendScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 320, 250, 210));

        friendArea.setEditable(false);
        friendArea.setBackground(new java.awt.Color(102, 153, 255));
        friendArea.setColumns(20);
        friendArea.setRows(5);
        friendScrollPane.setViewportView(friendArea);

        getContentPane().add(friendScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 60, 250, 210));

        searchLabel.setBackground(new java.awt.Color(102, 153, 255));
        searchLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchLabelActionPerformed(evt);
            }
        });
        getContentPane().add(searchLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 300, 30));

        postArea.setOpaque(false);

        newPost.setEditable(false);
        newPost.setBackground(new java.awt.Color(153, 153, 255));
        newPost.setColumns(20);
        newPost.setLineWrap(true);
        newPost.setWrapStyleWord(true);
        newPost.setRows(5);
        postArea.setViewportView(newPost);

        getContentPane().add(postArea, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 570, 410));

        jLabel10.setText("Friend List!");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 20, -1, -1));

        jLabel9.setText("People you may know!");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 290, 140, -1));

        jLabel7.setBackground(new java.awt.Color(102, 102, 255));
        jLabel7.setText("ADD");
        jLabel7.setToolTipText("");
        jLabel7.setOpaque(true);
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 30, 70, 30));

        jLabel6.setText("Search User");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 120, 30));

        jLabel3.setBackground(new java.awt.Color(102, 255, 102));
        jLabel3.setOpaque(true);
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-1, 0, 910, 550));

        jLabel12.setText("Friend List!");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 100, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Post p = new Post();
        p.setVisible(true);
        p.setLocationRelativeTo(null);

//        this.setEnabled(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void searchLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchLabelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchLabelActionPerformed

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
        DashController dc = new DashController();
        boolean found = dc.findFriend(searchLabel.getText());
        System.out.println(found);

        if (found) {
            // Show a confirmation dialog
            int option = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to follow " + searchLabel.getText() + "?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                // User clicked "Yes", add friend logic here
                System.out.println("following!");
                SocialGraph sg = new SocialGraph();
                sg.establishConnection(name, searchLabel.getText());
            } else {
                // User clicked "No" or closed the dialog
                System.out.println("not following");
            }
        } else {
            // Show a warning dialog for user not found
            JOptionPane.showMessageDialog(this, "User does not exist!", "Warning", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_jLabel7MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Dashboard db = new Dashboard(name);
                db.setVisible(true);
                db.setLocationRelativeTo(null);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea friendArea;
    private javax.swing.JScrollPane friendScrollPane;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextArea newPost;
    private javax.swing.JScrollPane postArea;
    private javax.swing.JTextArea recomendArea;
    private javax.swing.JScrollPane recomendScrollPane;
    private javax.swing.JTextField searchLabel;
    private javax.swing.JLabel userName;
    // End of variables declaration//GEN-END:variables
}
