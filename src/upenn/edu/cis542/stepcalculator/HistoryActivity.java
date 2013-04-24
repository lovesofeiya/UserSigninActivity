package upenn.edu.cis542.stepcalculator;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class HistoryActivity extends Activity {
	

    SimpleAdapter adapter;
    //non-edit mode
    boolean delete = false;
    Button bntEdit;
    //these are input sesson datas
    int numSessions = 5;
    String[] dataDate = new String[numSessions];
    String[] dataTime = new String[numSessions];
    String[] dataDist = new String[numSessions];
    
    static int clickPos;
    
    /** Called when the activity is first created. */ 
    @Override 
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setTitle("History");
        setContentView(R.layout.activity_history);
        
        //fake session data
        //will be imported later
        for(int i = 0; i<numSessions; i++){
        	dataDate[i] = new String("today");
        	dataTime[i] = new String("13:00:00");
        	dataDist[i] = new String("1.0 mi");
        }
     
        
        ListView lv=(ListView)findViewById(R.id.historyList);
        bntEdit=(Button)findViewById(R.id.edit);
        bntEdit.setText("Edit");
        
		final ArrayList<HashMap<String, Object>> items = getItems();

		adapter = new SimpleAdapter(this, items, R.layout.history_item, 
	                new String[] {"date","mile","startTime","endTime"}, 
	                new int[] {R.id.item_date, R.id.item_mile, R.id.item_startTime,R.id.item_endTime});

	    lv.setAdapter(adapter);

	    lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
              
            	if(delete == false){
	            	//start googlemaptest activity to show history detail
	                Intent intent=new Intent(); 
	                intent.setClass(HistoryActivity.this,GoogleMapTest.class); 
	                HistoryActivity.this.startActivity(intent); 
            	}
            	else{
            		clickPos = pos;
	            	showInfo(items);
            	}
   
            }
    });
	    
	    
	    
    } 
    public void edit(View view){
    	//if it's going to be edit status
    	if(delete == false){
	    	delete = true;
	    	bntEdit.setText("Done");
    	}
    	else{
    		delete = false;
    		bntEdit.setText("Edit");
    	}
    }
	public void deleteItem(){
	    adapter.notifyDataSetChanged();

	}

	  public void showInfo(final ArrayList<HashMap<String, Object>> items){
	        new AlertDialog.Builder(this)
	        .setTitle("Delete session")
	        .setMessage("Do you want to delete this sesson?")
	        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	            	items.remove(clickPos);
	            	deleteItem();
	            	dialog.cancel();
	            }
	        })
	        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	            	dialog.cancel();
	            }
	        })
	        .show();
	         
	    }
	 private ArrayList<HashMap<String, Object>> getItems() {
	        ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
	        for(int i = 0; i < numSessions; i++) {
	         
	             HashMap<String, Object> map = new HashMap<String, Object>();
	             map.put("date", dataDate[i]);
	        	 map.put("mile", dataDist[i]);
	        	 map.put("startTime", dataTime[i]);
	        	 map.put("endTime", dataTime[i]);
	       	
	            items.add(map);
	        }
	        return items;
	    }
	 
		public void goBack(View view){
			 Intent intent = new Intent();  
			 intent.setClass(this, MainSessionActivity.class );
		     startActivity(intent);
		}
	

}
