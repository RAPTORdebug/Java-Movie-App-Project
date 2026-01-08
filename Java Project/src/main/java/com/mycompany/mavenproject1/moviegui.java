/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.mavenproject1;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.border.EmptyBorder;

/**i 
 *
 * @author sihat
 */
public class moviegui extends javax.swing.JFrame {
    
     private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(moviegui.class.getName());
     private JLabel backgroundLabel;
     private JLabel movieLabel;
     private JLabel trendingMovieLabel;
     private JLabel trendingMovieLabelHeader;
     private boolean isExpanded = true;
     private int panelWidth = 180;
     private transient config client = new config("a47d66dcad20b616da0c76f004acedbd"); 

      
    private String[] movieImages = {
        "/com/mycompany/mavenproject1/ballerina-movie1.jpg",
        "/com/mycompany/mavenproject1/daredevil-born1.jpg",
        "/com/mycompany/mavenproject1/optimus-prime1.jpg",
        "/com/mycompany/mavenproject1/the-wild-robot1.jpg",
        "/com/mycompany/mavenproject1/a-quiet-place-day1.jpg",
        "/com/mycompany/mavenproject1/jurassic-world1.jpg",
        "/com/mycompany/mavenproject1/chainsaw-man1.jpg"
    };

    private int currentIndex = 0; // tracks which image is showing
    
     
    public moviegui() {
     initComponents();
     setDefaultCloseOperation(EXIT_ON_CLOSE);
     setExtendedState(JFrame.MAXIMIZED_BOTH);
     setLocationRelativeTo(null);

    //Base container 
    jPanel5.setLayout(new BoxLayout(jPanel5, BoxLayout.Y_AXIS));
    jPanel5.setBorder(null);
    jPanel5.setBackground(Color.BLACK);

    //layout panel 1
    jPanel1.setLayout(new BorderLayout());
    jPanel1.setBackground(new Color(60, 60, 80, 200));
    //clock
    Clock clock = new Clock();

    // Transparent panel for clock
    JPanel clockPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
    clockPanel.setOpaque(false);
    //clockPanel.add
    //clockPanel.setLayout(new GridBagLayout());
    clockPanel.add(clock.getLabel());

    // Add to the panel
    jPanel1.add(clockPanel, BorderLayout.SOUTH);

    // Make sure the parent panel is transparent (no background box)
    jPanel1.setOpaque(true);

    // Use BorderLayout for clean right alignment
    //jPanel1.setLayout(new BorderLayout());

   // Create the rounded text field
   RoundedTextField searchField = new RoundedTextField(20);
   searchField.setText("Search...");
   searchField.setForeground(Color.GRAY);
   searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
   searchField.setPreferredSize(new Dimension(200, 28)); 

   // Add placeholder behavior
   searchField.addFocusListener(new java.awt.event.FocusAdapter() {
    @Override
    public void focusGained(java.awt.event.FocusEvent evt) {
        if (searchField.getText().equals("Search...")) {
            searchField.setText("");
            searchField.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(java.awt.event.FocusEvent evt) {
        if (searchField.getText().isEmpty()) {
            searchField.setText("Search...");
            searchField.setForeground(Color.GRAY);
        }
    }
});

    // Create a right-aligned sub-panel
    JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
    rightPanel.setOpaque(false);
    rightPanel.add(jLabel2);
    rightPanel.add(searchField);

    // Add that sub-panel to jPanel1
    jPanel1.add(rightPanel, BorderLayout.EAST);
    jPanel1.setBackground(new Color(30, 30, 30));
  
    // Add action listener to trigger movie search when pressing Enter
    searchField.addActionListener(e -> {
    String query = searchField.getText().trim();

    if (query.isEmpty() || query.equalsIgnoreCase("Search...")) {
        JOptionPane.showMessageDialog(this, "Please enter a movie name.");
        return;
    }

    
    List<Movie> movies = client.searchMovies(query);

    if (movies.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No results found for: " + query);
    } else {
        showMovies(movies); 
    }
});



    //Movie slideshow
    movieLabel = new JLabel();
    movieLabel.setHorizontalAlignment(SwingConstants.LEFT);
    movieLabel.setVerticalAlignment(SwingConstants.CENTER);
    movieLabel.setPreferredSize(new Dimension(0, 500)); // 0 = flexible width
    movieLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

    jPanel5.add(Box.createVerticalStrut(20));
    jPanel5.add(movieLabel);

    //Trending header
    trendingMovieLabelHeader = new JLabel();
    trendingMovieLabelHeader.setPreferredSize(null);
    trendingMovieLabelHeader.setBackground(Color.WHITE);
    trendingMovieLabelHeader.setAlignmentX(Component.LEFT_ALIGNMENT);

    trendingMovieLabel = new JLabel("Trending Movies");
    trendingMovieLabel.setHorizontalAlignment(SwingConstants.CENTER);
    trendingMovieLabel.setVerticalAlignment(SwingConstants.CENTER);
    trendingMovieLabel.setForeground(Color.WHITE);
    trendingMovieLabel.setFont(new Font("Segoe UI Black", Font.BOLD, 18));
    trendingMovieLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

    jPanel5.add(Box.createVerticalStrut(20));
    jPanel5.add(trendingMovieLabelHeader);
    jPanel5.add(Box.createVerticalStrut(10));
    jPanel5.add(trendingMovieLabel);

    //Trending grid
    JPanel trendingPanel = new JPanel(new GridLayout(0, 5, 15, 15));
    trendingPanel.setBackground(Color.BLACK); //changed
    trendingPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    loadTrendingMovies(trendingPanel);

    jPanel5.add(Box.createVerticalStrut(10));
    jPanel5.add(trendingPanel);
    
    //scroll bar
    // Scroll Pane with customized scrollbar
    JScrollPane scrollPane = new JScrollPane(jPanel5);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.getVerticalScrollBar().setUnitIncrement(16);

    // Customize the vertical scrollbar
    JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
    verticalBar.setPreferredSize(new Dimension(12, 0)); // scrollbar width

    verticalBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
    @Override
    protected void configureScrollBarColors() {
        this.thumbColor = Color.WHITE;       // scrollbar thumb color
        this.trackColor = Color.BLACK;     // scrollbar track color
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(thumbColor);
        g2.fillRoundRect(r.x, r.y, r.width, r.height, 10, 10); // rounded thumb
        g2.dispose();
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(trackColor);
        g2.fillRect(r.x, r.y, r.width, r.height);
        g2.dispose();
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private JButton createZeroButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        return button;
    }
});

    // Add scroll pane to main panel
    getContentPane().add(scrollPane, BorderLayout.CENTER);


    //Image switching timer
    SwingUtilities.invokeLater(() -> updateMovieImage(movieLabel, movieImages[0]));
    Timer timer = new Timer(4000, e -> {
        currentIndex = (currentIndex + 1) % movieImages.length;
        updateMovieImage(movieLabel, movieImages[currentIndex]);
    });
    timer.start();
    
     // Make the menu icon clickable
     jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            togglePanel();
         }
    });
     
     jLabel10.addMouseListener(new java.awt.event.MouseAdapter(){
         @Override
         public void mouseClicked(java.awt.event.MouseEvent evt){
             watchlistMovies();
         }
     });
     

    setVisible(true);
}
    
private void loadTrendingMovies(JPanel trendingPanel) {
    List<Movie> trendingMovies = client.getTrendingMovies();
    if (trendingMovies == null || trendingMovies.isEmpty()) {
        trendingPanel.add(new JLabel("No trending movies available right now."));
        return;
    }

    java.util.Collections.shuffle(trendingMovies);
    List<Movie> fewMovies = trendingMovies.subList(0, Math.min(20, trendingMovies.size()));

    for (Movie movie : fewMovies) {
        // Rounded movie card
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
        };
        card.setLayout(new BorderLayout(0, 10)); // add vertical spacing between components
        card.setOpaque(false);
        card.setBackground(new Color(13,11,11));
        card.setPreferredSize(new Dimension(200, 360)); // slightly taller card
        

        //Title bar at top 
        JLabel titleLabel = new JLabel("<html><center>" + movie.getTitle() + "</center></html>");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        card.add(titleLabel, BorderLayout.NORTH);

        // Poster in middle 
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);

        try {
            if (movie.getPosterUrl() != null) {
                java.net.URL url = new java.net.URL(movie.getPosterUrl());
                Image img = javax.imageio.ImageIO.read(url);
                if (img != null) {
                    img = img.getScaledInstance(170, 250, Image.SCALE_SMOOTH); // larger poster

                    JLabel posterLabel = new JLabel(new ImageIcon(img));
                    posterLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    posterLabel.setOpaque(true);
                    posterLabel.setBackground(new Color(13,11,11));
                    posterLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                    posterLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {

                    //Create custom dialog
                    JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(posterLabel), "Movie Info", true);
                    dialog.setUndecorated(true);
                    dialog.setSize(700, 400);
                    dialog.setLocationRelativeTo(posterLabel);

                    // Make the dialog background transparent
                    dialog.setBackground(new Color(0, 0, 0, 0));

                   // Main rounded background
                   JPanel panel = new JPanel() {
                   @Override
                   protected void paintComponent(Graphics g) {
                     Graphics2D g2 = (Graphics2D) g.create();
                     g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                     g2.setColor(new Color(20, 20, 20));
                     g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                     g2.dispose();
                    }
                };
                   panel.setLayout(new BorderLayout());
                   panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                   panel.setOpaque(false);

                   //LEFT: Poster Image
                   JLabel poster = new JLabel();
                   poster.setHorizontalAlignment(SwingConstants.CENTER);
                   poster.setVerticalAlignment(SwingConstants.CENTER);
                   poster.setOpaque(true);
                   poster.setBackground(new Color(30, 30, 30));
                   poster.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                   try {
                    if (movie.getPosterUrl() != null) {
                      java.net.URL url = new java.net.URL(movie.getPosterUrl());
                      Image img = javax.imageio.ImageIO.read(url);
                    if (img != null) {
                      img = img.getScaledInstance(250, 360, Image.SCALE_SMOOTH); 
                      poster.setIcon(new ImageIcon(img));
                    } else {
                      poster.setText("No Image");
                      poster.setForeground(Color.GRAY);
                    }
                  }
                } catch (Exception ex) {
                     poster.setText("Image Load Failed");
                     poster.setForeground(Color.RED);
                }

                JPanel leftPanel = new JPanel(new BorderLayout());
                leftPanel.setOpaque(false);
                leftPanel.add(poster, BorderLayout.CENTER);

                // RIGHT: Movie Info 
                JPanel rightPanel = new JPanel();
                rightPanel.setLayout(new BorderLayout(10, 10));
                rightPanel.setOpaque(false);

                // Title
                JLabel titleLabel = new JLabel(movie.getTitle());
                titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
                titleLabel.setForeground(Color.WHITE);

                // Overview
                JTextArea overview = new JTextArea(movie.getOverview());
                overview.setWrapStyleWord(true);
                overview.setLineWrap(true);
                overview.setEditable(false);
                overview.setOpaque(false);
                overview.setBackground(new Color(36, 36, 36));
                overview.setForeground(new Color(220, 220, 220));
                overview.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                overview.setBorder(null);

                JScrollPane scroll = new JScrollPane(overview);
                scroll.setBorder(BorderFactory.createEmptyBorder());
                scroll.setOpaque(false);
                scroll.getViewport().setOpaque(false);
                scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
                scroll.setPreferredSize(new Dimension(380, 200));

                // Release date
                JLabel release = new JLabel("Release: " + movie.getReleaseDate());
                release.setForeground(new Color(180, 180, 180));
                release.setFont(new Font("Segoe UI", Font.PLAIN, 12));

                // Buttons
                JButton closeBtn = new JButton("Close");
                closeBtn.setFocusPainted(false);
                closeBtn.setBackground(new Color(229, 9, 20)); 
                closeBtn.setForeground(Color.WHITE);
                closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
                closeBtn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
                closeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                closeBtn.addActionListener(evt -> dialog.dispose());

                JPanel buttonPanel = new JPanel();
                buttonPanel.setOpaque(false);
                buttonPanel.add(closeBtn);

                // Combine info layout
                JPanel infoPanel = new JPanel(new BorderLayout(10, 10));
                infoPanel.setOpaque(false);
                infoPanel.add(titleLabel, BorderLayout.NORTH);
                infoPanel.add(scroll, BorderLayout.CENTER);
                infoPanel.add(release, BorderLayout.SOUTH);

                rightPanel.add(infoPanel, BorderLayout.CENTER);
                rightPanel.add(buttonPanel, BorderLayout.SOUTH);

                //Add left & right sections
                panel.add(leftPanel, BorderLayout.WEST);
                panel.add(rightPanel, BorderLayout.CENTER);

               dialog.setContentPane(panel);
               //dialog.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(229, 9, 20), 2, true));
               dialog.setVisible(true);
              }
            });
                    centerPanel.add(posterLabel, BorderLayout.CENTER);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        card.add(centerPanel, BorderLayout.CENTER);

        // Watchlist button at bottom 
        JButton addBtn = new JButton("Add to Watchlist");
        //((RoundedButton) addBtn).setRadius(25);
        //addBtn.setBorder(BorderFactory.createLineBorder(Color.RED)); // border color
        addBtn.setBackground(new Color(179, 153, 0));
        addBtn.setForeground(Color.WHITE);
        addBtn.setBorderPainted(false);
        addBtn.setFocusPainted(false);  
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        addBtn.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
                
        //addBtn.setContentAreaFilled(false); // removes the 3D background fill
        //addBtn.setOpaque(true);             // ensures your background color shows up
        addBtn.setPreferredSize(new Dimension(180, 35)); // fixed width and height
        addBtn.addActionListener(ev -> {
            if (!watchlist.contains(movie)) {
                watchlist.add(movie);
                JOptionPane.showMessageDialog(null, movie.getTitle() + " added to watchlist!");
            } else {
                JOptionPane.showMessageDialog(null, movie.getTitle() + " is already in your watchlist!");
            }
        });
        
        
        // Button wrapper with FlowLayout to respect button width
        JPanel btnWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5)); // 5px vertical gap
        btnWrapper.setOpaque(false);

        // Optional: center horizontally
        addBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add button
        btnWrapper.add(addBtn);

        card.add(btnWrapper, BorderLayout.SOUTH);

        
        trendingPanel.add(card);
    }
}


     
    // scale image to label size 
    private void updateMovieImage(JLabel label, String path) {
      try {
        java.net.URL imageUrl = getClass().getResource(path);
        if (imageUrl == null) {
            System.err.println("Image not found: " + path);
            return;
        }

        ImageIcon icon = new ImageIcon(imageUrl);
        Image img = icon.getImage();

        int width = label.getWidth();
        int height = label.getHeight();

        // fallback if label is not yet laid out
        if (width <= 0) width = 1000;
        if (height <= 0) height = 500;

        // scale image to fit label
        int panelWidth = label.getParent().getWidth(); // full panel width
        int panelHeight = 500; // fixed height for slideshow

        Image scaledImg = img.getScaledInstance(panelWidth, panelHeight, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(scaledImg));


        // important: refresh layout and repaint
        label.revalidate();
        label.repaint();

      } catch (Exception e) {
        e.printStackTrace();
    }
}


    // toggle the panel
    private void togglePanel() {
        Timer timer = new Timer(5, null);
        timer.addActionListener(e -> {
            if (isExpanded) {
                panelWidth -= 5;
                if (panelWidth <= 50) { // collapsed width
                    panelWidth = 50;
                    timer.stop();
                    isExpanded = false;
                }
            } else {
                panelWidth += 5;
                if (panelWidth >= 180) { // expanded width
                    panelWidth = 180;
                    timer.stop();
                    isExpanded = true;
                }
            }
            jPanel4.setPreferredSize(new Dimension(panelWidth, jPanel4.getHeight()));
            jPanel4.revalidate();
        });
        timer.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = jButton1 = new RoundedButton("Home");
        ((RoundedButton) jButton1).setRadius(25);
        jButton1.setBackground(Color.BLACK);
        jButton1.setForeground(Color.WHITE);
        jButton1.setBorder(BorderFactory.createLineBorder(Color.RED)); // border color;
        jButton2 = jButton2 = new RoundedButton("Movies");
        ((RoundedButton) jButton2).setRadius(25);
        jButton2.setBackground(Color.BLACK);
        jButton2.setForeground(Color.WHITE);
        ;
        jButton3 = jButton3 = new RoundedButton("TV Shows");
        ((RoundedButton) jButton3).setRadius(25);
        jButton3.setBackground(Color.BLACK); 
        jButton3.setForeground(Color.WHITE);
        ;
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1280, 720));

        jPanel1.setBackground(new java.awt.Color(60, 60, 80));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(0, 80));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/mavenproject1/search.png"))); // NOI18N

        jLabel16.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("CINEMAVERSE");
        jLabel16.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jComboBox1.setBackground(new java.awt.Color(0, 0, 0));
        jComboBox1.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(255, 255, 255));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Genre ", "Action ", "Thriller ", "Romance ", "Horror", " " }));
        jComboBox1.setBorder(null);
        jComboBox1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Home");
        jButton1.setBorder(null);
        jButton1.setPreferredSize(new java.awt.Dimension(94, 21));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 0, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Movies");
        jButton2.setBorder(null);
        jButton2.setPreferredSize(new java.awt.Dimension(94, 21));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 0, 0));
        jButton3.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("TV Shows");
        jButton3.setBorder(null);
        jButton3.setPreferredSize(new java.awt.Dimension(94, 21));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(178, 178, 178)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(jLabel2)))
                .addContainerGap(207, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox1)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(13, 11, 11));
        jPanel4.setPreferredSize(new java.awt.Dimension(180, 0));
        jPanel4.setRequestFocusEnabled(false);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/mavenproject1/menu.png"))); // NOI18N

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/mavenproject1/home.png"))); // NOI18N

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/mavenproject1/about.png"))); // NOI18N

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/mavenproject1/account.png"))); // NOI18N

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/mavenproject1/settings.png"))); // NOI18N

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/mavenproject1/favorite.png"))); // NOI18N

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/mavenproject1/logout.png"))); // NOI18N

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Home");

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Favourite");

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Profile");

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("About");

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Settings");

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText(" Logout ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(36, 36, 36)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                .addGap(12, 12, 12))
        );

        getContentPane().add(jPanel4, java.awt.BorderLayout.LINE_START);

        jPanel5.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 615, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 383, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel5, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void showMovies(List<Movie> movies) {
       // Create a new JFrame to display search results
       JFrame resultsFrame = new JFrame("Search Results");
       resultsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       resultsFrame.setSize(800, 600);
       resultsFrame.setLocationRelativeTo(null);

       //new panel to hold movie cards
       JPanel resultsPanel = new JPanel();
       resultsPanel.setLayout(new GridLayout(0, 4, 15, 15)); // 4 columns, scrolls vertically
       resultsPanel.setBackground(Color.BLACK);

       
       // Scroll Pane with customized scrollbar
       JScrollPane scrollPane = new JScrollPane(resultsPanel);
       scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
       scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
       scrollPane.getVerticalScrollBar().setUnitIncrement(16);

       // Customize the vertical scrollbar
       JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
       verticalBar.setPreferredSize(new Dimension(12, 0)); // scrollbar width

       verticalBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
       @Override
       protected void configureScrollBarColors() {
        this.thumbColor = Color.WHITE;       // scrollbar thumb color
        this.trackColor = Color.BLACK;     // scrollbar track color
       }

       @Override
       protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(thumbColor);
        g2.fillRoundRect(r.x, r.y, r.width, r.height, 10, 10); // rounded thumb
        g2.dispose();
     }

      @Override
      protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(trackColor);
        g2.fillRect(r.x, r.y, r.width, r.height);
        g2.dispose();
      }

      @Override
      protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
     }

      @Override
      protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
     }

     private JButton createZeroButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        return button;
     }
    });
    
    scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    resultsFrame.add(scrollPane);

       // Build each movie card
      for (Movie movie : movies) {
        JPanel card = new JPanel(new BorderLayout()) {
        @Override
        protected void paintComponent(Graphics g) {
         super.paintComponent(g);
         Graphics2D g2 = (Graphics2D) g.create();
         g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         g2.setColor(getBackground());
         g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // 20 = corner radius
         g2.dispose();
        }
     };
        card.setOpaque(false); // to make the rounded edges visible
        card.setLayout(new BorderLayout(0, 10)); // add vertical spacing between components
        card.setBackground(new Color(13,11,11));
        card.setPreferredSize(new Dimension(170, 300));
        card.setBorder(new RoundedBorder(20)); // Apply custom border
        

        // Exception handling - load the image safely
        try {
            if (movie.getPosterUrl() != null) {
                java.net.URL url = new java.net.URL(movie.getPosterUrl());
                Image img = javax.imageio.ImageIO.read(url);
                if (img != null) {
                    img = img.getScaledInstance(150, 220, Image.SCALE_SMOOTH);
                    JLabel posterLabel = new JLabel(new ImageIcon(img));
                    posterLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                    // Click event - show movie details
                    posterLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {

                    // === Create custom dialog ===
                    JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(posterLabel), "Movie Info", true);
                    dialog.setUndecorated(true);
                    dialog.setSize(700, 400);
                    dialog.setLocationRelativeTo(posterLabel);

                    // Make the dialog background transparent
                    dialog.setBackground(new Color(0, 0, 0, 0));

                   // === Main rounded background ===
                   JPanel panel = new JPanel() {
                   @Override
                   protected void paintComponent(Graphics g) {
                     Graphics2D g2 = (Graphics2D) g.create();
                     g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                     g2.setColor(new Color(20, 20, 20));
                     g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                     g2.dispose();
                    }
                };
                   panel.setLayout(new BorderLayout());
                   panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                   panel.setOpaque(false);

                   // === LEFT: Poster Image ===
                   JLabel poster = new JLabel();
                   poster.setHorizontalAlignment(SwingConstants.CENTER);
                   poster.setVerticalAlignment(SwingConstants.CENTER);
                   poster.setOpaque(true);
                   poster.setBackground(new Color(30, 30, 30));
                   poster.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                   try {
                    if (movie.getPosterUrl() != null) {
                      java.net.URL url = new java.net.URL(movie.getPosterUrl());
                      Image img = javax.imageio.ImageIO.read(url);
                    if (img != null) {
                      img = img.getScaledInstance(250, 360, Image.SCALE_SMOOTH); 
                      poster.setIcon(new ImageIcon(img));
                    } else {
                      poster.setText("No Image");
                      poster.setForeground(Color.GRAY);
                    }
                  }
                } catch (Exception ex) {
                     poster.setText("Image Load Failed");
                     poster.setForeground(Color.RED);
                }

                JPanel leftPanel = new JPanel(new BorderLayout());
                leftPanel.setOpaque(false);
                leftPanel.add(poster, BorderLayout.CENTER);

                // === RIGHT: Movie Info ===
                JPanel rightPanel = new JPanel();
                rightPanel.setLayout(new BorderLayout(10, 10));
                rightPanel.setOpaque(false);

                // Title
                JLabel titleLabel = new JLabel(movie.getTitle());
                titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
                titleLabel.setForeground(Color.WHITE);

                // Overview
                JTextArea overview = new JTextArea(movie.getOverview());
                overview.setWrapStyleWord(true);
                overview.setLineWrap(true);
                overview.setEditable(false);
                overview.setOpaque(false);
                overview.setBackground(new Color(36, 36, 36));
                overview.setForeground(new Color(220, 220, 220));
                overview.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                overview.setBorder(null);

                JScrollPane scroll = new JScrollPane(overview);
                scroll.setBorder(BorderFactory.createEmptyBorder());
                scroll.setOpaque(false);
                scroll.getViewport().setOpaque(false);
                scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
                scroll.setPreferredSize(new Dimension(380, 200));

                // Release date
                JLabel release = new JLabel("Release: " + movie.getReleaseDate());
                release.setForeground(new Color(180, 180, 180));
                release.setFont(new Font("Segoe UI", Font.PLAIN, 12));

                // === Buttons (red Close) ===
                JButton closeBtn = new JButton("Close");
                closeBtn.setFocusPainted(false);
                closeBtn.setBackground(new Color(229, 9, 20)); //red
                closeBtn.setForeground(Color.WHITE);
                closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
                closeBtn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
                closeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                closeBtn.addActionListener(evt -> dialog.dispose());

                JPanel buttonPanel = new JPanel();
                buttonPanel.setOpaque(false);
                buttonPanel.add(closeBtn);

                // Combine info layout
                JPanel infoPanel = new JPanel(new BorderLayout(10, 10));
                infoPanel.setOpaque(false);
                infoPanel.add(titleLabel, BorderLayout.NORTH);
                infoPanel.add(scroll, BorderLayout.CENTER);
                infoPanel.add(release, BorderLayout.SOUTH);

                rightPanel.add(infoPanel, BorderLayout.CENTER);
                rightPanel.add(buttonPanel, BorderLayout.SOUTH);

                // === Add left & right sections ===
                panel.add(leftPanel, BorderLayout.WEST);
                panel.add(rightPanel, BorderLayout.CENTER);

               dialog.setContentPane(panel);
               //dialog.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(229, 9, 20), 2, true));
               dialog.setVisible(true);
              }
            });
                    card.add(posterLabel, BorderLayout.CENTER);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // movie title
        JLabel titleLabel = new JLabel("<html><center>" + movie.getTitle() + "</center></html>");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        card.add(titleLabel, BorderLayout.NORTH);
        
        // watchlist button
        JButton addBtn = new JButton("Add to Watchlist");
        addBtn.setBackground(new Color(179, 153, 0));
        addBtn.setForeground(Color.WHITE);
        addBtn.setBorderPainted(false);
        addBtn.setFocusPainted(false);
        addBtn.setPreferredSize(new Dimension(0, 35)); // uniform height
        addBtn.addActionListener(ev -> {
        if (!watchlist.contains(movie)) {
           watchlist.add(movie);
           JOptionPane.showMessageDialog(resultsFrame,
           movie.getTitle() + " added to watchlist!");
        } else {
           JOptionPane.showMessageDialog(resultsFrame,
             movie.getTitle() + " is already in your watchlist!");
        }
        });

        card.add(addBtn, BorderLayout.SOUTH);

        resultsPanel.add(card);
      }

    // Show the new frame
    resultsFrame.setVisible(true);
    }
    
    private final java.util.List<Movie> watchlist = new java.util.ArrayList<>();

    private void watchlistMovies() {
    if (watchlist.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Your watchlist is empty!");
        return;
    }

    JFrame watchlistFrame = new JFrame("Your Watchlist");
    watchlistFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    watchlistFrame.setSize(800, 600);
    watchlistFrame.setLocationRelativeTo(null);

    JPanel resultsPanel = new JPanel();
    resultsPanel.setLayout(new GridLayout(0, 4, 15, 15));
    resultsPanel.setBackground(Color.BLACK);
    
    // Scroll Pane with customized scrollbar
    JScrollPane scrollPane = new JScrollPane(resultsPanel);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.getVerticalScrollBar().setUnitIncrement(16);

    // Customize the vertical scrollbar
    JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
    verticalBar.setPreferredSize(new Dimension(12, 0)); // scrollbar width

    verticalBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
      @Override
      protected void configureScrollBarColors() {
        this.thumbColor = Color.WHITE;       // scrollbar thumb color
        this.trackColor = Color.BLACK;     // scrollbar track color
      }

      @Override
      protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(thumbColor);
        g2.fillRoundRect(r.x, r.y, r.width, r.height, 10, 10); // rounded thumb
        g2.dispose();
     }

      @Override
      protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(trackColor);
        g2.fillRect(r.x, r.y, r.width, r.height);
        g2.dispose();
      }

      @Override
      protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
     }

      @Override
      protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
     }

     private JButton createZeroButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        return button;
     }
    });
    
    scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    watchlistFrame.add(scrollPane);


    for (Movie movie : watchlist) {
        JPanel card = new JPanel(new BorderLayout()) {
        @Override
        protected void paintComponent(Graphics g) {
          super.paintComponent(g);
          Graphics2D g2 = (Graphics2D) g.create();
          g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
          g2.setColor(getBackground());
          g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // 20 = corner radius
          g2.dispose();
        }
      };
        
       card.setLayout(new BorderLayout(0, 10)); // 10px vertical gap between center and south
       card.setOpaque(false); // to make the rounded edges visible
       card.setBackground(new Color(13,11,11));
       card.setPreferredSize(new Dimension(170, 300));
       card.setBorder(new RoundedBorder(20)); // Apply custom border

        try {
            if (movie.getPosterUrl() != null) {
                java.net.URL url = new java.net.URL(movie.getPosterUrl());
                Image img = javax.imageio.ImageIO.read(url);
                if (img != null) {
                    img = img.getScaledInstance(150, 220, Image.SCALE_SMOOTH);
                    JLabel posterLabel = new JLabel(new ImageIcon(img));
                    posterLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                    posterLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {

                    // === Create custom dialog ===
                    JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(posterLabel), "Movie Info", true);
                    dialog.setUndecorated(true);
                    dialog.setSize(700, 400);
                    dialog.setLocationRelativeTo(posterLabel);

                    // Make the dialog background transparent
                    dialog.setBackground(new Color(0, 0, 0, 0));

                   // === Main rounded background ===
                   JPanel panel = new JPanel() {
                   @Override
                   protected void paintComponent(Graphics g) {
                     Graphics2D g2 = (Graphics2D) g.create();
                     g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                     g2.setColor(new Color(20, 20, 20));
                     g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                     g2.dispose();
                    }
                };
                   panel.setLayout(new BorderLayout());
                   panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                   panel.setOpaque(false);

                   // === LEFT: Poster Image ===
                   JLabel poster = new JLabel();
                   poster.setHorizontalAlignment(SwingConstants.CENTER);
                   poster.setVerticalAlignment(SwingConstants.CENTER);
                   poster.setOpaque(true);
                   poster.setBackground(new Color(30, 30, 30));
                   poster.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                   try {
                    if (movie.getPosterUrl() != null) {
                      java.net.URL url = new java.net.URL(movie.getPosterUrl());
                      Image img = javax.imageio.ImageIO.read(url);
                    if (img != null) {
                      img = img.getScaledInstance(250, 360, Image.SCALE_SMOOTH); 
                      poster.setIcon(new ImageIcon(img));
                    } else {
                      poster.setText("No Image");
                      poster.setForeground(Color.GRAY);
                    }
                  }
                } catch (Exception ex) {
                     poster.setText("Image Load Failed");
                     poster.setForeground(Color.RED);
                }

                JPanel leftPanel = new JPanel(new BorderLayout());
                leftPanel.setOpaque(false);
                leftPanel.add(poster, BorderLayout.CENTER);

                // === RIGHT: Movie Info ===
                JPanel rightPanel = new JPanel();
                rightPanel.setLayout(new BorderLayout(10, 10));
                rightPanel.setOpaque(false);

                // Title
                JLabel titleLabel = new JLabel(movie.getTitle());
                titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
                titleLabel.setForeground(Color.WHITE);

                // Overview
                JTextArea overview = new JTextArea(movie.getOverview());
                overview.setWrapStyleWord(true);
                overview.setLineWrap(true);
                overview.setEditable(false);
                overview.setOpaque(false);
                overview.setBackground(new Color(36, 36, 36));
                overview.setForeground(new Color(220, 220, 220));
                overview.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                overview.setBorder(null);

                JScrollPane scroll = new JScrollPane(overview);
                scroll.setBorder(BorderFactory.createEmptyBorder());
                scroll.setOpaque(false);
                scroll.getViewport().setOpaque(false);
                scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
                scroll.setPreferredSize(new Dimension(380, 200));

                // Release date
                JLabel release = new JLabel("Release: " + movie.getReleaseDate());
                release.setForeground(new Color(180, 180, 180));
                release.setFont(new Font("Segoe UI", Font.PLAIN, 12));

                // === Buttons ===
                JButton closeBtn = new JButton("Close");
                closeBtn.setFocusPainted(false);
                closeBtn.setBackground(new Color(229, 9, 20)); 
                closeBtn.setForeground(Color.WHITE);
                closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
                closeBtn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
                closeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                closeBtn.addActionListener(evt -> dialog.dispose());

                JPanel buttonPanel = new JPanel();
                buttonPanel.setOpaque(false);
                buttonPanel.add(closeBtn);

                // Combine info layout
                JPanel infoPanel = new JPanel(new BorderLayout(10, 10));
                infoPanel.setOpaque(false);
                infoPanel.add(titleLabel, BorderLayout.NORTH);
                infoPanel.add(scroll, BorderLayout.CENTER);
                infoPanel.add(release, BorderLayout.SOUTH);

                rightPanel.add(infoPanel, BorderLayout.CENTER);
                rightPanel.add(buttonPanel, BorderLayout.SOUTH);

                // === Add left & right sections ===
                panel.add(leftPanel, BorderLayout.WEST);
                panel.add(rightPanel, BorderLayout.CENTER);

               dialog.setContentPane(panel);
               //dialog.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(229, 9, 20), 2, true));
               dialog.setVisible(true);
              }
            });
                    card.add(posterLabel, BorderLayout.CENTER);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel titleLabel = new JLabel("<html><center>" + movie.getTitle() + "</center></html>");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        card.add(titleLabel, BorderLayout.NORTH);

        JButton removeBtn = new JButton("Remove");
        removeBtn.setBackground(new Color(229, 9, 20));
        removeBtn.setForeground(Color.WHITE);
        removeBtn.setBorderPainted(false);
        removeBtn.setFocusPainted(false);
        removeBtn.setPreferredSize(new Dimension(0, 35)); // uniform height
        removeBtn.addActionListener(ev -> {
            watchlist.remove(movie);
            watchlistFrame.dispose();
            watchlistMovies(); // refresh the list
        });
        card.add(removeBtn, BorderLayout.SOUTH);

        resultsPanel.add(card);
    }

    watchlistFrame.setVisible(true);
}

    
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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new moviegui().setVisible(true));
    }

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    // End of variables declaration//GEN-END:variables
}
