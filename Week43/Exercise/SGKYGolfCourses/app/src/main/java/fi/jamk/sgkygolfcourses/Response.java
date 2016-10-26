package fi.jamk.sgkygolfcourses;

import java.util.List;

/**
 * Created by H3298 on 10/26/2016.
 */

public interface Response {
    public void onJSONparserComplete(List<Golfcourse> golfcourseList);
}
