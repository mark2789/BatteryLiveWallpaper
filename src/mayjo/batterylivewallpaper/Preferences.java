package mayjo.batterylivewallpaper;



import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.mobclix.android.sdk.MobclixMMABannerXLAdView;

public class Preferences extends PreferenceActivity {

	private LinearLayout rootView;
	private LinearLayout titleView;
	private ListView preferenceView;
	private TextView textView;
	private AdView adView;
	private CharSequence[] cs = {"Black", "White", "Blue", "Purple", "Grey", "Pink", "Dark Green"};
	private CharSequence[] cs2 = {"black", "white", "blue", "purple", "grey", "pink", "darkGreen"};
	int adPlacement = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
		rootView = new LinearLayout(this);
		rootView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		rootView.setOrientation(LinearLayout.VERTICAL);
		
		// Create the adView
		adView = new AdView(this, AdSize.BANNER, "**************");

		// Initiate a generic request to load it with an ad
		adView.loadAd(new AdRequest());
		rootView.addView(adView);
		
		textView = new TextView(this);
		titleView = new LinearLayout(this);
		titleView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 26));
		titleView.addView(textView);
		
		preferenceView = new ListView(this);
		preferenceView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		preferenceView.setId(android.R.id.list);

		PreferenceScreen screen = createPreferenceHierarchy();
		screen.bind(preferenceView);
		preferenceView.setAdapter(screen.getRootAdapter());
		
		rootView.addView(titleView);
		rootView.addView(preferenceView);
		
		// Create the adView
		AdView adView2 = new AdView(this, AdSize.BANNER, "**************");

		// Initiate a generic request to load it with an ad
		adView2.loadAd(new AdRequest());
		rootView.addView(adView2);
		
		this.setContentView(rootView);
		setPreferenceScreen(screen);
		
	}
	
	private PreferenceScreen createPreferenceHierarchy() {

		PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

		//Color Preference
		ListPreference colorPref = new ListPreference(this);
		colorPref.setKey("colors"); //Refer to get the pref value
		colorPref.setEntries(cs);
		colorPref.setEntryValues(cs2);
		colorPref.setTitle("Background Color");
		colorPref.setSummary("Choose your Background Color");
		root.addPreference(colorPref);
		
		//Number preference
		CheckBoxPreference numberToggle = new CheckBoxPreference(this);
		numberToggle.setKey("numberToggle");
		numberToggle.setTitle("Number toggle");
		numberToggle.setChecked(prefs.getBoolean("numberToggle", true));
		numberToggle.setSummary("Click to toggle number in center");
		root.addPreference(numberToggle);
		
		//Solid bar preference
		CheckBoxPreference barToggle = new CheckBoxPreference(this);
		barToggle.setKey("barToggle");
		barToggle.setTitle("Solid Bar");
		barToggle.setChecked(prefs.getBoolean("barToggle", false));
		barToggle.setSummary("Solid bar (on) or Twenty bars (off)");
		root.addPreference(barToggle);
		
		//Shape preference
		CheckBoxPreference circleOrOval = new CheckBoxPreference(this);
		circleOrOval.setKey("circleOrOval");
		circleOrOval.setTitle("Circle or Oval Shape");
		circleOrOval.setChecked(prefs.getBoolean("circleOrOval", true));
		circleOrOval.setSummary("Circle (on) or Oval (off)");
		root.addPreference(circleOrOval);
		
		return root;
	}
}
