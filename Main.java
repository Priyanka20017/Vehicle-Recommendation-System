//-----Hackathon - Priyanka Kumari-----
// Functionality added in Vehicle Recommendation System:-
// 1. Nearest Distance of the vehicle from user
// 2. Vehicle number
// 3. Vehicle Category
// 4. No. of Passengers allowed in the vehicle
// 5. Driver is avilable or not
// 6. Driver Contact number
// 7. No. of successful rides done by driver
// 8. Recommendation based on Average Review

import java.util.*;

class Vehicle {
    String vehicleNo;
    String vehicleCategory;
    int noOfPassengers;
    boolean isAvailable;
    String driverContactNo;
    int locationId;
    int successfulRides;
    List<String> reviews; 
    double averageRating;

    Vehicle(int locationId, String vehicleNo, String vehicleCategory, int noOfPassengers, boolean isAvailable, String driverContactNo, int successfulRides) {
        this.locationId = locationId;
        this.vehicleNo = vehicleNo;
        this.vehicleCategory = vehicleCategory;
        this.noOfPassengers = noOfPassengers;
        this.isAvailable = isAvailable;
        this.driverContactNo = driverContactNo;
        this.successfulRides = successfulRides;
        this.reviews = new ArrayList<>();
        this.averageRating = 0.0;
         
    }
     public void addReview(String review) {
        reviews.add(review);
        updateAverageRating();
    }

    // average rating
    private void updateAverageRating() {
        if (reviews.isEmpty()) {
            averageRating = 0.0;
        } else {
            double sum = 0.0;
            for (String review : reviews) {
                sum += Double.parseDouble(review);
            }
            averageRating = sum / reviews.size();
        }
    }
}



class VehicleRecommendationSystem {
    private List<Vehicle> vehicles;

    VehicleRecommendationSystem() {
        vehicles = new ArrayList<>();
    }
    
   

    void addVehicle(int locationId, String vehicleNo, String vehicleCategory, int noOfPassengers, boolean isAvailable, String driverContactNo, int successfulRides) {
        vehicles.add(new Vehicle(locationId, vehicleNo, vehicleCategory, noOfPassengers, isAvailable, driverContactNo, successfulRides));
    }

    double calculateDistance(int x1, int y1) {
        double d = Math.abs(x1 - y1);
        return d * d;
    }

    List<Vehicle> getRecommendedVehicles(int userLocationId, int noOfPassengers) {
        List<Vehicle> recommendedVehicles = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {
            if (vehicle.isAvailable && vehicle.noOfPassengers >= noOfPassengers) {
                double distance = calculateDistance(userLocationId, vehicle.locationId);
                vehicle.locationId = (int) distance; // Save distance for sorting
                recommendedVehicles.add(vehicle);
            }
        }

        // Sorting on the basis of distance
        Collections.sort(recommendedVehicles, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle v1, Vehicle v2) {
                if (v1.locationId != v2.locationId) {
                    return Integer.compare(v1.locationId, v2.locationId);
                }
                return Integer.compare(v2.successfulRides, v1.successfulRides);
            }
        });

        return recommendedVehicles;
    }
}
class Main {
    public static void main(String[] args) {
        VehicleRecommendationSystem recommendationSystem = new VehicleRecommendationSystem();

        // Data Table
        recommendationSystem.addVehicle(100, "HR-26 0091", "Car", 4, true, "903300304", 24);
        recommendationSystem.addVehicle(201, "HR-26 7896", "Auto", 2, true, "900000666", 15);
        recommendationSystem.addVehicle(301, "HR-26 0491", "Car", 4, true, "964954944", 20);
        recommendationSystem.addVehicle(011, "HR-26 0953", "Car", 4, true, "45464454", 17);
        recommendationSystem.addVehicle(102, "HR-26 2387", "Auto", 2, true, "789954954", 25);
        recommendationSystem.addVehicle(110, "HR-26 8765", "Bike", 1, true, "960404544", 10);
        recommendationSystem.addVehicle(105, "HR-26 4564", "Bike", 1, true, "750450440", 30);

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your location id: ");
        int userLocationId = scanner.nextInt();

        System.out.print("Enter the number of passengers: ");
        int noOfPassengers = scanner.nextInt();

        List<Vehicle> recommendedVehicles = recommendationSystem.getRecommendedVehicles(userLocationId, noOfPassengers);

        System.out.println("Recommended vehicles:");
        for (int i = 0; i < 3 && i < recommendedVehicles.size(); ++i) {
            Vehicle vehicle = recommendedVehicles.get(i);
            System.out.println("Vehicle " + (i + 1) + ":");
            System.out.println("Vehicle No: " + vehicle.vehicleNo);
            System.out.println("Category: " + vehicle.vehicleCategory);
            System.out.println("No of Passengers: " + vehicle.noOfPassengers);
            System.out.println("Driver Contact No: " + vehicle.driverContactNo);
            System.out.println("Distance from User: " + vehicle.locationId);
            System.out.println("Successful rides: " + vehicle.successfulRides);
             System.out.println("Average Rating: " + vehicle.averageRating);
            System.out.println(" ");
            System.out.println(" ");
        }
    }
}