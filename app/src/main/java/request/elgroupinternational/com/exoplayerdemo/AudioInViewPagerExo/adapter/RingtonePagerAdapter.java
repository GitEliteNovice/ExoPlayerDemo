package request.elgroupinternational.com.exoplayerdemo.AudioInViewPagerExo.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import request.elgroupinternational.com.exoplayerdemo.R;


public class RingtonePagerAdapter extends PagerAdapter {

    private Context mContext;
    List<String> data;
    public RingtonePagerAdapter(Context context, List<String> data) {
        mContext = context;
        this.data=data;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.detailed_item_view, collection, false);
        TextView detailed_textview=layout.findViewById(R.id.detailed_textview);
        /*String[] info = data.get(position).split("&");
        StringBuilder stringBuilder=new StringBuilder();
        for (int i=0;i<info.length;i++){
            stringBuilder.append(info[i]).append("\n");
        }*/
        detailed_textview.setText(data.get(position));
                collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}
