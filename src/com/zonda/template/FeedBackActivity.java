package com.zonda.template;

import java.util.List;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.model.Conversation;
import com.umeng.fb.model.Conversation.SyncListener;
import com.umeng.fb.model.DevReply;
import com.umeng.fb.model.Reply;

public class FeedBackActivity extends BaseActivity {

	private EditText feedBackEt;

	private FeedbackAgent agent;

	private Conversation defaultConversation;

	@Override
	protected void onCreate(Bundle arg0) {

		super.onCreate(arg0);

		setContentView(R.layout.feed_back_layout);

		feedBackEt = (EditText) findViewById(R.id.feedback_text_et);

		agent = new FeedbackAgent(this);

		defaultConversation = agent.getDefaultConversation();

		sync();
	}

	private void sync() {

		defaultConversation.sync(new SyncListener() {

			@Override
			public void onSendUserReply(List<Reply> arg0) {

				if (arg0 == null) {

					return;
				}

				// Toast.makeText(
				// getApplicationContext(),
				// "user-reply: "
				// + (arg0.isEmpty() ? "0" : arg0.get(0)
				// .getContent()), Toast.LENGTH_SHORT)
				// .show();
			}

			@Override
			public void onReceiveDevReply(List<DevReply> arg0) {

				if (arg0 == null) {

					return;
				}

				// Toast.makeText(
				// getApplicationContext(),
				// "onReceiveDevReply: "
				// + (arg0.isEmpty() ? "0" : arg0.get(0)
				// .getContent()), Toast.LENGTH_SHORT)
				// .show();
			}
		});
	}

	public void onClick(View view) {

		String feedbackContent = feedBackEt.getText().toString();

		if (TextUtils.isEmpty(feedbackContent)) {

			feedBackEt.setHint(R.string.feedback_input_tip_text);

			return;
		}
		
		defaultConversation.addUserReply(feedbackContent);

		sync();
		
		feedBackEt.setText("");
		feedBackEt.setHint(R.string.feedback_success);
	}
}
