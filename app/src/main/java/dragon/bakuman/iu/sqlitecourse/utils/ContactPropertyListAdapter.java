package dragon.bakuman.iu.sqlitecourse.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import dragon.bakuman.iu.sqlitecourse.MainActivity;
import dragon.bakuman.iu.sqlitecourse.R;
import dragon.bakuman.iu.sqlitecourse.models.Contact;

public class ContactPropertyListAdapter extends ArrayAdapter<String> {

    private static final String TAG = "ContactPropertyListAdap";

    private LayoutInflater mInflater;
    private List<String> mProperties = null;
    private int layoutResource;
    private Context mContext;
    private String mAppend;


    public ContactPropertyListAdapter(@NonNull Context context, int resource, @NonNull List<String> properties) {
        super(context, resource, properties);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutResource = resource;
        this.mContext = context;
        this.mProperties = properties;

    }

    private static class ViewHolder {

        TextView property;
        ImageView rightIcon;
        ImageView leftIcon;

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
            holder.property = convertView.findViewById(R.id.tvMiddleCardView);
            holder.rightIcon = convertView.findViewById(R.id.iconRightCardView);
            holder.leftIcon = convertView.findViewById(R.id.iconLeftCardView);
            //*********************************

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //******** stuff to change ********

        final String property = getItem(position);
        holder.property.setText(property);

        //check if its email or phone number
        //email
        if (property.contains("@")) {
            holder.leftIcon.setImageResource(mContext.getResources().getIdentifier("@drawable/ic_email", null, mContext.getPackageName()));
            holder.leftIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: opening email ");
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("plain/text");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{property});
                    mContext.startActivity(emailIntent);
                }
            });

        } else if ((property.length() != 0)) {
            holder.leftIcon.setImageResource(mContext.getResources().getIdentifier("@drawable/ic_call", null, mContext.getPackageName()));

            holder.leftIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((MainActivity) mContext).checkPermission(Init.PHONE_PERMISSIONS)) {
                        Log.d(TAG, "onClick: initiating phone call....");
                        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", property, null));
                        mContext.startActivity(callIntent);

                    } else {
                        ((MainActivity) mContext).verifyPermissions(Init.PHONE_PERMISSIONS);
                    }
                }
            });

            //setup the icon for sending text messages
            holder.rightIcon.setImageResource(mContext.getResources().getIdentifier("@drawable/ic_chat", null, mContext.getPackageName()));
            holder.rightIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: initiating text message...");

                    //the number that we want to send SMS
                    Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", property, null));
                    mContext.startActivity(smsIntent);
                }
            });

        }


        //*********************************

        return convertView;

    }
}
