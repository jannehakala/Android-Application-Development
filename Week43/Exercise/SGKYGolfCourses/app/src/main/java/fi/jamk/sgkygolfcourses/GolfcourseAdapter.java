package fi.jamk.sgkygolfcourses;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by H3298 on 10/25/2016.
 */

public class GolfcourseAdapter extends RecyclerView.Adapter<GolfcourseAdapter.ViewHolder> {
    private List<Golfcourse> golfcourseList;
    private Activity context;

    public GolfcourseAdapter(Activity context, List<Golfcourse> employeeList) {
        this.golfcourseList = employeeList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return golfcourseList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.golfcourse_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Golfcourse golfCourse = golfcourseList.get(position);
        Glide.with(this.context)
                .load("http://ptm.fi/jamk/android/golfcourses/" + golfCourse.photoId)
                .override(200,170)
                .into(viewHolder.golfCourseImageView);
        viewHolder.golfCourseNameTextView.setText(golfCourse.name);
        viewHolder.golfCourseAddressTextView.setText(golfCourse.address);
        viewHolder.golfCourseEmailTextView.setText(golfCourse.email);
        viewHolder.golfCoursePhoneTextView.setText(golfCourse.phone);
    }

    // view holder class to specify card UI objects
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView golfCourseImageView;
        public TextView golfCourseNameTextView;
        public TextView golfCourseAddressTextView;
        public TextView golfCourseEmailTextView;
        public TextView golfCoursePhoneTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            golfCourseImageView = (ImageView) itemView.findViewById(R.id.golfCourseImageView);
            golfCourseNameTextView = (TextView) itemView.findViewById(R.id.golfCourseNameTextView);
            golfCourseAddressTextView = (TextView) itemView.findViewById(R.id.golfCourseAddressTextView);
            golfCourseEmailTextView = (TextView) itemView.findViewById(R.id.golfCourseEmailTextView);
            golfCoursePhoneTextView = (TextView) itemView.findViewById(R.id.golfCoursePhoneTextView);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    String name = golfcourseList.get(position).name;
                    Toast.makeText(view.getContext(), name, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
