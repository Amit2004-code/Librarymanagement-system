package org.example;
import java.util.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
class Book {
    private static int nextBookId = 1;
    private int bookId;
    private String title;
    private String author;
    private int totalCopies;
    private int availableCopies;

    public Book(int bookId, String title, String author, int totalCopies) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.totalCopies = this.availableCopies = totalCopies;
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void decrementAvailableCopies() {
        availableCopies--;
    }

    public void incrementAvailableCopies() {
        availableCopies++;
    }
}

class Member {
    private static int nextMemberId = 1;
    private int memberId;
    private String name;
    private int age;
    private String phoneNumber;
    private double balance=0.0;

    public Member(String name, int age, String phoneNumber) {
        this.memberId = nextMemberId++;
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void updateBalance(double amount) {
        balance += amount;
    }
}

class Library {
    private List<Book> books;
    private List<Member> members;
    private Map<Member, List<Book>> borrowedBooks;

    public Library() {
        books = new ArrayList<>();
        members = new ArrayList<>();
        borrowedBooks = new HashMap<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(int bookId) {
        books.removeIf(book -> book.getBookId() == bookId);
    }

    public void registerMember(Member member) {
        members.add(member);
    }

    public void removeMember(String phoneNumber) {
        members.removeIf(member -> member.getPhoneNumber().equals(phoneNumber));
    }

    public Member findMember(String phoneNumber) {
        for (Member member : members) {
            if (member.getPhoneNumber().equals(phoneNumber)) {
                return member;
            }
        }
        return null;
    }

    public Book findBook(int bookId) {
        for (Book book : books) {
            if (book.getBookId() == bookId) {
                return book;
            }
        }
        return null;
    }

    public void issueBook(Member member, Book book) {
        borrowedBooks.computeIfAbsent(member, k -> new ArrayList<>()).add(book);
        book.decrementAvailableCopies();
    }

    public void returnBook(Member member, Book book) {
        borrowedBooks.get(member).remove(book);
        book.incrementAvailableCopies();
    }

    public List<Book> getAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getAvailableCopies() > 0) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    public List<Member> getAllMembers() {
        return members;
    }

    public List<Book> getBorrowedBooks(Member member) {
        return borrowedBooks.getOrDefault(member, new ArrayList<>());
    }

    public double calculateFine(int days) {
        return days * 3.0;
    }
}

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();
        Member currentMember = null;


        System.out.println("Library Portal Initialize");
        while (true) {
            System.out.println("---------------------------------");
            System.out.println("1.Enter as a librarian");
            System.out.println("2.Enter as a member");
            System.out.println("3.Exit");
            System.out.println("---------------------------------");
            int userType = scanner.nextInt();


            switch (userType) {
                case 1:
                    System.out.println("---------------------------------");
                    System.out.println("1.Register a member");
                    System.out.println("2.Remove a member");
                    System.out.println("3.Add a book");
                    System.out.println("4.Remove a book");
                    System.out.println("5.View all members along with their books and fines to be paid");
                    System.out.println("6.View all books");
                    System.out.println("7.Back");
                    System.out.println("---------------------------------");
                    int librarianChoice = scanner.nextInt();

                    switch (librarianChoice) {
                        case 1:

                            // to register a member
                            System.out.print("Name:");
                            String name = scanner.next();
                            System.out.print("Age:");
                            int age = scanner.nextInt();
                            System.out.print("Phone no:");
                            String phoneNumber = scanner.next();
                            Member newMember = new Member(name, age, phoneNumber);
                            library.registerMember(newMember);
                            System.out.println("Member Successfully Registered with Member ID: " + newMember.getPhoneNumber());
                            break;
                        case 2:
                            //    to remove a member
                            System.out.print("Phone No: ");
                            String removePhoneNumber = scanner.next();
                            library.removeMember(removePhoneNumber);
                            System.out.println("Member with Phone No: " + removePhoneNumber + " removed successfully.");
                            break;
                        case 3:

                            // Add a book
                            System.out.print("Book title: ");
                            String bookTitle = scanner.next();
                            System.out.print("Author: ");
                            String author = scanner.next();
                            System.out.print("Copies: ");
                            int copies = scanner.nextInt();
                            int bookId = library.getAvailableBooks().size() + 1 ; // Auto-increment book ID
                            Book newBook = new Book(bookId, bookTitle, author, copies);
                            library.addBook(newBook);
                            System.out.println("Book Added Successfully!");
                            break;
                        case 4:

                            // Remove a book
                            System.out.print("Book ID: ");
                            int removeBookId = scanner.nextInt();
                            library.removeBook(removeBookId);
                            System.out.println("Book with ID: " + removeBookId + " removed successfully.");
                            break;
                        case 5:

                            // View all members along with their books and fines to be paid
                            System.out.println("Members and their details:");
                            for (Member member : library.getAllMembers()) {
                                System.out.println("Name: " + member.getName());
                                System.out.println("Phone No: " + member.getPhoneNumber());
                                List<Book> borrowedBooks = library.getBorrowedBooks(member);
                                if (!borrowedBooks.isEmpty()) {
                                    System.out.println("Borrowed Books:");
                                    for (Book book : borrowedBooks) {
                                        System.out.println("Book ID: " + book.getBookId() + ", Title: " + book.getTitle());
                                    }
                                }
                                System.out.println("Balance Due: Rs. " + member.getBalance());
                                System.out.println("---------------------------------");
                            }
                            break;
                        case 6:

                            // View all books
                            System.out.println("Available Books:");
                            for (Book book : library.getAvailableBooks()) {
                                System.out.println("Book ID - " + book.getBookId());
                                System.out.println("Name - " + book.getTitle());
                                System.out.println("Author - " + book.getAuthor());
                                System.out.println("Available Copies - " + book.getAvailableCopies());
                                System.out.println();

                            }
                            break;
                        case 7:

                            // Back
                            break;
                        default:
                            System.out.println("Invalid choice. Please select a valid option.");
                    }
                    if (librarianChoice == 7) {
                        break; // Exit the librarian menu
                    }

                    break;
                case 2:
                    // Member Menu
                    System.out.println("---------------------------------");
                    System.out.println("Name: ");
                    String memberName = scanner.next();
                    System.out.println("Phone No: ");
                    String memberPhoneNumber = scanner.next();
                    currentMember = library.findMember(memberPhoneNumber);

                    if (currentMember != null && currentMember.getName().equals(memberName)) {
                        System.out.println("Welcome " + currentMember.getName() + ". Member ID: " + currentMember.getPhoneNumber());
                        System.out.println("---------------------------------");
                        System.out.println("1.List Available Books");
                        System.out.println("2.List My Books");
                        System.out.println("3.Issue book");
                        System.out.println("4.Return book");
                        System.out.println("5.Pay Fine");
                        System.out.println("6.Back");
                        System.out.println("---------------------------------");
                        int memberChoice = scanner.nextInt();

                        switch (memberChoice) {
                            case 1:
                                // List Available Books
                                System.out.println("Available Books:");
                                for (Book book : library.getAvailableBooks()) {
                                    System.out.println("Book ID: " + book.getBookId());
                                    System.out.println("Book Name: " + book.getTitle());
                                    System.out.println("---------------------------------");
                                }
                                break;
                            case 2:
                                // List My Books
                                List<Book> borrowedBooks = library.getBorrowedBooks(currentMember);
                                if (!borrowedBooks.isEmpty()) {
                                    System.out.println("Your Borrowed Books:");
                                    for (Book book : borrowedBooks) {
                                        System.out.println("Book ID: " + book.getBookId());
                                        System.out.println("Book Name: " + book.getTitle());
                                        System.out.println("---------------------------------");
                                    }
                                } else {
                                    System.out.println("You haven't borrowed any books.");
                                }
                                break;
                            case 3:
                                // Issue book
                                System.out.println("Enter Book ID: ");
                                int issueBookId = scanner.nextInt();
                                scanner.nextLine();
                                System.out.println("Enter Book Name: ");
                                String issueBookName = scanner.nextLine();
                                Book issueBook = library.findBook(issueBookId);
                                if (issueBook != null && issueBook.getTitle().equalsIgnoreCase(issueBookName) && issueBook.getAvailableCopies() > 0)  {
                                    library.issueBook(currentMember, issueBook);
                                    System.out.println("Book Issued Successfully!");
                                } else {
                                    System.out.println("Book not available for issuing.");
                                }
                                break;
                            case 4:
                                // Return book
                                List<Book> myBooks = library.getBorrowedBooks(currentMember);
                                if (!myBooks.isEmpty()) {
                                    System.out.println("Your Borrowed Books:");
                                    for (Book book : myBooks) {
                                        System.out.println("Book ID: " + book.getBookId());
                                        System.out.println("Book Name: " + book.getTitle());
                                        System.out.println("---------------------------------");
                                    }

                                    System.out.println("Enter Book ID: ");
                                    int returnBookId = scanner.nextInt();
                                    Book returnBook = library.findBook(returnBookId);
                                    if (returnBook != null && myBooks.contains(returnBook)) {
                                        System.out.println("Book ID: " + returnBook.getBookId() + " successfully returned.");
                                        int daysDelayed = 4;
                                        // For the purpose of the example
                                        double fineAmount = library.calculateFine(daysDelayed);
                                        currentMember.updateBalance(fineAmount);
                                        library.returnBook(currentMember, returnBook);
                                        System.out.println(fineAmount + " Rupees has been charged for a delay of " + daysDelayed + " days.");
                                    } else {
                                        System.out.println("You haven't borrowed the book with ID: " + returnBookId);
                                    }
                                } else {
                                    System.out.println("You haven't borrowed any books.");
                                }
                                break;
                            case 5:
                                // Pay Fine
                                double memberBalance = currentMember.getBalance();
                                if (memberBalance > 0) {
                                    System.out.println("You had a total fine of Rs. " + memberBalance + ". It has been paid successfully!");
                                    currentMember.updateBalance(-memberBalance);
                                } else {
                                    System.out.println("You don't have any pending fines.");
                                }
                                break;
                            case 6:
                                // Back
                                break;
                            default:
                                System.out.println("Invalid choice.");
                        }
                    } else {
                        System.out.println("Member with Name: " + memberName + " and Phone No: " + memberPhoneNumber + " doesn't exist.");
                    }
                    break;
                case 3:
                    // Exit
                    System.out.println("---------------------------------");
                    System.out.println("Thanks for visiting!");
                    System.out.println("---------------------------------");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
