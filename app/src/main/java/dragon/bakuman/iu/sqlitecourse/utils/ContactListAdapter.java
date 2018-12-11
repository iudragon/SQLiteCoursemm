package dragon.bakuman.iu.sqlitecourse.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import dragon.bakuman.iu.sqlitecourse.R;
import dragon.bakuman.iu.sqlitecourse.models.Contact;

public class ContactListAdapter extends ArrayAdapter<Contact> {

    private LayoutInflater mInflater;
    private List<Contact> mContacts = null;
    private ArrayList<Contact> arrayList;
    private int layoutResource;
    private Context mContext;
    private String mAppend;


    public ContactListAdapter(@NonNull Context context, int resource, @NonNull List<Contact> contacts, String append) {
        super(context, resource, contacts);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutResource = resource;
        this.mContext = context;
        mAppend = append;
        this.mContacts = contacts;
        arrayList = new ArrayList<>();
        this.arrayList.addAll(mContacts);

    }

    private static class ViewHolder {

        TextView name;
        CircleImageView contactImage;
        ProgressBar mProgressBar;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        /**
         *
         * ViewHolder Build Pattern Start *
         */
        final ViewHolder holder;
        if (convertView == null) {

            convertView = mInflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();

            //******** stuff to change ********
            holder.name = convertView.findViewById(R.id.contactName);
            holder.contactImage = convertView.findViewById(R.id.contactImage);
            //*********************************
            holder.mProgressBar = convertView.findViewById(R.id.contactProgressBar);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //******** stuff to change ********

        String namee = getItem(position).getName();
        String imagePath = getItem(position).getProfileImage();
        holder.name.setText(namee);

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(mAppend + imagePath, holder.contactImage, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                holder.mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                holder.mProgressBar.setVisibility(View.GONE);

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                holder.mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                holder.mProgressBar.setVisibility(View.GONE);
            }
        });


        //*********************************

        return convertView;

    }
}
