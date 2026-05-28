import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SnackStack {

    static String studentName = "Student";
    static int tokenCounter = 101;

    static ArrayList<String> cartItems = new ArrayList<>();
    static ArrayList<Integer> cartPrices = new ArrayList<>();

    static String latestToken = "-";
    static String orderStatus = "No order yet";
    static int totalOrders = 0;
    static int revenue = 0;

    static final Color BG = new Color(15, 15, 25);
    static final Color SURFACE = new Color(24, 24, 38);
    static final Color SURFACE2 = new Color(34, 34, 52);
    static final Color BORDER = new Color(55, 55, 80);
    static final Color PURPLE = new Color(130, 80, 255);
    static final Color PINK = new Color(255, 60, 140);
    static final Color CYAN = new Color(0, 210, 245);
    static final Color GREEN = new Color(50, 210, 120);
    static final Color AMBER = new Color(255, 185, 40);
    static final Color WHITE = new Color(240, 240, 250);
    static final Color MUTED = new Color(160, 160, 185);
    static final Color DARK_TEXT = new Color(100, 100, 130);

    static final Font FONT_HERO = new Font("Segoe UI", Font.BOLD, 52);
    static final Font FONT_H1 = new Font("Segoe UI", Font.BOLD, 34);
    static final Font FONT_H2 = new Font("Segoe UI", Font.BOLD, 22);
    static final Font FONT_H3 = new Font("Segoe UI", Font.BOLD, 17);
    static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 15);
    static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 13);
    static final Font FONT_MONO = new Font("Consolas", Font.BOLD, 18);

    static JFrame frame;
    static CardLayout cards;
    static JPanel root;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Default look and feel loaded.");
        }

        frame = new JFrame("SnackStack - Smart Canteen Ordering System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1000, 660));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        cards = new CardLayout();
        root = new JPanel(cards);

        buildAll();

        frame.add(root);
        frame.setVisible(true);
    }

    static void buildAll() {
        root.removeAll();
        root.add(buildWelcome(), "welcome");
        root.add(buildLogin(), "login");
        root.add(buildHome(), "home");
        root.add(buildMenu(), "menu");
        root.add(buildCart(), "cart");
        root.add(buildTrack(), "track");
        root.add(buildAdmin(), "admin");
        root.revalidate();
        root.repaint();
    }

    static void showPage(String page) {
        buildAll();
        cards.show(root, page);
    }

    static JPanel buildWelcome() {
        JPanel page = bg();
        page.setLayout(new GridBagLayout());

        JPanel card = surface(new Dimension(820, 430));
        card.setLayout(new BorderLayout());

        JPanel accent = new JPanel();
        accent.setPreferredSize(new Dimension(820, 5));
        accent.setBackground(PURPLE);
        card.add(accent, BorderLayout.NORTH);

        JPanel body = new JPanel(new GridLayout(4, 1, 0, 20));
        body.setOpaque(false);
        body.setBorder(pad(50, 55, 50, 55));

        JLabel logo = label("SnackStack", FONT_HERO, WHITE);
        logo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel tag = label("Order Fast. Eat Faster.", FONT_H2, CYAN);
        tag.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel sub = label("Smart college canteen ordering system", FONT_BODY, MUTED);
        sub.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttons.setOpaque(false);

        JButton studentBtn = btn("Student Login", PURPLE);
        JButton adminBtn = btnOutline("Canteen Admin");

        buttons.add(studentBtn);
        buttons.add(adminBtn);

        body.add(logo);
        body.add(tag);
        body.add(sub);
        body.add(buttons);

        card.add(body, BorderLayout.CENTER);
        page.add(card);

        studentBtn.addActionListener(e -> showPage("login"));
        adminBtn.addActionListener(e -> showPage("admin"));

        return page;
    }

    static JPanel buildLogin() {
        JPanel page = bg();
        page.setLayout(new GridBagLayout());

        JPanel card = surface(new Dimension(500, 400));
        card.setLayout(new GridLayout(5, 1, 0, 18));
        card.setBorder(pad(40, 45, 40, 45));

        JLabel title = label("Student Login", FONT_H1, WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField nameField = field("Your Name");
        JTextField usnField = field("USN");

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        buttons.setOpaque(false);

        JButton enter = btn("Enter App", PURPLE);
        JButton back = btnOutline("Back");

        buttons.add(enter);
        buttons.add(back);

        JLabel hint = label("Enter any name to continue", FONT_SMALL, DARK_TEXT);
        hint.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(title);
        card.add(nameField);
        card.add(usnField);
        card.add(buttons);
        card.add(hint);

        page.add(card);

        enter.addActionListener(e -> {
            String name = nameField.getText().trim();

            if (!name.equals("") && !name.equals("Your Name")) {
                studentName = name;
            }

            showPage("home");
        });

        back.addActionListener(e -> showPage("welcome"));

        return page;
    }

    static JPanel buildHome() {
        JPanel page = bg();
        page.setLayout(new BorderLayout());
        page.setBorder(pad(30, 40, 30, 40));

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 28, 0));

        JPanel titleGroup = new JPanel(new GridLayout(2, 1, 0, 4));
        titleGroup.setOpaque(false);

        titleGroup.add(label("Hey, " + studentName, FONT_H1, WHITE));
        titleGroup.add(label("What are we ordering today?", FONT_BODY, MUTED));

        JButton logout = btnOutline("Logout");
        logout.setPreferredSize(new Dimension(110, 40));

        topBar.add(titleGroup, BorderLayout.WEST);
        topBar.add(logout, BorderLayout.EAST);

        JPanel middle = new JPanel(new GridLayout(1, 2, 24, 0));
        middle.setOpaque(false);

        JPanel statusGrid = new JPanel(new GridLayout(2, 2, 16, 16));
        statusGrid.setOpaque(false);

        statusGrid.add(statCard("Canteen Rush", rushLabel(), rushColor()));
        statusGrid.add(statCard("Wait Time", getETA() + " mins", CYAN));
        statusGrid.add(statCard("Your Token", latestToken, PINK));
        statusGrid.add(statCard("Order Status", orderStatus, orderStatusColor()));

        JPanel special = surfaceColored(SURFACE2);
        special.setLayout(new GridLayout(5, 1, 0, 10));
        special.setBorder(pad(28, 28, 28, 28));

        special.add(label("TODAY'S SPECIAL", FONT_SMALL, CYAN));
        special.add(label("Burger + Cold Coffee", FONT_H2, WHITE));
        special.add(label("Combo Deal", FONT_BODY, MUTED));
        special.add(label("Rs. 120", FONT_H1, PINK));
        special.add(label("Perfect for break time. Limited quantity.", FONT_SMALL, MUTED));

        middle.add(statusGrid);
        middle.add(special);

        JPanel nav = new JPanel(new GridLayout(1, 3, 20, 0));
        nav.setOpaque(false);
        nav.setBorder(BorderFactory.createEmptyBorder(24, 0, 0, 0));

        JButton menuBtn = navBtn("View Menu", PURPLE);
        JButton cartBtn = navBtn("My Cart (" + cartItems.size() + ")", PINK);
        JButton trackBtn = navBtn("Track Order", CYAN);

        nav.add(menuBtn);
        nav.add(cartBtn);
        nav.add(trackBtn);

        page.add(topBar, BorderLayout.NORTH);
        page.add(middle, BorderLayout.CENTER);
        page.add(nav, BorderLayout.SOUTH);

        logout.addActionListener(e -> showPage("welcome"));
        menuBtn.addActionListener(e -> showPage("menu"));
        cartBtn.addActionListener(e -> showPage("cart"));
        trackBtn.addActionListener(e -> showPage("track"));

        return page;
    }

    static JPanel buildMenu() {
        JPanel page = bg();
        page.setLayout(new BorderLayout(0, 20));
        page.setBorder(pad(30, 40, 30, 40));

        JPanel header = headerRow("Menu", "Choose your items and add them to cart");

        JPanel grid = new JPanel(new GridLayout(2, 3, 20, 20));
        grid.setOpaque(false);

        grid.add(foodCard("Cheese Burger", 80, "Burger", "Crispy and juicy, 7 mins"));
        grid.add(foodCard("Cold Coffee", 60, "Coffee", "Chilled and creamy, 3 mins"));
        grid.add(foodCard("Masala Fries", 70, "Fries", "Spicy and crunchy, 5 mins"));
        grid.add(foodCard("Masala Dosa", 55, "Dosa", "Crisp dosa with chutney, 8 mins"));
        grid.add(foodCard("Hakka Noodles", 90, "Noodles", "Wok tossed, 10 mins"));
        grid.add(foodCard("Mini Pizza", 120, "Pizza", "Loaded 4 slice pizza, 12 mins"));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        bottom.setOpaque(false);

        JButton goCart = btn("Go to Cart", PURPLE);
        JButton goHome = btnOutline("Home");

        bottom.add(goCart);
        bottom.add(goHome);

        page.add(header, BorderLayout.NORTH);
        page.add(grid, BorderLayout.CENTER);
        page.add(bottom, BorderLayout.SOUTH);

        goCart.addActionListener(e -> showPage("cart"));
        goHome.addActionListener(e -> showPage("home"));

        return page;
    }

    static JPanel foodCard(String name, int price, String type, String desc) {
        JPanel card = surfaceColored(SURFACE);
        card.setLayout(new BorderLayout());

        JPanel strip = new JPanel();
        strip.setPreferredSize(new Dimension(0, 4));

        if (price >= 100) {
            strip.setBackground(PINK);
        } else if (price >= 80) {
            strip.setBackground(AMBER);
        } else {
            strip.setBackground(CYAN);
        }

        card.add(strip, BorderLayout.NORTH);

        JPanel body = new JPanel(new GridLayout(5, 1, 0, 6));
        body.setOpaque(false);
        body.setBorder(pad(18, 20, 18, 20));

        JLabel typeLabel = label(type, FONT_H3, CYAN);
        JLabel nameLabel = label(name, FONT_H3, WHITE);
        JLabel priceLabel = label("Rs. " + price, FONT_H2, PINK);
        JLabel descLabel = label(desc, FONT_SMALL, MUTED);

        JButton add = btn("Add to Cart", PURPLE);
        add.setFont(new Font("Segoe UI", Font.BOLD, 13));

        body.add(typeLabel);
        body.add(nameLabel);
        body.add(priceLabel);
        body.add(descLabel);
        body.add(add);

        card.add(body, BorderLayout.CENTER);

        add.addActionListener(e -> {
            cartItems.add(name);
            cartPrices.add(price);
            toast(name + " added to cart");
        });

        return card;
    }

    static JPanel buildCart() {
        JPanel page = bg();
        page.setLayout(new BorderLayout(0, 20));
        page.setBorder(pad(30, 40, 30, 40));

        int subtotal = 0;

        for (int price : cartPrices) {
            subtotal += price;
        }

        int total = cartItems.isEmpty() ? 0 : subtotal + 5;

        JPanel header = headerRow("Your Cart", cartItems.size() + " item(s)");

        JPanel listPanel = new JPanel();
        listPanel.setOpaque(false);
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        if (cartItems.isEmpty()) {
            JLabel empty = label("Your cart is empty. Head to the menu.", FONT_H2, MUTED);
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);

            listPanel.add(Box.createVerticalStrut(40));
            listPanel.add(empty);
        } else {
            for (int i = 0; i < cartItems.size(); i++) {
                listPanel.add(cartRow(cartItems.get(i), cartPrices.get(i)));
                listPanel.add(Box.createVerticalStrut(10));
            }

            listPanel.add(Box.createVerticalStrut(10));
            listPanel.add(divider());
            listPanel.add(Box.createVerticalStrut(10));
            listPanel.add(cartRow("Platform Fee", 5));
            listPanel.add(Box.createVerticalStrut(10));
            listPanel.add(divider());
            listPanel.add(Box.createVerticalStrut(12));

            JPanel totalRow = new JPanel(new BorderLayout());
            totalRow.setOpaque(false);
            totalRow.add(label("TOTAL", FONT_H2, WHITE), BorderLayout.WEST);
            totalRow.add(label("Rs. " + total, FONT_H2, GREEN), BorderLayout.EAST);

            listPanel.add(totalRow);
        }

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(12);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        bottom.setOpaque(false);

        JButton place = btn("Place Order", GREEN);
        JButton clear = btnOutline("Clear Cart");
        JButton toMenu = btnOutline("Back to Menu");

        bottom.add(place);
        bottom.add(clear);
        bottom.add(toMenu);

        page.add(header, BorderLayout.NORTH);
        page.add(scroll, BorderLayout.CENTER);
        page.add(bottom, BorderLayout.SOUTH);

        int finalTotal = total;

        place.addActionListener(e -> {
            if (cartItems.isEmpty()) {
                toast("Cart is empty");
                return;
            }

            latestToken = "SS-" + tokenCounter;
            tokenCounter++;

            orderStatus = "Placed";
            totalOrders++;
            revenue += finalTotal;

            cartItems.clear();
            cartPrices.clear();

            showPage("track");
        });

        clear.addActionListener(e -> {
            cartItems.clear();
            cartPrices.clear();
            showPage("cart");
        });

        toMenu.addActionListener(e -> showPage("menu"));

        return page;
    }

    static JPanel buildTrack() {
        JPanel page = bg();
        page.setLayout(new GridBagLayout());

        JPanel card = surface(new Dimension(700, 440));
        card.setLayout(new GridLayout(6, 1, 0, 18));
        card.setBorder(pad(40, 50, 40, 50));

        JLabel title = label("Order Tracking", FONT_H1, WHITE);
        JLabel token = label("Token: " + latestToken, FONT_MONO, PINK);
        JLabel status = label("Status: " + orderStatus, FONT_H2, CYAN);
        JLabel eta = label("Estimated wait: " + getETA() + " mins", FONT_BODY, MUTED);

        JPanel progress = buildProgressBar();

        JButton home = btn("Back to Home", PURPLE);

        card.add(title);
        card.add(token);
        card.add(status);
        card.add(eta);
        card.add(progress);
        card.add(home);

        page.add(card);

        home.addActionListener(e -> showPage("home"));

        return page;
    }

    static JPanel buildProgressBar() {
        String[] steps = {"Placed", "Preparing", "Ready", "Picked Up"};
        Color[] colors = {PURPLE, AMBER, GREEN, CYAN};

        JPanel row = new JPanel(new GridLayout(1, 4, 8, 0));
        row.setOpaque(false);

        int reached = 0;

        for (int i = 0; i < steps.length; i++) {
            if (steps[i].equals(orderStatus)) {
                reached = i;
            }
        }

        for (int i = 0; i < steps.length; i++) {
            boolean active = i <= reached;

            JPanel step = new JPanel(new GridLayout(2, 1, 0, 4));
            step.setBackground(active ? colors[i] : SURFACE2);
            step.setBorder(pad(10, 8, 10, 8));

            JLabel number = label(String.valueOf(i + 1), FONT_H3, active ? BG : BORDER);
            JLabel name = label(steps[i], FONT_SMALL, active ? BG : MUTED);

            number.setHorizontalAlignment(SwingConstants.CENTER);
            name.setHorizontalAlignment(SwingConstants.CENTER);

            step.add(number);
            step.add(name);

            row.add(step);
        }

        return row;
    }

    static JPanel buildAdmin() {
        JPanel page = bg();
        page.setLayout(new BorderLayout(0, 24));
        page.setBorder(pad(30, 40, 30, 40));

        JPanel header = headerRow("Admin Dashboard", "Canteen management");

        JPanel center = new JPanel(new GridLayout(1, 2, 24, 0));
        center.setOpaque(false);

        JPanel statsCol = new JPanel(new GridLayout(3, 1, 0, 16));
        statsCol.setOpaque(false);

        statsCol.add(statCard("Total Orders", String.valueOf(totalOrders), PURPLE));
        statsCol.add(statCard("Revenue", "Rs. " + revenue, GREEN));
        statsCol.add(statCard("Rush Level", rushLabel(), rushColor()));

        JPanel control = surfaceColored(SURFACE2);
        control.setLayout(new GridLayout(5, 1, 0, 16));
        control.setBorder(pad(30, 30, 30, 30));

        JLabel controlTitle = label("Order Control", FONT_H2, WHITE);
        JLabel tokenLabel = label("Token: " + latestToken, FONT_MONO, PINK);
        JLabel statusLabel = label("Status: " + orderStatus, FONT_H3, CYAN);

        JButton nextBtn = btn("Advance Status", AMBER);
        JButton backBtn = btnOutline("Back to Welcome");

        control.add(controlTitle);
        control.add(tokenLabel);
        control.add(statusLabel);
        control.add(nextBtn);
        control.add(backBtn);

        center.add(statsCol);
        center.add(control);

        page.add(header, BorderLayout.NORTH);
        page.add(center, BorderLayout.CENTER);

        nextBtn.addActionListener(e -> {
            if (orderStatus.equals("Placed")) {
                orderStatus = "Preparing";
            } else if (orderStatus.equals("Preparing")) {
                orderStatus = "Ready";
            } else if (orderStatus.equals("Ready")) {
                orderStatus = "Picked Up";
            } else {
                toast("No active order to advance");
            }

            showPage("admin");
        });

        backBtn.addActionListener(e -> showPage("welcome"));

        return page;
    }

    static JPanel bg() {
        return new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(BG);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
    }

    static JPanel surface(Dimension size) {
        JPanel panel = surfaceColored(SURFACE);
        panel.setPreferredSize(size);
        return panel;
    }

    static JPanel surfaceColored(Color background) {
        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(background);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        panel.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        panel.setOpaque(false);
        return panel;
    }

    static JLabel label(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    static JButton btn(String text, Color color) {
        JButton button = new JButton(text) {
            protected void paintComponent(Graphics g) {
                g.setColor(getModel().isRollover() ? color.darker() : color);
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };

        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setForeground(Color.WHITE);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBorder(pad(10, 22, 10, 22));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return button;
    }

    static JButton btnOutline(String text) {
        JButton button = new JButton(text) {
            protected void paintComponent(Graphics g) {
                if (getModel().isRollover()) {
                    g.setColor(SURFACE2);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }

                super.paintComponent(g);
            }
        };

        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setForeground(MUTED);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(9, 20, 9, 20)
        ));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return button;
    }

    static JButton navBtn(String text, Color accent) {
        JButton button = btn(text, accent);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(0, 72));
        return button;
    }

    static JTextField field(String hint) {
        JTextField field = new JTextField(hint) {
            boolean firstFocus = true;

            {
                addFocusListener(new FocusAdapter() {
                    public void focusGained(FocusEvent e) {
                        if (firstFocus) {
                            setText("");
                            firstFocus = false;
                            setForeground(WHITE);
                        }
                    }
                });
            }
        };

        field.setFont(FONT_BODY);
        field.setForeground(DARK_TEXT);
        field.setBackground(SURFACE2);
        field.setCaretColor(WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)
        ));

        return field;
    }

    static JPanel statCard(String title, String value, Color color) {
        JPanel panel = surfaceColored(SURFACE);
        panel.setLayout(new GridLayout(2, 1, 0, 6));
        panel.setBorder(pad(20, 22, 20, 22));

        panel.add(label(title, FONT_SMALL, MUTED));
        panel.add(label(value, FONT_H2, color));

        return panel;
    }

    static JPanel headerRow(String title, String subtitle) {
        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 4));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        panel.add(label(title, FONT_H1, WHITE));
        panel.add(label(subtitle, FONT_BODY, MUTED));

        return panel;
    }

    static JPanel cartRow(String name, int price) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);

        row.add(label(name, FONT_BODY, WHITE), BorderLayout.WEST);
        row.add(label("Rs. " + price, FONT_BODY, MUTED), BorderLayout.EAST);

        return row;
    }

    static JSeparator divider() {
        JSeparator separator = new JSeparator();
        separator.setForeground(BORDER);
        separator.setBackground(BORDER);
        return separator;
    }

    static Border pad(int top, int left, int bottom, int right) {
        return BorderFactory.createEmptyBorder(top, left, bottom, right);
    }

    static String rushLabel() {
        if (totalOrders <= 2) return "Low";
        if (totalOrders <= 5) return "Medium";
        return "High";
    }

    static Color rushColor() {
        if (rushLabel().equals("Low")) return GREEN;
        if (rushLabel().equals("High")) return PINK;
        return AMBER;
    }

    static Color orderStatusColor() {
        if (orderStatus.equals("Placed")) return AMBER;
        if (orderStatus.equals("Preparing")) return PURPLE;
        if (orderStatus.equals("Ready")) return GREEN;
        if (orderStatus.equals("Picked Up")) return CYAN;
        return MUTED;
    }

    static int getETA() {
        if (rushLabel().equals("Low")) return 7;
        if (rushLabel().equals("High")) return 18;
        return 12;
    }

    static void toast(String message) {
        JOptionPane.showMessageDialog(frame, message, "SnackStack", JOptionPane.INFORMATION_MESSAGE);
    }
}