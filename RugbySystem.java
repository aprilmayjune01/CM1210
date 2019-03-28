public class RugbySystem {

    public static void main(String[] args){

        // This is the list of all players currently loaded into the program
        List<Player> players = new ArrayList<Players>();

        // Scanner to get the user's input
        Scanner in = new Scanner(System.in);

        // Store the user's input
        int choice;

        // True whilst the user wants the program to continue running
        boolean running = true;
        while (running){
            // Display all of the menu options
            System.out.println("");
            System.out.println("Please select an option:");
            System.out.println("\t1. Load player data from file");
            System.out.println("\t2. Save player data to file");
            System.out.println("\t3. Add a player");
            System.out.println("\t4. View players");
            System.out.println("\t5. Delete a player by index");
            System.out.println("\t6. Quit");
            System.out.println("");
            // If the user has entered an int
            if (in.hasNextInt()){
                // Get the int
                choice = in.nextInt();
                // If it is not a valid menu choice
                if (choice >= 9 || choice <= 0){
                    System.out.println("Please enter a number");
                } else {
                    // Therefore a valid menu choice so get which choice it was and call the appropriate method
                    switch (choice) {
                        case 1:
                        players = optionLoad();
                        break;
                        case 2:
                        optionSave(players);
                        break;
                        case 3:
                        players.add(optionAdd());
                        break;
                        case 4:
                        filterMenu(players);
                        break;
                        case 5:
                        players = optionDelete(players);
                        break;
                        case 6:
                        running = false;
                        break;
                        default:
                        break;
                    }
                }
            } else {
                // Else the user hasn't entered an int
                System.out.println("Please enter a valid input");
                in.next();
            }
        }
    }

    /**
     * Displays the menu that shows all of the options for displaying players
     *
     * @param players List of all players. Doesn't use parent's list of players for explicitness so the method can be easily reused
     */
    public static void filterMenu(List<player> players) {
         // Get the user's choice
        Scanner in = new Scanner(System.in);
        System.out.println("Please select an option:");
        System.out.println("\t1. View all players");
        System.out.println("\t2. Filter by address");
        System.out.println("\t3. Filter by course");

        // Standard validation
        int choice;
        while (true) {
            if (in.hasNextInt()) {
                choice = in.nextInt();
                if (choice != 1 && choice != 2 && choice != 3) {
                    System.out.println("Please enter a valid option");
                } else {
                    break;
                }
            } else {
                System.out.println("Please enter a valid option");
                in.next();
            }
        }

        // Act appropriately based off of the input
        if (choice == 1) {
            optionShow(players);
        } else if (choice == 2) {
            optionSearchAddresses(players);
        } else if (choice == 3) {
            optionSearchCourse(players);
        }
    }

    /**
     * Saves the players to a file
     *
     * @param binary Whether the user wants to save as plaintext or binary
     * @param path The path to the file the user wants to save to. If the file doesn't exist, then a new *empty* one will be created
     * @param players The list of players to save
     *
     * @throws IOException
    */
    public static void save(boolean binary, String path, List<player> players) throws IOException {
        if (binary){
            // Class ObjectOutputStream retrieved 04/04/18
            // https://docs.oracle.com/javase/9/docs/api/java/io/ObjectOutputStream.html
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
            // Serialize object. Very similar to python pickle
            out.writeObject(players);
            out.close();
        }
        else {
            // Save each player as a new line in a .tsv style file
            PrintWriter writer = new PrintWriter(path, "UTF-8");
            for (player player : players) {
                writer.println(player.getName() + "\t" + player.getNumber() + "\t" + player.getCourse() + "\t" + player.getId() + "\t" + player.getHouseNumber() + "\t" + player.getStreetName() + "\t" + player.getTown() + "\t" + player.getPostcode());
            }
            writer.close();
        }
    }

    /**
     * Load players' details from a file
     *
     * @param binary Whether the file loaded is binary or text
     * @param path The path of the file to load
     *
     * @throws IOException
     * @throws ClassNotFoundException Thrown when the player class loaded doesn't match the actual player class
     */
    public static List<player> load(boolean binary, String path) throws IOException, ClassNotFoundException {
        // Errors aren't caught in this method because the method calling this one catches them
        if (binary){
            // Class ObjectInputStream retrieved 04/04/18
            // https://docs.oracle.com/javase/9/docs/api/java/io/ObjectInputStream.html
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
            List<player> s = (List<player>) in.readObject();
            in.close();
            return s;
        } else {
            // Create a new list of players
            List<player> players = new ArrayList<player>();
            // Open the file
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            // For each line in the file
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Split The line by tabs
                // Class Arrays retrieved 04/04/18
                // https://docs.oracle.com/javase/9/docs/api/java/util/Arrays.html
                List<String> values = new ArrayList<String>(Arrays.asList(line.split("\t")));
                players.add(new player(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5), values.get(6), values.get(7)));
            }
            return players;
        }
    }

    /**
     * Method that handles the user choosing which file to load
     *
     * @return List of players that have been loaded from the file
     */
    public static List<player> optionLoad() {
        // Get the file name and type. Repeat until valid
        while (true){
            System.out.println("Please enter the path/filename that you wish to load:");
            // Get the user's input
            Scanner in = new Scanner(System.in);
            String path = in.nextLine();
            System.out.println("Is this:\n\t1. A binary file\n\t2. A text file?");
            int choice;
            // Get the valid choice of binary/text
            while (true) {
                if (in.hasNextInt()) {
                    choice = in.nextInt();
                    if (choice >= 3 || choice <= 0) {
                        System.out.println("Please enter a valid number");
                    } else {
                        break;
                    }
                } else {
                    System.out.println("Please enter a valid number");
                    in.next();
                }
            }
            // Try loading a file based off of the user's entries
            try{
                if (choice == 1){
                    return load(true, path);
                } else {
                    return load(false, path);
                }
            } catch (IOException | ClassNotFoundException e) {
                // If there's an IO problem or the class loaded doesn't match the player class, repeat the method
                System.out.println("Unable to load file\n");
            }
        }
    }

    /**
     * Handles the user saving
     */
    public static void optionSave(List<player> players) {
        // Loop until the file had been saved
        while (true) {
            System.out.println("Please enter the path/filename that you wish to save (eg data.tsv or players.dat):");
            Scanner in = new Scanner(System.in);
            // Get the path to try and save the file at
            String path = in.nextLine();
            System.out.println("Would you like to save as:\n\t1. A binary file\n\t2. A text file?");
            // Get whether it is binary or text
            int choice;
            while (true) {
                if (in.hasNextInt()) {
                    choice = in.nextInt();
                    if (choice >= 3 || choice <= 0) {
                        System.out.println("Please enter a valid number");
                    } else {
                        break;
                    }
                } else {
                    System.out.println("Please enter a valid number");
                    in.next();
                }
            }
            // Try which ever type of save the user specified
            try {
                if (choice == 1) {
                    save(true, path, players);
                    return;
                } else {
                    save(false, path, players);
                    return;
                }
            } catch (IOException e) {
                // If there's a problem, tell the user and start the method again
                System.out.println("Unable to save file\n");
            }
        }
    }

    /**
     * Handles user adding a player
     *
     * @return A player object
     */
    public static player optionAdd() {
        // Declare all of the attributes that are going to be needed
        String name;
        String number;
        String course;
        String id;
        String houseNumber;
        String streetName;
        String town;
        String postcode;
        Scanner in = new Scanner(System.in);
        // Create a new blank player class
        player player = new player();

        // Try setting the attribute. If it isn't valid, the player object will throw an exception and the user will have to enter new data
        while (true) {
            try {
                System.out.println("Please enter the player's name:");
                name = in.nextLine();
                player.setName(name);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        // These all work in the same way
        while (true) {
            try {
                System.out.println("Please enter the player's number:");
                number = in.nextLine();
                player.setNumber(number);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.println("Please enter the player's course name:");
                course = in.nextLine();
                player.setCourse(course);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.println("Please enter the player's course ID:");
                id = in.nextLine();
                player.setId(id);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.println("Please enter the player's house number:");
                houseNumber = in.nextLine();
                player.setHouseNumber(houseNumber);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }


        while (true) {
            try {
                System.out.println("Please enter the player's street name:");
                streetName = in.nextLine();
                player.setStreetName(streetName);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.println("Please enter the player's town:");
                town = in.nextLine();
                player.setTown(town);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.println("Please enter the player's postcode:");
                postcode = in.nextLine();
                player.setPostcode(postcode);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        // And finally return the player so it can be added to the main list of players
        return player;
    }

    /**
     * Displays a list of players
     *
     * @param players The players to display. May have undesired effects if the list is empty.
     */
    public static void display(List<player> players) {
        System.out.println("-----------------------------------");
        // Iterate through the players printing each of their attributes
        for (player player : players) {
            System.out.println("Name:        " + player.getName());
            System.out.println("player No.: " + player.getNumber());
            System.out.println("Course Name: " + player.getCourse());
            System.out.println("Course ID:   " + player.getId());
            System.out.println("House No.:   " + player.getHouseNumber());
            System.out.println("Street Name: " + player.getStreetName());
            System.out.println("Town:        " + player.getTown());
            System.out.println("Postcode:    " + player.getPostcode());
            System.out.println("-----------------------------------");
        }
    }

    /**
     * Handles displaying the players
     *
     * @param players The list of players to display
     */
    public static void optionShow(List<player> players) {
        Scanner in = new Scanner(System.in);
        // If there are players
        if (players.size() > 0){
            // The user can either see all of the players or a subset of them
            System.out.println("Would you like to view:");
            System.out.println("\t1. All of the relevant players");
            System.out.println("\t2. A subset of the relevant players");
            // Get the user's choice
            int choice;
            while (true) {
                if (in.hasNextInt()) {
                    choice = in.nextInt();
                    if (choice != 1 && choice != 2) {
                        System.out.println("Please enter a valid option");
                    } else {
                        break;
                    }
                } else {
                    System.out.println("Please enter a valid option");
                    in.next();
                }
            }
            // If they wish to see all of them, then just call the display method
            if (choice == 1) {
                display(players);
            } else {
                // If they wish to see a subset
                System.out.println("There are " + Integer.toString(players.size()) + " relevant players");
                System.out.println("Please enter starting player index (1 - " + Integer.toString(players.size()) + ")");
                // Get the starting index
                int startIndex;
                while (true) {
                    if (in.hasNextInt()) {
                        startIndex = in.nextInt();
                        if (startIndex > players.size() || startIndex < 1) {
                            System.out.println("Please enter a valid option");
                        } else {
                            break;
                        }
                    } else {
                        System.out.println("Please enter a valid option");
                        in.next();
                    }
                }

                System.out.println("Please enter ending player index (" + Integer.toString(startIndex) + " - " + Integer.toString(players.size()) + ")");
                // Get the ending index. This can't be smaller than the starting index
                int endIndex;
                while (true) {
                    if (in.hasNextInt()) {
                        endIndex = in.nextInt();
                        if (endIndex < startIndex || endIndex > players.size()) {
                            System.out.println("Please enter a valid option");
                        } else {
                            break;
                        }
                    } else {
                        System.out.println("Please enter a valid option");
                        in.next();
                    }
                }
                // Uses the -1 because we want to include the one that the user said to start at
                display(players.subList(startIndex - 1, endIndex));
            }
        } else {
            // Tell the user there are no players to display
            System.out.println("There are no players to display");
        }
    }

    /**
     * Handles the user searching through courses
     *
     * @param players The list of players to search
     */
    public static void optionSearchCourse(List<player> players) {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter a substring to search courses for:");
        // Get the substring to search for
        String subString = in.nextLine();
        // To store the matches
        List<player> matches = new ArrayList<player>();
        // Iterate through the players
        for (player player : players) {
            // Uses lower case so it is case insensitive. Doesn't use regex because we don't want the user to be able to execute their own regex.
            if (player.getCourse().toLowerCase().contains(subString.toLowerCase())) {
                // If it matches, add it to the list of matches
                matches.add(player);
            }
        }
        // Finally, show the matches
        optionShow(matches);
    }

    /**
     * Handles the user searching by addess
     *
     * @param players The players to search
     */
    public static void optionSearchAddresses(List<player> players) {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter a substring to search address for:");
        // Get the substring to search for
        String subString = in.nextLine();
        // To store the matches
        List<player> matches = new ArrayList<player>();
        // Iterate through the players
        for (player player : players) {
            // If the substring matches any of the following:
            if (player.getHouseNumber().toLowerCase().contains(subString.toLowerCase()) ||
                player.getStreetName().toLowerCase().contains(subString.toLowerCase()) ||
                player.getTown().toLowerCase().contains(subString.toLowerCase()) ||
                player.getPostcode().toLowerCase().contains(subString.toLowerCase())) {
                    // Add it to the list of matches
                    matches.add(player);
            }
        }
        // Finally, show the matches
        optionShow(matches);
    }

    /**
     * Handles the user deleting a player
     *
     * @param players The list of players to delete from
     *
     * @return The new list of players once the specified player has been deleted
     */
    public static List<player> optionDelete(List<player> players) {
        System.out.println("Please enter the index of the player you wish to delete:");
        Scanner in = new Scanner(System.in);
        // Get the user's choice of index they wish to delete
        int choice;
        while (true) {
            if (in.hasNextInt()) {
                choice = in.nextInt();
                // Try to delete it
                try {
                    // -1 because you start counting at 1, not 0
                    players.remove(choice - 1);
                    System.out.println("Deleted player " + Integer.toString(choice));
                    break;
                } catch (IndexOutOfBoundsException e) {
                    // If there's an error, make the user pick again
                    System.out.println("This index doesn't exist");
                }
            } else {
                System.out.println("Please enter a valid option");
                in.next();
            }
        }
        // Finally, return the new list of players
        return players;
    }
}