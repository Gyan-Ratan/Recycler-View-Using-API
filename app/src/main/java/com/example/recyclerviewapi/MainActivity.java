package com.example.recyclerviewapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recycle; // defining the recycler view in the main activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recycle =findViewById(R.id.recycle); //        getting the id
//    creating method
        listingdata();
// Layout link kr rahe hai BC
        LinearLayoutManager llm =new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
//Linerlayout ko recycler ke saath set kr rahe
        recycle.setLayoutManager(llm);
    }

    private void listingdata() {
        ApiInterface apiInterface =Retrofit.getRetrofit().create(ApiInterface.class);
        Call<JavaSchemapojo> listingdata =apiInterface.getdata();
        listingdata.enqueue(new Callback<JavaSchemapojo>() {
            @Override
            public void onResponse(Call<JavaSchemapojo> call, Response<JavaSchemapojo> response) {
                if(response.isSuccessful()){
                    // if there is no error in fetching the data;
                    recycleadapter adapter =new recycleadapter(response.body().getData());
                    recycle.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<JavaSchemapojo> call, Throwable t) {
//    If data is not able to process
                Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_SHORT);
            }
        });
    }

    //        making the adapter of the RecycleView
    class recycleadapter extends RecyclerView.Adapter<recycleadapter.MyViewHolder> {
        // created Constructor for the data
    List<JavaSchemapojo.Datum> list;
    public recycleadapter(List<JavaSchemapojo.Datum> list){
        this.list = list;
    }
        @NonNull
        @Override
        public recycleadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlayout,null);
            recycleadapter.MyViewHolder viewHolder= new MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull recycleadapter.MyViewHolder holder, int position) {
//            NOW CONNECTING THE DATA (binding the api data with the layouts)
            holder.category.setText(list.get(position).getName());
            Picasso.with(getApplicationContext())
                    .load(list.get(position).getImage().getSmallPath())
                    .placeholder(R.id.catimg)
                    .fit()
                    .into(holder.catimg);
        }

        @Override
        public int getItemCount() {
            // return list.size(); //counts the size of the list <LIST>
            return list.size()>0 ? list.size():1;
        }
        class MyViewHolder extends RecyclerView.ViewHolder{
// here defining the layout ids
                TextView category;
                ImageView catimg;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                catimg = itemView.findViewById(R.id.catimg);
                category =itemView.findViewById(R.id.category);
            }
        }
    }
}