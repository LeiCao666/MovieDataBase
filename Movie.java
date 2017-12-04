package csudh.leicao;

/**
 * @author  Lei Cao
 * @version 1.0'
 * @since   2017-09-13
 */
public class Movie {
    // Attributes
    private String name;
    private int year;
    private int runtime;
    private String genre;
    private double rating;

    // Constructor
    public Movie(String name, int year, int runtime, String genre, double rating){
        this.name = name;
        this.year = year;
        this.runtime = runtime;
        this.genre = genre;
        this.rating = rating;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getGenre() {
        return genre;
    }

    public double getRating() {
        return rating;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
