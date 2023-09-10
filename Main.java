package assn07;


import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String,String> passwordManager = new PasswordManager<>();

        // your code below
        System.out.println("Enter Master Password");
        String input = scanner.nextLine();
        while (!passwordManager.checkMasterPassword(input)){
            System.out.println("Enter Master Password");
            input = scanner.nextLine();
        }

        while (true){
            input = scanner.nextLine();
            if (Objects.equals(input, "New password")){
                String website = scanner.nextLine();
                String password = scanner.nextLine();
                passwordManager.put(website, password);
                System.out.println("New password added");
            } else if (Objects.equals(input, "Get password")){
                String website = scanner.nextLine();
                String password = passwordManager.get(website);
                if (password == null){
                    System.out.println("Account does not exist");
                }
                else{
                    System.out.println(password);
                }
            } else if (Objects.equals(input, "Delete account")){
                String website = scanner.nextLine();
                if (passwordManager.get(website) != null){
                    passwordManager.remove(website);
                    System.out.println("Account deleted");
                } else{
                    System.out.println("Account does not exist");
                }
            } else if (Objects.equals(input, "Check duplicate password")){
                String password = scanner.nextLine();
                List<String> duplicates = passwordManager.checkDuplicate(password);
                if (duplicates.isEmpty()){
                    System.out.println("No account uses that password");
                } else{
                    System.out.println("Websites using that password:");
                    duplicates.forEach(System.out::println);
                }
            } else if (Objects.equals(input, "Get accounts")){
                System.out.println("Your accounts:");
                passwordManager.keySet().forEach(System.out::println);
            } else if (Objects.equals(input, "Generate random password")){
                int length = scanner.nextInt();
                System.out.println(passwordManager.generateRandomPassword(length));
            } else if (Objects.equals(input, "Exit")){
                System.exit(0);
            } else{
                System.out.println("Command not found");
            }
        }

    }
}
