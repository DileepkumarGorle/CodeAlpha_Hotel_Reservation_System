import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
class Room {
    private String roomNumber;
    private String roomType;
    private double rate;
    private boolean available;
    public Room(String roomNumber, String roomType, double rate) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.rate = rate;
        this.available = true; }
    public String getRoomNumber() {
        return roomNumber; }
    public String getRoomType() {
        return roomType; }
    public double getRate() {
        return rate; }
    public boolean isAvailable() {
        return available; }
    public void setAvailable(boolean available) {
        this.available = available; }
}
class Reservation {
    private Room room;
    private String guestName;
    private Date checkInDate;
    private Date checkOutDate;
    private double totalCost;
    public Reservation(Room room, String guestName, Date checkInDate, Date checkOutDate) {
        this.room = room;
        this.guestName = guestName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalCost = calculateTotalCost();
    }
    private double calculateTotalCost() {
        long diffInMillies = Math.abs(checkOutDate.getTime() - checkInDate.getTime());
        long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);
        return diffInDays * room.getRate(); }
    public String getGuestName() {
        return guestName;}
    public Room getRoom() {
        return room; }
    public Date getCheckInDate() {
        return checkInDate; }
    public Date getCheckOutDate() {
        return checkOutDate; }
    public double getTotalCost() {
        return totalCost; }
}
public class HotelReservationSystem {
    private static ArrayList<Room> rooms = new ArrayList<>();
    private static ArrayList<Reservation> reservations = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        initializeRooms();
        displayMenu(); }
    private static void initializeRooms() {
        rooms.add(new Room("101", "Standard", 99.99));
        rooms.add(new Room("202", "Deluxe", 149.99));
        rooms.add(new Room("303", "Suite", 199.99));}
    private static void displayMenu() {
        System.out.println("Hotel Reservation System");
        System.out.println("1. Search Rooms");
        System.out.println("2. Make Reservation");
        System.out.println("3. View Reservations");
        System.out.println("4. Cancel Reservation");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
        int choice;
        try {
            choice = scanner.nextInt();
            scanner.nextLine(); 
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); 
            displayMenu();
            return; }
        switch (choice) {
            case 1:
                searchRooms();
                break;
            case 2:
                makeReservation();
                break;
            case 3:
                viewReservations();
                break;
            case 4:
                cancelReservation();
                break;
            case 5:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                displayMenu();}
    }
    private static void searchRooms() {
        System.out.println("Available Rooms:");
        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.println(room.getRoomNumber() + " - " + room.getRoomType() + " - $" + room.getRate());
            } }
        displayMenu(); }
    private static void makeReservation() {
        System.out.print("Enter guest name: ");
        String guestName = scanner.nextLine();
        System.out.print("Enter room number: ");
        String roomNumber = scanner.nextLine();
        Room selectedRoom = null;
        for (Room room : rooms) {
            if (room.getRoomNumber().equals(roomNumber) && room.isAvailable()) {
                selectedRoom = room;
                break; } }
        if (selectedRoom == null) {
            System.out.println("Invalid room number or room is not available.");
        } else {
            System.out.print("Enter check-in date (yyyy-MM-dd): ");
            String checkInDateStr = scanner.nextLine();
            System.out.print("Enter check-out date (yyyy-MM-dd): ");
            String checkOutDateStr = scanner.nextLine();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date checkInDate, checkOutDate;
            try {
                checkInDate = dateFormat.parse(checkInDateStr);
                checkOutDate = dateFormat.parse(checkOutDateStr);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please try again.");
                displayMenu();
                return; }
            Reservation reservation = new Reservation(selectedRoom, guestName, checkInDate, checkOutDate);
            reservations.add(reservation);
            selectedRoom.setAvailable(false);
            System.out.println("Reservation created successfully!"); }
        displayMenu();
    }
    private static void viewReservations() {
        System.out.println("Current Reservations:");
        for (Reservation reservation : reservations) {
            System.out.println("Guest Name: " + reservation.getGuestName());
            System.out.println("Room Number: " + reservation.getRoom().getRoomNumber());
            System.out.println("Room Type: " + reservation.getRoom().getRoomType());
            System.out.println("Check-in Date: " + reservation.getCheckInDate());
            System.out.println("Check-out Date: " + reservation.getCheckOutDate());
            System.out.println("Total Cost: $" + reservation.getTotalCost());
            System.out.println("-------------------"); }
        displayMenu();}
    private static void cancelReservation() {
        System.out.print("Enter guest name: ");
        String guestName = scanner.nextLine();
        Reservation reservationToCancel = null;
        for (Reservation reservation : reservations) {
            if (reservation.getGuestName().equals(guestName)) {
                reservationToCancel = reservation;
                break; } }
        if (reservationToCancel == null) {
            System.out.println("No reservation found for the given guest name.");
        } else {
            reservations.remove(reservationToCancel);
            reservationToCancel.getRoom().setAvailable(true);
            System.out.println("Reservation canceled successfully!"); }
        displayMenu();
    }
}