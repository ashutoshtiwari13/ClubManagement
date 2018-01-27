package com.ruuha.bmscms;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by delaroy on 5/10/17.
 */
public class StudentRecyclerAdapter extends RecyclerView.Adapter<StudentRecyclerAdapter.BeneficiaryViewHolder>  {

    private ArrayList<StudentGetterSetter> listStudentGetterSetter;
    private Context mContext;



    public StudentRecyclerAdapter(ArrayList<StudentGetterSetter> listStudentGetterSetter, Context mContext) {
        this.listStudentGetterSetter = listStudentGetterSetter;
        this.mContext = mContext;


    }

    public class BeneficiaryViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewName;
        public AppCompatTextView textViewEmail;
        public AppCompatTextView textViewBranch;
        public AppCompatTextView textViewContact;
        public AppCompatTextView textViewUsn;
        public AppCompatTextView textViewClub;


        public BeneficiaryViewHolder(View view) {
            super(view);
            textViewUsn = (AppCompatTextView) view.findViewById(R.id.textViewUsn);
            textViewName = (AppCompatTextView) view.findViewById(R.id.textViewName);
            textViewBranch = (AppCompatTextView) view.findViewById(R.id.textViewBranch);
            textViewEmail = (AppCompatTextView) view.findViewById(R.id.textViewEmail);
            textViewClub = (AppCompatTextView) view.findViewById(R.id.textViewClub);
            textViewContact = (AppCompatTextView) view.findViewById(R.id.textViewContact);

        }


    }




    @Override
    public BeneficiaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_beneficiary_recycler, parent, false);

        return new BeneficiaryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BeneficiaryViewHolder holder, int position) {
        holder.textViewUsn.setText(listStudentGetterSetter.get(position).getUsn());
        holder.textViewName.setText(listStudentGetterSetter.get(position).getName());
        holder.textViewEmail.setText(listStudentGetterSetter.get(position).getEmail());
        holder.textViewBranch.setText(listStudentGetterSetter.get(position).getBranch());
        holder.textViewClub.setText(listStudentGetterSetter.get(position).getClub());
        holder.textViewContact.setText(listStudentGetterSetter.get(position).getContact());



    }


    @Override
    public int getItemCount() {
        return listStudentGetterSetter.size();
    }

    public void setFilter(ArrayList<StudentGetterSetter> newList){

        listStudentGetterSetter=new ArrayList<>();
        listStudentGetterSetter.addAll(newList);
        notifyDataSetChanged();
    }



}
