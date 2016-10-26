package fi.jamk.sgkygolfcourses;

/**
 * Created by H3298 on 10/25/2016.
 */

public class Golfcourse {
    String name;
    String address;
    String email;
    String phone;
    String photoId;

    public Golfcourse(String name, String position, String email, String phone, String photoId) {
        this.name = name;
        this.address = position;
        this.email = email;
        this.phone = phone;
        this.photoId = photoId;
    }
}
