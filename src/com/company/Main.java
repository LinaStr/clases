package com.company;

import com.company.transactions.Transaction;
import com.company.users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        int choice;
        List <User> users = new ArrayList<>(); //<> rei6kia generik tipas, netinka int, tinka Integer

        do {
            printMenu();
            Scanner sc = new Scanner(System.in);
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    createUser(users, sc);
                    break;
                case 2:
                    printUsersList(users);
                    break;
                case 3:
                    printUsersList(users);
                    System.out.println("kelinta vartotoja nori i6trinti?");
                    int remove = sc.nextInt();
                    remove--;
                    users.remove(remove);
                    break;
                case 4:
                    transactionFromAccToAcc(users, sc);
                    break;

                case 5:
                    depositToAccount(users, sc);
                    break;
                case 6:
                    printUsersList(users);
                    System.out.println("is kurios saskaitos norite isimti pinigus?");
                    String debitaccount = sc.next();
                    System.out.println("koek nori isimti?");
                    break;

                case 0:
                    break;
                default:
                    System.out.println("ismok skaityti");

            }

        }while (choice!=0);




    }

    private static void depositToAccount(List<User> users, Scanner sc) {
        printUsersList(users);
        System.out.println("ivesk saskaita i kurią nori idėti pinigus");
        String depositaccount = sc.next();
        System.out.println("kiek nori id4ti?");
        double depositamount = sc.nextDouble();

        for (User u: users) {
            if (u.getAccountNo().equals(depositaccount)) {
                u.setBalance(u.getBalance()+depositamount);
            } else {
                System.out.println("tokios s1skaitos n4ra");
            }
        }
    }

    private static void transactionFromAccToAcc(List<User> users, Scanner sc) {
        printUsersList(users);
        System.out.println("sask nr is kurios darysi pavedima");
        String accfrom = sc.next();
        System.out.println("sask nr i kuria darysi pavedima");
        String accto = sc.next();
        System.out.println("kiek pervesi");
        double amount = sc.nextDouble();
//                    users.stream().filter(u->u.getAccountNo().equals(accfrom));
        User from = null;
        User to = null;
        for (User u: users) {
            if (u.getAccountNo().equals(accfrom)) {
                from = u;
            }
            if (u.getAccountNo().equals(accto)) {
                to = u;
            }
        }
        users.remove(from);
        users.remove(to);

        Transaction transaction = new Transaction(from, to, amount);
        List <User> transactedUsers = transaction.doTransaction()
                .stream()
                .filter(u-> u!=null)
                .collect(Collectors.toList());

        users.addAll (transactedUsers);

        printUsersList(users);
    }

    private static void createUser(List<User> users, Scanner sc) {
        System.out.println("iveskite savo varda");
        String name = sc.next();

        System.out.println("iveskite savo balanca");
        double balance = sc.nextDouble();

        System.out.println("koki kredita");

        User user = new User(name, balance);
        users.add(user); //i users lista prideda  useri
    }

    public static void printUsersList(List <User> users){
        int i = 1;
        System.out.println("vartotoju sarasas: ");
        for (User u: users){
            System.out.println(i + ". " + u);
            i++;
        }
    }

    public static void printMenu (){
        System.out.println();
        System.out.println("1. sukurti varotoja, 2. persiureti vartotoja, 3. i6trinti useri 4. padaryti pavedima 5. papildyti ssaskaita 0. baigti programa");
    }


}
