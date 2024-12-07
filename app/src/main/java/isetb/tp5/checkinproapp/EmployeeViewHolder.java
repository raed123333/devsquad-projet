package isetb.tp5.checkinproapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EmployeeViewHolder extends RecyclerView.ViewHolder {
    TextView nameTextView, emailTextView, posteTextView, ageTextView, cinTextView;

    public EmployeeViewHolder(@NonNull View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.nameTextView);
        emailTextView = itemView.findViewById(R.id.emailTextView);
        posteTextView = itemView.findViewById(R.id.posteTextView);
        ageTextView = itemView.findViewById(R.id.ageTextView);
        cinTextView = itemView.findViewById(R.id.cinTextView);
    }
}
