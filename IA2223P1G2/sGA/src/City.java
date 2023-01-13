public class City {
    // Declaring three private variables.
    private int x, y, id;

    // A constructor.
    public City(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    /**
     * The function takes a city as an argument and returns the distance between the current city and
     * the city passed as an argument
     * 
     * @param other The city to which we are calculating the distance
     * @return The distance between two cities.
     */
    public double distance(City other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    /**
     * If the x and y coordinates of the current city are the same as the x and y coordinates of the
     * other city, return true. Otherwise, return false
     * 
     * @param other The city to compare to
     * @return The distance between the two cities.
     */
    public boolean equals(City other) {
        if (this.x == other.x && this.y == other.y)
            return true;
        return false;
    }

    // A getter method. It returns the value of the private variable `x`.
    public int getX() { return this.x; };
    
    // A getter method. It returns the value of the private variable `y`.
    public int getY() { return this.y; };

    // A getter method. It returns the value of the private variable `id`.
    public int getId() { return this.id; };
}
