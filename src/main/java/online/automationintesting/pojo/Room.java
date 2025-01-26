package online.automationintesting.pojo;

import online.automationintesting.utils.Helper;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import com.github.javafaker.Faker;

public class Room {
  // Room schema
  private int roomid;
  private String roomName;
  private String type;
  private boolean accessible;
  private String image;
  private String description;
  private String[] features;
  private int roomPrice;

  // Room enums
  public static enum RoomType {Single, Double, Twin, Family, Suite};
  public static enum RoomFeature {Wifi, Refreshments, TV, Safe, Radio, Views}
  private static final List<String> IMAGE_URLS = Arrays.asList(
      "https://img.freepik.com/premium-photo/bedroom-with-large-wall-panel-that-says-i-love-you_657162-961.jpg",
      "https://img.freepik.com/premium-photo/living-room-with-large-sofa-large-window-overlooking-ocean_865967-5160.jpg",
      "https://img.freepik.com/premium-photo/living-room-with-large-sofa-large-window-overlooking-ocean_865967-5160.jpg",
      "https://img.freepik.com/premium-photo/bedroom-with-large-window-that-has-picture-cityscape-it_849761-2556.jpg",
      "https://img.freepik.com/premium-photo/3d-rendered-interior-bed-room-with-black-tone-black-pillow-black-wall-master-bed-room-plants-sun-light-modern-bedrood_538866-507.jpg"
    );

  public Room(int roomid, String roomName, String type, boolean accessible, String image, String description, String[] features, int roomPrice) {
    this.roomid = roomid;
    this.roomName = roomName;
    this.type = type;
    this.accessible = accessible;
    this.image = image;
    this.description = description;
    this.features = features;
    this.roomPrice = roomPrice;
  }

  public static Room generateRandom() {
    return new Room(
      Room.generateRandomRoomId(),
      Room.generateRandomRoomName(),
      Room.generateRandomType(),
      Room.generateRandomAccessible(),
      Room.generateRandomImage(),
      Room.generateRandomDescription(),
      Room.generateRandomFeatures(),
      Room.generateRandomRoomPrice()
    );
  }

  public static int generateRandomRoomId() {
    return new Random().nextInt(1000);
  }

  public static String generateRandomRoomName() {
    Faker faker = new Faker();
    return faker.color().name() + " " + faker.gameOfThrones().city() + " Room";
  }

  public static String generateRandomType() {
    return RoomType.values()[new Random().nextInt(RoomType.values().length)].toString();
  }

  public static boolean generateRandomAccessible() {
    return new Random().nextBoolean();
  }

  public static String generateRandomImage() {
    Integer roomImageIndex = new Random().nextInt(IMAGE_URLS.size());
    return IMAGE_URLS.get(roomImageIndex);
  }

  public static String generateRandomDescription() {
    Faker faker = new Faker();
    return faker.lorem().sentence();
  }

  public static String[] generateRandomFeatures() {
    Integer numFeatures = new Random().nextInt(5);
    Integer[] selectedFeatures = Helper.getRandomDistinctValues(numFeatures, 5);
    String[] features = new String[numFeatures];
    for (int i = 0; i < numFeatures; i++) {
      features[i] = RoomFeature.values()[selectedFeatures[i]].toString();
    }
    return features;
  }

  public static int generateRandomRoomPrice() {
    return new Random().nextInt(1000);
  }

  // Getters for Jackson to serialize the object to JSON
  public int getRoomid() {
    return roomid;
  }

  public String getRoomName() {
    return roomName;
  }

  public String getType() {
    return type;
  }

  public boolean isAccessible() {
    return accessible;
  }

  public String getImage() {
    return image;
  }

  public String getDescription() {
    return description;
  }

  public String[] getFeatures() {
    return features;
  }

  public int getRoomPrice() {
    return roomPrice;
  }

}
