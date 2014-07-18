package com.west.todolist;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.west.todolist.database.Task;
import com.west.todolist.database.TaskDBHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class MainActivity extends Activity {

    public TaskDBHelper db;

    public List<Task> list;

    public MyAdapter adapt;

    public CheckBox checkBox1;

   // public ActionMode mActionMode;

    TextView taskTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        db = new TaskDBHelper(this);
        list = db.getAllTasks();
        adapt = new MyAdapter(this, R.layout.list_inner_view, list, null);

        //new
        //ListView listView = getListView();


        ListView listTask = (ListView) findViewById(R.id.listView1);


        listTask.setAdapter(adapt);

        //ArrayAdapter<Task> MyAdapter = new MyAdapter(this, R.layout.list_inner_view, list, listView);

        listTask.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listTask.setMultiChoiceModeListener(new MultiChoiceImpl(listTask));

    }


    public void addTaskNow(View v) {
        EditText t = (EditText) findViewById(R.id.editText1);
        String s = t.getText().toString();
        if (s.equalsIgnoreCase("")) {
            Toast.makeText(this, "Сначала введите задачу!", Toast.LENGTH_LONG).show();
        } else {
            Task task = new Task(s, 0);
            db.addTask(task);
            //
            t.setText("");
            adapt.add(task);
            adapt.notifyDataSetChanged();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//    }


    public class MyAdapter extends ArrayAdapter<Task>{

        Context context;

        CheckBox chk;

        public ListView listView;

        List<Task> taskList = new ArrayList<Task>();
        int layoutResourceId;

        public MyAdapter(Context context, int layoutResourceId, List<Task> objects, ListView listView) {
            super(context, layoutResourceId, objects);

            this.layoutResourceId = layoutResourceId;
            this.taskList = objects;
            this.context = context;
            this.listView = listView;

        }

         class ViewHolder {
            TextView text;
            CheckBox chk;
        }


        public View getView(final int position, View convertView, ViewGroup parent) {
            CheckBox chk = null;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_inner_view, parent, false);
                chk = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(chk);

                chk.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        Task changeTask = (Task) cb.getTag();
                        changeTask.setStatus(cb.isChecked() == true ? 1 : 0);

                        db.updateTask(changeTask);


                    }
                });

            } else {
                chk = (CheckBox) convertView.getTag();
            }
            Task current = taskList.get(position);
            chk.setText(current.getTaskName());
            chk.setTag(current);
            Log.d("listiner", String.valueOf(current.getId()));

            return convertView;

//            if (convertView==null){
//                LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
//                convertView = inflater.inflate(R.layout.list_inner_view, parent, false);
//                ViewHolder h = new ViewHolder();
//                h.text = (TextView) convertView.findViewById(R.id.taskTxt);
//                h.chk = (CheckBox) convertView.findViewById(R.id.checkBox1);
//                convertView.setTag(h);
//            }
//
//            ViewHolder h = (ViewHolder) convertView.getTag();
//
//
//                //????
//            h.text.setText("gg");
//            h.chk.isChecked();
//
//            h.chk.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    selectRow(v);
//                }
//
//                private void selectRow(View v) {
//                    listView.setItemChecked(position, !isItemChecked(position));
//                }
//
//                private boolean isItemChecked(int pos) {
//                    SparseBooleanArray sparseBooleanArray = listView.getCheckedItemPositions();
//                    return sparseBooleanArray.get(pos);
//                }
//            });
//            return convertView;
        }

    }



}

