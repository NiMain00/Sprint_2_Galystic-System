package gui;

import dao.LaporanDAO;
import dao.TransaksiDAO;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.ArrayList;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableRowSorter;
import model.Pengguna;
import model.Transaksi;

public class DashboardFrame extends JFrame {

    static final Color PRIMARY = new Color(126, 4, 3);
    static final Color PRIMARY_DARK = new Color(90, 3, 2);
    static final Color PRIMARY_LIGHT = new Color(160, 30, 30);
    static final Color SIDEBAR_BG = new Color(45, 45, 55);
    static final Color SIDEBAR_HOVER = new Color(65, 65, 80);
    static final Color SIDEBAR_ACTIVE = PRIMARY;
    static final Color BG_COLOR = new Color(240, 242, 247);
    static final Color CARD_BG = Color.WHITE;
    static final Color TEXT_PRIMARY = new Color(50, 50, 50);
    static final Color TEXT_SECONDARY = new Color(130, 130, 130);
    static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 20);
    static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 13);
    static final Font FONT_MENU = new Font("Segoe UI", Font.PLAIN, 14);
    static final Font FONT_MENU_BOLD = new Font("Segoe UI", Font.BOLD, 14);
static final DecimalFormat DF = new DecimalFormat("#,##0");

    static final java.time.format.DateTimeFormatter DATE_DB_FORMAT = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static final java.time.format.DateTimeFormatter DATE_DISPLAY_FORMAT = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private Pengguna currentUser;
    private JPanel contentPanel;
    private JLabel lblPageTitle;
    private JPanel sidebarPanel;
    private JButton activeButton = null;

    public DashboardFrame(Pengguna pengguna) {
        this.currentUser = pengguna;

        setTitle("Bengkel Lathifah - Sistem Informasi Manajemen");
        setSize(1280, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1100, 650));

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Sidebar
        sidebarPanel = createSidebar();

        // Top bar
        JPanel topBar = createTopBar();

        // Content
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(BG_COLOR);

        JPanel rightSection = new JPanel(new BorderLayout());
        rightSection.add(topBar, BorderLayout.NORTH);
        rightSection.add(contentPanel, BorderLayout.CENTER);

        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(rightSection, BorderLayout.CENTER);

        setContentPane(mainPanel);

        // Show dashboard by default
        showDashboardContent();
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setPreferredSize(new Dimension(180, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        // Logo area
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(PRIMARY_DARK);
        logoPanel.setMaximumSize(new Dimension(240, 80));
        logoPanel.setMinimumSize(new Dimension(240, 80));
        logoPanel.setPreferredSize(new Dimension(240, 80));
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblLogo = new JLabel("BENGKEL LATHIFAH");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblVer = new JLabel("GALYSTIC SYSTEM v1.0");
        lblVer.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblVer.setForeground(new Color(255, 255, 255, 150));
        lblVer.setAlignmentX(Component.LEFT_ALIGNMENT);

        logoPanel.add(lblLogo);
        logoPanel.add(Box.createVerticalStrut(3));
        logoPanel.add(lblVer);

        sidebar.add(logoPanel);
        sidebar.add(Box.createVerticalStrut(15));

        // Menu label
        JLabel lblMenu = new JLabel("   MENU UTAMA");
        lblMenu.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblMenu.setForeground(new Color(150, 150, 160));
        lblMenu.setMaximumSize(new Dimension(240, 25));
        lblMenu.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(lblMenu);
        sidebar.add(Box.createVerticalStrut(5));

        // Menu items
        JButton btnDashboard = createMenuButton("📊  Dashboard", e -> showDashboardContent());
        sidebar.add(btnDashboard);

        JButton btnTransaksi = createMenuButton("💳  Transaksi", e -> showPanel("Transaksi"));
        sidebar.add(btnTransaksi);

        JButton btnPelanggan = createMenuButton("👥  Pelanggan", e -> showPanel("Pelanggan"));
        sidebar.add(btnPelanggan);

        // Separator
        sidebar.add(Box.createVerticalStrut(10));
        JLabel lblData = new JLabel("   DATA MASTER");
        lblData.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblData.setForeground(new Color(150, 150, 160));
        lblData.setMaximumSize(new Dimension(240, 25));
        sidebar.add(lblData);
        sidebar.add(Box.createVerticalStrut(5));

        JButton btnBarang = createMenuButton("📦  Barang", e -> showPanel("Barang"));
        sidebar.add(btnBarang);

        JButton btnJasa = createMenuButton("🛠  Jasa", e -> showPanel("Jasa"));
        sidebar.add(btnJasa);

        JButton btnKategori = createMenuButton("📁  Kategori Barang", e -> showPanel("Kategori"));
        sidebar.add(btnKategori);

        // Laporan & Statistik - hanya Admin
        if (currentUser.getRole().equalsIgnoreCase("Admin")) {
            sidebar.add(Box.createVerticalStrut(10));
            JLabel lblReport = new JLabel("   LAPORAN & STATISTIK");
            lblReport.setFont(new Font("Segoe UI", Font.BOLD, 10));
            lblReport.setForeground(new Color(150, 150, 160));
            lblReport.setMaximumSize(new Dimension(240, 25));
            sidebar.add(lblReport);
            sidebar.add(Box.createVerticalStrut(5));
            
            JButton btnLaporan = createMenuButton("📋  Laporan", e -> showPanel("Laporan"));
            sidebar.add(btnLaporan);

            JButton btnStatistik = createMenuButton("📈  Statistik", e -> showPanel("Statistik"));
            sidebar.add(btnStatistik);
        }

        // Admin only
        if (currentUser.getRole().equals("Admin")) {
            sidebar.add(Box.createVerticalStrut(10));
            JLabel lblAdmin = new JLabel("   ADMINISTRASI");
            lblAdmin.setFont(new Font("Segoe UI", Font.BOLD, 10));
            lblAdmin.setForeground(new Color(150, 150, 160));
            lblAdmin.setMaximumSize(new Dimension(240, 25));
            sidebar.add(lblAdmin);
            sidebar.add(Box.createVerticalStrut(5));

            JButton btnPengguna = createMenuButton("👤  Pengguna", e -> showPanel("Pengguna"));
            sidebar.add(btnPengguna);
        }

        sidebar.add(Box.createVerticalGlue());

        // Logout button
        JButton btnLogout = createMenuButton("🚪  Logout", e -> {
            int opt = JOptionPane.showConfirmDialog(this, "Yakin ingin logout?",
                    "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (opt == JOptionPane.YES_OPTION) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });
        btnLogout.setBackground(new Color(180, 30, 30));
        sidebar.add(btnLogout);
        sidebar.add(Box.createVerticalStrut(10));

        // Set dashboard as active
        setActiveButton(btnDashboard);

        return sidebar;
    }

    private JButton createMenuButton(String text, ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));

        btn.setForeground(new Color(200, 200, 210));
        btn.setBackground(SIDEBAR_BG);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(240, 42));
        btn.setMinimumSize(new Dimension(240, 42));
        btn.setPreferredSize(new Dimension(240, 42));
        btn.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (btn != activeButton) btn.setBackground(SIDEBAR_HOVER);
            }
            public void mouseExited(MouseEvent e) {
                if (btn != activeButton) btn.setBackground(SIDEBAR_BG);
            }
        });

        btn.addActionListener(e -> {
            setActiveButton(btn);
            action.actionPerformed(e);
        });

        return btn;
    }

    private void setActiveButton(JButton btn) {
        if (activeButton != null) {
            activeButton.setBackground(SIDEBAR_BG);
            activeButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        }
        activeButton = btn;
        activeButton.setBackground(SIDEBAR_ACTIVE);
        activeButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(CARD_BG);
        topBar.setPreferredSize(new Dimension(0, 60));
        topBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));

        lblPageTitle = new JLabel("Dashboard");
        lblPageTitle.setFont(FONT_TITLE);
        lblPageTitle.setForeground(TEXT_PRIMARY);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        userPanel.setOpaque(false);

        JLabel lblUser = new JLabel(currentUser.getNamaLengkap());
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUser.setForeground(TEXT_PRIMARY);

        JLabel lblRole = new JLabel("[" + currentUser.getRole() + "]");
        lblRole.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblRole.setForeground(PRIMARY);

        userPanel.add(lblUser);
        userPanel.add(lblRole);

        topBar.add(lblPageTitle, BorderLayout.WEST);
        topBar.add(userPanel, BorderLayout.EAST);

        return topBar;
    }

    private void showDashboardContent() {
        lblPageTitle.setText("Dashboard");
        contentPanel.removeAll();

        JPanel dashboard = new JPanel(new BorderLayout(0, 20));
        dashboard.setBackground(BG_COLOR);
        dashboard.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Stats cards
        JPanel cardsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        cardsPanel.setOpaque(false);

        LaporanDAO laporanDAO = new LaporanDAO();

        cardsPanel.add(createStatCard("Transaksi Hari Ini",
                String.valueOf(laporanDAO.getTotalTransaksiHariIni()), "📋", new Color(52, 152, 219)));
        cardsPanel.add(createStatCard("Pendapatan Hari Ini",
                "Rp " + DF.format(laporanDAO.getPendapatanHariIni()), "💰", new Color(46, 204, 113)));
        cardsPanel.add(createStatCard("Pendapatan Bulan Ini",
                "Rp " + DF.format(laporanDAO.getPendapatanBulanIni()), "📈", PRIMARY));
        cardsPanel.add(createStatCard("Total Pelanggan",
                String.valueOf(laporanDAO.getTotalPelanggan()), "👥", new Color(155, 89, 182)));

        // Welcome panel
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBackground(CARD_BG);
        welcomePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        JLabel lblWelcome = new JLabel("<html>" +
                "<div style='font-size:16pt; font-weight:bold; color:#7e0403;'>Selamat Datang, " + currentUser.getNamaLengkap() + "!</div>" +
                "<br><div style='font-size:10pt; color:#666;'>Sistem Informasi Manajemen Bengkel Lathifah</div>" +
                "<br><div style='font-size:9pt; color:#aaa;'>Enhanced with search/filter, CSV export, recent transaksi</div>" +
                "</html>");
        welcomePanel.add(lblWelcome);

        // Recent transaksi mini-table (simple without styleTable dependency)
        JPanel recentPanel = new JPanel(new BorderLayout());
        recentPanel.setBackground(CARD_BG);
        recentPanel.setPreferredSize(new Dimension(0, 200));
        recentPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(0, 15, 15, 15)
        ));

        JLabel lblRecent = new JLabel("Recent Transaksi Terakhir", JLabel.CENTER);
        lblRecent.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblRecent.setForeground(PRIMARY);
        recentPanel.add(lblRecent, BorderLayout.NORTH);

        TransaksiDAO trxDAO = new TransaksiDAO();
DefaultTableModel recentModel = new DefaultTableModel(new String[]{"No Transaksi", "Tanggal", "Total"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tblRecent = new JTable(recentModel);
        tblRecent.getTableHeader().setReorderingAllowed(false);
        tblRecent.getColumnModel().getColumn(0).setPreferredWidth(150);
        tblRecent.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblRecent.getColumnModel().getColumn(2).setPreferredWidth(100);
tblRecent.setRowSorter(new TableRowSorter<>(recentModel));
        // Auto-sort by date (index 1) descending
        java.util.List<javax.swing.RowSorter.SortKey> sortKeys = new java.util.ArrayList<>();
        sortKeys.add(new javax.swing.RowSorter.SortKey(1, javax.swing.SortOrder.DESCENDING));
        tblRecent.getRowSorter().setSortKeys(sortKeys);
        tblRecent.setRowHeight(25);
        List<Transaksi> recentTrx = trxDAO.getAll();
        for (int i = 0; i < Math.min(5, recentTrx.size()); i++) {
            Transaksi t = recentTrx.get(i);
            String formattedDate = java.time.LocalDate.parse(t.getTanggal(), DATE_DB_FORMAT).format(DATE_DISPLAY_FORMAT);
            recentModel.addRow(new Object[]{t.getNoTransaksi(), formattedDate, "Rp " + DF.format(t.getTotalHarga())});
        }
        JScrollPane spRecent = new JScrollPane(tblRecent);
        recentPanel.add(spRecent, BorderLayout.CENTER);

        dashboard.add(cardsPanel, BorderLayout.NORTH);
        dashboard.add(welcomePanel, BorderLayout.CENTER);
        dashboard.add(recentPanel, BorderLayout.SOUTH);

        contentPanel.add(dashboard, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel createStatCard(String title, String value, String icon, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));

        JPanel topP = new JPanel(new BorderLayout());
        topP.setOpaque(false);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblTitle.setForeground(TEXT_SECONDARY);

        JLabel lblIcon = new JLabel(icon);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));

        topP.add(lblTitle, BorderLayout.CENTER);
        topP.add(lblIcon, BorderLayout.EAST);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblValue.setForeground(color);

        card.add(topP, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);

        return card;
    }

    public void showPanel(String panelName) {
        lblPageTitle.setText(panelName);
        contentPanel.removeAll();

        JPanel panel;
        switch (panelName) {
            case "Pengguna": panel = new PenggunaPanel(currentUser); break;
            case "Kategori": panel = new KategoriBarangPanel(currentUser); break;
            case "Barang": panel = new BarangPanel(currentUser); break;
            case "Jasa": panel = new JasaPanel(currentUser); break;
            case "Pelanggan": panel = new PelangganPanel(currentUser); break;
            case "Transaksi": panel = new TransaksiPanel(currentUser); break;
            case "Laporan":
                if (!currentUser.getRole().equalsIgnoreCase("Admin")) {
                    JOptionPane.showMessageDialog(this, "Akses ditolak! Hanya Admin yang dapat membuka menu Laporan.");
                    return;
                }
                panel = new LaporanPanel();
                break;
            case "Statistik":
                if (!currentUser.getRole().equalsIgnoreCase("Admin")) {
                    JOptionPane.showMessageDialog(this, "Akses ditolak! Hanya Admin yang dapat membuka menu Statistik.");
                    return;
                }
                panel = new StatistikPanel();
                break;
            default: panel = new JPanel(); break;
        }

        contentPanel.add(panel, BorderLayout.CENTER);
        
        // Update sidebar active button
        Component[] sidebarComps = sidebarPanel.getComponents();
        for (Component c : sidebarComps) {
          if (c instanceof JButton) {
            JButton btn = (JButton) c;
            if (btn.getText().contains(panelName)) {
              setActiveButton(btn);
              break;
            }
          }
        }
        
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}

