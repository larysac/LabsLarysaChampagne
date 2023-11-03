package algonquin.cst2335.labslarysachampagne;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import algonquin.cst2335.labslarysachampagne.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.labslarysachampagne.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {
    ActivityChatRoomBinding binding;
    ArrayList<String> messages = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.recyclerView.setAdapter(new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                return new MyRowHolder( binding.getRoot());
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                holder.message.setText("");
                holder.time.setText("");
                String obj = messages.get(position);
                holder.message.setText(obj);

            }

            @Override
            public int getItemCount() {
                return messages.size();
            }
        });



    }
    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView message;
        TextView time;
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            message =itemView.findViewById(R.id.message);
            time =itemView.findViewById(R.id.time);
        }
    }
}