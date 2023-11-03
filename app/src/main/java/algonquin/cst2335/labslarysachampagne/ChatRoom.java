package algonquin.cst2335.labslarysachampagne;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ViewGroup;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import algonquin.cst2335.labslarysachampagne.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.labslarysachampagne.databinding.ReceiveMessageBinding;
import algonquin.cst2335.labslarysachampagne.databinding.SentMessageBinding;
import data.ChatRoomViewModel;

public class ChatRoom extends AppCompatActivity {

    private ActivityChatRoomBinding binding;
    private ChatRoomViewModel chatModel;
    private ArrayList<ChatMessage> messages;
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

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));


        binding.sendButton.setOnClickListener(click -> {
            String messageText = binding.textinput.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());


            ChatMessage chatMessage = new ChatMessage(messageText, currentDateandTime, true);
            messages.add(chatMessage);


            myAdapter.notifyItemInserted(messages.size() - 1);
            binding.textinput.setText("");
        });

        binding.receiveButton.setOnClickListener(click -> {
            String messageText = binding.textinput.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());

            ChatMessage chatMessage = new ChatMessage(messageText, currentDateandTime, false);
            messages.add(chatMessage);

            myAdapter.notifyItemInserted(messages.size() - 1);
            binding.textinput.setText("");
        });

        binding.recyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == 0) {
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater(), parent, false);
                    return new MyRowHolder(binding);
                } else {
                    ReceiveMessageBinding binding = ReceiveMessageBinding.inflate(getLayoutInflater(), parent, false);
                    return new MyRowHolder(binding);
                }
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage chatMessage = messages.get(position);
                holder.bind(chatMessage);
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
        private SentMessageBinding sentMessageBinding;
        private ReceiveMessageBinding receiveMessageBinding;

        public MyRowHolder(SentMessageBinding sentMessageBinding) {
            super(sentMessageBinding.getRoot());
            this.sentMessageBinding = sentMessageBinding;
        }

        public MyRowHolder(ReceiveMessageBinding receiveMessageBinding) {
            super(receiveMessageBinding.getRoot());
            this.receiveMessageBinding = receiveMessageBinding;
        }

        public void bind(ChatMessage chatMessage) {
            if (chatMessage.isSentButton()) {
                sentMessageBinding.message.setText(chatMessage.getMessage());
                sentMessageBinding.time.setText(chatMessage.getTimeSent());

            } else {
                receiveMessageBinding.message.setText(chatMessage.getMessage());
                receiveMessageBinding.time.setText(chatMessage.getTimeSent());
            }
        }
    }
}