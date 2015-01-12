package hbworld.play.bluetooth;


import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

// this activity is to turn bluetooth on and jump to chat window

public class MainActivity extends ActionBarActivity {

	public TextView Intro;
	public TextView Device_Name;
	public Button Turn_on;
	public Button View_List;

	private BluetoothAdapter btAdapter;

	BroadcastReceiver BluetoothState = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			String stateExtra = BluetoothAdapter.EXTRA_STATE;
			int state = intent.getIntExtra(stateExtra, -1);

			switch (state) {
			case (BluetoothAdapter.STATE_TURNING_ON): {
				break;
			}

			case (BluetoothAdapter.STATE_TURNING_OFF): {
				break;
			}

			case (BluetoothAdapter.STATE_OFF): {
				setupUI();
				break;
			}
			case (BluetoothAdapter.STATE_ON): {
				setupUI();
				break;
			}
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setupUI();

	}

	private void setupUI() {

		final TextView Intro = (TextView) findViewById(R.id.textView1);
		final TextView Device_Name = (TextView) findViewById(R.id.textView2);
		final Button Turn_on = (Button) findViewById(R.id.button1);
		final Button View_List = (Button) findViewById(R.id.button2);

		Intro.setVisibility(View.GONE);
		Device_Name.setVisibility(View.GONE);
		Turn_on.setVisibility(View.GONE);
		View_List.setVisibility(View.GONE);

		btAdapter = BluetoothAdapter.getDefaultAdapter();
		if (btAdapter.isEnabled()) {
			String address = btAdapter.getAddress();
			String name = btAdapter.getName();
			String device_name = "Your Device Name"+"\n"+ name + "\n" + address;
			Device_Name.setText(device_name);
			Device_Name.setVisibility(View.VISIBLE);
			Intro.setVisibility(View.GONE);
			View_List.setVisibility(View.VISIBLE);

			Turn_on.setVisibility(View.GONE);

		} else {

			Intro.setVisibility(View.VISIBLE);
			Device_Name.setVisibility(View.GONE);
			Turn_on.setVisibility(View.VISIBLE);
			View_List.setVisibility(View.GONE);

		}

		Turn_on.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String actionStateChanged = BluetoothAdapter.ACTION_STATE_CHANGED;
				String actionRequestEnable = BluetoothAdapter.ACTION_REQUEST_ENABLE;
				IntentFilter filter = new IntentFilter(actionStateChanged);
				registerReceiver(BluetoothState, filter);
				startActivityForResult(new Intent(actionRequestEnable), 0);

			}
		});

		View_List.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ChatWindow.class);
				startActivity(intent);
				finish();

			}
		});

	}

}
