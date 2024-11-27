import java.util.Scanner;

class Main1 {
    public static void main(String[] args) {
        char r;
        Scanner obj1 = new Scanner(System.in);

        Library library = new Library(); // Library instance creation

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
                    library.add(obj1); // Pass the existing Scanner object
                    break;
                case 2:
                    library.iss(obj1); // Pass the existing Scanner object
                    break;
                case 3:
                    library.ret(obj1); // Pass the existing Scanner object
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
    static String[] bookNames = new String[100];  
    static float[] bookPrices = new float[100];   
    static int[] bookNos = new int[100];          
    static boolean[] bookIssued = new boolean[100]; // Track issued status
    static int bookCount = 0;

    static String issueBookName, issueDate, returnDate;
    static int issueBookID;
    
    void add(Scanner obj1) {
        System.out.println("How many books do you want to add?");
        int numberOfBooks = obj1.nextInt();
        obj1.nextLine();

        if (bookCount + numberOfBooks <= 100) {  
            for (int i = 0; i < numberOfBooks; i++) {
                System.out.println("Enter details for Book " + (bookCount + 1));
                System.out.println("Enter Book Name:");
                bookNames[bookCount] = obj1.nextLine();
                System.out.println("Enter Book Price:");
                bookPrices[bookCount] = obj1.nextFloat();
                System.out.println("Enter Book ID:");
                bookNos[bookCount] = obj1.nextInt();
                obj1.nextLine();

                bookIssued[bookCount] = false; // Initially, all books are not issued

                System.out.println("Book added: " + bookNames[bookCount] + ", Price: " + bookPrices[bookCount] + ", Book No: " + bookNos[bookCount]);
                bookCount++;
            }
        } else {
            System.out.println("Not enough space to add more books. Available slots: " + (100 - bookCount));
        }
    }

    void iss(Scanner obj1) {
        System.out.println("Enter Book Name to issue:");
        issueBookName = obj1.nextLine();
        System.out.println("Enter Book ID to issue:");
        issueBookID = obj1.nextInt();
        obj1.nextLine();  
        System.out.println("Enter Issue Date:");
        issueDate = obj1.nextLine();

        // Check if the book is already issued
        boolean bookFound = false;
        for (int i = 0; i < bookCount; i++) {
            if (bookNos[i] == issueBookID && !bookIssued[i]) {
                bookIssued[i] = true;  // Mark the book as issued
                System.out.println("Book issued successfully!");
                bookFound = true;
                break;
            }
        }

        if (!bookFound) {
            System.out.println("Either the book is already issued or the book ID is invalid.");
        }
    }

    void ret(Scanner obj1) {
        System.out.println("Enter Book ID to return:");
        int returnBookID = obj1.nextInt();
        obj1.nextLine(); 

        boolean bookFound = false;
        for (int i = 0; i < bookCount; i++) {
            if (bookNos[i] == returnBookID && bookIssued[i]) {
                bookIssued[i] = false;  // Mark the book as returned
                System.out.println("Book returned successfully.");
                bookFound = true;
                break;
            }
        }

        if (!bookFound) {
            System.out.println("Invalid Book ID or the book was not issued.");
        }
    }

    void detail() {
        System.out.println("Books details:");
        for (int i = 0; i < bookCount; i++) {
            System.out.println("Book Name: " + bookNames[i]);
            System.out.println("Book ID: " + bookNos[i]);
            System.out.println("Book Price: " + bookPrices[i]);
            System.out.println("Issued Status: " + (bookIssued[i] ? "Issued" : "Available"));
            System.out.println("------------");
        }
    }

    void exit() {
        System.out.println("Exiting...");
        System.exit(0);
    }
}