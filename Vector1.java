import java.sql.*;
import java.util.Scanner;

class Vector1 {
    public static void main(String[] args) {
        char r;
        Scanner obj1 = new Scanner(System.in);
        Library library = new Library();

        do {
            System.out.println("Library Management System");
            System.out.println("Press 1 to add Books");
            System.out.println("Press 2 to issue a book");
            System.out.println("Press 3 to return a book");
            System.out.println("Press 4 to print complete issue details");
            System.out.println("Press 5 to exit");
            System.out.print("Enter Any Number: ");

            int a = obj1.nextInt();
            obj1.nextLine();

            switch (a) {
                case 1:
                    library.add(obj1);
                    break;
                case 2:
                    library.iss(obj1);
                    break;
                case 3:
                    library.ret(obj1);
                    break;
                case 4:
                    library.detail();
                    break;
                case 5:
                    library.exit();
                    break;
                default:
                    System.out.println("Invalid number");
            }

            System.out.println("Do you want to select the next option? Y/N");
            r = obj1.next().charAt(0);
            obj1.nextLine();

        } while (r == 'y' || r == 'Y');

        obj1.close();
    }
}

class Library {
    Connection con;

    public Library() {
        try {
            // Establish database connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db", "root", "");
            System.out.println("Database connected successfully.");
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }

    void add(Scanner obj1) {
        try {
            System.out.println("How many books do you want to add?");
            int numberOfBooks = obj1.nextInt();
            obj1.nextLine();

            for (int i = 0; i < numberOfBooks; i++) {
                System.out.println("Enter details for Book " + (i + 1));
                System.out.println("Enter Book Name:");
                String name = obj1.nextLine();
                System.out.println("Enter Book Price:");
                float price = obj1.nextFloat();
                System.out.println("Enter Book ID:");
                int id = obj1.nextInt();
                obj1.nextLine();

                // Insert book into the database
                String query = "INSERT INTO books (id, name, price) VALUES (?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setInt(1, id);
                pst.setString(2, name);
                pst.setFloat(3, price);
                pst.executeUpdate();

                System.out.println("Book added: " + name + ", Price: " + price + ", Book No: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    void iss(Scanner obj1) {
        try {
            System.out.println("Book Name:");
            String issueBookName = obj1.nextLine();
            System.out.println("Book ID:");
            int issueBookID = obj1.nextInt();
            obj1.nextLine();
            System.out.println("Issue date (YYYY-MM-DD):");
            String issueDate = obj1.nextLine();
            System.out.println("Return date (YYYY-MM-DD):");
            String returnDate = obj1.nextLine();

            // Insert issue details into the database
            String query = "INSERT INTO issued_books (book_id, book_name, issue_date, return_date) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, issueBookID);
            pst.setString(2, issueBookName);
            pst.setDate(3, Date.valueOf(issueDate));
            pst.setDate(4, Date.valueOf(returnDate));
            pst.executeUpdate();

            System.out.println("Book issued: " + issueBookName + ", Book ID: " + issueBookID);
        } catch (SQLException e) {
            System.out.println("Error issuing book: " + e.getMessage());
        }
    }

    void ret(Scanner obj1) {
        try {
            System.out.println("Enter Book ID to return:");
            int bookID = obj1.nextInt();
            obj1.nextLine();

            String query = "SELECT * FROM issued_books WHERE book_id = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, bookID);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                System.out.println("Book Details:");
                System.out.println("Book Name: " + rs.getString("book_name"));
                System.out.println("Book ID: " + rs.getInt("book_id"));
                System.out.println("Issue Date: " + rs.getDate("issue_date"));
                System.out.println("Return Date: " + rs.getDate("return_date"));

                // Delete book from issued_books
                String deleteQuery = "DELETE FROM issued_books WHERE book_id = ?";
                PreparedStatement deletePst = con.prepareStatement(deleteQuery);
                deletePst.setInt(1, bookID);
                deletePst.executeUpdate();

                System.out.println("Book returned successfully.");
            } else {
                System.out.println("No book found with the entered ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error returning book: " + e.getMessage());
        }
    }

    void detail() {
        try {
            String query = "SELECT * FROM issued_books";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("No books have been issued.");
            } else {
                System.out.println("Issued Books Details:");
                while (rs.next()) {
                    System.out.println("Book Name: " + rs.getString("book_name"));
                    System.out.println("Book ID: " + rs.getInt("book_id"));
                    System.out.println("Issue Date: " + rs.getDate("issue_date"));
                    System.out.println("Return Date: " + rs.getDate("return_date"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching issue details: " + e.getMessage());
        }
    }

    void exit() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing database connection: " + e.getMessage());
        }
        System.out.println("Exiting...");
        System.exit(0);
    }
}
