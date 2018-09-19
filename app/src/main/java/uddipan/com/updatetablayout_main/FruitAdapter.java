package uddipan.com.updatetablayout_main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kamere on 4/12/2018.
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.FruitViewHolder> implements Filterable{

    Context context;
    List<Fruits> fruitsList;
    List<Fruits> filteredFruitList;
    private FruitAdapterListener listener;

    public FruitAdapter(Context context, List<Fruits> fruitsList, FruitAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.fruitsList = fruitsList;
        this.filteredFruitList = fruitsList;

    }

    @Override
    public FruitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate our view holder
       LayoutInflater inflater = LayoutInflater.from(context);
       View view1 = inflater.inflate(R.layout.fruits_list_data,null);
       return new FruitViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(FruitViewHolder holder, int position) {

        Fruits fruits = filteredFruitList.get(position);
        holder.fName.setText(fruits.getName());
        holder.fDesc.setText(fruits.getDesc());
        holder.fImage.setImageDrawable(context.getResources().getDrawable(fruits.getImage()));
    }

    @Override
    public int getItemCount() {
        return filteredFruitList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredFruitList = fruitsList;

                } else {
                    List<Fruits> filteredList = new ArrayList<>();
                    for (Fruits row : fruitsList) {


                        //filter according to your case
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getDesc().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                        Log.i("Car Plate",charString);
                    }

                    filteredFruitList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredFruitList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredFruitList = (ArrayList<Fruits>) filterResults.values;
                notifyDataSetChanged();

            }
        };

    }

    public class FruitViewHolder extends RecyclerView.ViewHolder {
        TextView fName,fDesc;
        ImageView fImage;


        public FruitViewHolder(View itemView) {
            super(itemView);
            fName = itemView.findViewById(R.id.fname);
            fDesc = itemView.findViewById(R.id.fdesc);
            fImage = itemView.findViewById(R.id.fImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
            listener.onFruitSelected(filteredFruitList.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface FruitAdapterListener {
        void onFruitSelected(Fruits fruits);
    }
}
