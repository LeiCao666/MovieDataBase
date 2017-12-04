package csudh.leicao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by caolei on 9/16/17.
 */
public class Menu {
    private MovieSet movieset = new MovieSet();
    protected void startMenu(){
        while(true) { //to make the menu appear repetively until choice "8" is made to exit the program.
            System.out.println("\n********************* Menu *********************");
            System.out.println("   1. List all movies");
            System.out.println("   2. Display all sortby year; old to new");
            System.out.println("   3. Sortby Runtime; short to long");
            System.out.println("   4. Sortby Ratings; high to low");
            System.out.println("   5. Ask for genre and display");
            System.out.println("   6. Search by Name");
            System.out.println("   7. Add a movie(up to five)");
            System.out.println("   8. Exit");
            System.out.println("************************************************\n");

            System.out.println("Please make your choice: ");
            int choice = 0;
            choice = ReadData.readInt();

            switch (choice) {
                case 1:
                    case1();
                    break;
                case 2:
                    case2();
                    break;
                case 3:
                    case3();
                    break;
                case 4:
                    case4();
                    break;
                case 5:
                    case5();
                    break;
                case 6:
                    case6();
                    break;
                case 7:
                    case7();
                    break;
                case 8:
                    case8();
                    break;
                default:
                    System.out.println("Please enter a valid choice number!");

            }
            System.out.println("\nContinue? (enter any key to continue or \"n\" to exit): ");
            String ifContinue = ReadData.readString();
            if(ifContinue.equalsIgnoreCase("n")){
                case8();// Exit the system.
            }
        }
    }

    private boolean case1(){
        movieset.printAll();
        return true;
    }
    private boolean case2(){
        callSortMethod("getYear");
        return true;
    }
    private boolean case3(){
        callSortMethod("getRuntime");
        return true;
    }
    private boolean case4(){
        callSortMethod("getRating");
        return true;
    }
    private boolean case5(){
        String genreInput = "";
        System.out.println("Please enter a genre: Comedy, Drama, Sci-Fi, Action, Documentary (Case-sensitive)");

        genreInput = ReadData.readString();
        if (genreInput.equals("Comedy") || genreInput.equals("Drama") || genreInput.equals("Sci-Fi")
                || genreInput.equals("Action") || genreInput.equals("Documentary")){
            for(int i = 0; i < movieset.getSize(); i++) {
                if (movieset.getMovieSet()[i].getGenre().equals(genreInput)) {
                    movieset.printMovie(i);
                }
            }
        }else{
            System.out.println("No movies are found in the genre.\n");
            return false;
        }
        return true;
    }
    private boolean case6(){
        String name;
        boolean checkExist=false;
        System.out.println("Please enter the movie name you're looking for: ");
        name = ReadData.readString();
        // print all movies with the entered name
        for(int i = 0; i < movieset.getSize(); i++){
            if (name.equals(movieset.getMovieSet()[i].getName())){
                movieset.printMovie(i);
                checkExist=true;
            }
        }
        if(checkExist) {
            return true;
        }else {
            System.out.println("Sorry, the movie is not found.");
            return false;
        }
    }
    private boolean case7(){
        if (movieset.getSize()>=movieset.getCapacity()) {
            System.out.println("You have no more space to add movie");
            return false;
        }
        String name="";
        int year=0;
        int runtime=0;
        String genre="";
        double rating=0.0;
        System.out.println("Please enter the prompted fields.");
        System.out.println("Name: ");//will add constraint: 0.1-10.0 duplicate checking
        name = ReadData.readString();
        System.out.println("Year: ");//will add constraint: 0.1-10.0
        year = ReadData.readInt();
        System.out.println("Runtime: ");//will add constraint: 0.1-10.0
        runtime = ReadData.readInt();
        System.out.println("Genre: ");
        genre = ReadData.readString();

        while(true){ //use an while loop to repetitively ask for rating input if invalid.
            System.out.println("Rating: (0.1-10.0)");
            rating = ReadData.readDouble();
            if(rating<0.1 || rating>10.0){
                System.out.println("Invalid input. Please enter a rating within this range: 0.1-10.0");
            }else {
                break;
            }
        }

        movieset.addMovie(name, year, runtime, genre, rating);
        return true;
    }
    private boolean case8(){
        System.out.println("Goodbye!");
        System.exit(0);
        return true;
    }
    private boolean callSortMethod(String methodName){

        int arr[] = new int[movieset.getSize()];
        /* Because Rating is double type while others are int type and the project requires reversed order (descending) for Rating,
         * Rating's processing is separate */
        if (methodName.equals("getRating")){
            /* copy the values that need to be sorted from Moive[] to arr[]
             * Please refer the the comment before merge() method in MovieSet class
             * */
            for (int i = 0; i < movieset.getSize(); i++){
                //remove decimal point(*10) and cast double to int because merge sort only sort int
                arr[i] = (int)(movieset.getMovieSet()[i].getRating()*10);
            }
            movieset.sort(arr, 0, movieset.getSize() - 1);
            movieset.reverseOrder(); // reverse the order to descending order
            movieset.printAll();
            return true;
        }

        /* Year and Runtime's sorting processing is here */
        for (int i = 0; i < movieset.getSize(); i++){
            //using java.lang.reflect.Method, so I can create a general method to call multiple methods with concrete names
            //get an inspiration from https://docs.oracle.com/javase/tutorial/reflect/member/methodInvocation.html
            try { //add a try block to handle exceptions
                //get the method from the Movie class by the methodName provided
                Method method = Movie.class.getDeclaredMethod(methodName);
                //to copy the column out to a separate int[], so it can be sorted by mergesort algorithm
                arr[i] = (int) method.invoke(movieset.getMovieSet()[i]);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        movieset.sort(arr, 0, movieset.getSize() - 1);
        movieset.printAll();
        return true;
    }

}
