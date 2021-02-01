# Spiget API

To use the spiget API, you need the following in your pom.xml

```xml
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
    
    <dependency>
        <groupId>com.github.BlueTree242</groupId>
        <artifactId>Spiget</artifactId>
        <version>1.0</version>
    </dependency>
```

# Example
```java
        int id = 12345; //ID of the resource
        try {
        Resource resource = new Resource(id);
        System.out.println("Downloads: " + resource.getDownloads());
        System.out.println("Rating: " + resource.getRating().getRate() + "/5");
        System.out.println("Latest version: " + resource.getLatestVersion());
        System.out.println("Reviews:");
        for (Review review : resource.getReviews()) {
        System.out.println("--------------");
        System.out.println("Rating: " + review.getRating().getRate() + "/5");
        System.out.println("Version: " + review.getVersion());
        System.out.println("Message: " + review.getMessage());
        System.out.println("--------------");
        }
        } catch (Exception e) {
        System.out.println("Resource not found or does not exist");
        }
```
