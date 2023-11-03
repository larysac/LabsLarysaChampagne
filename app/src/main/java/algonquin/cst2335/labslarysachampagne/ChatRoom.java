package algonquin.cst2335.labslarysachampagne;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import algonquin.cst2335.labslarysachampagne.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.labslarysachampagne.databinding.ReceiveMessageBinding;
import algonquin.cst2335.labslarysachampagne.databinding.SentMessageBinding;
import data.ChatRoomViewModel;

public class ChatRoom extends AppCompatActivity {

     ActivityChatRoomBinding binding;
     ChatRoomViewModel chatModel;
     ArrayList<ChatMessage> messages= new ArrayList<>();
    private RecyclerView.Adapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();

        if (messages == null) {
            chatModel.messages.postValue(messages = new ArrayList<>());
        }







        binding.sendButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            String text = binding.textinput.getText().toString();
            ChatMessage sendMessage = new ChatMessage(text, currentDateandTime, true);

            messages.add(sendMessage);
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.textinput.setText("");


        });


            binding.receiveButton.setOnClickListener(click -> {
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
                String currentDateandTime = sdf.format(new Date());
                String receiveText = binding.textinput.getText().toString();
                ChatMessage receiveMessage = new ChatMessage(receiveText, currentDateandTime, false);

                messages.add(receiveMessage);
                myAdapter.notifyItemInserted(messages.size()-1);
                binding.textinput.setText("");

        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if(viewType == 0){
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder( binding.getRoot() );
                }
                ReceiveMessageBinding binding = ReceiveMessageBinding.inflate(getLayoutInflater());
                return new MyRowHolder( binding.getRoot() );

            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage obj = messages.get(position);
                // msg on bind
                holder.message.setText(obj.getMessage());
                // time on bind
                holder.time.setText(obj.getTimeSent());
            }


            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position) {
                if (messages.get(position).isSentButton()) {
                    return 0; // Sent message
                } else {
                    return 1; // Received message
                }
            }
        });
    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView message;
        TextView time;


        public MyRowHolder(@NonNull View itemView){
            super(itemView);
            message = itemView.findViewById(R.id.message);
            time= itemView.findViewById(R.id.time);

}
    }
            }