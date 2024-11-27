import java.util.Scanner;
import java.util.Vector;

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

class Book {
    String name;
    float price;
    int id;

    Book(String name, float price, int id) {
        this.name = name;
        this.price = price;
        this.id = id;
    }
}

class IssueDetails {
    String bookName;
    int bookID;
    String issueDate;
    String returnDate;

    IssueDetails(String bookName, int bookID, String issueDate, String returnDate) {
        this.bookName = bookName;
        this.bookID = bookID;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
    }
}

class Library {
    Vector<Book> books = new Vector<>();
    Vector<IssueDetails> issuedBooks = new Vector<>(); // Stores issued books details

    void add(Scanner obj1) {
        System.out.println("How many books do you want to add?");
        int numberOfBooks = obj1.nextInt();
        obj1.nextLine();

        for (int i = 0; i < numberOfBooks; i++) {
            System.out.println("Enter details for Book " + (books.size() + 1));
            System.out.println("Enter Book Name:");
            String name = obj1.nextLine();
            System.out.println("Enter Book Price:");
            float price = obj1.nextFloat();
            System.out.println("Enter Book ID:");
            int id = obj1.nextInt();
            obj1.nextLine();

            books.add(new Book(name, price, id));
            System.out.println("Book added: " + name + ", Price: " + price + ", Book No: " + id);
        }
    }

    void iss(Scanner obj1) {
        System.out.println("Book Name:");
        String issueBookName = obj1.nextLine();
        System.out.println("Book ID:");
        int issueBookID = obj1.nextInt();
        obj1.nextLine();  
        System.out.println("Issue date:");
        String issueDate = obj1.nextLine();
        System.out.println("Return book date:");
        String returnDate = obj1.nextLine();

        // Add the issued book details to the issuedBooks list
        issuedBooks.add(new IssueDetails(issueBookName, issueBookID, issueDate, returnDate));
        System.out.println("Book issued: " + issueBookName + ", Book ID: " + issueBookID);
    }

    void ret(Scanner obj1) {
        System.out.println("Enter Book ID to return:");
        int bookID = obj1.nextInt();
        obj1.nextLine(); // Consume the newline character
        
        boolean found = false;

        // Search for the issued book by ID
        for (IssueDetails issue : issuedBooks) {
            if (issue.bookID == bookID) {
                System.out.println("Book Details:");
                System.out.println("Book Name: " + issue.bookName);
                System.out.println("Book ID: " + issue.bookID);
                System.out.println("Issue Date: " + issue.issueDate);
                System.out.println("Return Date: " + issue.returnDate);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("No book found with the entered ID.");
        }
    }

    void detail() {
        if (issuedBooks.isEmpty()) {
            System.out.println("No books have been issued.");
        } else {
            System.out.println("Issued Books Details:");
            for (IssueDetails issue : issuedBooks) {
                System.out.println("Book Name: " + issue.bookName);
                System.out.println("Book ID: " + issue.bookID);
                System.out.println("Issue Date: " + issue.issueDate);
                System.out.println("Return Date: " + issue.returnDate);
            }
        }
    }

    void exit() {
        System.out.println("Exiting...");
        System.exit(0);
    }
}